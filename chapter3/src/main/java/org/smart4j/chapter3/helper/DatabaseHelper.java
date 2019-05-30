package org.smart4j.chapter3.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter3.utils.PropUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wangteng on 2019/5/30.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final BasicDataSource DATA_SOURCE;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    static {
        Properties prop = PropUtil.loadProps("jdbc.properties");
        DRIVER = PropUtil.getString(prop, "jdbc.driver");
        URL = PropUtil.getString(prop, "jdbc.url");
        USERNAME = PropUtil.getString(prop, "jdbc.username");
        PASSWORD = PropUtil.getString(prop, "jdbc.password");

//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("load jdbc driver failure", e);
//        }
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }


    public static Connection getConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL.get();
        if(conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
                CONNECTION_THREAD_LOCAL.set(conn);
            } catch (SQLException e) {
                LOGGER.error("get jdbc connection failure", e);
            }
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close jdbc connection failure", e);
            }
        }
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object ...params) {
        Connection conn = getConnection();
        List<T> resultList;
        try {
            resultList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
        }
        return resultList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object ...params) {
        Connection conn = getConnection();
        T result = null;
        try {
            result = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
        }
        return result;
    }

    public static List<Map<String, Object>> queryMapList(String sql, Object ...params) {
        Connection conn = getConnection();
        List<Map<String, Object>> resultList;
        try {
            resultList = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query map list failure", e);
            throw new RuntimeException(e);
        } finally {
        }
        return resultList;
    }

    public static int executeUpdate(String sql, Object ...params) {
        Connection conn = getConnection();
        int rows = 0;
        try {
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute sql failure", e);
            throw new RuntimeException(e);
        } finally {
        }

        return rows;
    }

    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for(String fieldName: fieldMap.keySet()) {
            columns.append(fieldName + ", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), "");
        values.replace(values.lastIndexOf(","), values.length(), "");
        sql += " ( " + columns + ")";
        sql += "values (" + values + ")";
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean updateEntity(Class<T> entityClass, Map<String, Object> fieldMap, long id) {
        String sql = "update " + getTableName(entityClass) + " set ";
        StringBuilder columns = new StringBuilder();
        for(String fieldName: fieldMap.keySet()) {
            columns.append(fieldName + " = ?, ");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), "");
        sql += columns + " where id = ? ";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delete from " + getTableName(entityClass) + " where id = ? ";
        return executeUpdate(sql, id) == 1;
    }

    public static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

    public static void executeSqlFile(String fileName) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if(resourceAsStream == null) {
            LOGGER.error("sql file not found" + fileName);
            throw new RuntimeException(fileName + "sql file not found");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        try {
            String sql;
            while ((sql = bufferedReader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        } finally {
            try {
                bufferedReader.close();
                resourceAsStream.close();
            } catch (IOException e) {
                LOGGER.error("close input stream failure", e);
            }
        }

    }
}

package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.*;

/**
 * Created by wangteng on 2019/5/29.
 */
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    /*private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    static {
        Properties props = PropsUtil.loadProps("config.properties");
        DRIVER = props.getProperty("jdbc.driver");
        URL = props.getProperty("jdbc.url");
        USERNAME = props.getProperty("jdbc.username");
        PASSWORD = props.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("can not load jdbc driver", e);
        }

    }*/

    public List<Customer> getCustomerList() {
        Connection conn = null;
        String sql = "select * from customer";
        List<Customer> customerList = DatabaseHelper.qryEntityList(Customer.class, sql);
        return customerList ;
    }

    public Customer getCustomer(long id) {
        String sql = "select * from customer where id = ?";
        List<Object> list = new ArrayList<>();
        list.add(id);
        return DatabaseHelper.qryEntity(Customer.class, sql, list.toArray());
    }

    public boolean createCustomer(Map<String, Object> fieleMap) {
        //TODO
        return false;
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        //TODO
        return false;
    }

    public boolean deleteCustomer(long id) {
        //TODO
        return false;
    }

}

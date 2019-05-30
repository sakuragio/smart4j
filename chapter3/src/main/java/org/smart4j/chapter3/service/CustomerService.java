package org.smart4j.chapter3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter3.helper.DatabaseHelper;
import org.smart4j.chapter3.model.Customer;
import org.smart4j.chapter3.utils.PropUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wangteng on 2019/5/30.
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public List<Customer> getCustomerList() {
        List<Customer> result = new ArrayList<>();
        String sql = "select * from customer";
        result = DatabaseHelper.queryEntityList(Customer.class, sql);
        return result;
    }

    public List<Map<String, Object>> queryMapList() {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String sql = "select * from customer";
        resultList = DatabaseHelper.queryMapList(sql);
        return resultList;
    }

    public Customer getCustomer(long id) {
        String sql = "select * from customer where id = ?";
        return DatabaseHelper.queryEntity(Customer.class, sql,  id);
    }

    public boolean updateCustomer(long id, Map<String, Object> fielaMap) {
        return DatabaseHelper.updateEntity(Customer.class, fielaMap, id);
    }

    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }

}

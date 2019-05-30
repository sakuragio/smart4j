package org.smart4j.chapter3.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter3.helper.DatabaseHelper;
import org.smart4j.chapter3.model.Customer;
import org.smart4j.chapter3.service.CustomerService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangteng on 2019/5/30.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() {
        DatabaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    @Test
    public void getCustomerListTest() {
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(2, customerList.size());
    }

    @Test
    public void getMapListTest() {
        List<Map<String, Object>> resultList = customerService.queryMapList();
        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void getCustomerTest() {
        long id = 1;
        Customer customer = customerService.getCustomer(1);
        Assert.assertNotNull(customer);
    }

    @Test
    public void updateCustomerTest() {
        long id = 1;
        Map<String, Object> fileMap = new HashMap<>();
        fileMap.put("contact", "Eric");
        boolean result = customerService.updateCustomer(1, fileMap);
        Assert.assertTrue(result);
    }

    @Test
    public void createCustomerTest() {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "customerN");
        fieldMap.put("contact", "wangteng");
        fieldMap.put("telephone", "15720613056");
        fieldMap.put("email", "narutodunk@gmail.com");
        fieldMap.put("remark", "test");
        boolean customer = customerService.createCustomer(fieldMap);
        Assert.assertTrue(customer);
    }

    @Test
    public void deleteCustomerTest() {
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }



}

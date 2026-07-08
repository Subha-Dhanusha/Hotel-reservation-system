package com.hotel.dao;

import com.hotel.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> CUSTOMER_MAPPER = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setPhone(rs.getString("phone"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        return customer;
    };

    public boolean registerCustomer(Customer customer) {
        int rows = jdbcTemplate.update(
                "INSERT INTO customers(name, phone, email, password) VALUES(?,?,?,?)",
                customer.getName(), customer.getPhone(), customer.getEmail(), customer.getPassword());
        return rows > 0;
    }

    public Customer loginCustomer(String email, String password) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM customers WHERE email=? AND password=?",
                    CUSTOMER_MAPPER, email, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean emailExists(String email) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM customers WHERE email=?", Integer.class, email);
        return count != null && count > 0;
    }

    public boolean phoneExists(String phone) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM customers WHERE phone=?", Integer.class, phone);
        return count != null && count > 0;
    }

    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query("SELECT * FROM customers", CUSTOMER_MAPPER);
    }

    public boolean deleteCustomer(int customerId) {
        int rows = jdbcTemplate.update("DELETE FROM customers WHERE customer_id=?", customerId);
        return rows > 0;
    }

    public int getTotalCustomers() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customers", Integer.class);
        return count == null ? 0 : count;
    }
}

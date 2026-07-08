package com.hotel.dao;

import com.hotel.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Admin> ADMIN_MAPPER = (rs, rowNum) -> {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        return admin;
    };

    public Admin login(String username, String password) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM admin WHERE username=? AND password=?",
                    ADMIN_MAPPER, username, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

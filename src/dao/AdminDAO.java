package dao;

import database.DBConnection;
import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Admin Login
    public Admin login(String username, String password) {

        Admin admin = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM admin WHERE username=? AND password=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {

                admin = new Admin();

                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return admin;

    }

}
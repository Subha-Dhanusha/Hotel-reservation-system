package dao;

import database.DBConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Register Customer
    public boolean registerCustomer(Customer customer) {

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO customers(name, phone, email, password) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());

            int row = ps.executeUpdate();

            return row > 0;

        } catch (Exception e) {

            System.out.println(e);

            return false;
        }

    }
    public Customer loginCustomer(String email,String password){

        Customer customer=null;

        try{

            con=DBConnection.getConnection();

            String sql="SELECT * FROM customers WHERE email=? AND password=?";

            ps=con.prepareStatement(sql);

            ps.setString(1,email);

            ps.setString(2,password);

            rs=ps.executeQuery();

            if(rs.next()){

                customer=new Customer();

                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return customer;

    }
    // Check Email Already Exists
    public boolean emailExists(String email) {

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM customers WHERE email=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, email);

            rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            System.out.println(e);

            return false;
        }

    }

    // Check Phone Already Exists
    public boolean phoneExists(String phone) {

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM customers WHERE phone=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, phone);

            rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            System.out.println(e);

            return false;
        }


    }
    // View Customers

    public ResultSet getAllCustomers(){

        try{

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM customer";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

        }
        catch(Exception e){

            System.out.println(e);

        }

        return rs;

    }
    // Delete Customer

    public boolean deleteCustomer(int customerId){

        try{

            con = DBConnection.getConnection();

            String sql = "DELETE FROM customer WHERE customer_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1,customerId);

            return ps.executeUpdate()>0;

        }
        catch(Exception e){

            System.out.println(e);

        }

        return false;

    }
    // Total Customers
    public int getTotalCustomers(){

        int count=0;

        try{

            con=DBConnection.getConnection();

            String sql="SELECT COUNT(*) FROM customer";

            ps=con.prepareStatement(sql);

            rs=ps.executeQuery();

            if(rs.next()){

                count=rs.getInt(1);

            }

        }catch(Exception e){

            System.out.println(e);

        }

        return count;

    }

}
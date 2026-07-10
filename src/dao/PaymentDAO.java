package dao;

import database.DBConnection;
import model.Payment;

import java.sql.*;

public class PaymentDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Get Booking Amount
    public double getBookingAmount(int bookingId){

        double amount = 0;

        try{

            con = DBConnection.getConnection();

            String sql = "SELECT total_amount FROM bookings WHERE booking_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, bookingId);

            rs = ps.executeQuery();

            if(rs.next()){

                amount = rs.getDouble("total_amount");

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return amount;

    }

    // Make Payment
    public boolean makePayment(Payment payment){

        try{

            con = DBConnection.getConnection();

            String sql = "INSERT INTO payments(booking_id,amount,payment_date,payment_mode) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1,payment.getBookingId());

            ps.setDouble(2,payment.getAmount());

            ps.setString(3,payment.getPaymentDate());

            ps.setString(4,payment.getPaymentMode());

            int row = ps.executeUpdate();

            return row > 0;

        }
        catch(Exception e){

            System.out.println(e);

        }

        return false;

    }

    // View Payment History
    public ResultSet paymentHistory(){

        try{

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM payments";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

        }
        catch(Exception e){

            System.out.println(e);

        }

        return rs;

    }
    // Payment History

    public ResultSet getPaymentHistory(){

        try{

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM payments";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

        }
        catch(Exception e){

            System.out.println(e);

        }

        return rs;

    }

}
package dao;

import database.DBConnection;
import model.Booking;

import java.sql.*;
import java.util.ArrayList;

public class BookingDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Get Available Rooms
    public ArrayList<String> getAvailableRooms() {

        ArrayList<String> rooms = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT room_no FROM rooms WHERE status='Available'";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){

                rooms.add(rs.getString("room_no"));

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return rooms;

    }

    // Get Room Id

    public int getRoomId(int roomNo){

        int id=0;

        try{

            con=DBConnection.getConnection();

            String sql="SELECT room_id FROM rooms WHERE room_no=?";

            ps=con.prepareStatement(sql);

            ps.setInt(1,roomNo);

            rs=ps.executeQuery();

            if(rs.next()){

                id=rs.getInt("room_id");

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return id;

    }

    // Get Room Price

    public double getRoomPrice(int roomNo){

        double price=0;

        try{

            con=DBConnection.getConnection();

            String sql="SELECT price FROM rooms WHERE room_no=?";

            ps=con.prepareStatement(sql);

            ps.setInt(1,roomNo);

            rs=ps.executeQuery();

            if(rs.next()){

                price=rs.getDouble("price");

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return price;

    }

    // Book Room

    public boolean bookRoom(Booking booking){

        try{

            con=DBConnection.getConnection();

            String sql="INSERT INTO bookings(customer_id,room_id,checkin,checkout,total_amount) VALUES(?,?,?,?,?)";

            ps=con.prepareStatement(sql);

            ps.setInt(1,booking.getCustomerId());

            ps.setInt(2,booking.getRoomId());

            ps.setString(3,booking.getCheckIn());

            ps.setString(4,booking.getCheckOut());

            ps.setDouble(5,booking.getTotalAmount());

            int row=ps.executeUpdate();

            if(row>0){

                String update="UPDATE rooms SET status='Booked' WHERE room_id=?";

                ps=con.prepareStatement(update);

                ps.setInt(1,booking.getRoomId());

                ps.executeUpdate();

                return true;

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return false;

    }
    // View Customer Bookings

    public ResultSet viewBookings(int customerId){

        try{

            con = DBConnection.getConnection();

            String sql = "SELECT b.booking_id,r.room_no,r.room_type,b.checkin,b.checkout,b.total_amount " +
                    "FROM bookings b JOIN rooms r ON b.room_id=r.room_id " +
                    "WHERE b.customer_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);

            rs = ps.executeQuery();

        }
        catch(Exception e){

            System.out.println(e);

        }

        return rs;

    }
    // Cancel Booking

    public boolean cancelBooking(int bookingId){

        try{

            con = DBConnection.getConnection();

            int roomId = 0;

            String sql1 = "SELECT room_id FROM bookings WHERE booking_id=?";

            ps = con.prepareStatement(sql1);

            ps.setInt(1, bookingId);

            rs = ps.executeQuery();

            if(rs.next()){

                roomId = rs.getInt("room_id");

            }
            else{

                return false;

            }

            String sql2 = "DELETE FROM bookings WHERE booking_id=?";

            ps = con.prepareStatement(sql2);

            ps.setInt(1, bookingId);

            int row = ps.executeUpdate();

            if(row > 0){

                String sql3 = "UPDATE rooms SET status='Available' WHERE room_id=?";

                ps = con.prepareStatement(sql3);

                ps.setInt(1, roomId);

                ps.executeUpdate();

                return true;

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return false;

    }
    // Total Bookings
    public int getTotalBookings() {

        int count = 0;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM bookings";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {

                count = rs.getInt(1);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return count;

    }

    // Total Revenue
    public double getTotalRevenue() {

        double total = 0;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT SUM(total_amount) FROM bookings";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {

                total = rs.getDouble(1);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return total;

    }

}
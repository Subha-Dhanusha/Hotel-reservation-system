package dao;

import database.DBConnection;
import model.Room;

import java.sql.*;
import java.util.ArrayList;

public class RoomDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Add Room
    public boolean addRoom(Room room){

        try{

            con = DBConnection.getConnection();

            String sql = "INSERT INTO rooms(room_no,room_type,price,status) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, room.getRoomNo());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getPrice());
            ps.setString(4, room.getStatus());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(e);

        }

        return false;

    }

    // Check Room Exists
    public boolean roomExists(int roomNo){

        try{

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM rooms WHERE room_no=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, roomNo);

            rs = ps.executeQuery();

            return rs.next();

        }catch(Exception e){

            System.out.println(e);

        }

        return false;

    }

    // Update Room
    public boolean updateRoom(Room room){

        try{

            con = DBConnection.getConnection();

            String sql = "UPDATE rooms SET room_type=?,price=?,status=? WHERE room_no=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, room.getRoomType());
            ps.setDouble(2, room.getPrice());
            ps.setString(3, room.getStatus());
            ps.setInt(4, room.getRoomNo());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(e);

        }

        return false;

    }

    // Delete Room
    public boolean deleteRoom(int roomNo){

        try{

            con = DBConnection.getConnection();

            String sql = "DELETE FROM rooms WHERE room_no=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, roomNo);

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println(e);

        }

        return false;

    }

    // View Rooms
    public ArrayList<Room> getAllRooms(){

        ArrayList<Room> list = new ArrayList<>();

        try{

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM rooms";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){

                Room room = new Room();

                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNo(rs.getInt("room_no"));
                room.setRoomType(rs.getString("room_type"));
                room.setPrice(rs.getDouble("price"));
                room.setStatus(rs.getString("status"));

                list.add(room);

            }

        }catch(Exception e){

            System.out.println(e);

        }

        return list;

    }
    // Total Rooms
    public int getTotalRooms(){

        int count=0;

        try{

            con=DBConnection.getConnection();

            String sql="SELECT COUNT(*) FROM rooms";

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

    // Available Rooms
    public int getAvailableRoomsCount(){

        int count=0;

        try{

            con=DBConnection.getConnection();

            String sql="SELECT COUNT(*) FROM rooms WHERE status='Available'";

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

    // Booked Rooms
    public int getBookedRoomsCount(){

        int count=0;

        try{

            con=DBConnection.getConnection();

            String sql="SELECT COUNT(*) FROM rooms WHERE status='Booked'";

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
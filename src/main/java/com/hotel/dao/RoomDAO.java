package com.hotel.dao;

import com.hotel.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Room> ROOM_MAPPER = (rs, rowNum) -> {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setRoomNo(rs.getInt("room_no"));
        room.setRoomType(rs.getString("room_type"));
        room.setPrice(rs.getDouble("price"));
        room.setStatus(rs.getString("status"));
        return room;
    };

    public boolean addRoom(Room room) {
        int rows = jdbcTemplate.update(
                "INSERT INTO rooms(room_no, room_type, price, status) VALUES(?,?,?,?)",
                room.getRoomNo(), room.getRoomType(), room.getPrice(), room.getStatus());
        return rows > 0;
    }

    public boolean roomExists(int roomNo) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM rooms WHERE room_no=?", Integer.class, roomNo);
        return count != null && count > 0;
    }

    public boolean updateRoom(Room room) {
        int rows = jdbcTemplate.update(
                "UPDATE rooms SET room_type=?, price=?, status=? WHERE room_no=?",
                room.getRoomType(), room.getPrice(), room.getStatus(), room.getRoomNo());
        return rows > 0;
    }

    public boolean deleteRoom(int roomNo) {
        int rows = jdbcTemplate.update("DELETE FROM rooms WHERE room_no=?", roomNo);
        return rows > 0;
    }

    public List<Room> getAllRooms() {
        return jdbcTemplate.query("SELECT * FROM rooms ORDER BY room_no", ROOM_MAPPER);
    }

    public Room getRoomByNo(int roomNo) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM rooms WHERE room_no=?", ROOM_MAPPER, roomNo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getTotalRooms() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM rooms", Integer.class);
        return count == null ? 0 : count;
    }

    public int getAvailableRoomsCount() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM rooms WHERE status='Available'", Integer.class);
        return count == null ? 0 : count;
    }

    public int getBookedRoomsCount() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM rooms WHERE status='Booked'", Integer.class);
        return count == null ? 0 : count;
    }
}

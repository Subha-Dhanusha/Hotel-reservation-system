package com.hotel.dao;

import com.hotel.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookingDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Booking> BOOKING_MAPPER = (rs, rowNum) -> {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setRoomNo(rs.getInt("room_no"));
        booking.setRoomType(rs.getString("room_type"));
        booking.setCheckIn(rs.getString("checkin"));
        booking.setCheckOut(rs.getString("checkout"));
        booking.setTotalAmount(rs.getDouble("total_amount"));
        return booking;
    };

    public List<String> getAvailableRooms() {
        return jdbcTemplate.query(
                "SELECT room_no FROM rooms WHERE status='Available'",
                (rs, rowNum) -> rs.getString("room_no"));
    }

    public int getRoomId(int roomNo) {
        try {
            Integer id = jdbcTemplate.queryForObject(
                    "SELECT room_id FROM rooms WHERE room_no=?", Integer.class, roomNo);
            return id == null ? 0 : id;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public double getRoomPrice(int roomNo) {
        try {
            Double price = jdbcTemplate.queryForObject(
                    "SELECT price FROM rooms WHERE room_no=?", Double.class, roomNo);
            return price == null ? 0 : price;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Transactional
    public boolean bookRoom(Booking booking) {
        int rows = jdbcTemplate.update(
                "INSERT INTO bookings(customer_id, room_id, checkin, checkout, total_amount) VALUES(?,?,?,?,?)",
                booking.getCustomerId(), booking.getRoomId(), booking.getCheckIn(),
                booking.getCheckOut(), booking.getTotalAmount());

        if (rows > 0) {
            jdbcTemplate.update("UPDATE rooms SET status='Booked' WHERE room_id=?", booking.getRoomId());
            return true;
        }
        return false;
    }

    public List<Booking> viewBookings(int customerId) {
        return jdbcTemplate.query(
                "SELECT b.booking_id, r.room_no, r.room_type, b.checkin, b.checkout, b.total_amount " +
                        "FROM bookings b JOIN rooms r ON b.room_id = r.room_id WHERE b.customer_id=?",
                BOOKING_MAPPER, customerId);
    }

    @Transactional
    public boolean cancelBooking(int bookingId) {
        Integer roomId;
        try {
            roomId = jdbcTemplate.queryForObject(
                    "SELECT room_id FROM bookings WHERE booking_id=?", Integer.class, bookingId);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        int rows = jdbcTemplate.update("DELETE FROM bookings WHERE booking_id=?", bookingId);
        if (rows > 0 && roomId != null) {
            jdbcTemplate.update("UPDATE rooms SET status='Available' WHERE room_id=?", roomId);
            return true;
        }
        return false;
    }

    public int getTotalBookings() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM bookings", Integer.class);
        return count == null ? 0 : count;
    }

    public double getTotalRevenue() {
        Double total = jdbcTemplate.queryForObject("SELECT COALESCE(SUM(total_amount),0) FROM bookings", Double.class);
        return total == null ? 0 : total;
    }
}

package com.hotel.dao;

import com.hotel.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Payment> PAYMENT_MAPPER = (rs, rowNum) -> {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setBookingId(rs.getInt("booking_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setPaymentDate(rs.getString("payment_date"));
        payment.setPaymentMode(rs.getString("payment_mode"));
        return payment;
    };

    public double getBookingAmount(int bookingId) {
        try {
            Double amount = jdbcTemplate.queryForObject(
                    "SELECT total_amount FROM bookings WHERE booking_id=?", Double.class, bookingId);
            return amount == null ? 0 : amount;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public boolean makePayment(Payment payment) {
        int rows = jdbcTemplate.update(
                "INSERT INTO payments(booking_id, amount, payment_date, payment_mode) VALUES(?,?,?,?)",
                payment.getBookingId(), payment.getAmount(), payment.getPaymentDate(), payment.getPaymentMode());
        return rows > 0;
    }

    public List<Payment> getPaymentHistory() {
        return jdbcTemplate.query("SELECT * FROM payments ORDER BY payment_id DESC", PAYMENT_MAPPER);
    }
}

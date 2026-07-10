package com.hotel.controller;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.CustomerDAO;
import com.hotel.dao.PaymentDAO;
import com.hotel.model.Booking;
import com.hotel.model.Customer;
import com.hotel.model.Payment;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired private CustomerDAO customerDAO;
    @Autowired private BookingDAO bookingDAO;
    @Autowired private PaymentDAO paymentDAO;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("customerId") != null;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "customer/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String phone,
                            @RequestParam String email, @RequestParam String password,
                            Model model) {
        if (customerDAO.emailExists(email)) {
            model.addAttribute("error", "Email already registered.");
            return "customer/register";
        }
        if (customerDAO.phoneExists(phone)) {
            model.addAttribute("error", "Phone number already registered.");
            return "customer/register";
        }

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setPassword(password);
        customerDAO.registerCustomer(customer);

        return "redirect:/customer/login?registered";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "customer/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                         HttpSession session, Model model) {
        Customer customer = customerDAO.loginCustomer(email, password);
        if (customer != null) {
            session.setAttribute("customerId", customer.getCustomerId());
            session.setAttribute("customerName", customer.getName());
            return "redirect:/customer/dashboard";
        }
        model.addAttribute("error", "Invalid email or password");
        return "customer/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/customer/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";
        model.addAttribute("customerName", session.getAttribute("customerName"));
        return "customer/dashboard";
    }

    @GetMapping("/book")
    public String bookRoomForm(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";
        model.addAttribute("rooms", bookingDAO.getAvailableRooms());
        model.addAttribute("today", LocalDate.now().toString());
        return "customer/book-room";
    }

    @PostMapping("/book")
    public String bookRoom(HttpSession session, @RequestParam int roomNo,
                            @RequestParam String checkIn, @RequestParam String checkOut,
                            Model model) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";

        int customerId = (int) session.getAttribute("customerId");
        int roomId = bookingDAO.getRoomId(roomNo);
        double price = bookingDAO.getRoomPrice(roomNo);

        long nights = 1;
        try {
            nights = java.time.temporal.ChronoUnit.DAYS.between(
                    LocalDate.parse(checkIn), LocalDate.parse(checkOut));
            if (nights < 1) nights = 1;
        } catch (Exception ignored) {}

        Booking booking = new Booking();
        booking.setCustomerId(customerId);
        booking.setRoomId(roomId);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setTotalAmount(price * nights);

        boolean success = bookingDAO.bookRoom(booking);
        if (!success) {
            model.addAttribute("error", "Could not complete booking. The room may no longer be available.");
            model.addAttribute("rooms", bookingDAO.getAvailableRooms());
            model.addAttribute("today", LocalDate.now().toString());
            return "customer/book-room";
        }

        return "redirect:/customer/bookings";
    }

    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";
        int customerId = (int) session.getAttribute("customerId");
        model.addAttribute("bookings", bookingDAO.viewBookings(customerId));
        return "customer/bookings";
    }

    @PostMapping("/bookings/cancel/{bookingId}")
    public String cancelBooking(HttpSession session, @PathVariable int bookingId) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";
        bookingDAO.cancelBooking(bookingId);
        return "redirect:/customer/bookings";
    }

    @GetMapping("/payment/{bookingId}")
    public String paymentForm(HttpSession session, @PathVariable int bookingId, Model model) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";
        double amount = paymentDAO.getBookingAmount(bookingId);
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("amount", amount);
        return "customer/payment";
    }

    @PostMapping("/payment")
    public String makePayment(HttpSession session, @RequestParam int bookingId,
                               @RequestParam double amount, @RequestParam String paymentMode) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";

        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now().toString());
        payment.setPaymentMode(paymentMode);
        paymentDAO.makePayment(payment);

        return "redirect:/customer/payment/history";
    }

    @GetMapping("/payment/history")
    public String paymentHistory(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/customer/login";
        model.addAttribute("payments", paymentDAO.getPaymentHistory());
        return "customer/payment-history";
    }
}

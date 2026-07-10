package com.hotel.controller;

import com.hotel.dao.AdminDAO;
import com.hotel.dao.BookingDAO;
import com.hotel.dao.CustomerDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Admin;
import com.hotel.model.Room;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private AdminDAO adminDAO;
    @Autowired private RoomDAO roomDAO;
    @Autowired private BookingDAO bookingDAO;
    @Autowired private CustomerDAO customerDAO;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("adminUsername") != null;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                         HttpSession session, Model model) {
        Admin admin = adminDAO.login(username, password);
        if (admin != null) {
            session.setAttribute("adminUsername", admin.getUsername());
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "Invalid username or password");
        return "admin/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("adminUsername");
        return "redirect:/admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";

        model.addAttribute("adminUsername", session.getAttribute("adminUsername"));
        model.addAttribute("totalRooms", roomDAO.getTotalRooms());
        model.addAttribute("availableRooms", roomDAO.getAvailableRoomsCount());
        model.addAttribute("bookedRooms", roomDAO.getBookedRoomsCount());
        model.addAttribute("totalCustomers", customerDAO.getTotalCustomers());
        model.addAttribute("totalBookings", bookingDAO.getTotalBookings());
        model.addAttribute("totalRevenue", bookingDAO.getTotalRevenue());
        return "admin/dashboard";
    }

    @GetMapping("/rooms")
    public String viewRooms(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";
        model.addAttribute("rooms", roomDAO.getAllRooms());
        return "admin/rooms";
    }

    @GetMapping("/rooms/add")
    public String addRoomForm(HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";
        return "admin/add-room";
    }

    @PostMapping("/rooms/add")
    public String addRoom(HttpSession session, @RequestParam int roomNo, @RequestParam String roomType,
                           @RequestParam double price, @RequestParam String status, Model model) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";

        if (roomDAO.roomExists(roomNo)) {
            model.addAttribute("error", "Room number already exists.");
            return "admin/add-room";
        }

        Room room = new Room();
        room.setRoomNo(roomNo);
        room.setRoomType(roomType);
        room.setPrice(price);
        room.setStatus(status);
        roomDAO.addRoom(room);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/rooms/edit/{roomNo}")
    public String editRoomForm(HttpSession session, @PathVariable int roomNo, Model model) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";
        Room room = roomDAO.getRoomByNo(roomNo);
        if (room == null) return "redirect:/admin/rooms";
        model.addAttribute("room", room);
        return "admin/edit-room";
    }

    @PostMapping("/rooms/edit")
    public String updateRoom(HttpSession session, @RequestParam int roomNo, @RequestParam String roomType,
                              @RequestParam double price, @RequestParam String status) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";
        Room room = new Room();
        room.setRoomNo(roomNo);
        room.setRoomType(roomType);
        room.setPrice(price);
        room.setStatus(status);
        roomDAO.updateRoom(room);
        return "redirect:/admin/rooms";
    }

    @PostMapping("/rooms/delete/{roomNo}")
    public String deleteRoom(HttpSession session, @PathVariable int roomNo) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";
        roomDAO.deleteRoom(roomNo);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/reports")
    public String reports(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/admin/login";
        model.addAttribute("totalRooms", roomDAO.getTotalRooms());
        model.addAttribute("availableRooms", roomDAO.getAvailableRoomsCount());
        model.addAttribute("bookedRooms", roomDAO.getBookedRoomsCount());
        model.addAttribute("totalCustomers", customerDAO.getTotalCustomers());
        model.addAttribute("totalBookings", bookingDAO.getTotalBookings());
        model.addAttribute("totalRevenue", bookingDAO.getTotalRevenue());
        return "admin/reports";
    }
}

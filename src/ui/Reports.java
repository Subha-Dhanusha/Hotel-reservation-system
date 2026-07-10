package ui;

import dao.BookingDAO;
import dao.CustomerDAO;
import dao.RoomDAO;

import java.awt.*;
import java.awt.event.*;

public class Reports extends Frame implements ActionListener {

    Label title;

    Label l1,l2,l3,l4,l5;

    Label totalRooms;
    Label availableRooms;
    Label bookedRooms;
    Label totalCustomers;
    Label totalBookings;

    Label revenueTitle;
    Label revenue;

    Button refresh;
    Button back;

    RoomDAO roomDAO=new RoomDAO();
    CustomerDAO customerDAO=new CustomerDAO();
    BookingDAO bookingDAO=new BookingDAO();

    public Reports(){

        setTitle("Hotel Reports");

        setSize(550,450);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        title=new Label("HOTEL REPORT");
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0, 70, 160));

        l1=new Label("Total Rooms");
        l2=new Label("Available Rooms");
        l3=new Label("Booked Rooms");
        l4=new Label("Total Customers");
        l5=new Label("Total Bookings");

        // 🎨 Label color
        l1.setForeground(new Color(60,60,60));
        l2.setForeground(new Color(60,60,60));
        l3.setForeground(new Color(60,60,60));
        l4.setForeground(new Color(60,60,60));
        l5.setForeground(new Color(60,60,60));

        revenueTitle=new Label("Total Revenue");
        revenueTitle.setForeground(new Color(60,60,60));

        totalRooms=new Label();
        availableRooms=new Label();
        bookedRooms=new Label();
        totalCustomers=new Label();
        totalBookings=new Label();
        revenue=new Label();

        refresh=new Button("Refresh");
        back=new Button("Back");

        // 🎨 Button colors
        refresh.setBackground(new Color(0, 102, 204));
        refresh.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        title.setBounds(170,40,220,30);

        l1.setBounds(70,100,150,30);
        totalRooms.setBounds(260,100,150,30);

        l2.setBounds(70,140,150,30);
        availableRooms.setBounds(260,140,150,30);

        l3.setBounds(70,180,150,30);
        bookedRooms.setBounds(260,180,150,30);

        l4.setBounds(70,220,150,30);
        totalCustomers.setBounds(260,220,150,30);

        l5.setBounds(70,260,150,30);
        totalBookings.setBounds(260,260,150,30);

        revenueTitle.setBounds(70,300,150,30);
        revenue.setBounds(260,300,180,30);

        refresh.setBounds(120,360,100,35);
        back.setBounds(300,360,100,35);

        add(title);

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);

        add(totalRooms);
        add(availableRooms);
        add(bookedRooms);
        add(totalCustomers);
        add(totalBookings);

        add(revenueTitle);
        add(revenue);

        add(refresh);
        add(back);

        refresh.addActionListener(this);
        back.addActionListener(this);

        loadReport();

        addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){

                dispose();

            }

        });

        setVisible(true);

    }

    public void loadReport(){

        totalRooms.setText(String.valueOf(roomDAO.getTotalRooms()));

        availableRooms.setText(String.valueOf(roomDAO.getAvailableRoomsCount()));

        bookedRooms.setText(String.valueOf(roomDAO.getBookedRoomsCount()));

        totalCustomers.setText(String.valueOf(customerDAO.getTotalCustomers()));

        totalBookings.setText(String.valueOf(bookingDAO.getTotalBookings()));

        revenue.setText("Rs. "+bookingDAO.getTotalRevenue());

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==refresh){

            loadReport();

        }

        if(e.getSource()==back){

            new AdminDashboard();

            dispose();

        }

    }

}
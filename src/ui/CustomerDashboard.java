package ui;

import model.Session;

import java.awt.*;
import java.awt.event.*;

public class CustomerDashboard extends Frame implements ActionListener {

    Label title, welcome;

    Button bookRoom;
    Button viewBooking;
    Button cancelBooking;
    Button payment;
    Button paymentHistory;
    Button logout;

    public CustomerDashboard() {

        setTitle("Customer Dashboard");
        setSize(500,450);
        setLayout(null);
        setLocationRelativeTo(null);

        // 🎨 Background color added
        setBackground(new Color(235, 245, 255));

        title = new Label("CUSTOMER DASHBOARD");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 70, 160));

        welcome = new Label("Welcome : " + Session.customerName);
        welcome.setForeground(new Color(60,60,60));

        bookRoom = new Button("Book Room");
        viewBooking = new Button("View Booking");
        cancelBooking = new Button("Cancel Booking");
        payment = new Button("Payment");
        paymentHistory = new Button("Payment History");
        logout = new Button("Logout");

        title.setBounds(130,40,250,30);
        welcome.setBounds(40,80,250,30);

        bookRoom.setBounds(150,120,180,35);
        viewBooking.setBounds(150,170,180,35);
        cancelBooking.setBounds(150,220,180,35);
        payment.setBounds(150,270,180,35);
        paymentHistory.setBounds(150,320,180,35);
        logout.setBounds(150,370,180,35);

        // 🎨 Button colors
        bookRoom.setBackground(new Color(0, 102, 204));
        bookRoom.setForeground(Color.WHITE);

        viewBooking.setBackground(new Color(40, 167, 69));
        viewBooking.setForeground(Color.WHITE);

        cancelBooking.setBackground(new Color(220, 53, 69));
        cancelBooking.setForeground(Color.WHITE);

        payment.setBackground(new Color(255, 193, 7));
        payment.setForeground(Color.BLACK);

        paymentHistory.setBackground(new Color(108, 117, 125));
        paymentHistory.setForeground(Color.WHITE);

        logout.setBackground(new Color(0, 0, 0));
        logout.setForeground(Color.WHITE);

        add(title);
        add(welcome);

        add(bookRoom);
        add(viewBooking);
        add(cancelBooking);
        add(payment);
        add(paymentHistory);
        add(logout);

        bookRoom.addActionListener(this);
        viewBooking.addActionListener(this);
        cancelBooking.addActionListener(this);
        payment.addActionListener(this);
        paymentHistory.addActionListener(this);
        logout.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==bookRoom){

            new BookRoom();
            dispose();

        }

        if(e.getSource()==viewBooking){

            new ViewBooking();
            dispose();

        }

        if(e.getSource()==cancelBooking){

            new CancelBooking();
            dispose();

        }

        if(e.getSource()==payment){

            new Payment();
            dispose();

        }

        if(e.getSource()==paymentHistory){

            new PaymentHistory();
            dispose();

        }

        if(e.getSource()==logout){

            Session.customerId=0;
            Session.customerName=null;

            new Home();

            dispose();

        }

    }

}
package ui;

import dao.BookingDAO;
import model.Session;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class ViewBooking extends Frame implements ActionListener{

    TextArea area;

    Button refresh;
    Button back;

    BookingDAO dao=new BookingDAO();

    public ViewBooking(){

        setTitle("View Bookings");

        setSize(700,450);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        area=new TextArea();

        // 🎨 TextArea styling
        area.setBackground(Color.WHITE);
        area.setForeground(Color.BLACK);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        refresh=new Button("Refresh");
        back=new Button("Back");

        // 🎨 Button colors
        refresh.setBackground(new Color(0, 102, 204));
        refresh.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        area.setBounds(20,50,650,300);

        refresh.setBounds(180,370,100,35);

        back.setBounds(350,370,100,35);

        add(area);

        add(refresh);

        add(back);

        refresh.addActionListener(this);

        back.addActionListener(this);

        loadBookings();

        addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){

                dispose();

            }

        });

        setVisible(true);

    }

    public void loadBookings(){

        area.setText("");

        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        area.append(String.format("%-12s %-8s %-10s %-12s %-12s %-10s\n",
                "BookingID","RoomNo","Type","CheckIn","CheckOut","Amount"));

        area.append("---------------------------------------------------------------------\n");

        try{

            ResultSet rs = dao.viewBookings(Session.customerId);

            while(rs.next()){

                area.append(String.format("%-12d %-8d %-10s %-12s %-12s %-10.2f\n",
                        rs.getInt("booking_id"),
                        rs.getInt("room_no"),
                        rs.getString("room_type"),
                        rs.getString("checkin"),
                        rs.getString("checkout"),
                        rs.getDouble("total_amount")
                ));

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==refresh){

            loadBookings();

        }

        if(e.getSource()==back){

            new CustomerDashboard();

            dispose();

        }

    }

}
package ui;

import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends Frame implements ActionListener {

    Label title;

    Button addRoom;
    Button updateRoom;
    Button deleteRoom;
    Button viewRooms;
    Button logout;
    Button reports;

    public AdminDashboard() {

        setTitle("Admin Dashboard");

        setSize(500, 450);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color added
        setBackground(new Color(235, 245, 255));

        title = new Label("ADMIN DASHBOARD");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 70, 160)); // dark blue

        addRoom = new Button("Add Room");
        updateRoom = new Button("Update Room");
        deleteRoom = new Button("Delete Room");
        viewRooms = new Button("View Rooms");
        logout = new Button("Logout");
        reports = new Button("Reports");

        title.setBounds(150, 40, 220, 30);

        addRoom.setBounds(150, 100, 180, 35);
        updateRoom.setBounds(150, 150, 180, 35);
        deleteRoom.setBounds(150, 200, 180, 35);
        viewRooms.setBounds(150, 250, 180, 35);
        reports.setBounds(150,300,180,35);
        logout.setBounds(150,360,180,35);

        add(title);

        add(addRoom);
        add(updateRoom);
        add(deleteRoom);
        add(viewRooms);
        add(logout);
        add(reports);

        // 🎨 Button colors added
        addRoom.setBackground(new Color(0, 102, 204));
        addRoom.setForeground(Color.WHITE);

        updateRoom.setBackground(new Color(255, 153, 0));
        updateRoom.setForeground(Color.WHITE);

        deleteRoom.setBackground(new Color(220, 53, 69));
        deleteRoom.setForeground(Color.WHITE);

        viewRooms.setBackground(new Color(40, 167, 69));
        viewRooms.setForeground(Color.WHITE);

        reports.setBackground(new Color(108, 117, 125));
        reports.setForeground(Color.WHITE);

        logout.setBackground(new Color(0, 0, 0));
        logout.setForeground(Color.WHITE);

        addRoom.addActionListener(this);
        updateRoom.addActionListener(this);
        deleteRoom.addActionListener(this);
        viewRooms.addActionListener(this);
        logout.addActionListener(this);
        reports.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addRoom) {

            new AddRoom();
            dispose();

        }

        else if (e.getSource() == updateRoom) {

            new UpdateRoom();
            dispose();

        }

        else if (e.getSource() == deleteRoom) {

            new DeleteRoom();
            dispose();

        }

        else if (e.getSource() == viewRooms) {

            new ViewRoom();
            dispose();

        }
        else if(e.getSource()==reports){

            new Reports();

            dispose();

        }

        else if (e.getSource() == logout) {

            new Home();
            dispose();

        }

    }

}
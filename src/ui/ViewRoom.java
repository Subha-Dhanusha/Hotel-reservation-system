package ui;

import dao.RoomDAO;
import model.Room;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewRoom extends Frame implements ActionListener {

    Label title;

    TextArea area;

    Button refresh;
    Button back;

    RoomDAO dao = new RoomDAO();

    public ViewRoom() {

        setTitle("View Rooms");

        setSize(700,500);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        title = new Label("ROOM DETAILS");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0, 70, 160));

        area = new TextArea();

        // 🎨 TextArea styling
        area.setBackground(Color.WHITE);
        area.setForeground(Color.BLACK);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        refresh = new Button("Refresh");
        back = new Button("Back");

        // 🎨 Button colors
        refresh.setBackground(new Color(0, 102, 204));
        refresh.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        title.setBounds(260,40,180,30);

        area.setBounds(30,80,630,300);

        refresh.setBounds(180,410,100,35);

        back.setBounds(380,410,100,35);

        add(title);

        add(area);

        add(refresh);

        add(back);

        refresh.addActionListener(this);

        back.addActionListener(this);

        loadRooms();

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                dispose();

            }

        });

        setVisible(true);

    }

    public void loadRooms() {

        area.setText("");

        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        area.append(String.format("%-10s %-10s %-15s %-12s %-12s\n",
                "Room ID", "Room No", "Type", "Price", "Status"));

        area.append("---------------------------------------------------------------\n");

        ArrayList<Room> list = dao.getAllRooms();

        for(Room room : list){

            area.append(String.format("%-10d %-10d %-15s %-12.2f %-12s\n",
                    room.getRoomId(),
                    room.getRoomNo(),
                    room.getRoomType(),
                    room.getPrice(),
                    room.getStatus()
            ));

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==refresh){

            loadRooms();

        }

        if(e.getSource()==back){

            new AdminDashboard();

            dispose();

        }

    }

}
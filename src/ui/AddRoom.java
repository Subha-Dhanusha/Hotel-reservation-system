package ui;

import dao.RoomDAO;
import model.Room;

import java.awt.*;
import java.awt.event.*;

public class AddRoom extends Frame implements ActionListener {

    Label title;
    Label l1,l2,l3,l4;

    TextField roomNo;
    TextField price;

    Choice roomType;
    Choice status;

    Button save;
    Button reset;
    Button back;

    RoomDAO dao=new RoomDAO();

    public AddRoom(){

        setTitle("Add Room");

        setSize(500,400);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        title=new Label("ADD ROOM");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0, 70, 160));

        l1=new Label("Room No");
        l2=new Label("Room Type");
        l3=new Label("Price");
        l4=new Label("Status");

        // 🎨 Label color
        l1.setForeground(new Color(60,60,60));
        l2.setForeground(new Color(60,60,60));
        l3.setForeground(new Color(60,60,60));
        l4.setForeground(new Color(60,60,60));

        roomNo=new TextField();
        price=new TextField();

        // 🎨 TextField style
        roomNo.setBackground(Color.WHITE);
        price.setBackground(Color.WHITE);

        roomType=new Choice();
        roomType.add("Single");
        roomType.add("Double");
        roomType.add("Deluxe");
        roomType.add("Suite");

        status=new Choice();
        status.add("Available");
        status.add("Booked");

        // 🎨 Choice style
        roomType.setBackground(Color.WHITE);
        status.setBackground(Color.WHITE);

        save=new Button("Save");
        reset=new Button("Reset");
        back=new Button("Back");

        // 🎨 Button colors
        save.setBackground(new Color(0, 153, 76));
        save.setForeground(Color.WHITE);

        reset.setBackground(new Color(255, 153, 0));
        reset.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        title.setBounds(180,40,150,30);

        l1.setBounds(60,90,100,30);
        l2.setBounds(60,140,100,30);
        l3.setBounds(60,190,100,30);
        l4.setBounds(60,240,100,30);

        roomNo.setBounds(180,90,180,30);
        roomType.setBounds(180,140,180,30);
        price.setBounds(180,190,180,30);
        status.setBounds(180,240,180,30);

        save.setBounds(50,310,80,35);
        reset.setBounds(180,310,80,35);
        back.setBounds(310,310,80,35);

        add(title);

        add(l1);
        add(l2);
        add(l3);
        add(l4);

        add(roomNo);
        add(roomType);
        add(price);
        add(status);

        add(save);
        add(reset);
        add(back);

        save.addActionListener(this);
        reset.addActionListener(this);
        back.addActionListener(this);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e){

                dispose();

            }

        });

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==save){

            if(roomNo.getText().isEmpty()){

                showMessage("Enter Room Number");
                return;

            }

            if(price.getText().isEmpty()){

                showMessage("Enter Price");
                return;

            }

            try{

                int no=Integer.parseInt(roomNo.getText());

                if(dao.roomExists(no)){

                    showMessage("Room Already Exists");
                    return;

                }

                Room room=new Room();

                room.setRoomNo(no);
                room.setRoomType(roomType.getSelectedItem());
                room.setPrice(Double.parseDouble(price.getText()));
                room.setStatus(status.getSelectedItem());

                if(dao.addRoom(room)){

                    showMessage("Room Added Successfully");

                    roomNo.setText("");
                    price.setText("");

                }
                else{

                    showMessage("Failed to Add Room");

                }

            }
            catch(NumberFormatException ex){

                showMessage("Room Number and Price must be numeric");

            }

        }

        if(e.getSource()==reset){

            roomNo.setText("");
            price.setText("");

        }

        if(e.getSource()==back){

            new AdminDashboard();

            dispose();

        }

    }

    public void showMessage(String msg){

        Dialog d=new Dialog(this,"Message",true);

        d.setLayout(new FlowLayout());

        Label l=new Label(msg);

        Button ok=new Button("OK");

        ok.addActionListener(a->d.dispose());

        d.add(l);
        d.add(ok);

        d.setSize(250,120);

        d.setLocationRelativeTo(this);

        d.setVisible(true);

    }

}
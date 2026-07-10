package ui;

import dao.RoomDAO;

import java.awt.*;
import java.awt.event.*;

public class DeleteRoom extends Frame implements ActionListener {

    Label title;
    Label l1;

    TextField roomNo;

    Button delete;
    Button reset;
    Button back;

    RoomDAO dao = new RoomDAO();

    public DeleteRoom() {

        setTitle("Delete Room");

        setSize(450,300);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        title = new Label("DELETE ROOM");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0, 70, 160));

        l1 = new Label("Room Number");

        // 🎨 Label color
        l1.setForeground(new Color(60,60,60));

        roomNo = new TextField();

        // 🎨 TextField style
        roomNo.setBackground(Color.WHITE);

        delete = new Button("Delete");
        reset = new Button("Reset");
        back = new Button("Back");

        // 🎨 Button colors
        delete.setBackground(new Color(220, 53, 69));
        delete.setForeground(Color.WHITE);

        reset.setBackground(new Color(255, 193, 7));
        reset.setForeground(Color.BLACK);

        back.setBackground(new Color(108, 117, 125));
        back.setForeground(Color.WHITE);

        title.setBounds(140,40,180,30);

        l1.setBounds(50,100,120,30);

        roomNo.setBounds(180,100,180,30);

        delete.setBounds(40,190,80,35);
        reset.setBounds(170,190,80,35);
        back.setBounds(300,190,80,35);

        add(title);

        add(l1);

        add(roomNo);

        add(delete);
        add(reset);
        add(back);

        delete.addActionListener(this);
        reset.addActionListener(this);
        back.addActionListener(this);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                dispose();

            }

        });

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==delete){

            if(roomNo.getText().isEmpty()){

                showMessage("Enter Room Number");

                return;

            }

            try{

                int no=Integer.parseInt(roomNo.getText());

                if(!dao.roomExists(no)){

                    showMessage("Room Not Found");

                    return;

                }

                if(dao.deleteRoom(no)){

                    showMessage("Room Deleted Successfully");

                    roomNo.setText("");

                }

                else{

                    showMessage("Delete Failed");

                }

            }

            catch(NumberFormatException ex){

                showMessage("Enter Valid Room Number");

            }

        }

        if(e.getSource()==reset){

            roomNo.setText("");

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
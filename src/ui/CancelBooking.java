package ui;

import dao.BookingDAO;

import java.awt.*;
import java.awt.event.*;

public class CancelBooking extends Frame implements ActionListener {

    Label l1;

    TextField bookingId;

    Button cancel;
    Button clear;
    Button back;

    BookingDAO dao = new BookingDAO();

    public CancelBooking(){

        setTitle("Cancel Booking");

        setSize(450,300);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        l1 = new Label("Booking ID");

        // 🎨 Label color
        l1.setForeground(new Color(60,60,60));

        bookingId = new TextField();

        // 🎨 TextField style
        bookingId.setBackground(Color.WHITE);

        cancel = new Button("Cancel Booking");
        clear = new Button("Clear");
        back = new Button("Back");

        // 🎨 Button colors
        cancel.setBackground(new Color(220, 53, 69));
        cancel.setForeground(Color.WHITE);

        clear.setBackground(new Color(255, 193, 7));
        clear.setForeground(Color.BLACK);

        back.setBackground(new Color(108, 117, 125));
        back.setForeground(Color.WHITE);

        l1.setBounds(60,70,100,30);

        bookingId.setBounds(180,70,180,30);

        cancel.setBounds(40,170,110,35);

        clear.setBounds(170,170,80,35);

        back.setBounds(280,170,80,35);

        add(l1);

        add(bookingId);

        add(cancel);

        add(clear);

        add(back);

        cancel.addActionListener(this);

        clear.addActionListener(this);

        back.addActionListener(this);

        addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){

                dispose();

            }

        });

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==cancel){

            if(bookingId.getText().isEmpty()){

                showMessage("Enter Booking ID");

                return;

            }

            int id = Integer.parseInt(bookingId.getText());

            if(dao.cancelBooking(id)){

                showMessage("Booking Cancelled Successfully");

                bookingId.setText("");

            }
            else{

                showMessage("Invalid Booking ID");

            }

        }

        if(e.getSource()==clear){

            bookingId.setText("");

        }

        if(e.getSource()==back){

            new CustomerDashboard();

            dispose();

        }

    }

    public void showMessage(String msg){

        Dialog d = new Dialog(this,"Message",true);

        d.setLayout(new FlowLayout());

        Label l = new Label(msg);

        Button ok = new Button("OK");

        ok.addActionListener(a->d.dispose());

        d.add(l);

        d.add(ok);

        d.setSize(250,120);

        d.setLocationRelativeTo(this);

        d.setVisible(true);

    }

}
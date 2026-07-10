package ui;

import dao.BookingDAO;
import model.Booking;
import model.Session;
import dao.RoomDAO;
import model.Room;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BookRoom extends Frame implements ActionListener {

    Label title;
    Label l1,l2,l3,l4,l5;

    Choice roomChoice;

    TextField checkIn;
    TextField checkOut;
    TextField total;

    Button calculate;
    Button book;
    Button reset;
    Button back;

    BookingDAO dao=new BookingDAO();

    public BookRoom(){

        setTitle("Book Room");

        setSize(500,450);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        title=new Label("BOOK ROOM");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0, 70, 160));

        l1=new Label("Room Number");
        l2=new Label("Check In");
        l3=new Label("Check Out");
        l4=new Label("Total Amount");
        l5=new Label("Format : YYYY-MM-DD");

        // 🎨 Label color
        l1.setForeground(new Color(60,60,60));
        l2.setForeground(new Color(60,60,60));
        l3.setForeground(new Color(60,60,60));
        l4.setForeground(new Color(60,60,60));
        l5.setForeground(new Color(100,100,100));

        roomChoice=new Choice();

        ArrayList<String> rooms=dao.getAvailableRooms();

        for(String room:rooms){
            roomChoice.add(room);
        }

        // 🎨 Choice style
        roomChoice.setBackground(Color.WHITE);

        checkIn=new TextField();
        checkOut=new TextField();
        total=new TextField();

        // 🎨 TextField style
        checkIn.setBackground(Color.WHITE);
        checkOut.setBackground(Color.WHITE);
        total.setBackground(Color.WHITE);

        total.setEditable(false);

        calculate=new Button("Calculate");
        book=new Button("Book");
        reset=new Button("Reset");
        back=new Button("Back");

        // 🎨 Button colors
        calculate.setBackground(new Color(0, 102, 204));
        calculate.setForeground(Color.WHITE);

        book.setBackground(new Color(0, 153, 76));
        book.setForeground(Color.WHITE);

        reset.setBackground(new Color(255, 153, 0));
        reset.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        title.setBounds(170,40,180,30);

        l1.setBounds(60,90,120,30);
        l2.setBounds(60,140,120,30);
        l3.setBounds(60,190,120,30);
        l4.setBounds(60,240,120,30);
        l5.setBounds(170,275,180,20);

        roomChoice.setBounds(200,90,180,30);
        checkIn.setBounds(200,140,180,30);
        checkOut.setBounds(200,190,180,30);
        total.setBounds(200,240,180,30);

        calculate.setBounds(40,330,90,35);
        book.setBounds(150,330,90,35);
        reset.setBounds(260,330,90,35);
        back.setBounds(370,330,90,35);

        add(title);

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);

        add(roomChoice);

        add(checkIn);
        add(checkOut);
        add(total);

        add(calculate);
        add(book);
        add(reset);
        add(back);

        calculate.addActionListener(this);
        book.addActionListener(this);
        reset.addActionListener(this);
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

        if(e.getSource()==calculate){

            if(checkIn.getText().isEmpty() || checkOut.getText().isEmpty()){

                showMessage("Enter Check In and Check Out Date");
                return;

            }

            try{

                LocalDate in=LocalDate.parse(checkIn.getText());
                LocalDate out=LocalDate.parse(checkOut.getText());

                long days=ChronoUnit.DAYS.between(in,out);

                if(days<=0){

                    showMessage("Invalid Dates");
                    return;

                }

                int roomNo=Integer.parseInt(roomChoice.getSelectedItem());

                double price=dao.getRoomPrice(roomNo);

                total.setText(String.valueOf(days*price));

            }

            catch(Exception ex){

                showMessage("Date Format : YYYY-MM-DD");

            }

        }

        if(e.getSource()==book){

            if(checkIn.getText().isEmpty() ||
                    checkOut.getText().isEmpty() ||
                    total.getText().isEmpty()){

                showMessage("Calculate Amount First");
                return;

            }

            Booking booking=new Booking();

            int roomNo=Integer.parseInt(roomChoice.getSelectedItem());

            booking.setCustomerId(Session.customerId);
            booking.setRoomId(dao.getRoomId(roomNo));
            booking.setCheckIn(checkIn.getText());
            booking.setCheckOut(checkOut.getText());
            booking.setTotalAmount(Double.parseDouble(total.getText()));

            if(dao.bookRoom(booking)){

                showMessage("Room Booked Successfully");

                new CustomerDashboard();
                dispose();

            }
            else{

                showMessage("Booking Failed");

            }

        }

        if(e.getSource()==reset){

            checkIn.setText("");
            checkOut.setText("");
            total.setText("");

        }

        if(e.getSource()==back){

            new CustomerDashboard();
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
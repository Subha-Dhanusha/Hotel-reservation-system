package ui;

import dao.PaymentDAO;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class Payment extends Frame implements ActionListener {

    Label title,l1,l2,l3,l4;

    TextField bookingId;
    TextField amount;

    Choice paymentMode;

    Button getAmount;
    Button pay;
    Button reset;
    Button back;

    PaymentDAO dao=new PaymentDAO();

    public Payment(){

        setTitle("Payment");

        setSize(500,400);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        title=new Label("PAYMENT");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0, 70, 160));

        l1=new Label("Booking ID");
        l2=new Label("Amount");
        l3=new Label("Payment Mode");
        l4=new Label("Date : "+LocalDate.now());

        // 🎨 Label color
        l1.setForeground(new Color(60,60,60));
        l2.setForeground(new Color(60,60,60));
        l3.setForeground(new Color(60,60,60));
        l4.setForeground(new Color(100,100,100));

        bookingId=new TextField();

        amount=new TextField();
        amount.setEditable(false);

        // 🎨 TextField style
        bookingId.setBackground(Color.WHITE);
        amount.setBackground(Color.WHITE);

        paymentMode=new Choice();

        paymentMode.add("Cash");
        paymentMode.add("UPI");
        paymentMode.add("Credit Card");
        paymentMode.add("Debit Card");

        // 🎨 Choice style
        paymentMode.setBackground(Color.WHITE);

        getAmount=new Button("Get Amount");
        pay=new Button("Pay");
        reset=new Button("Reset");
        back=new Button("Back");

        // 🎨 Button colors
        getAmount.setBackground(new Color(0, 102, 204));
        getAmount.setForeground(Color.WHITE);

        pay.setBackground(new Color(0, 153, 76));
        pay.setForeground(Color.WHITE);

        reset.setBackground(new Color(255, 153, 0));
        reset.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        title.setBounds(180,40,150,30);

        l1.setBounds(60,90,100,30);
        l2.setBounds(60,140,100,30);
        l3.setBounds(60,190,100,30);
        l4.setBounds(170,240,200,30);

        bookingId.setBounds(180,90,180,30);
        amount.setBounds(180,140,180,30);
        paymentMode.setBounds(180,190,180,30);

        getAmount.setBounds(30,300,100,35);
        pay.setBounds(150,300,80,35);
        reset.setBounds(250,300,80,35);
        back.setBounds(350,300,80,35);

        add(title);
        add(l1);
        add(l2);
        add(l3);
        add(l4);

        add(bookingId);
        add(amount);
        add(paymentMode);

        add(getAmount);
        add(pay);
        add(reset);
        add(back);

        getAmount.addActionListener(this);
        pay.addActionListener(this);
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

        if(e.getSource()==getAmount){

            if(bookingId.getText().isEmpty()){

                showMessage("Enter Booking ID");
                return;

            }

            double amt=dao.getBookingAmount(Integer.parseInt(bookingId.getText()));

            if(amt==0){

                showMessage("Invalid Booking ID");

            }
            else{

                amount.setText(String.valueOf(amt));

            }

        }

        if(e.getSource()==pay){

            if(bookingId.getText().isEmpty() || amount.getText().isEmpty()){

                showMessage("Get Amount First");
                return;

            }

            model.Payment payment=new model.Payment();

            payment.setBookingId(Integer.parseInt(bookingId.getText()));

            payment.setAmount(Double.parseDouble(amount.getText()));

            payment.setPaymentDate(LocalDate.now().toString());

            payment.setPaymentMode(paymentMode.getSelectedItem());

            if(dao.makePayment(payment)){

                showMessage("Payment Successful");

                bookingId.setText("");
                amount.setText("");

            }
            else{

                showMessage("Payment Failed");

            }

        }

        if(e.getSource()==reset){

            bookingId.setText("");
            amount.setText("");

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
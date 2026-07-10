package ui;

import dao.PaymentDAO;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class PaymentHistory extends Frame implements ActionListener{

    TextArea area;

    Button refresh;
    Button back;

    PaymentDAO dao = new PaymentDAO();

    public PaymentHistory(){

        setTitle("Payment History");

        setSize(700,450);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        area = new TextArea();

        // 🎨 TextArea style
        area.setBackground(Color.WHITE);
        area.setForeground(Color.BLACK);
        area.setFont(new Font("Consolas", Font.PLAIN, 12));

        refresh = new Button("Refresh");
        back = new Button("Back");

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

        loadPayments();

        addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){

                dispose();

            }

        });

        setVisible(true);

    }

    public void loadPayments(){

        area.setText("");

        area.append("PaymentID\tBookingID\tAmount\tDate\t\tMode\n\n");

        try{

            ResultSet rs = dao.getPaymentHistory();

            while(rs.next()){

                area.append(
                        rs.getInt("payment_id")+"\t"+
                                rs.getInt("booking_id")+"\t"+
                                rs.getDouble("amount")+"\t"+
                                rs.getString("payment_date")+"\t"+
                                rs.getString("payment_mode")+"\n"
                );

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==refresh){

            loadPayments();

        }

        if(e.getSource()==back){

            new CustomerDashboard();

            dispose();

        }

    }

}
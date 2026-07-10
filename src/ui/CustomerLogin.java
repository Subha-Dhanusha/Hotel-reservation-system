package ui;

import dao.CustomerDAO;
import model.Customer;
import model.Session;

import java.awt.*;
import java.awt.event.*;

public class CustomerLogin extends Frame implements ActionListener {

    Label l1, l2;
    TextField t1, t2;
    Button login, reset, back;

    CustomerDAO dao = new CustomerDAO();

    public CustomerLogin() {

        setTitle("Customer Login");
        setSize(450,300);
        setLayout(null);
        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        l1 = new Label("Email");
        l2 = new Label("Password");

        l1.setForeground(new Color(60,60,60));
        l2.setForeground(new Color(60,60,60));

        t1 = new TextField();
        t2 = new TextField();
        t2.setEchoChar('*');

        t1.setBackground(Color.WHITE);
        t2.setBackground(Color.WHITE);

        login = new Button("Login");
        reset = new Button("Reset");
        back = new Button("Back");

        // 🎨 Button colors
        login.setBackground(new Color(0, 102, 204));
        login.setForeground(Color.WHITE);

        reset.setBackground(new Color(255, 153, 0));
        reset.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        l1.setBounds(60,70,100,30);
        l2.setBounds(60,120,100,30);

        t1.setBounds(170,70,180,30);
        t2.setBounds(170,120,180,30);

        login.setBounds(60,190,80,35);
        reset.setBounds(170,190,80,35);
        back.setBounds(280,190,80,35);

        add(l1);
        add(l2);

        add(t1);
        add(t2);

        add(login);
        add(reset);
        add(back);

        login.addActionListener(this);
        reset.addActionListener(this);
        back.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==login){

            String email=t1.getText();
            String password=t2.getText();

            if(email.isEmpty()){

                showMessage("Enter Email");
                return;

            }

            if(password.isEmpty()){

                showMessage("Enter Password");
                return;

            }

            Customer customer=dao.loginCustomer(email,password);

            if(customer!=null){

                Session.customerId=customer.getCustomerId();
                Session.customerName=customer.getName();

                showMessage("Login Successful");

                new CustomerDashboard();

                dispose();

            }

            else{

                showMessage("Invalid Email or Password");

            }

        }

        if(e.getSource()==reset){

            t1.setText("");
            t2.setText("");

        }

        if(e.getSource()==back){

            new Home();

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
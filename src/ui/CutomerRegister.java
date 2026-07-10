package ui;

import java.awt.*;
import java.awt.event.*;

import dao.CustomerDAO;
import model.Customer;

public class CutomerRegister extends Frame implements ActionListener {

    Label l1,l2,l3,l4,l5;

    TextField t1,t2,t3,t4,t5;

    Button register,reset,back;

    CustomerDAO dao=new CustomerDAO();

    public CutomerRegister() {

        setTitle("Customer Registration");

        setSize(500,450);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color
        setBackground(new Color(235, 245, 255));

        l1=new Label("Name");
        l2=new Label("Phone");
        l3=new Label("Email");
        l4=new Label("Password");
        l5=new Label("Confirm Password");

        // 🎨 Label color
        l1.setForeground(new Color(60,60,60));
        l2.setForeground(new Color(60,60,60));
        l3.setForeground(new Color(60,60,60));
        l4.setForeground(new Color(60,60,60));
        l5.setForeground(new Color(60,60,60));

        t1=new TextField();
        t2=new TextField();
        t3=new TextField();

        t4=new TextField();
        t4.setEchoChar('*');

        t5=new TextField();
        t5.setEchoChar('*');

        // 🎨 TextField background
        t1.setBackground(Color.WHITE);
        t2.setBackground(Color.WHITE);
        t3.setBackground(Color.WHITE);
        t4.setBackground(Color.WHITE);
        t5.setBackground(Color.WHITE);

        register=new Button("Register");
        reset=new Button("Reset");
        back=new Button("Back");

        // 🎨 Button colors
        register.setBackground(new Color(0, 153, 76));
        register.setForeground(Color.WHITE);

        reset.setBackground(new Color(255, 153, 0));
        reset.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        l1.setBounds(50,70,120,30);
        l2.setBounds(50,120,120,30);
        l3.setBounds(50,170,120,30);
        l4.setBounds(50,220,120,30);
        l5.setBounds(50,270,120,30);

        t1.setBounds(200,70,180,30);
        t2.setBounds(200,120,180,30);
        t3.setBounds(200,170,180,30);
        t4.setBounds(200,220,180,30);
        t5.setBounds(200,270,180,30);

        register.setBounds(70,340,90,35);
        reset.setBounds(190,340,90,35);
        back.setBounds(310,340,90,35);

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);

        add(t1);
        add(t2);
        add(t3);
        add(t4);
        add(t5);

        add(register);
        add(reset);
        add(back);

        register.addActionListener(this);
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

        if(e.getSource()==register){

            String name=t1.getText();
            String phone=t2.getText();
            String email=t3.getText();
            String pass=t4.getText();
            String confirm=t5.getText();

            if(name.isEmpty()){

                showMessage("Name cannot be empty");
                return;

            }

            if(phone.length()!=10){

                showMessage("Phone must contain 10 digits");
                return;

            }

            if(!phone.matches("[0-9]+")){

                showMessage("Phone must contain only digits");
                return;

            }

            if(!email.contains("@") || !email.contains(".")){

                showMessage("Invalid Email");
                return;

            }

            if(pass.length()<6){

                showMessage("Password should be minimum 6 characters");
                return;

            }

            if(!pass.equals(confirm)){

                showMessage("Passwords do not match");
                return;

            }

            if(dao.phoneExists(phone)){

                showMessage("Phone already exists");
                return;

            }

            if(dao.emailExists(email)){

                showMessage("Email already exists");
                return;

            }

            Customer customer=new Customer();

            customer.setName(name);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setPassword(pass);

            if(dao.registerCustomer(customer)){

                showMessage("Registration Successful");

                clearFields();

            }
            else{

                showMessage("Registration Failed");

            }

        }

        if(e.getSource()==reset){

            clearFields();

        }

        if(e.getSource()==back){

            new Home();

            dispose();

        }

    }

    public void clearFields(){

        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");

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
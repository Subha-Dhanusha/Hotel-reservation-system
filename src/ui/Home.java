package ui;

import java.awt.*;
import java.awt.event.*;

public class Home extends Frame implements ActionListener {

    Label title;

    Button adminBtn;
    Button customerBtn;
    Button registerBtn;
    Button exitBtn;

    public Home() {

        setTitle("Hotel Reservation System");

        setSize(500,400);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🔵 Background color added
        setBackground(new Color(230, 240, 255));

        title = new Label("HOTEL RESERVATION SYSTEM");

        title.setBounds(120,60,250,30);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 102, 204)); // blue

        adminBtn = new Button("Admin Login");
        customerBtn = new Button("Customer Login");
        registerBtn = new Button("Register");
        exitBtn = new Button("Exit");

        adminBtn.setBounds(150,120,180,30);
        customerBtn.setBounds(150,170,180,30);
        registerBtn.setBounds(150,220,180,30);
        exitBtn.setBounds(150,270,180,30);

        // 🎨 Button colors added
        adminBtn.setBackground(new Color(0, 102, 204));
        adminBtn.setForeground(Color.WHITE);

        customerBtn.setBackground(new Color(0, 153, 76));
        customerBtn.setForeground(Color.WHITE);

        registerBtn.setBackground(new Color(255, 153, 0));
        registerBtn.setForeground(Color.WHITE);

        exitBtn.setBackground(new Color(220, 53, 69));
        exitBtn.setForeground(Color.WHITE);

        add(title);
        add(adminBtn);
        add(customerBtn);
        add(registerBtn);
        add(exitBtn);

        adminBtn.addActionListener(this);
        customerBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==exitBtn){
            System.exit(0);
        }

        if(e.getSource()==adminBtn){
            new AdminLogin();
            dispose();
        }

        if(e.getSource()==customerBtn){
            new CustomerLogin();
            dispose();
        }

        if(e.getSource()==registerBtn){
            new CutomerRegister();
            dispose();
        }

    }

}
package ui;

import dao.AdminDAO;
import model.Admin;

import java.awt.*;
import java.awt.event.*;

public class AdminLogin extends Frame implements ActionListener {

    Label title,l1,l2;

    TextField username,password;

    Button login,reset,back;

    AdminDAO dao=new AdminDAO();

    public AdminLogin(){

        setTitle("Admin Login");

        setSize(450,320);

        setLayout(null);

        setLocationRelativeTo(null);

        // 🎨 Background color added
        setBackground(new Color(235, 245, 255));

        title=new Label("ADMIN LOGIN");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0, 70, 160));

        l1=new Label("Username");
        l2=new Label("Password");

        l1.setForeground(new Color(60,60,60));
        l2.setForeground(new Color(60,60,60));

        username=new TextField();
        password=new TextField();
        password.setEchoChar('*');

        username.setBackground(Color.WHITE);
        password.setBackground(Color.WHITE);

        login=new Button("Login");
        reset=new Button("Reset");
        back=new Button("Back");

        login.setBackground(new Color(0, 102, 204));
        login.setForeground(Color.WHITE);

        reset.setBackground(new Color(255, 153, 0));
        reset.setForeground(Color.WHITE);

        back.setBackground(new Color(220, 53, 69));
        back.setForeground(Color.WHITE);

        title.setBounds(150,40,180,30);

        l1.setBounds(60,100,100,30);
        l2.setBounds(60,150,100,30);

        username.setBounds(180,100,180,30);
        password.setBounds(180,150,180,30);

        login.setBounds(50,230,80,35);
        reset.setBounds(170,230,80,35);
        back.setBounds(290,230,80,35);

        add(title);

        add(l1);
        add(l2);

        add(username);
        add(password);

        add(login);
        add(reset);
        add(back);

        login.addActionListener(this);
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

        if(e.getSource()==login){

            String user=username.getText();

            String pass=password.getText();

            if(user.isEmpty()){

                showMessage("Enter Username");

                return;

            }

            if(pass.isEmpty()){

                showMessage("Enter Password");

                return;

            }

            Admin admin=dao.login(user,pass);

            if(admin!=null){

                showMessage("Login Successful");

                new AdminDashboard();

                dispose();

            }

            else{

                showMessage("Invalid Username or Password");

            }

        }

        if(e.getSource()==reset){

            username.setText("");

            password.setText("");

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
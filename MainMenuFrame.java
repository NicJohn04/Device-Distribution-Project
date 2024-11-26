import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;



public class MainMenuFrame extends JFrame{

    public MainMenuFrame(){
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Choose your login option");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        JButton adminloginButton = new JButton("Admin Login");
        panel.add(adminloginButton);

        adminloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                adminlog();
            }
        });

        JButton teacherloginButton = new JButton("Teacher Login");
        panel.add(teacherloginButton);
        teacherloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherlog();
            }
        });

        add(panel, BorderLayout.CENTER);
    }



    private void adminlog(){
        
        JFrame adminFrame = new JFrame("Admin Login");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Enter Password:");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(300, 30));
        panel.add(password);

        JButton adminloginButton = new JButton("Login");
        adminloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                adminloginlistener(adminFrame, password);
            }
        });
        panel.add(adminloginButton);

        adminFrame.add(panel, BorderLayout.CENTER);  
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null); 
        adminFrame.setVisible(true);
    
    }

    private void adminloginlistener(JFrame adminFrame, JPasswordField passwordField){
        String adminpass = new String(passwordField.getPassword());

        if (adminpass.equals("Terry48admin")){
            adminFrame.dispose();

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JFrame mainFrame = new JFrame("Admin DashBoard");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setContentPane(mainPanel);
            mainFrame.setVisible(true);

            JButton UsermanagementButton = new JButton("User Management");
            JButton ReportGenButton = new JButton("Report Creation");
            JButton InventoryButton = new JButton("Inventory Management");

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(UsermanagementButton);
            buttonPanel.add(ReportGenButton);
            buttonPanel.add(InventoryButton);

            mainFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
            mainFrame.setSize(800,350);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);

        } else{
            JOptionPane.showMessageDialog(adminFrame,"Ivalid password. please  try again", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

    }



    private void teacherlog(){
        
        JFrame teacherFrame = new JFrame("Teacher Login");
        teacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Enter Password:");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);
        

        JPasswordField password_teach = new JPasswordField();
        password_teach.setPreferredSize(new Dimension(300, 30));
        panel.add(password_teach);
        

        JButton teacherloginButton = new JButton("Login");
        teacherloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                teacherloginlistener(teacherFrame, password_teach);
            }
        });
        panel.add(teacherloginButton);

        teacherFrame.add(panel, BorderLayout.CENTER);  
        teacherFrame.pack();
        teacherFrame.setLocationRelativeTo(null); 
        teacherFrame.setVisible(true);
    
    }


    private void teacherloginlistener(JFrame teacherFrame, JPasswordField passwordField){
        String teachpass = new String(passwordField.getPassword());

        if (teachpass.equals("Terry")){
            teacherFrame.dispose();

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JFrame mainFrame_teach = new JFrame("teacher DashBoard");
            mainFrame_teach.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame_teach.setContentPane(mainPanel);
            mainFrame_teach.setVisible(true);

            JButton BookEquiButton = new JButton("Book Equipment");
            //BookEquiButton.addActionListener(new ActionListener(){
                //public void actionPerformed(ActionEvent e){
                   // new Booking();

                //}
            //});
            JButton NotifyButton = new JButton("Notification");

            JPanel buttonPanel_t = new JPanel();
            buttonPanel_t.add(BookEquiButton);
            buttonPanel_t.add(NotifyButton);
            
            mainFrame_teach.getContentPane().add(buttonPanel_t, BorderLayout.NORTH);
            mainFrame_teach.setSize(800,350);
            mainFrame_teach.setLocationRelativeTo(null);
            mainFrame_teach.setVisible(true);

        } else{
            JOptionPane.showMessageDialog(teacherFrame,"Ivalid password. please  try again", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

    }



    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenuFrame frame = new MainMenuFrame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.pack();
            }
        });
    }


}
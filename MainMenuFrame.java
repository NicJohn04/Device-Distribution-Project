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
        
        JFrame adminFrame = new JFrame("Teacher Login");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Enter Password:");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(300, 30));
        panel.add(password);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        adminFrame.add(panel, BorderLayout.CENTER);  
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null); 
        adminFrame.setVisible(true);
    
    }

    private void teacherlog(){
        
        JFrame teacherFrame = new JFrame("Teacher Login");
        teacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Enter Password:");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);
        

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(300, 30));
        panel.add(password);
        

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        teacherFrame.add(panel, BorderLayout.CENTER);  
        teacherFrame.pack();
        teacherFrame.setLocationRelativeTo(null); 
        teacherFrame.setVisible(true);
    
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
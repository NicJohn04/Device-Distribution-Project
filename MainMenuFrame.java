import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;




public class MainMenuFrame extends JFrame{
//Main window seen as you run the code(login page) it has the option to login as an admin or an teacher
    public MainMenuFrame(){
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Choose your login option");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        JButton adminloginButton = new JButton("Admin Login"); // adds a new button to the window titled admin Login
        panel.add(adminloginButton); // adds the button to the panel

        adminloginButton.addActionListener(new ActionListener() {  // this set the action that is taken by the Admin login button
            public void actionPerformed(ActionEvent e){
                adminlog(); // function that carries you to the main admin login option prompts you to enter password
            }
        });

        JButton teacherloginButton = new JButton("Teacher Login"); //adds a new button to the window titled teacher Login
        panel.add(teacherloginButton); // adds the button to the panel
        teacherloginButton.addActionListener(new ActionListener() {  // this set the action that is taken by the Admin login button
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

            JButton UsermanagementButton = new JButton("User Management"); // this is where you link the User Management option also allows for user controls mainly for the admin
            UsermanagementButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e){
                TeacherManagementGUI teacherManagementGUI = new TeacherManagementGUI();
                teacherManagementGUI.setVisible(true);
              }  
            });

            JButton ReportGenButton = new JButton("Report Creation"); // this is where you link the Report creation option
            ReportGenButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    DateRangeGUI date = new DateRangeGUI();
                    date.setVisible(true);
                    
                }
            });

            JButton InventoryButton = new JButton("Inventory Management"); // this is where you link the Inventory  Managment
            InventoryButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    new InventoryManagement();
                }
            });


            JPanel buttonPanel = new JPanel(); //Adds a new panel within the existing one this one is specifically for the buttons
            buttonPanel.add(UsermanagementButton); //adds the button to the new panel 
            buttonPanel.add(ReportGenButton); //adds the button to the new panel
            buttonPanel.add(InventoryButton); //adds the button to the new panel

            mainFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH); // adds the button panel to the existing frame
            mainFrame.setSize(800,350);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true); // allow the frame to be seen

        } else{
            JOptionPane.showMessageDialog(adminFrame,"Ivalid password. please  try again", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

    }



    private void teacherlog(){
        
        JFrame teacherFrame = new JFrame("Teacher Login");
        teacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));
        
        panel.add(new JLabel("Enter Email:   "));
        JTextField email =  new JTextField();
        email.setPreferredSize(new Dimension(300,30));
        panel.add(email);

        panel.add(new JLabel("Enter Password"));
        JPasswordField password_teach = new JPasswordField();
        password_teach.setPreferredSize(new Dimension(300, 30));
        panel.add(password_teach);
        

        //panel.add(new JLabel("Login"));
        JButton teacherloginButton = new JButton("Login");
        teacherloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    teacherloginlistener(teacherFrame, password_teach, email);
                } catch (HeadlessException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        teacherFrame.add(teacherloginButton, BorderLayout.SOUTH);

        teacherFrame.add(panel);  
        teacherFrame.pack();
        teacherFrame.setLocationRelativeTo(null); 
        teacherFrame.setVisible(true);
    
    }


    private void teacherloginlistener(JFrame teacherFrame, JPasswordField passwordField,JTextField email) throws HeadlessException, IOException{
        String teachpass = new String(passwordField.getPassword()).trim();
        String mail = email.getText().trim();


        if (fileCheck(teachpass) && fileCheck(mail)){
            teacherFrame.dispose();

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JFrame mainFrame_teach = new JFrame("teacher DashBoard");
            mainFrame_teach.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame_teach.setContentPane(mainPanel);
            mainFrame_teach.setVisible(true);

            JButton BookEquiButton = new JButton("Book Equipment");
            BookEquiButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    new Booking(mail);

                }
            });
            
           // JButton NotifyButton = new JButton("Notification");

            JPanel buttonPanel_t = new JPanel();
            buttonPanel_t.add(BookEquiButton);
           // buttonPanel_t.add(NotifyButton);

           
            mainFrame_teach.getContentPane().add(buttonPanel_t, BorderLayout.NORTH);
            mainFrame_teach.setSize(800,350);
            mainFrame_teach.setLocationRelativeTo(null);
            mainFrame_teach.setVisible(true);

        } else{
            JOptionPane.showMessageDialog(teacherFrame,"Ivalid password. please  try again", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

    }



    private static boolean fileCheck(String mail)throws IOException{
        try (FileReader fileChecker = new FileReader("C://Users//daena//Downloads//Device-Distribution-Project-main latest version//Device-Distribution-Project-main latest version//Device-Distribution-Project-main//Java Software Project//src//teachers.csv");
        BufferedReader fileReader = new BufferedReader(fileChecker)){
            String readFile = fileReader.readLine();
            while (readFile != null) {
                if (readFile.contains(mail)){
                    return true;
                }
                readFile = fileReader.readLine();
                
            }
            return false;
        }

    }




// this allows for the frame to run 
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenuFrame frame = new MainMenuFrame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true); // Allows you to see the frame 
                frame.pack(); // this fits the frame into the correct dimention
            }
        });
    }


}

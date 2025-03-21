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

public class MainMenuFrame extends JFrame {
    // Main window seen as you run the code(login page) it has the option to login
    // as an admin or a teacher
    public MainMenuFrame() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600)); // Set preferred size for MainMenuFrame
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        // Add title above the image
        JLabel titleLabel = new JLabel("Excelsior High School Device Distribution");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Add image above the buttons
        JLabel imageLabel = new JLabel(new ImageIcon("Device-Distribution-Project-main/crest.png"));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(imageLabel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(Color.WHITE); // Set background color to white

        JLabel label = new JLabel("Choose your login option");
        label.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE); // Set background color to white

        JButton adminloginButton = new JButton("Admin Login"); // adds a new button to the window titled admin Login
        buttonPanel.add(adminloginButton); // adds the button to the panel

        adminloginButton.addActionListener(new ActionListener() { // this set the action that is taken by the Admin
                                                                  // login button
            public void actionPerformed(ActionEvent e) {
                adminlog(); // function that carries you to the main admin login option prompts you to enter
                            // password
            }
        });

        JButton teacherloginButton = new JButton("Teacher Login"); // adds a new button to the window titled teacher
                                                                   // Login
        buttonPanel.add(teacherloginButton); // adds the button to the panel
        teacherloginButton.addActionListener(new ActionListener() { // this set the action that is taken by the Admin
                                                                    // login button
            public void actionPerformed(ActionEvent e) {
                teacherlog();
            }
        });

        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adminlog() {
        JFrame adminFrame = new JFrame("Admin Login");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setPreferredSize(new Dimension(800, 600)); // Set preferred size for adminFrame

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.WHITE); // Set background color to white

        JLabel label = new JLabel("Enter Password:");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(300, 30));
        panel.add(password);

        JButton adminloginButton = new JButton("Login");
        adminloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminloginlistener(adminFrame, password);
            }
        });
        panel.add(adminloginButton);

        adminFrame.add(panel, BorderLayout.CENTER);
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    private void adminloginlistener(JFrame adminFrame, JPasswordField passwordField) {
        String adminpass = new String(passwordField.getPassword());

        if (adminpass.equals("Terry48admin")) {
            adminFrame.dispose();

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(Color.WHITE); // Set background color to white

            JFrame mainFrame = new JFrame("Admin Dashboard");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setContentPane(mainPanel);
            mainFrame.setPreferredSize(new Dimension(800, 600)); // Set preferred size for mainFrame

            // Add title to the dashboard
            JLabel dashboardTitle = new JLabel("Admin Dashboard");
            dashboardTitle.setFont(new Font("Arial", Font.BOLD, 24));
            dashboardTitle.setHorizontalAlignment(JLabel.CENTER);
            dashboardTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainFrame.add(dashboardTitle, BorderLayout.NORTH);

            // Create a panel for buttons
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            buttonPanel.setBackground(Color.WHITE); // Set background color to white

            JButton userManagementButton = new JButton("User Management"); // this is where you link the User Management
                                                                           // option also allows for user controls
                                                                           // mainly for the admin
            userManagementButton.setPreferredSize(new Dimension(150, 40));
            userManagementButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TeacherManagementGUI teacherManagementGUI = new TeacherManagementGUI();
                    teacherManagementGUI.setVisible(true);
                }
            });

            JButton reportGenButton = new JButton("Report Creation"); // this is where you link the Report creation
                                                                      // option
            reportGenButton.setPreferredSize(new Dimension(150, 40));
            reportGenButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DateRangeGUI date = new DateRangeGUI();
                    date.setVisible(true);
                }
            });

            JButton inventoryButton = new JButton("Inventory Management"); // this is where you link the Inventory
                                                                           // Management
            inventoryButton.setPreferredSize(new Dimension(150, 40));
            inventoryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new InventoryManagement();
                }
            });

            buttonPanel.add(userManagementButton); // adds the button to the new panel
            buttonPanel.add(reportGenButton); // adds the button to the new panel
            buttonPanel.add(inventoryButton); // adds the button to the new panel

            mainFrame.add(buttonPanel, BorderLayout.CENTER); // adds the button panel to the existing frame

            // Add image below the buttons
            JLabel imageLabel = new JLabel(new ImageIcon("Device-Distribution-Project-main/excelsior.jpeg"));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainFrame.add(imageLabel, BorderLayout.SOUTH);

            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true); // allow the frame to be seen

        } else {
            JOptionPane.showMessageDialog(adminFrame, "Invalid password. please try again", "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void teacherlog() {
        JFrame teacherFrame = new JFrame("Teacher Login");
        teacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        teacherFrame.setPreferredSize(new Dimension(800, 600)); // Set preferred size for teacherFrame

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.setBackground(Color.WHITE); // Set background color to white

        panel.add(new JLabel("Enter Email:   "));
        JTextField email = new JTextField();
        email.setPreferredSize(new Dimension(300, 30));
        panel.add(email);

        panel.add(new JLabel("Enter Password"));
        JPasswordField password_teach = new JPasswordField();
        password_teach.setPreferredSize(new Dimension(300, 30));
        panel.add(password_teach);

        JButton teacherloginButton = new JButton("Login");
        teacherloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    teacherloginlistener(teacherFrame, password_teach, email);
                } catch (HeadlessException | IOException e1) {
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

    private void teacherloginlistener(JFrame teacherFrame, JPasswordField passwordField, JTextField email)
            throws HeadlessException, IOException {
        String teachpass = new String(passwordField.getPassword()).trim();
        String mail = email.getText().trim();

        if (fileCheck(teachpass) && fileCheck(mail)) {
            teacherFrame.dispose();

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(Color.WHITE); // Set background color to white

            JFrame mainFrame_teach = new JFrame("Teacher Dashboard");
            mainFrame_teach.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame_teach.setContentPane(mainPanel);
            mainFrame_teach.setPreferredSize(new Dimension(800, 600)); // Set preferred size for mainFrame_teach
            mainFrame_teach.setVisible(true);

            JButton bookEquiButton = new JButton("Book Equipment");
            bookEquiButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Booking(mail);
                }
            });

            JPanel buttonPanel_t = new JPanel();
            buttonPanel_t.setBackground(Color.WHITE); // Set background color to white
            buttonPanel_t.add(bookEquiButton);

            mainFrame_teach.getContentPane().add(buttonPanel_t, BorderLayout.NORTH);
            mainFrame_teach.pack();
            mainFrame_teach.setLocationRelativeTo(null);
            mainFrame_teach.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(teacherFrame, "Invalid password. please try again", "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean fileCheck(String mail) throws IOException {
        try (FileReader fileChecker = new FileReader(
                "C://Users//daena//Downloads//Device-Distribution-Project-main latest version//Device-Distribution-Project-main latest version//Device-Distribution-Project-main//Java Software Project//src//teachers.csv");
                BufferedReader fileReader = new BufferedReader(fileChecker)) {
            String readFile = fileReader.readLine();
            while (readFile != null) {
                if (readFile.contains(mail)) {
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
                frame.pack(); // this fits the frame into the correct dimension
            }
        });
    }
}

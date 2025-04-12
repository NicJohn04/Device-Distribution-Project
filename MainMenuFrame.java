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
        JLabel titleLabel = new JLabel("Excelsior Device Portal");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Changed to Times New Roman
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Add image below the title
        JLabel crestImageLabel = new JLabel(
                new ImageIcon("Device-Distribution-Project-main/crest-removebg-preview.png"));
        crestImageLabel.setHorizontalAlignment(JLabel.CENTER);
        crestImageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(crestImageLabel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(Color.WHITE); // Set background color to white

        JLabel label = new JLabel("Choose your login option");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 18)); // Changed to Times New Roman
        label.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE); // Set background color to white

        // Admin Login Button
        JButton adminloginButton = new JButton("Admin Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#00B562")); // Green color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                super.paintComponent(g2);
            }
        };
        adminloginButton.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Changed to Times New Roman
        adminloginButton.setForeground(Color.WHITE); // Text color
        adminloginButton.setOpaque(false);
        adminloginButton.setContentAreaFilled(false);
        adminloginButton.setBorderPainted(false);
        buttonPanel.add(adminloginButton);

        adminloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminlog();
            }
        });

        // Teacher Login Button
        JButton teacherloginButton = new JButton("Teacher Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#FEE400")); // Yellow color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                super.paintComponent(g2);
            }
        };
        teacherloginButton.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Changed to Times New Roman
        teacherloginButton.setForeground(Color.BLACK); // Text color
        teacherloginButton.setOpaque(false);
        teacherloginButton.setContentAreaFilled(false);
        teacherloginButton.setBorderPainted(false);
        buttonPanel.add(teacherloginButton);

        teacherloginButton.addActionListener(new ActionListener() {
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

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE); // Set background color to white

        // Add title and subtitle in a vertical layout
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Excelsior Device Portal");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Large font for title
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0)); // Add spacing below the title
        titlePanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Administrator Login");
        subtitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18)); // Smaller font for subtitle
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Add spacing below the subtitle
        titlePanel.add(subtitleLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // Add image below the title and subtitle
        JLabel imageLabel = new JLabel(new ImageIcon("Device-Distribution-Project-main/excelsior_image.jpeg"));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add spacing around the image
        panel.add(imageLabel, BorderLayout.CENTER);

        // Add password input and login button
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBackground(Color.WHITE); // Set background color to white

        // Password label and text field on the same line
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Enter Password:");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Use Times New Roman
        passwordPanel.add(label);

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(200, 30)); // Shorter text field
        passwordPanel.add(password);

        inputPanel.add(passwordPanel);

        // Create login button
        JButton adminloginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#00B562")); // Green color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                super.paintComponent(g2);
            }
        };
        adminloginButton.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Changed to Times New Roman
        adminloginButton.setForeground(Color.WHITE); // Text color
        adminloginButton.setOpaque(false);
        adminloginButton.setContentAreaFilled(false);
        adminloginButton.setBorderPainted(false);
        adminloginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminloginlistener(adminFrame, password);
            }
        });

        // Add login button below the password field
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(adminloginButton);

        inputPanel.add(buttonPanel);

        // Add inputPanel to the main panel
        panel.add(inputPanel, BorderLayout.SOUTH);

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
            JPanel titlePanel = new JPanel(new GridLayout(2, 1));
            titlePanel.setBackground(Color.WHITE);

            JLabel dashboardTitle = new JLabel("Excelsior Device Portal");
            dashboardTitle.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Large font for title
            dashboardTitle.setHorizontalAlignment(JLabel.CENTER);
            dashboardTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
            titlePanel.add(dashboardTitle);

            JLabel dashboardSubtitle = new JLabel("Administrator Dashboard");
            dashboardSubtitle.setFont(new Font("Times New Roman", Font.PLAIN, 18)); // Smaller font for subtitle
            dashboardSubtitle.setHorizontalAlignment(JLabel.CENTER);
            dashboardSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            titlePanel.add(dashboardSubtitle);

            mainFrame.add(titlePanel, BorderLayout.NORTH);

            // Create a panel for buttons
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            buttonPanel.setBackground(Color.WHITE); // Set background color to white

            // User Management Button (Yellow)
            JButton userManagementButton = new JButton("User Management") {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#FEE400")); // Yellow color
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                    super.paintComponent(g2);
                }
            };
            userManagementButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            userManagementButton.setForeground(Color.BLACK);
            userManagementButton.setOpaque(false);
            userManagementButton.setContentAreaFilled(false);
            userManagementButton.setBorderPainted(false);
            userManagementButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TeacherManagementGUI teacherManagementGUI = new TeacherManagementGUI();
                    teacherManagementGUI.setVisible(true);
                }
            });

            // Report Creation Button (Green)
            JButton reportGenButton = new JButton("Report Creation") {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#00B562")); // Green color
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                    super.paintComponent(g2);
                }
            };
            reportGenButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            reportGenButton.setForeground(Color.WHITE);
            reportGenButton.setOpaque(false);
            reportGenButton.setContentAreaFilled(false);
            reportGenButton.setBorderPainted(false);
            reportGenButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DateRangeGUI date = new DateRangeGUI();
                    date.setVisible(true);
                }
            });

            // Inventory Management Button (Cream)
            JButton inventoryButton = new JButton("Inventory Management") {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#FFFDD0")); // Cream color
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                    super.paintComponent(g2);
                }
            };
            inventoryButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            inventoryButton.setForeground(Color.BLACK);
            inventoryButton.setOpaque(false);
            inventoryButton.setContentAreaFilled(false);
            inventoryButton.setBorderPainted(false);
            inventoryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new InventoryManagement();
                }
            });

            buttonPanel.add(userManagementButton);
            buttonPanel.add(reportGenButton);
            buttonPanel.add(inventoryButton);

            mainFrame.add(buttonPanel, BorderLayout.CENTER);

            // Add image below the buttons
            JLabel imageLabel = new JLabel(new ImageIcon("Device-Distribution-Project-main/excelsior.jpeg"));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainFrame.add(imageLabel, BorderLayout.SOUTH);

            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(adminFrame, "Invalid password. Please try again.", "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void teacherlog() {
        JFrame teacherFrame = new JFrame("Teacher Login");
        teacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        teacherFrame.setPreferredSize(new Dimension(800, 600)); // Set preferred size for teacherFrame

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE); // Set background color to white

        // Add title and subtitle in a vertical layout
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Excelsior Device Portal");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Large font for title
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0)); // Add spacing below the title
        titlePanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Teacher Login");
        subtitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18)); // Smaller font for subtitle
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Add spacing below the subtitle
        titlePanel.add(subtitleLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // Add image below the title and subtitle
        JLabel imageLabel = new JLabel(new ImageIcon("Device-Distribution-Project-main/excelsior_image(2).jpeg"));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add spacing around the image
        panel.add(imageLabel, BorderLayout.CENTER);

        // Add email and password input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        inputPanel.setBackground(Color.WHITE); // Set background color to white

        // Email label and text field
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPanel.setBackground(Color.WHITE);

        JLabel emailLabel = new JLabel("Enter Email:");
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Use Times New Roman
        emailPanel.add(emailLabel);

        JTextField email = new JTextField();
        email.setPreferredSize(new Dimension(200, 30)); // Shorter text field
        emailPanel.add(email);

        inputPanel.add(emailPanel);

        // Password label and text field
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Use Times New Roman
        passwordPanel.add(passwordLabel);

        JPasswordField password_teach = new JPasswordField();
        password_teach.setPreferredSize(new Dimension(200, 30)); // Shorter text field
        passwordPanel.add(password_teach);

        inputPanel.add(passwordPanel);

        // Create login button
        JButton teacherLoginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#00B562")); // Green color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle
                super.paintComponent(g2);
            }
        };
        teacherLoginButton.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Changed to Times New Roman
        teacherLoginButton.setForeground(Color.WHITE); // Text color
        teacherLoginButton.setOpaque(false);
        teacherLoginButton.setContentAreaFilled(false);
        teacherLoginButton.setBorderPainted(false);
        teacherLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    teacherloginlistener(teacherFrame, password_teach, email);
                } catch (HeadlessException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Add login button below the input fields
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(teacherLoginButton);

        inputPanel.add(buttonPanel);

        // Add inputPanel to the main panel
        panel.add(inputPanel, BorderLayout.SOUTH);

        teacherFrame.add(panel, BorderLayout.CENTER);
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
        try (FileReader fileChecker = new FileReader("Device-Distribution-Project-main/teachers.csv");
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

    private void teacherManagementSystem(JFrame previousFrame) {
        JFrame teacherManagementFrame = new JFrame("Teacher Management System");
        teacherManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        teacherManagementFrame.setPreferredSize(new Dimension(800, 600)); // Set preferred size for
                                                                          // teacherManagementFrame

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Set background color to white

        // Add title
        JLabel titleLabel = new JLabel("Teacher Management System");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Large font for title
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE); // Set background color to white

        // Add Teacher Button
        JButton addTeacherButton = new JButton("Add Teacher");
        addTeacherButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        addTeacherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic for adding a teacher
            }
        });
        buttonPanel.add(addTeacherButton);

        // Remove Teacher Button
        JButton removeTeacherButton = new JButton("Remove Teacher");
        removeTeacherButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        removeTeacherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic for removing a teacher
            }
        });
        buttonPanel.add(removeTeacherButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherManagementFrame.dispose(); // Close the current frame
                previousFrame.setVisible(true); // Show the previous frame
            }
        });
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        teacherManagementFrame.add(mainPanel);
        teacherManagementFrame.pack();
        teacherManagementFrame.setLocationRelativeTo(null);
        teacherManagementFrame.setVisible(true);
    }

    // Method to open the Equipment Booking Frame
    private void openEquipmentBookingFrame(JFrame dashboardFrame) {
        dashboardFrame.dispose(); // Close the Teacher Dashboard Frame

        JFrame bookingFrame = new JFrame("Equipment Booking");
        bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookingFrame.setPreferredSize(new Dimension(800, 600)); // Set preferred size for bookingFrame

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE); // Set background color to white

        JLabel titleLabel = new JLabel("Equipment Booking");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Large font for title
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add spacing around the title
        panel.add(titleLabel, BorderLayout.NORTH);

        // Add content for booking functionality (e.g., form fields, buttons)
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(new JLabel("Booking functionality goes here.")); // Placeholder
        panel.add(contentPanel, BorderLayout.CENTER);

        bookingFrame.add(panel);
        bookingFrame.pack();
        bookingFrame.setLocationRelativeTo(null);
        bookingFrame.setVisible(true);
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

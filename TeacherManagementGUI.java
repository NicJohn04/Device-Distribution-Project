import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;

/**
 * GUI Application for Teacher Management System
 */
public class TeacherManagementGUI extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(TeacherManagementGUI.class.getName());
    
    // Static list to store teachers
    private static List<Teacher> teacherDatabase = new ArrayList<>(TeacherFileManager.loadTeachersFromFile());
    
    // Input Fields
    private JTextField txtTeacherId, txtName, txtEmail, txtContactNumber, txtRole;
    private JComboBox<Teacher.TeacherStatus> cmbStatus;
    private JTable teacherTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor to set up the GUI
     */
    public TeacherManagementGUI() {
        // Set up the main frame
        setTitle("Teacher Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create input panel
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        // Create table panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize the table with existing teachers
        refreshTable();
    }

    /**
     * Adds a new teacher to the system
     * @param teacher Teacher object to add
     * @return boolean indicating success
     */
    public static boolean addTeacher(Teacher teacher) {
        // Check for duplicate teacher ID
        if (teacherDatabase.stream().anyMatch(t -> t.getTeacherId() == teacher.getTeacherId())) {
            return false;
        }
        
        teacherDatabase.add(teacher);
        return true;
    }

    /**
     * Finds a teacher by ID
     * @param teacherId ID of the teacher to find
     * @return Teacher object or null if not found
     */
    public static Teacher findTeacherById(int teacherId) {
        return teacherDatabase.stream()
                .filter(t -> t.getTeacherId() == teacherId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes a teacher from the system
     * @param teacherId ID of the teacher to remove
     * @return boolean indicating success
     */
    public static boolean removeTeacher(int teacherId) {
        Teacher teacher = findTeacherById(teacherId);
        if (teacher == null) return false;
        
        teacher.setStatus(Teacher.TeacherStatus.INACTIVE);
        return true;
    }

    /**
     * Updates teacher information
     * @param teacherId ID of the teacher to update
     * @param name New name (can be null)
     * @param email New email (can be null)
     * @param contactNumber New contact number (can be null)
     * @param role New role (can be null)
     * @return boolean indicating success
     */
    public static boolean updateTeacherInfo(int teacherId, String name, String email, 
                                            String contactNumber, String role) {
        Teacher teacher = findTeacherById(teacherId);
        if (teacher == null) return false;

        if (name != null) teacher.setName(name);
        if (email != null) teacher.setEmail(email);
        if (contactNumber != null) teacher.setContactNumber(contactNumber);
        if (role != null) teacher.setRole(role);
        
        return true;
    }

    // Rest of the previous GUI implementation remains the same...
    // (createInputPanel, createTablePanel, createButtonPanel methods)

    /**
     * Create input panel (same as previous implementation)
     */
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Teacher Information"));
    
        // Teacher ID
        inputPanel.add(new JLabel("Teacher ID:"));
        txtTeacherId = new JTextField();
        inputPanel.add(txtTeacherId);
    
        // Name
        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);
    
        // Email
        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);
    
        // Contact Number
        inputPanel.add(new JLabel("Contact Number:"));
        txtContactNumber = new JTextField();
        inputPanel.add(txtContactNumber);
    
        // Role
        inputPanel.add(new JLabel("Role:"));
        txtRole = new JTextField();
        inputPanel.add(txtRole);
    
        // Status
        inputPanel.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(Teacher.TeacherStatus.values());
        cmbStatus.setSelectedItem(Teacher.TeacherStatus.ACTIVE);
        inputPanel.add(cmbStatus);
    
        return inputPanel;
    }

    /**
     * Create table panel (same as previous implementation)
     */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
    
        // Define column names
        String[] columnNames = {"ID", "Name", "Email", "Contact", "Role", "Status", "Date Created", "Last Updated"};
    
        // Create table model
        tableModel = new DefaultTableModel(columnNames, 0);
    
        // Create table
        teacherTable = new JTable(tableModel);
        teacherTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(teacherTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    
        return tablePanel;
    }

    /**
     * Create button panel (same as previous implementation)
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
        // Add Teacher Button
        JButton btnAdd = new JButton("Add Teacher");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTeacher();
        }
        });
        buttonPanel.add(btnAdd);
    
        // Update Teacher Button
        JButton btnUpdate = new JButton("Update Teacher");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTeacher();
            }
        });
        buttonPanel.add(btnUpdate);
    
        // Remove Teacher Button
        JButton btnRemove = new JButton("Remove Teacher");
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTeacher();
            }
        });
        buttonPanel.add(btnRemove);
    
        // Clear Button
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearInputFields();
            }
        });
        buttonPanel.add(btnClear);
    
        return buttonPanel;
    }

    /**
     * Add a new teacher to the system
     */
    private void addTeacher() {
        try {
            int teacherId = Integer.parseInt(txtTeacherId.getText());
            String name = txtName.getText();
            String email = txtEmail.getText();
            String contactNumber = txtContactNumber.getText();
            String role = txtRole.getText();

            Teacher newTeacher = new Teacher(teacherId, name, email, contactNumber, role);
            
            if (addTeacher(newTeacher)) {
                JOptionPane.showMessageDialog(this, "Teacher added successfully!", 
                                              "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add teacher. Teacher ID might already exist.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Teacher ID.", 
                                          "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error adding teacher", ex);
        }
    }

    /**
     * Update existing teacher information
     */
    private void updateTeacher() {
        try {
            int teacherId = Integer.parseInt(txtTeacherId.getText());
            String name = txtName.getText();
            String email = txtEmail.getText();
            String contactNumber = txtContactNumber.getText();
            String role = txtRole.getText();
            Teacher.TeacherStatus status = (Teacher.TeacherStatus) cmbStatus.getSelectedItem();

            Teacher teacher = findTeacherById(teacherId);
            if (teacher != null) {
                updateTeacherInfo(teacherId, name, email, contactNumber, role);
                teacher.setStatus(status);
                
                JOptionPane.showMessageDialog(this, "Teacher updated successfully!", 
                                              "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update teacher. Teacher not found.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Teacher ID.", 
                                          "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error updating teacher", ex);
        }
    }

    /**
     * Remove a teacher from the system
     */
    private void removeTeacher() {
        try {
            int teacherId = Integer.parseInt(txtTeacherId.getText());

            if (removeTeacher(teacherId)) {
                JOptionPane.showMessageDialog(this, "Teacher removed successfully!", 
                                              "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove teacher. Teacher not found.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Teacher ID.", 
                                          "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error removing teacher", ex);
        }
    }

    /**
     * Refresh the teacher table with current data
     */
    private void refreshTable() {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Formatter for date/time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Add teachers to the table
        for (Teacher teacher : teacherDatabase) {
            Vector<Object> row = new Vector<>();
            row.add(teacher.getTeacherId());
            row.add(teacher.getName());
            row.add(teacher.getEmail());
            row.add(teacher.getContactNumber());
            row.add(teacher.getRole());
            row.add(teacher.getStatus());
            row.add(teacher.getDateCreated().format(formatter));
            row.add(teacher.getLastUpdated().format(formatter));
            
            tableModel.addRow(row);
        }
    }

    /**
     * Clear all input fields
     */
    private void clearInputFields() {
        txtTeacherId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtContactNumber.setText("");
        txtRole.setText("");
        cmbStatus.setSelectedItem(Teacher.TeacherStatus.ACTIVE);
    }





// Modify the main method to save teachers when the application exits
public static void main(String[] args) {
    // Ensure GUI runs on Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
        try {
            // Optional: Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not set system look and feel", e);
        }

        // Create and show the application
        TeacherManagementGUI app = new TeacherManagementGUI();
        app.setVisible(true);

        // Add shutdown hook to save teachers when application closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            TeacherFileManager.saveTeachersToFile(teacherDatabase);
        }));
    });
}
}


  
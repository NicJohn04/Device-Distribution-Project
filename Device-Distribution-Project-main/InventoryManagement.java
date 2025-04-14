import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

enum EquipmentStatus {
    FUNCTIONAL,
    DAMAGED,
    UNDER_REPAIR,
    REMOVED
}

class Equipment {
    private String equipmentId;
    private String equipmentName;
    private String serialNumber;
    private EquipmentStatus status;
    private String description;

    // Constructor
    public Equipment(String equipmentId, String equipmentName, String serialNumber, EquipmentStatus status,
            String description) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.serialNumber = serialNumber;
        this.status = status;
        this.description = description;
    }

    // Getters
    public String getEquipmentId() {
        return equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    // Setter for status (needed for update functionality)
    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + equipmentId + ", Name: " + equipmentName + ", Serial: " + serialNumber + ", Status: " + status
                + ", Description: " + description;
    }
}

public class InventoryManagement {
    private JFrame frame;
    private JTextField equipmentIdField;
    private JTextField equipmentNameField;
    private JTextField serialField;
    private JTextField descriptionField;
    private JComboBox<EquipmentStatus> statusComboBox;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private List<Equipment> inventory;

    // File to save the inventory data
    private static final String FILE_PATH = "C:/Users/daena/Downloads/Device-Distribution-Project-main (1)/Device-Distribution-Project-main/Equipment.dat";

    public InventoryManagement() {
        inventory = new ArrayList<>();
        frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);
        frame.setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        // Add input fields
        inputPanel.add(new JLabel("Equipment ID:"));
        equipmentIdField = new JTextField();
        inputPanel.add(equipmentIdField);

        inputPanel.add(new JLabel("Equipment Name:"));
        equipmentNameField = new JTextField();
        inputPanel.add(equipmentNameField);

        inputPanel.add(new JLabel("Serial Number:"));
        serialField = new JTextField();
        inputPanel.add(serialField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(EquipmentStatus.values());
        inputPanel.add(statusComboBox);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Equipment");
        JButton updateButton = new JButton("Update Status");
        JButton removeButton = new JButton("Remove Equipment");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        // Table for displaying inventory
        String[] columnNames = { "Equipment ID", "Equipment Name", "Serial Number", "Status", "Description" };
        tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        // Add components to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equipmentId = equipmentIdField.getText();
                String equipmentName = equipmentNameField.getText();
                String serialNumber = serialField.getText();
                String description = descriptionField.getText();
                EquipmentStatus status = (EquipmentStatus) statusComboBox.getSelectedItem();

                if (!equipmentId.isEmpty() && !equipmentName.isEmpty() && !serialNumber.isEmpty()
                        && !description.isEmpty() && status != null) {
                    addEquipment(equipmentId, equipmentName, serialNumber, status, description);
                    clearFields();
                    saveInventoryToFile(); // Save to file after adding
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String serialNumber = serialField.getText();
                EquipmentStatus status = (EquipmentStatus) statusComboBox.getSelectedItem();
                if (!serialNumber.isEmpty() && status != null) {
                    updateEquipmentStatus(serialNumber, status);
                    clearFields();
                    saveInventoryToFile(); // Save to file after updating
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter serial number and new status.");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String serialNumber = serialField.getText();
                if (!serialNumber.isEmpty()) {
                    removeEquipment(serialNumber);
                    clearFields();
                    saveInventoryToFile(); // Save to file after removal
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter serial number to remove.");
                }
            }
        });

        // Load inventory from file
        loadInventoryFromFile();

        // Show the frame
        frame.setVisible(true);
    }

    // Method to add equipment
    public void addEquipment(String equipmentId, String equipmentName, String serialNumber, EquipmentStatus status,
            String description) {
        Equipment equipment = new Equipment(equipmentId, equipmentName, serialNumber, status, description);
        inventory.add(equipment);

        // Add the new equipment to the table model
        tableModel.addRow(new Object[] { equipmentId, equipmentName, serialNumber, status, description });

        JOptionPane.showMessageDialog(frame, "Equipment added: " + equipment);
    }

    // Method to update equipment status
    public void updateEquipmentStatus(String serialNumber, EquipmentStatus newStatus) {
        for (int i = 0; i < inventory.size(); i++) {
            Equipment equipment = inventory.get(i);
            if (equipment.getSerialNumber().equals(serialNumber)) {
                equipment.setStatus(newStatus);

                // Update the status in the table
                tableModel.setValueAt(newStatus, i, 3); // Column index 3 is the Status column

                JOptionPane.showMessageDialog(frame, "Equipment status updated: " + equipment);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Equipment with serial number " + serialNumber + " not found.");
    }

    // Method to remove equipment
    public void removeEquipment(String serialNumber) {
        for (int i = 0; i < inventory.size(); i++) {
            Equipment equipment = inventory.get(i);
            if (equipment.getSerialNumber().equals(serialNumber)) {
                inventory.remove(i);

                // Remove the row from the table
                tableModel.removeRow(i);

                JOptionPane.showMessageDialog(frame, "Removed equipment: " + equipment);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Equipment with serial number " + serialNumber + " not found.");
    }

    // Method to clear input fields
    public void clearFields() {
        equipmentIdField.setText("");
        equipmentNameField.setText("");
        serialField.setText("");
        descriptionField.setText("");
        statusComboBox.setSelectedIndex(0);
    }

    // Method to save the inventory to a file
    public void saveInventoryToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Equipment equipment : inventory) {
                writer.write(equipment.getEquipmentId() + "," + equipment.getEquipmentName() + ","
                        + equipment.getSerialNumber() + "," + equipment.getStatus() + ","
                        + equipment.getDescription() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving inventory to file: " + e.getMessage());
        }
    }

    // Method to load the inventory from a file
    public void loadInventoryFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // If the file doesn't exist, show a warning and return
            JOptionPane.showMessageDialog(frame, "Inventory file not found. Starting with an empty inventory.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 5) { // Ensure the line has all required fields
                    String equipmentId = parts[0].trim();
                    String equipmentName = parts[1].trim();
                    String serialNumber = parts[2].trim();
                    EquipmentStatus status = EquipmentStatus.valueOf(parts[3].trim());
                    String description = parts[4].trim();

                    // Create an Equipment object and add it to the inventory
                    Equipment equipment = new Equipment(equipmentId, equipmentName, serialNumber, status, description);
                    inventory.add(equipment);

                    // Add the equipment to the table model
                    tableModel.addRow(new Object[] { equipmentId, equipmentName, serialNumber, status, description });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading inventory from file: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Error parsing inventory file. Please check the file format.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new InventoryManagement();
    }
}

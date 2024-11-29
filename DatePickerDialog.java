import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePickerDialog extends JDialog {
    private JTextField dateField;
    private LocalDate selectedDate;
    private boolean confirmed = false;

    public DatePickerDialog(JFrame parent) {
        // Call the parent constructor, making this a modal dialog
        super(parent, "Select Date", true);
        
        // Set up the dialog layout
        setLayout(new BorderLayout());
        
        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter Date (dd-MM-yyyy):"));
        dateField = new JTextField(10);
        inputPanel.add(dateField);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        okButton.addActionListener(e -> validateAndClose());
        cancelButton.addActionListener(e -> {
            selectedDate = null;
            dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set dialog properties
        setSize(300, 150);
        setLocationRelativeTo(parent);
    }
    
    private void validateAndClose() {
        try {
            // Parse the date using ISO format
            selectedDate = LocalDate.parse(dateField.getText().trim(), 
                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            confirmed = true;
            dispose();
        } catch (DateTimeParseException ex) {
            // Show error message if date is invalid
            JOptionPane.showMessageDialog(this, 
                "Invalid date format. Please use dd-MM-yyyy format.", 
                "Date Format Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Returns the selected date.
     * @return LocalDate selected by the user, or null if no date was selected
     */
    public LocalDate getSelectedDate() {
        return confirmed ? selectedDate : null;
    }
}
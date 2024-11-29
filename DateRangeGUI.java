import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateRangeGUI extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(DateRangeGUI.class.getName());


    private JTextField startDate, endDate;
    private JComboBox<String> dateRangeComboBox;
    private JPanel middlePanel;

    public DateRangeGUI() {
        setTitle("Date Range Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top panel with date range options
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel dateRangeLabel = new JLabel("Choose booking period:");
        topPanel.add(dateRangeLabel);

        String[] dateRangeOptions = {" ", "1 Day", "1 Week", "2 Weeks", "1 Month", "Custom"};
        dateRangeComboBox = new JComboBox<>(dateRangeOptions);
        dateRangeComboBox.addActionListener(new ShowCustomActionListener());
        topPanel.add(dateRangeComboBox);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Middle panel with start and end date fields
        middlePanel = new JPanel(new FlowLayout());
        JLabel startDateLabel = new JLabel("Start Date:");
        startDate = new JTextField(10);
        middlePanel.add(startDateLabel);
        middlePanel.add(startDate);

        JLabel endDateLabel = new JLabel("End Date:");
        endDate = new JTextField(10);
        middlePanel.add(endDateLabel);
        middlePanel.add(endDate);

        JPanel bottomPanel = new JPanel();
        JButton submitButton = new JButton("Generate Report");
        submitButton.addActionListener(new SubmitButtonListener());
        bottomPanel.add(submitButton);

        middlePanel.setVisible(false);

        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class ShowCustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) dateRangeComboBox.getSelectedItem();
            middlePanel.setVisible(selectedOption.equals("Custom"));
        }
    }

    private void setDates() {
        String selectedOption = (String) dateRangeComboBox.getSelectedItem();
        LocalDate now = LocalDate.now();

        switch (selectedOption.toLowerCase()) {
            case "1 day":
                startDate.setText(now.minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                endDate.setText(now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                break;
            case "1 week":
                startDate.setText(now.minusWeeks(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                endDate.setText(now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                break;
            case "2 weeks":
                startDate.setText(now.minusWeeks(2).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                endDate.setText(now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                break;
            case "1 month":
                startDate.setText(now.minusMonths(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                endDate.setText(now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                break;
            case "custom":
                middlePanel.setVisible(true);
                break;
        }
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Set dates if not already set
            if (startDate.getText().isEmpty() || endDate.getText().isEmpty()) {
                setDates();
            }
            
            try {
                // Parse start and end dates
                LocalDate startLocalDate = LocalDate.parse(startDate.getText(), 
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalDate endLocalDate = LocalDate.parse(endDate.getText(), 
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                
                // Validate date range
                if (startLocalDate.isAfter(endLocalDate)) {
                    JOptionPane.showMessageDialog(DateRangeGUI.this, 
                        "Start date must be before or equal to end date.", 
                        "Invalid Date Range", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate that date range is not more than 1 month
                if (ChronoUnit.MONTHS.between(startLocalDate, endLocalDate) > 1) {
                    JOptionPane.showMessageDialog(DateRangeGUI.this, 
                        "Date range cannot exceed 1 month.", 
                        "Date Range Exceeded", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Open Report Manager with the selected date range
                ReportManager reportManager = new ReportManager(startLocalDate, endLocalDate);
                reportManager.setVisible(true);
                
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(DateRangeGUI.this, 
                    "Invalid date format. Please use dd-MM-yyyy.", 
                    "Date Format Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(DateRangeGUI.this, 
                    "An error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        JFrame DateRangeGUI = new DateRangeGUI();
        DateRangeGUI.setVisible(true);
    }
}
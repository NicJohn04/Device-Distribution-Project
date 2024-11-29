import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DateRangeGUI extends JFrame {
    //private static final long serialVersionUID = 1L;
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
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        bottomPanel.add(submitButton);

        middlePanel.setVisible(false);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class ShowCustomActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String selectedOption = (String) dateRangeComboBox.getSelectedItem();
            if (!selectedOption.equals("Custom")) {
                middlePanel.setVisible(false);
            } else {
                middlePanel.setVisible(true);
            }
        }
    }

    private void setDates() 
    {
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
            default:
                throw new IllegalArgumentException("Invalid date range option.");
        }
    }


    private class SubmitButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //Report report = new Report(getName(), getName(), getName(), getName(), getTitle(), getWarningString(), getName())
        }

    }



    public static void main(String[] args) {
        DateRangeGUI gui = new DateRangeGUI();
        gui.setVisible(true);
    }
}
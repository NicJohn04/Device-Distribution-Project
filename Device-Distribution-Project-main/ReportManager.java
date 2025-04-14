import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportManager extends JFrame {
    private List<Booking> allBookings;
    private List<Booking> filteredBookings;
    private DefaultTableModel tableModel;
    private JComboBox<String> deviceTypeComboBox;
    private Map<String, String> teacherMap;

    public ReportManager(LocalDate startDate, LocalDate endDate) {
        // Frame setup
        setTitle("Booking Report Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);

        // Load teacher information
        teacherMap = loadTeachers();

        // Load bookings
        allBookings = loadBookings();

        // Filter bookings by date range
        filteredBookings = filterBookingsByDateRange(startDate, endDate);

        // Create UI components
        createTablePanel();
        createControlPanel();
        createButtonPanel();
    }

    private Map<String, String> loadTeachers() {
        Map<String, String> teachers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(
                "C://Users//daena//Downloads//Device-Distribution-Project-main (1)//Device-Distribution-Project-main//teachers.csv"))) {
            // Skip header
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    // Map email to name
                    teachers.put(parts[2].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error reading teachers file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return teachers;
    }

    private List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(
                "C://Users//daena//Downloads//Device-Distribution-Project-main (1)//Device-Distribution-Project-main//BookedEquipment.dat"))) {
            // Skip header if exists
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    // Adjust parsing based on actual file format
                    Booking booking = new Booking(
                            parts[0], // Device ID
                            parts[1], // Device Type
                            parts[2], // Serial Number
                            LocalDate.now(), // Default date, replace with actual date from file
                            LocalDate.now().plusDays(1), // Default return date
                            parts[6] // Email - you may need to add this to your data file
                    );
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error reading bookings: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return bookings;
    }

    private List<Booking> filterBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return allBookings.stream()
                .filter(booking -> !booking.getDateBooked().isBefore(startDate) &&
                        !booking.getDateBooked().isAfter(endDate))
                .collect(Collectors.toList());
    }

    private void createTablePanel() {
        // Create table model
        String[] columnNames = { "Device ID", "Device Type", "Serial Number", "Date Booked", "Return Date",
                "Booked By" };
        tableModel = new DefaultTableModel(columnNames, 0);

        // Populate table with filtered bookings
        for (Booking booking : filteredBookings) {
            // Look up teacher name by email
            // String teacherName = booking.getEmail() != null
            // ? teacherMap.getOrDefault(booking.getEmail(), "Unknown")
            // : "Unknown";

            tableModel.addRow(new Object[] {
                    booking.getDeviceId(),
                    booking.getDeviceType(),
                    booking.getSerialNumber(),
                    booking.getDateBooked().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    booking.getReturnDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    booking.getEmail()
            });
        }

        // Create table and scroll pane
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Device Type Filter
        controlPanel.add(new JLabel("Filter by Device Type:"));
        List<String> deviceTypes = allBookings.stream()
                .map(Booking::getDeviceType)
                .distinct()
                .collect(Collectors.toList());
        deviceTypes.add(0, "All");

        deviceTypeComboBox = new JComboBox<>(deviceTypes.toArray(new String[0]));
        deviceTypeComboBox.addActionListener(e -> filterByDeviceType());
        controlPanel.add(deviceTypeComboBox);

        // Date Filter Button
        JButton dateFilterButton = new JButton("Filter by Date");
        dateFilterButton.addActionListener(e -> showDateFilterDialog());
        controlPanel.add(dateFilterButton);

        add(controlPanel, BorderLayout.NORTH);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Save Report Button
        JButton saveButton = new JButton("Save Report");
        saveButton.addActionListener(e -> saveReport());
        buttonPanel.add(saveButton);

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void filterByDeviceType() {
        String selectedType = (String) deviceTypeComboBox.getSelectedItem();

        if (selectedType == null || selectedType.equals("All")) {
            updateTableWithBookings(filteredBookings);
        } else {
            List<Booking> typeFilteredBookings = filteredBookings.stream()
                    .filter(booking -> booking.getDeviceType().equals(selectedType))
                    .collect(Collectors.toList());
            updateTableWithBookings(typeFilteredBookings);
        }
    }

    private void showDateFilterDialog() {
        DatePickerDialog dateDialog = new DatePickerDialog(this);
        dateDialog.setVisible(true);

        LocalDate selectedDate = dateDialog.getSelectedDate();
        if (selectedDate != null) {
            List<Booking> dateFilteredBookings = filteredBookings.stream()
                    .filter(booking -> booking.getDateBooked().isEqual(selectedDate))
                    .collect(Collectors.toList());
            updateTableWithBookings(dateFilteredBookings);
        }
    }

    private void updateTableWithBookings(List<Booking> bookings) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add filtered bookings to table
        for (Booking booking : bookings) {
            // Look up teacher name by email
            // String teacherName = booking.getEmail() != null
            // ? teacherMap.getOrDefault(booking.getEmail(), "Unknown")
            // : "Unknown";

            tableModel.addRow(new Object[] {
                    booking.getDeviceId(),
                    booking.getDeviceType(),
                    booking.getSerialNumber(),
                    booking.getDateBooked().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    booking.getReturnDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    booking.getEmail()
            });
        }
    }

    private void saveReport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Booking Report");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Write headers
                writer.write("Device ID,Device Type,Serial Number,Date Booked,Return Date,Booked By");
                writer.newLine();

                // Write booking data
                for (Booking booking : filteredBookings) {
                    // Look up teacher name by email
                    // String teacherName = booking.getEmail() != null
                    // ? teacherMap.getOrDefault(booking.getEmail(), "Unknown")
                    // : "Unknown";

                    writer.write(String.format("%s,%s,%s,%s,%s,%s",
                            booking.getDeviceId(),
                            booking.getDeviceType(),
                            booking.getSerialNumber(),
                            booking.getDateBooked().format(DateTimeFormatter.ISO_LOCAL_DATE),
                            booking.getReturnDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                            booking.getEmail()));
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(this,
                        "Report saved successfully!",
                        "Save Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving report: " + ex.getMessage(),
                        "Save Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Booking class to represent individual bookings
    private static class Booking {
        private String deviceId;
        private String deviceType;
        private String serialNumber;
        private LocalDate dateBooked;
        private LocalDate returnDate;
        // private String email;
        private String name;

        public Booking(String deviceId, String deviceType, String serialNumber,
                LocalDate dateBooked, LocalDate returnDate, String name) {
            this.deviceId = deviceId;
            this.deviceType = deviceType;
            this.serialNumber = serialNumber;
            this.dateBooked = dateBooked;
            this.returnDate = returnDate;
            // this.email = email;
            this.name = name;
        }

        // Getters
        public String getDeviceId() {
            return deviceId;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public LocalDate getDateBooked() {
            return dateBooked;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        // public String getEmail() { return email; }
        public String getEmail() {
            return name;
        }
    }

    // Main method to launch the ReportManager
    public static void main(String[] args) {
        // Assuming you want to show bookings for the current month by default
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);

        // Use SwingUtilities to ensure thread safety for Swing components
        SwingUtilities.invokeLater(() -> {
            ReportManager frame = new ReportManager(startDate, endDate);
            frame.setVisible(true);
        });
    }
}
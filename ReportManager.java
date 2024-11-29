import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportManager extends JFrame {
    private static final long serialVersionUID = 1L;

    private List<ReportManager> reports;
    private Map<String, String> personMap;
    private DefaultTableModel tableModel;

    public ReportManager() {
        setTitle("Booking Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        readBookingsFile("BookedEquipment.dat");
        readPeopleFile("teachers.csv");

        createAndDisplayTable();
        createFilteringControls();
    }

    private void readBookingsFile(String filename) {
        booked = new ArrayList<>();

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line;
            br.readLine(); // Skip header row

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String deviceId = parts[0];
                String deviceType = parts[1];
                String deviceSerialNum = parts[2];
                LocalDate dateBooked = LocalDate.parse(parts[5], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate returnDate = LocalDate.parse(parts[6], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String email = parts[7];
                
                booked.add(new ReportManager(email, deviceId, deviceType, deviceSerialNum, dateBooked, returnDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readPeopleFile(String filename) {
        //personMap = new HashMap<>();

        teach = new ArrayList<>();

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line;
            br.readLine(); // Skip header row

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[1];
                String email = parts[2];
                teach.add(new TeacherRecord(name, email));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAndDisplayTable() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Device Type", "Device ID", "Date Booked", "Return Date", "Object Name", "Object ID"}, 0);

        for (ReportManager booking : booked) {
            //String name = personMap.getOrDefault(booking.getEmail(), "Unknown");
            //
            String bookingEmail = booking.getEmail();
    
            // Check if the email exists in the teacherMap
            if (teacherMap.containsKey(bookingEmail)) {
                // Replace the email in the booking with the teacher's name
                String teacherName = teacherMap.get(bookingEmail);
                booking.setEmail(teacherName);  // Assuming there's a setEmail() method
            }
            //
            tableModel.addRow(new Object[]{booking.getId(), name, booking.getDeviceType(), booking.getDeviceId(), booking.getDateBooked().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), booking.getReturnDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), booking.getObjectName(), booking.getObjectId()});
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createFilteringControls() {
        JPanel filterPanel = new JPanel(new FlowLayout());

        // Device type filter
        JLabel deviceTypeLabel = new JLabel("Filter by Device Type:");
        filterPanel.add(deviceTypeLabel);

        List<String> deviceTypes = booked.stream()
                .map(ReportManager::getDeviceType)
                .distinct()
                .collect(Collectors.toList());
        deviceTypes.add(0, "All");
        JComboBox<String> deviceTypeComboBox = new JComboBox<>(deviceTypes.toArray(new String[0]));
        deviceTypeComboBox.addActionListener(e -> filterByDeviceType());
        filterPanel.add(deviceTypeComboBox);

        // Date booked filter
        JLabel dateBookedLabel = new JLabel("Filter by Date Booked:");
        filterPanel.add(dateBookedLabel);

        JButton dateBookedButton = new JButton("Select Date");
        dateBookedButton.addActionListener(e -> filterByDateBooked());
        filterPanel.add(dateBookedButton);

        add(filterPanel, BorderLayout.NORTH);
    }

    private void filterByDeviceType() {
        JComboBox<String> deviceTypeComboBox = (JComboBox<String>) ((JPanel) getContentPane().getComponent(0)).getComponent(2);
        String selectedDeviceType = (String) deviceTypeComboBox.getSelectedItem();

        if (selectedDeviceType.equals("All")) {
            updateTableModel(booked);
        } else {
            List<ReportManager> filteredBookings = booked.stream()
                    .filter(booking -> booking.getDeviceType().equals(selectedDeviceType))
                    .collect(Collectors.toList());
            updateTableModel(filteredBookings);
        }
    }

    private void filterByDateBooked() {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setVisible(true);

        LocalDate selectedDate = dialog.getSelectedDate();
        if (selectedDate != null) {
            List<ReportManager> filteredBookings = booked.stream()
                    .filter(booking -> booking.getDateBooked().isEqual(selectedDate))
                    .collect(Collectors.toList());
            updateTableModel(filteredBookings);
        }
    }

    private void updateTableModel(List<Booking> bookings) {
        tableModel.setRowCount(0);
        for (ReportManager booking : bookings) {
            String name = personMap.getOrDefault(booking.getEmail(), "Unknown");
            tableModel.addRow(new Object[]{booking.getId(), name, booking.getDeviceType(), booking.getDeviceId(), booking.getDateBooked().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), booking.getReturnDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), booking.getObjectName(), booking.getObjectId()});
        }
    }

    public static void main(String[] args) {
        BookingManager frame = new BookingManager();
        frame.setVisible(true);
    }

    private static class ReportManager {
        private int id;
        private String email;
        private String deviceType;
        private String deviceId;
        private LocalDate dateBooked;
        private LocalDate returnDate;
        private String objectName;
        private String objectId;

        public ReportManager(email, String deviceType, String deviceId, LocalDate dateBooked, LocalDate returnDate) {
            this.id = id;
            this.email = email;
            this.deviceType = deviceType;
            this.deviceId = deviceId;
            this.dateBooked = dateBooked;
            this.returnDate = returnDate;
        }

        public int getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public LocalDate getDateBooked() {
            return dateBooked;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public String getObjectName() {
            return objectName;
        }

        public String getObjectId() {
            return objectId;
        }
    }
}
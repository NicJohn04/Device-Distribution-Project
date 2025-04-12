import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

public class Booking extends JFrame {

    private JTable Bookingtable;
    private JTextField tbookedField;
    private JTextField timeBookedField;
    private String teacherEmail;

    public Booking(String teacherEmail) {
        this.teacherEmail = teacherEmail;

        // Frame setup
        setTitle("Book Equipment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        final String FILE_PATH_1 = "Device-Distribution-Project-main//Equipment.dat";
        final String FILE_PATH_2 = "Device-Distribution-Project-main//BookedEquipment.dat";

        JFrame BookingFrame = new JFrame("Book Equipment"); // the frame of booking
        BookingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // seting the frame to close once you click on
                                                                     // close
        BookingFrame.setLocationRelativeTo(null);
        BookingFrame.setLayout(new BorderLayout());

        String[] columnNames = { "Select", "Equipment ID", "Equipment Name", "Serial Number", "Status", "Description",
                "Bookin Time", "Booking Status" }; // column names for the table

        Object[][] data = loadDataFromFile(FILE_PATH_1); // load data from file to store in a veriable which builds the
                                                         // table

        Object[][] dataWithCheckbox = new Object[data.length][data[0].length + 1]; // add the check box column
        for (int i = 0; i < data.length; i++) {
            dataWithCheckbox[i][0] = Boolean.FALSE; // seting the chech box to false allowing it to be checked
            System.arraycopy(data[i], 0, dataWithCheckbox[i], 1, data[i].length); // copy the data
        }

        DefaultTableModel model = new DefaultTableModel(dataWithCheckbox, columnNames) { // create a table model that
                                                                                         // includes the check box
                                                                                         // column

            public Class<?> getColumnClass(int columnIndex) { // Allows for the check box to show as a check box and not
                                                              // the string
                if (columnIndex == 0) {
                    return Boolean.class; // makes the first column into a check box
                }
                return String.class; // makes all other column into string
            }

        };

        Bookingtable = new JTable(model); // adding the model to the table this inclueds the data and its headings

        JScrollPane scrollPane = new JScrollPane(Bookingtable); // declaring the pane and add the table to the pane
        BookingFrame.add(scrollPane, BorderLayout.CENTER); // add the pane to the frame

        JPanel buttonPanel = new JPanel(); // declaring a new panel button panel that will keep the button to avoid
                                           // overlapping
        buttonPanel.setLayout(new FlowLayout());

        JButton bookButton = new JButton("Book Equipment"); // declared a button
        bookButton.addActionListener(new ActionListener() { // what the button does
            public void actionPerformed(ActionEvent e) {

                String timebookedfor = tbookedField.getText();
                String bookdurartion = timeBookedField.getText();
                int selectedb = Bookingtable.getSelectedRow();

                if (selectedb != -1) {
                    DefaultTableModel model = (DefaultTableModel) Bookingtable.getModel();
                    String bookstat = (String) model.getValueAt(selectedb, 7);

                    if ("Booked".equals(bookstat)) { // check if the column is already booked
                        JOptionPane.showMessageDialog(null, "Booking not possible");
                    }

                    model.setValueAt("Booked", selectedb, 7);

                }

                if (timebookedfor.isEmpty() && bookdurartion.isEmpty()) {

                    JOptionPane.showMessageDialog(BookingFrame, "Please Enter a booking time", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Iterate through the rows to get the selected data and move them to the other
                // file
                for (int i = model.getRowCount() - 1; i >= 0; i--) {
                    Boolean isSelected = (Boolean) model.getValueAt(i, 0);
                    if (isSelected != null && isSelected) {
                        try {
                            String equipID = (String) model.getValueAt(i, 1);
                            String equipNam = (String) model.getValueAt(i, 2);
                            String serialNum = (String) model.getValueAt(i, 3);
                            String status = (String) model.getValueAt(i, 4);
                            String descript = (String) model.getValueAt(i, 5);
                            String bookTime = (String) model.getValueAt(i, 6);

                            // updateToFile("C:\\Users\\starg\\Downloads\\Device-Distribution-Project-main\\Java
                            // Project\\src\\BookedEquipment.dat");

                            try (BufferedWriter writetoFile = new BufferedWriter(new FileWriter(FILE_PATH_2, true))) {
                                writetoFile.write(equipID + "," + equipNam + "," + serialNum + "," + status + ","
                                        + descript + "," + bookTime + "," + teacherEmail);
                                writetoFile.newLine();

                            }

                            // model.removeRow(i);

                            updateToFile(FILE_PATH_1);

                        } catch (IOException ep) {
                            ep.printStackTrace();
                            System.err.println("Error with adding and removing from file");
                        }

                    }

                }

            }
        });

        JButton AbTime = new JButton("Add Booking Time"); // declearing the button
        AbTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTime();

            }
        });

        JButton closebutton = new JButton("Close"); // declaring close button
        closebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    updateToFile(FILE_PATH_1);
                } catch (IOException ep) {
                    ep.printStackTrace();
                }

                BookingFrame.setVisible(false); // setting visablity to false making the not visiable
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int isselected = Bookingtable.getSelectedRow();
                if (isselected != -1) {
                    DefaultTableModel model = (DefaultTableModel) Bookingtable.getModel();

                    model.setValueAt(null, isselected, 6);

                    JOptionPane.showMessageDialog(BookingFrame, "Equipment Sucessfullt edited");
                } else {
                    JOptionPane.showMessageDialog(BookingFrame, "Select the equipment data you want to edit", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JButton delButton = new JButton("Delete Booking");
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent b) {
                int selectrow = Bookingtable.getSelectedRow();
                if (selectrow != -1) {
                    DefaultTableModel model = (DefaultTableModel) Bookingtable.getModel();
                    String equipID = (String) model.getValueAt(selectrow, 1); // getting data from the table model
                    String equipNam = (String) model.getValueAt(selectrow, 2);
                    String serialNum = (String) model.getValueAt(selectrow, 3);
                    String status = (String) model.getValueAt(selectrow, 4);
                    String descript = (String) model.getValueAt(selectrow, 5);
                    String bookTime = (String) model.getValueAt(selectrow, 6);

                    String Bdata = equipID + "," + equipNam + "," + serialNum + "," + status + "," + descript + ","
                            + bookTime + "," + teacherEmail; // formating the data so that it matches how its saved in
                                                             // the file

                    try {
                        File check = new File(FILE_PATH_2);
                        List<String> lines = Files.readAllLines(check.toPath()); // read all lined from the file

                        boolean isfound = false;

                        List<String> newLines = new ArrayList<>(); // list used to store data that will not be deleted

                        // iterate through the lines to find the matching lines as selected
                        for (String line : lines) {
                            if (!line.equals(Bdata)) {
                                newLines.add(line); // keep the lines that doesnt match and saves them into the array
                            } else {
                                isfound = true;
                            }
                        }

                        // update the file when th selected booking data was found
                        if (isfound) {
                            Files.write(check.toPath(), newLines, StandardOpenOption.WRITE,
                                    StandardOpenOption.TRUNCATE_EXISTING);
                            JOptionPane.showMessageDialog(BookingFrame, "Booking deleted successful");

                            model.setValueAt(null, selectrow, 7);

                            updateToFile(FILE_PATH_1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Booking not found in the file");
                        }

                    } catch (Exception ej) {
                        ej.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Please select the booking you would like to delete");
                    }

                }

            }
        });

        // adding the button to the panel
        buttonPanel.add(AbTime);
        buttonPanel.add(bookButton);
        buttonPanel.add(editButton);
        buttonPanel.add(closebutton);
        buttonPanel.add(delButton);

        BookingFrame.add(buttonPanel, BorderLayout.SOUTH);// adding the button planel to the frame and putting it at the
                                                          // bottom/south

        BookingFrame.setVisible(true); // finalized the frame and allows it to be visable
        BookingFrame.pack(); // allows the frame to fit

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to update Equipment.dat file with the new table information
    private void updateToFile(String filename) throws IOException {
        ArrayList<String[]> allData = new ArrayList<>(); // create a list to store all information row

        DefaultTableModel model = (DefaultTableModel) Bookingtable.getModel(); // getting the default table model
        for (int i = 0; i < model.getRowCount(); i++) {
            String equipID = (String) model.getValueAt(i, 1); // getting data from the table model
            String equipNam = (String) model.getValueAt(i, 2);
            String serialNum = (String) model.getValueAt(i, 3);
            String status = (String) model.getValueAt(i, 4);
            String descript = (String) model.getValueAt(i, 5);
            String bookTime = (String) model.getValueAt(i, 6);
            String bookstat = (String) model.getValueAt(i, 7);

            allData.add(new String[] { equipID, equipNam, serialNum, status, descript, bookTime, bookstat }); // adding
                                                                                                              // the
                                                                                                              // data to
                                                                                                              // the
                                                                                                              // arrayList
        }
        // Rewriting the infromation for the table with the updated data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String[] row : allData) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }

    }

    private Object[][] loadDataFromFile(String filename) {
        List<Object[]> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] value = line.split(","); // Split the line by commas
                if (value.length > 0) { // Ensure the line is not empty
                    dataList.add(value); // Add the parsed data to the list
                }
            }

            // Check if the file is empty
            if (dataList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "The file is empty: " + filename, "Data Error",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from file: " + filename, "File Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected error occurred while loading the file: " + filename,
                    "Unexpected Error", JOptionPane.ERROR_MESSAGE);
        }

        // Ensure the array is not empty
        if (dataList.isEmpty()) {
            return new Object[0][0]; // Return an empty array if no data is loaded
        }

        Object[][] data = new Object[dataList.size()][];
        dataList.toArray(data);
        return data;
    }

    // Method to add the booking time to the table
    private void addTime() {
        JFrame adFrame = new JFrame("Add Booking Time"); // Creating the frame for this option
        adFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        inputPanel.add(new JLabel("Time booked for the equipment"));
        tbookedField = new JTextField();
        inputPanel.add(tbookedField);

        inputPanel.add(new JLabel("How long equipment was booked for"));
        timeBookedField = new JTextField();
        inputPanel.add(timeBookedField);

        adFrame.add(inputPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Time");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String timebooked = tbookedField.getText();
                String durartion = timeBookedField.getText();
                emailNotify.scheduleEmailFromDuration(teacherEmail, durartion);

                if (timebooked.isEmpty() && durartion.isEmpty()) {
                    JOptionPane.showMessageDialog(adFrame, "Please Enter a booking time and duration", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int selectRow = Bookingtable.getSelectedRow();
                if (selectRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) Bookingtable.getModel();
                    model.setValueAt(timebooked + " " + durartion, selectRow, 6);

                    JOptionPane.showMessageDialog(adFrame, "Booking Time Saved:" + timebooked + "," + durartion);
                    adFrame.dispose();

                } else {
                    JOptionPane.showMessageDialog(adFrame, "Please Select a row to add booking time");

                }

            }
        });

        adFrame.add(saveButton, BorderLayout.SOUTH);
        adFrame.pack();
        adFrame.setVisible(true);

    }
}

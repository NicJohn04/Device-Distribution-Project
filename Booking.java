import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Booking extends JFrame {
    
    private JTable Bookingtable;
    private JTextField tbookedField;
    private JTextField timeBookedField;


    public Booking(){
        JFrame BookingFrame =new JFrame("Book Equipment"); //the frame of booking 
        BookingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // seting the frame to close once you click on close
        BookingFrame.setLocationRelativeTo(null);
        BookingFrame.setLayout(new BorderLayout());

        String[] columnNames = {"Select", "Equipment ID", "Equipment Name", "Serial Number", "Status", "Description", "Bookin Time"}; // column names for the table

        Object[][] data = loadDataFromFile("C:\\Users\\starg\\Downloads\\SoftwareProject\\Equipment.dat"); //load data from file to store in a veriable which builds the table

        Object[][] dataWithCheckbox = new Object[data.length][data[0].length + 1]; //add the check box column 
        for(int i = 0; i< data.length; i++){
            dataWithCheckbox[i][0] = Boolean.FALSE; //seting the chech box to false allowing it to be checked 
            System.arraycopy(data[i], 0, dataWithCheckbox[i], 1, data[i].length); //copy the data
        }

        DefaultTableModel model = new DefaultTableModel(dataWithCheckbox, columnNames){ //create a table model that includes the check box column
            
            public Class<?> getColumnClass(int columnIndex){ // Allows for the check box to show as a check box and not the string
                if (columnIndex == 0){
                    return Boolean.class; //makes the first column into a check box
                }
                return String.class; // makes all other column into string
            }
            

        };

        Bookingtable = new JTable(model); // adding the model to the table this inclueds the data and its headings

        JScrollPane scrollPane = new JScrollPane(Bookingtable); //declaring the pane and add the table to the pane
        BookingFrame.add(scrollPane, BorderLayout.CENTER); //add the pane to the frame

        JPanel buttonPanel = new JPanel(); //declaring a new panel button panel that will keep the button to avoid overlapping
        buttonPanel.setLayout(new FlowLayout());

        JButton bookButton = new JButton("Book Equipment"); // declared a button
        bookButton.addActionListener(new ActionListener() { //what the button does 
            public void actionPerformed(ActionEvent e){

                String timebookedfor = tbookedField.getText();
                String bookdurartion = timeBookedField.getText();

                if(timebookedfor.isEmpty() && bookdurartion.isEmpty()){
                    JOptionPane.showMessageDialog(BookingFrame, "Please Enter a booking time", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Iterate through the rows to get the selected data and move them to the other file
                for(int i = model.getRowCount() -1; i>=0; i--){
                    Boolean isSelected = (Boolean) model.getValueAt(i, 0);
                    if(isSelected != null && isSelected){
                        try {
                            String equipID = (String) model.getValueAt(i, 1);
                            String equipNam = (String) model.getValueAt(i, 2);
                            String serialNum = (String) model.getValueAt(i, 3);
                            String status = (String) model.getValueAt(i, 4);
                            String descript = (String) model.getValueAt(i, 5);
                            String bookTime = (String) model.getValueAt(i, 6);

                            //updateToFile("C:\\Users\\starg\\Downloads\\SoftwareProject\\Equipment.dat");

                            try(BufferedWriter writetoFile = new BufferedWriter(new FileWriter("C:\\Users\\starg\\Downloads\\SoftwareProject\\BookedEquipment.dat", true))){
                                writetoFile.write(equipID +","+ equipNam +","+serialNum +","+ status +","+ descript + "," + bookTime);
                                writetoFile.newLine();

                            }

                            model.removeRow(i);

                            updateToFile("C:\\Users\\starg\\Downloads\\SoftwareProject\\Equipment.dat");

                        } catch (IOException ep) {
                            ep.printStackTrace();
                            System.err.println("Error with adding and removing from file");
                        }
                    
                    }

                }
                
            }
        });


        JButton AbTime = new JButton("Add Booking Time"); //declearing the button 
        AbTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                addTime ();

            }
        });

        JButton closebutton = new JButton("Close"); //declaring close button
        closebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                BookingFrame.setVisible(false); // setting visablity to false making the not visiable
            }
        });

        //adding the button to the panel
        buttonPanel.add(AbTime); 
        buttonPanel.add(bookButton);
        buttonPanel.add(closebutton);

        BookingFrame.add(buttonPanel, BorderLayout.SOUTH);//adding the button planel to the frame and putting it at the bottom/south


        BookingFrame.setVisible(true); // finalized the frame and allows it to be visable
        BookingFrame.pack(); //allows the frame to fit 

    }
    //Method to update Equipment.dat file with the new table information
    private void updateToFile(String filename)throws IOException{
        ArrayList<String[]> allData = new ArrayList<>(); //create a list to store all information row

        DefaultTableModel model = (DefaultTableModel) Bookingtable.getModel(); // getting the default table model
        for(int i = 0; i<model.getRowCount(); i++){
            String equipID = (String) model.getValueAt(i, 1); //getting data from the table model
            String equipNam = (String) model.getValueAt(i, 2);
            String serialNum = (String) model.getValueAt(i, 3);
            String status = (String) model.getValueAt(i, 4);
            String descript = (String) model.getValueAt(i, 5);
            String bookTime = (String) model.getValueAt(i, 6);

            allData.add(new String[]{equipID, equipNam, serialNum, status, descript, bookTime}); //adding the data to the arrayList
        }
        //Rewriting the infromation for the table with the updated data
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(String[] row: allData){
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }

    }

    private Object[][] loadDataFromFile (String filename){ //this get's the data from the file 
        List<Object[]> dataList = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){ //reads through the information in the file
            String line;
            while((line = br.readLine()) != null){
                String[] value = line.split(","); //split the file od commas 
                dataList.add(value); // add the new infromation from spliting to the array list
            }
        }catch(IOException e){
            e.printStackTrace();
            
        }

        Object[][] data = new Object[dataList.size()][]; //converts the list to a 2d array
        dataList.toArray(data);
        return data;

    }

    // Method to add the booking time to the table
    private void addTime(){
        JFrame adFrame = new JFrame("Add Booking Time"); //Creating the frame for this option
        adFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2,2));

        inputPanel.add(new JLabel("Time booked for the equipment"));
        tbookedField = new JTextField();
        inputPanel.add(tbookedField);

        inputPanel.add(new JLabel("How long equipment was booked for"));
        timeBookedField = new JTextField();
        inputPanel.add(timeBookedField);

        adFrame.add(inputPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Time");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String timebooked = tbookedField.getText();
                String durartion = timeBookedField.getText();

                if(timebooked.isEmpty() && durartion.isEmpty()){
                    JOptionPane.showMessageDialog(adFrame, "Please Enter a booking time and duration", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int selectRow = Bookingtable.getSelectedRow();
                if(selectRow != -1){
                    DefaultTableModel model = (DefaultTableModel) Bookingtable.getModel();
                    model.setValueAt(timebooked + "," + durartion, selectRow, 6);

                    JOptionPane.showMessageDialog(adFrame, "Booking Time Saved:" + timebooked +  "," + durartion);
                    adFrame.dispose();

                }else{
                    JOptionPane.showMessageDialog(adFrame, "Please Select a row to add booking time");
                    
                }
                
            }
        });

        adFrame.add(saveButton, BorderLayout.SOUTH);
        adFrame.pack();
        adFrame.setVisible(true);

    }


    //public static void main(String[] args) {
        //new Booking();
    //}

    
}

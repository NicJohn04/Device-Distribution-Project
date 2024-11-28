import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherFileManager {
    private static final Logger LOGGER = Logger.getLogger(TeacherFileManager.class.getName());
    // File to save the inventory data
    //private static final String FILE_PATH = "teachers.csv";
    private static final String FILE_PATH = "C:\\Users\\hriet\\OneDrive - The University of the West Indies, Mona Campus\\Documents\\School work\\UWI COURSES\\COMP2140\\Device-Distribution-Project-main\\Device-Distribution-Project-main\\Java Project\\src\\teachers.csv";
    private static final String DAT_FILE_PATH = "C:\\Users\\hriet\\OneDrive - The University of the West Indies, Mona Campus\\Documents\\School work\\UWI COURSES\\COMP2140\\Device-Distribution-Project-main\\Device-Distribution-Project-main\\Java Project\\src\\teachers.dat";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Save teachers to a CSV file
     * @param teachers List of teachers to save
     */
    public static void saveTeachersToFile(List<Teacher> teachers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write CSV header
            writer.write("TeacherId,Name,Email,Password,ContactNumber,Role,Status,DateCreated,LastUpdated");
            writer.newLine();

            // Write each teacher's data
            for (Teacher teacher : teachers) {
                String teacherData = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s",
                    teacher.getTeacherId(),
                    escapeCSV(teacher.getName()),
                    escapeCSV(teacher.getEmail()),
                    escapeCSV(teacher.getPasswordHash()),
                    escapeCSV(teacher.getContactNumber()),
                    escapeCSV(teacher.getRole()),
                    teacher.getStatus().name(),
                    teacher.getDateCreated().format(DATE_FORMATTER),
                    teacher.getLastUpdated().format(DATE_FORMATTER)
                    
                    
                );
                writer.write(teacherData);
                writer.newLine();
            }
            LOGGER.info("Teachers saved to file successfully.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving teachers to file", e);
        }

        // Save to .dat file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DAT_FILE_PATH))) {
            oos.writeObject(teachers);
            LOGGER.info("Teachers saved to .dat file successfully.");
        }   catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving teachers to .dat file", e);
        }   
    }

    /**
     * Load teachers from a CSV file
     * @return List of teachers loaded from file
     */
    public static List<Teacher> loadTeachersFromFile() {
        List<Teacher> teachers = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return teachers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header line
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 9) {
                    Teacher teacher = reconstructTeacher(fields);
                    teachers.add(teacher);
                }
            }
            LOGGER.info("Teachers loaded from file successfully.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading teachers from file", e);
        }

        return teachers;
    }


    

    /**
     * Reconstruct a Teacher object from CSV fields
     * @param fields Array of field values from CSV
     * @return Reconstructed Teacher object
     */
    private static Teacher reconstructTeacher(String[] fields) {
        try {
            int teacherId = Integer.parseInt(fields[0]);
            String name = unescapeCSV(fields[1]);
            String email = unescapeCSV(fields[2]);
            String password = fields[3];
            String contactNumber = unescapeCSV(fields[4]);
            String role = unescapeCSV(fields[5]);

            // Create teacher with basic constructor
            Teacher teacher = new Teacher(teacherId, name, email, contactNumber, role, "Temp_pwd");
            
           // Safe parsing of status
            Teacher.TeacherStatus status = Teacher.TeacherStatus.ACTIVE; // Default to ACTIVE
            try {
                // Only try to parse if the status field is a valid enum value
                if (fields[5] != null && !fields[6].isEmpty()) {
                    status = Teacher.TeacherStatus.valueOf(fields[6].trim());
                }
            } catch (IllegalArgumentException e) {
                // Log the error, but default to ACTIVE status
                LOGGER.log(Level.WARNING, "Invalid teacher status found: " + fields[6] + ". Defaulting to ACTIVE.");
            }
            teacher.setStatus(status);
            
            // Use reflection to set private date fields
            Field dateCreatedField = Teacher.class.getDeclaredField("dateCreated");
            dateCreatedField.setAccessible(true);
            dateCreatedField.set(teacher, LocalDateTime.parse(fields[7], DATE_FORMATTER));
            
            Field lastUpdatedField = Teacher.class.getDeclaredField("lastUpdated");
            lastUpdatedField.setAccessible(true);
            lastUpdatedField.set(teacher, LocalDateTime.parse(fields[8], DATE_FORMATTER));


            //Field passwordHashField = Teacher.class.getDeclaredField("passwordHash");
            //passwordHashField.setAccessible(true);
            //passwordHashField.set(teacher, fields.length > 9 ? fields[9] : "");

            return teacher;
        } catch (NoSuchFieldException | IllegalAccessException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error reconstructing teacher", e);
            return null;
        }
    }

    /**
     * Escape special characters in CSV
     * @param value String to escape
     * @return Escaped string
     */
    private static String escapeCSV(String value) {
        if (value == null) return "";
        // Replace commas with space, and handle null
        return value.replace(",", " ").replace("\n", " ").replace("\r", " ");
    }

    /**
     * Unescape CSV value
     * @param value String to unescape
     * @return Unescaped string
     */
    private static String unescapeCSV(String value) {
        return value.trim();
    }
}





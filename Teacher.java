import java.time.LocalDateTime;
import java.util.Base64;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Teacher implements Serializable {
    // Enum for Teacher Status
    public enum TeacherStatus {
        ACTIVE, INACTIVE
    }

    // Teacher attributes
    private int teacherId;
    private String name;
    private String email;
    private String contactNumber;
    private String role;
    private TeacherStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private String passwordHash;
    private String actualPassword;

    // Constructor function 
    public Teacher(int teacherId, String name, String email, String contactNumber, String role, String password) {
        this.teacherId = teacherId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.role = role;
        this.status = TeacherStatus.ACTIVE;
        this.dateCreated = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
        this.actualPassword = password;
        this.passwordHash = hashPassword(password);
    }

    private static final long serialVersionUID = 1L; //Updated serialization version


    //Method for checking password
    public boolean checkPassword(String inputPassword){
        return this.passwordHash.equals(hashPassword(inputPassword));
        //return this.passwordHash.equals(hashPassword(inputPassword));
    }

    //change password
    public void changePassword(String newPassword){
        this.passwordHash = hashPassword(newPassword);
        this.actualPassword = newPassword;
        //this.passwordHash = hashPassword(newPassword);
        updateLastUpdated();
    }


    //Helper method that will hash passwords
    private String hashPassword(String password){
        
        try{
            MessageDigest msgdgt = MessageDigest.getInstance("SHA-256");
            byte[] hasedBytes = msgdgt.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hasedBytes);
            //return password;
        }
        catch (Exception e){
            return String.valueOf(password.hashCode());
        }
    }



    // Getter functions 
    public int getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getRole() {
        return role;
    }

    public TeacherStatus getStatus() {
        return status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getActualPassword(){
        return actualPassword;
    }

    // Setter functions
    public void setName(String name) {
        this.name = name;
        updateLastUpdated();
    }

    public void setEmail(String email) {
        this.email = email;
        updateLastUpdated();
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        updateLastUpdated();
    }

    public void setRole(String role) {
        this.role = role;
        updateLastUpdated();
    }

    public void setStatus(TeacherStatus status) {
        this.status = status;
        updateLastUpdated();
    }

    public void setPassword(String passwrd){
        this.passwordHash = passwrd;
        updateLastUpdated();
    }

    // Helper method to update lastUpdated timestamp
    private void updateLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }
}

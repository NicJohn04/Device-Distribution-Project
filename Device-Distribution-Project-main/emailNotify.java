import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.TimerTask;
import java.util.Timer;


public class emailNotify {
    public static void sendEmail(String recipientEmail) {
        // Email credentials for sender
        final String username = "matthewkacross@gmail.com";
        final String password = "kbgc qeay ttjq qtqd"; //Uses an App Password to get around 2FA-verification everytime

        // Set up email server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS

        // Create a session with the email server properties to send email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Generates a message to be sent and who it is to be sent to
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("REMINDER: 5 Minutes Remaining!");
            message.setText("Hello, you have 5 minutes remaining until the equipment borrowed needs to be returned to the Lab ");

            
            Transport.send(message); // Sends the message
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }  
    }

     public static void scheduleEmail(String recipientEmail, long durationMillis) {
        // Calculates the time to send the email which is 5 minutes before the end of the duration
        long fiveMinsBefore = durationMillis - (5 * 60 * 1000); // 5 minutes = 5 * 60 * 1000 milliseconds

        // Schedule a task to run at the calculated time
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Calls the sendEmail method to send the email when the timer runs
                sendEmail(recipientEmail);
            }
        }, fiveMinsBefore); // Schedule to run 5 minutes before the duration
    }

    // Method to get the duration from the user input in Booking and schedule the email
    public static void scheduleEmailFromDuration(String recipientEmail, String durationText) {
        try {
            // Convert the entered duration to long (in minutes)
            long durationMinutes = Long.parseLong(durationText);

            // Convert the duration to milliseconds
            long durationMillis = durationMinutes * 60 * 1000;

            // Schedule the email to be sent 5 minutes before the duration is up
            scheduleEmail(recipientEmail, durationMillis);
            System.out.println("Email will be sent 5 minutes before the duration ends.");

        } catch (NumberFormatException ex) {
            System.out.println("Invalid duration entered. Please enter a valid number.");
        }
    }
}

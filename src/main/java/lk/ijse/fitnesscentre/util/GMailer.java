package lk.ijse.fitnesscentre.util;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GMailer {

//    private static final String TEST_EMAIL = "fitlifeifms@gmail.com";
//    private static Gmail service = null;

//    public GMailer() throws Exception {
//        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//        service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
//                .setApplicationName("Test Mailer")
//                .build();
//    }
//
//    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory) throws IOException {
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(GMailer.class.getResourceAsStream("/client_secrets.json")));
//
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
//                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
//                .setAccessType("offline")
//                .build();
//
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public static void sendMail(String subject, String message) throws Exception {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//        MimeMessage email = new MimeMessage(session);
//        email.setFrom(new InternetAddress(TEST_EMAIL));
//        email.addRecipient(TO, new InternetAddress(TEST_EMAIL));
//        email.setSubject(subject);
//        email.setText(message);
//
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        email.writeTo(buffer);
//        byte[] rawMessageBytes = buffer.toByteArray();
//        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
//        Message msg = new Message();
//        msg.setRaw(encodedEmail);
//
//        try {
//            msg = service.users().messages().send("me", msg).execute();
//            System.out.println("Message id: " + msg.getId());
//            System.out.println(msg.toPrettyString());
//        } catch (GoogleJsonResponseException e) {
//            GoogleJsonError error = e.getDetails();
//            if (error.getCode() == 403) {
//                System.err.println("Unable to send message: " + e.getDetails());
//            } else {
//                throw e;
//            }
//        }
//    }

//        int otp = generateOTP();
//
//        String msg = "<div style='border:1px solid #e2e2e2; padding:20px'>"
//                + "<h3>"
//                + "We received a request to get OTP Code "
//                + "<br>"
//                + "Your OTP Code is , "
//                + "<br>"
//                + "</h3>"
//                + "<p>"
//                + "<center>"
//                + "<h1>"
//                + "<b>"
//                + otp
//                + "</b>"
//                + "</h1>"
//                + "</center>"
//                + "</p>"
//                + "Use this OTP to gain Access ."
//                + "</div>";

    public static boolean sendEmail(String recipientEmail) {
        // Sender's email and password
        final String senderEmail = "fitlifeifms@gmail.com";
        final String password = "flpe urli iofh ecja"; // Replace with your Gmail password or app-specific password

        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        session.setDebug(true); // Enable debug mode for detailed logs

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set email subject
            message.setSubject("Email Verification");

            // Generate OTP and set it in the email content
            String otp = generateOTP();
            message.setText("Your OTP code is: " + otp);

            // Send the message
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            // Display error message
            System.out.println("Failed to send email: " + e.getMessage());
            return false;
        }
    }

    public static String generateOTP() {
        // Generate a 6-digit OTP
        int otp = (int) ((Math.random() * 900000) + 100000);
        return String.valueOf(otp);
    }

}

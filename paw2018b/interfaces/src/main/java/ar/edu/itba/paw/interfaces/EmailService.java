package ar.edu.itba.paw.interfaces;

public interface EmailService {

   void sendEmail(String to, String subject, String text);

   public String prepareConfirmationMessage(String name);

   void sendMessageWithAttachment(String name, String to, String subject);
}

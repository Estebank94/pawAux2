package ar.edu.itba.paw.interfaces.services;

public interface EmailService {

   void sendEmail(String to, String subject, String text);

   public String prepareConfirmationMessage(String name);

   void sendMessageWithAttachment(String name, String to, String subject);
}

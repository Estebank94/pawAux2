package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender jms;


    @Async
    @Override
    public void sendEmail(String recipient, String body, String subject) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(recipient);
        mail.setSubject(subject);
        mail.setText(body);
        jms.send(mail);
    }

    @Override
    public String prepareConfirmationMessage(String name) {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><body>")
                .append("<head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
                .append("<link href=\"https://fonts.googleapis.com/css?family=Rubik\" rel=\"stylesheet\">")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>")
                .append("</head>")
                .append("<body style=\"margin: 0;	padding: 0;\">")
                .append("<table style=\"border-spacing: 0; padding: 0; border: 0; width: 100%;\">")
                .append("<tr>")
                .append("<td style=\"padding: 10px 0 30px 0;\">")
                .append("<table style=\"border: 1px solid #257CBF; border-collapse: collapse; width: 600px;	margin-left:auto; margin-right:auto; border-spacing: 0;	padding: 0;\">")
                .append("<tr>")
                .append("<td style=\"padding: 30px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: 'Rubik', sans-serif;\" align=\"center\" bgcolor=\"#257CBF\" >")
                .append("<img style=\"display: block;\" src=\"https://i.imgur.com/DRVyjj1.png\" alt=\"Waldoc\" width=\"150\" height=\"50\"  />")
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">")
                .append("<table style=\"border-spacing: 0; padding: 0; border: 0; width: 100%;\">")
                .append("<tr>")
                .append("<td style=\"color: #153643; font-family: 'Rubik', sans-serif; font-size: 24px;\">")
                .append("<b>Bienvenido ")
                .append(name)
                .append(" ")
                .append("a Waldoc!</b>")
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td style=\"padding: 20px 0 10px 0; color: #153643; font-family: 'Rubik', sans-serif; font-size: 15px; line-height: 20px;\">")
                .append("Waldoc, la manera más fácil de sacar turnos con tus especialistas. ")
                .append("<br>")
                .append("Comenzá tu experiencia ahora: ")
                .append("<a href=\"http://pawserver.it.itba.edu.ar/paw-2018b-06/\">")
                .append("Haz click aquí")
                .append("</a>")
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td style=\"padding: 5px 0 15px 0; color: #257CBF; font-family: 'Rubik', sans-serif; font-size: 15px; line-height: 20px;\">")
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td style=\"font-style: italic; font-family: 'Rubik', sans-serif; color: #999; font-size: 15px;\">")
                .append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td bgcolor=\"#257CBF\" style=\"padding: 30px 30px 30px 30px;\">")
                .append("<table style=\"border-spacing: 0; padding: 0; border: 0; width: 100%;\">")
                .append("<tr>")
                .append("<td style=\"color: #ffffff; font-family: 'Rubik', sans-serif; font-size: 14px;\" width=\"75%\">")
                .append("&reg; Waldoc, 2018<br/>")
                .append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("</td>")
                .append("</body>")
                .append("</html>");
        return html.toString();
    }

//    public String prepareAppointmentMessage(){
//        StringBuilder html = new StringBuilder();
//        html.append("<!DOCTYPE html>")
//        .append("<head>")
//        .append("<meta charset=\"utf-8\">")
//
//
//    <title>Waldoc</title>
//
//</head>
//<body class="body-background">
//<nav class="navbar navbar-dark" style="background-color: #257CBF; padding-bottom: 0px;">
//    <div class="container">
//        <a class="navbar-brand" href="<c:url value="/"/>">
//            <h1><strong>Waldoc</strong></h1>
//        </a>
//    </div>
//</nav>
//
//<div class="outer-div">
//    <div class="inner-div card">
//        <p style="text-align: center;"><i style="color: #02bf02; font-size: 64px; margin-top:64px;" class="fas fa-check-circle"></i></p>
//        <h2 style="text-align: center;">¡Turno Reservado!</h2>
//        < style="text-align: center; margin-left:16px; margin-right:16px;">Te hemos reservado un turno con <c:out value="${doctor.firstName}"/> <c:out value="${doctor.lastName}"/> el <c:out value="${appointmentDay}"/> a las <c:out value="${appointmentTime}"/>.<p>
//        <br>
//        <button style="margin-bottom:64px;" class="btn btn-primary custom-btn center-horiz" type="button" onclick="window.location='<c:url value="/"/>'">
//                Volver al Inicio
//        </button>
//    </div>
//</div>
//    }

    @Override
    public void sendMessageWithAttachment(String name, String to, String subject) {


        String message = prepareConfirmationMessage(name);

        MimeMessage email = jms.createMimeMessage();

        try{email.setSubject(subject);
            email.setSubject("Contact message");
            email.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            email.setContent(message, "text/html; charset=utf-8");}
        catch (MessagingException e){}

        jms.send(email);
    }

}
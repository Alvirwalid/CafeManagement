package com.inn.cafe.utils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class EmailSenderUtils {
    @Autowired
private  final  JavaMailSender mailSender;

//    public EmailSenderUtils(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }



    public  void  sendEmail(String toEmail, String subject, String body, List<String> list){
        SimpleMailMessage message=new SimpleMailMessage();



        message.setFrom("alviarash620@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);

        message.setText(body);

        if(list !=null && list.size()>0){
            message.setCc(getCcArray(list));
        }

        mailSender.send(message);

    }

    private  String[] getCcArray(List<String>ccList){
        String[] cc=new String[ccList.size()];
        for (int i = 0; i<ccList.size(); i++){

            System.out.println(ccList.get(i));
            cc[i]= ccList.get(i);
        }
        return  cc;
    }

    public  void  forgotMail(String to,String subject,String password)throws MessagingException{

        MimeMessage message= mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setFrom("alviarash620@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg,"text/html");
        mailSender.send(message);

    }



}

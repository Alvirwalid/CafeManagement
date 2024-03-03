package com.inn.cafe.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EmailSenderService {
    @Autowired
private  final  JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }



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



}

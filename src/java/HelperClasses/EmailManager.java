/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelperClasses;

import java.io.File;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class EmailManager {
    static String host = "127.0.0.1";
    static String port = "1025";
    static String ssl_enabled = "true";
    static String Auth = "true";
    public static void sentEmail(String from,String to,String subject,String message,boolean attachment,String attachmentPath){
        Properties properties=System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port",port);
        properties.put("mail.smtp.ssl.enable","false");
        properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.smtp.auth","false");
        //properties.put("mail.smtp.connectiontimeout", "5000"); // 5 seconds
        //properties.put("mail.smtp.timeout", "5000"); // 5 seconds
        //properties.put("mail.smtp.writetimeout", "5000"); // 5 seconds

        //properties.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io");
        
        System.out.println("TO : "+to+" From : "+from+" Subject : "+subject);
        //step 1 get the session object
        Session session=Session.getInstance(properties,null);/* new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
             
                return new PasswordAuthentication("2273437e6be35c","*8d8b95317d5ff2");
            }
            
        });*/
        
        session.setDebug(true);
        
        //step 2 set message
        MimeMessage mimeMessage=new MimeMessage(session);
        try{
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            mimeMessage.setSubject(subject);
            if(attachment){
                //if attachemt is attach
                MimeMultipart mimeMultipart=new MimeMultipart();
                
                MimeBodyPart text=new MimeBodyPart();
                MimeBodyPart file=new MimeBodyPart();
                try{
                    text.setText(message);
                    file.attachFile(new File(attachmentPath));
                    mimeMultipart.addBodyPart(text);
                    mimeMultipart.addBodyPart(file);
                    //set tyhe message
                    mimeMessage.setContent(mimeMultipart);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            }else{
                //simple text message
               mimeMessage.setContent(message,"text/html");  
            }
           
            
              
        //step 3 sent Message
        Transport.send(mimeMessage);
        System.out.println("Message successfuly sent........");
    
        }catch(Exception e){
            System.out.println("message not sent");
            e.printStackTrace();
        }
    }    
}

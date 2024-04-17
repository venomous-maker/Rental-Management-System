/*
 * This servlet manage the registration,login and logout task...
 */
package Servlet;

import HelperClasses.EmailManager;
import HelperClasses.EntityHelper;
import HelperClasses.Helper;
import entities.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.sql.*;
import HelperClasses.DatabaseHandler;

@MultipartConfig
public class LoginRegisterServlet extends HttpServlet{
    HttpSession httpSession;
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
        res.setContentType("text/html;charset=UTF-8");
        String formType=req.getParameter("form_type");
        httpSession=req.getSession();
        System.out.println(" formtype : "+formType);
        String path=req.getRealPath("files"+File.separator+"images"+File.separator+"UserImg");
        //String path=".."+File.separator+".."+File.separator+".."+File.separator+"files" + File.separator + "images" + File.separator + "UserImg";
        PrintWriter out=res.getWriter();
        if(formType.equalsIgnoreCase("messageForm")){
            String email=req.getParameter("email");
            String name=req.getParameter("name");
            String comment=req.getParameter("comment");
            try{
               String from=email;
                String to="rentmanagement@gmail.com";
                String subject="Rent Management Message";
                String message="<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>User to Admin Message</title>\n" +
                            "</head>\n" +
                            "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">\n" +
                            "\n" +
                            "    <div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px;\">\n" +
                            "        <h2 style=\"color: #333333;\">Message from User</h2>\n" +
                            "        <p style=\"color: #666666;\"><strong>Name:</strong> "+name+"</p>\n" +
                            "        <p style=\"color: #666666;\"><strong>Email:</strong> "+email+"</p>\n" +
                            "        <hr style=\"border: 0; border-top: 1px solid #dddddd;\">\n" +
                            "        <p style=\"color: #666666;\"><strong>Message:</strong></p>\n" +
                            "        <p style=\"color: #666666;\">"+comment+"</p>\n" +
                            "        <hr style=\"border: 0; border-top: 1px solid #dddddd;\">\n" +
                            "        <p style=\"color: #666666;\">Please take appropriate action.</p>\n" +
                            "        <p style=\"color: #666666;\">Best regards,<br>"+name+"</p>\n" +
                            "    </div>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";
                EmailManager.sentEmail(from, to, subject, message,false,null);
                message = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>Email Message</title>\n" +
                            "</head>\n" +
                            "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">\n" +
                            "\n" +
                            "    <div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px;\">\n" +
                            "        <h2 style=\"color: #333333;\">Hello,</h2>\n" +
                            "        <p style=\"color: #666666;\">Thank you for contacting us!</p>\n" +
                            "        <p style=\"color: #666666;\">Here's the message you sent:</p>\n" +
                            "        \n" +
                            "        <hr style=\"border: 0; border-top: 1px solid #dddddd;\">\n" +
                            "        \n" +
                            "        <p><strong>Name:</strong> "+name+"</p>\n" +
                            "        <p><strong>Email:</strong> "+email+"</p>\n" +
                            "        <p><strong>Message:</strong>"+comment+"</p>\n" +
                            "        \n" +
                            "        <p style=\"color: #666666;\">We will get back to you as soon as possible.</p>\n" +
                            "        \n" +
                            "        <p style=\"color: #666666;\">Best regards,<br>Your Company</p>\n" +
                            "    </div>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";
                EmailManager.sentEmail(to, from, "no reply response", message,false,null);
                out.print("0");
            }catch(Exception e){
                e.printStackTrace();
                out.print("1");
            }
        }
        else if(formType.equalsIgnoreCase("login")){
            //login managment is here
            String email=req.getParameter("l-email");
            String password=req.getParameter("l-password");
            String encPassword=HelperClasses.PasswordEncodeDecode.getEncodedString(password);
            //DatabaseHandler dB = new DatabaseHandler();
            List<User> userList = new ArrayList<>();
            try{
               
            userList = EntityHelper.getByQuery("From User where email='"+email+"' and password='"+encPassword+"'");
            
            System.out.println(encPassword);
            if(userList.size()==0 ){
                out.print("-1");
            }else{
                for(User user:userList){
                
                    httpSession.setAttribute("loginUser",user);
                    out.print(user.getType());
                }
            }
            }catch(Exception e){
                e.printStackTrace();
                out.print("1");
            }
        }else if(formType.equalsIgnoreCase("register")){
           //register management is here
           String name=req.getParameter("r-name");
           String email=req.getParameter("r-email");
           String phone=req.getParameter("r-phone");
           String mobile=req.getParameter("r-mobile");
           String pass1=req.getParameter("r-pass1");
           String district=req.getParameter("district");
           String tal=req.getParameter("tal");
           String addr=req.getParameter("r-addr");
           Part part=req.getPart("r-pic");
           String encPassword=HelperClasses.PasswordEncodeDecode.getEncodedString(pass1);
           User user;
           if(part.getSize()==0){
           user=new User( name.trim(),email,phone,mobile,encPassword,district.trim(),tal.trim(),addr.trim(),"default.png","normal");
           httpSession.removeAttribute("otp");
           httpSession.removeAttribute("otpEmail");
           }else{
               httpSession.removeAttribute("otp");
               httpSession.removeAttribute("otpEmail");
            Random rand=new Random();
            int number=rand.nextInt();
            String picName="user-"+number+".jpg";
            
            user=new User(name.trim(),email,phone,mobile,encPassword,district.trim(),tal.trim(),addr.trim(),picName,"normal");
            
            File file=new File(path);
            if(!file.exists()){
                file.mkdir();
            }
            Helper.saveTheImageToFolder(path+File.separator+picName, part);
           }
           try{
               List<User> uList=new ArrayList<User>();
               uList.add(user);
               out.print(EntityHelper.saveNewData(uList));

           }catch(Exception e){
               e.printStackTrace();
               out.print("-1");
           }
            
        } //if(register) End 
        else if(formType.equalsIgnoreCase("logout")){
            //logout code
            httpSession.removeAttribute("loginUser");
            httpSession.setAttribute("logoutAction","done");
            
        }else if(formType.equalsIgnoreCase("generateOtp")){
            //OTP related code
           String toEmail=req.getParameter("to");
           String otpEmail=(String)httpSession.getAttribute("otpEmail");
           boolean flag=false;
            Timer timer=new Timer();
            TimerTask task=new TimerTask(){
                public void run(){
                        httpSession.removeAttribute("otp");
                        httpSession.removeAttribute("otpEmail");
                }
            };       
            if(!toEmail.equalsIgnoreCase(otpEmail) && httpSession.getAttribute("otpEmail")!=null ){
                System.out.println("Email changed");
                httpSession.removeAttribute("otp");
                httpSession.removeAttribute("otpEmail");
                flag=true;
            }
            if(httpSession.getAttribute("otp")==null){
            Random rand=new Random();
            int otp= rand.nextInt(999999);
            httpSession.setAttribute("otp",otp);
            httpSession.setAttribute("otpEmail",toEmail);
            String to=toEmail;
            String from="rentmanagement@gmail.com";
            String subject="Rent Management OTP";
            String message="<html>Dear user ,your OTP is <b>"+otp+"</b> please not share .</html>";
            EmailManager.sentEmail(from, to, subject, message,false,null);
            if(flag)
               out.print(otp+"/removeCounter"); 
                else
            out.print(otp+"/active");
            timer.schedule(task,124000);//otp invalid when 2 min 
            }else{
                out.print(httpSession.getAttribute("otp")+" /oldCounter");
            }
            //httpSession.setMaxInactiveInterval(150);
        }
    }
 
}

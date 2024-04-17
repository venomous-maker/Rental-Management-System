/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import HelperClasses.EntityHelper;
import HelperClasses.Helper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import entities.Bill;
import entities.House;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class PaymentServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM,yyyy");
        PrintWriter out=res.getWriter();
        String data="";
        String type=req.getParameter("payment");
        if(type.equalsIgnoreCase("table")){
            int tenant=Integer.parseInt(req.getParameter("tenant"));
            int house=Integer.parseInt(req.getParameter("house"));
            List<Bill> bills=EntityHelper.getByQuery("from Bill where tenant_Tenants_id="+tenant+" and House_id="+house);
        
            data=" \n" +
"            <div class=\"container\">\n"
                    + "<h4 class='text-muted my-2'>BILL TABLE </h4>" +
"                <div class=\"\">\n" +
"                    <table class=\"table borderd table-hover\">\n" +
"                        <tr class=\"alert-secondary\">\n" +
"                            <td>Bill No</td>\n" +
"                            <td>Date</td>\n" +
"                            <td>Amount</td>\n" +
"                            <td>States</td>\n" +
"                            <td>Action</td>\n" +
"                        </tr>\n";
              for(Bill bill:bills){  
                  data+="<tr class=\"\">\n" +
"                            <td>"+bill.getId()+"</td>\n" +
"                            <td>"+sdf.format(bill.getStartDate())+"<span class='text-muted px-md-2'> To </span>"+sdf.format(bill.getEndDate())+"</td>\n" +
"                            <td>"+bill.getAmount()+"</td>\n";
                   if(bill.getStates().equalsIgnoreCase("paid")){     
                    data+="   <td class='text-success'><b>Paid</b></td>\n";
                   }else if(bill.getStates().equalsIgnoreCase("unpaid")){
                    data+="   <td class='text-danger'><b>Unpaid</b></td>\n";
                   }
                   data+=""+ 
"                            <td><button onclick='ShowInvoiceBtn(\"" +bill.getId()+"\")' class=\" btn-sm btn-primary\" >View Bill</button></td>\n" +
"                        </tr>\n";
              }
                    data+=""+
"                    \n" +
"                        \n" +
"                    </table>\n" +
"                </div>\n" +
"            </div>";
        }else if(type.equalsIgnoreCase("invoice")){
            String id=req.getParameter("billId");
          
            
            List<Bill> bills=EntityHelper.getByQuery("from Bill where id='"+id+"'");
           Bill bill=bills.get(0);
           
           
            data=""
                    + "<div class=\"container\">\n" +
"            \n" +
"            " +
"            <div class=\"pr-lg-5 pl-lg-5 pr-md-3 pl-md-3 mt-3 mb-5 \" >\n"
                    + "<div class=\"pr-md-5 text-right\">\n" +
"                           <i onclick=\"generateInvoicePDF()\" class=\"fa-muted fa fa-download\"></i>\n"
                    + "     <i class='px-1 paymentBtn fa fa-arrow-circle-left' data-tenant='"+bill.getTenant().getId()+"'  data-house='"+bill.getHouseId()+"'></i>" +
"                    </div>" +
"            <div class=\"mx-lg-4  card py-0 my-3 \" id=\"downloadInvoice\">\n" +
"                <div class=\"bg-light pt-2\">\n" +
"                    <h3 class=\"text-dark pl-5 invoice\" ><img class=\"img-fluid\" src=\"files/images/webImg/homeVector.jpg\" style=\"max-height: 65px;width:auto\"><span class=\"pl-3\"><b>INVOICE</b></span></h3>\n" +
"                </div>\n" +
"                <div class=\"card-body alert-light mb-5 px-5 mx-4\">\n" +
"                    <div class=\"row\">\n" +
"                    <div class=\"col-12 col-sm-6\">\n" +
"                        <h5>"+bill.getTenant().getHouse().getName()+"</h5>\n" +
"                        <h6 class=\"mt-3 mb-0 pb-0\"><span class=\"text-muted\">Produce Date :</span>"+sdf.format(bill.getStartDate())+"</h6>\n" +
"                        <h6 class=\"mt-0 pt-0\"><span class=\"text-muted\">Invoice No :</span>"+bill.getId()+"</h6>\n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6 text-right invoice-taxt-mobile\">\n" +
"                        <p class=\"m-0 p-0 text-muted\">"+bill.getTenant().getName()+"</p>\n" +
"                        <p class=\"m-0 p-0 text-muted\">"+bill.getTenant().getGender()+"</p>\n" +
"                        <p class=\"m-0 p-0 text-muted\">Town City</p>\n" +
"                        <p class=\"m-0 p-0 text-muted\">States : ";
                            if(bill.getStates().equalsIgnoreCase("unpaid")){
                                data+="<span class=\"text-danger\">Unpaid</span>";
                            }else if(bill.getStates().equalsIgnoreCase("Paid")){
                                data+="<span class=\"text-success\">Paid</span>";                                
                            }
                            
                        data+="</p>\n" +
"                    </div>\n" +
"                    </div>\n" +
"                    \n" +
"                    <table class=\" table mt-5 mb-0 pb-0 \">\n" +
"                        <t-head>\n" +
"                            <tr class=\"\" style=\"border-bottom:1px solid #009900 ;\">\n" +
"                                <td class=\"text-muted text-left invoice-text\">Description</td>\n" +
"                                <td class=\"text-muted text-right invoice-text\">Rate</td>\n" +
"                                <td class=\"text-muted text-right invoice-text\">Time</td>\n" +
"                                <td class=\"text-muted text-right invoice-text\">Price</td>\n" +
"                            </tr>\n" +
"                        </t-head>\n" +
"                    </table>\n" +
"                    <table class=\"table mt-0 pt-0 \">\n" +
"                        <t-body>\n" +
"                            <tr class=\"mt-3 \">\n" +
"                                <td class=\"text-left text-dark invoice-text\"><b>H. Rent</b></td>\n" +
"                                <td class=\"text-right text-dark invoice-text\"><b><!--&#x20B9;-->$ "+bill.getAmount()+"</b></td>\n" +
"                                <td class=\"text-right text-dark invoice-text\"><b>1 <small>month</small></b></td>\n" +
"                                <td class=\"text-success text-right invoice-text\"><b><!--&#x20B9;--> $ "+bill.getAmount()+"</b></td>\n" +
"                            </tr>\n" +
"                            <tr class=\"mt-3 \">\n" +
"                                <td class=\"text-left text-dark invoice-text\"><b>L. Charge</b></td>\n" +
"                                <td class=\"text-right text-dark invoice-text\"><b><!--&#x20B9;-->$ 0.0</b></td>\n" +
"                                <td class=\"text-right text-dark invoice-text\"><b>0</b></td>\n" +
"                                <td class=\"text-success text-right invoice-text\"><b><!--&#x20B9;-->$ 0.0</b></td>\n" +
"                            </tr>\n" +
"                           \n";
                        if(bill.getStates().equalsIgnoreCase("unpaid")){
                            data+=
"                            <tr class=\"text-right\">\n" +
"                                <td colspan=\"3\">Total Price </td>\n" +
"                                <td ><h5 class=\"text-danger\"> <b>"+bill.getAmount()+"</b></h5></td>\n" +
"                            </tr>\n";
                        }
                              data+=
"                           \n" +
"                         \n" +
"                        </t-body>\n" +
"                    </table>\n";
                        if(bill.getStates().equalsIgnoreCase("unpaid")){
                            data+=
"                     <div class=\"text-right\">\n" +
"                         <button class=\"btn btn-success px-5 rentPayBtn \" data-id='"+bill.getId()+"' data-amount='"+bill.getAmount()+"' >PAY</button>\n" +
"                     </div>\n";
                        }
                data+="</div>\n";
                        if(bill.getStates().equalsIgnoreCase("paid")){
                            data+=
"                <div class=\"card-footer mt-5 alert-secondary px-5 \">\n" +
"                    <table class=\"table mt-4 px-5\">\n" +
"                       <t-head>\n" +
"                           <tr class=\"\" style=\"border-bottom:1px solid #999999 ;\">\n" +
"                                <td class=\"text-muted text-left\">Bank Info</td>\n" +
"                                <td class=\"text-muted text-right \">Due By</td>\n" +
"                                <td class=\"text-muted text-right\">Total Due</td>\n" +
"                            </tr>\n" +
"                        </t-head>\n" +
"                    </table>\n" +
"                    <table class=\"table\">\n" +
"                       <t-head>\n" +
"                           <tr class=\"\" style=\"border-bottom:1px solid #009900 ;\">\n" +
"                               <td class=\"text-muted text-left\">\n" +
"                                   <span class=\"text-muted invoice-text\">Account No : </span><span>123456789009</span><br>\n" +
"                                   <span class=\"text-muted invoice-text\">Sort Code : </span><span>12345</span>\n" +
"                               </td>\n" +
"                               <td class=\"text-dark text-right \"><h5 ><b>"+sdf.format(bill.getSubmitedDate())+"</b></h5></td>\n" +
"                               <td class=\"text-right text-tomato\"><h5><b><!--&#8377;-->$ "+bill.getAmount()+"</b></h5></td>\n" +
"                            </tr>\n" +
"                        </t-head>\n" +
"                    </table>\n" +
"                    <div class=\"row pb-5\">\n" +
"                    <div class=\"col-6\" ><i class=\"fa fa-heart text-danger\"> </i><b> Thank You!</b></div>\n" +
"                    <div class=\"col-6 text-right text-muted\" ><small>houserent@gmail.com | xxxxxxxxx | houserent.com</small></div>\n" +
"                    </div>\n" +
"                </div>\n";
        }
                data+=
"                    \n" +
"            </div>\n" +
"            </div>\n" +
"            \n" +
"        </div>";
            
        }else if(type.equalsIgnoreCase("razorPayIntegration")){
            try {
                float amount=Float.parseFloat(req.getParameter("amount"));
                String billid=req.getParameter("billId");
                //RazorpayClient rzp=new RazorpayClient("rzp_test_2U","Mc");
                JSONObject ob=new JSONObject();
                ob.put("amount",amount*100);
                ob.put("currency","INR");
                ob.put("receipt",billid);
                  //create a order
                  //Order order=rzp.orders.create(ob);
                  //data=order.toString();
                  
                Random random = new Random();
                int randomInt = random.nextInt(1, 4); // Randomly generate 0 or 1
                if (randomInt == 0) {
                    data = "error";
                } else {
                    data = "{\"status\":\"created\",\"amount\":" + amount + ",\"id\":\"order" + random.nextInt(randomInt, 999999) + "\"}";
                }
            } catch (Exception ex) {
                data="error";
                ex.printStackTrace();
            }
        }else if(type.equalsIgnoreCase("successfullPayment")){
            
            String billid=req.getParameter("billId");
            List<Bill> bills=EntityHelper.getByQuery("from Bill where id='"+billid+"'");
            System.out.println("!!!!!!!!!!!!!!!!!!!! "+billid+" %%%%%%%%%%%%%");
            bills.get(0).setSubmitedDate(new Date());
            bills.get(0).setStates("paid");
            EntityHelper.updateData(bills);
            data=""+bills.get(0).getId();
        }
        else if(type.equalsIgnoreCase("Billings")){
            int pageNo=1;
            int limit=5;
            String formType=req.getParameter("bill");
            String condition = req.getParameter("condition");
            if(formType.equalsIgnoreCase("full")){
                data=" <div class=\"row mt-3\">\n" +
    "                    <div class=\"col-11 col-sm-6\">\n" +
    "                    <h4 class=\"text-muted \" ><b>All Bills and Payments Library</b></h4>\n" +
    "                    </div>\n" +
    "                    <div class=\"col-11 col-sm-6\">\n" +
    "                            <input type='text' class='form-control ClientSearch' placeholder='Search The User...'/>\n"
                        + "      <div class='list-group SearchArea my-1'></div>" +
    "                    </div>\n" +
    "                </div>";
                pageNo=Integer.parseInt(req.getParameter("cPageNo"));
                int offset=(pageNo-1)*limit;
                List<Bill> bills;
                if(condition.equalsIgnoreCase("null"))
                    bills=EntityHelper.getDataForpagination("From Bill",offset,limit);
                else if(condition.equalsIgnoreCase("paid"))
                    bills=EntityHelper.getDataForpagination("From Bill where states='paid'",offset,limit);
                else if(condition.equalsIgnoreCase("unpaid"))
                    bills=EntityHelper.getDataForpagination("From Bill where states='unpaid'",offset,limit);
                else 
                    bills=EntityHelper.getDataForpagination("From Bill where states!='paid' and states!='unpaid'",offset,limit);
                data+="<h6 class='mt-5 text-muted'>Bill Details...</h6>"
                        + "<div class=\"mt-3 mb-2 \">\n" +
    "                    <table class=\"table table-responsive-md table-hover\">\n" +
    "                        <thead class=\"gray text-muted\">\n" +
    "                            \n" +
    "                                <th class=\"t-10\">ID</th>\n" +
    "                                <th>Name</th>\n" +
    "                                <th>Photo</th>\n" +
    "                                <th>Amount</th>\n" +
    "                                <th>Status</th>\n" +
    "                                <th>House</th>\n" +
    "                            \n" +
    "                        </thead>\n" +
    "                        <tbody>\n" +
    "                            ";
                                for(Bill bill:bills){
                                    List<House> houses = EntityHelper.getByQuery("From House Where House_id="+bill.getHouseId());
                                    List<User> users = EntityHelper.getByQuery("from User where id="+bill.getTenant().getId());
                                
                                  data+="<tr class=' clientRowClick' data-client='"+bill.getTenant().getId()+"'>\n"
                                 + " <td class=' pt-4 ' >"+bill.getId()+"</td>\n" +
    "                                <td class='pt-4 ' >"+bill.getTenant().getName()+"</td>\n" +
    "                                <td class=' pt-2 '><img class='m-0 img-fluid userPic pointer'  src=\"files\\images\\UserImg\\"+users.get(0).getPic()+" \"  style='max-width:50px; max-height:50px' > </td>\n" +
    "                                <td class=' pt-4 '>"+bill.getAmount()+"</td>\n"+
                                          ((bill.getStates().equalsIgnoreCase("paid"))? "<td class=' pt-2'>Paid Date : "+ bill.getSubmitedDate() :"<td class=' pt-2'>Paid Date : null ")+
                                          "<br>House : "+houses.get(0).getName()+" </td>"+
                                          "\n<td class=' pt-2'>Address: "+ houses.get(0).getAddress()
                                          +"<br>Name : "+houses.get(0).getName()+" </td>"+
    "                              \n" +
    "                            </tr>\n";
                                }                  
                           data+=" </tbody>\n" +
    "                    </table>\n" +
    "                </div>";
                    // pagination of client
                    data+= "<ul class='pagination justify-content-end mt-4 px-4'>";
                    int n=EntityHelper.getByQuery("from Bill").size()/limit;
                    System.out.println("as :  "+n);
                    if(pageNo!=1)
                        data+="<li onclick=\" bill('"+(pageNo-1)+"', '"+condition+"')\" class=\"page-item\" ><a class=\"page-link\">&laquo;</a></li>\n";
                           for(int i=1;i<=n+1;i++){
                               if(i==pageNo)
                                    data+="<li class=\"page-item active\" onclick=\" bill('"+i+"', '"+condition+"') \" ><a class=\"page-link\">"+i+"</a></li>\n";
                               else
                                    data+="<li class=\"page-item \" onclick='bill('"+i+"', '"+condition+"')' ><a class=\"page-link\">"+i+"</a></li>\n";
                           }   
                    if(pageNo!=n+1)
                        data+="<li onclick=\" bill('"+(pageNo+1)+"', condition='"+condition+"')\" class=\"page-item\" ><a class=\"page-link\">&raquo;</a></li>\n"
                    +"</ul>";
            }
        }
        
        out.print(data);
    }
    
}

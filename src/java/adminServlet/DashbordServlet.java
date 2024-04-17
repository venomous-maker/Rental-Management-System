
package adminServlet;

import HelperClasses.EntityHelper;
import entities.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.*;


public class DashbordServlet extends HttpServlet{
    public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        PrintWriter out=res.getWriter();
        String data;
        String Paid = "paid", Unpaid = "unpaid";
        List<House> houses = EntityHelper.getByQuery("From House");
        List<Tenants> tenants = EntityHelper.getByQuery("FROM Tenants");
        List<Bill> bills = EntityHelper.getByQuery("From Bill where states!='paid'");
        List<Bill> paid = EntityHelper.getByQuery("From Bill where states='paid'");
        long rentuncollected=0, rentcollected=0;
        for(Bill bill:paid){
            rentcollected+=bill.getAmount();
        }
        for(Bill bill:bills){
            rentuncollected+=bill.getAmount();
        }
        data="   <h4 class=\"text-right mt-2 mb-2 text-muted\">Welcome Admin</h4>\n" +
"                <div class=\" row my-3\">\n" +
"                    <div class=\"col-12 col-sm-6 col-md-4 col-lg-3 \">\n" +
"                        <div class=\"card-body bg-primary\">\n" +
"                        <i class=\"fa fa-building fa-2x text-light \"></i>\n" +
"                        <h5 class=\"number text-light\">"+houses.size()+"</h5>\n" +
"                        <h6 class=\"text text-light\">Houses</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-primary p-2\" onclick=\"house()\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-primary fa fa-arrow-circle-right \" onclick=\"house()\"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6 col-md-4 col-lg-3\">\n" +
"                        <div class=\"card-body bg-success\">\n" +
"                        <i class=\"fa fa-users fa-2x text-light \"></i>\n" +
"                        <h5 class=\"number text-light\">"+tenants.size()+"</h5>\n" +
"                        <h6 class=\"text text-light\">Tenants</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-success p-2\" onclick=\"tenants()\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-success fa fa-arrow-circle-right \" onclick=\"tenants()\"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6 col-md-4 col-lg-3\">\n" +
"                          <div class=\"card-body bg-warning\">\n" +
"                        <i class=\"fa  fa-2x text-light \">&#xf3d1;</i>\n" +
"                        <h5 class=\"number text-light\">"+paid.size()+"</h5>\n" +
"                        <h6 class=\"text text-light\">Payment</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-warning p-2\" onclick=\"bill('1','"+Paid+"')\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-warning fa fa-arrow-circle-right \" onclick=\"bill('1','"+Paid+"')\"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6 col-md-4 col-lg-3\">\n" +
"                          <div class=\"card-body bg-danger\">\n" +
"                        <i class=\"fa fa-poll fa-2x text-light \"></i>\n" +
"                        <h5 class=\"number text-light\">"+bills.size()+"</h5>\n" +
"                        <h6 class=\"text text-light\">Invoices</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-danger p-2\" onclick=\"bill('1', '"+Unpaid+"')\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-danger fa fa-arrow-circle-right \" onclick=\"bill('1','"+Unpaid+"')\"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6 col-md-4 col-lg-3\">\n" +
"                        <div class=\"card-body bg-secondary\">\n" +
"                        <i class=\"fa fa-2x text-light \">&#xf0b1;</i>\n" +
"                        <h5 class=\"number text-light\">"+rentcollected+"</h5>\n" +
"                        <h6 class=\"text text-light\">Rent Collected</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-secondary p-2\" onclick=\"bill('1',"+Paid+"')\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-secondary fa fa-arrow-circle-right \" onclick=\"bill('1','"+Paid+"')\"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                  <div class=\"col-12 col-sm-6 col-md-4 col-lg-3\">\n" +
"                          <div class=\"card-body bg-danger\">\n" +
"                        <i class=\"fa fa- fa-2x text-light \">&#xf66f;</i>\n" +
"                        <h5 class=\"number text-light\">"+rentuncollected+"</h5>\n" +
"                        <h6 class=\"text text-light\">All Balance</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-danger p-2\" onclick=\"bill('1',"+Unpaid+"')\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-danger fa fa-arrow-circle-right \" onclick=\"bill('1','"+Unpaid+"')\"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6 col-md-4 col-lg-3\">\n" +
"                          <div class=\"card-body bg-tomato\">\n" +
"                        <i class=\"fa fa-question-circle fa-2x text-light \"></i>\n" +
"                        <h5 class=\"number text-light\">2</h5>\n" +
"                        <h6 class=\"text text-light\">Warning Message</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-tomato p-2\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-tomato fa fa-arrow-circle-right \"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6 col-md-4 col-lg-3\">\n" +
"                          <div class=\"card-body bg-info\">\n" +
"                        <i class=\"fa fa-home fa-2x text-light \"></i>\n" +
"                        <h5 class=\"number text-light\">"+ EntityHelper.getByQuery("from House where tenants=NULL").size() +"</h5>\n" +
"                        <h6 class=\"text text-light\">Vacant house</h6>\n" +
"                        </div>\n" +
"                        <div class=\"class-footer p-0\">\n" +
"                            <h6 class=\"text-info p-2\">View Details</h6>\n" +
"                            <i class=\"v-arrow text-info fa fa-arrow-circle-right \"></i>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    \n" +
"                </div>";
        
                List<Tenants> tenantList=EntityHelper.getByQuery("from Tenants where checkByAdmin='false' and states='done'");
                System.out.println("dash bord "+tenantList.size());
                
                data+="  <div class=\"row mt-5 \">\n" +
"                    <div class=\"col-12 col-sm-6\">\n" +
"                        \n" +
"                    </div>\n" +
"                    <div class=\"col-12 col-sm-6  \" >\n" +
"                       \n" +
"                          <div class=\"card \" style=\"height: 250px; overflow:auto;\">\n" +
"                              <div class=\"alert-danger card-header mt-0 mb-0 pt-1 pb-0\"><h6>New Tenants</h6></div>\n" +
"                              <div class=\"card-body mt-0 mb-0 pt-0 pb-0\">\n" +
"                                <table class=\"table table-sm table-striped table-hover \">\n" +
"                                  <tr class='alert-dark '>\n" +
"                                      <td>Name</td>\n" +
"                                      <td>Date</td>\n" +
"                                      <td>States</td>\n" +
"                                      <td>Check</td>\n" +
"                                  </tr>\n";
                              for(Tenants tenant:tenantList){
                                    data+="<tr class=' text-muted ' id='tenant' data-x='dash' data-h='" +tenant.getTid()+ "' data-t='" +tenant.getId()+ "'  data-tenant='"+tenant.getId()+" '>\n";
                                    String arr[]=tenant.getName().split(" ");
                                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                                    String startDate =sdf.format(tenant.getStartdate());
           
                                    data+=
"                                          <td>"+arr[0].charAt(0)+" . "+arr[(arr.length)-1] +"</td>\n" +
"                                          <td>"+startDate+"</td>\n" +
"                                          <td>"+tenant.getStates() +"</td>\n" +
"                                          <td>"+tenant.isCheckByAdmin()+"</td>\n" +
"                                      </tr>\n";
                              }
                
                       data+="   </table>\n" +
"                              </div>\n" +
"                              <div class='card-footer mt-0 mb-0 pt-1 pb-0'>\n" +
"                                   <h6 class='text-muted pointer' onclick='tenants()' > View All Tenants </h6>" +
"                              </div>\n" +
"                           </div>\n" +
"                    </div>\n" +
"                </div>";
                
        
        
        out.print(data);
        
    }
}

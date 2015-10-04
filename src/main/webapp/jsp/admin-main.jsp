<%@include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Ferndale Homeowners</title>
        <!--[if IE 6]>
        <style>
        body {behavior: url("csshover3.htc");}
        #menu li .drop {background:url("img/drop.gif") no-repeat right 8px; 
        </style>
        <![endif]-->
        
        <!-- Common js -->
		<script src="js/common-util.js"></script>

    </head>
    
    <body>
        <ul id="menu">
            <li><a href="admin-home.action" class="drop">HOME</a></li>
            <li><a href="#">REQUEST</a>
                <div class="dropdown_1column"><!-- Begin 4 columns container -->
                    
                    <div class="col_1">
                        <ul>
                            <li><a href="admin-search-requests.action">Search</a></li>
                            <li><a href="admin-create-request.action">Create Request</a></li>
                            <li><a href="admin-add-adjustment-penalty.action">Add Adjustment/Penalty</a></li>
                        </ul>
                    </div>
                </div>
            </li>
            <li><a href="#">BILLING</a>
                <div class="dropdown_1column"><!-- Begin 4 columns container -->
                    
                    <div class="col_1">
                        <ul>
							<li><a href="admin-search-bill.action">Payment</a></li>
							<li><a href="admin-search-or-to-cancel.action">Cancel Official Receipt</a></li>
							<li><a href="admin-assoc-dues-yearly.action">Assoc Dues - Yearly</a></li>
							<!-- <li><a href="admin-searchCancelledOR.action">Cancelled OR</a></li> temporarily disabled, codes not yet ready --> 
                        </ul>
                    </div>
                </div>
            </li>
            <li><a href="#" class="drop">REPORTS</a>
                <div class="dropdown_3columns">
                    <div class="col_1">
                         <h3>DAILY</h3>
                    <ul>
                    	<!-- <li><a href="admin-searchViewOR.action">View Official Receipt</a></li>
                             <li><a href="admin-paymentsDaily.action">View Payment</a></li> -->
                    	<li><a href="admin-collections-daily.action">View Collections</a></li>
                    	<li><a href="admin-collections-daily-detailed.action">Detailed Collections</a></li>
                    </ul>
                </div>
                <div class="col_1">
                    <h3>MONTHLY</h3>
                    <ul>
                    	<!-- <li><a href="admin-searchStatement.action">View Statement</a></li> -->
                    	<li><a href="admin-collections-monthly.action">View Collections</a></li>
                    </ul>
                </div>
                <div class="col_1">
                    <h3>DATE RANGE</h3>
                    <ul>
                    	<li><a href="admin-collections-monthly-detailed.action">Collections by Date</a></li>
                    	<li><a href="admin-collections-monthly-summary.action">Collections Summary</a></li>
                    </ul>
                </div>     
                </div>
            </li>
            <!-- <li><a href="#">SETTINGS</a>
                <div class="dropdown_1column">Begin 4 columns container
                    
                    <div class="col_1">
                        <ul>
                              <li><a href="admin-settings.action">Request/Services</a></li>
                                 <li><a href="admin-orseries.action">Official Receipt</a></li>
                                 <li><a href="admin-rates.action">Rates</a></li>
                                 <li><a href="admin.action">House/Owner/Lessee</a></li>
                                 <li><a href="admin-user-maintenance.action">House/Owner/Lessee</a></li>
                                 <li><a href="admin-upload-water.action">Upload Water Reading</a></li>     
                        </ul>
                    </div>
                </div>
            </li> -->
             <li><a href="#" class="drop">SETTINGS</a>
                <div class="dropdown_2columns">
                    <div class="col_1">
                         <h3>ADMIN</h3>
                    <ul>
                    	<li><a href="admin-settings.action">Request/Services</a></li>
                        <li><a href="admin-orseries.action">Official Receipt</a></li>
                        <li><a href="admin-rates.action">Rates</a></li>
                        <li><a href="admin-search-user-to-reset-password.action">Reset User Password</a></li>
                        <li><a href="change-password.action">Change Password</a></li>
                        <!-- <li><a href="admin.action">House/Owner/Lessee</a></li> ASK IF THIS IS REALLY NEEDED! -->
                    </ul>
                </div>
                <div class="col_1">
                    <h3>UPLOADS</h3>
                    <ul>
                    	<li><a href="admin-upload-water.action?file=water">Upload Water Reading</a></li>    
                    	<li><a href="admin-upload-delinquent.action?file=delinquent">Upload Delinquent</a></li>    
                    </ul>
                </div>     
                </div>
            </li>
            <li><a href="#">FORMS</a>
                <div class="dropdown_1column"><!-- Begin 4 columns container -->
                    
                    <div class="col_1">
                        <ul>
                            <li><a href="admin-house-registration.action">House Registration</a></li>
                            <li><a href="admin-move-in-out.action">Move-In/Out</a></li>
                            <li><a href="admin-deactivate-user.action">De-activate User</a></li>
                            <!-- THIS ONE SEARCHES FOR A PARTICULAR OWNER AND THE RESULT CAN BE EDITED -->
                       		<li><a href="admin-user-maintenance.action">House/Owner/Lessee</a></li>
                            <!-- <li><a href="admin-moveOut.action">Move-Out</a></li> -->     
                        </ul>
                    </div>
                </div>
            </li>
            
            <li class="menu_right"><a href="javascript:show()" class="drop">LOGOUT</a></li>
        </ul>
        <p></p>
        <table align="center" class="container">
            <tr>
                <td>
                    <div id="my-tabs"></div>
                </td>
            </tr>
        </table>
        
    </body>
</html>
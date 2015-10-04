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

    </head>
    
    <body>
    	<!-- LOGO + WELCOME -->
    	<div style="margin-top:-9px;">
    		<img src="images/homeportal_image.jpg" width="300" height="50"></img><br>
    	</div>
        
        <div style="margin-top:5px; margin-left:40px;">
        	<b><span>Welcome </span><c:out value="${ownerName}"/><span>!</span></b>
        </div>
        
        <div style="margin-left:40px;">
        	<span id="ownerAddress"></span>
        	<span id="houseListLink" style="float: right; margin-right: 50px; display:none;">
        		<a href="home-owner-landing-page.action">BACK TO HOUSE LIST</a>
        	</span>
        </div>
        
        
        <div style="margin-top:-12px;">
  			<ul id="menu">
	            <li><a id="homeLink" href="user-home.action">HOME</a></li>
	            <li><a id="createRequestLink" href="user-create-request.action">CREATE REQUEST</a></li>
	  
	            
	            <li><a href="#">BILLING</a>
	                <div class="dropdown_1column"><!-- Begin 4 columns container -->
	                    
	                    <div class="col_1">
	                        <ul>
	                            <li><a id="billingViewLink" href="user-balances.action">Outstanding Balances</a></li>
	                            <li><a id="billingStatementLink" href="user-statement.action">Monthly Statement</a></li>
	                        </ul>
	                    </div>
	                </div>
	            </li>
	            <li><a id="viewORHistoryLink" href="user-or-history.action">VIEW OR HISTORY</a></li>
	            <li><a href="#">SETTINGS</a>
	                <div class="dropdown_1column">
	                    <div class="col_1">
	                        <ul>
	                            <li><a id="updateProfileLink" href="user-profile.action">Update Profile</a></li>
	                            <li><a id="changePasswordLink" href="change-password.action">Change Password</a></li>
	                        </ul>
	                    </div>
	                </div>
	            </li>
	            
	            <!-- <li><a id="updateProfileLink" href="user-profile.action">UPDATE PROFILE</a></li> -->
	            
	            
	            <!-- <li id="backToHouseList"><a href="home-owner-landing-page.action">BACK TO HOUSE LIST</a></li> -->
	            <li class="menu_right"><a href="javascript:show()" class="drop">LOGOUT</a></li>
	        </ul>      
	    </div>
  
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
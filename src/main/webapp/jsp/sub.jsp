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
    	
    	<div>
    		<!-- RACS: <c:out value="${billerUserId}"/> -->
    	</div>
        <ul id="menu">
            <li><a href="admin-home.action" class="drop">BACK TO HOME</a></li>
        </ul>
        <p></p>
        <table align="center" class="container">
            <tr>
                <td>
                    <div id="my-tabs">
                    
                    	<div id="fi-form">
                    	
                    	</div>
                    
                    </div>
                </td>
            </tr>
        </table>
       
        <div>
        	<input type="hidden" id="totalPayment" value="0.00"/>
        </div>
        
        <div>
        	<input type="hidden" id="totalPaymentChanged" value="0.00"/>
        </div>
       
        
        <!-- USED FOR ADMIN CREATE REQUEST -->
        <!-- <div>
        	<input type="hidden" id="comboUserId" value=""/>
        </div>
        <div>
        	<input type="hidden" id="comboHouseId" value=""/>
        </div> -->
        
        <div id="comboUserId" style="display:none">
        	comboUserId
        </div>
        <div id="comboHouseId" style="display:none">
        	comboHouseId
        </div>
      
      
      
      <!-- BILLING HIDDEN VALUES -->
      	<div>
        	<input type="hidden" id="billerUserId" value="<c:out value="${billerUserId}"/>"/>
        </div>
        
        <div>
        	<input type="hidden" id="billerHouseId" value="<c:out value="${billerHouseId}"/>"/>
        </div>
        
        <div>
        	<input type="hidden" id="billerPayeeName" value="<c:out value="${billerPayeeName}"/>"/>
        </div>
        
        <div>
        	<input type="hidden" id="billerModeOfPayment" value="<c:out value="${billerModeOfPayment}"/>"/>
        </div>
        
        <div>
        	<input type="hidden" id="billerPayeeAddress" value="<c:out value="${billerPayeeAddress}"/>"/>
        </div>
        
        <div>
        	<input type="hidden" id="billerBank" value="<c:out value="${billerBank}"/>"/>
        </div>
        
        <div>
        	<input type="hidden" id="billerChequeNumber" value="<c:out value="${billerChequeNumber}"/>"/>
        </div>
      
      
      
      
    </body>
</html>
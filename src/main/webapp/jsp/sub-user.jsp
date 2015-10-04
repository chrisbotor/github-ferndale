<%@include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <link rel="stylesheet" href="menu.css" type="text/css" media="screen" />

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
        </div>
        
        
        <div style="margin-top:-12px;">
        	<ul id="menu">
            	<li><a id="homeLink" href="user-home.action" class="drop">BACK TO HOME</a></li>
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
<%@include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <link rel="stylesheet" href="menu.css" type="text/css" media="screen" />

        <title>Ferndale Homeowners</title>
	
		<!-- App js -->
		<script src="js/user-bean.js"></script>
		<script src="js/admin-search-user-to-reset-password.js"></script>
		
    </head>
    <body>
        <ul id="menu">
            <li><a href="admin-home.action" class="drop">BACK TO HOME</a></li>
        </ul>
        
       	<div>
			<table>
				<tbody>
					<!-- LOGO and NAVIGATION -->
					<%-- <%@include file="navigation-header.jsp" %> --%>
					<tr>
						<td height="14px" colspan="2"></td>
					</tr>
					<tr>
						<td valign="top">
							<div id="admin-create-request-table" style="margin-left: 425px; margin-top: 20px; margin-bottom: 80px;">
								
							</div>
						</td>
					</tr>
					<%-- <tr>
						<td>
							<%@include file="footer.jsp" %>
						</td>
					</tr> --%>
				</tbody>
			</table>
		</div>
    </body>
</html>
<html>
    <head>
        <%@include file="header.jsp" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        
        <!-- App js -->
		<!-- <script src="js/admin-grid.js"></script>
		<script src="js/admin-alerts.js"></script> -->
		
		<title>Ferndale Homeowners - Downloads</title>
        
    </head>
    <body>
    	<div align="center">
			<table id="AdminLayout" width="1082px" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td colspan="2">
							<a name="top"></a>
							<%@include file="adminHeader.jsp" %>
						</td>												
					</tr>
					<tr>
						<!-- <td>
							
							<h1>Download Page</h1>
								<p>Click the download links below:</p>
								<c:url value="/main/download/pdf.action" var="downloadPdf"/>
								<a href="${downloadPdf}">Download PDF</a>
						</td> -->
						
						<td>
							<div style="background-color:yellow; width:250px; height:150px; font:arial; font-size:12px; padding:5px;">
								Click the link to select the type of report
								
								<div>
									<ul>
										<li>
											<a href="#">Billing</a>	
										</li>
									</ul>
									
								</div>
								
							
							</div>
							<div style="background-color:green; width:450px; height:325px; margin-left:300px; margin-top:-100px; margin-bottom:50px;">
							
							
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<%@include file="footer.jsp" %>
						</td>
					</tr>
				</tbody>
			</table>    	
    	</div>
    </body>
</html>
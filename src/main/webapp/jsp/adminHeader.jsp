<%@include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--"src/main/webapp/WEB-INF/pages/HelloWorldPage.jsp"
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Name       : WildFlowers
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20120902

-->
<html>
    <head>
        <meta name="keywords" content="" />
        <meta name="description" content="" />
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link href='http://fonts.googleapis.com/css?family=Oswald:400,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Abel' rel='stylesheet' type='text/css'>
        
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="css/menu.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
        
        .icon-user-pdf {
            background-image: url(ext-3.4.0/examples/shared/icons/fam/pdf.png) !important;
        }
        
</style>
        
        <title>Ferndale Homeowners</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="header-wrapper" class="container">
                    <nav>
                        <ul>
                            <li><a href="admin-home.action">HOME</a>
                                
                            </li>
                            <li><a href="">REQUEST</a>
                                <ul>
                                 <li><a href="admin-searchRequest.action">View</a></li>
                                 <li><a href="admin-createRequest.action">Create</a></li>
                                </ul>
                            </li>
                            <li><a href="">BILLING</a> 
                                 <ul>
                                 <li><a href="admin-searchBill.action">Payment</a></li>
                                 <li><a href="admin-searchStatement.action">Statement</a></li>
                                 <li><a href="admin-collectionsHistory.action">Collections</a></li>
                                 <li><a href="admin-searchViewOR.action">View Official Receipt</a></li>
                                 <li><a href="admin-searchCancelledOR.action">Cancelled Official Receipt</a></li>
                                </ul>
                            </li>
                            <li><a href="">SETTINGS</a> 
                                 <ul>
                                 <li><a href="admin-settings.action">Request/Services</a></li>
                                 <li><a href="admin-orseries.action">Official Receipt</a></li>
                                 <li><a href="admin-rates.action">Rates</a></li>
                                 <li><a href="admin.action">House/Owner/Lessee</a></li>
                                </ul>
                            </li>
                           
                            <li><a href="">FORM</a>
                                <ul>
                                     <li><a href="admin-moveIn.action">Lot Ownership</a></li>
                                    <li><a href="admin-moveLesse.action">Move-In</a></li>
                                    <li><a href="admin-moveOut.action">Move-Out</a></li>
                                </ul>
                            </li>
                            <li><a href="javascript:show()">LOG-OUT</a></li>
                        </ul>
                    </nav>
            </div>
            <div id="my-tabs" class="container" align="center"></div>
        </div>
        <div id="footer-content"></div>
        <div id="footer">
            <p>Copyright (c) 2012 ayala-ferndalehomes.com. All rights reserved.</p>
        </div>
        <!-- end #footer -->
    </body>
</html>
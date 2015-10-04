<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");%>
<%response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");%>
<%response.addHeader("Cache-Control", "post-check=0, pre-check=0");%>
<%response.setHeader("Pragma", "no-cache");%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="content-language" content="EN">
		
		<!-- THIS IS THE CSS THAT DESIGNS THE TOOLBAR "BACK HOME" -->
		<link href="css/menu.css" rel="stylesheet" type="text/css" media="screen"/>
		
		<!-- Ext JS Files -->
		<!-- <link rel="stylesheet" type="text/css" href="/homeportaluat/extjs/resources/css/ext-all.css" />
		<script type="text/javascript" src="/homeportaluat/extjs/bootstrap.js"></script> -->
		
		<link rel="stylesheet" type="text/css" href="/homeportal/extjs/resources/css/ext-all.css" />
	    <script type="text/javascript" src="/homeportal/extjs/bootstrap.js"></script>
		
		<!-- file upload form js -->
		<script src="/homeportal/js/file-upload.js"></script>
		<!-- <script src="/homeportal/js/file-upload-water.js"></script> -->
			
		
		<title>Ayala Ferndales Homeowners - File Upload</title>
	</head>
	<body>
		
		<!-- BACK TO HOME NAVIGATION -->
		<div style="margin-top: 65px;">
			<ul id="menu">
	            <li><a href="admin-home.action" class="drop">BACK TO HOME</a></li>
	        </ul>
	    </div>
	    
	    <!-- UPLOAD FORM -->
	    <div style="margin-top: 50px; margin-left: 120px;">
			
			<div style="margin-left: 330px;">
				Click on "Select a File" button to select a file and click on Upload button.
				<br/>			
				<b>Note:</b> It should be .csv file.
			
			</div>
			
		
			<div id="main-panel" style="padding:25px; margin-left: 300px;"></div>
			
			<div id="fi-form" style="padding:25px;"></div>
		</div>
		
	</body>
</html>
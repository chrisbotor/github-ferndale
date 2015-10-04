<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<link rel="stylesheet" type="text/css" href="/homeportaluat/extjs/resources/css/ext-all.css" />
	    <script type="text/javascript" src="/homeportaluat/extjs/bootstrap.js"></script>
		
		
			
		
		<title>Ayala Ferndales Homeowners - Home Owner Landing Page</title>
	</head>
	<body>
	
		<!-- BACK TO HOME NAVIGATION -->
		<div style="margin-top: 65px;">
			<ul id="menu">
	            <!-- <li><a href="admin-home.action" class="drop">BACK TO HOME</a></li> -->
	        </ul>
	    </div>
	    
	    
	    <c:set var="houseIdMap" value="${houseIdMap}"/>
	    <c:set var="houseNum" value="${fn:length(houseIdMap)}"/>
	    
	    <div style="margin-left:550px; margin-top: 80px;">
	    	<b>Select your house address:</b>
	    </div>
		
		<div style="margin-left:500px; margin-top: 30px;">
			<c:forEach items="${houseIdMap}" var="item">
			
				<c:url var="url" value="user-home.action">
				  <c:param name="houseId" value="${item.key}" />
				  <c:param name="address" value="${item.value}" />
				  <c:param name="houseNum" value="${houseNum}" />
				  <c:param name="del" value="${delinquent}" />
				</c:url>
				
				<div style="text-align:center;">
				    <ul>
				    	<li style="padding-top:5px;">
				    		<a href="${url}">
					    		<span style="font-size:150%;"><b>${item.value}</b></span>
					    	</a>
					    </li>
				    	
				    	<%-- <li style="padding-top:5px;"><a href="home.action?houseId=${item.key}"><span style="font-size:150%;"><b>${item.value}</b></span></a></li> --%>
				    </ul>
				</div>
			</c:forEach>
		</div>
		
	    
	    <!-- ORIG CODE BELOW -->
		<%-- <c:set var="houseIdList" value="${houseIdList}"/>
		
		<c:forEach items="${houseIdList}" var="item">
			<div style="text-align:center;">
			    <ul>
			    	<li><a href="#"><b>House ID: ${item}</b></a></li>
			    </ul>
			</div>
			
		</c:forEach> --%>
		
	<%-- <c:forEach var="houseIdList" items="${houseIdList}" varStatus="i">
  		<c:set var="houseId" value="${houseIdList.houseId}"/>
	  		
	  	<div>
		    <ul>
		    	<li>${houseId}</li>
		    </ul>
		</div>
	</c:forEach>
	 --%>		
	</body>
</html>
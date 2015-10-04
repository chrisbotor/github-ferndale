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

<!-- ExtJS css -->
<link rel="stylesheet" type="text/css" href="ext-3.4.0/resources/css/ext-all.css" />

<!-- Row Editor plugin css -->
<link rel="stylesheet" type="text/css" href="ext-3.4.0/examples/ux/css/rowEditorCustom.css" />
<link rel="stylesheet" type="text/css" href="ext-3.4.0/examples/shared/examples.css" />
<link rel="stylesheet" type="text/css" href="ext-3.4.0/examples/ux/css/RowEditor.css" />
<link rel="stylesheet" type="text/css" href="ext-3.4.0/examples/ux/fileuploadfield/css/fileuploadfield.css"/>

<!-- App custom css -->
<link rel="stylesheet" type="text/css" href="css/crudgrid.css" />

<!-- ExtJS js -->
<script src="ext-3.4.0/adapter/ext/ext-base.js"></script>
<script src="ext-3.4.0/ext-all.js"></script>

<!-- Row Editor plugin js -->
<script src="ext-3.4.0/examples/ux/RowEditor.js"></script>
<script src="/homeportal/ext-3.4.0/examples/ux/fileuploadfield/FileUploadField.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="content-language" content="EN">
<meta name="keywords" content="ayala ferndale homes, ayala, ferndale">
<meta name="copyright" content=" ">
<meta name="author" content=" ">

<link href="css/menu.css" rel="stylesheet" type="text/css" media="screen"/>
<script type="text/javascript">
        function show() {
        window.location.replace('http://www.ayalaferndalehomes.com');
        };
        </script>
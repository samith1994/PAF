<%@page import="com.funding"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Funding Bodies </title>
<link rel="stylesheet" href="views/bootstrap.min.css">
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/main.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Funding Bodies Details</h1>
				<form id="formItem" name="formItem">
					name:
					<input id="Name" name="Name" type="text"
						class="form-control form-control-sm"> <br> 
						Description: 
						<input id="Desc" name="Desc" type="text"
						class="form-control form-control-sm"> <br> 
						Date: 
						<input id="date" name="date" type="text"
						class="form-control form-control-sm"> <br> 
						
						
						<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
						<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
						funding itemObj = new funding();
						out.print(itemObj.readItems());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
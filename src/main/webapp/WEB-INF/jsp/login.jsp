<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Spring-boot Custom Login Page </title>
</head>
<body>
	
	<div align="center">
	<h2>Login</h2>
	</div>
	<div align="center">
    <form action="authenticate" method="POST">
    	<label>Username: </label> <input type="text" name="username"/>
    	<br>
    	<label>Password: </label> <input type="password" name="password"/>
    	<br>
    	<button type="submit">Login</button> 
    </form>
    </div>
   
</body>
</html>
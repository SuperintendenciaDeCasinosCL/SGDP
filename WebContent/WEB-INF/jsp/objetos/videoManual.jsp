<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width">
</head>
<body style="margin: 0px;" cz-shortcut-listen="true">
	<video controls autoplay="" name="media">
		<source src='<c:url value="/videos/${idVideo}" />' type="video/mp4" />
	</video>
	<%-- <div id="viewPortSize" class="bottom_right"
		style="background-color: rgb(0, 0, 0); color: rgb(255, 255, 255); font-size: 12px; display: none;">
		Inner: 1920 x 989<br>Outer: 1920 x 1040
	</div>--%>
</body>
</html>
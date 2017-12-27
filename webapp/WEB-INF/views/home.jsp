<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<form action="/test/textArea" method="post">
	<textarea name="textArea" cols="100"></textarea>
	<input type="submit" value="submit"/>
</form>
</body>
</html>

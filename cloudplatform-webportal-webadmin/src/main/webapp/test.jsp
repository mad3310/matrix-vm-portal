<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/kaptcha" method="post">
<img src="/kaptcha" width="65" height="30" id="kaptchaImage" style="margin-bottom: 2px"/><input type="text" name="kaptcha" /><input type="submit" value="Submit" name="_finish"/>
</form>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>	
<head>
	<meta charset="UTF-8">
	<title>${pageContext.errorData.throwable.message}</title>
    <link rel="stylesheet" href="css/dukeshop.css">		
</head>
<body>
    <div class="main">
        <p class="exceptionname">${pageContext.errorData.throwable.message}</p>
        <p>
            ${pageContext.errorData.throwable}<br>
           <br>
            This is the request URI: <br>
            <code>${pageContext.errorData.requestURI}</code>
            <br>
        </p>
    </div>
</body>
</html>

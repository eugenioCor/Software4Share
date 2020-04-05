<%@page import="S4S_Model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
User user = (User) session.getAttribute("user");
if ((user!= null))
{	
	if(user.isAdministrator())
	{
		response.sendRedirect("homepage.jsp");
	    return;	
	}
    
}
%>
<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="./css/cartStyle.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script src="./Jquery/jQuery.js">
    </script>
  <script src="./js/cartLoader.js"></script>
</head>

<body>
<jsp:include page="./header.jsp"/> 
    <div class="master-container">
        <div class="title-box">
            <div class="title">Carrello<span class="cart_icon"></span></div>
        </div>

    </div>
    
    
    <script src="./js/cartControl.js"></script>
    <script src="./js/singlePage.js"></script>
    <script src="https://js.braintreegateway.com/js/braintree-2.32.1.min.js"></script>
    
</body>
<jsp:include page="footer.jsp" /> 
</html>
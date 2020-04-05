<%@page import="S4S_Model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
// Verifica che una pagina sia bloccata
User user = (User) session.getAttribute("user");
if ((user == null))
{	
    response.sendRedirect("login.jsp");
    return;
}else if(user!=null)
{
	if(!user.isAdministrator())
	{
		response.sendRedirect("homepage.jsp");
	    return;
	}
}
%> 
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Pagina Prodotto</title>
    <link rel="stylesheet" href="./css/userListStyle.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script type="text/javascript" src="./Jquery/jQuery.js"></script>
    <script type="text/javascript" src="./js/userListLoader.js"></script>
    <script src="Jquery/jQuery.js"></script>
</head>

<body>
<jsp:include page="./header.jsp"/> 
    <div class="master-container">
        <div class="title-box">
            <div class="title">Lista utenti</div>
        </div>


        <div class="product-section">
            <div class="product-container">

           
            </div>

        </div>


    </div>
   <script type="text/javascript" src="./js/userListController.js"></script>
   <script type="text/javascript" src="./js/singlePage.js"></script>  
<jsp:include page="footer.jsp" /> 
</body>

</html>
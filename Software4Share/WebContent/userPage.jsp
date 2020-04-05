<%@ page language="java" contentType="text/html" 
	pageEncoding="ISO-8859-1" import="S4S_Model.*" %>

<!-- Pagina per il profilo utente -->  
<!DOCTYPE html>

<%
User user = (User) session.getAttribute("user");
if (user==null)
{	
    response.sendRedirect("./login.jsp");
    return;
}
%>

<html lang="en">

<head>
	<jsp:include page="header.jsp" /> 
    <title>Software4Share</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Let's sell our softwares!">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="./css/userPage_css.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script type="text/javascript" src="./Jquery/jQuery.js"></script>
</head>

<body>

<div id="megaContainer">
<!-- Greetings -->
<div class="cont" >
	<h1 id="greetings">Ciao, <%= user.getUserName() %></h1>
	
</div>

<div id="allContainer">
<!-- Credenziali -->
<ul id="userList">
    <li  class="userElem">
        <h3 id="username">Username: <%= user.getUserName() %></h3>
    </li>
    <li  class="userElem">
        <h3 id="name">Nome: <%= user.getName() %></h3>
    </li>
    <li  class="userElem">
            <h3 id="surname">Cognome: <%= user.getSurname() %></h3>
    </li>
    <li  class="userElem">
            <h3 id="birth">Data di nascita: <%= user.getBirthDay() %></h3>
    </li>
    <% if(user.getpIVA()!=null) { %>
        <li  class="userElem">
                <h3 id="iva">Partita IVA: <%= user.getpIVA() %></h3>
        </li>
    <% } %>
    <li  class="userElem">
            <h3 id="email">e-mail: <%= user.getEmail() %></h3>
    </li>
</ul>

<!-- Div dei vari bottoni di cui potrebbe aver bisogno un user/admin -->
<div id="buttonsDiv">
<% if(user.getpIVA()!=null) { %>
    
    <input class="singleButton" type="button" onclick="window.location='upload.jsp';" value="Aggiungi prodotto+" />
    <input class="singleButton" type="button" onclick="showProducts()" value="In vendita" />
<% } 
	if(user.isAdministrator()) { %>
	
	<input class="singleButton" type="button" onclick="window.location='userList.jsp';" value="Utenti registrati" />
	<input class="singleButton" type="button" onclick="window.location='inv.jsp';" value="Fatture" />
    
    
<% } else { %>

	<input class="singleButton" type="button" onclick="window.location='wishlist.jsp';" value="Wishlist" />
    <input class="singleButton" type="button" onclick="window.location='inv.jsp';" value="Fatture" />
    
<% } %>


	<!-- Bottone di log-out, � indipendente dagli altri -->
	<form method="POST" action="LogOutServlet">
		<input class="logoutButton" type="submit" value="Log-out" />	
	</form>
	<% if(!user.isAdministrator()) { %>
		<!-- Bottone per cancellare il proprio accounts -->    
    	<button class="removeButton">Elimina l'account</button>
<% }%> 

	
</div>
</div>
<script type="text/javascript" src="./js/removeUser.js"></script>

<div id="emptyContainer" style="display:none;"><h1 id="noProducts">Non hai ancora caricato prodotti</h1></div>
<div id="tableContainer" style="display:none;">
</div> 

<script type="text/javascript" src="./js/productForUser.js"></script>
<script type="text/javascript" src="./js/singlePage.js"></script>

</body>
<jsp:include page="footer.jsp" /> 
</div>
</html>
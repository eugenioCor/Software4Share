<%@ page language="java" contentType="text/html" 
	pageEncoding="ISO-8859-1" import="S4S_Model.*" %>

<!DOCTYPE html>
<html lang="en">

<head>
<title>Software4Share</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="Let's sell our softwares!">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="./css/header_css.css">
<link rel="stylesheet" type="text/css" href="./css/alertBox.css">

</head>

<body>

	<!-- Header -->

	<!--Prima parte dell'header-->
	<header class="header">
		<nav class="menu_nav">
			<ul class="list_header">
				<li><a id="img_logo" href="homepage.jsp">S4S</a></li>
				<!--Qui serve l'immagine del logo del sito-->
				<li><a id="logo" href="homepage.jsp">Software4Share</a></li>
				<li><a href="catalog.jsp">Catalogo</a></li>
				<!--  <li><a href="#">Categorie</a></li> -->
				<li><a href="contactUs.jsp">Contattaci</a></li>
				<!-- <li><a id="bug" href="#"></a></li> -->
				<!--Questa è l'immagine del bug-->
			</ul>
		</nav>

		<!--Seconda parte dell'header-->
		<!-- Ricerca -->
		<nav class="sub_menu">
			<ul>
				<li>
					<form  id="menu_search_form" class="menu_search_form" method="GET" action="ResearchServlet">
						<!--Form di ricerca-->
						<input type="text" name="searchParameter" class="search_input" placeholder="Cerca" required>
						<button class="menu_search_button">
						</button>
						<!--Lente di ingrandimento-->
					</form>
				</li>
				<li><span class="user"></span><a href="userPage.jsp">Utente</a></li>
				<li><span class="cart"> </span><a href="cart.jsp">Carrello</a></li> <!--Immagine del carrellino-->
				<%
					
					User user = (User) session.getAttribute("user");
					if (user==null)
					{	%>
					
    			<li><span class="login"> </span><a href="login.jsp">Login</a></li>
    			<li><span class="registration"> </span><a href="registration.jsp">Registrati</a></li>
<% } %>
			</ul>
		</nav>
	</header>
	<script type="text/javascript" src="./js/singlePage.js"></script>
</body>
</html>
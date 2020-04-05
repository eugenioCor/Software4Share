<%@ page language="java" contentType="text/html" 
	pageEncoding="ISO-8859-1" import="S4S_Model.*" %>
  
<!DOCTYPE html>

<html lang="en">

<head>
	<jsp:include page="header.jsp" /> 
    <title>Software4Share</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Let's sell our softwares!">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="./css/homepage_css.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script type="text/javascript" src="./Jquery/jQuery.js"></script>
    <script type="text/javascript" src="./js/catalogLoader.js"></script>
</head>

<body>

<!-- La select per le categorie, quando ne scelgo una viene effettuata una chiamata ajax e la tabella si aggiorna  -->
<div class="categorySelectorContainer">

<form>
   <select name="category" id="categorySelector">
   </select>
</form>
</div>

<!-- Questo cambia in base a se è la pag iniziale o una categoria specifica -->
<div class="markedContainer">
	<h2 id="marked"> A - Z </h2>
</div>
<div id="hideable">


<div class="product-section">
<div class="product-container">
				
</div>
            
</div>

</div>




<!-- Il primo è lo script per quando clicco su una categoria, il secondo è per riempire la select e la pagina iniziale -->
<script type="text/javascript" src="./js/dynamicHome.js"></script>
<script type="text/javascript" src="./js/singlePage.js"></script>
<script type="text/javascript" src="./js/dynamicCat.js"></script>


</body>
 <jsp:include page="footer.jsp" /> 
</html>
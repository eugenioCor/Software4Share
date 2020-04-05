<%@page import="com.google.gson.Gson"%>
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
    <script type="text/javascript" src="./js/dynamicHome.js"></script>
    
</head>

<body>

    <!-- Slideshow container -->
    
<div id="hideable">
<div class="slideshow-container2">

    <!-- Full-width images with number and caption text -->
    <div class="mySlides2 fade" style="display: block;">
      <img src="./images/img1.png" style="width:100%">
    </div>
  
    <div class="mySlides2 fade">
   
      <img src="./images/img2.png" style="width:100%">
    </div>
  
    <div class="mySlides2 fade">
      
      <img src="./images/img3.png" style="width:100%">
    </div>
</div>
  


<!-- Script per far funzionare lo slideshow -->  
<script type="text/javascript" src="./js/homepage_slideshow.js"></script>

<!-- I prodotti in evidenza -->
<div class="master-container">
	<h2 id="marked">In Evidenza</h2><!-- Metti poppins - bold -->

<div class="product-section">
<div class="product-container">
				
</div>
            
</div>

</div>
</div>

<!-- Tavola contenente i prodotti in evidenza -->


<!-- Script per riempire dinamicamente la sezione in evidenza -->

<script type="text/javascript" src="./js/homePageLoader.js"></script>
<!--  Script per andare al prodotto singolo, non so se usata  -->

<script type="text/javascript" src="./js/singlePage.js"></script>

</body>
<jsp:include page="footer.jsp" /> 
</html>


 <!-- <tr class="productRow">
    <td class="productSquare">
      <div id="imageContainer">
        <p class="productName">Asino</p>
        <img src="images/donkey.png" >
          <p class="productPrice">100.000€</p>
          <p class="like"><span class="fa-heart"></span></p>
          <p class="stars"></p>
      </div>
    </td> 
  </tr> -->
<%@page import="S4S_Model.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
Product product = (Product) session.getAttribute("singleProduct");;
if (product == null)
{	
    response.sendRedirect("homepage.jsp");
    return;
}
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Pagina Prodotto</title>
    <link rel="stylesheet" href="./css/productPageStyle.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script src="./Jquery/jQuery.js"></script>
   	<script src="./js/productPageLoader.js"></script>
</head>

<body>
<jsp:include page="./header.jsp"/> 
    <div class="master-container">
        <div class="title-box">
            <div class="title">Pagina Prodotto </div>

        </div>


        <div class="product-section">
            <div class="product-container">

                <div class="product-grid">
                    <div class="box">
                        <div class="box__content">
                            <div class="slideshow-container"></div>
                            <br>
                            <div class="dots"></div>
                        </div>
                    </div>

                    <div class="box2">
                        <div class="box__content2">
                            <h1 class="product-name"></h1>
                               <div class="rating">
                               	 <div class="rating-stars">
                                
                            		</div>
                            		<div class="rating-txt">
                                
                            		</div>
                            	</div>
                            <h3 class="product-price"></h3><span class="money-euro"></span>
                            <div class="product-seller">
                            	<h3 class="product-sellet-head">Venditore:</h3>
                            	<p class="seller-name"></p>
                            </div>
                            
                            <div class="comment-box">
                                <span class="plus-button"></span>
                                <h3 class="comment-header">Aggiungi un commento</h3>
                                <div class="comment-area">

                            </div>
                            </div>
                            <div class="description-box">
                                <h3 class="description-txt">Descrizione Prodotto</h3>
                                
                            </div>

                            <div class="button-container">
                                <button class="button-style" id="wishlist-button">Aggiungi a lista desideri</button>
                                <button class="button-style" id="cart-button">Aggiungi a carrello</button>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="comment-zone">
                </div>
            </div>


        </div>
    </div>


    <script src="./js/productPageControl.js"></script>
    <script type="text/javascript" src="./js/singlePage.js"></script>
    <jsp:include page="footer.jsp" /> 
</body>

</html>
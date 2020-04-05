

var slideIndex = 1;
function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex - 1].style.display = "block";
    dots[slideIndex - 1].className += " active";
}

function generatePageProduct(user,product,review,avgReview)
{
	var slider = $(".slideshow-container");
	var dots = $(".dots");
	var i=1;			
	$(product.images).each(function(){
		var image = $("<div class='mySlides fade'><img class='product-image' src='"+this+"' alt='immagine non disponibile'></div>");
		var dot = $("<span class='dot' onclick='currentSlide("+i+")'></span>");
		$(slider).append(image);
		$(dots).append(dot);
		i++;
	});
	var button = $(" <a class='prev' onclick='plusSlides(-1)'>&#10094;</a><a class='next' onclick='plusSlides(1)''>&#10095;</a>");
	$(slider).append(button);
	
	$(".product-name").append(product.name);
	$(".product-price").append("Prezzo: "+product.price.toFixed(2));
	var description = $("<textarea name='comment' id='comment-txt' disabled='disabled'>"+product.description+"</textarea>");
	$(".description-box").append(description);
	
	$(".seller-name").append(product.seller.userName);
	var rating= $(".rating-stars");
	if(review.length==0)
	{
			var ratingTxt=$("<p class='rating-txt'>0 voti su 0</p>");
				$(".rating-txt").append(ratingTxt);		
			 	    				
					var ratingStar="";
			for(i=5;i>=1;i--)
				{
					ratingStar=$("<input type='radio' id='star"+i+"' name='rating' value="+i+" class='starStyle star-control'><label class='full' for='star"+i+"'></label>");
					$(rating).append(ratingStar);		
				}
			
			$(".comment-zone").append($("<h3 class='reviews-txt'>Non ci sono recensioni</h3>"));
	}
	else if(review.length>0)
	{
		
		
		var voti=review.length;
		var votanti=0;
		$(review).each(function(){
			if(this.rating>0)
				{
					votanti++;
					
				}
		});
	
		var ratingTxt=$("<p class='rating-txt'>"+votanti+" voti su "+voti+"</p>");
				$(".rating-txt").append(ratingTxt);		
			 	    				
					var ratingStar="";
			for(i=5;i>=1;i--)
				{
					ratingStar=$("<input type='radio' id='star"+i+"' name='rating' value="+i+" class='starStyle star-control'><label class='full' for='star"+i+"'></label>");
					$(rating).append(ratingStar);		
				}
			$("#star"+avgReview).prop("checked", true);
		$(".comment-zone").append($("<h3 class='reviews-txt'>Recensioni</h3>"));
		var commentTable=$("<table class='comment-table'><thead ><tr class='cmnt-tb-row comment-table-head'><th class='cmnt-tb-th'>Acquirente</th><th class='cmnt-tb-th'>Rating</th><th class='cmnt-tb-th'>Commento</th></tr></thead><tbody class='comment-table-body'></tbody></table>");
	
		$(".comment-zone").append(commentTable);
		if(user!=null)
			{
				if(user.administrator)
				{
					$(".comment-table-head").append("<th class='cmnt-tb-th comment-trash-th'>Elimina recensioni</th>");
				}
			}
	
		
		var dinamicName=0
		$(review).each(function(){
			
			
			var commentTableRow=$("<tr class='cmnt-tb-row'> </tr>");
			$(".comment-table-body").append(commentTableRow);
			$(commentTableRow).append("<td class='cmnt-tb-td' data-label='Acquirente'>"+this.user.userName+"</td>");
			$(commentTableRow).append("<td class='cmnt-tb-td' data-label='Rating'><div id='rating-table-"+dinamicName+"' class='rating-table'></div></td>");
			if(this.comment=="")
				{
				$(commentTableRow).append("<td class='cmnt-tb-td comment-txt-td' data-label='Commento'>Nessun commento</td>");
				}
			else
				{
				$(commentTableRow).append("<td class='cmnt-tb-td comment-txt-td' data-label='Commento'>"+this.comment+"</td>");	
				}
			if(user!=null)
				{
					if(user.administrator)
					{
						$(commentTableRow).append("<td class='cmnt-tb-td comment-trash-td' data-label='Elimina recensione'><span class='comment-trash' id='trash-"+dinamicName+"'></span></td>");	
					}
				}
	
			var commentStar="";
			for(i=5;i>=1;i--)
			{
				
				if(i==this.rating)
					{
					commentStar=" <input class='starStyle' type='radio'  name='rating"+dinamicName+"' value='"+i+"' checked/><label class='full2'></label>"
					}
				else
					{
					commentStar=" <input class='starStyle' type='radio'  name='rating"+dinamicName+"' value='"+i+"'/><label class='full2'></label>"	
					}
				
				$("#rating-table-"+dinamicName).append(commentStar);
			}
			dinamicName++;
			
		});
		
		$(".comment-trash").each(function(){
			
			$(this).click(function(){
				
				var id=$(this).attr("id");
				id=id.substring(id.length-1);
				id=review[id].idReview;
				
				$.post("ProductPageServlet",{idReview:id},function(data){
					 clearPage();
					loadPage();
				}).fail(function(){alertBox("Si &egrave; verificato un errore")});
			});
		});
	}
	
	starsAndComment(user,product,review);
		
}

function starsAndComment(user,product,review)
{
	var radioB=$(".star-control");
	var commentButton=$(".plus-button");
	var whishButton=$("#wishlist-button");
	
	if(user==null || user.userName==product.seller.userName||user.administrator)
	{
		
		var stringStar="Devi essere loggato per poter dare un voto al prodotto";
		var stringCommentButton="Devi essere loggato per poter esprimenre un commento sul prodotto";
		var stringWishButton="Devi essere loggato per poter aggiungere il prodotto alla wishlist";
		if(user==null)
			{
				cartButtonFunc();
			}
		if(user!=null)
			{
				var stringCartButton = "";
				if(user.userName==product.seller.userName)
				{
					stringStar="Sei il venditore, non puoi votarti da solo";
					stringCommentButton="Sei il venditore,non puoi esprimenre un commento sul tuo prodotto";
					stringWishButton="Sei il venditore,non puoi aggiungere il tuo prodotto alla wishlist";
					stringCartButton = "Sei il venditore,non puoi comprare i tuoi prodotti";
				}
				else if(user.administrator)
					{
						stringStar="Sei l'amministratore,non puoi votare gli utenti del tuo sito";
						stringCommentButton="Sei l'amministratore,non puoi esprimenre un commento sui prodotti del tuo sito";
						stringWishButton="Sei l'amministratore,non puoi aggiungre alla wishlist i prodotti del tuo sito";
						stringCartButton = "Sei l'amministratore,non puoi comprare prodotti";
					}
				
				$("#cart-button").click(function() 
				{
					alertBox(stringCartButton); 
				});
			}
		
		
		$(radioB).each(function(){
		 	$(this).click(function(){
		 	
		 		alertBox(stringStar);
		 		return false ;
		 	});
		});
		
		
		$(commentButton).click(function() 
		{
		 	alertBox(stringCommentButton);
		});
		
		$(whishButton).click(function() 
		{
			alertBox(stringWishButton);
			return false ;
		});
	}
	else
	{
		$(radioB).each(function(){
			$(this).click(function(){
				$.post("ProductPageServlet",{star:$(this).val()},function(){
					
					clearPage()
					loadPage();
				}).fail(function(){alertBox("Si &egrave; verificato un errore")});
			});
		});
		
		wishButtonFunc();
		cartButtonFunc();
		
		$(".plus-button").click(function() {
			var comment="";
		   
			$(review).each(function(){
				
				if(this.user.userNumber==user.userNumber)
					{
						comment=this.comment;	
					}
			});
			var textArea = $("<textarea name='comment' id='comment-txt'>"+comment+"</textarea><button class='button-style' id='comment-button'>Invia commento</button>");

		    if ($(".comment-area").children().length == 0) {
		        $(".comment-area").append(textArea);
		    } else {
		        $(".comment-area").children().remove();
		    }
			
		
			$("#comment-button").click(function() {
				
				var commentTxt=$("#comment-txt").val();
				$.post("ProductPageServlet",{comment:commentTxt},function(data){
					 clearPage()
					loadPage();
				}).fail(function(){alertBox("Si &egrave; verificato un errore")});
			});
		});

	}
}


function clearPage()
{
	$(".product-section").remove();
	$(".master-container").append("<div class='product-section'> <div class='product-container'><div class='product-grid'><div class='box'> <div class='box__content'><div class='slideshow-container'></div> <br><div class='dots'></div></div></div><div class='box2'> <div class='box__content2'> <h1 class='product-name'></h1><div class='rating'><div class='rating-stars'></div><div class='rating-txt'></div></div><h3 class='product-price'></h3><span class='money-euro'></span><div class='product-seller'><h3 class='product-sellet-head'>Venditore:</h3><p class='seller-name'></p></div><div class='comment-box'><span class='plus-button'></span><h3 class='comment-header'>Aggiungi un commento</h3><div class='comment-area'></div></div><div class='description-box'><h3 class='description-txt'>Descrizione Prodotto</h3></div><div class='button-container'><button class='button-style' id='wishlist-button'>Aggiungi a lista desideri</button><button class='button-style' id='cart-button'>Aggiungi a carrello</button></div></div></div></div><div class='comment-zone'></div></div></div>");
}

function cartButtonFunc()
{

	$("#cart-button").click(function(){
		$.post("ProductPageServlet",{product:"cart"},function(data){
			alertCart(data);
		}).fail(function(){alertBox("Si &egrave; verificato un errore")});
	});

}

function wishButtonFunc()
{
	$("#wishlist-button").click(function(){
		$.post("ProductPageServlet",{product:"wish"},function(data){
			
			if(data)
				{
				alertWish("Prodotto già inserito nella wishlist");
				
				}
			else
				{
				alertWish("Prodotto inserito correttamente nella wishlist");
				}
			
		}).fail(function(){alertBox("Si &egrave; verificato un errore")});
	});
}


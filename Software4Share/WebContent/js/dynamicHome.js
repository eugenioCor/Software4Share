/*AJAX e Json per ottenere dinamicamente dal database i prodotti da mettere in evidenza sul sito*/
function loadButtons(products,user)
{
	$(".heart-icon").click(function(){
		var id=$(this).parent().parent().find(".rating-table").attr("id").replace("rating-table-","");
		if(user==null)
			{
				alertLog("Non puoi aggiungere prodotti alla lista desideri se non sei loggato");
			}
		else
			{
			if(user.userName==products[id].seller.userName)
			{
				alertBox("Non puoi aggiungere i tuoi prodotti alla lista desideri");
			}
			else if(user.administrator)
				{
					alertBox("Sei l'amministratore non puoi aggiungere prodotti alla lista desideri");
				}
			else
			{
				$.post("DynamicHomepageServlet",{idProduct:products[id].idProduct,idUser:user.userNumber,type:"wish"},function(data){
					if(data)
					{
						alertWish("Prodotto gi&agrave; inserito nella wishlist");
					
					}
				else
					{
						alertWish("Prodotto inserito correttamente nella wishlist");
					}
					}).fail(function(){alertBox("si &egrave; verificato un errore");});
				
			}

			}
		
	});
	
	$(".cart-icon").click(function(){
		
			if(user==null)
				{
				var id=$(this).parent().parent().find(".rating-table").attr("id").replace("rating-table-","");
				var idProduct=products[id].idProduct;
				$.post("DynamicHomepageServlet",{idProduct:idProduct,type:"cart"},function(data){
					alertCart(data);
				}).fail(function(){alertBox("si &egrave; verificato un errore");});
				}
			else
				{
				var id=$(this).parent().parent().find(".rating-table").attr("id").replace("rating-table-","");
				var idProduct=products[id].idProduct;
				
					if(user.administrator)
						{
							alertBox("Sei l'amministratore non puoi acquistare i prodotti");
						}
					else if(user.userName==products[id].seller.userName)
						{
						alertBox("Sei il venditore non puoi acquistare i tuoi prodotti");
						}
					else
						{
						$.post("DynamicHomepageServlet",{idProduct:idProduct,type:"cart"},function(data){
							alertCart(data);
						}).fail(function(){alertBox("si &egrave; verificato un errore");});
						}
				}
			
			
		
	});
}
function setVoidPage()
{
	$(".product-container").append($("<div class='noProductContainer'><h1 class='noProducts-txt'>Non ci sono prodotti</h1></div>"));
}

function loadComponents()
{
	$(".slides").each(function() {
	    var i = 0;
	    $(this).children().each(function() {
	        if (i == 0) {
	            $(this).children().css("display", "block");
	        } else {
	            $(this).children().css("display", "none");
	        }
	        i++;
	    });

	});
	$(".dots").each(function() {
	    $(this).children().first().addClass("active");
	});
}
function clearPage()
{
	$(".product-grid").remove();
	$(".noProductContainer").remove();
}

function generateHomePage(products,reviews)
{
	
	 $(".product-container").append("<div class='product-grid'></div>");
	var grid=$(".product-grid");
	var boxes=0;
	$(products).each(function(){
		var box="<div class='box'><div class='box__content'><div class='image-box'><div class='slideshow-container'><div class='slides' id='slide-"+boxes+"'></div><a class='prev' onclick='plusSlides(this)'>&#10094;</a><a class='next' onclick='plusSlides(this)'>&#10095;</a></div><br><div class='dots' id='dots-"+boxes+"'></div></div><div class='product-Information'><h1 class='product-name'>"+this.name+"</h1><div class='rating-table' id='rating-table-"+boxes+"'></div><h3 class='product-price'>Prezzo: "+this.price+" &euro;</h3><h3 class='product-price'>"+this.seller.userName+"</h3><div class='description-box'><h3 class='description-txt'>Descrizione Prodotto</h3><textarea name='description' id='description-txt' disabled>"+this.description+"</textarea></div><div class='control-button'><span class='heart-icon'></span><span class='cart-icon'></span></div></div></div>";
		$(grid).append(box);
		var i = 0;
		$(this.images).each(function(){
		
			var image="<div class='mySlides fade'><img class='product-image first' src='"+this+"' alt='immagine non disponibile' onclick='sendImage(this)'></div>";
			var dot="<span class='dot' onclick='currentSlide(this,"+i+")'></span>";
			$("#slide-"+boxes+"").append(image);
			$("#dots-"+boxes+"").append(dot);
			i++;
		});
		
		
		boxes++;
		
	
	});
	
	var dinamicName=0;
	$(reviews).each(function(){
		
		for(i=5;i>=1;i--)
		{
		
			if(i==this)
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
}
function currentSlide(inp, pos) {

    var slide = $(inp).parent().prev().prev().find(".slides").children();
    var dots = $(inp).parent().children();
    var k;
    for (var j = 0; j < slide.length; j++) {
        $(slide[j]).children().css("display", "none");
        $(dots[j]).removeClass("active");
    }

    $(slide[pos]).children().css("display", "block");
    $(dots[pos]).addClass("active");
}

function plusSlides(inp) {
    var slide = $(inp).parent().find(".slides").children();
    var dots = $(inp).parent().next().next().children();
    if ($(inp).hasClass("next")) {
        var k;
        for (var j = 0; j < slide.length; j++) {
            $(dots[j]).removeClass("active");
            if ($(slide[j]).children().css("display") == "block") {
                k = j;
            }
        }
        if (k >= slide.length - 1) {
            $(slide[k]).children().css("display", "none");
            $(slide[0]).children().css("display", "block");
            $(dots[0]).addClass("active");
        } else {
            $(slide[k]).children().css("display", "none");
            $(slide[k + 1]).children().css("display", "block");
            $(dots[k + 1]).addClass("active");
        }

    } else if ($(inp).hasClass("prev")) {
        var k;
        for (var j = 0; j < slide.length; j++) {
            $(dots[j]).removeClass("active");
            if ($(slide[j]).children().css("display") == "block") {
                k = j;
            }
        }
        if (k <= 0) {
            $(slide[k]).children().css("display", "none");
            $(slide[slide.length - 1]).children().css("display", "block");
            $(dots[slide.length - 1]).addClass("active");
        } else {
            $(slide[k]).children().css("display", "none");
            $(slide[k - 1]).children().css("display", "block");
            $(dots[k - 1]).addClass("active");
        }

    }
}
function imageEffect()
{
	$(".product-image").hover(function(){$(this).css({"opacity": "1","-webkit-transition": ".3s ease-in-out","transition": ".3s ease-in-out"})},function(){$(this).css({"opacity": "0.80","-webkit-transition": ".3s ease-in-out","transition": ".3s ease-in-out"})});
}

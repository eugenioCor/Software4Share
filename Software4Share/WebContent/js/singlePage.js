function sendImage(inp) 
{
	var src = inp.src;
    var indexToCut = src.search("resource");
    src=src.substring(indexToCut);
   
    $.post("SingleProductServlet", {image:src}, function(data) 
	   			{
	   				window.location.href=data;
  	           }).fail(function() {
  	        	 alertBox("si &egrave; verificato un errore");
     });
}

function alertCart(size)
{
	
	var content="";
	if(size==1)
		{
			content="C'&egrave; un solo prodotto nel carrello";
		}
	else
		{
		content="Ci sono "+size+" prodotti nel carrello";
		}
	var link="'cart.jsp'";
	var over="<div class='overlay'><div class='wrap'><div class='card'><div class='title-alert'>Prodotto inserito nel carrello!</div><div class='content'>"+content+"</div><div class='menu'><input type='button' value='Vai al carrello' class='accept' onclick='cartRedirect()'/><input type='button' value='Continua gli acquisti' class='cancel' onclick='clearAlert()'/></div></div></div></div>";
	
	$("body").append(over);
	var myTimer = setTimeout(function() {$(".overlay").remove();}, 10000);
}

function alertBox(txt)
{
	var over="<div class='overlay'><div class='wrap'><div class='card'><div class='title-alert'>"+txt+"</div><div class='menu'><input type='button' value='Chiudi' class='cancel' onclick='clearAlert()'/></div></div></div></div>";
	$("body").append(over);
	var myTimer = setTimeout(function() {$(".overlay").remove();}, 10000);
}

function alertWish(txt)
{
	var over="<div class='overlay'><div class='wrap'><div class='card'><div class='title-alert'>"+txt+"</div><div class='menu'><input type='button' value='Vai alla lista desideri' class='accept' onclick='wishRedirect()'/><input type='button' value='Chiudi' class='cancel' onclick='clearAlert()'/></div></div></div></div>";
	$("body").append(over);
	var myTimer = setTimeout(function() {$(".overlay").remove();}, 10000);
}
function alertLog(txt)
{
	var over="<div class='overlay'><div class='wrap'><div class='card'><div class='title-alert'>"+txt+"</div><div class='menu'><input type='button' value='Vai alla login' class='accept' onclick='loginRedirect()'/><input type='button' value='Chiudi' class='cancel' onclick='clearAlert()'/></div></div></div></div>";
	$("body").append(over);
	var myTimer = setTimeout(function() {$(".overlay").remove();}, 10000);
}
function clearAlert()
{
	$(".overlay").remove();
}

function cartRedirect()
{
	window.location.href="cart.jsp";
}
function wishRedirect()
{
	window.location.href="wishlist.jsp";
}
function loginRedirect()
{
	window.location.href="login.jsp";
}


function alertLoading()
{
	var over="<div class='overlay'><div class='wrap'><div class='card'><div class='title-alert'>Attendere...</div><div class='loader'></div></div></div></div>";	
	$("body").append(over);
}

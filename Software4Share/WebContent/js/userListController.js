function loadUsers()
{
	$.get("UserListServlet",function(data){
		generateUserList(data);
	}).fail(function(){alertBox("si &egrave; verificato un errore");});
}
function generateUserList(user)
{
	
	  var container="<div class='product-grid'></div>";
	  $(".product-container").append(container);
	  $(user).each(function(){
		  var userBox="<div class='box'>" +
		  	"<div class='box__content'>" +
		  		"<div class='product-Information'>" +
		  			"<h1 class='product-name'>Username: "+this.userName+"</h1>" +
		  			"<h1 class='product-name'>Nome: "+this.name+"</h1>" +
		  			"<h1 class='product-name'>Cognome: "+this.surname+"</h1>" +
		  			"<h1 class='product-name'>Mail: "+this.email+"</h1>" +
		  			"<h1 class='product-name'>Data di nascita: "+this.birthDay+"</h1>" +
		  			"<h1 class='product-name'>Partita iva: "+this.pIVA+"</h1>" +
		  			"<div class='control-button' id='userN-"+this.userNumber+"'><span class='trash-icon' onclick='deleteUser(this)'></span><span class='cart-icon' onclick='showProducts(this)'></span></div>" +
		  			"</div></div></div>";
		  $(".product-grid").append(userBox);
	  });
}


function showProducts(inp)
{
	var id=$(inp).parent().attr("id").replace("userN-","");
	$.post('DynamicUserProductServlet',{id:id},function(response) {
	    clearPage();
		printProductsForUser(response);
	}).fail(function() {
		alertBox("Si &egrave; verificato un errore.");
	}); 
}
function deleteUser(inp)
{
	var id=$(inp).parent().attr("id").replace("userN-","");
	$.post("RemoveUserServlet",{id:id},function(data){
		if(data)
		{
			clearPage();
			loadUsers();
		}
		else
			{
				alertBox("Impossibile eliminare l'utente almeno da dieci giorni dall'ultimo acquisto");
			}
	}).fail(function(){alertBox("si &egrave; verificato un errore")});
}

function printProductsForUser(response)
{
    var stringa = JSON.stringify(response);
    var productObject = JSON.parse(stringa);
    
    if(productObject == null || productObject == undefined)
    {
    	setVoidPage();
    }
    else
    	{
    	var productPageBase = "<div class='cart_section'><div class='cart_containter'><table class='cart-table'><thead><tr class='crt-tb-row'> <th class='crt-tb-th' scope='col' colspan='3'>Prodotto</th><th class='crt-tb-th' scope='col'>Venditore</th><th class='crt-tb-th' scope='col'>Categoria</th><th class='crt-tb-th' scope='col'>Prezzo</th><th class='crt-tb-th' scope='col'>IVA</th><th class='crt-tb-th' scope='col'>Sconto</th></tr></thead><tbody class='table-body'></tbody></table>";
		$(".product-container").append(productPageBase);
	var cartBody=$(".table-body");
		$(productObject).each(function(){
			
			$(cartBody).append("<tr class='crt-tb-row'> <td class='crt-tb-td' data-label='Prodotto' colspan='3'> <div class='products'> <table class='product_info_table'><tr><td id='prd-tb-check'><span class='removeProduct' onclick='removeProduct(this)'>          </span></td><td id='prd-tb-img-"+this.idProduct+"'><img class='product-image' onclick='sendImage(this)' src='"+this.images[0]+"' alt='immagine non disponibile'></td><td id='prd-tb-dscr'><div id='prd-tb-descr-"+this.idProduct+"'><h3 class='product-Name'>"+this.name+"</h3> <h3 class='product-desc'>Descrizione:</h3><textarea name='description' class='description-txt' disabled>"+this.description+"</textarea></div></td> </tr></table> </div></td><td class='crt-tb-td' data-label='Venditore'>"+this.seller.userName+"</td><td class='crt-tb-td' data-label='Categoria'>"+this.category+"</td><td class='crt-tb-td price' data-label='Prezzo' id='price'>" +  this.price.toFixed(2) + "</td><td class='crt-tb-td iva' data-label='IVA'>"+this.iva+"%</td><td class='crt-tb-td quantity' data-label='Quantit&agrave;'>"+this.sale+" %</td></tr>");

		});
		productObjcet = "";
    	}
    
}

function setVoidPage()
{
	$(".product-container").append($("<div class='noProductContainer'><h1 class='noProducts-txt'>Non ci sono prodotti</h1></div>"));
}
function clearPage()
{
	$(".product-grid").remove();
	$(".noProductContainer").remove();
	$(".cart_section").remove();
	
}
function removeProduct(inp)
{
	
	var id=$(inp).parent().parent().parent().find(".product-image").parent().attr("id").replace("prd-tb-img-","");
	
	
	$.post('RemoveProductServlet', { productName :id}, function(response) {
		 
		var stringa = JSON.stringify(response);
	    var counter = JSON.parse(stringa);
	    
		$(inp).parents('.crt-tb-row').remove();
	
		if(counter == 0)
		{
			clearPage();
			setVoidPage();
			
		}
	}).fail(function () {
		alertBox("Si &egrave; verificato un errore."); 
	}); 
	 
}

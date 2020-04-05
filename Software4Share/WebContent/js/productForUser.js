/* Per riempire la tabella che permetta lo show dei prodotti di un utente-venditore */

//QUI FUNGE
$(document).ready(function() {
    $.get('DynamicUserProductServlet', function(response) {
        printProductsForUser(response);
    }).fail(function() {
    	alertBox("Si &egrave; verificato un errore.");
    }); 
});

function removeProduct(inp)
{
	
	var id=$(inp).parent().parent().parent().find(".product-image").parent().attr("id").replace("prd-tb-img-","");
	
	
	$.post('RemoveProductServlet', { productName :id}, function(response) {
		 
		var stringa = JSON.stringify(response);
	    var counter = JSON.parse(stringa);
	    
		$(inp).parents('.crt-tb-row').remove();
		
		if(counter == 0)
		{
			$("#tableContainer").hide();
			$("#emptyContainer").show();
			
		}
	}).fail(function () {
		alertBox("Si &egrave; verificato un errore."); 
	}); 
	 
}


function printProductsForUser(response)
{
    var stringa = JSON.stringify(response);
    var productObject = JSON.parse(stringa);
    
    if(productObject == null || productObject == undefined)
    {
    	$("#tableContainer").hide();
    	return;
    }
    var productPageBase = "<div class='cart_section'><div class='cart_containter'><table class='cart-table'><thead><tr class='crt-tb-row'> <th class='crt-tb-th' scope='col' colspan='3'>Prodotto</th><th class='crt-tb-th' scope='col'>Venditore</th><th class='crt-tb-th' scope='col'>Categoria</th><th class='crt-tb-th' scope='col'>Prezzo</th><th class='crt-tb-th' scope='col'>IVA</th><th class='crt-tb-th' scope='col'>Sconto</th></tr></thead><tbody class='table-body'></tbody></table>";
		$("#tableContainer").append(productPageBase);
	var cartBody=$(".table-body");
		$(productObject).each(function(){
			
			$(cartBody).append("<tr class='crt-tb-row'> <td class='crt-tb-td' data-label='Prodotto' colspan='3'> <div class='products'> <table class='product_info_table'><tr><td id='prd-tb-check'><span class='removeProduct' onclick='removeProduct(this)'>          </span></td><td id='prd-tb-img-"+this.idProduct+"'><img class='product-image' onclick='sendImage(this)' src='"+this.images[0]+"' alt='immagine non disponibile'></td><td id='prd-tb-dscr'><div id='prd-tb-descr-"+this.idProduct+"'><h3 class='product-Name'>"+this.name+"</h3> <h3 class='product-desc'>Descrizione:</h3><textarea name='description' class='description-txt' disabled>"+this.description+"</textarea></div></td> </tr></table> </div></td><td class='crt-tb-td' data-label='Venditore'>"+this.seller.userName+"</td><td class='crt-tb-td' data-label='Categoria'>"+this.category+"</td><td class='crt-tb-td price' data-label='Prezzo' id='price'><input class='control-upload control inputStyle' type='number' step='any' autocomplete='off' id='productPrice' name='price' placeholder=" +  this.price.toFixed(2) + " onchange='changePrice(this)' /></td><td class='crt-tb-td iva' data-label='IVA'>"+this.iva+"%</td><td class='crt-tb-td quantity' data-label='Quantit&agrave;'><div class='text_quantity'><div class='add_quantity' onclick='addQuantity(this)'>+</div><div class='quantity qt'>"+this.sale+" %</div><div class='remove_quantity' onclick='removeQuantity(this)'>-</div></div></td></tr>");

		});
		productObjcet = "";
}

function changePrice(inp)
{
	
	var id=$(inp).parent().parent().find(".product-image").parent().attr("id").replace("prd-tb-img-","");
	 
	 if(!checkPrice(inp))
		 {
		  $.post('ChangePriceServlet', { productName : id, newPrice :$(inp).val() }, function(response) {
		    	//nothing?
		    }).fail(function() {
		    	alertBox("Si &egrave; verificato un errore."); 
		    });
		 	 
		 }
	 
}

function showProducts()
{
	if ($(".table-body").length != 0)
	{
		$( "#tableContainer" ).slideToggle();
	}
	else
	{
		$("#noProducts").show();
		$("#emptyContainer").slideToggle();
		//$("#noProducts").slideToggle();
		//$("#emptyContainer").show();
	}
}

function addQuantity(inp) {

    var quantity = parseFloat($(inp).next().html());
    var maxValue = 100; 
    var id=$(inp).parent().parent().parent().find(".product-image").parent().attr("id").replace("prd-tb-img-","");
    
    if (quantity < maxValue) {
        quantity+=5;
        $(inp).next().html(quantity+" %");
        $.post('ChangeSaleServlet', {productName : id, newSale : quantity }, function(response) {
            //do nothing
    }).fail(function() {
    	alertBox("Si &egrave; verificato un errore.");
    });
    }
}

function removeQuantity(inp) {

    var quantity = parseFloat($(inp).prev().html());
    var id=$(inp).parent().parent().parent().find(".product-image").parent().attr("id").replace("prd-tb-img-","");
    if (0 < quantity) {
        quantity-=5;
        $(inp).prev().html(quantity+" %");
        $.post('ChangeSaleServlet', {productName : id, newSale : quantity }, function(response) {
            //do nothing
    }).fail(function() {
    	alertBox("Si &egrave; verificato un errore.");
    });
}
}
function checkPrice(inp) {
    var check = false;
    var priceConv=""+$(inp).val();
    
    if (priceConv.length >= 12) {
        check = true;
        alertBox( "Il prezzo ha un numero di cifre troppo elevato");
    } else {
        priceConv = parseFloat(priceConv);
        if (isNaN(priceConv)) {
            check = true;
            alertBox("Hai inserito dei caratteri non validi");
        } else {
        	
            $(inp).val(priceConv.toFixed(2));
           
        }

    }
    return check;
}



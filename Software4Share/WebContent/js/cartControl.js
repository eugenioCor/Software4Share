
total();

function addQuantity(inp) {

    var quantity = parseFloat($(inp).next().html());
    var maxValue = 999; 

    if (quantity < maxValue) {
        quantity++;
        $(inp).next().html(quantity);
        totalPrice(inp, quantity);
    }
}

function removeQuantity(inp) {

    var quantity = parseFloat($(inp).prev().html());

    if (1 < quantity) {
        quantity--;
        $(inp).prev().html(quantity);
        totalPrice(inp, quantity);
    }
}

function totalPrice(input, num) {

    var price = parseFloat($(input).parents().filter(".crt-tb-row").children().filter(".price").text());
    var totalPrice = price * num;
    var sale = parseFloat($(input).parents().filter(".crt-tb-row").children().filter(".sale").text())
    var iva = parseFloat($(input).parents().filter(".crt-tb-row").children().filter(".iva").text())
    totalPrice -= (totalPrice * sale) / 100;
    totalPrice += (totalPrice * iva) / 100;

    $(input).parents().filter(".crt-tb-row").children().filter(".totalPrice").text(totalPrice.toFixed(2) + " \u20ac"); //il toFixed serve per indicare che voglio solo due cifre dopo la virgola

    total();
}

function total() {

    var total = 0;
    $(".priceSelected").each(function() {
        total += parseFloat($(this).text());
    });
    $(".total").text(total.toFixed(2) + " \u20ac");
}

function isAllSelected() {
    var allChecked = true;
    $(".singleSelect").each(function() {
        if (!$(this).prop("checked")) {
            allChecked = false;
        }
    });
    return allChecked;
}

function isAllDeselected(){
	var deselected=true;
	$(".singleSelect").each(function() {
        if ($(this).prop("checked")) {
        	deselected = false;
        }
    });
	return deselected;
}
function toggleAllSelection() {
    if (isAllSelected()) {

        $(".selectAll-check").prop("checked", true);

    } else {
        $(".selectAll-check").prop("checked", false);
    }
}

function unSelectProduct(inp) {
    if (!$(inp).prop("checked")) {
        $(inp).parents().filter(".crt-tb-row").children().filter(".totalPrice").removeClass("priceSelected");
    } else {
        $(inp).parents().filter(".crt-tb-row").children().filter(".totalPrice").addClass("priceSelected")
    }
    total();
}

function toggleSelection() {
    if ($(".selectAll-check").prop("checked")) {
        $(".singleSelect").each(function() {
            $(this).prop("checked", true);
            unSelectProduct(this);
        });
    } else {
        $(".singleSelect").each(function() {
            $(this).prop("checked", false);
            unSelectProduct(this);
        });
    }
}

function setVoidPage()
{
	$(".master-container").append($("<div class='noProductContainer'><h1 class='noProducts-txt'>Non ci sono prodotti</h1></div>"));
}
function buildCartPage(products,user)
{
	prod=products;
	var cartPageBase = "<div class='cart_section'><div class='cart_containter'><form id='send-form' action='DownloadServlet' method='post'><table class='cart-table-selectAll'><thead><tr class='crt-tb-row'><th class='crt-tb-th' scope='col'>Selezionare tutti i prodotti nel carrello</th><th class='crt-tb-th' scope='col'><input type='checkbox' id='selectedItem' name='selectedAllItem' onchange='toggleSelection()' class='selectedItemSwitch selectAll-check' checked /></th></tr></thead></table><table class='cart-table'><thead><tr class='crt-tb-row'> <th class='crt-tb-th' scope='col' colspan='3'> prodotto</th><th class='crt-tb-th' scope='col'>venditore</th><th class='crt-tb-th' scope='col'>Categoria</th><th class='crt-tb-th' scope='col'>Prezzo</th><th class='crt-tb-th' scope='col'>Sconto</th><th class='crt-tb-th' scope='col'>IVA</th><th class='crt-tb-th' scope='col'>Quantit&agrave;</th><th class='crt-tb-th' scope='col'>Totale</th></tr></thead><tbody class='cart-tb-body'></tbody></table><table class='cart-table-total'><thead><tr class='crt-tb-row'><th class='crt-tb-th' scope='col'>Prezzo totale da pagare</th><th class='crt-tb-th total' scope='col'></th><th class='crt-tb-th' scope='col'><input type='button' value='Procedi al checkOut' class='styleButton checkOut'></th></tr></thead></table></form>"
		$(".master-container").append(cartPageBase);
	var cartBody=$(".cart-tb-body");
	var rowId=0;
		$(products.productList).each(function(){
			
			$(cartBody).append("<tr class='crt-tb-row'> <td class='crt-tb-td' data-label='Prodotto' colspan='3'> <div class='products'> <table class='product_info_table'><tr><td id='prd-tb-check'> <input type='checkbox' name='selectedItem' class='selectedItemSwitch singleSelect' onchange='toggleAllSelection(); unSelectProduct(this);' checked/><span class='removeProduct' onClick='removeProduct(this)'></span></td><td id='prd-tb-img-"+rowId+"'><img class='product-image' onclick='sendImage(this)' src='"+this.product.images[0]+"' alt='immagine non disponibile'></td><td id='prd-tb-dscr'><div id='prd-tb-descr-"+rowId+"'><h3 class='product-Name'>"+this.product.name+"</h3> <h3 class='product-desc'>Descrizione:</h3><textarea name='description' class='description-txt' disabled>"+this.product.description+"</textarea></div></td> </tr></table> </div></td><td class='crt-tb-td' data-label='Venditore'>"+this.product.seller.userName+"</td><td class='crt-tb-td' data-label='Categoria'>"+this.product.category+"</td><td class='crt-tb-td price' data-label='Prezzo' id='price'>"+this.product.price.toFixed(2)+"&euro;</td><td class='crt-tb-td sale' data-label='Sconto'>"+this.product.sale+"%</td><td class='crt-tb-td iva' data-label='IVA'>"+this.product.iva+"%</td><td class='crt-tb-td quantity' data-label='Quantit&agrave;'><div class='text_quantity'><div class='add_quantity' onclick='addQuantity(this)'>+</div><div class='quantity qt'>"+this.quantity+"</div><div class='remove_quantity' onclick='removeQuantity(this)'>-</div></div></td><td class='crt-tb-td totalPrice priceSelected' name='tot' data-label='totale'></td></tr>");
			
			rowId++;
		});
		
		$(".totalPrice").each(function() {
			var val=$(this).parent().find("div.quantity").text();
			totalPrice(this, val);
		});
		
		if(user==null)
			{
				$(".checkOut").click(function(){
					alertLog("Per acquistare prodotti devi essere loggato");
					window.location.href="login.jsp";
				});
			}
		else if(user!=null)
			{
				$(".checkOut").click(function(){
					if(!isAllDeselected())
						{
							updateCart();
							hidePage();
							loadPaymentPart();
						}
					else{
						alertBox("Non hai selezionato nessun prodotto");
					}
					
					
				});
			}
}
var prod;
function updateCart()
{
	var quant=[];
	$(".qt").each(function(){
		quant.push($(this).text());
	});
	$.post("CartServlet",{quantity:JSON.stringify(quant)},function(){
		
	}).fail(function(){alertBox("si &egrave; verificato un errore");});
}
function removeProduct(inp)
{
	var id=$(inp).parent().next().attr("id");
	id=id.replace("prd-tb-img-","");
	delete prod.productList[id];
	
	$.post("CartServlet",{pos:id},function(data){
		 clearPage();
		if(data.productList.length==0)
			{
				setVoidPage();
			}
		else
			{
				buildCartPage(data);
			}
		
	}).fail(function(){ alertBox("si &egrave; verificato un errore");});
	
}
toggleAllSelection();

function clearPage()
{
	$(".cart_section").remove();
	
}

function hidePage()
{
	$(".cart_section").hide();
	
}


function loadPaymentPart()
{
	var paymentPart="<div class='contaier'><div class='form-container'><form id='checkout-form' class='form-body'><div class='payment-container'><h2 class='serviceBilling'>Informazioni di pagamento</h2><p class='payment-txt-info'>Inserire i dati del proprietario della carta</p> <div id='dropin-container'></div><div class='clear'></div><div id='submit_button_div'><input type='submit' class='btn btn-warning btn-large btn-style' id='signup_submit_button' value='Paga "+$('.total').text()+"'></div></div></form></div></div> </div>"
	$(".master-container").append(paymentPart);
	loadPayementScript();
}

function loadPayementScript()
{
    var $form = $('#checkout-form');
    braintree.setup(
        'sandbox_mfks3dd7_zp6jn5gm4nfyrn9m',
        'dropin', {
            container: 'dropin-container',

            paypal: {
                amount: $("total").text(), //inserire totale 
                currency: 'EUR',
                button: {
                    type: 'checkout'
                }
            },
            form: 'checkout-form',
            onPaymentMethodReceived: function(payload) {
               
                $("#signup_submit_button").attr("type","button");
                $("#signup_submit_button").attr("value","continua");
                $("#signup_submit_button").click(function(){
                	
                	$(".contaier").remove();
                	clearPage();
                	loadCart();
                });
                $("#send-form").submit();
            },
            onError: function(error) {
           
                console.error(error.type + ' Error: ', error);
            }
        });

    $form.submit(function(event) {
    	 event.preventDefault();
    	 return true;
    });
}

function loadCart()
{
	$.get("CartServlet",function(data){
		
		if(data==0 || data[0].productList.length==0)
			{
				setVoidPage();
			}
		else
			{
				buildCartPage(data[0],data[1]);
			}
		
	}).fail(function(){alertBox("si &egrave; verificato un errore");});
}


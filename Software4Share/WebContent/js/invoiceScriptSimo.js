
function invoiceFiler()
{
	var userName = $(".textInput").val();
	var fromData = $(".fromDate").val();
	var toData = $(".toDate").val();
	

	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var yyyy = today.getFullYear();

	//today = yyyy + "-" + mm + "-" + dd;
	//console.log(today);
	
	var dateFromDate = new Date(fromData);
	var dateToDate = new Date(toData);
	if(dateFromDate > dateToDate || dateFromDate > today || dateToDate > today)
	{
		alertBox("Formato delle date non valido");
	}
	else if(userName == "" && fromData == "" && toData == "")
	{
		alertBox("Non hai inserito nessun filtro");
	}
	else if(userName == "" && fromData == "")
	{
		$.post('InvoiceFilterServlet', { ToData : toData }, function(response) {
			 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

           
             $('#noInv').hide();
             $('#invoicesTable').empty();
             $('#invoicesTable').append('<tr id="infoRow">' + 
            		 				'<th class="info">Numero Fattura</th>' +
            		 					'<th class="info">Acquirente</th>' +
            		 					'<th class="info">Venditore</th>' + 
            		 					'<th class="info">Prodotti</th>' + 
            		 					'<th class="info tot">Totale</th>' + '</tr>');
            if(invoices === null)
             {
            	$('.tableContainer').hide();
            	$('#invoicesTable').hide();
        
                 $('#noInv').html("");
            
                 $('#noInv').html("Non ci sono fatture per il tuo parametro di ricerca");
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agrave;: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
	}
	else if(fromData == "" && toData == "")
	{
		$.post('InvoiceFilterServlet', { User : userName }, function(response) {
			 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

            
             $('#noInv').hide();
             $('#invoicesTable').empty();
             $('#invoicesTable').append('<tr id="infoRow">' + 
            		 				'<th class="info">Numero Fattura</th>' +
            		 					'<th class="info">Acquirente</th>' +
            		 					'<th class="info">Venditore</th>' + 
            		 					'<th class="info">Prodotti</th>' + 
            		 					'<th class="info tot">Totale</th>' + '</tr>');
            if(invoices === null)
             {
            	$('.tableContainer').hide();
            	$('#invoicesTable').hide();
                 $('#noInv').html("");
           
                 $('#noInv').html("Non ci sono fatture per il tuo parametro di ricerca");
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class=" dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto:' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agravr;: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '%</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
	}
	else if(userName == "" && toData == "")
	{
		$.post('InvoiceFilterServlet', { FromData : fromData }, function(response) {
			 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

             
             $('#noInv').hide();
             $('#invoicesTable').empty();
             $('#invoicesTable').append('<tr id="infoRow">' + 
            		 				'<th class="info">Numero Fattura</th>' +
            		 					'<th class="info">Acquirente</th>' +
            		 					'<th class="info">Venditore</th>' + 
            		 					'<th class="info">Prodotti</th>' + 
            		 					'<th class="info tot">Totale</th>' + '</tr>');
            if(invoices === null)
             {
            	$('.tableContainer').hide();
            	$('#invoicesTable').hide();
                 $('#noInv').html("");
                 $('#noInv').html("Non ci sono fatture per il tuo parametro di ricerca");
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agrave: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
	}
	else if(userName == "")
	{
		$.post('InvoiceFilterServlet', { FromData : fromData, ToData : toData }, function(response) {
			 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

            
             $('#noInv').hide();
             $('#invoicesTable').empty();
             $('#invoicesTable').append('<tr id="infoRow">' + 
            		 				'<th class="info">Numero Fattura</th>' +
            		 					'<th class="info">Acquirente</th>' +
            		 					'<th class="info">Venditore</th>' + 
            		 					'<th class="info">Prodotti</th>' + 
            		 					'<th class="info tot">Totale</th>' + '</tr>');
            if(invoices === null)
             {
            	$('.tableContainer').hide();
            	$('#invoicesTable').hide();
                 $('#noInv').html("");
                 $('#noInv').html("Non ci sono fatture per il tuo parametro di ricerca");
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto:' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agrave;: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
	}
	else if(toData == "")
	{
		$.post('InvoiceFilterServlet', { User : userName, FromData : fromData }, function(response) {
			 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

             
             $('#noInv').hide();
             $('#invoicesTable').empty();
             $('#invoicesTable').append('<tr id="infoRow">' + 
            		 				'<th class="info">Numero Fattura</th>' +
            		 					'<th class="info">Acquirente</th>' +
            		 					'<th class="info">Venditore</th>' + 
            		 					'<th class="info">Prodotti</th>' + 
            		 					'<th class="info tot">Totale</th>' + '</tr>');
            if(invoices === null)
             {
            	$('.tableContainer').hide();
            	$('#invoicesTable').hide();
                 $('#noInv').html("");
                 $('#noInv').html("Non ci sono fatture per il tuo parametro di ricerca");
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto:' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agrave: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
	}
	else if(fromData == "")
	{
		$.post('InvoiceFilterServlet', { User : userName, ToData : toData }, function(response) {
			 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

            
             $('#noInv').hide();
             $('#invoicesTable').empty();
             $('#invoicesTable').append('<tr id="infoRow">' + 
            		 				'<th class="info">Numero Fattura</th>' +
            		 					'<th class="info">Acquirente</th>' +
            		 					'<th class="info">Venditore</th>' + 
            		 					'<th class="info">Prodotti</th>' + 
            		 					'<th class="info tot">Totale</th>' + '</tr>');
            if(invoices === null)
             {
            	$('.tableContainer').hide();
            	$('#invoicesTable').hide();
                 $('#noInv').html("");
                 $('#noInv').html("Non ci sono fatture per il tuo parametro di ricerca");
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">N: ' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agrave: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
	}
	else
	{
		$.post('InvoiceFilterServlet', { User : userName, FromData : fromData, ToData : toData }, function(response) {
			 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

           
             $('#noInv').hide();
             $('#invoicesTable').empty();
             $('#invoicesTable').append('<tr id="infoRow">' + 
            		 				'<th class="info">Numero Fattura</th>' +
            		 					'<th class="info">Acquirente</th>' +
            		 					'<th class="info">Venditore</th>' + 
            		 					'<th class="info">Prodotti</th>' + 
            		 					'<th class="info tot">Totale</th>' + '</tr>');
            if(invoices === null)
             {
            	$('.tableContainer').hide();
            	$('#invoicesTable').hide();
                 $('#noInv').html("");
                 $('#noInv').html("Non ci sono fatture per il tuo parametro di ricerca");
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">N: ' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agrave;: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
	}
}

function toggleBuy()
{
	
	if($('#invoicesTableSELL').css('display') != 'none')
	{
		$('#invoicesTableSELL').slideToggle();
		$('.tableContainer').slideToggle();
	}
	
	if($('#noSell').css('display') != 'none')
	{
		$('#noSell').hide();
	}
	
	if($('#invoicesTableBUY').children().children().length == 1)
	{
		$('#noInv').slideToggle();
	}
	else
	{
		$('.tableContainer').slideToggle();
		$('#invoicesTableBUY').slideToggle();
	}
}

function toggleSell()
{
	if($('#invoicesTableBUY').css('display') != 'none')
	{
		$('.tableContainer').slideToggle();
		$('#invoicesTableSELL').slideToggle();
	}
	
	if($('#noInv').css('display') != 'none')
	{
		$('#noInv').hide();
	}
	
	if($('#invoicesTableSELL').children().children().length == 1)
	{
		$('#noSell').slideToggle();
	}
	else
	{
		$('.tableContainer').slideToggle();
		$('#invoicesTableSELL').slideToggle();
	}
}

$(document).ready(function() {
	
	
    if(us.pIVA != "" && us.pIVA !== null && us.administrator == false)
    {
        $('#choiceContainer').append('<input class="singleButton" type="button" onclick="toggleSell()" value="Venduti" />' +
                                    '<input class="singleButton" type="button" onclick="toggleBuy()" value="Acquistati" />');
        
        $.get('InvoiceUserServlet', function(response) {

            var stringa = JSON.stringify(response);
            var invoices = JSON.parse(stringa);
            
            if(invoices.buyerLog)
            {
            	var i = 0;
                $(invoices.buyerLog).each(function() {
        
                    $('#invoicesTableBUY').append('<tr class="singleInv' + i + '"></tr>');
                     $('.singleInv' + i + '').append('<td class="dataInv">' + 
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">N: ' + this.id + '</li>' +
                                                            '<li class="listElem">' + this.data + '</li>' +
                                                            '<li class="listElem">' + this.payment + '</li>' +
  					                                        '</ul>' +
  				                                    '</td>' +
  				
  				                                    '<td class="dataInv">' +
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
  						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
  						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
  					                                    '</ul>' +
			                                        '</td>' +

  		                                    		'<td class="dataInv">' +
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">' + this.SellerName + '</li>' +
  						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
  						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
  	                                    				'</ul>' +
  				                                    '</td>' +
  				
  				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
  					
                    $(this.products).each(function() {
                        $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
  						                                    '<li class="listProd">' + this.name + '</li>' +
  						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                            '<li class="listProd">Quantit&agrave:' + this.quantity + '</li>' +
                                                            '<li class="listProd">IVA: ' + this.iva + '%</li>' +
  						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
  	                                    				'</ul>');
  				                               
                    });
                    $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                    i++;
                });
            }
            
            if(invoices.sellerLog)
            {
            	var i = 0;
                $(invoices.sellerLog).each(function() {
        
                    $('#invoicesTableSELL').append('<tr class="singleInv' + i + '"></tr>');
                     $('.singleInv' + i + '').append('<td class="dataInv">' + 
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">N: ' + this.id + '</li>' +
                                                            '<li class="listElem">' + this.data + '</li>' +
                                                            '<li class="listElem">' + this.payment + '</li>' +
  					                                        '</ul>' +
  				                                    '</td>' +
  				
  				                                    '<td class="dataInv">' +
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
  						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
  						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
  					                                    '</ul>' +
			                                        '</td>' +

  		                                    		'<td class="dataInv">' +
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">' + this.SellerName + '</li>' +
  						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
  						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
  	                                    				'</ul>' +
  				                                    '</td>' +
  				
  				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
  					
                    $(this.products).each(function() {
                        $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
  						                                    '<li class="listProd">' + this.name + '</li>' +
  						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                            '<li class="listProd">Quantit&agrave: ' + this.quantity + '</li>' +
                                                            '<li class="listProd">IVA: ' + this.iva + '</li>' +
  						                                    '<li class="listProd">Prezzo: ' + this.price+ '%</li>' +
  	                                    				'</ul>');
  				                               
                    });
                    $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                    i++;
                });
            }
        });
    }
    else if(us.administrator == true)
    {
    	
    	
         
         $('#choiceContainer').append('<div id="adminText"><p>Filtri per le fatture</p></div>');

         $('#choiceContainer').append('<div class="inputData">' + '<p>Inserire UserName:</p>' +
        		 '<input class="textInput" placeholder="Username" type="text" name="username"/>');
         
         $('#choiceContainer').append('<div class="inputData">' + '<p>Da:</p>' +
         		'<input class="fromDate" type="date" name="fromDate"/>' + '<p>A:</p>' +
         		'<input class="toDate" type="date" name="toDate"/>');
         
         $('#choiceContainer').append('<div class="inputData">' +
        		 '<input class="singleButton" type="button" onclick="invoiceFiler()" value="Filtra" />');
         
         $.get('InvoiceUserServlet', function(response) {

        	 var stringa = JSON.stringify(response);
             var invoices = JSON.parse(stringa);

            if(invoices === null)
             {
                 $('#invoicesTable').hide();
                 $('#noInv').show();
             }
             else
             {
                 var i = 0;
                 $(invoices).each(function() {
         
                     $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                      $('.singleInv' + i + '').append('<td class="dataInv">' + 
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">N: ' + this.id + '</li>' +
                                                             '<li class="listElem">' + this.data + '</li>' +
                                                             '<li class="listElem">' + this.payment + '</li>' +
   					                                        '</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
   					                                    '</ul>' +
 			                                        '</td>' +

   		                                    		'<td class="dataInv">' +
   					                                    '<ul class="listInv">' +
   						                                    '<li class="listElem">' + this.SellerName + '</li>' +
   						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
   						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
   	                                    				'</ul>' +
   				                                    '</td>' +
   				
   				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
   					
                     $(this.products).each(function() {
                         $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
   						                                    '<li class="listProd">' + this.name + '</li>' +
   						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                             '<li class="listProd">Quantit&agrave;: ' + this.quantity + '</li>' +
                                                             '<li class="listProd">IVA: ' + this.iva + '%</li>' +
   						                                    '<li class="listProd">Prezzo: ' + this.price+ '</li>' +
   	                                    				'</ul>');
   				                               
                     });
                     $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                     i++;
                 });
                 $('.tableContainer').show();
                 $('#invoicesTable').show();
             }
        });
    }
    else
    {
        $.get('InvoiceUserServlet', function(response) {

        	$('#choiceContainer').hide();
            var stringa = JSON.stringify(response);
            var invoices = JSON.parse(stringa);

         
     
           if(invoices === null || !invoices || !invoices.length)
            {
                $('#invoicesTable').hide();
                $('#noInv').show();
            }
            else
            {
                var i = 0;
                $(invoices).each(function() {
        
                    $('#invoicesTable').append('<tr class="singleInv' + i + '"></tr>');
                     $('.singleInv' + i + '').append('<td class="dataInv">' + 
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">N: ' + this.id + '</li>' +
                                                            '<li class="listElem">' + this.data + '</li>' +
                                                            '<li class="listElem">' + this.payment + '</li>' +
  					                                        '</ul>' +
  				                                    '</td>' +
  				
  				                                    '<td class="dataInv">' +
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">' + this.BuyerName + '</li>' +
  						                                    '<li class="listElem">' + this.BuyerSurname + '</li>' +
  						                                    '<li class="listElem">' + this.BuyerMail + '</li>' +
  					                                    '</ul>' +
			                                        '</td>' +

  		                                    		'<td class="dataInv">' +
  					                                    '<ul class="listInv">' +
  						                                    '<li class="listElem">' + this.SellerName + '</li>' +
  						                                    '<li class="listElem">' + this.SellerSurname + '</li>' +
  						                                    '<li class="listElem">' + this.SellerMail + '</li>' +
  	                                    				'</ul>' +
  				                                    '</td>' +
  				
  				                                    '<td class="dataInvProd dataInvProd' + i + '"> </td>');    
  					
                    $(this.products).each(function() {
                        $('.dataInvProd' + i + '').append('<ul class="listInvProd">' +
  						                                    '<li class="listProd">' + this.name + '</li>' +
  						                                    '<li class="listProd">Sconto: ' + this.sale + '%</li>' +
                                                            '<li class="listProd">Quantit&agrave;: ' + this.quantity + '</li>' +
                                                            '<li class="listProd">IVA: ' + this.iva + '</li>' +
  						                                    '<li class="listProd">Prezzo: ' + this.price+ '%</li>' +
  	                                    				'</ul>');
  				                               
                    });
                    $('.singleInv' + i + '').append('<td class="dataInv"><p>' + this.totalPrice + '</p></td>');
                    i++;
                });
                $('.tableContainer').show();
                $('#invoicesTable').show();
            }
        });
    }
});

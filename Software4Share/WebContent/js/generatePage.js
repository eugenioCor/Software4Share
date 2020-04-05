/*Per ottenere la pagina per la categoria desiderata*/


/*
 * On change del menù a tendina, viene chiamata una servlet che prende dal db i prodotti per quella categoria
 */
$('select').on('change', function() {
	var cat = this.value;
	$.get('CategoryServlet', { category : cat }, function functCat(response) {
		printGenerateProducts(cat, response);	
	}).fail(function() {
		alertBox("si &egrave; verificato un errore");
	}); 
});


/*
 * Una volta presi i prodotti dal db, devo ovviamente visualizzarli! Il nome non è quello che sta altrove
 * perchè mi faceva conflitto...
 */
function printGenerateProducts(cat, response)
{
	var stringa = JSON.stringify(response);
	var productObject = JSON.parse(stringa);
    var j = 0;
    var i = 1;  

    if(cat.includes("Tutti"))
    {
    	$('#marked').html("A - Z");
    }
    else
    {
    	$('#marked').html(cat);
    }  
    //Cambio il testo da 'a - z' al nome della categoria che voglio vedere 
    
    $('#tableProducts').html(' ');
    //Ripulisco la tabella prima di poterla riempire di nuovo
    
    $.each(response, function(j, productObject) {
        	
               if(j == 0 || j == 3 || j%3 == 0)
               {
            		$('#tableProducts').append('<tr class="productRow">');
                    $('.productRow').last().append('<td class="productSquare"> <div id="imageContainer">'
                    		+ '<p class="productName">' + $(productObject).eq(0).attr("name") + '</p>'
                    		+ '<img class="prodIMG" onclick="MyFunction(this)" src='+ $(productObject).eq(0).attr("images")[0] +'>'
                    		+ '<p class="productPrice">' + $(productObject).eq(0).attr("price").toFixed(2) + '</p>'
                    		+ '<p class="like"><span class="fa-heart"></span></p>'
                    		+ '<p class="stars"></p></div></td>');
               }
               else if(j%3 != 0 && i%3 == 0)
               {
            	   $('.productRow').last().append('<td class="productSquare"> <div id="imageContainer">'
                   		+ '<p class="productName">' + $(productObject).eq(0).attr("name") + '</p>'
                   		+ '<img class="prodIMG" onclick="MyFunction(this)" src='+ $(productObject).eq(0).attr("images")[0] +'>'
                   		+ '<p class="productPrice">' + $(productObject).eq(0).attr("price").toFixed(2) + '</p>'
                   		+ '<p class="like"><span class="fa-heart"></span></p>'
                   		+ '<p class="stars"></p></div></td></tr>');
            	}
               else if(j%3 != 0 && i%3 != 0)
               {
            	   $('.productRow').last().append('<td class="productSquare"> <div id="imageContainer">'
                   		+ '<p class="productName">' + $(productObject).eq(0).attr("name") + '</p>'
                   		+ '<img class="prodIMG" onclick="MyFunction(this)" src='+ $(productObject).eq(0).attr("images")[0] +'>'
                   		+ '<p class="productPrice">' + $(productObject).eq(0).attr("price").toFixed(2) + '</p>'
                   		+ '<p class="like"><span class="fa-heart"></span></p>'
                   		+ '<p class="stars"></p></div></td>');
               }
               j++;
               i++;     
        });
}
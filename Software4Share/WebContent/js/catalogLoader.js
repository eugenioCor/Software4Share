$(document).ready(function(){
	loader("Tutti");
	$.get('DynamicCatalogServlet', function(response) 
			{
				var stringa = JSON.stringify(response);
				var elem = JSON.parse(stringa);
				
				 var option=$("<option class='optionStyle' value='Tutti'>Tutti</option>");
				 	$('#categorySelector').append(option);
				var arrayLength = elem.length;
				for (var i = 0; i < arrayLength; i++) {
				   
				    var option=$("<option class='optionStyle' value='"+ elem[i] +"'>"+ elem[i] + "</option>");
				 	$('#categorySelector').append(option);
				    
				}
			}).fail(function() {alertBox("si &egrave; verificato un errore");});
	
	
	$('select').on('change', function() {
		var cat = this.value;
		loader(cat);
	});

});

function loader(cat)
{
	
	$.get('CategoryServlet', { category : cat }, function functCat(data) {
	    
		if(cat.includes("Tutti"))
	    {
	    	$('#marked').html("A - Z");
	    }
	    else
	    {
	    	$('#marked').html(cat);
	    }
	    if(data[0]==null||data[0].length==0)
		{
	    	 clearPage()
		setVoidPage()
		}
	else
		{
		 clearPage()
			generateHomePage(data[0],data[2]);
			loadComponents();
			loadButtons(data[0],data[1]);
		}	
	}).fail(function() {
		alertBox("si &egrave; verificato un errore");
	}); 
}

$(document).ready(function(){ loadPage();});



function loadPage()
{
	$.get("ProductPageServlet",
	    	function(data)
	    	{
				
	    		generatePageProduct(data[0],data[1],data[2],data[3]);
	    		showSlides(1);
	    	}).fail(function(){ alertBox("si &egrave; verificato un errore")});				
}




/**
 * 
 */$(document).ready(function() {
    $.get('DynamicHomepageServlet', function(data) 
    {
    	if(data[0]==null||data[0].length==0)
		{
		setVoidPage()
		}
	else
		{
			generateHomePage(data[0],data[2]);
			loadComponents();
			loadButtons(data[0],data[1]);
			imageEffect();
		}
    }).fail(function() {
    	alertBox("si &egrave; verificato un errore");
    }); 
});
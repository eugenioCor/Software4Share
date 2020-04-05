/*Serve a chiamare (se necessario) la servlet per cancellare l'utente*/

$('.removeButton').on('click', function() {
    if (confirm('Sei sicuro di voler eliminare l\u0027account?')) {
        $.post('RemoveUserServlet', function displayRes(response) {
        	
        	var stringa = JSON.stringify(response);
        	var booleanValue = JSON.parse(stringa);
        	
        	if(booleanValue)
        	{
        		$(window.location).attr('href', 'homepage.jsp');
        	}
        	else
        	{
        		alertBox("Non puoi cancellare il tuo account, devono trascorrere almeno 10 giorni dall\u0027ultimo ordine!");
        	}
        });
        
    } else {
    	
    	$(window.location).attr('href', 'homepage.jsp');
    }
});

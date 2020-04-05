
function checkEmail() {
    var StringValidator = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var mail = $("#email");

    if ($(mail).val().match(StringValidator) || $(mail).val().trim() == "") {
        return true;
    } else {
        alertBox("Il formato della mail non \u00E8 corretto deve essere mail@mail.com");
        return false;
    }
}

function checkText() {
    var textArea = $("#textMessage");
    
    if ($(textArea).val() == "") 
    {
    	alertBox("Il messaggio \u00E8 vuoto");
        return false;
    } else {
        
        return true;
    }
}

function checkEmptyMail()
{
	 var mail = $("#email");
	 if ($(mail).val() == "") 
	    {
	    	alertBox("Il campo mail \u00E8 vuoto ");
	        return false;
	    } else {
	        
	        return true;
	    }
}

function check()
{
	if(checkEmptyMail())
		{
			return false;
		}
	else if(checkEmail())
		{
			return false;
		}
	else if (checkText())
	{
		return false;
	}
	else 
	{
		return true;
	}
	
}

/*Funzione per il contactUs, serve per controllare la mail sul click e poi nel caso chiamare la servlet che ti invia la mail*/

$("#buttonContainer").click(function(){

	
	if($("#email").length!=0)
		{
			if(checkEmptyMail())
				{
					if(checkEmail())
						{
						if(checkText())
						{
						alertLoading();
						var message = document.getElementById("textMessage").value;
						var obj =$("#objectSelector").children("option:selected").val();
						var email = document.getElementById("email").value;
					
						$.post("SendEmailServlet", { Text : message, Object : obj, Email : email }, function(response) {
							$(".overlay").remove();
							alertBox(response);
						});
						}
						}
				}
		}
	else
	{
		
		
		if(checkText())
			{
			var message=$(textArea).val();
			var obj = $("#objectSelector").children("option:selected").val();
			
				
			alertLoading();
				$.post("SendEmailServlet", { Text : message, Object : obj }, function(response) {
					$(".overlay").remove();
					alertBox(response);
				}).fail(function() {
					alertBox("Si \u00E8 verificato un errore.");
				});
			
			
			}
		
	}
	
	
});

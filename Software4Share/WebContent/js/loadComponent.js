/**
 * questi codici javascript verranno eseguiti solo nel momento in cui la pagina è totalmente caricata
 */

$(document).ready(function() 
{ 
	registerControl();
	loginControl();
	unlockControl();
	
});

function blockPage(user) 
{
	$(".login100-form").children().hide();
	var elementBlockPage = $("<div class='blockPage'> <p class='blockPage_txt_header'> Pagina bloccata</p> <p class='blockPage_txt_body'> Hai tentato di accedere all' account di <strong id='user'>"+ user +"</strong> con una password errata , dimostra di essere realmente il possessore dell'account inserendo il codice di sblocco che abbiamo inviato via mail, in caso di perdita della password sblocca la pagina e clicca su password dimenticata. </p> <div class='wrap-input100-unlock'> <span class='label-input100-unlock'>chiave di sblocco</span> <input class='input100 input100-unlock' type='text' name='unlock' id='unlock' placeholder='Inserisci chiave' autocomplete='off' maxlength='10'></input> <input type='button' value='invia!' class='login100-form-btn-unlock' id='ctrl-3'></input></div> <div>");
	$(".login100-form").append(elementBlockPage);
	$(".input100").focus(function() {
		hideAlert(this);
    });
}
function unlock() {
    $(".login100-form").children().show();
    $(".blockPage").remove();
    $(".passPage").remove();
}
function loginControl()
{
	$("#ctrl-1").on("click", function() {
		
        hideAlert($("#uname"));
        hideAlert($("#pass"));
        var contr = checkLog();
        var user = $("#uname").val();
        var pass = $("#pass").val();
        
        if (contr) {
        	$("#ctrl-1").attr("disabled","disabled");
            $.post("ControllerRegisterServlet", {
          userName: user,
          pass:pass
      },function(data){
    	  
    	  $("#ctrl-1").removeAttr('disabled');
    	  if(data.checkTry>0)
    		  {
    			  if(data.checkUsername)
    			  {
    		  		if(!data.checkpass)
    		  			{
    		  				showAlert($("#pass"),"Password errata hai ancora "+data.checkTry+" tentativi prima che la pagina si blocchi");
        			  			  		
    		  			}
    		  		else
    		  			{
    		  				$(".login100-form").attr("action", "login");
    		  				$(".login100-form").attr("method", "post");
                			$(".login100-form").submit();
    		  			}
    			  }
    		 	 else
    		 	 {
    			  	showAlert($("#uname"),"Utente inesistente");
    		 	 	results=false;
    			  }
	  
    		  }
    	  else
    		  {
    		 	blockPage(user);
    		 	unlockControl();
    		  }
    }).fail(function(data){ $("#ctrl-1").removeAttr('disabled'); alertBox("si &egrave; verificato un errore")});
      
        }
    });
	
}

function registerControl()
{
	$("#ctrl-2").on("click", function() {
	
 	   var results=true;
 	 	var contr=check();
 	   var email=$("#email").val();
 	   var user=$("#uname").val();
 	   $.post("ControllerRegisterServlet", {
            email: email,
            userName: user
        }, function(data) {
        	
     	   if($("#uname").val().trim()==""||$("#email").val().trim()=="")
     		   {
     		   results=false;
     		   }
            if (data.checkEmail) {
                var email = $("#email");
                showAlert(email, "Questa mail \u00E8 gi\u00E0 stata usata si prega di cambiarla")
					results=false;	
            }
            if (data.checkUsername) {
                var uname = $("#uname");
                showAlert(uname, "Questo username \u00E8 gi\u00E0 stato usato si prega di cambiarlo");
            	results=false;
            }
            if(results && contr)
         	   {
            		
               		$(".login100-form").attr("action", "RegisterServlet");
                		$(".login100-form").attr("method", "post");
                		$(".login100-form").submit();
         	   }
        }).fail(function() {
        	
        	alertBox("si &egrave; verificato un errore");
        })
    });
}

function unlockControl()
{
	$("#ctrl-3").on("click", function() {
 	   var contr=isEmpty($("#unlock"));
 	   var code=$("#unlock").val();
 	   var user=$("#user").text();
 	   if(!contr)
 		   {
 		  
 		   	$.post("ControllerRegisterServlet", {unlock:code,userName:user}, function(data) 
 		   			{
 		   				if(data.unlock)
 		   					{
 		   						unlock();
 		   					}
 		   				else
 		   					{
 		   						showAlert($("#unlock"),"Chiave errata riprovare");
 		   					}
 	        	
 	     	           }).fail(function() {
 	        	
 	     	        	 alertBox("si &egrave; verificato un errore");
 	        });
 		   }
    });
}

function forgotControl()
{
	$("#ctrl-4").on("click", function() {
		
 	   var contr=checkForgot($("#email"));
 	   var mail=$("#email").val();
 	   if(contr)
 		   {
 		   		$("#ctrl-4").attr("disabled","disabled");
 		   		$.post("ControllerRegisterServlet", {email:mail,forgot:true}, function(data) 
 		   			{
 		   				$("#ctrl-4").removeAttr('disabled');
 		   				if(data.checkEmail)
 		   					{
 		   						passPage(mail);
 		   						codeControl();
 		   						checkFocus();
 		   					}
 		   				else
 		   					{
 		   						showAlert($("#email"),"Email sconosciuta inserire una mail registrata");
 		   					}
 		   				
 		   				
 	        	
 	     	           }).fail(function() {
 	     	        	 $("#ctrl-4").removeAttr('disabled');
 	     	        	alertBox("si &egrave; verificato un errore");
 	        });
 		   }
    });
}

function codeControl()
{
	$("#ctrl-5").on("click", function() {
		
 	   var contr=isEmpty($("#code"));
 	   
 	   var code=$("#code").val();
 	  var mail=$("#mail").text();
 	   if(!contr)
 		   {
 		   		$("#ctrl-5").attr("disabled","disabled");
 		   		$.post("ControllerRegisterServlet", {unlock:code,email:mail,forgot:"pass"}, function(data) 
 		   			{
 		   				$("#ctrl-5").removeAttr('disabled');
 		   				if(data.unlock)
 		   					{
 		   						newPassPage();
 		   						newPassControl(mail);
		   						checkFocus();
 		   						
 		   					}
 		   				else
 		   					{
 		   						showAlert($("#code"),"Chiave errata riprovare");
 		   					}
 		   		
 	     	           }).fail(function() {
 	     	        	 $("#ctrl-5").removeAttr('disabled');
 	     	        	alertBox("si &egrave; verificato un errore");
 	        });
 		   }
    });
}

function newPassControl(mail)
{
	$("#ctrl-6").on("click", function() {
		var pass=$("#newPass");
		var confPass=$("#pass2");
	
		contr=checkNewPass(pass,confPass);
 	   if(contr)
 		   {
 		   		$("#ctrl-6").attr("disabled","disabled");
 		   		$.post("ControllerRegisterServlet", {pass:$(pass).val(),email:mail,forgot:"changePass"}, function(data) 
 		   			{
 		   				$("#ctrl-6").removeAttr('disabled');
 		   				if(data.unlock)
 		   					{
 		   						unlock();
 		   					}
 		   		
 	     	           }).fail(function() {
 	     	        	 $("#ctrl-6").removeAttr('disabled');
 	     	        	alertBox("si &egrave; verificato un errore");
 	        });
 		   }
    });
}

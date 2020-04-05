function checkPass() {
    var pass = $("#pass");
    var StringValidator = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9_.,\-+*!#@?]{6,25})$$/; /*la stringa deve contenere almeno un numeno ,un carattere minuiscolo ,un carattere maiuscolo e un carattere speciale , la stringa deve essere compresa tra 6 e 25 caratteri*/

    if ($(pass).val().match(StringValidator) || $(pass).val().trim() == "") /*controllo che l'input inserito corrisponda all'espressione regolare oppure se e vuota restituisco lo stesso true perchè per le stringhe vuote faro un controllo a parte */ {
        return true;
    } else {

        showAlert(pass, "Formato password non valido inserire maiuscola , minuscola e numero per visualizzare la password clicca sul lucchetto");
        return false;
    }
}
function checkPass2() {
    var pass = $("#newPass");
    var StringValidator = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9_.,\-+*!#@?]{6,25})$$/; /*la stringa deve contenere almeno un numeno ,un carattere minuiscolo ,un carattere maiuscolo e un carattere speciale , la stringa deve essere compresa tra 6 e 25 caratteri*/

    if ($(pass).val().match(StringValidator) || $(pass).val().trim() == "") /*controllo che l'input inserito corrisponda all'espressione regolare oppure se e vuota restituisco lo stesso true perchè per le stringhe vuote faro un controllo a parte */ {
        return true;
    } else {

        showAlert(pass, "Formato password non valido inserire maiuscola , minuscola e numero per visualizzare la password clicca sul lucchetto");
        return false;
    }
}

function checkUsername() {
    var userName = $("#uname");
    var StringValidator = /^[A-Za-z0-9_-]{3,18}$/; /*Quest'espressione regolare verifica che la stringa abbia almeno dai 3 ai 10 caratteri alfanumerici inoltre può avere un - o _ 1 sola occorrenza e poi puo avere 0 a 4 numeri dopo il trattino se c'e  */
    if ($(userName).val().match(StringValidator) || $(userName).val().trim() == "") /*controllo che l'input inserito corrisponda all'espressione regolare oppure se e vuota restituisco lo stesso true perchè per le stringhe vuote faro un controllo a parte */ {
        return true;
    } else {

        showAlert(userName, "Formato username non valido o username troppo lungo");
        return false;
    }
}


function hideAlert(input) {

    var father = $(input).parent();
    if ($(input).attr("type") == "date") {
        $(father).children().filter(".alert-validate-date").removeClass("alert-validate-date");
    } else if ($(input).hasClass("input100-unlock")) {
        $(father).children().filter(".alert-validate-block").removeClass("alert-validate-block");
    } else if ($(input).hasClass("input100-unlock_2")) {
        $(father).children().filter(".alert-validate-block_2").removeClass("alert-validate-block_2");
    } else {
        $(father).children().filter(".alert-validate").removeClass("alert-validate");
    }

}

function checkFocus()
{
	$(".input100").each(function() {
	    $(this).focus(function() {
	        hideAlert(this);
	    });
	});
}

checkFocus();


function isAllEmpty() {
    var empty = true;
    $(".input100").each(function() {
        if ($(this).val().trim() == "") {
            if ($(this).attr("name") != "pIva") { //in questo modo la partita iva può essere vuota 
                $(this).each(function() {
                    showAlert(this, "Il campo non pu\u00F2 essere vuoto");
                });
                empty = false;
            }

        }
    });
    return empty;
}

function checkText(el) {
    var inputTxt1 = $("#name");
    var inputTxt2 = $("#surname");
    var StringValidator = /^[A-Za-z]+$/;
    var surnameValidator = /^[A-Za-z]+\s?[-\']?[A-Za-z]+$/;

    if (el == 'name') {
        if ($(inputTxt1).val().match(StringValidator) || $(inputTxt1).val().trim() == "") {
            return true;
        } else {
            showAlert(inputTxt1, "Formato non valido");
        }

    } else {
        if ($(inputTxt2).val().match(surnameValidator) || $(inputTxt2).val().trim() == "") {
            return true;
        } else {
            showAlert(inputTxt2, "Formato non valido");
        }

    }
}


function check() {
    if (!checkUsername()) {
    
    	return false;
    } else if (!checkPass()) {
    
    	        return false;
    } else if (!isAllEmpty()) {
    	
        return false;

    } else if (!checkEqual()) {
    	
        return false;
    } else if (!checkText("name")) {
    	
    	return false;
    } else if (!checkText("surname")) {
    	
    	return false;
    } else if (!checkEmail()) 
    { 
    
    	return false; } 
    else if (!checkIva()) 
    {     	
    	return false; } 
    else if (!checkDate()) 
    {
        return false;
    } 
    else {
    
        return true;

    }
}


function checkLog() {
    if (!checkUsername()) {
    	return false;
    } else if (!checkPass()) {
    	
    	        return false;
    } else if (!isAllEmpty()) {
        return false;
    } 
    else {
        return true;
    }
}



function hideShowPass(pos) {
    if (pos == 0) {
        if ($("#pass").attr("type") == "password") {
            $("#pass").attr("type", "text");
            $("#toggle-pass").removeClass("label-icon-pass-lock");
            $("#toggle-pass").addClass("label-icon-pass-unlock");

        } else {
            $("#pass").attr("type", "password");
            $("#toggle-pass").addClass("label-icon-pass-lock");
            $("#toggle-pass").removeClass("label-icon-pass-unlock");

        }
    } else if (pos == 1) {
        if ($("#pass2").attr("type") == "password") {
            $("#pass2").attr("type", "text");
            $("#toggle-pass2").removeClass("label-icon-pass-lock");
            $("#toggle-pass2").addClass("label-icon-pass-unlock");

        } else {
            $("#pass2").attr("type", "password");
            $("#toggle-pass2").addClass("label-icon-pass-lock");
            $("#toggle-pass2").removeClass("label-icon-pass-unlock");

        }
    }else if (pos == 3) {
        if ($("#newPass").attr("type") == "password") {
            $("#newPass").attr("type", "text");
            $("#toggle-pass").removeClass("label-icon-pass-lock");
            $("#toggle-pass").addClass("label-icon-pass-unlock");

        } else {
            $("#newPass").attr("type", "password");
            $("#toggle-pass").addClass("label-icon-pass-lock");
            $("#toggle-pass").removeClass("label-icon-pass-unlock");

        }
    }
}

function checkEqual() {
    var pass = $("#pass");
    var confPass = $("#pass2");

    if (pass.val() != confPass.val()) {
        showAlert(confPass, "Le password non corrispondono");
        return false;

    } else {
        return true;
    }
}

function checkEmail() {
    var StringValidator = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var mail = $("#email");

    if ($(mail).val().match(StringValidator) || $(mail).val().trim() == "") {
        return true;
    } else {
        showAlert(mail, "Il formato della mail non \u00E8 corretto deve essere mail@mail.com");
        return false;
    }


}

function checkIva() {
    var StringValidator = /^[0-9]{9,10}$/;
    var iva = $("#iva");

    if ($(iva).val().match(StringValidator) || $(iva).val().trim() == "") {
        return true;
    } else {
        showAlert(iva, "Il formato dell iva non  corretto devono essere almeno 10 numeri");
        return false;
    }
}



function checkDate() {
    var date = $("#date");
    var oggi = new Date();
    var nascita = new Date($(date).val());
    var minDate = new Date(oggi.getFullYear() - 120, oggi.getMonth(), oggi.getDay());
    if (nascita.getFullYear().toString().length >= 4) 
    {
        if (oggi <= nascita || minDate >= nascita) 
        {
        	showAlert(date, "Inserire una data valida");
            return false;
        } 
        else
        {
        	hideAlert(date);
        	return true;
        }
    }
   

}


function showAlert(input, textAlert) {
	console.log(input);
    var dad = $(input).parent();
    var alert = $("<div></div>");
    if ($(input).attr("type") == "date") {
        if ($(dad).children().filter(".alert-validate-date").length == 0) //verifica che non ci siano altri errori altrimenti ogni volta che deve mostrare l'errore vengono sovrapposti con questo si risolve il problema
        {

            dad.append(alert);
            $(alert).attr("data-validate", textAlert);
            $(alert).addClass("alert-validate-date");

        }
    } else if ($(input).hasClass("input100-unlock_2")) {
        
        if ($(dad).children().filter(".alert-validate-block_2").length == 0) //verifica che non ci siano altri errori altrimenti ogni volta che deve mostrare l'errore vengono sovrapposti con questo si risolve il problema
        {
            
            dad.append(alert);
            $(alert).attr("data-validate", textAlert);
            $(alert).addClass("alert-validate-block_2");

        }
    } else if ($(input).hasClass("input100-unlock")) {
        if ($(dad).children().filter(".alert-validate-block").length == 0) //verifica che non ci siano altri errori altrimenti ogni volta che deve mostrare l'errore vengono sovrapposti con questo si risolve il problema
        {

            dad.append(alert);
            $(alert).attr("data-validate", textAlert);
            $(alert).addClass("alert-validate-block");

        }
    } else {
        if ($(dad).children().filter(".alert-validate").length == 0) //verifica che non ci siano altri errori altrimenti ogni volta che deve mostrare l'errore vengono sovrapposti con questo si risolve il problema
        {
            dad.append(alert);
            $(alert).attr("data-validate", textAlert);
            $(alert).addClass("alert-validate");

        }
    }


}

function isEmpty(input)
{
	var empty = false;
     if ($(input).val().trim() == "") 
     {
                    showAlert(input, "Il campo non pu\u00F2 essere vuoto");
                    empty = true;
     }
     
    return empty;
	
}
function isEmpty2(input,input2)
{
	
	var empty = false;
     if ($(input).val().trim() == "") 
     {
    	 
    	   showAlert(input, "Il campo non pu\u00F2 essere vuoto");
     empty = true;
     }
     if ($(input2).val().trim() == "") 
     {
    	
    	   showAlert(input2, "Il campo non pu\u00F2 essere vuoto");
    	   empty = true;
     }
     
    return empty;
	
}

$("#forgot").click(function() {
	forgotpass();
	forgotControl();
	checkFocus();
});

function forgotpass()
{
	$(".login100-form").children().hide();
    var forgot = $("<div class='passPage'> <p class='passPage_txt_header'> Password dimenticata?</p> <p class='passPage_txt_body'>Inserire l'indirizzo mail associato al tuo account Software 4 Share, le sar\u00e0 inviata una mail con un codice di verifica che le permettera di modificare la password. </p> <div class = 'wrap-input100-passPage' > <span class = 'label-input100-passPage' > Indirizzo Email </span> <input class = 'input100 input100-unlock' type = 'email' name = 'email' id ='email' placeholder = 'Inserisci mail' autocomplete = 'off' onchange='checkEmail()' ></input> <input type = 'button' value = 'invia!' class = 'login100-form-btn-unlock' id='ctrl-4'> </input> </div></div>");
    $(".login100-form").append(forgot);
}
function newPassPage() {
    
    var forgot = $("<div class='passPage'><p class='passPage_txt_header'>Crea una nuova password</p><p class='passPage_txt_body'>Ti richiederemo questa password ogni volta che effettuerai l'accesso.</p><div class='wrap-input100-passPage'><div class='label-icon-pass-lock lockImage' id='toggle-pass' onclick='hideShowPass(3)'> </div><span class='label-input100-newPass'>Password</span><input class='input100 input100-unlock_2' type='password' name='mewPass' id='newPass' placeholder='Inserisci codice' minlength='8' maxlength='20' onchange='checkPass2()' autocomplete='new-password'></input></div><div class='wrap-input100-passPage'><div class='label-icon-pass-lock lockImage' id='toggle-pass2' onclick='hideShowPass(1)'> </div><span class='label-input100-newPass'> Conferma Password</span><input class='input100 input100-unlock' type='password' name='confPass' id='pass2' placeholder='Inserisci codice' autocomplete='off' onchange='checkEqual2()'></input><input type='button' value='invia!' class='login100-form-btn-unlock' id='ctrl-6'></input></div></div>");
    $(".passPage").remove();
    $(".login100-form").append(forgot);
}

function checkForgot(input) 
{
    if (!checkEmail()) 
    {
   	 return false;
   } else if (isEmpty(input)) {
       return false;
   } 
   else {
       return true;
   }
}

function checkNewPass(input,input2)
{
	
     if (!checkPass2()) 
     {
    	 return false;
    }else if (!checkEqual2()) 
    {
   	 return false;
   }else if (isEmpty2(input,input2)) {
        return false;
    }
    else {
        return true;
    }
}

function passPage(mail) {

    var forgot = $("<div class='passPage'><p class='passPage_txt_header'>Stiamo verificando che sia proprio tu!</p> <p class='passPage_txt_body'>Per la tua sicurezza, \u00E8 necessario verificare la tua identit\u00e0. Abbiamo inviato un codice al tuo indirizzo e-mail <strong id='mail'>"+ mail +"</strong>. Inseriscilo ora qui sotto. </p> <div class='wrap-input100-passPage'> <span class='label-input100-unlock'>codice identificativo</span> <input class='input100 input100-unlock' type='text' name='code' id='code' placeholder='Inserisci codice' autocomplete='off'></input> <input type='button' value='invia!' class='login100-form-btn-unlock' id='ctrl-5'></input> </div> </div>");
    $(".passPage").remove();
    $(".login100-form").append(forgot);
}



function checkEqual2() {
    var pass = $("#newPass");
    var confPass = $("#pass2");

    if (pass.val() != confPass.val()) {
        showAlert(confPass, "Le password non corrispondono");
        return false;

    } else {
        return true;
    }
}

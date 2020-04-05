function setFileName(inp) {
    var filename = $(inp).val();
    /*il metodo test serve a testare una regExp restituisce true se mathcano*/
    if (/^\s*$/.test(filename)) {
        $($(inp).parent().parent()).removeClass('active');
        $($(inp).prev()).text("Nessun file selezionato");
    } else {
        $($(inp).parent().parent()).addClass('active');
        $($(inp).prev()).text(filename.replace("C:\\fakepath\\", "")); //il replace serve perchè quando si carica un file ha il path fake path lho rimosso per legibilità

    }
}

function resetForm() {
    var request = confirm("Sei sicuro di voler resettare tutto il form?");
    if (request) {
        $("#formControl").trigger("reset");
        $(".chooseFile").each(function() {
            setFileName(this);
        });
        $(".control").each(function() {
            hideAlert(this);
        });
    }
}

function isAllEmpty() {
    var empty = false;
    $(".control-upload").each(function() {
        if ($(this).val().trim() == "") {
            showAlert(this, "Il campo non può essere vuoto");
            empty = true;
        }

    });
    return empty
}


function checkTxt(n) {
    if (n == 1) {
        var txt = $("#productName");
        var StringValidator = /^([A-Za-z0-9]+[ -.]?[_]?){5,50}$/;
        var txt_screen = "Il nome del protto deve avere minimo 5 caratteri e massimo 50, non sono ammessi caratteri speciali";

    }
    if (n == 2) {
        var txt = $("#productTag");
        var StringValidator = /^([A-Za-z0-9]{3,10}[ -]?[_]?){1,20}$/;
        var txt_screen = "Inserire al massimo 20 tag, ogni tag deve avere almeno 3 caratteri, non sono consentiti caratteri speciali";
    }
    if ($(txt).val().match(StringValidator) || $(txt).val().trim() == "") {
        return true;
    } else {
        showAlert(txt, txt_screen);
        return false;
    }
}


function showAlert(input, textAlert) {
    var dad = $(input).parent();
    var alert = $("<div></div>");
    if ($(dad).hasClass("control-input-description")) {
        if (!$(dad).children().hasClass("alert-validate-description")) //verifica che non ci siano altri errori altrimenti ogni volta che deve mostrare l'errore vengono sovrapposti con questo si risolve il problema
        {

            $(dad).append(alert);
            $(alert).attr("data-validate", textAlert);
            $(alert).addClass("alert-validate-description");
        }
    } else if ($(dad).hasClass("file-select")) {
        if (!$(dad).parent().children().hasClass("alert-validate")) //verifica che non ci siano altri errori altrimenti ogni volta che deve mostrare l'errore vengono sovrapposti con questo si risolve il problema
        {
            $(dad).parent().append(alert);
            $(alert).attr("data-validate", textAlert);
            $(alert).addClass("alert-validate");
        }
    } else {
        if (!$(dad).children().hasClass("alert-validate")) //verifica che non ci siano altri errori altrimenti ogni volta che deve mostrare l'errore vengono sovrapposti con questo si risolve il problema
        {
            $(dad).append(alert);
            $(alert).attr("data-validate", textAlert);
            $(alert).addClass("alert-validate");
        }
    }

}

$(".control-upload").each(function() {
    $(this).focus(function() {
        hideAlert(this);
    });
});

function hideAlert(input) {
    var father = $(input).parents().filter(".control-input");
    if ($(father).children().hasClass("alert-validate-description")) {
        $(father).children().filter(".alert-validate-description").removeClass("alert-validate-description");
    } else {
        $(father).children().filter(".alert-validate").removeClass("alert-validate");
    }
}



function check() {
    if (isAllEmpty()) {
        return false;
    } else if (!checkTxt(1)) {

        return false;
    } else if (!checkTxt(2)) {

        return false;
    } else if (checkPrice()) {
        return false;
    } else if (controlFile()) {
        return false;
    } else {
        return true;
    }
}

function controlFile() {
    var fail = false;
    $(".chooseFile").each(function() {
        if (!checkExtention(this)) {
            fail = true;
        }
    });
   
    return fail;
}

function checkExtention(inp) {
    var fileName = $(inp).val();
    if (fileName == "") {
        return true;
    }
    var type = $(inp).attr("name");
    if (type == "file") {
        var ext = fileName.substring(fileName.length - 3);
        if (ext == "exe" || ext == "zip" || ext == "rar") {
            
            return true;
        } else {
           
            showAlert(inp, "Formato file non valido, i formati validi sono:.exe,.zip,.rar");
            return false;
        }
    } else {
        type = type.substring(0, 5);
        if (type == "photo") {
            var ext = fileName.substring(fileName.length - 3);
            if (ext == "jpg" || ext == "jpeg" || ext == "png") {
                
                return true;
            } else {
               
                showAlert(inp, "Formato file non valido, i formati validi sono:.jpg,.jpeg,.png");
                return false;
            }
        } else {
            alert("there is a bugg");
            return false;
        }
    }



}

function checkPrice() {
    var check = false;
    var price = $("#productPrice");
    if ($(price).val().length >= 12) {
        check = true;
        showAlert(price, "Il prezzo ha un numero di cifre troppo elevato");
    } else {
        var priceConv = parseFloat($(price).val());
        if (isNaN(priceConv)) {
            check = true;
            showAlert(price, "Hai inserito dei caratteri non validi");
        } else {
            $("#productPrice").val(priceConv.toFixed(2))
        }

    }
    return check;
}

$(document).ready(function() 
		{  	   var  selector = $("#categorySelector");
		$( "#formControl" ).submit(function() {
				$("#buttonSubmit").attr("type","button");
				var contr = check();
				
				
				if(!contr)
					{	
					$("#buttonSubmit").attr("type","submit");
					}
				
				return contr;
			});
	   		$.get("CategoryControlServlet", function(data) 
	   				{
	   					$(data).each(function(){
	   						var option=$("<option class='optionStyle' value='"+this+"' selected>"+this+"</option>");
	   					 	$(selector).append(option);
	   					});
	   				}).fail(function() {alert("si \u00E8 verificato un errore");})
	   	});


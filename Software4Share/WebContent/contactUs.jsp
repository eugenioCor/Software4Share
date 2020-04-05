<%@ page language="java" contentType="text/html" 
	pageEncoding="ISO-8859-1" import="S4S_Model.*" %>
  
<!-- Pagina per contattare gli admin del sito -->
<!DOCTYPE html>

<html lang="en">

<head>
	<jsp:include page="header.jsp"/> 
    <title>Software4Share</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Let's sell our softwares!">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="./css/contactUs_css.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script type="text/javascript" src="./Jquery/jQuery.js"></script>
</head>

<body>

<!-- Messaggio di apertura -->
<div id="megaContainer">

<div class="cont" >
<h1 id="greetings">Hai riscontrato un problema? Contattaci!</h1>	
</div>

<!-- Form per inserire tutti i dati -->
<div id="formContainer">

   <p class="paragraph">Descrivi brevemente il tuo problema, faremo del nostro meglio per risolverlo!</p>
	<form>
<!-- Se sei loggato, non ti chiederà di inserire l'e-mail! -->
   <%
Boolean autenticated = (Boolean) session.getAttribute("authenticated");
User user = (User) session.getAttribute("user");
if ((autenticated == null) || (!autenticated.booleanValue()))
{	%>
 
 <div id="inputContainer">
 <p class="paragraph">Inserire il proprio indirizzo e-mail: </p>
 <input type="email" name="mailSender" id="email" placeholder="example@mail.com" onchange="checkEmail()" autocomplete="off" required>
  </div>
<%  }  %>


<!-- Serve per gli oggetti della mail, bisogna selezionarne uno! -->
<div id="selectorContainer">
 	<select name="object" id="objectSelector" required>
 	<option class="optionStyle" value="Bug nel sito" selected>Bug nel sito</option>
 	<option class="optionStyle" value="Problemi con il pagamento">Problemi con il pagamento</option>
 	<option class="optionStyle" value="Info sul metodo di pagamento">Info sul metodo di pagamento</option>
 	<option class="optionStyle" value="Segnalazione di un utente">Segnalazione di un utente</option>
 	<option class="optionStyle" value="Problemi con il download">Problemi con il download</option>
 	<option class="optionStyle" value="Altro">Altro</option>
   </select>
</div>

<div id="textArea">
  <textarea name="message" id="textMessage" placeholder="Scrivi..." rows="15" maxlength="500" style="width:80%;" required></textarea>
</div>
 <br>
  <div id="buttonContainer"><input id="send" type="button" value="Invia" ></div>
  <div id="hideText"><h2 id="hideable" style="display: none;"> Attendere...</h2></div>
  </form>
</div>
<!-- Script, mi serve per il controllo della mail -->
<script src="./js/singlePage.js"></script>
 <script src="./js/contactController.js"></script>
</body>

<jsp:include page="footer.jsp" /> 
</div>
</html>
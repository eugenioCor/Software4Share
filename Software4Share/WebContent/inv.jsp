<%@ page language="java" contentType="text/html" 
	pageEncoding="ISO-8859-1" import="S4S_Model.*" import="com.google.gson.Gson" %>

<!-- Pagina per il profilo utente -->  
<!DOCTYPE html>

<%
User user = (User) session.getAttribute("user");
if (user==null)
{	
    response.sendRedirect("./login.jsp");
    return;
}
%>

<script type="text/javascript">
var us =(<%=new Gson().toJson((User)session.getAttribute("user"))%>);
console.log(us);
</script>



<html lang="en">

<head>
	<jsp:include page="header.jsp" /> 
    <title>Software4Share</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Let's sell our softwares!">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="./css/userPage_css.css">
     <link rel="stylesheet" href="./css/invoice_css.css">
     <link rel="stylesheet" href="./css/alertBox.css">
    <script type="text/javascript" src="./Jquery/jQuery.js"></script>
</head>

<body>

<div id="allInvoices">

	<div id="titleInv"><h1>Fatture</h1></div>

		<div id="choiceContainer">
		</div>

		<div id="noInv" style="display:none;">Non hai ancora acquistato nulla</div>
		<div id="noSell" style="display:none;">Nessuno ha comprato i tuoi prodotti</div>
		
		<div class="tableContainer" style="display:none;">
		<table id="invoicesTable" style="display:none;">
			<tr id="infoRow">
				<th class="info">Numero Fattura</th>
    			<th class="info">Acquirente</th>
    			<th class="info">Venditore</th> 
    			<th class="info">Prodotti</th> 
    			<th class="info">Totale</th> 
  			</tr>
		</table>

		<table id="invoicesTableBUY" style="display:none;">
			<tr id="infoRow">
				<th class="info">Numero Fattura</th>
    			<th class="info">Acquirente</th>
    			<th class="info">Venditore</th> 
    			<th class="info">Prodotti</th> 
    			<th class="info">Totale</th> 
  			</tr>
		</table>
		
		<table id="invoicesTableSELL" style="display:none;">
			<tr id="infoRow">
				<th class="info">Numero Fattura</th>
    			<th class="info">Acquirente</th>
    			<th class="info">Venditore</th> 
    			<th class="info">Prodotti</th> 
    			<th class="info">Totale</th> 
  			</tr>
		</table>
		</div>
<script type="text/javascript" src="./js/invoiceScriptSimo.js"></script>
<script type="text/javascript" src="./js/singlePage.js"></script>
</body>
</div>
<jsp:include page="footer.jsp" /> 
</html>
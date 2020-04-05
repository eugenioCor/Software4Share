<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html" 
	pageEncoding="ISO-8859-1" import="S4S_Model.*" import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">

<head>
 <jsp:include page="header.jsp" />
<title>Software4Share</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="Let's sell our softwares!">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="./css/homepage_css.css">
<link rel="stylesheet" href="./css/alertBox.css">
<script type="text/javascript" src="./Jquery/jQuery.js"></script>
</head>

<body>

<script type="text/javascript" src="./js/dynamicHome.js"></script>





<div id="hideable">

<div class="product-section">
<div class="product-container">
				
</div>
            
</div>

</div>


<script type="text/javascript" src="./js/singlePage.js"></script>

</body>
<script type="text/javascript">
$(document).ready(function(){
	var products =(<%=new Gson().toJson(request.getAttribute("Products"))%>);
	var user =(<%=new Gson().toJson(request.getAttribute("user"))%>);
	var reviews =(<%=new Gson().toJson(request.getAttribute("reviews"))%>);
	if(products==null)
		{
		setVoidPage()
		}
	else
		{
		generateHomePage(products,reviews);
		loadComponents();
		loadButtons(products,user);
		}
});
</script>
 <jsp:include page="footer.jsp" /> 
</html>
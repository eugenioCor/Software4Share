<%@page import="S4S_Model.User"%>
<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%
// Verifica che una pagina sia bloccata
Boolean pageBlock = (Boolean) session.getAttribute("pageBlock");
String usernameBlocked = (String) session.getAttribute("username");
User user = (User) session.getAttribute("user");
if ((user!= null))
{	
    response.sendRedirect("homepage.jsp");
    return;
}
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <title>Pagina di Login</title>

    <meta charset="UTF-8"></meta>

    <meta name="viewport" content="width=device-width, initial-scale=1"></meta>

    <link rel="stylesheet" type="text/css" href="./css/LoginAndRegisterStyle.css"></link>
    <link rel="stylesheet" href="./css/alertBox.css">
    <script src="Jquery/jQuery.js"></script>
    <script src="./js/loadComponent.js"></script>
  
</head>

<body>
    <div class="limiter">
        <div class="container-login100">
            <div class="wrap-login100">
                <div class="login100-form-title-image" style="background-image: url(images/images.jpg);">
                    <span class="login100-form-title-text">Login</span>
                </div>

                <form class="login100-form control-form">
                    <div class="wrap-input100 control-input wrapLogin">
                        
                        
                        <span class="label-input100 label-icon-user">Username</span>

                        <input class="input100 inputStyle" type="text" name="username" id="uname" placeholder="inserisci username" onchange="checkUsername()" ></input>

                        <span class="focus-input100 "></span>
                    </div>

                    <div class="wrap-input100 control-input wrapLogin">
                        <span class="label-input100 label-icon-pass-lock" id="toggle-pass" onclick="hideShowPass(0)">Password</span>

                        <input class="input100 inputStyle" type="password" id="pass" placeholder="inserisci password" onchange="checkPass()" maxlength="25"></input>

                        <span class="focus-input100"></span>
                        <!-- Questo elemento serve per l'effetto di sottolineatura quando clicchiamo sul campo di input -->
                    </div>
                    <div class="contact100-form-checkbox">
                        <input class="input-checkbox100" id="ckb1" type="checkbox" name="remember-me">
                        <label class="label-checkbox100" for="ckb1">
                            Ricordami
                        </label>
                    </div>
                    <div class="container-links">
                        <a href="#" id="forgot">Password dimenticata?</a>

                        <a href="./registration.jsp">Non sei registrato?</a>
                    </div>

                    <div class="container-login100-form-btn">
                        <input type="button" value="Accedi!" class="login100-form-btn" id="ctrl-1"></input>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="./js/control-scriptRegAndLog.js"></script>
    <script type="text/javascript" src="./js/singlePage.js"></script>

</body>
 <% if(pageBlock!=null)
	{
	if ((pageBlock.booleanValue()))
	{
	%>
	<script type="text/javascript">
	var name = String(<%=new Gson().toJson(usernameBlocked)%>);//ho convertito con gson perchè jsp e js sono incompatibili (java !=javascript)
	blockPage(name);
	</script>			
<%	
	}
}
%>


</html>
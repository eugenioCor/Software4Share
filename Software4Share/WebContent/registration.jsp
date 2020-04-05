<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Pagina di Registraione</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/LoginAndRegisterStyle.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script src="./Jquery/jQuery.js"></script>
  	<script src="./js/loadComponent.js"></script>
</head>

<body>

    <div class="limiter">
        <div class="container-login100">
            <div class="wrap-login100 ">
                <div class="login100-form-title-image" style="background-image: url(./images/images.jpg);">
                    <span class="login100-form-title-text">
						Registrazione
					</span>
                </div>

                <form class="login100-form control-form">
                    <table>
                        <tbody>
                            <tr>

                                <td>
                                    <div class="wrap-input100 control-input ">
                                        <span class="label-input100 label-icon-write">Nome</span>
                                        <input class="input100 inputStyle txt" type="text" name="name" id="name" placeholder="Inserisci Nome" autocomplete="off" onchange="checkText('name')">
                                        <span class="focus-input100"></span>
                                    </div>
                                </td>
                                <td>
                                    <div class="wrap-input100 control-input ">
                                        <span class="label-input100 label-icon-write">Cognome</span>
                                        <input class="input100  inputStyle txt" type="text" name="surname" id="surname" placeholder="Inserisci Cognome" autocomplete="off" onchange="checkText('surname')">
                                        <span class="focus-input100"></span>
                                    </div>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <div class="wrap-input100 control-input ">
                                        <span class="label-input100 label-icon-user">Username</span>
                                        <input class="input100 inputStyle" type="text" name="username" id="uname" placeholder="Inserire username" onchange="checkUsername()" autocomplete="off">
                                        <span class="focus-input100"></span>
                                    </div>
                                </td>

                                <td>
                                    <div class="wrap-input100 control-input ">
                                        <span class="label-input100 label-icon-iva"> Partita IVA</span>
                                        <input class="input100 inputStyle" type="text" name="pIva" id="iva" placeholder="Inserisci partita IVA" onchange="checkIva()" maxlength="10" autocomplete="off">
                                        <span class="focus-input100"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="wrap-input100 control-input ">
                                        <span class="label-input100 label-icon-date">Data di nascita</span>
                                        <input class="input100 inputStyle" type="date" name="birthday" id="date" onchange="checkDate()">
                                        <span class="focus-input100"></span>
                                    </div>
                                </td>
                                <td>
                                    <div class="wrap-input100 control-input ">
                                        <span class="label-input100 label-icon-email">Email</span>
                                        <input class="input100 inputStyle" type="email" name="email" id="email" placeholder="Inserisci  mail" onchange="checkEmail()" autocomplete="off">
                                        <span class="focus-input100"></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="wrap-input100 control-input ">
                                        <div class="label-icon-pass-lock lockImage" id="toggle-pass" onclick="hideShowPass(0)"> </div>
                                        <span class="label-input100 lockSpace" id="toggle-pass">Password</span>
                                        <input class="input100 inputStyle pass" type="password" name="pass" id="pass" placeholder="Inserire la password" minlength="8" maxlength="20" onchange="checkPass()" autocomplete="new-password">
                                        <span class="focus-input100 show-pass"></span>
                                    </div>
                                </td>

                                <td>
                                    <div class="wrap-input100 control-input">
                                        <div class="label-icon-pass-lock lockImage" id="toggle-pass2" onclick="hideShowPass(1)"> </div>
                                        <span class="label-input100"> Conferma Password</span>
                                        <input class="input100 inputStyle pass" type="password" name="confPass" id="pass2" placeholder="Inserire la password" onchange="checkEqual()">
                                        <span class="focus-input100"></span>
                                    </div>
                                </td>
                            </tr>


                        </tbody>
                    </table>
                    <div class="container-login100-form-btn">
                        <input type="button" value="Registrati!" class="login100-form-btn" id="ctrl-2">
                    </div>
                </form>
            </div>
        </div>
    </div>


    <script src="./js/control-scriptRegAndLog.js"></script>
    <script type="text/javascript" src="./js/singlePage.js"></script>
</body>

</html>
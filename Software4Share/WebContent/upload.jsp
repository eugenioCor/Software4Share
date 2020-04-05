<%@page import="S4S_Model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%
User user =(User)session.getAttribute("user");
if (user==null)
{	
    response.sendRedirect("./login.jsp");
    return;
}
if(user!=null){
	if(user.isAdministrator())
	{
		response.sendRedirect("./homepage.jsp");
	    return;
	}
	else if(user.getpIVA()==null)
	{
		response.sendRedirect("./homepage.jsp");
	    return;
	}
}

%>
<html>

<head>
    <title>Pagina di Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="./css/upload.css">
    <link rel="stylesheet" href="./css/alertBox.css">
    <script src="./Jquery/jQuery.js">
    </script>
    
</head>
<jsp:include page="./header.jsp"/> 
<body>

    <div class="master-container">
        <div class="title-box">
            <div class="title">Upload<span id="uploadIcon"></span></div>

        </div>

        <div class="upload_section">
            <div class="upload_containter">
                <form action="UploadServlet" method="post" class="formStyle" id="formControl" enctype="multipart/form-data">
                    <table>
                        <tr>
                            <td><span class="label-Upload">Nome Prodotto: </span></td>
                            <td>
                                <div class="control-input">
                                    <input class="control-upload control inputStyle" type="text" id="productName" name="productName" minlength="5" maxlength="50" autocomplete="off" placeholder="Inserisci il nome del prodotto" onchange="checkTxt(1)" /></span>

                                </div>

                            </td>
                        </tr>
                        <tr>
                            <td><span class="label-Upload">Categoria: </span></td>
                            <td>

                                <div class="categorySelectorContainer">
                                    <select name="category" id="categorySelector">
                                        
                                      </select>
                                </div>

                            </td>
                        </tr>
                        <tr>

                            <td><span class="label-Upload">Descrizione: </span></td>
                            <td>
                                <div class="control-input-description control-input">
                                    <textarea class="control-upload control descriptionStyle" name="description" id="descriptionText" autocomplete="off" placeholder="Inserire una descrizione per il prodotto" maxlength="500"></textarea>
                                </div>
                            </td>


                        </tr>
                        <tr>
                            <td><span class="label-Upload">Prezzo: </span></td>
                            <td>
                                <div class="control-input">
                                    <input class="control-upload control inputStyle" type="number" step="any" autocomplete="off" id="productPrice" name="price" placeholder="Inserisci il prezzo del prodotto" onchange="checkPrice()" />

                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="label-Upload">Programma:</span></td>

                            <td>
                                <div class="file-upload control-input">
                                    <div class="file-select">
                                        <div class="file-select-button" id="fileName">Seleziona File</div>
                                        <div class="file-select-name noFile">Nessun file selezionato</div>
                                        <input type="file" name="file" class="chooseFile control control-upload" accept=".zip,.exe,.rar" onchange="setFileName(this);checkExtention(this)">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="label-Upload">Tags: </span></td>
                            <td>
                                <div class="control-input">
                                    <input class=" control inputStyle" type="text" autocomplete="off" id="productTag" name="Tags" placeholder="Inserire i tag del prodotto " onchange="checkTxt(2)" />
                                </div>
                            </td>

                        </tr>
                        <tr>
                            <td><span class="label-Upload">Foto 1: </span></td>
                            <td>
                                <div class="file-upload control-input">
                                    <div class="file-select">
                                        <div class="file-select-button" id="fileName">Seleziona Foto</div>
                                        <div class="file-select-name noFile">Nessun file selezionato</div>
                                        <input type="file" name="photo1" id="photo1" class="chooseFile control control-upload" accept="image/*" onchange="setFileName(this);checkExtention(this)">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="label-Upload"> Foto 2: </span></td>
                            <td>
                                <div class="file-upload void">
                                    <div class="file-select">
                                        <div class="file-select-button" id="fileName">Seleziona Foto</div>
                                        <div class="file-select-name noFile">Nessun file selezionato</div>
                                        <input type="file" name="photo2" id="photo2" class="chooseFile control" accept="image/*" onchange="setFileName(this);checkExtention(this)">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="label-Upload"> Foto 3: </span></td>
                            <td>
                                <div class="file-upload void">
                                    <div class="file-select">
                                        <div class="file-select-button" id="fileName">Seleziona Foto</div>
                                        <div class="file-select-name noFile">Nessun file selezionato</div>
                                        <input type="file" name="photo3" id="photo3" class="chooseFile control" accept="image/*" onchange="setFileName(this);checkExtention(this)">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="label-Upload"> Foto 4: </span></td>
                            <td>
                                <div class="file-upload void">
                                    <div class="file-select">
                                        <div class="file-select-button" id="fileName">Seleziona Foto</div>
                                        <div class="file-select-name noFile">Nessun file selezionato</div>
                                        <input type="file" name="photo4" id="photo4" class="chooseFile control" accept="image/*" onchange="setFileName(this);checkExtention(this)">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="label-Upload"> Foto 5: </span></td>
                            <td>
                                <div class="file-upload void">
                                    <div class="file-select">
                                        <div class="file-select-button" id="fileName">Seleziona Foto</div>
                                        <div class="file-select-name noFile">Nessun file selezionato</div>
                                        <input type="file" name="photo5" id="photo5" class="chooseFile control" accept="image/*" onchange="setFileName(this);checkExtention(this)">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="container-Upload-form-btn">
                                    <input type="submit" id="buttonSubmit" value="Upload" class="Upload-form-btn styleButton">
                                </div>
                            </td>
                            <td>
                                <div class="container-Upload-form-btn">
                                    <input type="button" id="buttonReset" value="Resetta forms" class="Cancel-form-btn styleButton" onclick="resetForm()">
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
    <script>
    </script>
    <script src="./js/controlUpload.js"></script>
    <script type="text/javascript" src="./js/singlePage.js"></script>
</body>
<jsp:include page="footer.jsp" /> 
</html>
package S4S_Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import S4S_Database.ProductDAO;
import S4S_Model.Product;
import S4S_Model.User;


@SuppressWarnings("serial")
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10,//10MB
					maxFileSize=1024*1024*100000,//100GB
					maxRequestSize=1024*1024*10000)//10GB
public class UploadServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
			HttpSession session=req.getSession(false);
			User user = (User) session.getAttribute("user");
		
			Product product=new Product();
			String rootFolder="resources";//serve a dare il nome della cartella di root per salvare i file se gia c'è non la crea
			String rootPath=req.getServletContext().getRealPath("")+rootFolder;//costruisce la stringa contenete il percorso della root dove salviamo i file 
			String userFolder=user.getUserName();
			String userPath=rootPath+File.separator+userFolder;//serve per definire la cartella dell'utente se gia esiste non viene creata
			String userProgramPath=userPath+File.separator+"programs";//crea la cartella dove verranno inseriti i vari programmi
			String userImagesPath=userPath+File.separator+"images";//crea la cartella dove verranno inserite le varie immagini
			product.setDescription(req.getParameter("description"));
			product.setSeller(user);
			product.setCategory(req.getParameter("category"));
			product.setName(req.getParameter("productName"));
			product.setPrice(Double.parseDouble(req.getParameter("price")));
		
			File dirRoot=new File(rootPath);
			if(!dirRoot.exists())//se la cartella esiste non la crea altrimenti genera la cartella 
			{
				dirRoot.mkdirs();
			}
			
			File userDir=new File(userPath);
			if(!userDir.exists())//serve a creare la cartella dell'utente
			{
				userDir.mkdir();
			
				
			}
			
			File userProgramsDir=new File(userProgramPath);
			if(!userProgramsDir.exists())//serve a creare la cartella dei programmi dell'utente
			{
				userProgramsDir.mkdir();
			}

			File userImagesDir=new File(userImagesPath);
			if(!userImagesDir.exists())//serve a creare la cartella immagini dell'utente
			{
				userImagesDir.mkdir();
			}

			
			Part filePart=req.getPart("file");
			String fileName=user.getUserName()+"-"+filePart.getSubmittedFileName().replaceAll(" ", "_");
			String path = rootFolder+File.separator+user.getUserName()+File.separator+"programs"+File.separator+fileName;
			product.setFilePath(path);
			InputStream inputStream=filePart.getInputStream();
			Files.copy(inputStream,Paths.get(userProgramPath +File.separator+fileName), StandardCopyOption.REPLACE_EXISTING);
			inputStream.close();
			ArrayList<String> imagesNames=new ArrayList<>();
			for(int i=0;i<5;i++)
			{
				Part photo=req.getPart("photo"+(i+1));
				if(photo.getSize()!=0)
				{

					inputStream=photo.getInputStream();
					String photoName="id_"+user.getUserNumber()+"_"+user.getUserName()+"_"+fileName+"_"+photo.getSubmittedFileName().replaceAll(" ", "_");
					
					Files.copy(inputStream,Paths.get(userImagesPath +File.separator+photoName), StandardCopyOption.REPLACE_EXISTING);
					imagesNames.add(rootFolder+File.separator+user.getUserName()+File.separator+"images"+File.separator+photoName);
					inputStream.close();
				}
			}
			product.setImages(imagesNames);
			
		
			ProductDAO.UploadFileInDB(product);
			resp.sendRedirect("upload.jsp");
		} catch (Exception e)
		{
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}

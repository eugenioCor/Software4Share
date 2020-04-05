package S4S_Controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import S4S_Database.ProductDAO;
import S4S_Model.Cart;
import S4S_Model.User;

@SuppressWarnings("serial")
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		User user = (User) session.getAttribute("user"); 
		String[] item= req.getParameterValues("selectedItem");
		session.setAttribute("cart", null);
		ArrayList<String> select=new ArrayList<String>(Arrays.asList(item));
		ArrayList<File> files=new ArrayList<File>();
		
			
		
			try {
				ProductDAO.createInvoice(user, cart,"visa");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		for(int i=0;i<select.size();i++)
		{
			if(select.get(i).equalsIgnoreCase("on"))
			{
				String fileName =req.getServletContext().getRealPath("")+ cart.getProductList().get(i).getProduct().getFilePath();
				
				files.add(new File(fileName));
			}
		}
		
		byte[] buffer=new byte[BUFFER_SIZE];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(baos);
        
        for (int i=0; i<files.size(); i++) {
            FileInputStream fis = new FileInputStream(files.get(i));
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            String entryname = files.get(i).getName();
            out.putNextEntry(new ZipEntry(entryname));
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) !=-1) {
                out.write(buffer, 0, bytesRead);
            }
            out.closeEntry();
            bis.close();
            fis.close();
          }
        
        out.flush();
        baos.flush();
        out.close();
        baos.close();  
        ServletOutputStream sos = resp.getOutputStream();
        resp.setContentType("application/zip");
        resp.setHeader("Content-Disposition", "attachment; filename=\"Programmi.zip\"");
        sos.write(baos.toByteArray());
        out.flush();
        out.close();
        sos.flush();
		
        
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private static int BUFFER_SIZE=1024*10000;

}

package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import S4S_Database.ProductDAO;



@SuppressWarnings("serial")
@WebServlet("/CategoryControlServlet")
public class CategoryControlServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String responseGsonString="";
		try
		{
			responseGsonString = new Gson().toJson(ProductDAO.categories());
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		resp.getWriter().write(responseGsonString);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);;
	}
}

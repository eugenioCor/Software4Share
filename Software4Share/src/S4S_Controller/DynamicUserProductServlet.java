package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import S4S_Database.ProductDAO;
import S4S_Database.UserDAO;
import S4S_Model.Product;
import S4S_Model.User;

/**
 * Servlet implementation class DynamicUserProductServlet
 */
@WebServlet("/DynamicUserProductServlet")
public class DynamicUserProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DynamicUserProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();

		synchronized (session)
		{
			User us = (User) session.getAttribute("user");
			
			ArrayList<Product> list = new ArrayList<Product>();
			try
			{
				list = ProductDAO.findBySeller(us);
				if(list == null)
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					
					response.getWriter().write(gg.toJson(null));
				}
				else
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					
					response.getWriter().write(gg.toJson(list));
				}
			}
			catch(SQLException e)
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			
			try
			{
				String idUser= request.getParameter("id");
				User us = UserDAO.FindUser(Integer.parseInt(idUser));

				
				ArrayList<Product> list = new ArrayList<Product>();
				list = ProductDAO.findBySeller(us);
				if(list == null)
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					
					response.getWriter().write(gg.toJson(null));
				}
				else
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					
					response.getWriter().write(gg.toJson(list));
				}
			}
			catch(SQLException e)
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}



package S4S_Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import S4S_Database.ProductDAO;
import S4S_Database.UserDAO;
import S4S_Model.User;

/**
 * Servlet implementation class RemoveUserServlet
 */
@WebServlet("/RemoveUserServlet")
public class RemoveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveUserServlet() {
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
			
			int codex = 0;
			
			try 
			{
				codex = us.getUserNumber();
				if(ProductDAO.checkLastOrder(us.getName(), us.getSurname(), us.getEmail()))
				{
					UserDAO.removeUser(codex, us);
					session.setAttribute("authenticated", false);
					session.setAttribute("user", null);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					response.getWriter().write(gg.toJson(true));
				}
				else
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
				
					response.getWriter().write(gg.toJson(false));	
				}
			}
			catch(Exception e)
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
				String idUser = request.getParameter("id");
				User user =UserDAO.FindUser(Integer.parseInt(idUser));
				
				if(ProductDAO.checkLastOrder(user.getName(), user.getSurname(), user.getEmail()))
				{
					UserDAO.removeUser(user.getUserNumber(), user);
					
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					response.getWriter().write(gg.toJson(true));
				}
				else
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					
					response.getWriter().write(gg.toJson(false));	
				}
			}
			catch(Exception e)
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				e.printStackTrace();
				return;
			}
			
		
	}

}

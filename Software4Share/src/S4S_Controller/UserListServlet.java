package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import S4S_Database.UserDAO;
import S4S_Model.User;


@SuppressWarnings("serial")
@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
			try
			{
				ArrayList<User> users = new ArrayList<User>();
				users=UserDAO.FindAllUser();
				
				String responseGsonString= new Gson().toJson(users);
				
				resp.getWriter().write(responseGsonString);
			}
			catch(SQLException e)
			{
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				e.printStackTrace();
				return;
			}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}

package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import S4S_Database.UserDAO;
import S4S_Model.User;
import S4S_utility.MailUtility;

@SuppressWarnings("serial")
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		User user=new User();
		user.setName(req.getParameter("name"));
		user.setSurname(req.getParameter("surname"));
		user.setUserName(req.getParameter("username"));
		user.setpIVA(req.getParameter("pIva"));
		user.setBirthDay(req.getParameter("birthday"));
		user.setPassword(req.getParameter("pass"));
		user.setEmail(req.getParameter("email"));
		String redirectPage="";
		HttpSession session= req.getSession();
		synchronized (session)
		{
			try
			{
				UserDAO.InsertUser(user);
				session.setAttribute("authenticated",true);
				session.setAttribute("user",user);
				String mailObject="Registrazione S4S";
				String mailMsg="Gentile "+user.getName()+" "+user.getSurname()+" la informiamo che la sua registrazione al nostro sito Software 4 Share \u00e8 avvenuta con successo.\n"
						+ "Le sue credenziali sono:\n"
						+ "Username: "+user.getUserName()+"\n"
								+ "Password: "+user.getPassword()+"\nLe preghiamo di conservare la mail per ricordare le proprie credenziali.\nCordiali saluti dal Team di Software 4 Share";
				MailUtility.sendMail(user.getEmail(),mailObject, mailMsg);
				redirectPage+="homepage.jsp";
				
				resp.sendRedirect(redirectPage);
			} catch (SQLException e)
			{
				
				e.printStackTrace();
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			} catch (MessagingException e)
			{
				e.printStackTrace();
				resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, e.getMessage());
			}
				
		}
		
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doPost(req, resp);
	}
}

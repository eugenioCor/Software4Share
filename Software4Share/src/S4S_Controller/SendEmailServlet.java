package S4S_Controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import S4S_Model.User;
import S4S_utility.MailUtility;

/**
 * Servlet implementation class SendEmailServlet
 */
@WebServlet("/SendEmailServlet")
public class SendEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmailServlet() {
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
			
			if(us == null)
			{
				String mail = request.getParameter("Email");
				String object = request.getParameter("Object");
				String message = request.getParameter("Text");
				
				try
				{
					MailUtility.sendMailFrmoUser(mail, object, message);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					response.getWriter().write(gg.toJson("Mail inviata con succcesso!"));
				}
				catch(MessagingException e)
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					response.getWriter().write(gg.toJson("Si è verificato un errore durante l'invio dell'email"));	
				}
			}
			else
			{
				String userMail = us.getEmail();
				String object = request.getParameter("Object");
				String message = request.getParameter("Text");
				
				try
				{
					MailUtility.sendMailFrmoUser(userMail, object, message);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					response.getWriter().write(gg.toJson("Mail inviata con succcesso!"));
				}
				catch(MessagingException e)
				{
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					response.getWriter().write(gg.toJson("Si è verificato un errore durante l'invio dell'email"));	
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

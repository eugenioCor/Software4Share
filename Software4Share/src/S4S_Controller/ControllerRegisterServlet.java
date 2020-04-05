package S4S_Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import S4S_Database.UserDAO;
import S4S_Model.ControlAjaxLogAndRegBean;
import S4S_utility.MailUtility;

@SuppressWarnings("serial")
@WebServlet("/ControllerRegisterServlet")
public class ControllerRegisterServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String email=req.getParameter("email");
		String userName=req.getParameter("userName");
		String pass=req.getParameter("pass");
		String code=req.getParameter("unlock");
		String forgot=req.getParameter("forgot");
		boolean isAdmin=false;
		boolean isUser=false;
		
		ControlAjaxLogAndRegBean controller= new ControlAjaxLogAndRegBean();

		try
		{
			isAdmin=UserDAO.isAdmin(userName);
			isUser=UserDAO.isUser(userName);
			controller.setCheckEmail(UserDAO.existEmail(email,controller));
			controller.setCheckUsername((isAdmin||isUser));
		
			if(forgot!=null)
			{
				if(controller.isCheckEmail() && forgot.equals("true"))
				{
					MailUtility.sendMailCode(email, controller.isCheckAdmin());
					
				}
				if(code!=null && forgot.equals("pass"))
				{
					controller.setUnlock(UserDAO.verifyCodeByMail(code, email, controller.isCheckAdmin()));
				}
				if(pass!=null && forgot.equals("changePass"))
				{
					UserDAO.changePass(email, pass, controller.isCheckAdmin());
					String objet="Cambio Password";
					String msg="Le comunichiamo che in base alla richiesta di una nuova password, quest'utlima è stata cambiata con successo.\nLa sua nuova password è :"+pass+"\nCordiali saluti dal team Software 4 Share ";
					MailUtility.sendMail(email, objet, msg);
					controller.setUnlock(true);
				}
			}
			if(!controller.isCheckUsername())
			{
				controller.setCheckpass(false);
				controller.setCheckTry(0);
				
				String responseGsonString= new Gson().toJson(controller);
				
				resp.getWriter().write(responseGsonString);
			
				
			}
			else 
			{	
				if(pass!=null)
				{
				
					controller.setCheckpass(UserDAO.checkUser(userName,pass,isAdmin));
					controller.setCheckTry(UserDAO.userTry(userName,isAdmin));
					
					if(controller.getCheckTry()==0)
					{
						session.setAttribute("pageBlock", true);
						session.setAttribute("username", userName);
						MailUtility.sendMail(UserDAO.getMail(userName, isAdmin),isAdmin);
					}
				}
				if(code!=null)
				{
					controller.setUnlock(UserDAO.verifyCode(code, userName, isAdmin));
					if(controller.isUnlock())
					{
						session.setAttribute("pageBlock", false);
					}
				}
				String responseGsonString= new Gson().toJson(controller);
				
				resp.getWriter().write(responseGsonString);
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

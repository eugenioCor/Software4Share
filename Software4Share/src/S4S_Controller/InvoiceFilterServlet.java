package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import S4S_Database.InvoiceDAO;
import S4S_Database.UserDAO;
import S4S_Model.Invoice;

/**
 * Servlet implementation class InvoiceFilterServlet
 */
@WebServlet("/InvoiceFilterServlet")
public class InvoiceFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String usName = (String) request.getParameter("User");
		String fromDate = (String) request.getParameter("FromData");
		String toDate = (String) request.getParameter("ToData");
		InvoiceDAO iDao = new InvoiceDAO();
		ArrayList<Invoice> inv = new ArrayList<Invoice>();
		
		System.out.println("PUDDI GIGA PUDDI");
		System.out.println(usName + " " + fromDate + " " + toDate);
		SimpleDateFormat sdp = new SimpleDateFormat();
		sdp.applyPattern("yyyy-MM-dd");
		String currentDate = sdp.format(new Date());
		System.out.println(currentDate );
		try
		{
			if(usName == null && fromDate == null)
			{	
				inv = iDao.SearchBetweenDates(currentDate, toDate);
			}
			else if(toDate == null && fromDate == null)
			{
				String mail = UserDAO.getMail(usName, false);
				inv = iDao.findByMail(mail);
			}
			else if(toDate == null && usName == null)
			{
				inv = iDao.SearchBetweenDates(fromDate, currentDate);
			}
			else if(usName == null)
			{
				inv = iDao.SearchBetweenDates(fromDate, toDate);
			}
			else if(toDate == null)
			{
				String mail = UserDAO.getMail(usName, false);
				inv = iDao.SearchBetweenDatesAndClients(fromDate, currentDate, mail);
			}
			else if(fromDate == null)
			{
				String mail = UserDAO.getMail(usName, false);
				inv = iDao.SearchBetweenDatesAndClients(currentDate, toDate, mail);
			}
			else
			{
				System.out.println("GAY GENIO");
				String mail = UserDAO.getMail(usName, false);
				inv = iDao.SearchBetweenDatesAndClients(fromDate, toDate, mail);
			}
		}
		catch(SQLException e)
		{
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			e.printStackTrace();
			return;
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gg = new Gson();
		System.out.println(gg.toJson(inv));
		response.getWriter().write(gg.toJson(inv));	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

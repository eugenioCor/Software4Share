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

import S4S_Database.InvoiceDAO;
import S4S_Model.Invoice;
import S4S_Model.ListSeller;
import S4S_Model.User;

/**
 * Servlet implementation class InvoiceUserServlet
 */
@WebServlet("/InvoiceUserServlet")
public class InvoiceUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceUserServlet() {
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
			InvoiceDAO iDao = new InvoiceDAO();
			User us = (User) session.getAttribute("user");
			String mail = us.getEmail();
			ArrayList<Invoice> inv = new ArrayList<Invoice>();
			ListSeller ls = new ListSeller();
			try
			{
				System.out.println("PUDDIIIII");
				if(us.isAdministrator())
				{
					inv = iDao.getAllInv();
					
					if(inv == null)
					{
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						Gson gg = new Gson();
						System.out.println(gg.toJson(0));
						response.getWriter().write(gg.toJson(0));
					}
					else
					{
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						Gson gg = new Gson();
						System.out.println(gg.toJson(inv));
						response.getWriter().write(gg.toJson(inv));
					}
				}
				else if(us.getpIVA().equals(""))
				{
					inv = iDao.findBuyerInvoice(mail);
					
					if(inv == null)
					{
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						Gson gg = new Gson();
						System.out.println(gg.toJson(0));
						response.getWriter().write(gg.toJson(0));
					}
					else
					{
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						Gson gg = new Gson();
						System.out.println(gg.toJson(inv));
						response.getWriter().write(gg.toJson(inv));
					}	
				}
				else
				{
					inv = iDao.findBuyerInvoice(mail);
					if(inv == null)
					{
						ls.setBuyerInvoice(null);
					}
					else
					{
						ls.setBuyerInvoice(inv);
					}
					
					inv = new ArrayList<Invoice>();
					inv = iDao.findSellerInvoice(mail);
					if(inv == null)
					{
						ls.setSellerInvoice(null);
					}
					else
					{
						ls.setSellerInvoice(inv);
					}
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					Gson gg = new Gson();
					System.out.println(gg.toJson(ls));
					response.getWriter().write(gg.toJson(ls));	
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

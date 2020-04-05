package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import S4S_Database.ProductDAO;
import S4S_Model.Product;



/**
 * Servlet implementation class SingleProductServlet
 */
@SuppressWarnings("serial")
@WebServlet("/SingleProductServlet")

public class SingleProductServlet extends HttpServlet {
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	
	
		Product product = new Product();
		String parameter = request.getParameter("image");
		
		parameter = parameter.replaceAll("/", "\\\\");
		HttpSession session = request.getSession();
		
		synchronized (session) {
		
			try
			{
				product = ProductDAO.findByImage(parameter);
				
				session.setAttribute("singleProduct",product);
				
				String responseGsonString= new Gson().toJson("productPage.jsp");

				response.getWriter().write(responseGsonString);
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

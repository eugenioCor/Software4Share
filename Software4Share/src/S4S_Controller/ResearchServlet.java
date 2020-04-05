package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import S4S_Database.ProductDAO;
import S4S_Model.Product;
import S4S_Model.User;


/**
 * Servlet implementation class ResearchServlet
 */
@WebServlet("/ResearchServlet")
public class ResearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		ArrayList<Product> products = new ArrayList<Product>();
		ArrayList<Integer> reviews = new ArrayList<Integer>();
		String parameter = request.getParameter("searchParameter");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		request.setAttribute("user",user);
	
			try
			{
				products = ProductDAO.findByName(parameter);
				if(products == null)
				{
					request.setAttribute("Products", null);
				}
				else
				{
					request.setAttribute("Products", products);
				}
				
				if(products!=null)
				{
					
					for (Product product : products) 
					{		
						try {
							reviews.add(ProductDAO.getAvgReviews(product.getIdProduct()));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				request.setAttribute("reviews",reviews);
			}
			catch(SQLException e)
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				e.printStackTrace();
				return;
			}
//	}		
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ResultResearch.jsp");
			dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

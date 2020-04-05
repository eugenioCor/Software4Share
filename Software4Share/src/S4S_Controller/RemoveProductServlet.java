package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import S4S_Database.ProductDAO;
import S4S_Model.Product;

/**
 * Servlet implementation class RemoveProductServlet
 */
@WebServlet("/RemoveProductServlet")
public class RemoveProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("productName");
		int productNum = 0;
		Product product = new Product();
		try
		{
			product = ProductDAO.findByID(Integer.parseInt(name));
			ProductDAO.removeProduct(Integer.parseInt(name));
			productNum = ProductDAO.countBySeller(product.getSeller().getUserName());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Gson gg = new Gson();
			
			response.getWriter().write(gg.toJson(productNum));
		}
		catch(SQLException e)
		{
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			e.printStackTrace();
			return;
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

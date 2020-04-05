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

import S4S_Database.ProductDAO;
import S4S_Model.Product;

/**
 * Servlet per ottenere tutti i prodotti in ordine alfabetico, usata nel CATALOGO
 */
@WebServlet("/DynamicCatPageServlet")
public class DynamicCatPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DynamicCatPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Product> prod = new ArrayList<Product>();
		
		try
		{
			prod = ProductDAO.getAllProductsInOrder();
			//Qui prelevo tutti gli oggetti in ordine alfabetico
			
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
		response.getWriter().write(gg.toJson(prod));	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

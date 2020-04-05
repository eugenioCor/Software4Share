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

import S4S_Database.ProductDAO;

import S4S_Model.Product;
import S4S_Model.User;

/*
 * Questa è la servlet per ottenere i prodotti per una determinata categoria desiderata
 */

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Product> products = new ArrayList<Product>();
		String cat = request.getParameter("category");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ArrayList<Integer> reviews = new ArrayList<Integer>();
		try
		{
			if(cat.equalsIgnoreCase("Tutti"))
			{
				products = ProductDAO.getAllProductsInOrder();
				if(products!=null)
				{
					
					for (Product product : products) 
					{
						
						reviews.add(ProductDAO.getAvgReviews(product.getIdProduct()));
					}
				}
			}
			else 
			{
				products = ProductDAO.findByCategory(cat);
				if(products!=null)
				{
					
					for (Product product : products) 
					{
						
						reviews.add(ProductDAO.getAvgReviews(product.getIdProduct()));
					}
				}
			}
			//Mi restituisce tutti i prodotti per la suddetta categoria (la ricevo dalla chiamata Ajax on change della select
			
		}
		catch(SQLException e)
		{
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			e.printStackTrace();
			return;
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add(products);
		obj.add(user);
		obj.add(reviews);
		Gson gg = new Gson();
		response.getWriter().write(gg.toJson(obj));	
		//Passo l'oggetto Json per poter stampare nella pagina i prodotti, la pagina sarà quella iniziale del catalogo ma modificata
		//per questa occasione
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

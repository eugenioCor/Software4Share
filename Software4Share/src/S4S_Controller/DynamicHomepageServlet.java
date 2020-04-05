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

import com.google.gson.*;

import S4S_Database.ProductDAO;
import S4S_Model.Cart;
import S4S_Model.Product;
import S4S_Model.User;

/**
 * Servlet implementation class DynamicHomepageServlet
 */
@WebServlet("/DynamicHomepageServlet")
public class DynamicHomepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DynamicHomepageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ArrayList<Product> products = new ArrayList<Product>();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ArrayList<Integer> reviews = new ArrayList<Integer>();
		try
		{
			products = ProductDAO.lastSixProducts();
			if(products!=null)
			{
				
				for (Product product : products) 
				{
					
					reviews.add(ProductDAO.getAvgReviews(product.getIdProduct()));
				}
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
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add(products);
		obj.add(user);
		obj.add(reviews);
		Gson gg = new Gson();
		response.getWriter().write(gg.toJson(obj));	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
		String idProduct = request.getParameter("idProduct");
		String idUser= request.getParameter("idUser");
		String type= request.getParameter("type");
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		
		boolean exist=false;
		synchronized (session) {
	if(type.equals("wish"))
	{
		if(idProduct!=null && idUser!=null)
		{
			try {
				exist=ProductDAO.addProductToWishlist(Integer.parseInt(idProduct),Integer.parseInt(idUser));
				String responseGsonString = new Gson().toJson(exist);
				response.getWriter().write(responseGsonString);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	else if(type.equals("cart"))
	{
		
		if(cart==null)
		{
		
			cart = new Cart();
		}
	
		
		if(idProduct!=null)
		{
			try {
				cart.addProduct(ProductDAO.findByID(Integer.parseInt(idProduct)));
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		session.setAttribute("cart",cart);
		String responseGsonString = new Gson().toJson(cart.getProductList().size());
		response.getWriter().write(responseGsonString);
	}
}
		
	}
}

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
import S4S_Model.Cart;
import S4S_Model.Product;
import S4S_Model.User;

@SuppressWarnings("serial")
@WebServlet("/wishlistServlet")
public class wishlistServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		

		User user = (User)session.getAttribute("user");
		
		if(user!=null)
		{
			try {
				ArrayList<Product> products = ProductDAO.getWishlistProduct(user.getUserNumber());
				ArrayList<Integer> reviews = new ArrayList<Integer>();
				for (Product product : products) 
				{
					
					reviews.add(ProductDAO.getAvgReviews(product.getIdProduct()));
				}
				ArrayList<Object> obj=new ArrayList<Object>();
				obj.add(products);
				obj.add(reviews);
				String responseGsonString = new Gson().toJson(obj);
				resp.getWriter().write(responseGsonString);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String idProduct=req.getParameter("id");
		String type=req.getParameter("type");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		Cart cart = (Cart)session.getAttribute("cart");
		
		if(type.equals("cart"))
		{
			if(idProduct!=null)
			{
				int id = Integer.parseInt(idProduct);
				if(cart==null)
				{
				
					cart = new Cart();
				}
				try {
					cart.addProduct(ProductDAO.findByID(id));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				session.setAttribute("cart",cart);
				
			}
			
		}
		else if(type.equals("trash"))
		{
			if(idProduct!=null)
			{
				int id = Integer.parseInt(idProduct);
				try {
					ProductDAO.removeFromWishlist(user.getUserNumber(), id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
}

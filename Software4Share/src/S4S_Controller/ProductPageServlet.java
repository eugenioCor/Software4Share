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
@WebServlet("/ProductPageServlet")
public class ProductPageServlet extends HttpServlet
{	
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
		{
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			HttpSession session = req.getSession();
			User user = (User)session.getAttribute("user");
			Product product= (Product)session.getAttribute("singleProduct");
			
			String responseGsonString;
			synchronized (session) {
				try
				{
					
					ArrayList<Object> obj= new ArrayList<Object>();
					obj.add(user);
					obj.add(product);
					
					obj.add(ProductDAO.getReviews(product.getName()));
					obj.add(ProductDAO.getAvgReviews(product.getIdProduct()));
					responseGsonString = new Gson().toJson(obj);
					resp.getWriter().write(responseGsonString);
				} catch (NumberFormatException | SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
		{
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			String star =req.getParameter("star");
			String comment =req.getParameter("comment");
			String idReview=req.getParameter("idReview");
			String prod =req.getParameter("product");
			HttpSession session = req.getSession();
			User user = (User)session.getAttribute("user");
			Product product= (Product)session.getAttribute("singleProduct");
			Cart cart = (Cart)session.getAttribute("cart");
			boolean exist=false;
			synchronized (session) {
				try {
					if(star!=null)
					{
						int starValue=Integer.parseInt(star);
						
						ProductDAO.addStarReview(starValue,user.getUserNumber(),product.getIdProduct());
						String responseGsonString = new Gson().toJson(exist);
						resp.getWriter().write(responseGsonString);
						
					}
					else if(comment!=null)
					{
					
						ProductDAO.addCommentReview(comment,user.getUserNumber(),product.getIdProduct());
						String responseGsonString = new Gson().toJson(exist);
						resp.getWriter().write(responseGsonString);
					}
					else if(idReview!=null)
					{
						
						int id =Integer.parseInt(idReview);
						ProductDAO.deleteReview(id);
						String responseGsonString = new Gson().toJson(exist);
						resp.getWriter().write(responseGsonString);
					}
					else if(prod!=null)
					{
						if(prod.equals("wish"))
						{
						
							exist=ProductDAO.addProductToWishlist(product.getIdProduct(),user.getUserNumber());
							String responseGsonString = new Gson().toJson(exist);
							resp.getWriter().write(responseGsonString);
						}
						else if(prod.equals("cart"))
						{
							
							if(cart==null)
							{
							
								cart = new Cart();
							}
							
							cart.addProduct(product);
					
							session.setAttribute("cart",cart);
							String responseGsonString = new Gson().toJson(cart.getProductList().size());
							resp.getWriter().write(responseGsonString);
						}
					}
					
					
			} catch (SQLException e1) {
			
				e1.printStackTrace();
			}
			}
			
		
		}
}

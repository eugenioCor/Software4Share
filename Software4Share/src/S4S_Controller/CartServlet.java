package S4S_Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;


import S4S_Database.ProductDAO;
import S4S_Model.Cart;

import S4S_Model.ProductAndQuantity;
import S4S_Model.User;


@SuppressWarnings("serial")
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet 
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		User user = (User)session.getAttribute("user");
		synchronized (session) {
			
			
			
		
			if(cart!=null)
			{
				ArrayList<ProductAndQuantity> listProducts= cart.getProductList();
				cart=new Cart();
				for (ProductAndQuantity productAndQuantity : listProducts) 
				{
					try {
						productAndQuantity.setProduct(ProductDAO.findByID(productAndQuantity.getProduct().getIdProduct()));//è ridondande ma serve a ricaricare i prodotti in modo tale che se modifico i prodotti se ricarico il carrello queste modifiche sono viste cosa che con solo la sessione non avviene
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(user!=null)
				{
					ArrayList<String> buyed=null;
					try {
						buyed= ProductDAO.ProductAlreadyBuyed(user.getEmail());
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					
					for(int i=0;i<listProducts.size();i++)
					{
						if(listProducts.get(i).getProduct().getSeller().getUserNumber()==user.getUserNumber())
						{
							listProducts.remove(i);
						}
						if(buyed!=null)
						{
							for (String string : buyed) 
							{
								if(listProducts.get(i).getProduct().getName().equals(string))
								{
									listProducts.get(i).getProduct().setPrice(0);
								}
							}
						}
					}
					
				}
				cart.setProductList(listProducts);
				session.setAttribute("cart",cart);
				ArrayList<Object> obj= new ArrayList<Object>();
				obj.add(cart);
				obj.add(session.getAttribute("user"));
				String responseGsonString = new Gson().toJson(obj);
				resp.getWriter().write(responseGsonString);
			}
			else
			{
				
				String responseGsonString = new Gson().toJson(0);
				resp.getWriter().write(responseGsonString);
			}
		}		
		
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String psz=req.getParameter("pos");
		String quantity= req.getParameter("quantity");
		ArrayList<String> quantitiList=null;
		if(quantity!=null)
		{
			String[] quant=new Gson().fromJson(quantity, String[].class);
			quantitiList= new ArrayList<String>(Arrays.asList(quant));
			
		}
		synchronized (session) {
			Cart cart = (Cart)session.getAttribute("cart");
		
			
		
			if(cart!=null)
			{
				ArrayList<ProductAndQuantity> listProducts= cart.getProductList();
				cart=new Cart();
				int i = 0;
				for (ProductAndQuantity productAndQuantity : listProducts) 
				{
					try {
							if(quantitiList!=null)
							{
								productAndQuantity.setQuantity(Integer.parseInt(quantitiList.get(i)));
							}
							 double totalPrice = productAndQuantity.getProduct().getPrice() * productAndQuantity.getQuantity();
							 totalPrice -= (totalPrice * productAndQuantity.getProduct().getSale()) / 100;
							 totalPrice += (totalPrice * productAndQuantity.getProduct().getIVA()) / 100;
							 productAndQuantity.setTotal(totalPrice);
							productAndQuantity.setProduct(ProductDAO.findByID(productAndQuantity.getProduct().getIdProduct()));//è ridondande ma serve a ricaricare i prodotti in modo tale che se modifico i prodotti se ricarico il carrello queste modifiche sono viste cosa che con solo la sessione non avviene
							i++;
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(psz!=null)
				{
					int pos = Integer.parseInt(req.getParameter("pos"));
					listProducts.remove(pos);
				}
				
				cart.setProductList(listProducts);
				
				session.setAttribute("cart",cart);
				String responseGsonString = new Gson().toJson(cart);
				resp.getWriter().write(responseGsonString);
			}
			else
			{
				String responseGsonString = new Gson().toJson(0);
				resp.getWriter().write(responseGsonString);
			}
			
		}
	}
}

	
	
	
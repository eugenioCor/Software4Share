package S4S_Model;

import java.util.ArrayList;

public class Cart
{
	//costruttore
	 public Cart()
	{
		productList=new ArrayList<ProductAndQuantity>();
	}
	//metodi
	 public void addProduct(Product product)
	 {
		 ProductAndQuantity  paq=new ProductAndQuantity();
		 boolean exist=false;
		
			 for (ProductAndQuantity productsAndQuantity : productList) {
					if(productsAndQuantity.getProduct().getIdProduct()==product.getIdProduct())
					{
						int qt=productsAndQuantity.getQuantity();
						qt+=1;
						productsAndQuantity.setQuantity(qt);
						exist=true;
					}
				}
			 if(!exist)
			 {
				 paq.setProduct(product);
				 productList.add(paq);
			 }
		 
		 
	 }
	 
	 public ArrayList<ProductAndQuantity> getProductList() {
		return productList;
	}
	 public void setProductList(ArrayList<ProductAndQuantity> productList) {
		this.productList = productList;
	}
	 public void deleteProduct(Product product)
	 {
		 for (ProductAndQuantity product_list : productList)
		{
			if(product_list.getProduct().getName().equals(product.getName()))
			{
				productList.remove(product_list);
				break;
			}
		}
	 }
	 
	 @Override
	public String toString() {
		return productList.toString();
	}
	//variabili
	private ArrayList<ProductAndQuantity>productList;
	
}

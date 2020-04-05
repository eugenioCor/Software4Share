package S4S_Model;

public class ProdInv {

	public ProdInv()
	{
		
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setSale(int s)
	{
		sale = s;
	}
	
	public int getSale()
	{
		return sale;
	}
	
	public void setQuantity(int q)
	{
		quantity = q;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setPrice(float p)
	{
		price = p;
	}
	
	public float getPrice()
	{
		return price;
	}
	
	public void setIva(int i)
	{
		iva = i;
	}
	
	public int getIva()
	{
		return iva;
	}
	
	public String toString()
	{
		return name+" "+sale+" "+quantity+" "+price+" "+iva;
	}
	//VARIABILI
	private String name;
	private int sale;
	private int quantity;
	private float price;
	private int iva;
}

package S4S_Model;

import java.util.ArrayList;

public class Product {

	public Product()
	{ 
		//costruttore 		
	}
	//eugenio:ho aggiunto la stringa del filePath nei costuttori
	public Product(String name, String category,String filePath, double price, String description, int sale, User seller)
	{
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
		this.sale = sale;
		this.seller = seller;
		this.filePath=filePath;
	}
	
	public Product(String name, String category,String filePath, double price, String description, User seller)
	{
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
		this.seller = seller;

		this.filePath=filePath;
	}
    
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String newDescription)
	{
		description = newDescription;
	}
	
    public String getName()
    {
    	return name;
    }
    
    public void setName(String newName)
    {
    	name = newName;
    }
    
    public String getCategory()
    {
    	return category;
    }
    
    public void setCategory(String newCategory)
    {
    	category = newCategory;
    }
    
    public double getPrice()
    {
    	return price;
    }
    
    public void setPrice(double newPrice)
    {
    	price = newPrice;
    }
    
    public int getSale()
    {
    	return sale;
    }
    
    public void setSale(int newSale)
    {
    	sale = newSale;
    }

    public User getSeller()
    {
    	return seller;
    }
    
    public void setSeller(User newSeller)
    {
    	seller = newSeller;
    }

    public void setImages(ArrayList<String> newImages)
    {
    	images = newImages;
    }
    
    public ArrayList<String> getImages()
    {
    	return images;
    }
    
    public void addOneImage(String image)
    {
    	images.add(image);
    }
    //eugenio: ho aggiunto le funzioni per prendere e settare il file path
    public String getFilePath()
	{
		return filePath;
	}
    public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
    
    public int getIVA()
    {
    	return iva;
    }
    
    public void setIVA(int pIva)
    {
    	iva = pIva;
    }
    @Override
    public String toString() {
    	return "iva: "+iva+" description: "+description+" name: "+name+" category: "+category+" filePath: "+filePath+" price: "+price+" sale: "+sale+" seller: "+seller+" images:"+images;
    }
    //Variabili private
    private String description;
    private int idProduct;//eugenio:ho aggiunto un intero per indicare l'id del prodotto
    private String name;
    private String category;
    private String filePath;//eugenio:ho aggiunto la stringa per il path del file 
    private double price;
    private int sale;
    private User seller;
    private ArrayList<String> images;
    private int iva;
}

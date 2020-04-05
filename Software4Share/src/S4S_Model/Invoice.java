//File contenente i dati della invoice (Fattura) per la creazione del oggetto Invoice

package S4S_Model;

import java.util.ArrayList;

public class Invoice
{
	public Invoice()
	{
		
	}

	//get serve a ricevere i dati da un oggetto di tipo Invoice
	public int GetInvoiceId()
	{
		return id;
	}
	
	public ArrayList<ProdInv> GetProducts()
	{
		return products;
	}


	public String GetPayment()
	{
		return payment;
	}

	public String GetData()
	{
		return data;
	}

	public String GetSellerName()
	{
		return SellerName;
	}

	public String GetSellerSurname()
	{
		return SellerSurname;
	}

	public String GetSellerMail()
	{
		return SellerMail;
	}

	public String GetBuyerName()
	{
		return BuyerName;
	}

	public String GetBuyerSurname()
	{
		return BuyerSurname;
	}

	public String GetBuyerMail()
	{
		return BuyerMail;
	}

	public void SetInvoiceId(int NewInvoiceId)
	{
		this.id=NewInvoiceId;
	}


	public void SetData(String NewData)
	{
		this.data=NewData;
	}

	public void SetProduct(ArrayList<ProdInv> NewProduct)
	{
		this.products=NewProduct;
	}

	public void SetSellerName(String NewSellerName)
	{
		this.SellerName=NewSellerName;
	}

	public void SetSellerSurname(String NewSellerSurname)
	{
		this.SellerSurname=NewSellerSurname;
	}

	public void SetSellerMail(String NewSellerMail)
	{
		this.SellerMail=NewSellerMail;
	}

	public void SetBuyerName(String NewBuyerName)
	{
		this.BuyerName=NewBuyerName;
	}

	public void SetBuyerSurname(String NewBuyerSurname)
	{
		this.BuyerSurname=NewBuyerSurname;
	}

	public void SetBuyerMail(String NewBuyerMail)
	{
		this.BuyerMail=NewBuyerMail;
	}

	public void SetTotalprice(float NewTotalPrice)
	{
		this.totalPrice=NewTotalPrice;
	}

	public void setPayment(String p)
	{
		payment = p;
	}
	
	public String getPayment()
	{
		return payment;
	}
	
	public String toString()
	{
		return id + " " + payment + " " + data + " " + products.toString() + " " +SellerName+ " " +SellerSurname+ " " +SellerMail+ " " +BuyerName+ " " +BuyerSurname+ " " +BuyerMail+ " " +totalPrice;
	}

	//variabili del oggetto invoice
	private int id;
	private String payment;
	private String data;
	private ArrayList<ProdInv> products;
	private String SellerName; 
	private String SellerSurname;
	private String SellerMail;
	private String BuyerName;
	private String BuyerSurname;
	private String BuyerMail;
	private float totalPrice;
}


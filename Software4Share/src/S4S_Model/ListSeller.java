package S4S_Model;

import java.util.ArrayList;

public class ListSeller {

	public ListSeller()
	{
		
	}
	
	public void setBuyerInvoice(ArrayList<Invoice> buyer)
	{
		buyerLog = buyer;
	}
	
	public ArrayList<Invoice> getBuyerInvoice()
	{
		return buyerLog;
	}

	public void setSellerInvoice(ArrayList<Invoice> buyer)
	{
		sellerLog = buyer;
	}
	
	public ArrayList<Invoice> getSellerInvoice()
	{
		return sellerLog;
	}
	
	private ArrayList<Invoice> sellerLog;
	private ArrayList<Invoice> buyerLog;
}

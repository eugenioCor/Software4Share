package S4S_Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import S4S_Model.Invoice;
import S4S_Model.ProdInv;


import java.util.*;



public class InvoiceDAO {
	
	public ArrayList<Invoice> findBuyerInvoice(String mail) throws SQLException
	{
		Connection connection=null;
		Connection connection1=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement1=null;
		ProdInv prodinv = new ProdInv();
		Invoice invoice = new Invoice();
		ArrayList<ProdInv> pd = new ArrayList<ProdInv>();
		ArrayList<Invoice> allInv = new ArrayList<Invoice>();
		int invNum = 0;
		String inv="select * from software4share.Invoice where BuyerMail = ?";
		String pInv= "select * from software4share.ProductInIvoice where Invoice = ?";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement(inv);
			preparedStatement1.setString(1, mail);
			ResultSet rSet1 = preparedStatement1.executeQuery();
			while(rSet1.next())
			{
				invNum = rSet1.getInt("idInvoice");
				System.out.println(invNum);
				invoice.SetInvoiceId(invNum);
				invoice.SetBuyerName(rSet1.getString("BuyerName"));
				invoice.SetBuyerSurname(rSet1.getString("BuyerSurname"));
				invoice.SetBuyerMail(rSet1.getString("BuyerMail"));
				invoice.SetSellerName(rSet1.getString("SellerName"));
				invoice.SetSellerSurname(rSet1.getString("SellerSurname"));
				invoice.SetSellerMail(rSet1.getString("SellerMail"));
				invoice.setPayment(rSet1.getString("PayMethod"));
				invoice.SetData(rSet1.getString("Date"));
				invoice.SetTotalprice((float) rSet1.getDouble("TotalPrice"));
				
				try
				{
					connection1=S4S_Database.DriverManagerConnectionPool.getConnection();
					preparedStatement=connection1.prepareStatement(pInv);
					preparedStatement.setInt(1, invNum);
					ResultSet rSet=preparedStatement.executeQuery();
					while(rSet.next())
					{
						prodinv.setName(rSet.getString("Product"));
						System.out.println(prodinv.getName());
						prodinv.setSale(rSet.getInt("FinalSale"));
						prodinv.setQuantity(rSet.getInt("Quantity"));
						prodinv.setPrice((float) rSet.getDouble("FinalPrice"));
						prodinv.setIva(rSet.getInt("FinalIva"));
						pd.add(prodinv);
						prodinv = new ProdInv();
					}
				}
				finally
				{ 
					try
					{ if (preparedStatement != null)
						{	preparedStatement.close(); }
					
					} finally {
						S4S_Database.DriverManagerConnectionPool.releaseConnection(connection1);
					}
				}
				System.out.println(pd);
				invoice.SetProduct(pd);
				pd = new ArrayList<ProdInv>();
				allInv.add(invoice);
				invoice = new Invoice();
			}
			
		} finally {
			try {
				if (preparedStatement1 != null)
				{	preparedStatement1.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		System.out.println(allInv);
		
		if(allInv.isEmpty())
		{
			return null;
		}
		
		return allInv;
	}
	
	public ArrayList<Invoice> findByMail(String mail) throws SQLException
	{
		Connection connection=null;
		Connection connection1=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement1=null;
		ProdInv prodinv = new ProdInv();
		Invoice invoice = new Invoice();
		ArrayList<ProdInv> pd = new ArrayList<ProdInv>();
		ArrayList<Invoice> allInv = new ArrayList<Invoice>();
		int invNum = 0;
		String inv="select * from software4share.Invoice where (BuyerMail = ? or SellerMail = ?)";
		String pInv= "select * from software4share.ProductInIvoice where Invoice = ?";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement(inv);
			preparedStatement1.setString(1, mail);
			preparedStatement1.setString(2, mail);
			ResultSet rSet1 = preparedStatement1.executeQuery();
			while(rSet1.next())
			{
				invNum = rSet1.getInt("idInvoice");
				System.out.println(invNum);
				invoice.SetInvoiceId(invNum);
				invoice.SetBuyerName(rSet1.getString("BuyerName"));
				invoice.SetBuyerSurname(rSet1.getString("BuyerSurname"));
				invoice.SetBuyerMail(rSet1.getString("BuyerMail"));
				invoice.SetSellerName(rSet1.getString("SellerName"));
				invoice.SetSellerSurname(rSet1.getString("SellerSurname"));
				invoice.SetSellerMail(rSet1.getString("SellerMail"));
				invoice.setPayment(rSet1.getString("PayMethod"));
				invoice.SetData(rSet1.getString("Date"));
				invoice.SetTotalprice((float) rSet1.getDouble("TotalPrice"));
				
				try
				{
					connection1=S4S_Database.DriverManagerConnectionPool.getConnection();
					preparedStatement=connection1.prepareStatement(pInv);
					preparedStatement.setInt(1, invNum);
					ResultSet rSet=preparedStatement.executeQuery();
					while(rSet.next())
					{
						prodinv.setName(rSet.getString("Product"));
						System.out.println(prodinv.getName());
						prodinv.setSale(rSet.getInt("FinalSale"));
						prodinv.setQuantity(rSet.getInt("Quantity"));
						prodinv.setPrice((float) rSet.getDouble("FinalPrice"));
						prodinv.setIva(rSet.getInt("FinalIva"));
						pd.add(prodinv);
						prodinv = new ProdInv();
					}
				}
				finally
				{ 
					try
					{ if (preparedStatement != null)
						{	preparedStatement.close(); }
					
					} finally {
						S4S_Database.DriverManagerConnectionPool.releaseConnection(connection1);
					}
				}
				System.out.println(pd);
				invoice.SetProduct(pd);
				pd = new ArrayList<ProdInv>();
				allInv.add(invoice);
				invoice = new Invoice();
			}
			
		} finally {
			try {
				if (preparedStatement1 != null)
				{	preparedStatement1.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		System.out.println(allInv);
		
		if(allInv.isEmpty())
		{
			return null;
		}
		
		return allInv;
	}
	
	public ArrayList<Invoice> getAllInv() throws SQLException
	{
		Connection connection=null;
		Connection connection1=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement1=null;
		ProdInv prodinv = new ProdInv();
		Invoice invoice = new Invoice();
		ArrayList<ProdInv> pd = new ArrayList<ProdInv>();
		ArrayList<Invoice> allInv = new ArrayList<Invoice>();
		int invNum = 0;
		String inv="select * from software4share.Invoice";
		String pInv= "select * from software4share.ProductInIvoice where Invoice = ?";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement(inv);
	
			ResultSet rSet1 = preparedStatement1.executeQuery();
			while(rSet1.next())
			{
				invNum = rSet1.getInt("idInvoice");
				System.out.println(invNum);
				invoice.SetInvoiceId(invNum);
				invoice.SetBuyerName(rSet1.getString("BuyerName"));
				invoice.SetBuyerSurname(rSet1.getString("BuyerSurname"));
				invoice.SetBuyerMail(rSet1.getString("BuyerMail"));
				invoice.SetSellerName(rSet1.getString("SellerName"));
				invoice.SetSellerSurname(rSet1.getString("SellerSurname"));
				invoice.SetSellerMail(rSet1.getString("SellerMail"));
				invoice.setPayment(rSet1.getString("PayMethod"));
				invoice.SetData(rSet1.getString("Date"));
				invoice.SetTotalprice((float) rSet1.getDouble("TotalPrice"));
				
				try
				{
					connection1=S4S_Database.DriverManagerConnectionPool.getConnection();
					preparedStatement=connection1.prepareStatement(pInv);
					preparedStatement.setInt(1, invNum);
					ResultSet rSet=preparedStatement.executeQuery();
					while(rSet.next())
					{
						prodinv.setName(rSet.getString("Product"));
						System.out.println(prodinv.getName());
						prodinv.setSale(rSet.getInt("FinalSale"));
						prodinv.setQuantity(rSet.getInt("Quantity"));
						prodinv.setPrice((float) rSet.getDouble("FinalPrice"));
						prodinv.setIva(rSet.getInt("FinalIva"));
						pd.add(prodinv);
						prodinv = new ProdInv();
					}
				}
				finally
				{ 
					try
					{ if (preparedStatement != null)
						{	preparedStatement.close(); }
					
					} finally {
						S4S_Database.DriverManagerConnectionPool.releaseConnection(connection1);
					}
				}
				System.out.println(pd);
				invoice.SetProduct(pd);
				pd = new ArrayList<ProdInv>();
				allInv.add(invoice);
				invoice = new Invoice();
			}
			
		} finally {
			try {
				if (preparedStatement1 != null)
				{	preparedStatement1.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		System.out.println(allInv);
		
		if(allInv.isEmpty())
		{
			return null;
		}
		
		return allInv;
	}
	
	public ArrayList<Invoice> findSellerInvoice(String mail) throws SQLException
	{
		Connection connection=null;
		Connection connection1=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement1=null;
		ProdInv prodinv = new ProdInv();
		Invoice invoice = new Invoice();
		ArrayList<ProdInv> pd = new ArrayList<ProdInv>();
		ArrayList<Invoice> allInv = new ArrayList<Invoice>();
		int invNum = 0;
		String inv="select * from software4share.Invoice where SellerMail = ?";
		String pInv= "select * from software4share.ProductInIvoice where Invoice = ?";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement(inv);
			preparedStatement1.setString(1, mail);
			ResultSet rSet1 = preparedStatement1.executeQuery();
			while(rSet1.next())
			{
				invNum = rSet1.getInt("idInvoice");
				System.out.println(invNum);
				invoice.SetInvoiceId(invNum);
				invoice.SetBuyerName(rSet1.getString("BuyerName"));
				invoice.SetBuyerSurname(rSet1.getString("BuyerSurname"));
				invoice.SetBuyerMail(rSet1.getString("BuyerMail"));
				invoice.SetSellerName(rSet1.getString("SellerName"));
				invoice.SetSellerSurname(rSet1.getString("SellerSurname"));
				invoice.SetSellerMail(rSet1.getString("SellerMail"));
				invoice.setPayment(rSet1.getString("PayMethod"));
				invoice.SetData(rSet1.getString("Date"));
				invoice.SetTotalprice((float) rSet1.getDouble("TotalPrice"));
				
				try
				{
					connection1=S4S_Database.DriverManagerConnectionPool.getConnection();
					preparedStatement=connection1.prepareStatement(pInv);
					preparedStatement.setInt(1, invNum);
					ResultSet rSet=preparedStatement.executeQuery();
					while(rSet.next())
					{
						prodinv.setName(rSet.getString("Product"));
						System.out.println(prodinv.getName());
						prodinv.setSale(rSet.getInt("FinalSale"));
						prodinv.setQuantity(rSet.getInt("Quantity"));
						prodinv.setPrice((float) rSet.getDouble("FinalPrice"));
						prodinv.setIva(rSet.getInt("FinalIva"));
						pd.add(prodinv);
						prodinv = new ProdInv();
					}
				}
				finally
				{ 
					try
					{ if (preparedStatement != null)
						{	preparedStatement.close(); }
					
					} finally {
						S4S_Database.DriverManagerConnectionPool.releaseConnection(connection1);
					}
				}
				System.out.println(pd);
				invoice.SetProduct(pd);
				pd = new ArrayList<ProdInv>();
				allInv.add(invoice);
				invoice = new Invoice();
			}
			
		} finally {
			try {
				if (preparedStatement1 != null)
				{	preparedStatement1.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		System.out.println(allInv);
		
		if(allInv.isEmpty())
		{
			return null;
		}
		
		return allInv;
	}
	
	public static ArrayList<Invoice> SearchBetweenDates(String date1,String date2) throws SQLException 
	{
		Connection connection=null;
		Connection connection1=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement1=null;
		ProdInv prodinv = new ProdInv();
		Invoice invoice = new Invoice();
		ArrayList<ProdInv> pd = new ArrayList<ProdInv>();
		ArrayList<Invoice> allInv = new ArrayList<Invoice>();
		int invNum = 0;
		String inv="SELECT * FROM software4share.invoice WHERE Date BETWEEN ? AND ?;";
		String pInv= "select * from software4share.ProductInIvoice where Invoice = ?";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement(inv);
			preparedStatement1.setString(1, date1);
			preparedStatement1.setString(2, date2);
			ResultSet rSet1 = preparedStatement1.executeQuery();
			while(rSet1.next())
			{
				invNum = rSet1.getInt("idInvoice");
				System.out.println(invNum);
				invoice.SetInvoiceId(invNum);
				invoice.SetBuyerName(rSet1.getString("BuyerName"));
				invoice.SetBuyerSurname(rSet1.getString("BuyerSurname"));
				invoice.SetBuyerMail(rSet1.getString("BuyerMail"));
				invoice.SetSellerName(rSet1.getString("SellerName"));
				invoice.SetSellerSurname(rSet1.getString("SellerSurname"));
				invoice.SetSellerMail(rSet1.getString("SellerMail"));
				invoice.setPayment(rSet1.getString("PayMethod"));
				invoice.SetData(rSet1.getString("Date"));
				invoice.SetTotalprice((float) rSet1.getDouble("TotalPrice"));
				
				try
				{
					connection1=S4S_Database.DriverManagerConnectionPool.getConnection();
					preparedStatement=connection1.prepareStatement(pInv);
					preparedStatement.setInt(1, invNum);
					ResultSet rSet=preparedStatement.executeQuery();
					while(rSet.next())
					{
						prodinv.setName(rSet.getString("Product"));
						System.out.println(prodinv.getName());
						prodinv.setSale(rSet.getInt("FinalSale"));
						prodinv.setQuantity(rSet.getInt("Quantity"));
						prodinv.setPrice((float) rSet.getDouble("FinalPrice"));
						prodinv.setIva(rSet.getInt("FinalIva"));
						pd.add(prodinv);
						prodinv = new ProdInv();
					}
				}
				finally
				{ 
					try
					{ if (preparedStatement != null)
						{	preparedStatement.close(); }
					
					} finally {
						S4S_Database.DriverManagerConnectionPool.releaseConnection(connection1);
					}
				}
				System.out.println(pd);
				invoice.SetProduct(pd);
				pd = new ArrayList<ProdInv>();
				allInv.add(invoice);
				invoice = new Invoice();
			}
			
		} finally {
			try {
				if (preparedStatement1 != null)
				{	preparedStatement1.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		
		}
System.out.println(allInv);
		
		if(allInv.isEmpty())
		{
			return null;
		}
		
		return allInv;
	}
	
	
	public static ArrayList<Invoice> SearchBetweenDatesAndClients(String date1,String date2,String mail) throws SQLException 
	{
		Connection connection=null;
		Connection connection1=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement1=null;
		ProdInv prodinv = new ProdInv();
		Invoice invoice = new Invoice();
		ArrayList<ProdInv> pd = new ArrayList<ProdInv>();
		ArrayList<Invoice> allInv = new ArrayList<Invoice>();
		int invNum = 0;
		String inv="SELECT *FROM software4share.invoice WHERE (BuyerMail = ? and Date BETWEEN ? AND ?) or (SellerMail = ? and Date BETWEEN ? AND ?)";
		String pInv= "select * from software4share.ProductInIvoice where Invoice = ?";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement(inv);
			preparedStatement1.setString(1, mail);
			preparedStatement1.setString(2, date1);
			preparedStatement1.setString(3, date2);
			preparedStatement1.setString(4, mail);
			preparedStatement1.setString(5, date1);
			preparedStatement1.setString(6, date2);
			ResultSet rSet1 = preparedStatement1.executeQuery();
			while(rSet1.next())
			{
				invNum = rSet1.getInt("idInvoice");
				System.out.println(invNum);
				invoice.SetInvoiceId(invNum);
				invoice.SetBuyerName(rSet1.getString("BuyerName"));
				invoice.SetBuyerSurname(rSet1.getString("BuyerSurname"));
				invoice.SetBuyerMail(rSet1.getString("BuyerMail"));
				invoice.SetSellerName(rSet1.getString("SellerName"));
				invoice.SetSellerSurname(rSet1.getString("SellerSurname"));
				invoice.SetSellerMail(rSet1.getString("SellerMail"));
				invoice.setPayment(rSet1.getString("PayMethod"));
				invoice.SetData(rSet1.getString("Date"));
				invoice.SetTotalprice((float) rSet1.getDouble("TotalPrice"));
				
				try
				{
					connection1=S4S_Database.DriverManagerConnectionPool.getConnection();
					preparedStatement=connection1.prepareStatement(pInv);
					preparedStatement.setInt(1, invNum);
					ResultSet rSet=preparedStatement.executeQuery();
					while(rSet.next())
					{
						prodinv.setName(rSet.getString("Product"));
						System.out.println(prodinv.getName());
						prodinv.setSale(rSet.getInt("FinalSale"));
						prodinv.setQuantity(rSet.getInt("Quantity"));
						prodinv.setPrice((float) rSet.getDouble("FinalPrice"));
						prodinv.setIva(rSet.getInt("FinalIva"));
						pd.add(prodinv);
						prodinv = new ProdInv();
					}
				}
				finally
				{ 
					try
					{ if (preparedStatement != null)
						{	preparedStatement.close(); }
					
					} finally {
						S4S_Database.DriverManagerConnectionPool.releaseConnection(connection1);
					}
				}
				System.out.println(pd);
				invoice.SetProduct(pd);
				pd = new ArrayList<ProdInv>();
				allInv.add(invoice);
				invoice = new Invoice();
			}
			
		} finally {
			try {
				if (preparedStatement1 != null)
				{	preparedStatement1.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		
		}
System.out.println(allInv);
		
		if(allInv.isEmpty())
		{
			return null;
		}
		
		return allInv;
	}
}
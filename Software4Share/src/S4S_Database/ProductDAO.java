package S4S_Database;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import S4S_Model.Cart;
import S4S_Model.Product;
import S4S_Model.Review;
import S4S_Model.User;


public class ProductDAO
{
	//Prendo tutte le stringhe per recuperare le immagini
	public static ArrayList<String> allProductImages(int codex) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String imagesResearch = "select aImage from software4share.Image where Product = ?";
		ArrayList<String> allImages = new ArrayList<String>();
		try
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(imagesResearch);
			preparedStatement.setInt(1, codex);
			
			ResultSet rSet=preparedStatement.executeQuery();
			while(rSet.next())
			{
				allImages.add(rSet.getString("aImage"));
			}
			return allImages;
		} 
		finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} 
			finally 
			{
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public  static ArrayList<Product> findByCategory(String category) throws SQLException {
		Connection connection=null;
		Statement statement=null;
		PreparedStatement preparedStatement=null;
		ArrayList<Product> productList = new ArrayList<Product>();
		Product product = new Product();
		
		String reserach="select * from software4share.Product where Category=?";
		String ivaSearch = "select * from software4share.iva";
		int iva = 0;
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			statement = connection.createStatement();
			ResultSet rSet1 = statement.executeQuery(ivaSearch);
			while(rSet1.next())
			{
				iva = rSet1.getInt("IvaValue");
			}
			
			preparedStatement=connection.prepareStatement(reserach);
			preparedStatement.setString(1, category);
			
			ResultSet rSet=preparedStatement.executeQuery();
			int codex = 0;
			while(rSet.next())
			{
				codex = rSet.getInt("idProduct");
				product.setIdProduct(codex);
				product.setName(rSet.getString("Pname"));
				product.setCategory(rSet.getString("Category"));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setSeller(UserDAO.FindUser(rSet.getInt("Seller")));
				product.setFilePath(rSet.getString("ProductPath"));
				if(ProductDAO.isDiscount(codex))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setImages(ProductDAO.allProductImages(codex));
				product.setIVA(iva);
				productList.add(product);
				product = new Product();
			}
		} finally {
			try {
				if (preparedStatement != null)
				{	preparedStatement.close(); }
				
				if(statement != null)
				{ statement.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		if(productList.isEmpty())
		{
			return null;
		}
		
		return productList;
	}

	public static ArrayList<Product> findByName(String name) throws SQLException {
		Connection connection=null;
		Statement statement1 = null;
		PreparedStatement statement=null;
		ArrayList<Product> productList = new ArrayList<Product>();
		Product product = new Product();
		
		int iva = 0;
		String ivaSearch = "select * from software4share.iva";
		String research="SELECT * FROM software4share.Product WHERE Pname LIKE ?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			statement1 = connection.createStatement();
			ResultSet rSet1 = statement1.executeQuery(ivaSearch);
			while(rSet1.next())
			{
				iva = rSet1.getInt("IvaValue");
			}
			
			statement = connection.prepareStatement(research);
			statement.setString(1, "%" + name + "%");
			ResultSet rSet=statement.executeQuery();
			int codex = 0;
			
			while(rSet.next())
			{
				codex = rSet.getInt("idProduct");
				product.setIdProduct(codex);
				product.setName(rSet.getString("Pname"));
				product.setCategory(rSet.getString("Category"));
				product.setSeller(UserDAO.FindUser(rSet.getInt("Seller")));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setFilePath(rSet.getString("ProductPath"));
				if(ProductDAO.isDiscount(codex))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setImages(ProductDAO.allProductImages(codex));
				product.setIVA(iva);
				productList.add(product);
				product = new Product();
			}
		} finally {
			try {
				if (statement != null)
				{	statement.close(); }
				
				if(statement1 != null)
				{ statement1.close(); }
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		if(productList.isEmpty())
		{
			return null;
		}
		
		return productList;
	}

	public static ArrayList<Product> findBySeller(User seller) throws SQLException {
		Connection connection=null;
		Statement statement = null;
		PreparedStatement preparedStatement=null;
		ArrayList<Product> productList = new ArrayList<Product>();
		Product product = new Product();
		int idUser = seller.getUserNumber();
		int iva = 0;
		String ivaSearch = "select * from software4share.iva";
		String reserach="select * from software4share.Product where Seller=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			statement = connection.createStatement();
			ResultSet rSet1 = statement.executeQuery(ivaSearch);
			while(rSet1.next())
			{
				iva = rSet1.getInt("IvaValue");
			}
			
			preparedStatement=connection.prepareStatement(reserach);
			preparedStatement.setInt(1, idUser);
			
			ResultSet rSet=preparedStatement.executeQuery();
			int codex = 0;
			while(rSet.next())
			{
				codex = rSet.getInt("idProduct");
				product.setIdProduct(codex);
				product.setName(rSet.getString("Pname"));
				product.setCategory(rSet.getString("Category"));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setSeller(seller);
				product.setFilePath(rSet.getString("ProductPath"));
				product.setIVA(iva);
				if(ProductDAO.isDiscount(codex))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setImages(ProductDAO.allProductImages(codex));
				productList.add(product);
				product = new Product();
			}
		} finally {
			try {
				if (preparedStatement != null)
				{	preparedStatement.close(); }
				
				if(statement != null)
				{
					statement.close();
				}
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		if(productList.isEmpty())
		{
			return null;
		}
		
		return productList;
	}

	public static ArrayList<Product> findByPrice(double price) throws SQLException {
		Connection connection=null;
		Statement statement = null;
		PreparedStatement preparedStatement=null;
		ArrayList<Product> productList = new ArrayList<Product>();
		Product product = new Product();
		
		int iva = 0;
		String ivaSearch = "select * from software4share.iva";
		String reserach="select * from software4share.Product where Price=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			
			statement = connection.createStatement();
			ResultSet rSet1 = statement.executeQuery(ivaSearch);
			while(rSet1.next())
			{
				iva = rSet1.getInt("IvaValue");
			}
			
			preparedStatement=connection.prepareStatement(reserach);
			preparedStatement.setDouble(1, price);
			
			ResultSet rSet=preparedStatement.executeQuery();
			int codex = 0;
			while(rSet.next())
			{
				codex = rSet.getInt("idProduct");
				product.setIdProduct(codex);
				product.setName(rSet.getString("Pname"));
				product.setCategory(rSet.getString("Category"));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setSeller(UserDAO.FindUser(rSet.getInt("Seller")));
				product.setFilePath(rSet.getString("ProductPath"));
				if(ProductDAO.isDiscount(codex))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setImages(ProductDAO.allProductImages(codex));
				product.setIVA(iva);
				productList.add(product);
				product = new Product();
			}
		} finally {
			try {
				if (preparedStatement != null)
				{	preparedStatement.close(); }
				
				if(statement != null)
				{
					statement.close();
				}
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		if(productList.isEmpty())
		{
			return null;
		}
		
		return productList;
	}

	public static boolean isDiscount(int codex) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		boolean valid=false;
		String serach="select * from software4share.Product where idProduct=? and Sale != 0";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setInt(1, codex);
			
			ResultSet rSet = preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				valid = true;
				return valid;
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return valid;
	}
	public static  int countBySeller(String seller) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		int result = 0;
		String reserach="select count(idProduct) from software4share.Product where Seller = (select idUser from software4share.User where UserName =?)";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(reserach);
			preparedStatement.setString(1, seller);
			
			ResultSet rSet=preparedStatement.executeQuery();
	
			while(rSet.next())
			{
				result = rSet.getInt("count(idProduct)");
			}
		} finally {
			try {
				if (preparedStatement != null)
				{	preparedStatement.close(); }
				
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return result;
	}
	public static ArrayList<Product> lastSixProducts() throws SQLException {
		
		Connection connection=null;
		Statement statement1 = null;
		int iva = 0;
		Statement statement=null;
		Product product = new Product();
		ArrayList<Product> productList = new ArrayList<Product>();
		
		int codex = 0;
		String ivaSearch = "select * from software4share.iva";
		String search="SELECT * FROM software4share.product order by idProduct desc LIMIT 6";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			statement1 = connection.createStatement();
			ResultSet rSet1 = statement1.executeQuery(ivaSearch);
			while(rSet1.next())
			{
				iva = rSet1.getInt("IvaValue");
			}
			
			statement = connection.createStatement();
			
			ResultSet rSet=statement.executeQuery(search);
			while(rSet.next())
			{
				codex = rSet.getInt("idProduct");
				product.setIdProduct(codex);
				product.setName(rSet.getString("Pname"));
				product.setCategory(rSet.getString("Category"));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setSeller(UserDAO.FindUser(rSet.getInt("Seller")));
				product.setFilePath(rSet.getString("ProductPath"));
				if(ProductDAO.isDiscount(codex))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setIVA(iva);
				product.setImages(ProductDAO.allProductImages(codex));
				productList.add(product);	
				product = new Product();
			}
		}
		finally 
		{
			try {
				if (statement != null)
				{	statement.close();  }
				
				if(statement1 != null)
				{
					statement1.close();
				}
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		if(productList.isEmpty())
		{
			return null;
		}
		
		return productList;
			
	}
	
	public static ArrayList<String> getAllCategories() throws SQLException
	{
		Connection connection=null;
		Statement stat = null;
		String research = "select * from software4share.CategoryTable;";
		ArrayList<String> allCategories = new ArrayList<String>();
		try
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			stat = connection.createStatement();
			
			ResultSet rSet= stat.executeQuery(research);
			while(rSet.next())
			{
				allCategories.add(rSet.getString("CategoryName"));
			}
			return allCategories;
		} 
		finally 
		{
			try 
			{
				if (stat != null)
					stat.close();
			} 
			finally 
			{
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public static ArrayList<Product> lastThreeProducts(String category) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Statement statement = null;
		ArrayList<Product> productList = new ArrayList<Product>();
		Product product = new Product();
		int codex = 0;
		int iva = 0;
		String ivaSearch = "select * from software4share.iva";
		String search="SELECT * FROM software4share.product where Category = ? order by idProduct desc LIMIT 3";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			statement = connection.createStatement();
			ResultSet rSet1 = statement.executeQuery(ivaSearch);
			while(rSet1.next())
			{
				iva = rSet1.getInt("IvaValue");
			}
			preparedStatement = connection.prepareStatement(search);
			preparedStatement.setString(1, category);
			
			ResultSet rSet= preparedStatement.executeQuery();
			while(rSet.next())
			{
				codex = rSet.getInt("idProduct");
				product.setIdProduct(codex);
				product.setName(rSet.getString("Pname"));
			
				product.setCategory(rSet.getString("Category"));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setSeller(UserDAO.FindUser(rSet.getInt("Seller")));
				product.setFilePath(rSet.getString("ProductPath"));
				if(ProductDAO.isDiscount(codex))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setImages(ProductDAO.allProductImages(codex));
				product.setIVA(iva);
				productList.add(product);	
				product = new Product();
			}
		}
		finally 
		{
			try {
				if (preparedStatement != null)
				{	preparedStatement.close(); }
				
				if(statement != null)
				{
					statement.close();
				}
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		
		if(productList.isEmpty())
		{
			return null;
		}
		
		return productList;
	}
	
	public static Product findByImage(String img) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Product product = new Product();
		String reserach="select Product from software4share.Image where aImage=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(reserach);
			preparedStatement.setString(1, img);
			
			ResultSet rSet=preparedStatement.executeQuery();
			int codex = 0;
			while(rSet.next())
			{
				codex = rSet.getInt("Product");
			}
			
			product = ProductDAO.findByID(codex);
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return product;
	}
	
	public static Product findByID(int cod) throws SQLException {
		Connection connection=null;
		Statement statement1 = null;
		PreparedStatement statement=null;
		Product product = new Product();
	
		int iva = 0;
		String ivaSearch = "select * from software4share.iva";
		String research="SELECT * FROM software4share.Product WHERE idProduct = ?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			statement1 = connection.createStatement();
			ResultSet rSet1 = statement1.executeQuery(ivaSearch);
			while(rSet1.next())
			{
				iva = rSet1.getInt("IvaValue");
			}
			statement = connection.prepareStatement(research);
			statement.setInt(1, cod);
			ResultSet rSet=statement.executeQuery();
			
			while(rSet.next())
			{
				product.setIdProduct(rSet.getInt("idProduct"));
				product.setName(rSet.getString("Pname"));
				product.setCategory(rSet.getString("Category"));
				product.setSeller(UserDAO.FindUser(rSet.getInt("Seller")));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setFilePath(rSet.getString("ProductPath"));
				if(ProductDAO.isDiscount(cod))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setImages(ProductDAO.allProductImages(cod));
				product.setIVA(iva);
			}
		} finally {
			try {
				if (statement != null)
				{	statement.close(); }
				
				if(statement1 != null)
				{
					statement1.close();
				}
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return product;
	}
	
	public static ArrayList<Product> getAllProductsInOrder() throws SQLException {
		Connection connection=null;
		Statement statement=null;
		ArrayList<Product> productList = new ArrayList<Product>();
		Product product = new Product();
	
		String research="SELECT * FROM software4share.Product ORDER BY Pname";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			statement = connection.createStatement();
			ResultSet rSet=statement.executeQuery(research);
			int codex = 0;
			
			while(rSet.next())
			{
				codex = rSet.getInt("idProduct");
				product.setIdProduct(rSet.getInt("idProduct"));
				product.setName(rSet.getString("Pname"));
				product.setCategory(rSet.getString("Category"));
				product.setSeller(UserDAO.FindUser(rSet.getInt("Seller")));
				product.setPrice(rSet.getDouble("Price"));
				product.setDescription(rSet.getString("Description"));
				product.setFilePath(rSet.getString("ProductPath"));
				if(ProductDAO.isDiscount(codex))
				{
					product.setSale(rSet.getInt("Sale"));
				}
				product.setImages(ProductDAO.allProductImages(codex));
				productList.add(product);
				product = new Product();
			}
		} finally {
			try {
				if (statement != null)
					statement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		if(productList.isEmpty())
		{
			return null;
		}
		
		return productList;
	}
	
	public static boolean checkLastOrder(String name, String surname, String mail) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Calendar calendar = Calendar.getInstance();
		String reserach="SELECT max(Date) FROM software4share.invoice where (BuyerName = ? and BuyerSurname = ? and BuyerMail = ?) or "
				+ "(SellerName = ? and SellerSurname = ? and SellerMail = ?)";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(reserach);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, surname);
			preparedStatement.setString(3, mail);
			preparedStatement.setString(4, name);
			preparedStatement.setString(5, surname);
			preparedStatement.setString(6, mail);
			ResultSet rSet=preparedStatement.executeQuery();
			while(rSet.next())
			{
				
				if(rSet.getString("max(Date)")==null)
				{
					return true;
				}
				int month = Integer.parseInt(rSet.getString("max(Date)").substring(5, 7));
				int day = Integer.parseInt(rSet.getString("max(Date)").substring(8, 10));
				int currentMonth = calendar.get(Calendar.MONTH) + 1;
				int currentDay= calendar.get(Calendar.DAY_OF_MONTH);
				
				int dayPlusTen = day + 10;
				int updatedMonth = 0;
				if(dayPlusTen > 30 && (month == 11 || month == 4 || month == 6 || month == 9))
				{
					dayPlusTen = 10 - (30 - day);
					updatedMonth = month + 1;
				}
				else if(dayPlusTen > 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12))
				{
					dayPlusTen = 10 - (31 - day);
					updatedMonth = month + 1;
				}
				else if(dayPlusTen > 28 && (month == 2))
				{
					dayPlusTen = 10 - (28 - day);
					updatedMonth = month + 1;
				}
				
				
				if(updatedMonth == 0)
				{
					updatedMonth = month;
				}
				if(updatedMonth < currentMonth)
				{
					return true;
				}
				else if(dayPlusTen > currentDay)
				{
					return false;
				}
				else
				{
					return true;
				}
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return false;
	}
	
	public static void removeProduct(int idProduct) throws SQLException
	{
		Connection connection=null;
		PreparedStatement statement=null;
		String research="delete FROM software4share.Product where idProduct = ?";
		Product p = new Product();
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			p = ProductDAO.findByID(idProduct);
			File f = new File(p.getFilePath());
			f.delete();
			
			ArrayList<String> img = p.getImages();
			
			for(String s : img)
			{
				File f1 = new File(s);
				f1.delete();
			}
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(research);
			statement.setInt(1, idProduct);
			statement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} finally {
				connection.setAutoCommit(true);
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public static void changeSale(int idProduct, int newSale) throws SQLException
	{
		Connection connection=null;
		PreparedStatement statement=null;
		String research="update software4share.Product set Sale = ? where idProduct = ?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(research);
			statement.setFloat(1, newSale);
			statement.setFloat(2, idProduct);
			statement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} finally {
				connection.setAutoCommit(true);
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public static void changePrice(int idProduct, float price) throws SQLException
	{
		Connection connection=null;
		PreparedStatement statement=null;
		String research="update software4share.Product set Price = ? where idProduct = ?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(research);
			statement.setFloat(1, price);
			statement.setFloat(2, idProduct);
			statement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} finally {
				connection.setAutoCommit(true);
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public static void removeAllFilesPerSeller(User seller) throws SQLException, IOException, NoSuchFileException, DirectoryNotEmptyException
	{
		ArrayList<Product> array = new ArrayList<Product>();
		
		try 
		{
			array = ProductDAO.findBySeller(seller);
			for(Product p : array)
			{
				File f = new File(p.getFilePath());
				f.delete();
				
				ArrayList<String> images = p.getImages();
				for(String img : images)
				{
					File f1 = new File(img);
					f1.delete();
				}
			}
		}
		finally
		{
			//boh
		}
	}
	
	public static void UploadFileInDB(Product product) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String insert="INSERT INTO software4share.product(Pname,Category,Price,Sale,Seller,Description,ProductPath) VALUES(?,?,?,?,?,?,?)";
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(insert ,Statement.RETURN_GENERATED_KEYS);//vedi come si trova con il nuovo codice
			preparedStatement.setString(1,product.getName());
			preparedStatement.setString(2,product.getCategory());
			preparedStatement.setDouble(3,product.getPrice());
			preparedStatement.setInt(4,product.getSale());
			preparedStatement.setInt(5,product.getSeller().getUserNumber());
			preparedStatement.setString(6,product.getDescription());
			preparedStatement.setString(7,product.getFilePath());
			
			preparedStatement.executeUpdate();
			
			int id = 0;
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if(rs.next())
			{
				id = rs.getInt(1);
			}

			for(int i=0;i<product.getImages().size();i++)
			{
				insert="INSERT INTO software4share.image(aImage,Product) VALUES(?,?)";
				preparedStatement=connection.prepareStatement(insert);
				preparedStatement.setString(1,product.getImages().get(i));
				preparedStatement.setInt(2,id);
				preparedStatement.executeUpdate();
				
			}
			
			
		} finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				connection.setAutoCommit(true);
			}
		}
	}

	public static ArrayList<String> categories() throws SQLException
	{
		ArrayList<String> listCategories=new ArrayList<String>();
		Connection connection=null;
		
		PreparedStatement preparedStatement=null;
		
		String serach="select * from software4share.categorytable";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				listCategories.add(rSet.getString("categoryName"));
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return listCategories;
	}
	
	
	public static ArrayList<Review> getReviews(String productName) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		ArrayList<Review> reviews= new ArrayList<Review>();
		String serach="select * from software4share.review where Product in (select idProduct from software4share.product where Pname=?)";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setString(1, productName);
			ResultSet rSet=preparedStatement.executeQuery();
			while(rSet.next())
			{
				Review review=new Review();
				review.setIdReview(rSet.getInt("idReview"));
				review.setUser(UserDAO.FindUser(rSet.getInt("User")));
				review.setIdProduct(rSet.getInt("Product"));
				review.setRating(rSet.getInt("Rating"));
				review.setComment(rSet.getString("Comment"));
	
				reviews.add(review);
			}
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
			}
		}
	
		return reviews;
	}
	
	public static int getAvgReviews(int productId) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		int avg = 0;
		String query="select avg(Rating) as 'avgRating'  from software4share.review where Product=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			ResultSet rSet=preparedStatement.executeQuery();
			if(rSet.next())
			{
				avg=rSet.getInt("avgRating");
			}
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
			}
		}
		return avg;
	}
	public static void addStarReview(int star,int userId,int productId) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String insert="INSERT INTO software4share.review (User, Product, rating) VALUES(?,?,?) ON DUPLICATE KEY UPDATE rating =?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(insert);
			preparedStatement.setInt(1,userId);
			preparedStatement.setInt(2,productId);
			preparedStatement.setInt(3,star);
			preparedStatement.setInt(4,star);
			
			preparedStatement.executeUpdate();
			
		} finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				connection.setAutoCommit(true);
			}
		}
	}

	public static void addCommentReview(String comment,int userId,int productId) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String insert="INSERT INTO software4share.review (User, Product,Comment) VALUES(?,?,?) ON DUPLICATE KEY UPDATE Comment =?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(insert);
			preparedStatement.setInt(1,userId);
			preparedStatement.setInt(2,productId);
			preparedStatement.setString(3,comment);
			preparedStatement.setString(4,comment);
			
			preparedStatement.executeUpdate();
			
		} finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				connection.setAutoCommit(true);
			}
		}
	}

	public static void deleteReview(int id) throws SQLException 
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String query="delete from software4share.review where idReview=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1,id);

			preparedStatement.executeUpdate();
			
		} finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				connection.setAutoCommit(true);
			}
		}
	}

	public static boolean addProductToWishlist(int idProduct, int idUser) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String query="insert into software4share.wishlist (User,Product) values(?,?)";
		boolean alreadyExist=false;
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1,idUser);
			preparedStatement.setInt(2,idProduct);
			
			preparedStatement.executeUpdate();
			
		}catch (SQLException e) 
		{
		
			if(e.getMessage().contains("Duplicate"))
			{
				alreadyExist=true;
			}
			else
			{
				e.printStackTrace();
			}
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				connection.setAutoCommit(true);
			}
		}
		return alreadyExist;
	}
	
	public static ArrayList<Integer> createInvoice(User user,Cart cart ,String payment) throws SQLException
	{
		Connection connection=null;
		ArrayList<Integer> idInvoices= new ArrayList<Integer>(); 
		PreparedStatement preparedStatement=null;
		 SimpleDateFormat sdf = new SimpleDateFormat();
		 sdf.applyPattern("yyyy-MM-dd");
		 String dataStr = sdf.format(new Date());       
		
		ArrayList<Integer> idUser= new ArrayList<Integer>(); 
		for(int i=0;i<cart.getProductList().size();i++)
		{
			idUser.add(cart.getProductList().get(i).getProduct().getSeller().getUserNumber());
		}
		ArrayList<Integer> idUser2= new ArrayList<Integer>();
		
	
		
			for (Integer integer : idUser) {
				if(idUser2.size()==0)
				{
					idUser2.add(integer);
				}
				else if(!idUser2.contains(integer))
				{
					idUser2.add(integer);
				}
				
			}
			
		ArrayList<Double> totals = new ArrayList<Double>();
		
		
			for (Integer integer : idUser2) 
			{
				double tot  = 0;
				for (int i=0;i<cart.getProductList().size();i++)
				{
					if(cart.getProductList().get(i).getProduct().getSeller().getUserNumber()==integer)
					{
						tot+=cart.getProductList().get(i).getTotal();
					}
				}
				totals.add(tot);
			}
			
		ArrayList<User> sellers= new ArrayList<User>();
		for (Integer integer : idUser2)
		{
			sellers.add(UserDAO.FindUser(integer));
		}
			int i=0;
		   for (User user2 : sellers) {
			
			String query="insert into software4share.invoice(BuyerName,BuyerSurname,BuyerMail,SellerName,SellerSurname,SellerMail,PayMethod,Date,TotalPrice) values(?,?,?,?,?,?,?,?,?)";
			try 
			{
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getSurname());
				preparedStatement.setString(3, user.getEmail());
				preparedStatement.setString(4, user2.getName());
				preparedStatement.setString(5, user2.getSurname());
				preparedStatement.setString(6, user2.getEmail());
				preparedStatement.setString(7, payment);
				preparedStatement.setString(8, dataStr);
				preparedStatement.setDouble(9, totals.get(i));

				preparedStatement.executeUpdate();
				
				i++;
				ResultSet rs = preparedStatement.getGeneratedKeys();
				while(rs.next())
				{
					int id = rs.getInt(1);
					idInvoices.add(id);
					
					for(int k=0;k<cart.getProductList().size();k++)
					{
						if(cart.getProductList().get(k).getProduct().getSeller().getEmail().equals(user2.getEmail()))
						{
							query = "insert into software4share.productinivoice(Invoice,Product,FinalSale,FinalPrice,FinalIva,Quantity)values(?,?,?,?,?,?)";
							preparedStatement=connection.prepareStatement(query);
							
							preparedStatement.setInt(1, id);
							preparedStatement.setString(2, cart.getProductList().get(k).getProduct().getName());
							preparedStatement.setInt(3, cart.getProductList().get(k).getProduct().getSale());
							preparedStatement.setDouble(4, cart.getProductList().get(k).getProduct().getPrice());
							preparedStatement.setDouble(5, cart.getProductList().get(k).getProduct().getIVA());
							preparedStatement.setInt(6, cart.getProductList().get(k).getQuantity());
							preparedStatement.executeUpdate();
						}
					}
					
					
				}
			
			}
			finally 
			{
				try 
				{
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
					connection.setAutoCommit(true);
				}
			}
		}

		 		
		return idInvoices;
	}
	
	public static ArrayList<String> ProductAlreadyBuyed(String userMail) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ArrayList<String> products= new ArrayList<String>();
		
		String query="select Product from software4share.productinivoice where Invoice in(select idInvoice from software4share.invoice where BuyerMail=?);";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1,userMail);
			ResultSet rSet=preparedStatement.executeQuery();
			while(rSet.next())
			{
				products.add(rSet.getString("Product"));
			}
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
			}
		}
		return products;
	}
	
	public static ArrayList<Product> getWishlistProduct(int idUser) throws SQLException
	{
		
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ArrayList<Product> products= new ArrayList<Product>();
		
		String query="select Product from software4share.wishlist where User=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1,idUser);
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				products.add(ProductDAO.findByID(rSet.getInt("Product")));
			}
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
			}
		}
		return products;
		
	}
	
	public static void removeFromWishlist(int idUser,int idProduct) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String query="delete from software4share.wishlist where User=? and Product=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1,idUser);
			preparedStatement.setInt(2,idProduct);
			
			preparedStatement.executeUpdate();
			
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				connection.setAutoCommit(true);
			}
		}
	}
}

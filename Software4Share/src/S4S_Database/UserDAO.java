package S4S_Database;


import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import S4S_Model.ControlAjaxLogAndRegBean;
import S4S_Model.User;

public  class UserDAO 
{

	public static User FindUser(String name) throws SQLException 
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		User user=new User();
		String serach="select * from software4share.user where UserName=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setString(1,name);
			
			
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				user.setUserNumber(rSet.getInt("idUser"));
				user.setUserName(rSet.getString("UserName"));
				user.setBirthDay(rSet.getString("Birth"));
				user.setEmail(rSet.getString("Email"));
				user.setName(rSet.getString("Name"));
				user.setSurname(rSet.getString("Surname"));
				user.setPassword(rSet.getString("Pass"));
				user.setpIVA(rSet.getString("pIva"));
				user.setAdministrator(false);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
			}
		}
		return user;
	}

	public static ArrayList<User> FindAllUser() throws SQLException 
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ArrayList<User> users = new ArrayList<User>();
		User user=new User();
		String serach="SELECT * FROM software4share.user";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			
			
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				user.setUserNumber(rSet.getInt("idUser"));
				user.setUserName(rSet.getString("UserName"));
				user.setBirthDay(rSet.getString("Birth"));
				user.setEmail(rSet.getString("Email"));
				user.setName(rSet.getString("Name"));
				user.setSurname(rSet.getString("Surname"));
				user.setPassword(rSet.getString("Pass"));
				user.setpIVA(rSet.getString("pIva"));
				user.setAdministrator(false);
				users.add(user);
				user=new User();
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
			}
		}
		return users;
	}


	public static boolean isUser(String name) throws SQLException 
	{

		Connection connection=null;
		PreparedStatement preparedStatement=null;
		boolean valid=false;
		String serach="select * from software4share.user where UserName=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setString(1,name);
			
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				valid=true;
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



	public static User FindAdministrator(String name) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		User user=null;
		String serach="select * from admin where aUserName=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setString(1,name);
			
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				user=new User();
				user.setUserNumber(rSet.getInt("idAdmin"));
				user.setUserName(rSet.getString("aUserName"));
				user.setBirthDay(rSet.getString("aBirth"));
				user.setEmail(rSet.getString("aEmail"));
				user.setName(rSet.getString("aName"));
				user.setSurname(rSet.getString("aSurname"));
				user.setPassword(rSet.getString("aPass"));
				user.setAdministrator(true);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return user;
	}

	public static void InsertUser(User user) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String insert="INSERT INTO software4share.user(UserName,Name,Surname,Birth,Email,Pass,Piva) VALUES(?,?,?,?,?,md5(?),?)";//togli md5 in caso di eccezione 
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(insert,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1,user.getUserName());
			preparedStatement.setString(2,user.getName());
			preparedStatement.setString(3,user.getSurname());
			preparedStatement.setString(4,user.getBirthDay());
			preparedStatement.setString(5,user.getEmail());
			preparedStatement.setString(6,user.getPassword());
			preparedStatement.setString(7,user.getpIVA());;
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if(rs.next())
			{
				user.setUserNumber( rs.getInt(1));
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


	public static boolean checkUser(String username, String pass,boolean admin) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		boolean check=false;
		String verify="";
		String update="";
		if(admin)
		{	
			try
			{
				verify+="select * from software4share.admin where aUsername=? and aPass=md5(?)";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(verify);
				preparedStatement.setString(1,username);
				preparedStatement.setString(2,pass);
				
				ResultSet rSet=preparedStatement.executeQuery();
				if(!rSet.next())
				{
					update+="update software4share.admin set aTry=aTry+1 where aUsername=?";
					preparedStatement.close();
					preparedStatement=connection.prepareStatement(update);
					preparedStatement.setString(1,username);
					
					preparedStatement.executeUpdate();
				}
				else
				{
					check=true;
					update+="update software4share.admin set aTry=0 where aUsername=?";
					preparedStatement.close();
					preparedStatement=connection.prepareStatement(update);
					preparedStatement.setString(1,username);
					
					preparedStatement.executeUpdate();
				}
			} finally
			{
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
					connection.setAutoCommit(true);
				}
			}
			
			
		}
		else
		{
			try 
			{
				
				verify+="select * from software4share.user where UserName=? and Pass=md5(?)";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(verify);
				preparedStatement.setString(1,username);
				preparedStatement.setString(2,pass);
				
				ResultSet rSet=preparedStatement.executeQuery();
				if(!rSet.next())
				{
					
					update+="update software4share.user set Try=Try+1 where UserName=?";
					preparedStatement=connection.prepareStatement(update);
					preparedStatement.setString(1,username);
					
					preparedStatement.executeUpdate();
				}
				else
				{
					check=true;
					update+="update software4share.user set Try=0 where UserName=?";
					preparedStatement.close();
					preparedStatement=connection.prepareStatement(update);
					preparedStatement.setString(1,username);
					
					preparedStatement.executeUpdate();
				}
			
			} 
			finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
					connection.setAutoCommit(true);
				}
			}
		}
		
		return check;
		
	}



	public static boolean isAdmin(String name) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		boolean valid=false;
		String serach="select * from software4share.admin where aUsername=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setString(1,name);
			
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				valid=true;
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
	
	
	
	public static boolean existEmail(String email,ControlAjaxLogAndRegBean admin) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		boolean existUser=false;
		boolean existAdmin=false;
		String serach="select Email from software4share.user where Email=?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setString(1,email);
			
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				existUser=true;
			}
			
			
	
		} finally {
			try {
				if (preparedStatement != null)
				{
					preparedStatement.close();
					serach="select aEmail from software4share.admin where aEmail=?";
					connection=S4S_Database.DriverManagerConnectionPool.getConnection();
					preparedStatement=connection.prepareStatement(serach);
					preparedStatement.setString(1,email);
					ResultSet rSet=preparedStatement.executeQuery();
					while(rSet.next())
					{
						existAdmin=true;
					}
				}
					
			} finally {
				try
				{
					if (preparedStatement != null)
						preparedStatement.close();
				} finally
				{
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				}
				
			}
		}
		if(existAdmin)
		{
			admin.setCheckAdmin(existAdmin);
		}
		return (existUser||existAdmin);
	}



	public static int userTry(String username,boolean admin) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String queryString="";
		int nTry=0;
		if(admin)
		{	
			try
			{
			
				queryString+="select aTry from software4share.admin where aUsername=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(queryString);
				preparedStatement.setString(1,username);
				
				ResultSet rSet=preparedStatement.executeQuery();
				if(rSet.next())
				{
					nTry=rSet.getInt("aTry");
				}
			
			} finally
			{
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
				}
			}
			
			
		}
		else
		{
			try 
			{
				queryString+="select Try from software4share.user where UserName=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(queryString);
				preparedStatement.setString(1,username);
				ResultSet rSet=preparedStatement.executeQuery();
				if(rSet.next())
				{
					nTry=rSet.getInt("Try");
				}
				
			} 
			finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
				}
			}
		}
		
		return nTry;
		

	}
	public static void setBlockCode(String mail,String blockCode,boolean admin) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String insert=""; 
		if(admin)
		{
			insert="update  software4share.admin set aUnlock_code=? where aEmail=? ";
			try 
			{
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(insert);
				preparedStatement.setString(1,blockCode);
				preparedStatement.setString(2,mail);
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
		else {
			insert="update  software4share.user set unlock_code=? where Email=? ";
			try 
			{
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(insert);
				preparedStatement.setString(1,blockCode);
				preparedStatement.setString(2,mail);
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
		
	}

	public static String getMail(String username,boolean  admin) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String queryString="";
		String mail="";
		if(admin)
		{	
			try
			{
				queryString+="select aEmail from software4share.admin where aUsername=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(queryString);
				preparedStatement.setString(1,username);
				
				ResultSet rSet=preparedStatement.executeQuery();
				if(rSet.next())
				{
					mail=rSet.getString("aEmail");
				}
			
			} finally
			{
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				}
			}
		}
		else 
		{
			try 
			{
				queryString+="select Email from software4share.user where UserName=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(queryString);
				preparedStatement.setString(1,username);
				ResultSet rSet=preparedStatement.executeQuery();
				if(rSet.next())
				{
					mail=rSet.getString("Email");
				}
				
			} 
			finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
					
				}
			}
		}
		
		return mail;
	}
	
	public static boolean verifyCode(String code,String username,boolean  admin) throws SQLException 
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		boolean unlock=false;
		String query="";
		
		if(admin)
		{
			try
			{
				query="update software4share.admin set aTry=0 where aUsername=? and aUnlock_code=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setString(1,username);
				preparedStatement.setString(2,code);
				int ris=preparedStatement.executeUpdate();
				if(ris==1)
				{
					unlock=true;
				}
			}finally 
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
		else
		{
			try
			{
				query="update software4share.user set Try=0 where UserName=? and unlock_code=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setString(1,username);
				preparedStatement.setString(2,code);
				int ris=preparedStatement.executeUpdate();
				if(ris==1)
				{
					unlock=true;
				}
				
			}finally 
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
		
		
		return unlock;
		
		
	}
	
	
	public static boolean verifyCodeByMail(String code,String mail,boolean  admin) throws SQLException 
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		boolean unlock=false;
		String query="";
		
		if(admin)
		{
			try
			{
				query="update software4share.admin set aTry=0 where aEmail=? and aUnlock_code=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setString(1,mail);
				preparedStatement.setString(2,code);
				int ris=preparedStatement.executeUpdate();
				if(ris==1)
				{
					unlock=true;
				}
			}finally 
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
		else
		{
			try
			{
				query="update software4share.user set Try=0 where Email=? and unlock_code=?";
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setString(1,mail);
				preparedStatement.setString(2,code);
				int ris=preparedStatement.executeUpdate();
				if(ris==1)
				{
					unlock=true;
				}
				
			}finally 
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
		
		
		return unlock;
		
		
	}
	
	public static void changePass(String mail,String pass,boolean admin) throws SQLException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String insert=""; 
		if(admin)
		{
			insert="update  software4share.admin set aPass=md5(?) where aEmail=? ";
			try 
			{
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(insert);
				preparedStatement.setString(1,pass);
				preparedStatement.setString(2,mail);
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
		else {
			insert="update  software4share.user set Pass=md5(?) where Email=? ";
			try 
			{
				connection=S4S_Database.DriverManagerConnectionPool.getConnection();
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(insert);
				preparedStatement.setString(1,pass);
				preparedStatement.setString(2,mail);
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

	}
	
	public static User FindUser(int id) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		User user=new User();
		String serach="select * from software4share.User where idUser = ?";
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(serach);
			preparedStatement.setInt(1, id);
			
			
			ResultSet rSet=preparedStatement.executeQuery();
			
			while(rSet.next())
			{
				user.setUserNumber(rSet.getInt("idUser"));// in questo modo e simile alla ricerca con il nome
				user.setUserName(rSet.getString("UserName"));
				user.setBirthDay(rSet.getString("Birth"));
				user.setEmail(rSet.getString("Email"));
				user.setName(rSet.getString("Name"));
				user.setSurname(rSet.getString("Surname"));
				user.setPassword(rSet.getString("Pass"));
				user.setpIVA(rSet.getString("pIva"));
				user.setAdministrator(false);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
				
			}
		}
		return user;
	}
	
	public static void removeUser(int idUser, User user) throws SQLException, IOException, NoSuchFileException, DirectoryNotEmptyException
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		String search="delete from software4share.User where idUser = ?";
		
		if(!user.getpIVA().contentEquals(""))
		{
			try
			{
				ProductDAO.removeAllFilesPerSeller(user);
			}
			finally
			{
				//boh
			}
		}
		
		try 
		{
			connection=S4S_Database.DriverManagerConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(search);
			preparedStatement.setInt(1, idUser);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				connection.setAutoCommit(true);
				S4S_Database.DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
}





	

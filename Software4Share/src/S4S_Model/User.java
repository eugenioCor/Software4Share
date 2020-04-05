package S4S_Model;


public class User 
{
	//constructor
	public User() 
	{
	}
	
	public User(String name,String surname,String email, String userName, String password ,String birthDay, String pIVA,boolean administrator) 
	{	
		this.email=email;
		this.name=name;
		this.password=password;
		this.pIVA=pIVA;
		this.surname=surname;
		this.userName=userName;
		this.birthDay=birthDay;
		this.administrator=administrator;
	}
	
	//methods
	public String getBirthDay() 
	{
		return birthDay;
	}
	public String getEmail()
	{
		return email;
	}
	public String getName() 
	{
		return name;
	}
	public String getPassword() 
	{
		return password;
	}
	public String getpIVA() 
	{
		return pIVA;
	}
	public String getSurname() 
	{
		return surname;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public int getUserNumber()
	{
		return userNumber;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setpIVA(String pIVA) 
	{
		this.pIVA = pIVA;
	}
	
	public void setSurname(String surname) 
	{
		
		this.surname = surname;
	}
	
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	
	public boolean isAdministrator() {
		return administrator;
	}
	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}
	
	public void setUserNumber(int userNumber)
	{
		this.userNumber = userNumber;
	}
	
	@Override
	public String toString()
	{
		return "name:"+name+" surname:"+ surname+" userName:"+userName+"password:"+password+"pIVA:"+pIVA+"birthDay:"+birthDay+"email:"+email+"userNumber:"+userName+"administrator:"+administrator;
	}

	//var
	private String name;
	private String surname;
	private String userName;
	private String password;
	private String pIVA;
	private String birthDay;
	private String email;
	private int userNumber;
	private boolean administrator;
	
}

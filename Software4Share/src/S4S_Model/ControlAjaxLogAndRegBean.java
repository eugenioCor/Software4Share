package S4S_Model;

public class ControlAjaxLogAndRegBean
{
	//costruttore
	public ControlAjaxLogAndRegBean()
	{
	}
	//metodi
	public boolean isCheckEmail()
	{
		return checkEmail;
	}
	public boolean isCheckUsername()
	{
		return checkUsername;
	}
	public void setCheckEmail(boolean checkEmail)
	{
		this.checkEmail = checkEmail;
	}
	public void setCheckUsername(boolean checkUsername)
	{
		this.checkUsername = checkUsername;
	}
	public boolean isCheckpass()
	{
		return checkpass;
	}
	public void setCheckpass(boolean checkpass)
	{
		this.checkpass = checkpass;
	}
	public void setCheckTry(int checkTry)
	{
		
		if(this.checkTry-checkTry<0)
		{
			this.checkTry=0;
		}
		else
		{
			this.checkTry -= checkTry;
		}
		
	}
	public boolean isCheckAdmin()
	{
		return checkAdmin;
	}
	public void setCheckAdmin(boolean checkAdmin)
	{
		this.checkAdmin = checkAdmin;
	}
	public int getCheckTry()
	{
		return checkTry;
	}
	
	public boolean isUnlock()
	{
		return unlock;
	}
	public void setUnlock(boolean unlock)
	{
		this.unlock = unlock;
	}
	//variabili
	private boolean checkEmail;
	private boolean checkUsername;
	private boolean checkpass;
	private boolean unlock;
	private boolean checkAdmin;
	private int checkTry=4;
	
}

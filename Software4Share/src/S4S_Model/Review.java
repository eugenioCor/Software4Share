package S4S_Model;

public class Review
{
	//costruttore
	public Review()
	{
	}
	public Review(User user,int idProduct,int rating,String comment)
	{
		this.user=user;
		this.idProduct=idProduct;
		this.rating=rating;
		this.comment=comment;
	}
	//metodi
	public String getComment()
	{
		return comment;
	}
	public int getIdProduct()
	{
		return idProduct;
	}
	public User getUser()
	{
		return user;
	}
	public int getRating()
	{
		return rating;
	}
	public void setComment(String comment)
	{
		if(comment==null)
		{
			this.comment="";
		}
		else
		{
			this.comment = comment;
		}
	
	}
	public void setIdProduct(int idProduct)
	{
		this.idProduct = idProduct;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public void setRating(int rating)
	{
		this.rating = rating;
	}
	public int getIdReview() {
		return idReview;
	}
	public void setIdReview(int idReview) {
		this.idReview = idReview;
	}
	
	@Override
	public String toString() {
	return "idReview: "+idReview+" user: "+user+" idProduct: "+idProduct+" rating: " +rating+" comment"+comment;
	}
	//variabili
	private int idReview;
	private User user;
	private int idProduct;
	private int rating;
	private String comment;
	
}

package S4S_Model;

public class ProductAndQuantity {
	//metodi
			public Product getProduct() {
				return product;
			}
			public int getQuantity() {
				return quantity;
			}
			
			public void setProduct(Product product) {
				this.product = product;
			}
			
			public void setQuantity(int quantity) {
				this.quantity = quantity;
			}
			public double getTotal() {
				return total;
			}
			public void setTotal(double total) {
				this.total = total;
			}
			@Override
			public String toString() {
				return "prouct: "+product+" quantity: "+quantity+" total: "+total;
			}
			
			//variabili
			private Product product;
			private int quantity=1;
			private double total;
}

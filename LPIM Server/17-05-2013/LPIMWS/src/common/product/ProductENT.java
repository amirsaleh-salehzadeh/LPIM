package common.product;

import java.sql.Blob;




public class ProductENT {
	int productID;
	int categoryID;
	String productName;
	String img;

	public String getImg() {
		return img;
	}

	public void setImg(String string) {
		this.img = string;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}

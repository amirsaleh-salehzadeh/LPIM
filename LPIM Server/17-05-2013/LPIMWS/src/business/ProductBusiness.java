package business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import common.AMSTools;
import common.product.ProductENT;
import common.product.ProductLST;


public class ProductBusiness {


	public ArrayList<ProductENT> getProductList(String query) {
		ArrayList<ProductENT> productENTs = new ArrayList<ProductENT>();
		ProductLST productLST = new ProductLST();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/lpim", "root", "");
			String sql = "Select * from product where productName like ?";
			PreparedStatement ps = con.prepareStatement(sql);
			if (query == null) {
				query ="";
			}
			ps.setString(1, "%"+query+"%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductENT p = new ProductENT();
				p.setCategoryID(rs.getInt("categoryID"));
				p.setProductID(rs.getInt("productID"));
				p.setProductName(rs.getString("productName"));
				p.setImg("image/"+rs.getString("img"));
				productENTs.add(p);
			}
			productLST.setProductENTs(productENTs);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return productENTs;
	}
	public static void main(String[] args) {
		ProductBusiness business = new ProductBusiness();
		AMSTools.printObject(business.getProductList("pl"));
	}
}

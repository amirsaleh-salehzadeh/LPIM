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
import common.order.OrderDetailENT;
import common.order.OrderENT;
import common.product.ProductENT;
import common.product.ProductLST;


public class Business {


	public ArrayList<ProductENT> getProductList(String query) {
		ArrayList<ProductENT> productENTs = new ArrayList<ProductENT>();
		ProductLST productLST = new ProductLST();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/lpim", "root", "");
			String sql = "Select * from product where productName like ?";
			PreparedStatement ps = con.prepareStatement(sql);
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
	
	public OrderENT getOrder(String query) {
		ArrayList<OrderDetailENT> orderDetailENTs = new ArrayList<OrderDetailENT>();
		OrderENT orderENT = new OrderENT();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/lpim", "root", "");
			orderENT.setEnts(orderDetailENTs);
			String sql = "Select * from orders where orderID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				orderENT.setMainOrderId(rs.getString("orderID"));
				orderENT.setOrderDate(rs.getString("date"));
			}
			sql = "Select * from orderdetail where orderID = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, query);
			rs = ps.executeQuery();

			while (rs.next()) {
				OrderDetailENT p = new OrderDetailENT();
				p.setId(rs.getString("ordersDetailID"));
				p.setOrderId(rs.getString("orderID"));
				p.setProductId(rs.getString("productID"));
				sql = "Select * from product where productID = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rs.getString("productID"));
				ResultSet rs2 = ps.executeQuery();
				while (rs2.next()) {
					p.setProductName(rs2.getString("productName"));
					p.setImg("image/"+rs2.getString("img"));
				}
				orderDetailENTs.add(p);
			}
			
			

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return orderENT;
	}
	public String getOrdersString(String query) {
		String res = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/lpim", "root", "");
			String sql = "Select productID from orderdetail where orderID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				res += (rs.getString("productID"))+',';
			}
			res = res.substring(0, res.length()-1);
			System.out.println(res);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return res;
	}
	public static void main(String[] args) {
		Business business = new Business();
		AMSTools.printObject(business.getOrdersString("2"));
//		AMSTools.printObject(business.getProductList(""));
	}
}

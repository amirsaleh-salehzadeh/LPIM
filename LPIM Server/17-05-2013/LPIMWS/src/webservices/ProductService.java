package webservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import business.ProductBusiness;

import common.AMSTools;
import common.product.ProductENT;
import common.product.ProductLST;
import com.google.gson.Gson;

@Path("/WebService")
public class ProductService {

	@GET
	@Path("/GetProducts")
	@Produces("application/json")
	public String feed(@QueryParam("productName") String productName) {
		String json = null;
		try {
			ArrayList<ProductENT> productData = null;
			ProductBusiness productBusiness = new ProductBusiness();
			productData = productBusiness.getProductList(productName);
			Gson gson = new Gson();
			System.out.println(gson.toJson(productData));
			json = gson.toJson(productData);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return json;
	}

}

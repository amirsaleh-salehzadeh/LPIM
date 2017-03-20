package webservices;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import business.Business;

import common.order.OrderENT;
import common.product.ProductENT;
import com.google.gson.Gson;

@Path("/WebService")
public class LPIMService {

	@GET
	@Path("/GetProducts")
	@Produces("application/json")
	public String products(@QueryParam("productName") String productName) {
		String json = null;
		try {
			ArrayList<ProductENT> productData = null;
			Business business = new Business();
			productData = business.getProductList(productName);
			Gson gson = new Gson();
			System.out.println(gson.toJson(productData));
			json = gson.toJson(productData);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return json;
	}
	
	@GET
	@Path("/GetOrder")
	@Produces("application/json")
	public String order(@QueryParam("orderNo") String orderId) {
		String json = null;
		try {
			OrderENT orderData = null;
			Business business = new Business();
			orderData = business.getOrder(orderId);
			Gson gson = new Gson();
			System.out.println(gson.toJson(orderData));
			json = gson.toJson(orderData);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return json;
	}
	
	@GET
	@Path("/GetOrdersString")
	@Produces("application/json")
	public String getOrdersString(@QueryParam("orderNo") String orderId) {
		String json = null;
		try {
			String orderData = "";
			Business business = new Business();
			orderData = business.getOrdersString(orderId);
			Gson gson = new Gson();
			System.out.println(gson.toJson(orderData));
			json = gson.toJson(orderData);
		}

		catch (Exception e) {
			System.out.println("Exception Error"); // Console
		}
		return json;
	}

}

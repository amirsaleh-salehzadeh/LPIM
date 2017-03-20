package common.order;

import java.util.ArrayList;

public class OrderENT {
	String mainOrderId;

	public String getMainOrderId() {
		return mainOrderId;
	}

	public void setMainOrderId(String mainOrderId) {
		this.mainOrderId = mainOrderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	String orderDate;
	ArrayList<OrderDetailENT> ents = new ArrayList<OrderDetailENT>();

	public ArrayList<OrderDetailENT> getEnts() {
		return ents;
	}

	public void setEnts(ArrayList<OrderDetailENT> ents) {
		this.ents = ents;
	}

}

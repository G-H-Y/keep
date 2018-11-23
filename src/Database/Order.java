package Database;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	private IntegerProperty UserID;
	private StringProperty OrderRecordDay;
	private StringProperty GoodsID;
	private StringProperty GoodsName;
	private DoubleProperty UnitPrice;
	private IntegerProperty GoodsQuantity;
	private DoubleProperty TotalCost;
	private StringProperty Address;
	private StringProperty Telephone;
	
	public Order(int userID, String ord,String gid,String gn,double up,int gq,double tc,String addr,String tele) {
		super();
		UserID = new SimpleIntegerProperty(userID);
		OrderRecordDay = new SimpleStringProperty(ord);
		GoodsID = new SimpleStringProperty(gid);
		GoodsName = new SimpleStringProperty(gn);
		UnitPrice = new SimpleDoubleProperty(up);
		GoodsQuantity = new SimpleIntegerProperty(gq);
		TotalCost = new SimpleDoubleProperty(tc);
		Address = new SimpleStringProperty(addr);
		Telephone = new SimpleStringProperty(tele);
	}

	public IntegerProperty getUserIDProperty() {
		return UserID;
	}

	public StringProperty getOrderRecordDayProperty() {
		return OrderRecordDay;
	}

	public StringProperty getGoodsIDProperty() {
		return GoodsID;
	}

	public StringProperty getGoodsNameProperty() {
		return GoodsName;
	}

	public DoubleProperty getUnitPriceProperty() {
		return UnitPrice;
	}

	public IntegerProperty getGoodsQuantityProperty() {
		return GoodsQuantity;
	}

	public DoubleProperty getTotalCostProperty() {
		return TotalCost;
	}

	public StringProperty getAddressProperty() {
		return Address;
	}

	public StringProperty getTelephoneProperty() {
		return Telephone;
	}
	//////////////////////////////
	public int getUserID() {
		return UserID.get();
	}

	public String getOrderRecordDay() {
		return OrderRecordDay.get();
	}

	public String getGoodsID() {
		return GoodsID.get();
	}

	public String getGoodsName() {
		return GoodsName.get();
	}

	public double getUnitPrice() {
		return UnitPrice.get();
	}

	public int getGoodsQuantity() {
		return GoodsQuantity.get();
	}

	public double getTotalCost() {
		return TotalCost.get();
	}

	public String getAddress() {
		return Address.get();
	}

	public String getTelephone() {
		return Telephone.get();
	}
	
}

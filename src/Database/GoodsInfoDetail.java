package Database;

public class GoodsInfoDetail {
	private String GoodsID;
	private String GoodsName;
	private String GoodsType;
	private double UnitPrice;
	//private String GoodsDetail;
	
	public GoodsInfoDetail(String GoodsID,String GoodsName,String GoodsType,double UnitPrice) {
		super();
		this.GoodsID = GoodsID;
		this.GoodsName = GoodsName;
		this.GoodsType = GoodsType;
		this.UnitPrice = UnitPrice;
		//this.GoodsDetail = GoodsDetail;
	}

	public String getGoodsID() {
		return GoodsID;
	}

	public void setGoodsID(String goodsID) {
		GoodsID = goodsID;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

	public String getGoodsType() {
		return GoodsType;
	}

	public void setGoodsType(String goodsType) {
		GoodsType = goodsType;
	}

	public double getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}

	/*public String getGoodsDetail() {
		return GoodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		GoodsDetail = goodsDetail;
	}*/
	
}

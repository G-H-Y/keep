package Database;

public class WeighttoDay {
	private int UserID;
	private String UserName;
	private String RecordDay;
	private double Weight;
	
	public WeighttoDay(int userID, String userName, String rd,double weight) {
		super();
		this.UserID = userID;
		this.UserName = userName;
		this.RecordDay = rd;
		this.Weight = weight;
	}
	
	public WeighttoDay(int userID, String rd,double weight) {
		super();
		this.UserID = userID;
		this.RecordDay = rd;
		this.Weight = weight;
	}

	public int getUserID() {
		return UserID;
	}

	public String getUserName() {
		return UserName;
	}

	public String getRecordDay() {
		return RecordDay;
	}

	public double getWeight() {
		return Weight;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setRecordDay(String recordDay) {
		RecordDay = recordDay;
	}

	public void setWeight(double weight) {
		Weight = weight;
	}
}

package Database;

public class UserInfo {
	private int UserID;
	private String UserName;
	private String Sex;
	private int Age;
	private double Height;
	private double Weight; //最近一次记录的体重
	private double Balance;
	private double BMR;
	
	private double BFR;
	private String RankBFR;
	private double BMI;
	private String RankBMI;
	
	public UserInfo() {
		
	}
/*	public UserInfo(int userID, String userName,double height,double weight,double balance,double bmr,double bfr,double bmi) {
		super();
		UserID = userID;
		UserName = userName;
		Height = height;
		Weight = weight; //最近一次记录的体重
		Balance = balance;
		BMR = bmr;
		BFR = bfr;
		BMI = bmi;
	}*/

	public int getUserID() {
		return UserID;
	}

	public String getUserName() {
		return UserName;
	}

	public double getHeight() {
		return Height;
	}

	public double getWeight() {
		return Weight;
	}

	public double getBalance() {
		return Balance;
	}

	public double getBMR() {
		return BMR;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setHeight(double height) {
		Height = height;
	}

	public void setWeight(double weight) {
		Weight = weight;
	}

	public void setBalance(double balance) {
		Balance = balance;
	}

	public void setBMR(double bMR) {
		BMR = bMR;
	}
	public double getBFR() {
		return BFR;
	}
	public double getBMI() {
		return BMI;
	}
	public void setBFR(double bFR) {
		BFR = bFR;
	}
	public void setBMI(double bMI) {
		BMI = bMI;
	}

	public String getSex() {
		return Sex;
	}

	public int getAge() {
		return Age;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getRankBFR() {
		return RankBFR;
	}

	public String getRankBMI() {
		return RankBMI;
	}

	public void setRankBFR(String rankBFR) {
		RankBFR = rankBFR;
	}

	public void setRankBMI(String rankBMI) {
		RankBMI = rankBMI;
	}
	
}

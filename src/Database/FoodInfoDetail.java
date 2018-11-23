package Database;

public class FoodInfoDetail {
	private String FoodID;
	private String FoodName;
	private String FoodType;
	private Double FoodUnitCal;
	private String Rank;
	private Double Fat;
	private Double Protein;
	private Double Carbo;
	
	public FoodInfoDetail(String FoodID,String FoodName,String FoodType,double FoodUnitCal,
			String Rank,double Fat,double Protein,double Carbo) {
		super();
		this.FoodID = FoodID;
		this.FoodName = FoodName;
		this.FoodType = FoodType;
		this.FoodUnitCal = FoodUnitCal;
		this.Rank = Rank;
		this.Fat = Fat;
		this.Protein = Protein;
		this.Carbo = Carbo;
	}

	public String getFoodID() {
		return FoodID;
	}

	public void setFoodID(String foodID) {
		FoodID = foodID;
	}

	public String getFoodName() {
		return FoodName;
	}

	public void setFoodName(String foodName) {
		FoodName = foodName;
	}

	public String getFoodType() {
		return FoodType;
	}

	public void setFoodType(String foodType) {
		FoodType = foodType;
	}

	public double getFoodUnitCal() {
		return FoodUnitCal;
	}

	public void setFoodUnitCal(double foodUnitCal) {
		FoodUnitCal = foodUnitCal;
	}

	public String getRank() {
		return Rank;
	}

	public void setRank(String rank) {
		Rank = rank;
	}

	public double getFat() {
		return Fat;
	}

	public void setFat(double fat) {
		Fat = fat;
	}

	public double getProtein() {
		return Protein;
	}

	public void setProtein(double protein) {
		Protein = protein;
	}

	public double getCarbo() {
		return Carbo;
	}

	public void setCarbo(double carbo) {
		Carbo = carbo;
	}

	public void setFoodUnitCal(Double foodUnitCal) {
		FoodUnitCal = foodUnitCal;
	}

	public void setFat(Double fat) {
		Fat = fat;
	}

	public void setProtein(Double protein) {
		Protein = protein;
	}

	public void setCarbo(Double carbo) {
		Carbo = carbo;
	}
	
	
}

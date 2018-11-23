package Database;

public class SportInfoDetail {
	private String SportID;
	private String SportName;
	private String SportType;
	private double CaloriesPerSet;
	private String Step;
	
	public SportInfoDetail(String SportID,String SportName,String SportType,double CaloriesPerSet,String Step) {
		super();
		this.SportID = SportID;
		this.SportName = SportName;
		this.SportType = SportType;
		this.CaloriesPerSet = CaloriesPerSet;
		this.Step = Step;
	}

	public String getSportID() {
		return SportID;
	}

	public void setSportID(String sportID) {
		SportID = sportID;
	}

	public String getSportName() {
		return SportName;
	}

	public void setSportName(String sportName) {
		SportName = sportName;
	}

	public String getSportType() {
		return SportType;
	}

	public void setSportType(String sportType) {
		SportType = sportType;
	}

	public double getCaloriesPerSet() {
		return CaloriesPerSet;
	}

	public void setCaloriesPerSet(double caloriesPerSet) {
		CaloriesPerSet = caloriesPerSet;
	}

	public String getStep() {
		return Step;
	}

	public void setStep(String step) {
		Step = step;
	}
	
}

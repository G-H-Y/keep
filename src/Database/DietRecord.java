package Database;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DietRecord {
	private IntegerProperty UserID;
	private StringProperty DietRecordDay;
	private StringProperty MealType;
	private StringProperty FoodID;
	private StringProperty FoodName;
	private DoubleProperty FoodCalo;
	private DoubleProperty FoodWeight;
	private DoubleProperty IntakeCalo;
	
	public DietRecord(int userID,String drd,String mt,String fid,String fn,double fc,double fw,double ic) {
		super();
		UserID = new SimpleIntegerProperty(userID) ;
		DietRecordDay = new SimpleStringProperty(drd) ;
		MealType =  new SimpleStringProperty(mt) ;
		FoodID = new SimpleStringProperty(fid);
		FoodName = new SimpleStringProperty(fn);
		FoodCalo = new SimpleDoubleProperty(fc) ;
		FoodWeight = new SimpleDoubleProperty(fw) ;
		IntakeCalo = new SimpleDoubleProperty(ic) ;
	}

	public IntegerProperty getUserIDProperty() {
		return UserID;
	}

	public StringProperty getDietRecordDayProperty() {
		return DietRecordDay;
	}

	public StringProperty getMealTypeProperty() {
		return MealType;
	}

	public StringProperty getFoodIDProperty() {
		return FoodID;
	}

	public StringProperty getFoodNameProperty() {
		return FoodName;
	}

	public DoubleProperty getFoodCaloProperty() {
		return FoodCalo;
	}

	public DoubleProperty getFoodWeightProperty() {
		return FoodWeight;
	}

	public DoubleProperty getIntakeCaloProperty() {
		return IntakeCalo;
	}


	public int getUserID() {
		return UserID.get();
	}

	public String getDietRecordDay() {
		return DietRecordDay.get();
	}

	public String getMealType() {
		return MealType.get();
	}

	public String getFoodID() {
		return FoodID.get();
	}
	
	public String getFoodName() {
		return FoodName.get();
	}

	public double getFoodCalo() {
		return FoodCalo.get();
	}

	public double getFoodWeight() {
		return FoodWeight.get();
	}

	public double getIntakeCalo() {
		return IntakeCalo.get();
	}
}

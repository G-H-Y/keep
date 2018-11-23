package Database;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExerciseRecord {
	private IntegerProperty UserID;
	private StringProperty ExerRecordDay;
	private StringProperty SportType;
	private StringProperty SportID;
	private StringProperty SportName;
	private DoubleProperty BurnCalo;
	private IntegerProperty TrainSet;
	
	public ExerciseRecord(int userID,String erd,String st,String sid,String sn,double bc,int ts) {
		super();
		UserID = new SimpleIntegerProperty(userID);
		ExerRecordDay = new SimpleStringProperty(erd);
		SportType = new SimpleStringProperty(st);
		SportID = new SimpleStringProperty(sid);
		SportName = new SimpleStringProperty(sn);
		BurnCalo = new SimpleDoubleProperty(bc);
		TrainSet = new SimpleIntegerProperty(ts);
	}

	public IntegerProperty getUserIDProperty() {
		return UserID;
	}

	public StringProperty getExerRecordDayProperty() {
		return ExerRecordDay;
	}

	public StringProperty getSportTypeProperty() {
		return SportType;
	}

	public StringProperty getSportIDProperty() {
		return SportID;
	}

	public StringProperty getSportNameProperty() {
		return SportName;
	}

	public DoubleProperty getBurnCaloProperty() {
		return BurnCalo;
	}

	public IntegerProperty getTrainSetProperty() {
		return TrainSet;
	}
	public int getUserID() {
		return UserID.get();
	}

	public String getExerRecordDay() {
		return ExerRecordDay.get();
	}

	public String getSportType() {
		return SportType.get();
	}

	public String getSportID() {
		return SportID.get();
	}
	
	public String getSportName() {
		return SportName.get();
	}

	public double getBurnCalo() {
		return BurnCalo.get();
	}

	public int getTrainSet() {
		return TrainSet.get();
	}
}

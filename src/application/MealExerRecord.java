package application;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Database.DietRecord;
import Database.ExerciseRecord;
import Database.GetDatabaseInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MealExerRecord implements Initializable{
	
	@FXML
	private Button ReturnHome;
	
	@FXML
	private TableView<ExerciseRecord> Exercise;
	
	@FXML
	private TableColumn<ExerciseRecord,String> SportName;
	
	@FXML
	private TableColumn<ExerciseRecord,Number> TrainSet;
	
	@FXML
	private TableColumn<ExerciseRecord,Number> BurnCal;
	
	@FXML
	private TableView<DietRecord> Diet;
	
	@FXML
	private TableColumn<DietRecord,String> MealType;
	
	@FXML
	private TableColumn<DietRecord,String> FoodName;
	
	@FXML
	private TableColumn<DietRecord,Number> FoodWeight;
	
	@FXML
	private TableColumn<DietRecord,Number> IntakeCal;
	
	@FXML
	private Button AddRecord;
	
	@FXML
	private TextField WeightRecord;
	
	@FXML
	private DatePicker WeightRecordDay;
	
	@FXML
	private DatePicker ExerDietRcdDay;
	
	@FXML
	private Button Query;
	
	private ObservableList<ExerciseRecord> ExerRcdObvList = FXCollections.observableArrayList();
	private ObservableList<DietRecord> DietRcdObvList = FXCollections.observableArrayList();
	private List<ExerciseRecord> ExerRcdList = new ArrayList<ExerciseRecord>();
	private List<DietRecord> DietRcdList = new ArrayList<DietRecord>();
	
	private int userID;
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setUserID(int userID) {
		this.userID = userID;
		System.out.println(userID);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){	
		//初始化运动饮食记录显示当天的记录，如果用户查询再显示其他记录
		SportName.setCellValueFactory(cellData -> cellData.getValue().getSportNameProperty());
		TrainSet.setCellValueFactory(cellData -> cellData.getValue().getTrainSetProperty());
		BurnCal.setCellValueFactory(cellData -> cellData.getValue().getBurnCaloProperty());
		
		MealType.setCellValueFactory(cellData->cellData.getValue().getMealTypeProperty());
		FoodName.setCellValueFactory(cellData->cellData.getValue().getFoodNameProperty());
		FoodWeight.setCellValueFactory(cellData->cellData.getValue().getFoodWeightProperty());
		IntakeCal.setCellValueFactory(cellData->cellData.getValue().getIntakeCaloProperty());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String rd = dateFormat.format(date);
		ExerRcdList = GetDatabaseInfo.getExerciseRecord(userID,rd);
		DietRcdList = GetDatabaseInfo.getDietRecord(userID,rd);
		ExerRcdObvList.addAll(ExerRcdList);
		DietRcdObvList.addAll(DietRcdList);
		
	}
	
	@FXML
	public void QueryClicked(MouseEvent evt) {
		String date;
		if(ExerDietRcdDay.getValue()!=null) {
			date = ExerDietRcdDay.getValue().toString();
			ExerRcdObvList.clear();
			DietRcdObvList.clear();
			ExerRcdObvList.addAll(GetDatabaseInfo.getExerciseRecord(userID, date));
			Exercise.setItems(ExerRcdObvList);
			DietRcdObvList.addAll(GetDatabaseInfo.getDietRecord(userID, date));
			Diet.setItems(DietRcdObvList);
		}
	}
	
	@FXML
	public void AddRecordClicked(MouseEvent evt) {
		if(WeightRecord.getText().equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加失败");
			altUsr.setHeaderText("请填写您记录的体重！");
			altUsr.showAndWait();
			return;
		}
		else if(WeightRecordDay.getValue()==null) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加失败");
			altUsr.setHeaderText("请填写您记录的体重日期！");
			altUsr.showAndWait();
			return;
		}
		else {
			String date = WeightRecordDay.getValue().toString();
			String weight = WeightRecord.getText();
			GetDatabaseInfo.insertWeightRecord(userID, date, weight);
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加成功");
			altUsr.setHeaderText("您"+date+"号的体重为"+weight+"kg!");
			altUsr.showAndWait();
			return;
		}
	}
	
	@FXML
	public void ReturnHomeClicked(MouseEvent evt) {
		try {
			//加载视图
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
			Parent root = loader.load();
			//生成场景scene
			Scene scene = new Scene(root);
			//设置舞台
			Stage stage = new Stage();
			stage.setScene(scene);
			HomePage hpCtl = loader.getController();
			hpCtl.setUser(GetDatabaseInfo.getUserInfo(userID));
			hpCtl.setStage(stage);
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
		
}

package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Database.GetDatabaseInfo;
import Database.SportInfoDetail;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SportInformation implements Initializable{
	
	@FXML
	private RadioButton Treadmill;
	
	@FXML
	private RadioButton	HIIT;
	
	@FXML
	private RadioButton AerobicExer;
	
	@FXML
	private RadioButton Warmup;
	
	@FXML
	private ListView<String> SportList;
	
	@FXML
	private ImageView SportImg;
	
	@FXML
	private Label SportName;
	
	@FXML
	private Label UnitCalories;
	
	@FXML
	private TextFlow Step;
	
	@FXML
	private TextField SelectSport;
	
	@FXML
	private TextField BurnCalories;
	
	@FXML
	private Slider TrainNum;
	
	@FXML
	private Label ShowTrainNum;
	
	@FXML
	private Button AddRecord;
	
	@FXML
	private Button ReturnHome;
	
	private ObservableList<String> SportObvList = FXCollections.observableArrayList();
	//private ObservableList TrainStep = Step.getChildren();
	private List<SportInfoDetail> SportInfo;
	private SportInfoDetail selSport = null;
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
		//获取运动信息
		SportInfo = GetDatabaseInfo.getSportInfoDetail();
		/////////////////初始化界面///////////////////////
		//初始化ImageView根据用户在ListView中选择的运动再进行设置
			Image image = new Image(getClass().getResource("/image/SportInfo.png").toExternalForm());
			SportImg.setImage(image);
		
		//初始化Label，根据用户在ListView中选择的运动再进行设置
		SportName.setText("运动项目名称：");
		UnitCalories.setText("燃脂(kcal/组)：");
		
		//初始化textflow
		//Step.setAccessibleText("一起运动吧！");
		Text text = new Text("一起运动吧！"); 
		text.setFont(new Font(15));
		text.setFill(Color.DARKBLUE);
		Step.getChildren().add(text);
		
		////////////////处理radioButton事件//////////////////
		ToggleGroup buttonGroup = new ToggleGroup();
		Treadmill.setToggleGroup(buttonGroup);
		HIIT.setToggleGroup(buttonGroup);
		AerobicExer.setToggleGroup(buttonGroup);
		Warmup.setToggleGroup(buttonGroup);
		Treadmill.setUserData("跑步机课程");
		HIIT.setUserData("HIIT燃脂");
		AerobicExer.setUserData("有氧操课");
		Warmup.setUserData("热身拉伸");
		
		//选择某种运动类别后将listView的内容设置为相应类别的运动课程
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		    		
		            if (new_toggle != null) {
		            	String slectedSportType = new_toggle.getUserData().toString();
		            	System.out.println("选择的运动种类： "+slectedSportType);
		            	SportObvList.clear();
		            	for(SportInfoDetail sid:SportInfo) {
		            		if(sid.getSportType().equals(slectedSportType)) {
		            			SportObvList.add(sid.getSportName());
		            			System.out.println(sid.getSportName());
		            		}
		            	}
		            	//SportList.getItems().clear();
		            	SportList.setItems(SportObvList);
		            }                
		        }
		});
		
		//////////////////处理ListView事件/////////////////////
		//选择某种运动后将ImageView设置成对应的运动的图片，设置Label，设置Textfield
		SportList.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	for(SportInfoDetail sid:SportInfo) {
		            		if(sid.getSportName().equals(new_val)) {	
		            			//设置当前选中的运动
		            			selSport = sid;
		            			//设置ImageView
		            			String sportID = sid.getSportID();
		            			Image image = new Image("/image/"+sportID +".png");
		            			SportImg.setImage(image);
		            			//设置Label
		            			SportName.setText(sid.getSportName().toString());
		            			UnitCalories.setText(sid.getCaloriesPerSet()+"kcal/组");
		            			//设置Textfiled
		            			SelectSport.setText(new_val);
		            			//设置textflow
		            			Text text = new Text(sid.getStep().toString()); 
		            			text.setFont(new Font(15));
		            			text.setFill(Color.DARKBLUE);
		            			Step.getChildren().clear();
		            			Step.getChildren().add(text);
		            		}
		            	}	                	 
	            }
	        });
		
		SportList.getFocusModel().focusedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	for(SportInfoDetail sid:SportInfo) {
		            		if(sid.getSportName().equals(new_val)) {	
		            			//设置当前选中的运动
		            			selSport = sid;
		            			//设置ImageView
		            			String sportID = sid.getSportID();
		            			Image image = new Image("/image/"+sportID +".png");
		            			SportImg.setImage(image);
		            			//设置Label
		            			SportName.setText(sid.getSportName().toString());
		            			UnitCalories.setText(sid.getCaloriesPerSet()+"kcal/组");		  
		            			//设置textflow
		            			Text text = new Text(sid.getStep().toString()); 
		            			text.setFont(new Font(15));
		            			text.setFill(Color.DARKBLUE);
		            			Step.getChildren().clear();
		            			Step.getChildren().add(text);		            			
		            		}
		            	}	                       	 
	            }
	        });
		
		////////////处理Slider事件///////////////////////////
		TrainNum.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
						ShowTrainNum.setText(String.format("%d", new_val.intValue())+"组");
						if(!(selSport==null)) {
							BurnCalories.setText((selSport.getCaloriesPerSet()*new_val.intValue())+"");
						}
					}
				});
	}
	
	@FXML
	public void AddRecordClicked(MouseEvent evt) {
		if(selSport==null) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加失败");
			altUsr.setHeaderText("请选择您要记录的运动！");
			altUsr.showAndWait();
			return;
		}
		else if(TrainNum.getValue()==0) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加失败");
			altUsr.setHeaderText("请选择您今日做了几组"+selSport.getSportName().toString()+"!");
			altUsr.showAndWait();
			return;
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = new Date();
		    String rd = dateFormat.format(date);
			String sportID = selSport.getSportID();
			String sportName = selSport.getSportName();
			String sportType = selSport.getSportType();
			int sportSet = (int)TrainNum.getValue();
			String burnCal = BurnCalories.getText();
			GetDatabaseInfo.insertExerRecord(userID,rd ,sportID, sportName,sportType, sportSet, burnCal);
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加成功");
			altUsr.setHeaderText("太棒啦！您今天完成了"+sportSet+"组"+sportName+",继续加油哦!");
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

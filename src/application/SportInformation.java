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
		//��ȡ�˶���Ϣ
		SportInfo = GetDatabaseInfo.getSportInfoDetail();
		/////////////////��ʼ������///////////////////////
		//��ʼ��ImageView�����û���ListView��ѡ����˶��ٽ�������
			Image image = new Image(getClass().getResource("/image/SportInfo.png").toExternalForm());
			SportImg.setImage(image);
		
		//��ʼ��Label�������û���ListView��ѡ����˶��ٽ�������
		SportName.setText("�˶���Ŀ���ƣ�");
		UnitCalories.setText("ȼ֬(kcal/��)��");
		
		//��ʼ��textflow
		//Step.setAccessibleText("һ���˶��ɣ�");
		Text text = new Text("һ���˶��ɣ�"); 
		text.setFont(new Font(15));
		text.setFill(Color.DARKBLUE);
		Step.getChildren().add(text);
		
		////////////////����radioButton�¼�//////////////////
		ToggleGroup buttonGroup = new ToggleGroup();
		Treadmill.setToggleGroup(buttonGroup);
		HIIT.setToggleGroup(buttonGroup);
		AerobicExer.setToggleGroup(buttonGroup);
		Warmup.setToggleGroup(buttonGroup);
		Treadmill.setUserData("�ܲ����γ�");
		HIIT.setUserData("HIITȼ֬");
		AerobicExer.setUserData("�����ٿ�");
		Warmup.setUserData("��������");
		
		//ѡ��ĳ���˶�����listView����������Ϊ��Ӧ�����˶��γ�
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		    		
		            if (new_toggle != null) {
		            	String slectedSportType = new_toggle.getUserData().toString();
		            	System.out.println("ѡ����˶����ࣺ "+slectedSportType);
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
		
		//////////////////����ListView�¼�/////////////////////
		//ѡ��ĳ���˶���ImageView���óɶ�Ӧ���˶���ͼƬ������Label������Textfield
		SportList.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	for(SportInfoDetail sid:SportInfo) {
		            		if(sid.getSportName().equals(new_val)) {	
		            			//���õ�ǰѡ�е��˶�
		            			selSport = sid;
		            			//����ImageView
		            			String sportID = sid.getSportID();
		            			Image image = new Image("/image/"+sportID +".png");
		            			SportImg.setImage(image);
		            			//����Label
		            			SportName.setText(sid.getSportName().toString());
		            			UnitCalories.setText(sid.getCaloriesPerSet()+"kcal/��");
		            			//����Textfiled
		            			SelectSport.setText(new_val);
		            			//����textflow
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
		            			//���õ�ǰѡ�е��˶�
		            			selSport = sid;
		            			//����ImageView
		            			String sportID = sid.getSportID();
		            			Image image = new Image("/image/"+sportID +".png");
		            			SportImg.setImage(image);
		            			//����Label
		            			SportName.setText(sid.getSportName().toString());
		            			UnitCalories.setText(sid.getCaloriesPerSet()+"kcal/��");		  
		            			//����textflow
		            			Text text = new Text(sid.getStep().toString()); 
		            			text.setFont(new Font(15));
		            			text.setFill(Color.DARKBLUE);
		            			Step.getChildren().clear();
		            			Step.getChildren().add(text);		            			
		            		}
		            	}	                       	 
	            }
	        });
		
		////////////����Slider�¼�///////////////////////////
		TrainNum.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
						ShowTrainNum.setText(String.format("%d", new_val.intValue())+"��");
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
			altUsr.setTitle("���ʧ��");
			altUsr.setHeaderText("��ѡ����Ҫ��¼���˶���");
			altUsr.showAndWait();
			return;
		}
		else if(TrainNum.getValue()==0) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("���ʧ��");
			altUsr.setHeaderText("��ѡ�����������˼���"+selSport.getSportName().toString()+"!");
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
			altUsr.setTitle("��ӳɹ�");
			altUsr.setHeaderText("̫�����������������"+sportSet+"��"+sportName+",��������Ŷ!");
			altUsr.showAndWait();
			return;
		}
	}
	
	@FXML
	public void ReturnHomeClicked(MouseEvent evt) {
		try {
			//������ͼ
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
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

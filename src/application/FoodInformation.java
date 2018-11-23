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

import Database.FoodInfoDetail;
import Database.GetDatabaseInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.stage.Stage;

public class FoodInformation implements Initializable{
	
	@FXML
	private RadioButton Grains;
	
	@FXML
	private RadioButton MeatEgg;
	
	@FXML
	private RadioButton MilkDairy;
	
	@FXML
	private RadioButton FruitVegtLact;
	
	@FXML
	private RadioButton NutSoyb;
	
	@FXML
	private RadioButton Drink;
	
	@FXML
	private RadioButton Snacks;
	
	@FXML
	private RadioButton Dishes;
	
	@FXML
	private ListView<String> FoodList;
	
	@FXML
	private ImageView FoodImg;
	
	@FXML
	private Label FoodName;
	
	@FXML
	private Label UnitCalories;
	
	@FXML
	private Label FoodRank;
	
	@FXML
	private BarChart<String,Number> NutrientsContent;
	
	@FXML
	private CategoryAxis Nutrients;
	
	@FXML
	private NumberAxis Content;
	
	@FXML
	private TextField SelectFood;
	
	@FXML
	private ComboBox<String> MealType;
	
	@FXML
	private Slider FoodQuantity;
	
	@FXML
	private Label ShowQuantity;
	
	@FXML
	private TextField IntakeCalories;
	
	@FXML
	private Button AddRecord;
	
	@FXML
	private Button ReturnHome;
	
	private ObservableList<String> FoodObvList = FXCollections.observableArrayList();
	private ObservableList<String> MealTypeObvList = FXCollections.observableArrayList("���","���","���","�Ӳ�");
	private List<FoodInfoDetail> FoodInfo;
	
	final static String FAT = "֬��";
	final static String PROTEIN = "������";
	final static String CARBOH = "̼ˮ������";
	private FoodInfoDetail selFood = null;
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
		//��ȡʳ����Ϣ
		FoodInfo = GetDatabaseInfo.getFoodInfoDetail();
		
		////////////////////////////��ʼ������/////////////////////////
		//��ʼ��ImageView�����û���ListView��ѡ���ʳ���ٽ�������
		Image img = new Image(getClass().getResource("/image/FoodInfo.png").toExternalForm());
		FoodImg.setImage(img);
		
		//��ʼ��Label�������û���ListView��ѡ���ʳ���ٽ�������
		FoodName.setText("ʳ������:");
		UnitCalories.setText("��λ����:");
		FoodRank.setText("ʳ��������");
		
		//��ʼ��bar chart
		XYChart.Series<String,Number> ser = new XYChart.Series<String,Number>();
		ser.getData().add(new XYChart.Data<String,Number>(FAT,0));
		ser.getData().add(new XYChart.Data<String,Number>(PROTEIN,0));
		ser.getData().add(new XYChart.Data<String,Number>(CARBOH,0));
		NutrientsContent.getData().add(ser);
		
		//��ʼ��combobox
		MealType.setItems(MealTypeObvList);
		
		/////////////////����radioButton/////////////////////////////
		ToggleGroup buttonGroup = new ToggleGroup();
		Grains.setToggleGroup(buttonGroup);
		MeatEgg.setToggleGroup(buttonGroup);
		MilkDairy.setToggleGroup(buttonGroup);
		FruitVegtLact.setToggleGroup(buttonGroup);
		NutSoyb.setToggleGroup(buttonGroup);
		Drink.setToggleGroup(buttonGroup);
		Snacks.setToggleGroup(buttonGroup);
		Dishes.setToggleGroup(buttonGroup);
		
		Grains.setUserData("�������Ӷ�����ʳ");
		MeatEgg.setUserData("���ࡢ���༰��Ʒ");
		MilkDairy.setUserData("���༰��Ʒ");
		FruitVegtLact.setUserData("�߹�������");
		NutSoyb.setUserData("������󶹼���Ʒ");
		Drink.setUserData("����");
		Snacks.setUserData("��ʳ");
		Dishes.setUserData("����");
		
		//ѡ��ĳ��ʳ������listView����������Ϊ��Ӧ����ʳ��
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (new_toggle != null) {
		            	String slectedFoodType = new_toggle.getUserData().toString();
		            	System.out.println("����ʳ������Ϊ��"+slectedFoodType);
		            	FoodObvList.clear();
		            	for(FoodInfoDetail fid:FoodInfo) {
		            		if(fid.getFoodType().equals(slectedFoodType)) {
		            			FoodObvList.add(fid.getFoodName());
		            			System.out.println("ʳ�����ƣ�"+fid.getFoodName());
		            		}
		            	}
		            	//FoodList.getSelectionModel().clearSelection();
		            	FoodList.setItems(FoodObvList);
		            }                
		        }
		});
		
		//////////////����ListView////////////////////
		//ѡ��ĳ��ʳ���ImageView���óɶ�Ӧ��ʳ���ͼƬ������Label������Textfield
		FoodList.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	for(FoodInfoDetail fid:FoodInfo) {
		            		if(fid.getFoodName().equals(new_val)) {	
		            			//���õ�ǰѡ�е�ʳ��
		            			selFood = fid;
		            			//����ImageView
		            			String foodID = fid.getFoodID();		      
		            			Image image = new Image(getClass().getResource("/image/"+foodID+".png").toExternalForm());
		            			FoodImg.setImage(image);
		            			//����Label
		            			FoodName.setText("ʳ������:"+fid.getFoodName().toString());
		            			UnitCalories.setText("��λ������"+fid.getFoodUnitCal()+"kcal/100g");
		            			FoodRank.setText("ʳ������"+fid.getRank().toString());
		            			//����Textfiled
		            			SelectFood.setText(new_val);
		            			//���ñ��
		            			XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
		            			series.getData().add(new XYChart.Data<String,Number>(FAT,fid.getFat()));
		            			series.getData().add(new XYChart.Data<String,Number>(PROTEIN,fid.getProtein()));
		            			series.getData().add(new XYChart.Data<String,Number>(CARBOH,fid.getCarbo()));
		            			/*System.out.println(fid.getFat());
		            			System.out.println(fid.getProtein());
		            			System.out.println(fid.getCarbo());*/
		            			NutrientsContent.getData().clear();
		            			NutrientsContent.getData().add(series);
		            			
		            		}
		            	}	                	 
	            }
	        });
		
		FoodList.getFocusModel().focusedItemProperty().addListener(
				new ChangeListener<String>() {
					 public void changed(ObservableValue<? extends String> ov, 
			                    String old_val, String new_val) {
						 for(FoodInfoDetail fid:FoodInfo) {
			            		if(fid.getFoodName().equals(new_val)) {
			            			//����ImageView
			            			String foodID = fid.getFoodID();
			            			Image image = new Image(getClass().getResource("/image/"+foodID+".png").toExternalForm());
			            			FoodImg.setImage(image);
			            			//����Label
			            			FoodName.setText("ʳ������: "+fid.getFoodName().toString());
			            			UnitCalories.setText("��λ����: "+fid.getFoodUnitCal()+"kcal/100g");
			            			FoodRank.setText("ʳ������: "+fid.getRank().toString());
			            			//���ñ��
			            			XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
			            			series.getData().add(new XYChart.Data<String,Number>(FAT,fid.getFat()));
			            			series.getData().add(new XYChart.Data<String,Number>(PROTEIN,fid.getProtein()));
			            			series.getData().add(new XYChart.Data<String,Number>(CARBOH,fid.getCarbo()));
			            			NutrientsContent.getData().clear();
			            			NutrientsContent.getData().add(series);
			            		}
			            	}
					 }
				});
		
		/////////////����Slider�¼�///////////////////////////////
		FoodQuantity.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
						ShowQuantity.setText(String.format("%.2f", new_val.doubleValue())+"g");
						if(!(selFood==null)) {		
							IntakeCalories.setText(""+(selFood.getFoodUnitCal()*(new_val.doubleValue()/100)));
						}
					}
				});
		
	}
	
	@FXML
	public void AddRecordClicked(MouseEvent evt) {
		if(selFood==null) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("���ʧ��");
			altUsr.setHeaderText("��ѡ����Ҫ��¼��ʳ�");
			altUsr.showAndWait();
			return;
		}
		else if(FoodQuantity.getValue()==0) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("���ʧ��");
			altUsr.setHeaderText("��ѡ����Ҫ��¼��ʳ��������");
			altUsr.showAndWait();
			return;
		}
		else if(MealType.getValue()==null) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("���ʧ��");
			altUsr.setHeaderText("��ѡ��ͱ�");
			altUsr.showAndWait();
			return;
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = new Date();
		    String rd = dateFormat.format(date);
			String mealType = MealType.getValue();
			String foodID = selFood.getFoodID();
			String foodName = selFood.getFoodName();
			double foodCal = selFood.getFoodUnitCal();
			double foodWeight = FoodQuantity.getValue();
			String inCal = IntakeCalories.getText();
			GetDatabaseInfo.insertDietRecord(userID,rd,mealType,foodID,foodName,foodCal,foodWeight,inCal);
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("��ӳɹ�");
			altUsr.setHeaderText("���Ѿ��ɹ����һ����ʳ��¼��");
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

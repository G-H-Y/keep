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
	private ObservableList<String> MealTypeObvList = FXCollections.observableArrayList("早餐","午餐","晚餐","加餐");
	private List<FoodInfoDetail> FoodInfo;
	
	final static String FAT = "脂肪";
	final static String PROTEIN = "蛋白质";
	final static String CARBOH = "碳水化合物";
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
		//获取食物信息
		FoodInfo = GetDatabaseInfo.getFoodInfoDetail();
		
		////////////////////////////初始化界面/////////////////////////
		//初始化ImageView根据用户在ListView中选择的食物再进行设置
		Image img = new Image(getClass().getResource("/image/FoodInfo.png").toExternalForm());
		FoodImg.setImage(img);
		
		//初始化Label，根据用户在ListView中选择的食物再进行设置
		FoodName.setText("食物名称:");
		UnitCalories.setText("单位热量:");
		FoodRank.setText("食物评级：");
		
		//初始化bar chart
		XYChart.Series<String,Number> ser = new XYChart.Series<String,Number>();
		ser.getData().add(new XYChart.Data<String,Number>(FAT,0));
		ser.getData().add(new XYChart.Data<String,Number>(PROTEIN,0));
		ser.getData().add(new XYChart.Data<String,Number>(CARBOH,0));
		NutrientsContent.getData().add(ser);
		
		//初始化combobox
		MealType.setItems(MealTypeObvList);
		
		/////////////////设置radioButton/////////////////////////////
		ToggleGroup buttonGroup = new ToggleGroup();
		Grains.setToggleGroup(buttonGroup);
		MeatEgg.setToggleGroup(buttonGroup);
		MilkDairy.setToggleGroup(buttonGroup);
		FruitVegtLact.setToggleGroup(buttonGroup);
		NutSoyb.setToggleGroup(buttonGroup);
		Drink.setToggleGroup(buttonGroup);
		Snacks.setToggleGroup(buttonGroup);
		Dishes.setToggleGroup(buttonGroup);
		
		Grains.setUserData("谷薯芋、杂豆、主食");
		MeatEgg.setUserData("蛋类、肉类及制品");
		MilkDairy.setUserData("奶类及制品");
		FruitVegtLact.setUserData("蔬果及菌藻");
		NutSoyb.setUserData("坚果、大豆及制品");
		Drink.setUserData("饮料");
		Snacks.setUserData("零食");
		Dishes.setUserData("菜肴");
		
		//选择某种食物类别后将listView的内容设置为相应类别的食物
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (new_toggle != null) {
		            	String slectedFoodType = new_toggle.getUserData().toString();
		            	System.out.println("更改食物种类为："+slectedFoodType);
		            	FoodObvList.clear();
		            	for(FoodInfoDetail fid:FoodInfo) {
		            		if(fid.getFoodType().equals(slectedFoodType)) {
		            			FoodObvList.add(fid.getFoodName());
		            			System.out.println("食物名称："+fid.getFoodName());
		            		}
		            	}
		            	//FoodList.getSelectionModel().clearSelection();
		            	FoodList.setItems(FoodObvList);
		            }                
		        }
		});
		
		//////////////设置ListView////////////////////
		//选择某种食物后将ImageView设置成对应的食物的图片，设置Label，设置Textfield
		FoodList.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	for(FoodInfoDetail fid:FoodInfo) {
		            		if(fid.getFoodName().equals(new_val)) {	
		            			//设置当前选中的食物
		            			selFood = fid;
		            			//设置ImageView
		            			String foodID = fid.getFoodID();		      
		            			Image image = new Image(getClass().getResource("/image/"+foodID+".png").toExternalForm());
		            			FoodImg.setImage(image);
		            			//设置Label
		            			FoodName.setText("食物名称:"+fid.getFoodName().toString());
		            			UnitCalories.setText("单位热量："+fid.getFoodUnitCal()+"kcal/100g");
		            			FoodRank.setText("食物评级"+fid.getRank().toString());
		            			//设置Textfiled
		            			SelectFood.setText(new_val);
		            			//设置表格
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
			            			//设置ImageView
			            			String foodID = fid.getFoodID();
			            			Image image = new Image(getClass().getResource("/image/"+foodID+".png").toExternalForm());
			            			FoodImg.setImage(image);
			            			//设置Label
			            			FoodName.setText("食物名称: "+fid.getFoodName().toString());
			            			UnitCalories.setText("单位热量: "+fid.getFoodUnitCal()+"kcal/100g");
			            			FoodRank.setText("食物评级: "+fid.getRank().toString());
			            			//设置表格
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
		
		/////////////处理Slider事件///////////////////////////////
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
			altUsr.setTitle("添加失败");
			altUsr.setHeaderText("请选择您要记录的食物！");
			altUsr.showAndWait();
			return;
		}
		else if(FoodQuantity.getValue()==0) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加失败");
			altUsr.setHeaderText("请选择您要记录的食物数量！");
			altUsr.showAndWait();
			return;
		}
		else if(MealType.getValue()==null) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("添加失败");
			altUsr.setHeaderText("请选择餐别！");
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
			altUsr.setTitle("添加成功");
			altUsr.setHeaderText("您已经成功添加一条饮食记录！");
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

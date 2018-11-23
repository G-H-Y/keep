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
import Database.GoodsInfoDetail;
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
import javafx.stage.Stage;

public class Shop implements Initializable{
	
	@FXML
	private RadioButton ExerSup;
	
	@FXML
	private RadioButton	FitDrink;
	
	@FXML
	private RadioButton LowCalSnack;
	
	@FXML
	private RadioButton SportEquip;
	
	@FXML
	private ListView<String> GoodsList;
	
	@FXML
	private ImageView GoodsImg;
	
	@FXML
	private Label GoodsName;
	
	@FXML
	private Label UnitPrice;
	
	@FXML
	private TextField Address;
	
	@FXML
	private TextField Telephone;
	
	@FXML
	private TextField RechargeMoney;
	
	@FXML
	private Slider GoodsQuantity;
	
	@FXML
	private Label ShowGoodsQuantity;
	
	@FXML
	private Button Buy;
	
	@FXML
	private Button Recharge;
	
	@FXML
	private Button ReturnHome;
	
	private ObservableList<String> GoodsObvList = FXCollections.observableArrayList();
	private List<GoodsInfoDetail> GoodsInfo;
	private GoodsInfoDetail selGoods = null;
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
		//获取商品信息
		GoodsInfo = GetDatabaseInfo.getGoodsInfoDetail();
		/////////////////初始化界面///////////////////////
		//初始化ImageView根据用户在ListView中选择的商品再进行设置		
		Image img = new Image("/image/GoodsInfo.png");
		GoodsImg.setImage(img);
		
		//初始化Label，根据用户在ListView中选择的商品再进行设置
		GoodsName.setText("商品名称：");
		UnitPrice.setText("单价(元)：");
		
		////////////////处理radioButton事件//////////////////
		ToggleGroup buttonGroup = new ToggleGroup();
		ExerSup.setToggleGroup(buttonGroup);
		FitDrink.setToggleGroup(buttonGroup);
		LowCalSnack.setToggleGroup(buttonGroup);
		SportEquip.setToggleGroup(buttonGroup);
		
		ExerSup.setUserData("运动补充");
		FitDrink.setUserData("健身饮品");
		LowCalSnack.setUserData("轻卡零食");
		SportEquip.setUserData("运动装备");
		
		//选择某种商品类别后将listView的内容设置为相应类别的商品
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (new_toggle != null) {
		            	GoodsObvList.clear();
		            	String slectedSportType = new_toggle.getUserData().toString();
		            	for(GoodsInfoDetail gid:GoodsInfo) {
		            		if(gid.getGoodsType().equals(slectedSportType)) {
		            			GoodsObvList.add(gid.getGoodsName());
		            		}
		            	}
		            	//GoodsList.getItems().clear();
		            	GoodsList.setItems(GoodsObvList);
		            }                
		        }
		});
		
		//////////////////处理ListView事件/////////////////////
		//选择某种运动后将ImageView设置成对应的运动的图片，设置Label，设置Textfield
		GoodsList.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	for(GoodsInfoDetail gid:GoodsInfo) {
		            		if(gid.getGoodsName().equals(new_val)) {	
		            			//设置当前选中的运动
		            			selGoods = gid;
		            			//设置ImageView
		            			String goodsID = gid.getGoodsID();
		            			Image image = new Image("/image/"+goodsID+".png");
		            			GoodsImg.setImage(image);
		            			//设置Label
		            			GoodsName.setText("商品名称："+gid.getGoodsName().toString());
		            			UnitPrice.setText("单价(元)："+gid.getUnitPrice()+"元");
		            		}
		            	}	                	 
	            }
	        });
		
		GoodsList.getFocusModel().focusedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	for(GoodsInfoDetail gid:GoodsInfo) {
		            		if(gid.getGoodsName().equals(new_val)) {	
		            			//设置当前选中的运动
		            			selGoods = gid;
		            			//设置ImageView
		            			String goodsID = gid.getGoodsID();
		            			Image image = new Image("/image/"+goodsID +".png");
		            			GoodsImg.setImage(image);
		            			//设置Label
		            			GoodsName.setText("商品名称："+gid.getGoodsName().toString());
		            			UnitPrice.setText("单价(元)："+gid.getUnitPrice()+"元");
		            		}
		            	}	                	 
	            }
	        });
		
		////////////处理Slider事件///////////////////////////
		GoodsQuantity.valueProperty().addListener(
			new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					ShowGoodsQuantity.setText(String.format("%d", new_val.intValue())+"个");
				}
			});
	}
	
	@FXML
	public void BuyClicked(MouseEvent evt) {
		if(selGoods==null) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("购买失败");
			altUsr.setHeaderText("请选择您要购买的商品！");
			altUsr.showAndWait();
			return;
		}
		else if(GoodsQuantity.getValue()==0) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("购买失败");
			altUsr.setHeaderText("请选择您要购买的商品数目！");
			altUsr.showAndWait();
			return;
		}
		else if(GoodsQuantity.getValue()*selGoods.getUnitPrice()>GetDatabaseInfo.getUserBalance(userID)) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("购买失败");
			altUsr.setHeaderText("您的账户余额不足，请充值！");
			altUsr.showAndWait();
			return;
		}
		else if(Address.getText().equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("购买失败");
			altUsr.setHeaderText("请填写您的收货地址！");
			altUsr.showAndWait();
			return;
		}
		else if(Telephone.getText().equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("购买失败");
			altUsr.setHeaderText("请填写您的联系电话！");
			altUsr.showAndWait();
			return;
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = new Date();
		    String rd = dateFormat.format(date);
			String goodsID = selGoods.getGoodsID();
			String goodsName = selGoods.getGoodsName();
			double unitPrice = selGoods.getUnitPrice();
			int quantity = (int)GoodsQuantity.getValue();
			double totalCost = ((int)GoodsQuantity.getValue())*selGoods.getUnitPrice();
			String address = Address.getText();
			String telephone = Telephone.getText();
			GetDatabaseInfo.insertOrderRecord(userID,rd, goodsID, goodsName,unitPrice, quantity, totalCost, address, telephone);
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("购买成功");
			altUsr.setHeaderText("您的宝贝正在快马加鞭向您赶来！");
			altUsr.showAndWait();
			return;
		}
	}
	
	@FXML
	public void RechargeClicked(MouseEvent evt) {
		if(RechargeMoney.getText().toString().equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("充值失败");
			altUsr.setHeaderText("请输入充值金额！");
			altUsr.showAndWait();
			return;
		}
		else {
			GetDatabaseInfo.updateUserBalance(userID, RechargeMoney.getText().toString());
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("充值成功");
			altUsr.setHeaderText("您已经成功充值"+RechargeMoney.getText().toString()+"元！");
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

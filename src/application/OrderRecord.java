package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Database.GetDatabaseInfo;
import Database.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OrderRecord implements Initializable{

	@FXML
	private Button ReturnHome;
	
	@FXML
	private ImageView Img;
	
	@FXML
	private TableView<Order> Orders;
	
	@FXML
	private TableColumn<Order,String> GoodsName;
	
	@FXML
	private TableColumn<Order,Number> GoodsQuantity;
	
	@FXML
	private TableColumn<Order,Number> UnitPrice;
	
	@FXML
	private TableColumn<Order,Number> TotalCost;
	
	@FXML
	private TableColumn<Order,String> OrderDay;
	
	private ObservableList<Order> OrderObvList = FXCollections.observableArrayList();
	private List<Order> OrderList= new ArrayList<Order>();
	private int userID;
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setUserID(int userID) {
		this.userID = userID;
		System.out.println(userID);
		OrderList = GetDatabaseInfo.getOrderList(userID);
		OrderObvList.addAll(OrderList);
		Orders.setItems(OrderObvList);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		
			Image image = new Image("/image/OrderInfo.png");
			Img.setImage(image);
		
		GoodsName.setCellValueFactory(cellData -> cellData.getValue().getGoodsNameProperty());
		GoodsQuantity.setCellValueFactory(cellData -> cellData.getValue().getGoodsQuantityProperty());
		UnitPrice.setCellValueFactory(cellData -> cellData.getValue().getUnitPriceProperty());
		TotalCost.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty());
		OrderDay.setCellValueFactory(cellData -> cellData.getValue().getOrderRecordDayProperty());
		
		
	}
	
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

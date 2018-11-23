package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Database.GetDatabaseInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RcmdRecipe implements Initializable{
	
	@FXML
	private ImageView RpImgLeft;
	
	@FXML
	private ImageView RpImgRight;
	
	@FXML
	private Button ReturnHome;
	
	@FXML
	private RadioButton Recipe1;
	
	@FXML
	private RadioButton Recipe2;
	
	@FXML
	private RadioButton Recipe3;
	
	@FXML
	private RadioButton Recipe4;
	
	@FXML
	private RadioButton Recipe5;
	
	@FXML
	private RadioButton Recipe6;
	
	private Stage stage;
	
	private int userID;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){		
		ToggleGroup buttonGroup = new ToggleGroup();
		Recipe1.setToggleGroup(buttonGroup);
		Recipe2.setToggleGroup(buttonGroup);
		Recipe3.setToggleGroup(buttonGroup);
		Recipe4.setToggleGroup(buttonGroup);
		Recipe5.setToggleGroup(buttonGroup);
		Recipe6.setToggleGroup(buttonGroup);
		Recipe1.setUserData("食谱1");
		Recipe2.setUserData("食谱2");
		Recipe3.setUserData("食谱3");
		Recipe4.setUserData("食谱4");
		Recipe5.setUserData("食谱5");
		Recipe6.setUserData("食谱6");
		
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {		    	
		            if (new_toggle != null) {
		            	System.out.println("选中食谱！");
		            	String selRecipe = new_toggle.getUserData().toString();
		            	System.out.println(selRecipe);
		            	Image imgL = new Image(getClass().getResource("/image/"+selRecipe+"_1.png").toExternalForm());
		            	RpImgLeft.setImage(imgL);
		            	Image imgR = new Image(getClass().getResource("/image/"+selRecipe+"_2.png").toExternalForm());
		            	RpImgRight.setImage(imgR);		    			
		            }
		            else {
		            	System.out.println("食谱出错啦！");
		            }
		        }
		});
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
			hpCtl.setStage(stage);
			hpCtl.setUser(GetDatabaseInfo.getUserInfo(userID));
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
}

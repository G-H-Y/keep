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
		Recipe1.setUserData("ʳ��1");
		Recipe2.setUserData("ʳ��2");
		Recipe3.setUserData("ʳ��3");
		Recipe4.setUserData("ʳ��4");
		Recipe5.setUserData("ʳ��5");
		Recipe6.setUserData("ʳ��6");
		
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {		    	
		            if (new_toggle != null) {
		            	System.out.println("ѡ��ʳ�ף�");
		            	String selRecipe = new_toggle.getUserData().toString();
		            	System.out.println(selRecipe);
		            	Image imgL = new Image(getClass().getResource("/image/"+selRecipe+"_1.png").toExternalForm());
		            	RpImgLeft.setImage(imgL);
		            	Image imgR = new Image(getClass().getResource("/image/"+selRecipe+"_2.png").toExternalForm());
		            	RpImgRight.setImage(imgR);		    			
		            }
		            else {
		            	System.out.println("ʳ�׳�������");
		            }
		        }
		});
	}
	
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
			hpCtl.setStage(stage);
			hpCtl.setUser(GetDatabaseInfo.getUserInfo(userID));
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
}

package application;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

import Database.GetDatabaseInfo;
import Database.UserInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginSignup implements Initializable{

	@FXML
	private TextField LoginUser;
	
	@FXML
	private Button Login;
	
	@FXML
	private PasswordField LoginPsw;
	
	@FXML
	private TextField SignupUser;
	
	@FXML
	private CheckBox Female;
	
	@FXML
	private CheckBox Male;
	
	@FXML
	private Slider Age;
	
	@FXML
	private Slider Height;
	
	@FXML
	private Slider InitialWeight;
	
	@FXML
	private Slider TargetWeight;
	
	@FXML
	private DatePicker StartLoseWeightDay;
	
	@FXML
	private DatePicker FinishLoseWeightDay;
	
	@FXML
	private PasswordField SetPsw;
	
	@FXML
	private PasswordField RepeatPsw;
	
	@FXML
	private Button Signup;
	
	@FXML
	private ImageView LoginImg;
	
	@FXML
	private ImageView SignupImg;
	
	@FXML
	private Label ShowAge;
	
	@FXML
	private Label ShowHeight;
	
	@FXML
	private Label ShowInitialWght;
	
	@FXML
	private Label ShowTgtWght;
	
	private Stage stage;
	
	public void setStage(Stage stg) {
		stage = stg;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){	
		Image imgL = new Image(getClass().getResource("/image/login.png").toExternalForm());
		Image imgS = new Image(getClass().getResource("/image/signup.png").toExternalForm());
		LoginImg.setImage(imgL);
		SignupImg.setImage(imgS);
		/////////////处理Slider事件///////////////////////////////
		Age.valueProperty().addListener(
			new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
					ShowAge.setText( ((int)new_val.doubleValue())+"岁");
				}
			});	
		
		Height.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
						ShowHeight.setText(String.format("%.2f", new_val.doubleValue())+"cm");
					}
				});	
		
		InitialWeight.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
						ShowInitialWght.setText(String.format("%.2f", new_val.doubleValue())+"kg");
					}
				});	
		
		TargetWeight.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
						ShowTgtWght.setText(String.format("%.2f", new_val.doubleValue())+"kg");
					}
				});	
	}

	@FXML
	public void LoginClicked(MouseEvent evt) {
		String lgUser = LoginUser.getText();
		String lgPsw = LoginPsw.getText();
		System.out.println("用户名："+lgUser);
		System.out.println("用户密码: "+lgPsw);
		if(lgUser.equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("登录失败");
			altUsr.setHeaderText("请输入您的用户名！");
			altUsr.showAndWait();
			return;
		}
		else if(lgPsw.equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("登录失败");
			altUsr.setHeaderText("请输入您的登录密码！");
			altUsr.showAndWait();
			return;
		}
		if(!GetDatabaseInfo.isUserExist(lgUser)) { //判断用户是否存在，返回布尔值，填写一个对应的函数IsUserExist
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("登录失败");
			altUsr.setHeaderText("用户不存在，请检查您输入的用户名是否正确！");
			altUsr.showAndWait();
			return;
		}
		else if(!GetDatabaseInfo.login(lgUser, lgPsw)) { //从数据库中获取账号和密码信息并判断，返回布尔值,填写一个对应的函数Login
			Alert altPsw = new Alert(Alert.AlertType.INFORMATION);
			altPsw.setTitle("登录失败");
			altPsw.setHeaderText("密码错误，请重新输入您的密码！");
			altPsw.showAndWait();
			return;
		}
		else { //登录成功，转到个人主页页面
			try {
				System.out.println("登录成功！");
				
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
				hpCtl.setUser(GetDatabaseInfo.getUserInfo(lgUser));												
				stage.show();
				this.stage.close();			
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}		
	}
	
	@FXML
	public void SignupClicked(MouseEvent evt) {
		String signupUser = SignupUser.getText();
		int age = (int)Age.getValue();
		String sex = "";
		double height = Height.getValue();
		double initWeight = InitialWeight.getValue();
		double tgtWeight = TargetWeight.getValue();
		LocalDate start = StartLoseWeightDay.getValue();
		LocalDate end = FinishLoseWeightDay.getValue();
		String fstPsw = SetPsw.getText();
		String sndPsw = RepeatPsw.getText();
		if(signupUser.equals("")) {
			Notification("注册失败","请填写您的注册用户名！");
		}
		else if((Female.isSelected()&&Male.isSelected())||(!(Female.isSelected()||Male.isSelected()))) {
			Notification("注册失败","请选择您的性别!");
		}
		else if(age==0) {
			Notification("注册失败","请选择您的年龄，方便我们为您提供更精确的减重和锻炼意见!");
		}
		else if((height==0)||(initWeight==0)||(tgtWeight==0)) {
			Notification("注册失败","请填写您的身高体重信息！");
		}
		else if((start==null)||(end==null)) {
			Notification("注册失败","请填写您预计的减重时间段！");
		}
		else if(end.compareTo(start)<0) {
			Notification("注册失败","起始日期不能晚于结束日期！");
		}
		else if(fstPsw.equals("")||sndPsw.equals("")) {
			Notification("注册失败","请填写您的登录密码并进行确认！");
		}
		else if(!fstPsw.equals(sndPsw)) {
			Notification("注册失败","两次密码输入不一致，请重新输入密码！");
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = new Date();
		    String rd = dateFormat.format(date);
		    sex = (Female.isSelected())?"F":"M";
			long days = ChronoUnit.DAYS.between(start,end);
			System.out.println(signupUser);
			System.out.println(fstPsw);
			System.out.println(sex);
			System.out.println(age+"");
			System.out.println(height+"");
			System.out.println(initWeight+"");
			System.out.println(tgtWeight+"");
			System.out.println(days+"");
			System.out.println(rd);
			GetDatabaseInfo.signup(signupUser, fstPsw, sex, age, height, initWeight, tgtWeight, days,rd);
			GetDatabaseInfo.insertWeightRecord(signupUser, rd, initWeight);
			Notification("注册成功","您已经注册成功！");
		}
	}
	
	public void Notification(String title,String text) {
		Alert altPsw = new Alert(Alert.AlertType.INFORMATION);
		altPsw.setTitle(title);
		altPsw.setHeaderText(text);
		altPsw.showAndWait();
		return;
	}
}

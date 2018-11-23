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
		/////////////����Slider�¼�///////////////////////////////
		Age.valueProperty().addListener(
			new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
					ShowAge.setText( ((int)new_val.doubleValue())+"��");
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
		System.out.println("�û�����"+lgUser);
		System.out.println("�û�����: "+lgPsw);
		if(lgUser.equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("��¼ʧ��");
			altUsr.setHeaderText("�����������û�����");
			altUsr.showAndWait();
			return;
		}
		else if(lgPsw.equals("")) {
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("��¼ʧ��");
			altUsr.setHeaderText("���������ĵ�¼���룡");
			altUsr.showAndWait();
			return;
		}
		if(!GetDatabaseInfo.isUserExist(lgUser)) { //�ж��û��Ƿ���ڣ����ز���ֵ����дһ����Ӧ�ĺ���IsUserExist
			Alert altUsr = new Alert(Alert.AlertType.INFORMATION);
			altUsr.setTitle("��¼ʧ��");
			altUsr.setHeaderText("�û������ڣ�������������û����Ƿ���ȷ��");
			altUsr.showAndWait();
			return;
		}
		else if(!GetDatabaseInfo.login(lgUser, lgPsw)) { //�����ݿ��л�ȡ�˺ź�������Ϣ���жϣ����ز���ֵ,��дһ����Ӧ�ĺ���Login
			Alert altPsw = new Alert(Alert.AlertType.INFORMATION);
			altPsw.setTitle("��¼ʧ��");
			altPsw.setHeaderText("������������������������룡");
			altPsw.showAndWait();
			return;
		}
		else { //��¼�ɹ���ת��������ҳҳ��
			try {
				System.out.println("��¼�ɹ���");
				
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
			Notification("ע��ʧ��","����д����ע���û�����");
		}
		else if((Female.isSelected()&&Male.isSelected())||(!(Female.isSelected()||Male.isSelected()))) {
			Notification("ע��ʧ��","��ѡ�������Ա�!");
		}
		else if(age==0) {
			Notification("ע��ʧ��","��ѡ���������䣬��������Ϊ���ṩ����ȷ�ļ��غͶ������!");
		}
		else if((height==0)||(initWeight==0)||(tgtWeight==0)) {
			Notification("ע��ʧ��","����д�������������Ϣ��");
		}
		else if((start==null)||(end==null)) {
			Notification("ע��ʧ��","����д��Ԥ�Ƶļ���ʱ��Σ�");
		}
		else if(end.compareTo(start)<0) {
			Notification("ע��ʧ��","��ʼ���ڲ������ڽ������ڣ�");
		}
		else if(fstPsw.equals("")||sndPsw.equals("")) {
			Notification("ע��ʧ��","����д���ĵ�¼���벢����ȷ�ϣ�");
		}
		else if(!fstPsw.equals(sndPsw)) {
			Notification("ע��ʧ��","�����������벻һ�£��������������룡");
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
			Notification("ע��ɹ�","���Ѿ�ע��ɹ���");
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

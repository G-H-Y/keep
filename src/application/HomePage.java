package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import Database.DietRecord;
import Database.ExerciseRecord;
import Database.GetDatabaseInfo;
import Database.UserInfo;
import Database.WeighttoDay;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomePage implements Initializable{
	
	@FXML
	private ImageView Avatar;
	
	@FXML
	private Label UserName;
	
	@FXML
	private Label Balance;
	
	@FXML
	private Label DateTime;
	
	@FXML
	private Label Weight;
	
	@FXML
	private Label BMI;
	
	@FXML
	private Label BFR;
	
	@FXML
	private Label TodayExercise;
	
	@FXML
	private Label TodayMeal;
	
	@FXML
	private Label BrkftCal;
	
	@FXML
	private Label LunchCal;
	
	@FXML
	private Label DinnerCal;
	
	@FXML
	private ProgressBar InBrkft;
	
	@FXML
	private ProgressBar InLunch;
	
	@FXML
	private ProgressBar InDinner;
	
	@FXML
	private LineChart<String,Number> WeightChart;
	
	@FXML
	private CategoryAxis DayXaxis;
	
	@FXML
	private NumberAxis WeightYaxis;
	
	@FXML
	private Button RcmdRecipe;
	
	@FXML
	private Button MealExerRcrd;
	
	@FXML
	private Button SportsInfo;
	
	@FXML
	private Button FoodInfo;
	
	@FXML
	private Button OrdersRcrd;
	
	@FXML
	private Button GoodsShop;
	
	private Stage stage;
	private UserInfo user;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setUser(UserInfo user) {
		System.out.println("��ʼ���ø�����ҳ��Ϣ����");	
		this.user = user;	
		//�������֣�����ʱ�䣬���أ���֬��BMI	
		UserName.setText(user.getUserName());System.out.println(user.getUserName());	
		Balance.setText("��"+user.getBalance());System.out.println("��"+user.getBalance());
		Weight.setText("���أ�"+user.getWeight());System.out.println("���أ�"+user.getWeight());
		BMI.setText("BMI:"+user.getBMI()+" "+user.getRankBMI());System.out.println("BMI:"+user.getBMI()+" "+user.getRankBMI());
		BFR.setText("��֬�ʣ�"+user.getBFR()+"%  "+user.getRankBFR());System.out.println("��֬�ʣ�"+(user.getBFR()*100)+"%  "+user.getRankBFR());
		//��ȡ��������
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String rd = dateFormat.format(date);
	    DateTime.setText(rd);	
		//��������˶���������������ʳ��������
		List<ExerciseRecord> todayExer = GetDatabaseInfo.getExerciseRecord(user.getUserID(), rd);
		List<DietRecord> todayDiet = GetDatabaseInfo.getDietRecord(user.getUserID(), rd);
		double burnCal = 0;
		double inCal = 0;
		double brkfst = 0;
		double lunch = 0;
		double dinner = 0;
		for(ExerciseRecord te:todayExer) {
			burnCal += te.getBurnCalo();
		}
		
		for(DietRecord td:todayDiet) {
			inCal += td.getIntakeCalo();
			if(td.getMealType().equals("���")) {
				brkfst += td.getIntakeCalo();
			}
			else if(td.getMealType().equals("���")) {
				lunch += td.getIntakeCalo();
			}
			else if(td.getMealType().equals("���")) {
				dinner += td.getIntakeCalo();
			}
		}
		//��ʾ������ʳ��������������
		TodayExercise.setText("�����˶����ģ�"+burnCal+"kcal");System.out.println("�����˶����ģ�"+burnCal+"kcal");
		TodayMeal.setText("��������������"+inCal+"kcal/"+user.getBMR()+"kcal");System.out.println("��������������"+inCal+"kcal/"+user.getBMR()+"kcal");
		InBrkft.setProgress((brkfst*13)/(user.getBMR()*3));
		InLunch.setProgress((lunch*13)/(user.getBMR()*4));
		System.out.println("������룺"+(lunch*4)/(user.getBMR()*13));
		InDinner.setProgress((dinner*13)/(user.getBMR()*3));
		
		//��ʾ���ر仯		
		List<WeighttoDay> wgtDay = GetDatabaseInfo.getWeighttoDayList(user.getUserID());
		System.out.println("��ȡ���ر仯");
		XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
		series.setName("����");
		for(WeighttoDay wtd: wgtDay) {
			series.getData().add(new XYChart.Data<String,Number>(wtd.getRecordDay(), wtd.getWeight()));
		}
		WeightChart.getData().add(series);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){	
		System.out.println("��ӭ����������ҳ��");	
		//����ͷ��
		Image img = new Image(getClass().getResource("/image/avatar.jpg").toExternalForm());
		Avatar.setImage(img);
	}
	
	@FXML
	public void RcmdRecipeClicked(MouseEvent evt) {
		try {
			//������ͼ
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RecommendRecipe.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
			Stage stage = new Stage();
			stage.setScene(scene);
			RcmdRecipe rrCtl = loader.getController();
			rrCtl.setUserID(user.getUserID());
			rrCtl.setStage(stage);
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@FXML
	public void MealExerRcrdClicked(MouseEvent evt) {
		try {
			//������ͼ
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MealExerciseRecord.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
			Stage stage = new Stage();
			stage.setScene(scene);
			MealExerRecord merCtl = loader.getController();
			merCtl.setStage(stage);
			merCtl.setUserID(user.getUserID());
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@FXML
	public void SportsInfoClicked(MouseEvent evt) {
		try {
			//������ͼ
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SportInformation.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
			Stage stage = new Stage();
			stage.setScene(scene);
			SportInformation siCtl = loader.getController();
			siCtl.setStage(stage);
			siCtl.setUserID(user.getUserID());
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void FoodInfoClicked(MouseEvent evt) {
		try {
			//������ͼ
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodInformation.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
			Stage stage = new Stage();
			stage.setScene(scene);
			FoodInformation fdCtl = loader.getController();
			fdCtl.setStage(stage);
			fdCtl.setUserID(user.getUserID());
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@FXML
	public void OrdersRcrdClicked(MouseEvent evt) {
		try {
			//������ͼ
			FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderRecord.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
			Stage stage = new Stage();
			stage.setScene(scene);
			OrderRecord orCtl = loader.getController();
			orCtl.setStage(stage);
			orCtl.setUserID(user.getUserID());
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@FXML
	public void GoodsShopClicked(MouseEvent evt) {
		try {
			//������ͼ
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Shop.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
			Stage stage = new Stage();
			stage.setScene(scene);
			Shop spCtl = loader.getController();
			spCtl.setStage(stage);
			spCtl.setUserID(user.getUserID());
			stage.show();
			this.stage.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}

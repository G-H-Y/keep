package application;
	
import Database.ConnectDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ConnectDatabase.connect();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginSignup.fxml"));
			Parent root = loader.load();
			//���ɳ���scene
			Scene scene = new Scene(root);
			//������̨
			primaryStage.setScene(scene);
			LoginSignup lsCtl = loader.getController();
			lsCtl.setStage(primaryStage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

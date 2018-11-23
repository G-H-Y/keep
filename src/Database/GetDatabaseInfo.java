package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Pair;

public class GetDatabaseInfo {
	private static Connection conn = null;
	
	public static void setConn(Connection conn) {
		GetDatabaseInfo.conn = conn;
	}
	
	//获取食物信息
	public static List<FoodInfoDetail> getFoodInfoDetail(){
		List<FoodInfoDetail> FoodInfoList = new ArrayList<FoodInfoDetail>();
		try {
			String sqlQuery = "select * from FoodInfo";
			PreparedStatement prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			while(sqlRes.next()) {
				String FoodID = sqlRes.getString("FoodID");
				String FoodName = sqlRes.getString("FoodName");
				String FoodType = sqlRes.getString("FoodType");
				double FoodUnitCal = sqlRes.getDouble("FoodUnitCalories");
				String Rank = sqlRes.getString("Rank");
				double Fat = sqlRes.getDouble("Fat");
				double Protein = sqlRes.getDouble("Protein");
				double Carbo = sqlRes.getDouble("Carbohydrates");
				FoodInfoList.add(new FoodInfoDetail(FoodID,FoodName,FoodType,FoodUnitCal,Rank,Fat,Protein,Carbo));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return FoodInfoList;
	}
	
	//插入饮食记录
	public static void insertDietRecord(int userID,String date,String mealType,String foodID,String foodName,double foodCal,double foodWeight,String inCal) {
		String sqlInsert = "insert into UserDietRecord values(null,"+userID+",'"+date+"','"+mealType+"','"+foodID+"','"+foodName+"',"+foodCal+","+foodWeight+","+inCal+");";
		System.out.println(sqlInsert);
		PreparedStatement prepst;
		try {
			prepst = conn.prepareStatement(sqlInsert);
			prepst.executeUpdate(sqlInsert);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	//获取运动信息
	public static List<SportInfoDetail> getSportInfoDetail(){
		List<SportInfoDetail> SportInfoList = new ArrayList<SportInfoDetail>();
		
		PreparedStatement prepst;
		try {
			String sqlQuery = "select * from SportInfo";
			prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			while(sqlRes.next()) {
				String SportID = sqlRes.getString("SportID");
				String SportName = sqlRes.getString("SportName");
				String SportType = sqlRes.getString("SportType");
				double CaloriesPerSet = sqlRes.getDouble("CaloriesPerSet");
				String Step = sqlRes.getString("Step");
				SportInfoList.add(new SportInfoDetail(SportID,SportName,SportType,CaloriesPerSet,Step));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return SportInfoList;
	}
	
	//插入运动记录
	public static void insertExerRecord(int userID,String date, String sid,String sn,String st,int ts,String bc) {
		String sqlInsert = "insert into UserExerciseRecord values(null,"+userID+",'"+date+"','"+sid+"','"+sn+"','"+st+"',"+ts+",'"+bc+"');";
		PreparedStatement prepst;
		try {
			prepst = conn.prepareStatement(sqlInsert);
			prepst.executeUpdate(sqlInsert);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	//获取商品信息
	public static List<GoodsInfoDetail> getGoodsInfoDetail() {
		List<GoodsInfoDetail> GoodsInfoList = new ArrayList<GoodsInfoDetail>();
		
		PreparedStatement prepst;
		try {
			String sqlQuery = "select * from GoodsInfo";
			prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			while(sqlRes.next()) {
				String GoodsID = sqlRes.getString("GoodsID");
				String GoodsName = sqlRes.getString("GoodsName");
				String GoodsType = sqlRes.getString("GoodsType");
				double UnitPrice = sqlRes.getDouble("UnitPrice");
				//String GoodsDetail = sqlRes.getString("GoodsDetail");
				GoodsInfoList.add(new GoodsInfoDetail(GoodsID,GoodsName,GoodsType,UnitPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return GoodsInfoList;
	}
	
	//获取账户余额
	public static double getUserBalance(int userID) {
		double balance = 0;
		PreparedStatement prepst;
		try {
			String sqlQuery = "select Balance from UserRegisterInfo where UserID = "+userID+";";
			prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			if(sqlRes.next()) {
				balance = sqlRes.getDouble("Balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return balance;
	}
	
	//插入订单
	public static void insertOrderRecord(int userID,String date,String gid,String gn,double up,int gq,double tc,String addr,String tele) {
		String sqlInsert = "insert into UserOrderRecord values(null,"+userID+",'"+date+"','"+gid+"','"+gn+"',"+up+","+gq+","+tc+",'"+addr+"','"+tele+"');";
		PreparedStatement prepst;
		try {
			prepst = conn.prepareStatement(sqlInsert);
			prepst.executeUpdate(sqlInsert);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	//充值后更新余额
	public static void updateUserBalance(int userID,String rgMoney) {
		String sqlUpdate = "update UserRegisterInfo set Balance = Balance + "+rgMoney+" where UserID = "+userID+";";
		PreparedStatement prepst;
		try {
			prepst = conn.prepareStatement(sqlUpdate);
			prepst.executeUpdate(sqlUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	//获取运动记录
	public static List<ExerciseRecord> getExerciseRecord(int userID, String date){
		List<ExerciseRecord> exerRcdList = new ArrayList<ExerciseRecord>();
		PreparedStatement prepst;
		try {
			String sqlQuery = "select * from UserExerciseRecord where UserID = "+userID+" and ExerciseRecordDay = '"+date+"';";
			System.out.println(sqlQuery);
			prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			while(sqlRes.next()) {
				int UserID = sqlRes.getInt("UserID");
				String ExerRecordDay = sqlRes.getString("ExerciseRecordDay");
				String SportType = sqlRes.getString("SportType");
				String SportID = sqlRes.getString("SportID");
				String SportName = sqlRes.getString("SportName");
				double BurnCalo = sqlRes.getDouble("BurnCalories");
				int TrainSet = sqlRes.getInt("TrainSet");
				exerRcdList.add(new ExerciseRecord(UserID,ExerRecordDay,SportType,SportID,SportName,BurnCalo,TrainSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return exerRcdList;
	}
	
	//获取饮食记录
	public static List<DietRecord> getDietRecord(int userID, String date){
		List<DietRecord> DietRcdList = new ArrayList<DietRecord>();
		try {
			String sqlQuery = "select * from UserDietRecord where UserID = "+userID+" and DietRecordDay = '"+date+"';";
			PreparedStatement prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			while(sqlRes.next()) {
				int UserID = sqlRes.getInt("UserID") ;
				String DietRecordDay = sqlRes.getString("DietRecordDay") ;
				String MealType =  sqlRes.getString("MealType") ;
				String FoodID = sqlRes.getString("FoodID");
				String FoodName = sqlRes.getString("FoodName");
				double FoodCalo = sqlRes.getDouble("FoodCalories") ;
				double FoodWeight = sqlRes.getDouble("FoodWeight") ;
				double IntakeCalo = sqlRes.getDouble("IntakeColories");
				DietRcdList.add(new DietRecord(UserID,DietRecordDay,MealType,FoodID,FoodName,FoodCalo,FoodWeight,IntakeCalo));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return DietRcdList;
	}
	
	//插入体重记录
	public static void insertWeightRecord(int userID, String date,String weight) {
		String sqlInsert = "insert into UserPhysicalIndicator (UserID, RecordDay, Weight) values("+userID+",'"+date+"',"+weight+");";
		PreparedStatement prepst;
		try {
			prepst = conn.prepareStatement(sqlInsert);
			prepst.executeUpdate(sqlInsert);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	public static void insertWeightRecord(String userName, String date,double weight) {
		
		String sqlQuery = "select UserID from UserRegisterInfo where UserName = '"+userName+"';";
		
		PreparedStatement prepst;
		try {
			prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			if(sqlRes.next()) {
				int userID = sqlRes.getInt("UserID");
				String sqlInsert = "insert into UserPhysicalIndicator (UserID, RecordDay, Weight) values("+userID+",'"+date+"',"+weight+");";
				prepst = conn.prepareStatement(sqlInsert);
			prepst.executeUpdate(sqlInsert);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	//获取历史订单
	public static List<Order> getOrderList(int userID){
		List<Order> orderList = new ArrayList<Order>();
		try {
			String sqlQuery = "select * from UserOrderRecord where UserID = "+userID+";";
			System.out.println("订单查询：");
			System.out.println(sqlQuery);
			PreparedStatement prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			while(sqlRes.next()) {
				int UserID = sqlRes.getInt("UserID");
				String OrderRecordDay = sqlRes.getString("OrderRecordDay");
				String GoodsID = sqlRes.getString("GoodsID");
				String GoodsName = sqlRes.getString("GoodsName");
				double UnitPrice = sqlRes.getDouble("UnitPrice");
				int GoodsQuantity = sqlRes.getInt("GoodsQuantity");
				double TotalCost = sqlRes.getDouble("TotalCost");
				String Address = sqlRes.getString("Address");
				String Telephone = sqlRes.getString("Telephone");
				orderList.add(new Order(UserID,OrderRecordDay,GoodsID,GoodsName,UnitPrice,GoodsQuantity,TotalCost,Address,Telephone));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		return orderList;
	}
	
	//获取用户信息
	public static UserInfo getUserInfo(String userName){
		UserInfo user = new UserInfo();
		PreparedStatement prepst;
		try {			
			String sqlQuery = "select * from UserRegisterInfo where UserName = '"+userName+"';";
			prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();	
			if(sqlRes.next()) {
				user.setUserName(userName);
				user.setUserID(sqlRes.getInt("UserID"));
				user.setAge(sqlRes.getInt("Age"));
				user.setSex(sqlRes.getString("Sex"));
			    user.setHeight(sqlRes.getDouble("Height"));
				user.setBalance(sqlRes.getDouble("Balance"));
				user.setBMR(sqlRes.getDouble("BMR"));
				user.setWeight(sqlRes.getDouble("CurrentWeight"));
				user.setBFR(sqlRes.getDouble("CurrentBFR"));
				user.setRankBFR(sqlRes.getString("CurrentRankBFR"));
				user.setBMI(sqlRes.getDouble("CurrentBMI"));
				user.setRankBMI(sqlRes.getString("CurrentRankBMI"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return user;
	}
	
	public static UserInfo getUserInfo(int userID){
		UserInfo user = new UserInfo();
		PreparedStatement prepst;
		try {			
			String sqlQuery = "select * from UserRegisterInfo where UserID = "+userID+";";
			prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();	
			if(sqlRes.next()) {
				user.setUserName(sqlRes.getString("UserName"));
				user.setUserID(sqlRes.getInt("UserID"));
				user.setAge(sqlRes.getInt("Age"));
				user.setSex(sqlRes.getString("Sex"));
			    user.setHeight(sqlRes.getDouble("Height"));
				user.setBalance(sqlRes.getDouble("Balance"));
				user.setBMR(sqlRes.getDouble("BMR"));
				user.setWeight(sqlRes.getDouble("CurrentWeight"));
				user.setBFR(sqlRes.getDouble("CurrentBFR"));
				user.setRankBFR(sqlRes.getString("CurrentRankBFR"));
				user.setBMI(sqlRes.getDouble("CurrentBMI"));
				user.setRankBMI(sqlRes.getString("CurrentRankBMI"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return user;
	}
	
	//获取用户体重变化
	public static List<WeighttoDay> getWeighttoDayList(int userID){
		List<WeighttoDay> wgtDaylist = new ArrayList<WeighttoDay>();
		try {
			String sqlQuery = "select * from UserPhysicalIndicator where UserID = "+userID+" order by RecordDay asc;";
			PreparedStatement prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			while(sqlRes.next()) {
				String recordDay = sqlRes.getString("RecordDay");
				double weight = sqlRes.getDouble("Weight");
				wgtDaylist.add(new WeighttoDay(userID,recordDay,weight));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return wgtDaylist;
	}
	
	//判断用户是否存在
	public static boolean isUserExist(String userName) {
		try {
			String sqlQuery = "select * from UserRegisterInfo where userName = '"+userName+"';";
			PreparedStatement prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			if(sqlRes.next()) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//获取用户密码
	public static boolean login(String userName,String psw) {
		try {
			String sqlQuery = "select * from UserRegisterInfo where userName = '"+userName+"';";
			PreparedStatement prepst = conn.prepareStatement(sqlQuery);
			ResultSet sqlRes = prepst.executeQuery();
			if(sqlRes.next()) {
				return sqlRes.getString("Password").equals(psw);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//用户注册
	public static void signup(String userName,String psw,String sex,int age,double height,double initWeight,double tgtWeight,long days,String registerDay ) {
		try {
			String sqlInsert = "insert into UserRegisterInfo(UserName,Password,Sex,Age,Height,InitialWeight,TargetWeight,LoseWeightDuration,UserRegisterDay,Balance) values('"+userName+"','"+psw+"','"+sex+"',"+age+","+height+","+initWeight+","+tgtWeight+","+days+",'"+registerDay+"',"+0+");";
			System.out.println(sqlInsert);
			PreparedStatement prepst = conn.prepareStatement(sqlInsert);
			prepst.executeUpdate(sqlInsert);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

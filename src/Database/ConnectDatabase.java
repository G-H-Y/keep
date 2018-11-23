package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDatabase {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/keep?useUnicode=true&characterEncoding=utf8";
	static final String USER = "root";
	static final String PASSWORD = "19990117ghy";
	
	static Connection conn = null;
	
	public static void connect() {
		if(conn == null) {
			try{
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
				if(conn==null) {
					System.out.println(DB_URL);
				}
				GetDatabaseInfo.setConn(conn);
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

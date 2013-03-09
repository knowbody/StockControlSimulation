import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
	
	private static final String DB_STRING = "jdbc:odbc:db1";
	
	public static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(DB_STRING);
		
	}
	
	public static void processException(SQLException e) {
		System.err.println("Error message: " + e.getMessage());
		System.err.println("Error code: " + e.getErrorCode());
		System.err.println("SQL state: " + e.getSQLState());
	}

}

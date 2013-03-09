import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {

	

	public static void main(String[] args) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		// because these objects have been place within the try with resources section
		// when the application is finished executing try-catch block, they're close methods 
		// will be called automatically and if they're null that is
		try {
			// conn = DriverManager.getConnection(DB_STRING);
			conn = DBUtil.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT ItemPrice, ItemQuantity FROM Table1");

			rs.last();
			System.out.println("rows: " + rs.getRow());

		} catch (SQLException e) {
			DBUtil.processException(e);
		} finally {
			// closing connection in reverse order than executed
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

}

import java.sql.*;


public class db {

	
	Connection cn;
	Statement st;
	ResultSet rs;
	
	
	// my db constructor
	public db() {
		
		connect();
		
	}
	
	// declare my method connect
	public void connect(){
		
		try{
			
			// driver to access database
			String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
			
			// load the class
			Class.forName(driver);
			
			// declaring my database (the name of my database is: db1)
			String db = "jdbc:odbc:Driver";
			
			//connecting to my database
			cn = DriverManager.getConnection(db);
			
			// accessing Table inside my database (my case table name is
			// "Table1")
			st = cn.createStatement();
			String sql = "select * from Table1"; // select everything in Table1
			
			// rs holds everything what is in my Table1
			rs = st.executeQuery(sql);
			
			// check if my connection works by printing the table
			while(rs.next()) {
				
				String productNumberDb = rs.getString("ProductNumber");
				String productNameDb = rs.getString("ProductName");
				double priceDb = rs.getDouble("Price");
				int quantityDb = rs.getInt("Quantity");
				
				System.out.println(productNumberDb + " " + productNameDb + " " + priceDb + " " + quantityDb);
			}
			
		}catch(Exception ex){
			
			
		}
		
	}
	
	public static void main(String[] args) {
		
		new db();
		
	}

}

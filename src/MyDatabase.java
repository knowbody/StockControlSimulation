import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class MyDatabase {

	private String name;
	private String price;
	private String quantity;
	private int searchData;

	/*
	 * 
	 * 
	 * 
	 * */

	/** SEARCH DATABASE */
	public MyDatabase(int searchData) {
		this.searchData = searchData;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:db1");
			Statement st = con.createStatement();
			String productCode = PurchaseItem.getStockNo().getText();
			ResultSet rs = st.executeQuery("select * from Table1 where Key="
					+ productCode);

			while (rs.next()) {
				switch (searchData) {
				case 1:
					name = rs.getString("ItemName");
					price = rs.getString("ItemPrice");
					quantity = rs.getString("ItemQuantity");
					break;
				}
			}
			con.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public String getQuantity() {
		return quantity;
	}

	/** INSERT DATA */
	public static void insertData() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:db1");
			Statement st = con.createStatement();
			String nameOfItem = JOptionPane
					.showInputDialog("Enter new product name:");
			String priceOfItem = JOptionPane
					.showInputDialog("Enter new product price:");
			String quantityOfItem = JOptionPane
					.showInputDialog("Enter new product quantity:");
			int i = st
					.executeUpdate("insert into Table1(ItemName,ItemPrice,ItemQuantity) values('"
							+ nameOfItem
							+ "','"
							+ priceOfItem
							+ "','"
							+ quantityOfItem + "')");

			System.out.println("Row is added");
			con.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void deleteData() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:db1");
			Statement st = con.createStatement();
			String productCode = JOptionPane
					.showInputDialog("Enter product code of the item you want to delete:");
			int i = st.executeUpdate("delete from Table1 where Key="
					+ productCode);
			System.out.println("Row is deleted");
			con.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void updateData() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:db1");
			Statement st = con.createStatement();
			String nameOfTheItem = "Charger";
			String priceOfTheItem = "50";
			String quantityOfTheItem = "15";
			String productCode = "11";
			int i = st.executeUpdate("update Table1 set ItemName='"
					+ nameOfTheItem + "', ItemPrice='" + priceOfTheItem
					+ "', ItemQuantity='" + quantityOfTheItem + "' where Key="
					+ productCode);
			System.out.println("Row is updated");
			con.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
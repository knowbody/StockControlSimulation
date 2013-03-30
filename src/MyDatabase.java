import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class MyDatabase {

	private String name;
	private String price;
	private String quantity;

	/** SEARCH DATABASE */
	public MyDatabase(int searchData) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:db1");
			Statement st = con.createStatement();

			switch (searchData) {

			/* CheckStock - case 1 */
			case 1:
				String checkCode = CheckStock.getStockNo().getText();
				if (checkCode.equals("")) {
					// do not look for the record in the database
				} else { // look for the product name
					ResultSet rs = st
							.executeQuery("SELECT * FROM Table1 WHERE Key="
									+ checkCode);

					while (rs.next()) {
						name = rs.getString("ItemName");
						price = rs.getString("ItemPrice");
						quantity = rs.getString("ItemQuantity");
					}
				}
				break;

			/* PurchaseItem - case 2 */
			case 2:
				String purchaseCode = PurchaseItem.getStockNo().getText();
				if (purchaseCode.equals("")) {
					// do not look for the record in the database
				} else { // look for the product name
					ResultSet rs = st
							.executeQuery("SELECT * FROM Table1 WHERE Key="
									+ purchaseCode);
					while (rs.next()) {
						name = rs.getString("ItemName");
						price = rs.getString("ItemPrice");
						quantity = rs.getString("ItemQuantity");
					}
				}
				break;

			/* Update Stock - add to the list - case 3 */
			case 3:
				String updateCode = UpdateStock.getStockNo().getText();
				if (updateCode.equals("")) {
					// do not look for the record in the database
				} else {
					ResultSet rs = st
							.executeQuery("SELECT * FROM Table1 WHERE Key="
									+ updateCode);
					while (rs.next()) {
						name = rs.getString("ItemName");
						price = rs.getString("ItemPrice");
						quantity = rs.getString("ItemQuantity");
					}
				}
				break;
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
					.executeUpdate("INSERT INTO Table1(ItemName,ItemPrice,ItemQuantity) VALUES('"
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
			int i = st.executeUpdate("DELETE FROM Table1 WHERE Key="
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
			int i = st.executeUpdate("UPDATE Table1 SET ItemName='"
					+ nameOfTheItem + "', ItemPrice='" + priceOfTheItem
					+ "', ItemQuantity='" + quantityOfTheItem + "' WHERE Key="
					+ productCode);
			System.out.println("Row is updated");
			con.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
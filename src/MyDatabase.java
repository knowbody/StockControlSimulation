/*
 * Author: Mateusz Zatorski
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyDatabase {

	private String code;
	private String name;
	private String price;
	private String quantity;
	private static String updatedAmount;
	private static String key;
	private static String dbAmount;

	/** GETS WHICH ACTION WILL BE PERFORMED FOR WHICH CLASS **/
	/*
	 * int whichClass: (1) - CheckStock; (2) - PurchaseItem; (3) - UpdateStock
	 * 
	 * String whichAction: ("search"); ("update"); ("insert"); ("delete")
	 */
	public MyDatabase(int whichClass, String whichAction) {
		if (whichAction.equals("search")) {
			// calls searchData() method
			switch (whichClass) {
			case 1:
				searchData(1);
				break;
			case 2:
				searchData(2);
				break;
			case 3:
				searchData(3);
				break;
			}

		} else if (whichAction.equals("update")) {
			// calls updateData() method
			updateData();
		}

		// GOING TO BE DONE SOON
		// else if (whichAction.equals("insert")) {
		// switch (whichClass) {
		// case 3:
		// break;
		// }
		//
		// } else if (whichAction.equals("delete")) {
		// switch (whichClass) {
		// case 3:
		// break;
		// }
		// }
	}

	/** SEARCH DATABASE **/
	private void searchData(int searchData) {
		// using Java 7 autoclosable feature (try-with-resources statement)
		try (Connection con = DriverManager.getConnection("jdbc:odbc:db1");
				Statement st = con.createStatement();) {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			switch (searchData) {
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
						code = rs.getString("Key");
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
						code = rs.getString("Key");
						name = rs.getString("ItemName");
						price = rs.getString("ItemPrice");
						quantity = rs.getString("ItemQuantity");
					}
				}
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	/** FIND AMOUNT IN DATABASE **/
	public static void findDbAmount() {
		try (Connection con = DriverManager.getConnection("jdbc:odbc:db1");
				Statement st = con.createStatement();) {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			ResultSet rs = st.executeQuery("SELECT * FROM Table1 WHERE Key="
					+ getKey());
			while (rs.next()) {
				dbAmount = rs.getString("ItemQuantity");
			}

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

	}

	/** UPDATE DATA **/
	public static void updateData() {
		try (Connection con = DriverManager.getConnection("jdbc:odbc:db1");
				Statement st = con.createStatement();) {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			int i = st.executeUpdate("UPDATE Table1 SET ItemQuantity='"
					+ getUpdatedAmount() + "' WHERE Key=" + getKey());

			System.out.println("Row is updated");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// /** INSERT DATA */
	// public static void insertData() {
	// try {
	// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	// Connection con = DriverManager.getConnection("jdbc:odbc:db1");
	// Statement st = con.createStatement();
	// String nameOfItem = JOptionPane
	// .showInputDialog("Enter new product name:");
	// String priceOfItem = JOptionPane
	// .showInputDialog("Enter new product price:");
	// String quantityOfItem = JOptionPane
	// .showInputDialog("Enter new product quantity:");
	// int i = st
	// .executeUpdate("INSERT INTO Table1(ItemName,ItemPrice,ItemQuantity) VALUES('"
	// + nameOfItem
	// + "','"
	// + priceOfItem
	// + "','"
	// + quantityOfItem + "')");
	//
	// System.out.println("Row is added");
	// con.close();
	// st.close();
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// }
	//
	// public static void deleteData() {
	// try {
	// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	// Connection con = DriverManager.getConnection("jdbc:odbc:db1");
	// Statement st = con.createStatement();
	// String productCode = JOptionPane
	// .showInputDialog("Enter product code of the item you want to delete:");
	// int i = st.executeUpdate("DELETE FROM Table1 WHERE Key="
	// + productCode);
	// System.out.println("Row is deleted");
	// con.close();
	// st.close();
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// }

	/********** SETTERS AND GETTERS **********/
	public String getCode() {
		return code;
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

	public static String getUpdatedAmount() {
		return updatedAmount;
	}

	public static void setUpdatedAmount(String updatedAmount) {
		MyDatabase.updatedAmount = updatedAmount;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		MyDatabase.key = key;
	}

	public static String getDbAmount() {
		return dbAmount;
	}

	public static void setDbAmount(String dbAmount) {
		MyDatabase.dbAmount = dbAmount;
	}
}
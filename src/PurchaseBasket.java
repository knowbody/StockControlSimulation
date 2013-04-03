/*
 * Author: Mateusz Zatorski
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PurchaseBasket extends JPanel implements ActionListener {
	private JTable table;
	private Vector rows, columns;
	private static DefaultTableModel tabModel;
	private JScrollPane scrollPane;
	private JButton addBtn, deleteBtn;
	private JPanel mainPanel, buttonPanel;
	private String productCode;
	private String productName;
	private String spinnerAmount;
	private static int quantityDbInt;
	private int amountInt;

	public PurchaseBasket() {
		rows = new Vector();
		columns = new Vector();
		String[] columnNames = { "Code", "Amount", "Product name",
				"Price per item" };
		addColumns(columnNames);

		// overriding DefaultTableModel, to disable editing cells
		setTabModel(new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // all cells false
			}
		});
		getTabModel().setDataVector(rows, columns);

		/** SETTING TABLE PROPERTIES **/
		table = new JTable(getTabModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(240);
		scrollPane = new JScrollPane(table);

		/** ADD and DELETE buttons **/
		buttonPanel = new JPanel();
		addBtn = new JButton("ADD TO BASKET");
		deleteBtn = new JButton("DELETE");
		deleteBtn.setEnabled(false);
		buttonPanel.add(addBtn);
		buttonPanel.add(deleteBtn);
		addBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add("Center", scrollPane);
		mainPanel.add("North", buttonPanel);
		add(mainPanel);

		setVisible(true);
	}

	/** adds/deletes items to/from table, calls totalPrice() method **/
	public void actionPerformed(ActionEvent source) {
		if (source.getSource() == (JButton) addBtn) {
			validateInput();
			totalPrice();
		} else if (source.getSource() == (JButton) deleteBtn) {
			deleteRow(table.getSelectedRow());
			totalPrice();
		}
	}

	/** validates user's input; calls checkForDuplicates() method **/
	private void validateInput() {
		productName = new MyDatabase(2, "search").getName();
		amountInt = Integer.parseInt(PurchaseItem.getAmount().getValue()
				.toString());
		if (new MyDatabase(2, "search").getQuantity() == null) {
			// if the answer from db is null do not parseInt
		} else {
			quantityDbInt = Integer.parseInt(new MyDatabase(2, "search")
					.getQuantity());
		}

		// checks if the entered code exists in db
		if (productName == null) {
			PurchaseItem.getErrorMsg().setText("Enter correct code");

			// checks if the user chose the amount
		} else if (amountInt == 0) {
			PurchaseItem.getErrorMsg().setText("Please enter correct amount");

			// checks if the amount of product is available in db
		} else if (quantityDbInt < amountInt) {
			PurchaseItem.getErrorMsg().setText(
					"Only " + quantityDbInt + " item(s) available.");

			// calls checkForDuplicates() method, makes amount value 0,
			// and 'code' field empty, enables delete button
		} else {
			checkForDuplicates();
			PurchaseItem.getAmount().setValue(0);
			PurchaseItem.getStockNo().setText("");
			deleteBtn.setEnabled(true);
		}
	}

	/** CHECKS FOR DUPLICATE PRODUCTS IN JTable **/
	private void checkForDuplicates() {
		boolean duplicate = false;
		for (int i = 0; i < getTabModel().getRowCount(); i++)
			// when item already on the list update the amount
			if (getTabModel().getValueAt(i, 0).equals(
					PurchaseItem.getStockNo().getText())) {
				int currentAmount = Integer.parseInt((String) getTabModel()
						.getValueAt(i, 1));
				int newAmount = currentAmount + amountInt;
				// checks if the amount of items in stock < purchased items
				if (quantityDbInt < newAmount) {
					PurchaseItem.getErrorMsg().setText(
							"Only " + quantityDbInt + " item(s) available.");
					duplicate = true;
					break;
				}
				// updates amount value in the table
				getTabModel().setValueAt(Integer.toString(newAmount), i, 1);
				duplicate = true;
				PurchaseItem.getErrorMsg().setText("");
				break;
			}

		// if item is not duplicated add it to the list
		if (!duplicate) {
			addRow();
			PurchaseItem.getErrorMsg().setText("");
		}
	}

	/** CALCULATES TOTAL PRICE **/
	private void totalPrice() {
		double totalPrice = 0;
		// nothing in the table then totalPrice field is empty
		if (getTabModel().getRowCount() == 0) {
			PurchaseItem.getTotalPrice().setText("");
		} else {
			// not empty then calculate totalPrice of all items in the table
			for (int i = 0; i < getTabModel().getRowCount(); i++) {
				totalPrice += Double.parseDouble((String) getTabModel()
						.getValueAt(i, 1))
						* Double.parseDouble((String) getTabModel().getValueAt(
								i, 3));
				PurchaseItem.getTotalPrice().setText("" + totalPrice);
			}
		}
	}
	
	/** UPDATES DB AFTER PURCHASING ALL OF THE ITEMS **/
	public static void updateDatabase() {
		for (int i = 0; i < getTabModel().getRowCount(); i++) {
			// use 'Code' column to say which record in db will be updated
			MyDatabase.setKey(getTabModel().getValueAt(i, 0).toString());
			// get the amount of particular product from db
			MyDatabase.findDbAmount();
			int dbAmountInt = Integer.parseInt(MyDatabase.getDbAmount());

			String purchasedAmountStr = getTabModel().getValueAt(i, 1)
					.toString();
			int purchasedAmount = Integer.parseInt(purchasedAmountStr);
			// calculates final amount after purchasing
			int valueToUpdate = dbAmountInt - purchasedAmount;

			// use valueToUpdate to update values in db
			MyDatabase.setUpdatedAmount("" + valueToUpdate);

			new MyDatabase(3, "update");
		}
	}

	private void addColumns(String[] colName) {
		for (int i = 0; i < colName.length; i++)
			columns.addElement(colName[i]);
	}

	private void addRow() {
		Vector r = new Vector();
		r = createNewElement();
		getTabModel().addRow(r);
	}

	private Vector createNewElement() {
		Vector t = new Vector();
		spinnerAmount = PurchaseItem.getAmount().getValue().toString();
		// read from database
		productName = new MyDatabase(2, "search").getName();
		productCode = new MyDatabase(2, "search").getCode();
		String productPrice = new MyDatabase(2, "search").getPrice().toString();
		t.addElement(productCode);
		t.addElement(spinnerAmount);
		t.addElement(productName);
		t.addElement(productPrice);
		return t;
	}

	private void deleteRow(int index) {
		table.changeSelection(0, 0, false, false);
		int size = getTabModel().getRowCount();
		// can't delete when no items
		if (size == 0) {
			deleteBtn.setEnabled(false);
		}
		// at least one row in table
		if (index != -1) {
			getTabModel().removeRow(index);
		}
	}

	/********** GETTERS AND SETTERS **********/
	public static DefaultTableModel getTabModel() {
		return tabModel;
	}

	public static void setTabModel(DefaultTableModel tabModel) {
		PurchaseBasket.tabModel = tabModel;
	}
}
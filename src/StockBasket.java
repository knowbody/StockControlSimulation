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

public class StockBasket extends JPanel implements ActionListener {
	private JTable table;
	private Vector rows, columns;
	private static DefaultTableModel tabModel;
	private JScrollPane scrollPane;
	private JButton addBtn, deleteBtn;
	private JPanel mainPanel, buttonPanel;
	private String productCode;
	private String productName;
	private String spinnerAmount;
	private int quantityDbInt;
	private int amountInt;

	public StockBasket() {
		rows = new Vector();
		columns = new Vector();
		String[] columnNames = { "Code", "Amount", "Product name",
				"Total in stock" };
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
		table.changeSelection(0, 0, false, false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(45);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		scrollPane = new JScrollPane(table);

		/** ADD and DELETE buttons **/
		buttonPanel = new JPanel();
		addBtn = new JButton("ADD TO UPDATE");
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
		} else if (source.getSource() == (JButton) deleteBtn) {
			deleteRow(table.getSelectedRow());
		}
	}

	/** validates user's input; calls checkForDuplicates() method **/
	private void validateInput() {
		productName = new MyDatabase(3, "search").getName();
		amountInt = Integer.parseInt(UpdateStock.getAmount().getValue()
				.toString());
		if (new MyDatabase(3, "search").getQuantity() == null) {
			// if the answer from db is null do not parseInt
		} else {
			quantityDbInt = Integer.parseInt(new MyDatabase(3, "search")
					.getQuantity());
		}

		// checks if the entered code exists in db
		if (productName == null) {
			UpdateStock.getErrorMsg().setText("Enter correct code");

			// checks if the user chose the amount
		} else if (amountInt == 0) {
			UpdateStock.getErrorMsg().setText("Please enter correct amount");

			// calls checkForDuplicates() method, makes amount value 0,
			// and 'code' field empty, enables delete button
		} else {
			checkForDuplicates();
			UpdateStock.getAmount().setValue(0);
			UpdateStock.getStockNo().setText("");
			deleteBtn.setEnabled(true);
		}
	}

	/** CHECKS FOR DUPLICATE PRODUCTS IN JTable **/
	private void checkForDuplicates() {
		boolean duplicate = false;
		for (int i = 0; i < getTabModel().getRowCount(); i++)
			// when item already on the list update the amount
			if (getTabModel().getValueAt(i, 0).equals(
					UpdateStock.getStockNo().getText())) {
				int currentAmount = Integer.parseInt((String) getTabModel()
						.getValueAt(i, 1));
				int newAmount = currentAmount + amountInt;
				int currentInStock = Integer.parseInt((String) getTabModel()
						.getValueAt(i, 3));
				int newStock = currentInStock + amountInt;
				// updates amount value in the table
				getTabModel().setValueAt(Integer.toString(newAmount), i, 1);
				// updates total stock amount value in the table
				getTabModel().setValueAt(Integer.toString(newStock), i, 3);
				duplicate = true;
				UpdateStock.getErrorMsg().setText("");
				break;
			}

		// if item doesn't exist on the list add it to the list
		if (!duplicate) {
			addRow();
			UpdateStock.getErrorMsg().setText("");
		}
	}

	/** CALCULATES TOTAL PRICE **/
	private void totalPrice() {
		double totalPrice = 0;
		// nothing in the table then totalPrice empty
		if (getTabModel().getRowCount() == 0) {
			UpdateStock.getTotalPrice().setText("");
		} else {
			// not empty then calculate totalPrice of all items in the table
			for (int i = 0; i < getTabModel().getRowCount(); i++) {
				totalPrice += Double.parseDouble((String) getTabModel()
						.getValueAt(i, 1))
						* Double.parseDouble((String) getTabModel().getValueAt(
								i, 3));
				UpdateStock.getTotalPrice().setText("" + totalPrice);
			}
		}
	}

	/** UPDATES DB AFTER PURCHASING ALL OF THE ITEMS **/
	public static void updateDatabase() {
		for (int i = 0; i < getTabModel().getRowCount(); i++) {
			// use 'Code' column to say which record in db will be updated
			MyDatabase.setKey(getTabModel().getValueAt(i, 0).toString());
			// use 'Total in stock' column to update values in db
			MyDatabase.setUpdatedAmount(getTabModel().getValueAt(i, 3)
					.toString());
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
		spinnerAmount = UpdateStock.getAmount().getValue().toString();
		int spinnerAmountInt = Integer.parseInt(spinnerAmount);
		// read product name from database
		productName = new MyDatabase(3, "search").getName();
		productCode = new MyDatabase(3, "search").getCode();
		// itemsInStock - get value from db and add amount added by user
		int itemsInStock = quantityDbInt + spinnerAmountInt;
		String itemsInStockStr = "" + itemsInStock;
		t.addElement(productCode);
		t.addElement(spinnerAmount);
		t.addElement(productName);
		t.addElement(itemsInStockStr);
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
		StockBasket.tabModel = tabModel;
	}
}
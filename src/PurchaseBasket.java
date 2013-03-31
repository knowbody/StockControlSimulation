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
	private DefaultTableModel tabModel;
	private JScrollPane scrollPane;
	private JButton addBtn, deleteBtn;
	private JPanel mainPanel, buttonPanel;
	private String productCode;
	private String productName;
	private String spinnerAmount;
	private int quantityDbInt;
	private int amountInt;

	public PurchaseBasket() {
		rows = new Vector();
		columns = new Vector();
		String[] columnNames = { "Code", "Amount", "Product name", "Price per item" };
		addColumns(columnNames);

		// overriding DefaultTableModel, all cells not editable
		tabModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tabModel.setDataVector(rows, columns);

		
		table = new JTable(tabModel);
		table.changeSelection(0, 0, false, false);
		scrollPane = new JScrollPane(table);// ScrollPane
		

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

	public void actionPerformed(ActionEvent source) {
		if (source.getSource() == (JButton) addBtn) {
			addBtnChecks();
			totalPrice();
		} else if (source.getSource() == (JButton) deleteBtn) {
			deleteRow(table.getSelectedRow());
			totalPrice();
		}
	}

	private void addBtnChecks() {
		validateInput();
	}

	/**
	 * validating user's input: (a) if product code is correct (b) if purchased
	 * amount is <= than quantity in db (c) if the amount was entered (d) if
	 * everything okay it calls checkForDuplicates() method
	 **/
	private void validateInput() {
		productName = new MyDatabase(2).getName();
		amountInt = Integer.parseInt(PurchaseItem.getAmount().getValue()
				.toString());
		if (new MyDatabase(2).getQuantity() == null) {
			// if the answer from db is null do not parseInt
		} else {
			quantityDbInt = Integer.parseInt(new MyDatabase(2).getQuantity());
		}
		if (productName == null) {
			PurchaseItem.getErrorMsg().setText("Enter correct code");
		} else if (amountInt == 0) {
			PurchaseItem.getErrorMsg().setText("Please enter correct amount");
		} else {
			checkForDuplicates();
			PurchaseItem.getAmount().setValue(0);
			PurchaseItem.getStockNo().setText("");
			deleteBtn.setEnabled(true);
		}
	}

	/** checks for duplicate products in JTable **/
	private void checkForDuplicates() {
		boolean updated = false;
		for (int i = 0; i < tabModel.getRowCount(); i++)
			// when item already on the list update the amount on the list
			if (tabModel.getValueAt(i, 1).equals(productName.toString())) {
				int currentAmount = Integer.parseInt((String) tabModel
						.getValueAt(i, 0));
				int newAmount = currentAmount + amountInt;
				// check if the amount of items in stock < purchased items
				if (quantityDbInt < newAmount) {
					PurchaseItem.getErrorMsg().setText(
							"Only " + quantityDbInt + " item(s) available.");
					updated = true;
					break;
				}
				tabModel.setValueAt(Integer.toString(newAmount), i, 0);
				updated = true;
				PurchaseItem.getErrorMsg().setText("");
				break;
			}

		// if item doesn't exist on the list add it to the list
		if (!updated) {
			addRow();
			PurchaseItem.getErrorMsg().setText("");
		}
	}

	/** calculates totalPrice **/
	private void totalPrice() {
		double totalPrice = 0;
		// nothing in the table then totalPrice empty
		if (tabModel.getRowCount() == 0) {
			PurchaseItem.getTotalPrice().setText("");
		} else {
			// not empty then calculate totalPrice of all items in the table
			for (int i = 0; i < tabModel.getRowCount(); i++) {
				totalPrice += Double.parseDouble((String) tabModel.getValueAt(
						i, 1))
						* Double.parseDouble((String) tabModel.getValueAt(i, 3));
				PurchaseItem.getTotalPrice().setText("" + totalPrice);
			}
		}
	}

	private void addColumns(String[] colName) {
		for (int i = 0; i < colName.length; i++)
			columns.addElement(colName[i]);
	}

	private void addRow() {
		Vector r = new Vector();
		r = createNewElement();
		rows.addElement(r);
		table.addNotify();
	}

	private Vector createNewElement() {
		Vector t = new Vector();
		spinnerAmount = PurchaseItem.getAmount().getValue().toString();
		// read from database
		productName = new MyDatabase(2).getName();
		productCode = new MyDatabase(2).getCode();
		String productPrice = new MyDatabase(2).getPrice().toString();
		t.addElement(productCode);
		t.addElement(spinnerAmount);
		t.addElement(productName);
		t.addElement(productPrice);
		return t;
	}

	private void deleteRow(int index) {
		// makes the first row always selected
		table.changeSelection(0, 0, false, false);
		int size = tabModel.getRowCount();
		// can't delete when no items
		if (size == 1) {
			deleteBtn.setEnabled(false);
		}
		// at least one row in table
		if (index != -1) {
			rows.removeElementAt(index);
			table.addNotify();
		}
	}
}

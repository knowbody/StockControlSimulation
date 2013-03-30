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
	private DefaultTableModel tabModel;
	private JScrollPane scrollPane;
	private JButton addBtn, deleteBtn;
	private JPanel mainPanel, buttonPanel;
	private String productName;
	private String spinnerAmount;
	private int quantityDbInt;
	private int amountInt;

	public StockBasket() {
		rows = new Vector();
		columns = new Vector();
		String[] columnNames = { "Amount to update", "Product name",
				"Total in stock after update" };
		addColumns(columnNames);

		tabModel = new DefaultTableModel();
		tabModel.setDataVector(rows, columns);

		table = new JTable(tabModel);
		table.changeSelection(0, 0, false, false);
		scrollPane = new JScrollPane(table);// ScrollPane
		scrollPane.setPreferredSize(new java.awt.Dimension(500, 200));

		buttonPanel = new JPanel();
		addBtn = new JButton("ADD TO UPDATE");
		deleteBtn = new JButton("DELETE");
		deleteBtn.setEnabled(false);

		buttonPanel.add(addBtn);
		buttonPanel.add(deleteBtn);

		addBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

		mainPanel = new JPanel();
		mainPanel.setSize(450, 300);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add("Center", scrollPane);
		mainPanel.add("North", buttonPanel);
		add(mainPanel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent source) {
		if (source.getSource() == (JButton) addBtn) {
			addBtnChecks();
		} else if (source.getSource() == (JButton) deleteBtn) {
			deleteRow(table.getSelectedRow());
		}
	}

	private void addBtnChecks() {
		productName = new MyDatabase(3).getName();
		amountInt = Integer.parseInt(UpdateStock.getAmount().getValue()
				.toString());
		if (new MyDatabase(3).getQuantity() == null) {
			// if the answer from db is null do not parseInt
		} else {
			quantityDbInt = Integer.parseInt(new MyDatabase(3).getQuantity());
		}
		if (productName == null) {
			UpdateStock.getErrorMsg().setText("Enter correct code");

		} else if (amountInt == 0) {
			UpdateStock.getErrorMsg().setText("Please enter correct amount");

		} else {
			addRow();
			UpdateStock.getAmount().setValue(0);
			UpdateStock.getStockNo().setText("");
			UpdateStock.getErrorMsg().setText("");
			deleteBtn.setEnabled(true);
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
		spinnerAmount = UpdateStock.getAmount().getValue().toString();
		int spinnerAmountInt = Integer.parseInt(spinnerAmount);
		// read product name from database
		productName = new MyDatabase(3).getName();
		// itemsInStock - get value from db and add amount added by user
		int itemsInStock = quantityDbInt + spinnerAmountInt;
		t.addElement(spinnerAmount);
		t.addElement(productName);
		t.addElement(itemsInStock);
		return t;
	}

	private void deleteRow(int index) {
		// makes the first row always selected
		table.changeSelection(0, 0, false, false);
		int size = tabModel.getRowCount();
		if (size == 1) { // can't delete when no items
			deleteBtn.setEnabled(false);
		}
		if (index != -1) { // At least one Row in Table
			rows.removeElementAt(index);
			table.addNotify();
		}
	}
}

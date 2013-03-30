import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class StockUtil extends JPanel implements ActionListener, TableModelListener {
	public JFrame frame;
	public JTable table;
	public Vector rows, columns;
	public DefaultTableModel tabModel;
	public JScrollPane scrollPane;
	public JLabel lblMessage;
	public JButton cmdAdd, cmdDelete, cmdSetValue, cmdGetValue;
	public JPanel mainPanel, buttonPanel;
	
	public StockUtil() {
		rows = new Vector();
		columns = new Vector();
		String[] columnNames = {"Amount", "Product name", "Price per item"};
		addColumns(columnNames);

		tabModel = new DefaultTableModel();
		tabModel.setDataVector(rows, columns);

		table = new JTable(tabModel);
		scrollPane = new JScrollPane(table);// ScrollPane

		table.setRowSelectionAllowed(false);

		table.getModel().addTableModelListener(this);

		lblMessage = new JLabel("aaaa");

		buttonPanel = new JPanel();
		cmdAdd = new JButton("Add Row");
		cmdDelete = new JButton("Delete");
		cmdSetValue = new JButton("Set Value");
		cmdGetValue = new JButton("Get Value");

		buttonPanel.add(cmdAdd);
		buttonPanel.add(cmdDelete);
		buttonPanel.add(cmdSetValue);
		buttonPanel.add(cmdGetValue);

		cmdAdd.addActionListener(this);
		cmdDelete.addActionListener(this);
		cmdSetValue.addActionListener(this);
		cmdGetValue.addActionListener(this);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add("Center", scrollPane);
		mainPanel.add("South", buttonPanel);
		add(mainPanel);
		setVisible(true);
	}

	public void addColumns(String[] colName) {
		for (int i = 0; i < colName.length; i++)
			columns.addElement(colName[i]);
	}

	public void addRow() {
		Vector r = new Vector();
		r = createNewElement();
		rows.addElement(r);
		table.addNotify();
	}

	public Vector createNewElement() {
		Vector t = new Vector();
		t.addElement(PurchaseItem.getAmount().getValue());
		t.addElement(new MyDatabase(2).getName());
		t.addElement(new MyDatabase(2).getPrice());
		return t;
	}

	public void deleteRow(int index) {
		// At least one Row in Table
		if (index != -1) {
			rows.removeElementAt(index);
			table.addNotify();
		}
	}

	public void tableChanged(javax.swing.event.TableModelEvent source) {

		String msg = "";
		TableModel tabMod = (TableModel) source.getSource();

		switch (source.getType()) {
		case TableModelEvent.UPDATE:
			msg = "Table Value Updated for  cell "
					+ table.getSelectedRow()
					+ ","
					+ table.getSelectedColumn()
					+ "\nWhich is "
					+ table.getValueAt(table.getSelectedRow(),
							table.getSelectedColumn()).toString();
			JOptionPane.showMessageDialog(null, msg, "Table Example",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}

	public void selectCell(int row, int col) {

		if (row != -1 && col != -1) {
			table.setRowSelectionInterval(row, row);
			table.setColumnSelectionInterval(col, col);
		}
	}

	public void actionPerformed(ActionEvent source) {
		if (source.getSource() == (JButton) cmdAdd) {
			addRow();
		}
		if (source.getSource() == (JButton) cmdDelete) {
			deleteRow(table.getSelectedRow());
		}
		if (source.getSource() == (JButton) cmdSetValue) {
			String CName = JOptionPane.showInputDialog(null,
					"Enter Value to be set at Cell 0,2 ",
					"Simple Table Example", JOptionPane.INFORMATION_MESSAGE);
			if (!CName.trim().equals("") && table.getRowCount() > 0) {
				selectCell(0, 2);
				table.setValueAt(CName, 0, 2);
			}
		}
		if (source.getSource() == (JButton) cmdGetValue) {
			if (table.getRowCount() > 0) {
				String msg = "Value At cell 0,0 is "
						+ table.getValueAt(0, 0).toString();
				JOptionPane.showMessageDialog(null, msg, "Table Example",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Basket extends JPanel {

	public Basket() {
		
		Vector columnNames = new Vector();
		Vector data = new Vector();
		
		columnNames.addElement("Amount");
		columnNames.addElement("Product");
		columnNames.addElement("Price per item");

		String[] entries = {"hi","helo","mum"};
		Vector row = new Vector(columnNames.size());
		for (int i = 1; i <= entries.length; i++) {
			row.addElement(entries[i]);
		}
		data.addElement(row);
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
		};
		JTable table = new JTable(model);
		table.setRowSelectionAllowed(true);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
				
		setVisible(true);
		
	}
	
}

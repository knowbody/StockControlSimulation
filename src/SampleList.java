import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SampleList extends JPanel {

	private int identifier;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private JButton addButton;
	private JButton delButton;
	private JScrollPane listPane;
	private JPanel listPanel;
	private JPanel buttonPanel;
	DecimalFormat pounds = new DecimalFormat("£#,##0.00");

	public SampleList(int identifier) {
		this.identifier = identifier;
		setLayout(new BorderLayout());

		String[] entries = {};
		listModel = new DefaultListModel<String>();
		for (int i = 0; i < entries.length; i++)
			listModel.addElement(entries[i]);
		list = new JList<String>(listModel);
		list.setVisibleRowCount(5);

		listPane = new JScrollPane(list);
		listPanel = new JPanel();
		listPane.setPreferredSize(new java.awt.Dimension(500, 200));
		listPanel.add(listPane);
		add(listPanel);
		addButton = new JButton("ADD ITEM");
		delButton = new JButton("DELETE ITEM");
		delButton.setEnabled(false);
		addButton.addActionListener(new ItemAdder());
		delButton.addActionListener(new ItemDel());
		buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		add(buttonPanel, BorderLayout.NORTH);

		setVisible(true);
	}

	private class ItemAdder implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			String key = PurchaseItem.getStockNo().getText();
			int quantity = StockData.getQuantity(key);
			String name = StockData.getName(key);
			String amountStr = PurchaseItem.getAmount().getValue().toString();
			int amountInt = Integer.parseInt(amountStr);

			switch (identifier) {
			case 1:

				break;
			case 2:
				if (name == null) {
					PurchaseItem.getErrorMsg().setText("Enter correct code");
				} else if (amountInt == 0) {
					PurchaseItem.getErrorMsg().setText(
							"Please enter correct amount.");
				} else if (quantity < amountInt) {
					PurchaseItem.getErrorMsg().setText(
							"Only " + quantity + " item(s) available.");
				} else {
					PurchaseItem.getAmount().setValue(0);
					PurchaseItem.getStockNo().setText("");
					PurchaseItem.getErrorMsg().setText("");
					int index = listModel.getSize();
					
					listModel
							.addElement(amountStr
									+ " x "
									+ name
									+ "   ("
									+ amountStr
									+ " x "
									+ pounds.format(StockData.getPrice(key))
									+ " = "
									+ pounds.format(StockData.getPrice(key)
											* amountInt) + ")" + "     code: "
									+ key);
					
					getRootPane().invalidate();
					getRootPane().validate();
					list.setSelectedIndex(index);
					list.ensureIndexIsVisible(index);
					delButton.setEnabled(true);
				}
			}
		}
	}

	private class ItemDel implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			switch (identifier) {
			case 2:
				int index = list.getSelectedIndex();
				listModel.remove(index);
				int size = listModel.getSize();
				if (size == 0) { // Nothing in the list, disable deleting.
					delButton.setEnabled(false);
				} else { // Select an index.
					if (index == listModel.getSize()) {
						// removed item in last position
						index--;
					}
					list.setSelectedIndex(index);
					list.ensureIndexIsVisible(index);
				}
				break;

			default:
				break;
			}

		}

	}
}
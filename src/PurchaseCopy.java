/*
 * Author: Mateusz Zatorski
 * Student ID: 000738254
 *
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class PurchaseCopy extends JFrame implements ActionListener {

	private static JTextField stockNo;
	private JSpinner amount;
	private JButton purchase;
	private TextArea bill;
	DecimalFormat pounds = new DecimalFormat("£#,##0.00");

	public PurchaseCopy() {
		setLayout(new FlowLayout());
		setBounds(100, 100, 270, 360);
		setTitle("Purchase Item");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		add(new JLabel("Enter Stock Number:"));
		setStockNo(new JTextField(7));
		add(getStockNo());
		getStockNo().setEditable(false);

		add(new JLabel("Enter quantity of product:"));
		amount = new JSpinner();
		amount.setEditor(new JSpinner.DefaultEditor(amount));
		Dimension prefSize = amount.getPreferredSize();
		prefSize = new Dimension(45, prefSize.height);
		amount.setPreferredSize(prefSize);
		amount.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		add(amount);

		purchase = new JButton("Purchase");
		add(purchase);
		purchase.addActionListener(this);

		bill = new TextArea(2, 30);
		bill.setEditable(false);
		add(bill);

		add(new Keypad(2));

		setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		String key = getStockNo().getText();
		int quantity = StockData.getQuantity(key);
		String name = StockData.getName(key);
		String amountStr = amount.getValue().toString();
		int amountInt = Integer.parseInt(amountStr);

		if (name == null && amountInt <= 0) {
			bill.setText("No such item in stock.");
		} else if (name != null && amountInt <= 0) {
			bill.setText("Please enter correct quantity.");
		} else if (quantity < amountInt) {
			bill.setText("Only " + quantity + " item(s) available.");
		} else {
			StockData.update(key, -amountInt);
			bill.setText("Quantity: " + amountStr);
			bill.append("\nProduct: " + name);
			bill.append("\nTo pay: "
					+ pounds.format(StockData.getPrice(key) * amountInt));
		}
	}

	public static JTextField getStockNo() {
		return stockNo;
	}

	public void setStockNo(JTextField stockNo) {
		this.stockNo = stockNo;
	}
}

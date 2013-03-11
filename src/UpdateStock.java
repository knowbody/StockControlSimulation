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

public class UpdateStock extends JFrame implements ActionListener {

	private static JTextField stockNo;

	// private JTextField amount;
	private JSpinner amount;
	private JButton update;
	private TextArea stock;
	DecimalFormat pounds = new DecimalFormat("£#,##0.00");

	public UpdateStock() {
		setLayout(new FlowLayout());
		setBounds(100, 100, 340, 360);
		setTitle("Update Stock");
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

		update = new JButton("Update");
		add(update);
		update.addActionListener(this);

		stock = new TextArea(2, 40);
		add(stock);
		stock.setEditable(false);

		add(new Keypad(3));
		setResizable(false);
		setVisible(true);
	}

//	public void actionPerformed(ActionEvent e) {
//		String key = getStockNo().getText();
//		int quantity = StockData.getQuantity(key);
//		String name = StockData.getName(key);
//		String amountStr = amount.getValue().toString();
//		int amountInt = Integer.parseInt(amountStr);
//
//		// total amount of product in the stock
//		int amountInStock = amountInt + quantity;
//
//		if (name == null && amountInt <= 0) {
//			stock.setText("No such item in stock.");
//		} else if (name != null && amountInt <= 0) {
//			stock.setText("Please enter correct quantity.");
//		} else {
//			StockData.update(key, amountInt);
//			stock.setText("Stock has been updated.");
//			stock.append("\n" + amountInt + " items added.");
//			stock.append("\nThere is " + amountInStock + " " + name
//					+ " in the stock.");
//		}
//	}

	public static JTextField getStockNo() {
		return stockNo;
	}

	public static void setStockNo(JTextField stockNo) {
		UpdateStock.stockNo = stockNo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

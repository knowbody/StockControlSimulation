/*
 * Author: Mateusz Zatorski
 * Student ID: 000738254
 *
 */
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CheckStock extends JFrame implements ActionListener {

	private static JTextField stockNo;
	private TextArea information;
	private JButton check;
	DecimalFormat pounds = new DecimalFormat("£#,##0.00");

	public CheckStock() {
		setLayout(new FlowLayout());
		setBounds(100, 100, 280, 360);
		setTitle("Check Stock");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		add(new JLabel("Enter Stock Number:"));
		setStockNo(new JTextField(3));
		add(getStockNo());
		getStockNo().setEditable(false);

		check = new JButton("Check");
		add(check);
		check.addActionListener(this);

		information = new TextArea(2, 35);
		add(information);
		information.setEditable(false);

		add(new Keypad(1));

		setResizable(true);
		setVisible(true);
	}

	// takes whatever user input in stock number field
	// and also takes the name of the product from StockData class
	// to output information about the product: name, price, stock no.
	// or if it exists at all
	public void actionPerformed(ActionEvent e) {
		String key = getStockNo().getText();
		String name = StockData.getName(key);

		if (name == null) {
			information.setText("No such item in stock");
		} else {
			information.setText(name);
			information.append("\nPrice: "
					+ pounds.format(StockData.getPrice(key)));
			information.append("\nAmount in stock: "
					+ StockData.getQuantity(key));
		}
	}

	public static JTextField getStockNo() {
		return stockNo;
	}

	public void setStockNo(JTextField stockNo) {
		this.stockNo = stockNo;
	}
}
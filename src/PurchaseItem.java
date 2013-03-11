/*
 * Author: Mateusz Zatorski
 * Student ID: 000738254
 *
 */
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class PurchaseItem extends JFrame implements ActionListener {

	private static JTextField stockNo;
	private static JTextField errorMsg;
	private static JSpinner amount;
	private static JTextField totalPrice;
	

	private JButton checkOutBtn;
	private TextArea basket;
	DecimalFormat pounds = new DecimalFormat("£#,##0.00");

	public PurchaseItem() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel totalGUI = new JPanel();

		// JPanel with the GridBagLayout.
		// I also create a GridBagConstraints Object.

		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// For each item I create to add to the mainPanel
		// I set constraints.
		// This one is cell (0,0)
		/** ENTER CODE FIELD AND LABEL AND ERROR LABEL */
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("Enter code: "));
		setStockNo(new JTextField(3));
		panel1.add(getStockNo());
		setErrorMsg(new JTextField(20));
		panel1.add(getErrorMsg());
		getStockNo().setEditable(false);
		getErrorMsg().setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(0, 0, 0, 50);
		mainPanel.add(panel1, c);

		/** ENTER AMOUNT SPINNER AND LABEL */
		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("Enter amount: "));
		setAmount(new JSpinner());
		getAmount().setEditor(new JSpinner.DefaultEditor(getAmount()));
		Dimension prefSize = getAmount().getPreferredSize();
		prefSize = new Dimension(45, prefSize.height);
		getAmount().setPreferredSize(prefSize);
		getAmount().setModel(new SpinnerNumberModel(0, 0, 999, 1));
		panel2.add(getAmount());
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(panel2, c);

		/** KEYPAD */
		JPanel panel4 = new JPanel();
		panel4.add(new Keypad(2));
		c.gridx = 1;
		c.gridy = 3;
		mainPanel.add(panel4, c);

		/** BORDER AROUND BASKET AND TOTAL PRICE */
		JPanel panel5and6 = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 3;
		panel5and6.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("YOUR BASKET"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		mainPanel.add(panel5and6, c);

		/** BASKET AREA - ADD and DELETE BUTTONS + SHOPPING LIST */
		JPanel panel5 = new JPanel();
		panel5.add(new PurchaseBasket());
		c.gridx = 0;
		c.gridy = 1;
		panel5and6.add(panel5, c);

		/** TOTAL PRICE FIELD */
		JPanel panel6 = new JPanel();
		panel6.add(new JLabel("Total price: "));
		setTotalPrice(new JTextField(10));
		panel6.add(getTotalPrice());
		getTotalPrice().setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.NONE;
		panel5and6.add(panel6, c);

		/** CHECK OUT BUTTON */
		JPanel panel7 = new JPanel();
		checkOutBtn = new JButton("CHECK OUT");
		panel7.add(checkOutBtn);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panel7, c);

		totalGUI.add(mainPanel);

		setContentPane(totalGUI);
		pack();
		setVisible(true);
	}

	

	public void actionPerformed(ActionEvent e) {

	}

//	public void addToBasketUtil() {
//		String key = getStockNo().getText();
//		int quantity = StockData.getQuantity(key);
//		String name = StockData.getName(key);
//		String amountStr = amount.getValue().toString();
//		int amountInt = Integer.parseInt(amountStr);
//
//		if (name == null) {
//			basket.setText("Enter correct code");
//		} else if (amountInt == 0) {
//			basket.setText("Please enter correct amount.");
//		} else if (quantity < amountInt) {
//			basket.setText("Only " + quantity + " item(s) available.");
//		} else {
//			StockData.update(key, -amountInt);
//			basket.setText(amountStr + " x " + name);
//			basket.append("\t\t(" + amountStr + " x "
//					+ pounds.format(StockData.getPrice(key)));
//			basket.append(" = "
//					+ pounds.format(StockData.getPrice(key) * amountInt) + ")");
//			totalPrice.setText(pounds.format(StockData.getPrice(key)
//					* amountInt));
//
//		}
//	}

	public static JTextField getStockNo() {
		return stockNo;
	}

	public void setStockNo(JTextField stockNo) {
		this.stockNo = stockNo;
	}

	public static JSpinner getAmount() {
		return amount;
	}

	public static void setAmount(JSpinner amount) {
		PurchaseItem.amount = amount;
	}
	public static JTextField getErrorMsg() {
		return errorMsg;
	}

	public static void setErrorMsg(JTextField errorMsg) {
		PurchaseItem.errorMsg = errorMsg;
	}
	
	public static JTextField getTotalPrice() {
		return totalPrice;
	}

	public static void setTotalPrice(JTextField totalPrice) {
		PurchaseItem.totalPrice = totalPrice;
	}

}

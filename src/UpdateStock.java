/*
 * Author: Mateusz Zatorski
 */
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class UpdateStock extends JFrame implements ActionListener {

	private static JTextField stockNo;
	private static JTextField errorMsg;
	private static JSpinner amount;
	private static JTextField totalPrice;
	private JButton updateBtn;

	public UpdateStock() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Update Stock");		
		
		JPanel totalGUI = new JPanel();
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		/** ENTER CODE FIELD AND LABEL AND ERROR FIELD */
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("Enter code: "));
		setStockNo(new JTextField(3));
		panel1.add(getStockNo());
		setErrorMsg(new JTextField(20));
		panel1.add(getErrorMsg());
		getStockNo().setEditable(false);
		getErrorMsg().setEditable(false);
		// I set constraints. This one is cell (0,0)
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
		panel4.add(new Keypad(3));
		c.gridx = 1;
		c.gridy = 3;
		mainPanel.add(panel4, c);

		/** BORDER AROUND ITEMS TO UPDATE AND TOTAL PRICE */
		JPanel panel5and6 = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 3;
		panel5and6.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("ITEMS TO UPDATE"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		mainPanel.add(panel5and6, c);

		/** ITEMS TO UPDATE AREA - ADD and DELETE BUTTONS + ITEM LIST */
		JPanel panel5 = new JPanel();
		// adds Update area which is fully controlled by StockBasket class
		panel5.add(new StockBasket());
		c.gridx = 0;
		c.gridy = 1;
		panel5and6.add(panel5, c);

		/** UPDATE STOCK BUTTON */
		JPanel panel7 = new JPanel();
		updateBtn = new JButton("UPDATE STOCK");
		panel7.add(updateBtn);
		updateBtn.addActionListener(this);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panel7, c);
		
		totalGUI.add(mainPanel);
		setContentPane(totalGUI);
		pack();
		setVisible(true);
	}

	/* Update button makes changes in db */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateBtn) {
			StockBasket.updateDatabase();
		}
	}

	/********** GETTERS AND SETTERS **********/
	public static JTextField getStockNo() {
		return stockNo;
	}

	public void setStockNo(JTextField stockNo) {
		UpdateStock.stockNo = stockNo;
	}

	public static JSpinner getAmount() {
		return amount;
	}

	public static void setAmount(JSpinner amount) {
		UpdateStock.amount = amount;
	}

	public static JTextField getErrorMsg() {
		return errorMsg;
	}

	public static void setErrorMsg(JTextField errorMsg) {
		UpdateStock.errorMsg = errorMsg;
	}

	public static JTextField getTotalPrice() {
		return totalPrice;
	}

	public static void setTotalPrice(JTextField totalPrice) {
		UpdateStock.totalPrice = totalPrice;
	}
}
/*
 * Author: Mateusz Zatorski
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CheckStock extends JFrame implements ActionListener {

	private static JTextField stockNo;
	private static JTextArea information;
	private JButton check;
	DecimalFormat pounds = new DecimalFormat("£#,##0.00");

	public CheckStock() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Check Stock");

		JPanel totalGUI = new JPanel();
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("Enter Stock Number:"));
		setStockNo(new JTextField(3));
		panel1.add(getStockNo());
		getStockNo().setEditable(false);
		check = new JButton("Check");
		check.addActionListener(this);
		panel1.add(check);
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panel1, c);

		JPanel panel2 = new JPanel();
		setInformation(new JTextArea(3, 19));
		getInformation().setEditable(false);
		panel2.add(getInformation());
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panel2, c);

		JPanel panel3 = new JPanel();
		panel3.add(new Keypad(1));
		c.gridy = 2;
		mainPanel.add(panel3, c);

		totalGUI.add(mainPanel);

		setLocationRelativeTo(null);
		setContentPane(totalGUI);
		pack();
		setVisible(true);
	}

	// user inputs Product Code value; gives details, about the product,
	// validates input
	public void actionPerformed(ActionEvent e) {
		String name = (new MyDatabase(1, "search").getName());
		if (name == null) {
			information.setText("No such item in stock");
		} else {
			information.setText(name);
			information.append("\nPrice: £"
					+ (new MyDatabase(1, "search").getPrice()));
			information.append("\nAmount in stock: "
					+ (new MyDatabase(1, "search").getQuantity()));
			getStockNo().setText("");
		}
	}

	/** SETTERS AND GETTERS **/
	public static JTextField getStockNo() {
		return stockNo;
	}

	public void setStockNo(JTextField stockNo) {
		this.stockNo = stockNo;
	}

	public static JTextArea getInformation() {
		return information;
	}

	public static void setInformation(JTextArea information) {
		CheckStock.information = information;
	}
}
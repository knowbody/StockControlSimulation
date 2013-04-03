/*
 * Author: Mateusz Zatorski
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Master extends JFrame implements ActionListener {

	private JButton check;
	private JButton purchase;
	private JButton stock;

	public static void main(String[] args) {
		Master jf = new Master();
	}

	public Master() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Master");

		JPanel totalGUI = new JPanel();
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel(
				"Select an option by clicking one of the buttons below"));
		c.gridy = 0;
		mainPanel.add(panel1, c);

		JPanel panel2 = new JPanel();
		check = new JButton("Check Stock");
		panel2.add(check);
		check.addActionListener(this);

		purchase = new JButton("Purchase Item");
		panel2.add(purchase);
		purchase.addActionListener(this);

		stock = new JButton("Update Stock");
		panel2.add(stock);
		stock.addActionListener(this);
		c.gridy = 1;
		mainPanel.add(panel2, c);

		totalGUI.add(mainPanel);

		setLocationRelativeTo(null);
		setContentPane(totalGUI);
		pack();
		setVisible(true);
	}

	// check which button has been clicked by user
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == check) {
			new CheckStock();
		} else if (e.getSource() == purchase) {
			new PurchaseItem();
		} else if (e.getSource() == stock) {
			new PasswordCheck();
		}
	}
}
/*
 * Author: Mateusz Zatorski
 * Student ID: 000738254
 *
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Master extends JFrame implements ActionListener {

	private JButton check;
	private JButton purchase;
	private JButton stock;
	private JButton quit;

	public static void main(String[] args) {
		Master jf = new Master();
	}

	public Master() {
		setLayout(new FlowLayout());
		setBounds(500, 300, 415, 110);
		setTitle("Master");
		// close application only by clicking the quit button
//		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		add(new JLabel("Select an option by clicking one of the buttons below"));

		check = new JButton("Check Stock");
		add(check);
		check.addActionListener(this);

		purchase = new JButton("Purchase Item");
		add(purchase);
		purchase.addActionListener(this);

		stock = new JButton("Update Stock");
		add(stock);
		stock.addActionListener(this);

//		quit = new JButton("Exit");
//		add(quit);
//		quit.addActionListener(this);

		setResizable(false);
		setVisible(true);
	}

	// check which button has been clicked by user
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == check) {
			new CheckStock();
		} else if (e.getSource() == purchase) {
			new PurchaseItem();
		} else if (e.getSource() == stock) {
			new UpdateStock();
		} else if (e.getSource() == quit) {
			StockData.close();
			System.exit(0);
		}
	}
}

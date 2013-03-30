import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Keypad extends JPanel implements ActionListener {

	private int identifier;
	private JButton buttonU = new JButton("Undo");
	private JButton button0 = new JButton("0");
	private JButton buttonR = new JButton("Reset");
	private JButton[] buttonArray;

	public Keypad(int identifier) {

		this.identifier = identifier;
		setLayout(new GridLayout(4, 3, 5, 5));
		buttonArray = new JButton[10]; // button array 1-9
		for (int i = 1; i < buttonArray.length; i++) {
			buttonArray[i] = new JButton(String.valueOf(i));
			add(buttonArray[i]);
			buttonArray[i].addActionListener(this);
			buttonArray[i].setActionCommand(String.valueOf(i));
		}

		add(buttonU);
		buttonU.addActionListener(this);
		add(button0);
		button0.addActionListener(this);
		add(buttonR);
		buttonR.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		switch (identifier) {
		// case 1 - answer from keypad in CheckStock
		case 1:
			String c = CheckStock.getStockNo().getText();
			// reset makes JTextField and JTextArea empty
			if (e.getActionCommand().equalsIgnoreCase("Reset")) {
				CheckStock.getStockNo().setText("");
				CheckStock.getInformation().setText("");
			} else if (e.getActionCommand().equalsIgnoreCase("Undo")) {
				CheckStock.getStockNo().setText(
						c = c.substring(0, c.length() - 1));
			} else {
				CheckStock.getStockNo().setText(c + e.getActionCommand());
			}
			break;

		// case 2 - answer from keypad in PurchaseItem
		case 2:
			String p = PurchaseItem.getStockNo().getText();
			if (e.getActionCommand().equalsIgnoreCase("Reset")) {
				PurchaseItem.getStockNo().setText("");
			} else if (e.getActionCommand().equalsIgnoreCase("Undo")) {

				if (p.equalsIgnoreCase("")) {
					// if JTextField is empty don't try to delete last digit
				} else {
					PurchaseItem.getStockNo().setText(
							p = p.substring(0, p.length() - 1));
				}
			} else {
				PurchaseItem.getStockNo().setText(p + e.getActionCommand());
			}
			break;

		// case 3 - answer from keypad in UpdateStock
		case 3:
			String u = UpdateStock.getStockNo().getText();
			// reset makes JTextField empty
			if (e.getActionCommand().equalsIgnoreCase("Reset")) {
				UpdateStock.getStockNo().setText("");
			} else if (e.getActionCommand().equalsIgnoreCase("Undo")) {
				// undo deletes last character
				UpdateStock.getStockNo().setText(
						u = u.substring(0, u.length() - 1));
			} else {
				// append next number to the previous one
				UpdateStock.getStockNo().setText(u + e.getActionCommand());
			}
			break;

		// case 4 - answer from keypad in PasswordCheck
		case 4:
			char[] pass = PasswordCheck.getPass().getPassword();
			String passStr = String.valueOf(pass);
			if (e.getActionCommand().equalsIgnoreCase("Reset")) {
				PasswordCheck.getPass().setText("");
			} else if (e.getActionCommand().equalsIgnoreCase("Undo")) {
				// undo deletes last character
				PasswordCheck.getPass().setText(
						passStr = passStr.substring(0, passStr.length() - 1));
			} else {
				// append next number to the previous one
				PasswordCheck.getPass().setText(passStr + e.getActionCommand());
			}
			break;
		}
	}
}
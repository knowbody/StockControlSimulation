import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class KeypadUtil extends JPanel {

	protected int identifier;
	protected JButton buttonU = new JButton("Undo");
	protected JButton button0 = new JButton("0");
	protected JButton buttonR = new JButton("Reset");

	public KeypadUtil() {
		super();
	}

	public KeypadUtil(LayoutManager layout) {
		super(layout);
	}

	public KeypadUtil(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public KeypadUtil(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public void actionPerformed(ActionEvent e) {
		switch (identifier) {
		// case 1 - answer from keypad in CheckStock
		case 1:
			String c = CheckStock.getStockNo().getText();
			if (e.getActionCommand().equalsIgnoreCase("Reset")) {
				CheckStock.getStockNo().setText(""); // reset makes JTextField
														// empty
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
			if (e.getActionCommand().equalsIgnoreCase("Reset")) {
				UpdateStock.getStockNo().setText(""); // reset makes JTextField
														// empty
			} else if (e.getActionCommand().equalsIgnoreCase("Undo")) {
				// undo deletes last character
				UpdateStock.getStockNo().setText(
						u = u.substring(0, u.length() - 1));
			} else {
				// append next number to the previous one
				UpdateStock.getStockNo().setText(u + e.getActionCommand());
			}
			break;

		}
	}

}
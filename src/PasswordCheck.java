import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class PasswordCheck extends JFrame implements ActionListener {

	private JLabel passLbl;
	private static JPasswordField pass;
	private JButton okayBtn;

	public PasswordCheck() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("User validation");
		JPanel totalGUI = new JPanel();
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel panel1 = new JPanel();
		passLbl = new JLabel("Enter password: ");
		panel1.add(passLbl);
		setPass(new JPasswordField(4));
		getPass().setEditable(false);
		panel1.add(getPass());
		okayBtn = new JButton("OK");
		okayBtn.addActionListener(this);
		panel1.add(okayBtn);
		c.gridy = 0;
		mainPanel.add(panel1, c);

		JPanel panel2 = new JPanel();
		panel2.add(new Keypad(4));
		c.gridy = 1;
		mainPanel.add(panel2, c);

		totalGUI.add(mainPanel);

		setContentPane(totalGUI);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okayBtn) {
			char[] enteredPassword = getPass().getPassword();
			char[] correctPassword = { '1', '2', '3', '4' };

			if (Arrays.equals(correctPassword, enteredPassword)) {
				new UpdateStock();
			} else {
				JOptionPane.showMessageDialog(null, "Wrong password");
			}
			/*
			 * Best practice says that passwords should be stored in char
			 * arrays, and then zero-out the array when you're done. This will
			 * ensure that the password does not stick around in memory. Even if
			 * the array is garbage collected, the data is still in memory. It
			 * is not removed until either the computer shuts down or the data
			 * is overwritten.
			 */
			Arrays.fill(enteredPassword, '0');
		}
	}

	public static JPasswordField getPass() {
		return pass;
	}

	public static void setPass(JPasswordField pass) {
		PasswordCheck.pass = pass;
	}

}

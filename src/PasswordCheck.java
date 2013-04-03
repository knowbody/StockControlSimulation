/*
 * Author: Mateusz Zatorski
 */
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

		/** PASSWORD INPUT **/
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

		/** KEYPAD **/
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

	/** VALIDATE PASSWORD **/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okayBtn) {
			char[] enteredPassword = getPass().getPassword();
			/** PASSWORD STORED IN AN ARRAY **/
			char[] correctPassword = { '1', '2', '3', '4' };
			if (Arrays.equals(correctPassword, enteredPassword)) {
				new UpdateStock();
			} else {
				JOptionPane.showMessageDialog(null, "Wrong password");
			}
			Arrays.fill(enteredPassword, '0');
			getPass().setText("");
		}
	}

	/********** GETTERS AND SETTERS **********/
	public static JPasswordField getPass() {
		return pass;
	}

	public static void setPass(JPasswordField pass) {
		PasswordCheck.pass = pass;
	}
}
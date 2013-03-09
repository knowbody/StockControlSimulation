import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class New extends JFrame implements ListSelectionListener {
	
	private JList list;
    private DefaultListModel listModel;
    public static JTextField codeField;

	public static void main(String[] args) {

		New n = new New();

	}

	public New() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel totalGUI = new JPanel();

		// JPanel with the GridBagLayout.
		// I also create a GridBagConstraints Object.

		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// For each item I create to add to the mainPanel
		// I set constraints.
		// This one is cell (0,0)
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("Enter code: "));
		codeField = new JTextField(7);
		panel1.add(codeField);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(0, 0, 0, 50);
		mainPanel.add(panel1, c);

		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("Enter amount: "));
		panel2.add(new JSpinner());
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(panel2, c);

		JPanel panel3 = new JPanel();
		panel3.add(new JButton("ADD TO BASKET"));
		panel3.add(new JButton("DELETE FROM BASKET"));
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panel3, c);

		JPanel panel4 = new JPanel();
		panel4.add(new Keypad(2));
		c.gridx = 1;
		c.gridy = 3;
		mainPanel.add(panel4, c);

		JPanel panel5and6 = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 3;
		panel5and6.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("YOUR BASKET"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		mainPanel.add(panel5and6, c);

		JPanel panel5 = new JPanel();
		panel5.add(new TextArea(null, 20, 60,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS));
		c.gridx = 0;
		c.gridy = 1;
		panel5and6.add(panel5, c);

		JPanel panel6 = new JPanel();
		panel6.add(new JLabel("Total price: "));
		panel6.add(new JTextField(10));
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.NONE;
		panel5and6.add(panel6, c);

		JPanel panel7 = new JPanel();
		panel7.add(new JButton("CHECK OUT"));
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panel7, c);
		
		JPanel panel8 = new JPanel();
		panel8.add(new SampleList(1));
		c.gridx = 0;
        c.gridy = 5;
        mainPanel.add(panel8, c);

		totalGUI.add(mainPanel);

		setContentPane(totalGUI);
		pack();
		setVisible(true);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

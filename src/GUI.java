import java.awt.*;

import javax.swing.*;

public class GUI {

	public JPanel GUI() {

		JPanel totalGUI = new JPanel();

		// We create a JPanel with the GridBagLayout.
		// We also create a GridBagConstraints Object.

		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// For each item we create to add to the mainPanel
		// we set constraints.
		// This one is cell (0,0) and will not resize.

		JPanel red = new JPanel();
		red.add(new JLabel("aaa"));
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(red, c);

		// This one is cell (1,0) and will not resize.
		JPanel blue = createSquareJPanel(Color.blue, 80);
		c.gridx = 1;
		mainPanel.add(blue, c);

		// This one is cell (2,0).
		// This should be a 40x40 square, but we set it to
		// stretch on the vertical.
		JPanel orange = createSquareJPanel(Color.orange, 40);
		c.gridx = 2;
		c.fill = GridBagConstraints.VERTICAL;
		mainPanel.add(orange, c);

		// This starts in cell (3,0)
		// but covers (3, 1) and (3,2) as it's height is 3.
		// This will stretch right down to the bottom because
		// the fill is still set to vertical.
		JPanel pink = createSquareJPanel(Color.pink, 80);
		c.gridx = 3;
		c.gridheight = 3;
		mainPanel.add(pink, c);

		// This one is in cell (0, 1)
		// It is now a simple green square.
		JPanel green = createSquareJPanel(Color.green, 80);
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		mainPanel.add(green, c);

		// This one is cell (0,2).
		// This should be a 40x40 square, but we set it to
		// stretch on the horizontal.
		JPanel yellow = createSquareJPanel(Color.yellow, 40);
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(yellow, c);

		// This starts in cell (0,3)
		// but covers (1,3) and (2,3) as it's width is 3.
		// This will stretch right along to the side because
		// the fill is still set to horizontal.
		JPanel cyan = createSquareJPanel(Color.cyan, 80);
		c.gridy = 3;
		c.gridwidth = 3;
		mainPanel.add(cyan, c);

		// This will place a JPanel at the bottom corner, small and positioned
		// to first_line_start in cell (3,3).
		JPanel black = createSquareJPanel(Color.black, 40);
		c.gridx = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.NONE;
		mainPanel.add(black, c);

		// This sets a gray JPanel in the middle.
		// it covers (1,1), (1,2), (2,1) and (2,2) because
		// of it's width and height.
		// We center it using the anchor.
		// Then make it larger by padding it out by 40px on the
		// vertical and horizontal (20px each side).
		JPanel gray = createSquareJPanel(Color.gray, 40);
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 2;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = 40;
		c.ipady = 40;
		mainPanel.add(gray, c);

		// We finish by adding the mainPanel to the totalGUI
		// and returning the JPanel to be set as the content pane.

		totalGUI.add(mainPanel);
		totalGUI.setOpaque(true);
		return totalGUI;
	}

	// In this method, we create a square JPanel of a colour and set size
	// specified by the arguments.

	private JPanel createSquareJPanel(Color color, int size) {
		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(color);
		tempPanel.setMinimumSize(new Dimension(size, size));
		tempPanel.setMaximumSize(new Dimension(size, size));
		tempPanel.setPreferredSize(new Dimension(size, size));
		return tempPanel;
	}

	private static void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("[=] GridBagLayout [=]");

		GUI demo = new GUI();
		frame.setContentPane(demo.GUI());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}

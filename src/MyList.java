import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MyList extends JPanel {

	private JList list;
	private DefaultListModel listModel;

	public static void main(String[] args) {
		MyList ml = new MyList();
	}

	public MyList() {

		setLayout(new BorderLayout());

		listModel = new DefaultListModel();
		list = new JList(listModel);
		String[] entries = new String[20];
		
		listModel.addElement("Mateusz");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		// list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);

		JButton addToBasketBtn = new JButton("ADD");
		AddToBasketListener basketListener = new AddToBasketListener(
				addToBasketBtn);
		addToBasketBtn.setActionCommand("ADD");
		addToBasketBtn.addActionListener(basketListener);
		addToBasketBtn.setEnabled(false);

		add(listScrollPane, BorderLayout.CENTER);
		setVisible(true);

	}

	// This listener is shared by the text field and the hire button.
	class AddToBasketListener implements ActionListener, DocumentListener {
		private boolean alreadyEnabled = false;
		private JButton button;

		public AddToBasketListener(JButton button) {
			this.button = button;
		}

		// Required by ActionListener.
		public void actionPerformed(ActionEvent e) {
			String nameTextField = New.codeField.getText();

			// User didn't type in a unique name...
			if (nameTextField.equals("") || alreadyInList(nameTextField)) {
				Toolkit.getDefaultToolkit().beep();
				New.codeField.requestFocusInWindow();
				New.codeField.selectAll();
				return;
			}

			int index = list.getSelectedIndex(); // get selected index
			if (index == -1) { // no selection, so insert at beginning
				index = 0;
			} else { // add after the selected item
				index++;
			}

			listModel.insertElementAt(New.codeField.getText(), index);
			// If we just wanted to add to the end, we'd do this:
			// listModel.addElement(employeeName.getText());

			// Reset the text field.
			New.codeField.requestFocusInWindow();
			New.codeField.setText("");

			// Select the new item and make it visible.
			list.setSelectedIndex(index);
			list.ensureIndexIsVisible(index);
		}

		// This method tests for string equality. You could certainly
		// get more sophisticated about the algorithm. For example,
		// you might want to ignore white space and capitalization.
		protected boolean alreadyInList(String name) {
			return listModel.contains(name);
		}

		// Required by DocumentListener.
		public void insertUpdate(DocumentEvent e) {
			enableButton();
		}

		// Required by DocumentListener.
		public void removeUpdate(DocumentEvent e) {
			handleEmptyTextField(e);
		}

		// Required by DocumentListener.
		public void changedUpdate(DocumentEvent e) {
			if (!handleEmptyTextField(e)) {
				enableButton();
			}
		}

		private void enableButton() {
			if (!alreadyEnabled) {
				button.setEnabled(true);
			}
		}

		private boolean handleEmptyTextField(DocumentEvent e) {
			if (e.getDocument().getLength() <= 0) {
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			}
			return false;
		}
	}

}

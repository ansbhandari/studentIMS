package studentsIMS;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JButton addNew;
	public JButton searchBtn;
	public JButton editBtn;

	public ButtonPanel() {
		addNew = new JButton("Add New Student");
		searchBtn = new JButton("Search For Students");
		editBtn = new JButton("Edit Records");

		setLayout(new FlowLayout());
		add(addNew);
		add(searchBtn);
		add(editBtn);
	}

}

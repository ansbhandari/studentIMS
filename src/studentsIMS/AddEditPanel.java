package studentsIMS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddEditPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblRoll;
	public JTextField tfRoll;
	JLabel lblFName;
	public JTextField tfFname;
	JLabel lblLName;
	public JTextField tfLName;

	JLabel lblFaculty;
	public JComboBox<String> cbFaculty;
	JLabel lblSemester;
	public JComboBox<String> cbSemester;

	public JButton btnAdd;
	public JButton btnReset;

	public AddEditPanel(boolean isEditMode) {
		lblRoll = new JLabel("Roll");
		tfRoll = new JTextField();
		lblFName = new JLabel("First Name");
		tfFname = new JTextField();
		lblLName = new JLabel("Last Name");
		tfLName = new JTextField();

		lblFaculty = new JLabel("Faculty");
		cbFaculty = new JComboBox<String>();
		lblSemester = new JLabel("Semester");
		cbSemester = new JComboBox<String>();

		btnAdd = new JButton(isEditMode ? "Edit" : "Add");
		btnReset = new JButton(isEditMode ? "Cancel" : "Reset");

		JPanel lebels = new JPanel();

		lebels.setLayout(new GridLayout(6, 2, 5, 5));
		lebels.add(lblRoll);
		lebels.add(tfRoll);
		lebels.add(lblFName);
		lebels.add(tfFname);
		lebels.add(lblLName);
		lebels.add(tfLName);
		lebels.add(lblFaculty);
		cbFaculty.addItem("Computer Science");
		cbFaculty.addItem("Environment Science");
		lebels.add(cbFaculty);
		lebels.add(lblSemester);
		cbSemester.addItem("First Semester");
		cbSemester.addItem("Fourth Semester");
		lebels.add(cbSemester);

		JPanel btns = new JPanel();
		btns.setLayout(new BoxLayout(btns, BoxLayout.LINE_AXIS));
		btns.add(Box.createHorizontalGlue());
		btns.add(btnAdd);
		btns.add(Box.createRigidArea(new Dimension(20, 0)));
		btns.add(btnReset);
		btns.add(Box.createHorizontalGlue());

		setLayout(new BorderLayout());
		add(lebels, BorderLayout.CENTER);
		add(btns, BorderLayout.PAGE_END);		
	}
}

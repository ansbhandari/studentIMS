package studentsIMS;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainInterface implements ActionListener {

	private ButtonPanel btnPanel;
	private AddEditPanel addPanel;
	private AddEditPanel editPanel;
	private DataTablePanel srPanel;
	private StudentIMSDB dbcon = new StudentIMSDB();

	CardLayout cl = new CardLayout();
	JPanel totalGUI;
	JPanel cpanel; //card panel

	public JPanel createContentPane() throws IOException, SQLException {
		totalGUI = new JPanel();
		btnPanel = new ButtonPanel();
		addPanel = new AddEditPanel(false);
		editPanel = new AddEditPanel(true);		
		srPanel = new DataTablePanel();
		

		addPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Add"));
		editPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Edit"));
		srPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Search"));

		cpanel = new JPanel();
		cpanel.setLayout(cl);
		cpanel.add(srPanel, "search");
		cpanel.add(addPanel, "add");
		cpanel.add(editPanel, "edit");

		totalGUI.setLayout(new BorderLayout());
		totalGUI.add(btnPanel, BorderLayout.NORTH);
		totalGUI.add(cpanel, BorderLayout.CENTER);

		btnPanel.addNew.addActionListener(this);
		btnPanel.editBtn.addActionListener(this);
		btnPanel.searchBtn.addActionListener(this);

		addPanel.btnAdd.addActionListener(this);
		addPanel.btnReset.addActionListener(this);
		
		editPanel.btnAdd.addActionListener(this);
		editPanel.btnReset.addActionListener(this);
		
		editPanel.tfRoll.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!editPanel.tfRoll.getText().isEmpty()){
					try {
						String values[] = dbcon.showItems(editPanel.tfRoll.getText());
						editPanel.tfRoll.setEditable(false);
						editPanel.tfFname.setText(values[0]);
						editPanel.tfLName.setText(values[1]);
						editPanel.cbFaculty.setToolTipText(values[2]);
						editPanel.cbSemester.setToolTipText(values[3]);
					} catch (IOException e1) {
						System.out.println("Couldn't fetch values.");
					}
				}							
			}
		});

		return totalGUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnClicked = (JButton) e.getSource();
		System.out.println("Action Listening...Clicked <" + btnClicked.getText() + "> ");

		if (btnClicked == btnPanel.editBtn) {
			cl.show(cpanel, "edit");
			
		} else if (btnClicked == btnPanel.addNew) {
			cl.show(cpanel, "add");
			
		} else if (btnClicked == btnPanel.searchBtn) {
			cl.show(cpanel, "search");
			
		} else if (btnClicked == addPanel.btnAdd) {
			String roll = addPanel.tfRoll.getText();
			String fname = addPanel.tfFname.getText();
			String lname = addPanel.tfLName.getText();
			String faculty = addPanel.cbFaculty.getSelectedItem().toString();
			String semester = addPanel.cbSemester.getSelectedItem().toString();
			if(roll.isEmpty()|fname.isEmpty()|lname.isEmpty())
				JOptionPane.showMessageDialog(null, "Fields are empty");
			else{
				try {
					dbcon.addRecord(roll, fname, lname, faculty, semester);
				} catch (SQLException | IOException e2) {
					System.out.println("Couldn't add !!");
					System.out.println(e2.getMessage());
				}				
				
				cpanel.remove(srPanel);
					
				try {
					srPanel = new DataTablePanel();
					cpanel.add(srPanel, "search");
					cl.show(cpanel, "search");
				} catch (SQLException | IOException e1) {
					System.out.println("The error may be here too..");
					e1.printStackTrace();
				}
			}			
						
		} else if (btnClicked == addPanel.btnReset) {
			addPanel.tfRoll.setText("");
			addPanel.tfFname.setText("");
			addPanel.tfLName.setText("");
			addPanel.cbFaculty.setSelectedIndex(0);
			addPanel.cbSemester.setSelectedIndex(0);

		} else if (btnClicked == editPanel.btnAdd) {
			String roll = editPanel.tfRoll.getText();
			String fname = editPanel.tfFname.getText();
			String lname = editPanel.tfLName.getText();
			String faculty = editPanel.cbFaculty.getSelectedItem().toString();
			String semester = editPanel.cbSemester.getSelectedItem().toString();
			if(roll.isEmpty()|fname.isEmpty()|lname.isEmpty())
				JOptionPane.showMessageDialog(null, "Fields are empty");
			else{
				try {
					int status = dbcon.removeRecord(roll);
					if(status == 1)
					dbcon.addRecord(roll, fname, lname, faculty, semester);
				} catch (SQLException | IOException e2) {
					System.out.println("Couldn't edit !!");
					System.out.println(e2.getMessage());
				}				
				
				cpanel.remove(srPanel);
					
				try {
					srPanel = new DataTablePanel();
					cpanel.add(srPanel, "search");
					cl.show(cpanel, "search");
				} catch (SQLException | IOException e1) {
					System.out.println("The error may be here too..");
					e1.printStackTrace();
				}
			}	

		} else if (btnClicked == editPanel.btnReset) {
			editPanel.tfRoll.setEditable(true);
			editPanel.tfRoll.setText("");
			editPanel.tfFname.setText("");
			editPanel.tfLName.setText("");
			editPanel.cbFaculty.setSelectedIndex(0);
			editPanel.cbSemester.setSelectedIndex(0);
		}
	}
	
	private static void createAndShowGUI() throws IOException, SQLException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Student IMS");
		MainInterface sims = new MainInterface();
		frame.setContentPane(sims.createContentPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
}


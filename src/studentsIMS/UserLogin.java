package studentsIMS;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UserLogin extends JFrame implements ActionListener{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btn_login, btn_cancel;
	JTextField tf_namefield;
	JPasswordField pf_passfield;
	JPanel mainPanel;
	ConnectionClass connect;
	String tableName="users_table";
	public UserLogin(String title) {
		super(title);
		btn_login = new JButton("Login");
		btn_cancel = new JButton("Cancel");
		tf_namefield = new JTextField(20);
		pf_passfield = new JPasswordField(20);

		mainPanel = new JPanel(new GridLayout(3, 1));
		mainPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Login"));
		JPanel userPanel = createUsernamePanel("Username ", tf_namefield);
		JPanel passPanel = createPasswordPanel("Password ", pf_passfield);
		JPanel buttonsPanel = createButtonsPanel();		
		mainPanel.add(userPanel);
		mainPanel.add(passPanel);
		mainPanel.add(buttonsPanel);
		add(mainPanel);
		// register action listeners
		btn_cancel.addActionListener(this);
		btn_login.addActionListener(this);
	}	
	private JPanel createUsernamePanel(String labelString, JTextField tfName) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel(labelString));
		panel.add(tfName);
		return panel;
	}
	private JPanel createPasswordPanel(String labelString, JPasswordField pfName) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel(labelString));
		panel.add(pfName);
		return panel;
	}
	
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(btn_login);
		buttonsPanel.add(btn_cancel);
		return buttonsPanel;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton btnClicked = (JButton) ae.getSource();
		if (btnClicked == btn_login) {
			String user = tf_namefield.getText();
			String pass = pf_passfield.getText();
			if (user.isEmpty()||pass.isEmpty()) {
				if (user.isEmpty()&&pass.isEmpty()){
					JOptionPane.showMessageDialog(null, "Both Field Required !", "Student IMs",	JOptionPane.ERROR_MESSAGE);
				}				
				else if(user.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Username field empty !!", "Student IMs", JOptionPane.ERROR_MESSAGE);
				}				
				else {
					JOptionPane.showMessageDialog(null,	"Password field empty !!", "Student IMs", JOptionPane.ERROR_MESSAGE);					
				}				
			} else {
				int valid = 0;
				try {
					valid = validateUser(user, pass);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(valid);
				if(valid == 1){
					this.dispose();
					JFrame.setDefaultLookAndFeelDecorated(true);
					JFrame frame = new JFrame("Student IMS");
					MainInterface sims = new MainInterface();
					try {
						frame.setContentPane(sims.createContentPane());
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect username or password.\nTry again!");
				}
			}
		}		
		else if (btnClicked == btn_cancel) {
			System.exit(0);
		}
	}	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UserLogin login = new UserLogin("PharmManager User Login:");
				login.setSize(420, 200);
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				login.setResizable(false);
			}
		});
	}	

	public int validateUser(String user, String pass) throws IOException {
		connect = new ConnectionClass();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs;
		String sql;
		String username, password;
		String hashPass = PasswordEncryption.getHashPass(pass);
		int val = 0;
		try {
			conn = connect.getConnection();
			sql = "select username, password from "+tableName;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				do {
					username = rs.getString("username");
					password = rs.getString("password");
					if(username.equals(user) && password.equals(hashPass)){
						val = 1;
					}
				} while (rs.next());						
		} catch (Exception ex) {
			System.out.println("Error:"+ex.getMessage());
		} finally {
			connect.closeConnection(conn);
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("Error on closing Prepared-Statement.");
					e.printStackTrace();
				}
			}				
		}		
		return val;
	}		
}


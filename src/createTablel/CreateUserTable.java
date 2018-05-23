package createTablel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CreateUserTable {
	ConnectionClass connect;
	public void createTable(String tableName) throws IOException {
		Connection conn = null;
		connect = new ConnectionClass();
		PreparedStatement prStmt = null;
		String createQuery = "create table "+tableName+"(username varchar(20) " +
				"not null primary key,password varchar(40),email varchar(35)," +
				"previllege varchar(15))";
		try {
			conn = connect.getConnection();
			prStmt = conn.prepareStatement(createQuery);
			prStmt.executeUpdate();
			JOptionPane.showMessageDialog(null, tableName+" created!");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			if (conn != null) {
				connect.closeConnection(conn);
			}
			if (prStmt != null) {
				try {
					prStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void insertDefaultUser(String tableName) throws IOException {
		String username, password, email, previllege;
		username = "user";
		password = "user";
		email = "user@test.com";
		previllege = "admin";
		connect = new ConnectionClass();
		Connection conn = connect.getConnection();
		PreparedStatement prStmt = null;
		String hashPass = PasswordEncryption.getHashPass(password);
		String insertQuery = "insert into "+tableName+" values(?,?,?,?)";
		try {
			prStmt = conn.prepareStatement(insertQuery);
			prStmt.setString(1, username);
			prStmt.setString(2, hashPass);
			prStmt.setString(3, email);
			prStmt.setString(4, previllege);
			prStmt.executeUpdate();
			System.out.println("values inserted.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connect.closeConnection(conn);
			if (prStmt != null) {
				try {
					prStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}

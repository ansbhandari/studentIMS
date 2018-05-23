package createTablel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class createDatabaseTable {
	ConnectionClass connect;
	public void createTable(String tableName) throws IOException {
		Connection conn = null;
		connect = new ConnectionClass();
		PreparedStatement prStmt = null;
		String createQuery = "create table "+tableName+"(roll varchar(5) not null primary key,fname varchar(20),lname varchar(20),faculty varchar(20),semester varchar(20))";
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

	public void insertData(String sTable) throws IOException {
		Connection conn = null;
		connect = new ConnectionClass();
		PreparedStatement prStmt = null;
		String insertQuery = "insert into "+sTable+" values(?,?,?,?,?)";
		conn = connect.getConnection();
		try {
			prStmt = conn.prepareStatement(insertQuery);
			prStmt.setString(1, "102");
			prStmt.setString(2, "Default");
			prStmt.setString(3, "Name");
			prStmt.setString(4, "CSIT");
			prStmt.setString(5, "Seventh");
			prStmt.executeUpdate();
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

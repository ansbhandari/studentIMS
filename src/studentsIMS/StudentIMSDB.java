package studentsIMS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class StudentIMSDB {
	ConnectionClass connect;
	String tableName = "students_table";
	
	public void addRecord(String roll, String fname, String lname, String faculty, String semester) throws SQLException, IOException {
		connect = new ConnectionClass();
		Connection conn = connect.getConnection();
		PreparedStatement ps = null;		
		String sql = "insert into "+tableName+" values (?,?,?,?,?)";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, roll);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setString(4, faculty);
			ps.setString(5, semester);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Success!");
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connect.closeConnection(conn);
			if (ps != null) {
					ps.close();
			}
		}
	}

	public int removeRecord(String roll) throws IOException {
		connect = new ConnectionClass();
		Connection conn = connect.getConnection();
		PreparedStatement ps = null;		
		String sql = "delete from "+tableName+" where roll = '"+roll+"'";
		int val = 0;
		try{
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();	
			val = 1;
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return val;		
	}

	public String[] showItems(String text) throws IOException {
		connect = new ConnectionClass();
		Connection conn = connect.getConnection();
		PreparedStatement ps = null;
		ResultSet rs;
		String sql = "select * from "+tableName+" where roll = '"+text+"'";
		String fname = null,lname = null,faculty = null,semester = null;
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();	
			if(rs.next()){
				fname = rs.getString("fname");
				lname = rs.getString("lname");
				faculty = rs.getString("faculty");
				semester = rs.getString("semester");
			}
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return new String[] {fname,lname,faculty,semester};
	}
}

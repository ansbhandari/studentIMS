package studentsIMS;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DataTablePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String[] columnNames = { "Roll No", "First Name", "Last Name", "Faculty", "Semester" };
	JTable table;
	String roll, fname, lname, faculty, semester;
	
	String tableName = "students_table";
	ConnectionClass connect;
	public void getTableData() throws IOException, SQLException {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);

		table = new JTable();
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(550, 250));
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		Connection con = null;
		connect = new ConnectionClass();
		String sql;
		PreparedStatement ps = null;
		ResultSet rs;
		try {

			con = connect.getConnection();
			sql = "select * from "+tableName;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			int i = 0;
			if (rs.next())
				do {
					roll = rs.getString("roll");
					fname = rs.getString("fname");
					lname = rs.getString("lname");
					faculty = rs.getString("faculty");
					semester = rs.getString("semester");
					model.addRow(new Object[] { roll, fname, lname, faculty, semester });
					i++;
				} while (rs.next());
			if (i < 1) {
				System.out.println("No result found !! ");
			} else {
				System.out.println(i + " Records Found !!");
			}
		} catch (Exception ex) {
			System.out.println("Error...!!");
			ex.printStackTrace();
		} finally {
			connect.closeConnection(con);
			if (ps != null) {
				ps.close();
			}
		}

		add(scroll);
	}

	public DataTablePanel() throws SQLException, IOException {
		getTableData();

	}
}

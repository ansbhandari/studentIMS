package createTablel;

import java.io.IOException;

public class CreateTables {
	public static String uTable = "users_table";
	public static String sTable = "students_table";
	public static void main(String[] args) {
		CreateUserTable utable = new CreateUserTable();
		try {
			utable.createTable(uTable);
			utable.insertDefaultUser(uTable);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		createDatabaseTable dbTable = new createDatabaseTable();
		try {
			dbTable.createTable(sTable);
			dbTable.insertData(sTable);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}

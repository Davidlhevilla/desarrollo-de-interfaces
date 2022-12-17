package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	Connection connection;
	
	public Connection getConnection () {
		String dbName = "bd_libros";
		String userName = "root";
		String password = "5dejuliO";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + dbName,
					userName,
					password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
		
	}
}

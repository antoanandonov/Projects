package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
	private static final String URL = "jdbc:mysql://localhost:3306/Library";
	private static final String USER = "root";
	public static final String PASSWORD = "";
	private static Connection connection;
	private static DBConnection DBconnection;

	private DBConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static DBConnection getDBConnection() throws ClassNotFoundException, SQLException
	{
		if(DBconnection != null)
		{
			return DBconnection;
		}
		return new DBConnection();
	}

	public static Connection getConnection()
	{
		return connection;
	}

}

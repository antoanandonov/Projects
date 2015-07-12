package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

public class PeopleDAO
{
	private Connection connection;

	public PeopleDAO()
	{
		try
		{
			DBConnection.getDBConnection();
			this.connection = DBConnection.getConnection();
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	
	public List<People> getAllPeople() throws Exception
	{
		List<People> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try
		{
			myStmt = (Statement) connection.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM `people`");

			while (myRs.next())
			{
				People tempPeople = convertRowToPeople(myRs);
				list.add(tempPeople);
			}

			return list;
		}
		finally
		{
			if(myStmt != null)
			{
				myStmt.close();
			}
			if(myRs != null)
			{
				myRs.close();
			}

		}
	}

	public List<People> searchPeople(String firstName, String lastName) throws Exception
	{
		List<People> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try
		{
			if(firstName == null){
				lastName += "%";
				myStmt = connection.prepareStatement("SELECT * FROM `people` WHERE last_name like ?");
				myStmt.setString(1, lastName);
			}else if(lastName == null){
				firstName += "%";
				myStmt = connection.prepareStatement("SELECT * FROM `people` WHERE first_name like ?");
				myStmt.setString(1, firstName);
			}else{
				firstName += "%";
				lastName += "%";
				myStmt = connection.prepareStatement("SELECT * FROM `people` WHERE `first_name` like ? OR last_name like ?");
				myStmt.setString(1, firstName);
				myStmt.setString(2, lastName);
			}

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				People tempPeople = convertRowToPeople(myRs);
				list.add(tempPeople);
			}

			return list;
		}
		finally
		{
			if(myStmt != null)
			{
				myStmt.close();
			}
			if(myRs != null)
			{
				myRs.close();
			}
		}
	}

	public void addPeople(People people) throws Exception
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `people` (first_name, last_name) VALUES (?, ?)");

			// set params
			myStmt.setString(1, people.getFirstName());
			myStmt.setString(2, people.getLastName());

			// execute SQL
			myStmt.executeUpdate();
		}
		finally
		{
			if(myStmt != null)
			{
				myStmt.close();
			}
		}

	}

	public void deletePeople(int peopleID) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("DELETE FROM `people` WHERE ID=?");

			// set param
			myStmt.setInt(1, peopleID);

			// execute SQL
			myStmt.executeUpdate();
		}
		finally
		{
			if(myStmt != null)
			{
				myStmt.close();
			}
		}
	}

	private People convertRowToPeople(ResultSet myRs) throws SQLException
	{

		int id = myRs.getInt("ID");
		String firstName = myRs.getString("first_name");
		String lastName = myRs.getString("last_name");

		People tempPeople = new People(id, firstName, lastName);

		return tempPeople;
	}
}

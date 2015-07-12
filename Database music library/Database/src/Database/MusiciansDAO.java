package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

public class MusiciansDAO
{
	private Connection connection;

	public MusiciansDAO()
	{
		try
		{
			DBConnection.getDBConnection();
			this.connection = DBConnection.getConnection();
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public List<Musicians> getAllMusicians() throws SQLException
	{
		List<Musicians> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try
		{
			myStmt = (Statement) connection.createStatement();
			myRs = myStmt.executeQuery("SELECT `musicians`.`peopleID`, `first_name`, `last_name` FROM `musicians`, `people` WHERE `musicians`.`peopleID` = `people`.`ID`;");

			while (myRs.next())
			{
				Musicians tempMusician = convertRowToMusicians(myRs);
				list.add(tempMusician);
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

	public List<Musicians> searchMusicians(String firstName) throws Exception
	{
		List<Musicians> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try
		{
			firstName += "%";
			myStmt = connection.prepareStatement("SELECT `musicians`.`peopleID`, `first_name`, last_name FROM `musicians`, `people` WHERE first_name like ? AND peopleID = people.ID;");

			myStmt.setString(1, firstName);

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				Musicians tempMusician = convertRowToMusicians(myRs);
				list.add(tempMusician);
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

	public void updateMusicians(Musicians musician) throws SQLException
	{
		PreparedStatement myStmt = null;
		try
		{

			// prepare statement
			myStmt = connection.prepareStatement("UPDATE people SET people.first_name = ?, people.last_name = ? WHERE people.ID = ( SELECT peopleID FROM musicians WHERE musicians.peopleID = ? );");

			// set params

			myStmt.setString(1, musician.getFirstName());
			myStmt.setString(2, musician.getLastName());
			myStmt.setInt(3, musician.getPeopleID());

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

	private Musicians convertRowToMusicians(ResultSet myRs) throws SQLException
	{

		int peopleID = myRs.getInt("peopleID");
		String firstName = myRs.getString("first_name");
		String lastName = myRs.getString("last_name");

		Musicians tempMusician = new Musicians(peopleID, firstName, lastName);

		return tempMusician;
	}

	public void addMusicians(Musicians musician) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `people` (first_name, last_name) VALUES (?,?)");

			// set params
			myStmt.setString(1, musician.getFirstName());
			myStmt.setString(2, musician.getLastName());

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

	public void deleteMusicians(int id) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("DELETE FROM `musicians` WHERE peopleID=?");

			// set param
			myStmt.setInt(1, id);

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

	public int selectPeopleID(String firstName, String lastName) throws SQLException
	{
		// TODO: Грешен метод, който не работи :D
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		try
		{
			firstName += "%";
			lastName += "%";
			myStmt = connection.prepareStatement("SELECT `ID` FROM `people` WHERE `first_name` like ? AND `last_name` like ? ORDER BY `ID` DESC LIMIT 1;");

			myStmt.setString(1, firstName);
			myStmt.setString(2, lastName);

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				id = myRs.getInt("ID");
			}
			return id;
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

	public boolean isExsistsPeople(String firstName, String lastName) throws SQLException
	{
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		try
		{
			firstName += "%";
			lastName += "%";
			myStmt = connection.prepareStatement("SELECT `ID` FROM `people` WHERE `first_name` like ? AND `last_name` like ? ORDER BY `ID` DESC LIMIT 1;");

			myStmt.setString(1, firstName);
			myStmt.setString(2, lastName);

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				id = myRs.getInt("ID");
			}

			if(id > 0)
			{
				return true;
			}
			return false;
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

	public void insertIntoMusicians(int id) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `musicians` (`peopleID`) VALUES (?)");

			// set params
			myStmt.setInt(1, id);

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

	public int getMusicianIDbyName(int peopleID) throws SQLException
	{

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("SELECT `musicians`.`peopleID` FROM `musicians` WHERE `peopleID` like ?");

			// set param
			myStmt.setInt(1, peopleID);

			// execute SQL
			myRs = myStmt.executeQuery();
			while (myRs.next())
			{
				id = myRs.getInt("peopleID");
			}
			return id;
		}
		finally
		{
			if(myStmt != null)
			{
				myStmt.close();
			}
		}

	}

	public List<Musicians> searchMusician(String firstName, String lastName) throws SQLException
	{
		List<Musicians> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try
		{
			if(firstName == null)
			{
				lastName += "%";
				myStmt = connection.prepareStatement("SELECT `musicians`.`peopleID`, `first_name`, last_name FROM `musicians`, `people` WHERE `last_name` like ? AND `peopleID` = `people`.`ID`;");
				myStmt.setString(1, lastName);
			}
			else if(lastName == null)
			{
				firstName += "%";
				myStmt = connection.prepareStatement("SELECT `musicians`.`peopleID`, `first_name`, last_name FROM `musicians`, `people` WHERE `first_name` like ? AND `peopleID` = `people`.`ID`;");
				myStmt.setString(1, firstName);
			}
			else
			{
				firstName += "%";
				lastName += "%";
				myStmt = connection.prepareStatement("SELECT `musicians`.`peopleID`, `first_name`, last_name FROM `musicians`, `people` WHERE `first_name` like ? OR `last_name` like ? AND `peopleID` = `people`.`ID`;");
				myStmt.setString(1, firstName);
				myStmt.setString(2, lastName);
			}

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				Musicians tempMusician = convertRowToMusicians(myRs);
				list.add(tempMusician);
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

}

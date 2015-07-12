package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

public class AuthorsDAO
{

	private Connection connection;

	public AuthorsDAO()
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

	public List<Authors> getAllAuthors() throws SQLException
	{
		List<Authors> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try
		{
			myStmt = (Statement) connection.createStatement();
			myRs = myStmt.executeQuery("SELECT `authors`.`peopleID`, `first_name`, `last_name` FROM `authors`, `people` WHERE `authors`.`peopleID` = `people`.`ID`;");

			while (myRs.next())
			{
				Authors tempAuthor = convertRowToAuthors(myRs);
				list.add(tempAuthor);
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

	public List<Authors> searchAuthors(String firstName) throws Exception
	{
		List<Authors> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try
		{
			firstName += "%";
			myStmt = connection.prepareStatement("SELECT `authors`.`peopleID`, `first_name`, last_name FROM `authors`, `people` WHERE `first_name` like ? AND `peopleID` = `people`.`ID`;");

			myStmt.setString(1, firstName);

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				Authors tempAuthor = convertRowToAuthors(myRs);
				list.add(tempAuthor);
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

	private Authors convertRowToAuthors(ResultSet myRs) throws SQLException
	{

		int peopleID = myRs.getInt("peopleID");
		String firstName = myRs.getString("first_name");
		String lastName = myRs.getString("last_name");

		Authors tempAuthor = new Authors(peopleID, firstName, lastName);

		return tempAuthor;
	}

	public void addAuthors(Authors author) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `people`" + " (first_name, last_name)" + " VALUES (?,?)");

			// set params
			myStmt.setString(1, author.getFirstName());
			myStmt.setString(2, author.getLastName());

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
			if(id>0)
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

	
	public void insertIntoAuthors(int id) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `authors` (`peopleID`) VALUES (?)");

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

	public void deleteAuthors(int id) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("DELETE FROM `authors` WHERE peopleID=?");
			// myStmt =
			// connection.prepareStatement("DELETE FROM `authors` WHERE peopleID=? TRUNCATE TABLE 'authors' GO");

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

	public void updateAuthors(Authors author) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("UPDATE people SET people.first_name = ?, people.last_name = ? WHERE people.ID = ( SELECT peopleID FROM authors WHERE authors.peopleID = ? );");

			// set params
			myStmt.setString(1, author.getFirstName());
			myStmt.setString(2, author.getLastName());
			myStmt.setInt(3, author.getPeopleID());

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

	public int getAuthorIDbyName(int peopleID) throws SQLException
	{

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("SELECT `authors`.`peopleID` FROM `authors` WHERE `peopleID` like ?");

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

	public List<Authors> searchAuthor(String firstName, String lastName) throws SQLException
	{
		List<Authors> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try
		{
			if(firstName == null)
			{
				lastName += "%";
				myStmt = connection.prepareStatement("SELECT `authors`.`peopleID`, `first_name`, last_name FROM `authors`, `people` WHERE `last_name` like ? AND `peopleID` = `people`.`ID`;");
				myStmt.setString(1, lastName);
			}
			else if(lastName == null)
			{
				firstName += "%";
				myStmt = connection.prepareStatement("SELECT `authors`.`peopleID`, `first_name`, last_name FROM `authors`, `people` WHERE `first_name` like ? AND `peopleID` = `people`.`ID`;");
				myStmt.setString(1, firstName);
			}
			else
			{
				firstName += "%";
				lastName += "%";
				myStmt = connection.prepareStatement("SELECT `authors`.`peopleID`, `first_name`, last_name FROM `authors`, `people` WHERE `first_name` like ? OR `last_name` like ? AND `peopleID` = `people`.`ID`;");
				myStmt.setString(1, firstName);
				myStmt.setString(2, lastName);
			}

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				Authors tempAuthor = convertRowToAuthors(myRs);
				list.add(tempAuthor);
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

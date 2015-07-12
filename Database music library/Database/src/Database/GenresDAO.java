package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

public class GenresDAO
{
	private Connection connection;

	public GenresDAO()
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

	public List<Genres> getAllGenres() throws SQLException
	{
		List<Genres> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try
		{
			myStmt = (Statement) connection.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM `genres`");

			while (myRs.next())
			{
				Genres tempGenre = convertRowToGenres(myRs);
				list.add(tempGenre);
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

	public List<Genres> searchGenres(String genre) throws Exception
	{
		List<Genres> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try
		{
			genre += "%";
			myStmt = connection.prepareStatement("SELECT * FROM `genres` WHERE `Genre` like ?");

			myStmt.setString(1, genre);

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				Genres tempGenre = convertRowToGenres(myRs);
				list.add(tempGenre);
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

	public void addGenres(Genres genre) throws Exception
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `genres`" + " (`Genre`)" + " VALUES (?)");

			// set params
			myStmt.setString(1, genre.getGenre());

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

	public void deleteGenres(int genreID) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("DELETE FROM `genres` WHERE ID=?");

			// set param
			myStmt.setInt(1, genreID);

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

	private Genres convertRowToGenres(ResultSet myRs) throws SQLException
	{

		int id = myRs.getInt("ID");
		String genre = myRs.getString("Genre");

		Genres tempGenre = new Genres(id, genre);

		return tempGenre;
	}

	public int getGenreIDbyName(String genre) throws SQLException
	{

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("SELECT `genres`.`ID` FROM `genres` WHERE `Genre` like ?");

			// set param
			myStmt.setString(1, genre);
			// execute SQL
			// myStmt.executeQuery();
			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				id = myRs.getInt("ID");
			}
			return id;

			// return myRs.getInt(0);
		}
		finally
		{
			if(myStmt != null)
			{
				myStmt.close();
			}
		}

	}

	public void updateGenres(Genres genre) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("UPDATE `genres` SET `Genre`=? WHERE `ID`=?");

			// set params
			myStmt.setString(1, genre.getGenre());
			myStmt.setInt(2, genre.getID());

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

}

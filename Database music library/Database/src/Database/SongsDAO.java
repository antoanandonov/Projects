package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

public class SongsDAO
{
	private Connection connection;

	public SongsDAO()
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

	public List<Songs> getAllSongs() throws SQLException
	{
		List<Songs> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try
		{
			myStmt = (Statement) connection.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM `songs`");

			while (myRs.next())
			{
				Songs tempPeople = convertRowToSongs(myRs);
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

	public List<Songs> getRate() throws SQLException
	{
		List<Songs> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try
		{
			myStmt = (Statement) connection.createStatement();
			myRs = myStmt.executeQuery("SELECT `Rate` FROM `songs`");

			while (myRs.next())
			{
				Songs tempPeople = convertRowToSongs(myRs);
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

	public List<Songs> searchSongs(String song) throws Exception
	{
		List<Songs> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try
		{
			song += "%";
			myStmt = connection.prepareStatement("SELECT * FROM `songs` WHERE `Song` like ?");

			myStmt.setString(1, song);

			myRs = myStmt.executeQuery();

			while (myRs.next())
			{
				Songs tempSong = convertRowToSongs(myRs);
				list.add(tempSong);
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

	public void addSongs(Songs song) throws Exception
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `songs` (`Song`, `genreID`, `Rate`) VALUES (?, ?, ?)");

			// set params
			myStmt.setString(1, song.getSong());
			myStmt.setInt(2, song.getGenreID());
			myStmt.setString(3, String.valueOf(song.getRate()));

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

	public void deleteSongs(int songID) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("DELETE FROM `songs` WHERE ID=?");

			// set param
			myStmt.setInt(1, songID);

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

	private Songs convertRowToSongs(ResultSet myRs) throws SQLException
	{

		int id = myRs.getInt("ID");
		String firstName = myRs.getString("song");
		int lastName = myRs.getInt("genreID");
		int rate = myRs.getInt("Rate");

		Songs tempPeople = new Songs(id, firstName, lastName, rate);

		return tempPeople;
	}

	public int getSongIDbyName(String song) throws SQLException
	{

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("SELECT `songs`.`ID` FROM `songs` WHERE `Song` like ?");

			// set param
			myStmt.setString(1, song);
			// execute SQL
			// myStmt.executeUpdate();
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

	public void insertIntoSongAuthors(int songID, int authorID) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `song_authors`(`songID`, `authorID`) VALUES (?, ?)");

			// set params
			myStmt.setInt(1, songID);
			myStmt.setInt(2, authorID);
			System.out.println("songID " + songID);
			System.out.println("authorID " + authorID);
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

	public void insertIntoSongMusicians(int songID, int musicianID) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `song_musicians`(`songID`, `musicianID`) VALUES (?, ?)");

			// set params
			myStmt.setInt(1, songID);
			myStmt.setInt(2, musicianID);

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

	public void updateSongs(Songs song) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("UPDATE `songs` SET `Song`=? WHERE `ID`=?");

			// set params
			myStmt.setString(1, song.getSong());
			myStmt.setInt(2, song.getID());

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

	public void insertGenreID(int genreID) throws SQLException
	{
		PreparedStatement myStmt = null;

		try
		{
			// prepare statement
			myStmt = connection.prepareStatement("INSERT INTO `songs`(`genreID`) VALUES (?)");

			// set params
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
}

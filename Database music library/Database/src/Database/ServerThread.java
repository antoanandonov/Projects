package Database;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ServerThread implements Runnable
{

	private Socket socket;
	private static Connection connection;

	public ServerThread(Socket socket) throws UnknownHostException
	{
		try
		{
			DBConnection.getDBConnection();
			connection = DBConnection.getConnection();
			this.socket = socket;
		}
		catch (SQLException e)
		{
			close();
			JOptionPane.showMessageDialog(null, e.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			close();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			close();
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static Connection getConnection()
	{
		return connection;
	}


	public void run()
	{

		try
		{
			if(!connection.isClosed() && !socket.isClosed())
			{
				System.out.println("ST run connection " + connection.isClosed());
				System.out.println("ST run socket " + socket.isClosed());
			}
			else
			{
				JOptionPane.showMessageDialog(null, new SQLException("Something with SQL is wrong!"), "SQLException", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (Exception e)
		{
			close();
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	private void close()
	{
		try
		{
			if(connection != null)
			{
				connection.close();
			}
			if(socket != null)
			{
				socket.close();
			}
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}

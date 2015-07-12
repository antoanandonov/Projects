package Database;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

public class Server
{
	private static final int PORT = 8888;
	private static ServerSocket ss;

//	public static void main(String[] args)
	 public Server()
	{
		try
		{
			ss = new ServerSocket(PORT);
			JOptionPane.showMessageDialog(null, "Server is running\nand expecting connection", "Info", JOptionPane.INFORMATION_MESSAGE);
			while (true)
			{
				new Thread(new ServerThread(ss.accept())).start();
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		finally
		{
			if(ss != null)
			{
				try
				{
					ss.close();
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}
}

package Database;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client
{
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8888;

//	 public static void main(String[] args)
	public Client()
	{
		try
		{
			new Thread(new ClientThread(new Socket(HOST, PORT))).start();
		}
		catch (UnknownHostException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "UnknownHostException!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "IOException!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}

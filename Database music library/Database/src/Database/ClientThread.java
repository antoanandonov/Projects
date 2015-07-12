package Database;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class ClientThread implements Runnable
{
	private Socket socket;

	public ClientThread(Socket socket)
	{
			this.socket = socket;
	}

	@Override
	public void run()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					if(isRunning())
					{
						JOptionPane.showMessageDialog(null, "Successfully connected!", "Info", JOptionPane.INFORMATION_MESSAGE);
						try
						{
							ClientWindow frame = new ClientWindow();
							frame.setVisible(true);
						}
						catch (Exception e)
						{
							close();
							JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
						finally
						{
							close();
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, new SocketException("\nCommunications link failure!\nStart your SQL Server!"), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (HeadlessException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "HeadlessException", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

	private boolean isRunning()
	{
		return socket.isConnected();
	}

	private void close()
	{
		try
		{
			if(socket != null)
			{
				socket.close();
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "IOException!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}
}

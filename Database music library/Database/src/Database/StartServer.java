package Database;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StartServer
{

	private JFrame frame;
	private JButton startServer;
	private JButton startClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					StartServer window = new StartServer();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "Something went wrong!\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartServer()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Server");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) ((dimension.getWidth()-545)/2), (int) ((dimension.getHeight()-305)/2), 545, 305);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		startServer = new JButton("");
		startServer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				new Thread(new Runnable()
				{
					public void run()
					{
						new Server();
					}
				}).start();

			}

		});
		startServer.setBounds(16, 16, 250, 250);
		startServer.setIcon(new ImageIcon("/Users/antoanandonov/Documents/Core Java/Database Project/src/Server.png"));
		frame.getContentPane().add(startServer);

		startClient = new JButton("");
		startClient.setBounds(278, 16, 250, 250);
		startClient.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				new Thread(new Runnable()
				{
					public void run()
					{
						new Client();
					}
				}).start();

			}
		});
		startClient.setIcon(new ImageIcon("/Users/antoanandonov/Documents/Core Java/Database Project/src/Client.png"));
		frame.getContentPane().add(startClient);

	}
}

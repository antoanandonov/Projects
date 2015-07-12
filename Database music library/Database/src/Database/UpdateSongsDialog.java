package Database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UpdateSongsDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField songTextField;

	private SongsDAO songsDAO;

	ClientWindow clientWindow;

	private Songs previousSong = null;
	private boolean updateMode = false;

	public UpdateSongsDialog(ClientWindow theSongClientWindow, SongsDAO theSongsDAO, Songs thePreviousSong, boolean theUpdateMode)
	{
		this();
		songsDAO = theSongsDAO;
		clientWindow = theSongClientWindow;

		previousSong = thePreviousSong;

		updateMode = theUpdateMode;

		if(updateMode)
		{
			setTitle("Update Song");

			populateGui(previousSong);
		}
	}

	private void populateGui(Songs theSong)
	{

		songTextField.setText(theSong.getSong());
	}

	public UpdateSongsDialog(ClientWindow theSongsClientWindow, SongsDAO theSongsDAO)
	{
		this(theSongsClientWindow, theSongsDAO, null, false);
	}

	/**
	 * Create the dialog.
	 */
	public UpdateSongsDialog()
	{
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setUndecorated(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.UTILITY);
		setTitle("Update Song");
		setBounds(550, 353, 270, 95);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblSong = new JLabel("Song");
			lblSong.setBounds(9, 21, 44, 15);
			lblSong.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblSong.setBackground(new Color(128, 128, 128));
			contentPanel.add(lblSong);
		}
		{
			songTextField = new JTextField();
			songTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			songTextField.setBounds(65, 15, 200, 28);
			contentPanel.add(songTextField);
			songTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(128, 128, 128));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
				okButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(songTextField.getText().equals(""))
						{
							JOptionPane.showMessageDialog(clientWindow, "Can't be null", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						saveSong();
					}
				});
				okButton.setActionCommand("Save");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void saveSong()
	{

		// get the song info from gui
		String song = songTextField.getText();

		Songs tempSong = null;

		if(updateMode)
		{
			tempSong = previousSong;

			tempSong.setSong(song);

		}
		else
		{
			tempSong = new Songs(song);
		}

		try
		{
			// save to the database
			if(updateMode)
			{

				songsDAO.updateSongs(tempSong);

			}
			else
			{
				songsDAO.addSongs(tempSong);
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			ClientWindow.refreshSongsView();

			// show success message
			JOptionPane.showMessageDialog(null, "The Song saved succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (SQLException exc)
		{
			JOptionPane.showMessageDialog(null, exc.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}

	}
}

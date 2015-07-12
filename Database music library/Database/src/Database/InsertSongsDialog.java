package Database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class InsertSongsDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField songsTextField;
	private ClientWindow clientWindow;
	private SongsDAO songsDAO;
	private GenresDAO genresDAO;
	private AuthorsDAO authorsDAO;
	private MusiciansDAO musiciansDAO;

	private List<Genres> genre;
	private List<Authors> author;
	private List<Musicians> musician;
	private String[] genres;
	private String[] authors;
	private String[] musicians;
	private String[] rate;
	private JComboBox<String> AuthorsComboBox;
	JComboBox<String> MusiciansComboBox;
	JComboBox<String> GenresComboBox;
	JComboBox<String> RateComboBox;

	/**
	 * Create the dialog.
	 */
	public InsertSongsDialog()
	{
		initialize();
	}

	private void initialize()
	{
		getContentPane().setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setUndecorated(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Insert");
		setResizable(false);
		setType(Type.UTILITY);
		try
		{
			songsDAO = new SongsDAO();
			genresDAO = new GenresDAO();
			authorsDAO = new AuthorsDAO();
			musiciansDAO = new MusiciansDAO();

			genre = genresDAO.getAllGenres();
			genres = new String[genre.size()];
			List<String> genreStr = new ArrayList<>();
			genre.stream().forEach(x -> genreStr.add(x.getGenre()));
			genreStr.toArray(genres);

			author = authorsDAO.getAllAuthors();
			authors = new String[author.size()];
			List<String> authorStr = new ArrayList<>();
			author.stream().forEach(x -> authorStr.add(x.getFirstName() + " " + x.getLastName()));
			authorStr.toArray(authors);

			musician = musiciansDAO.getAllMusicians();
			musicians = new String[musician.size()];
			List<String> musicianStr = new ArrayList<>();
			musician.stream().forEach(x -> musicianStr.add(x.getFirstName() + " " + x.getLastName()));
			musicianStr.toArray(musicians);

			rate = new String[] { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		}
		catch (SQLException exc)
		{
			JOptionPane.showMessageDialog(clientWindow, exc.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error: " + exc, "Error!", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}

		setBounds(525, 353, 360, 205);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblSong = new JLabel("Song");
			lblSong.setBounds(63, 11, 38, 15);
			lblSong.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblSong.setForeground(Color.BLACK);
			contentPanel.add(lblSong);
		}
		{
			songsTextField = new JTextField();
			songsTextField.setBounds(106, 5, 250, 27);
			songsTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(songsTextField);
			songsTextField.setColumns(10);
		}
		{
			JLabel lblAuthor = new JLabel("Author");
			lblAuthor.setBounds(48, 43, 53, 15);
			lblAuthor.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblAuthor.setForeground(Color.BLACK);
			contentPanel.add(lblAuthor);
		}
		{
			AuthorsComboBox = new JComboBox<>();
			AuthorsComboBox.setBounds(106, 37, 250, 27);
			AuthorsComboBox.setModel(new DefaultComboBoxModel<String>(authors));
			contentPanel.add(AuthorsComboBox);
		}
		{
			JLabel lblMusician = new JLabel("Musician");
			lblMusician.setBounds(40, 75, 61, 15);
			lblMusician.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblMusician.setForeground(Color.BLACK);
			contentPanel.add(lblMusician);
		}
		{
			MusiciansComboBox = new JComboBox<>();
			MusiciansComboBox.setBounds(106, 69, 250, 27);
			MusiciansComboBox.setModel(new DefaultComboBoxModel<String>(musicians));
			contentPanel.add(MusiciansComboBox);
		}
		{
			JLabel lblSelectGenre = new JLabel("Select Genre");
			lblSelectGenre.setBounds(5, 106, 96, 15);
			lblSelectGenre.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblSelectGenre.setForeground(Color.BLACK);
			contentPanel.add(lblSelectGenre);
		}
		{
			GenresComboBox = new JComboBox<String>();
			GenresComboBox.setBounds(106, 101, 250, 27);
			GenresComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			GenresComboBox.setModel(new DefaultComboBoxModel<String>(genres));
			contentPanel.add(GenresComboBox);
		}
		{
			JLabel lblRate = new JLabel("Rate");
			lblRate.setBounds(68, 139, 33, 15);
			lblRate.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lblRate);
		}
		{
			RateComboBox = new JComboBox<String>();
			RateComboBox.setBounds(106, 133, 80, 27);
			RateComboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			RateComboBox.setModel(new DefaultComboBoxModel<String>(rate));
			contentPanel.add(RateComboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
				okButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(songsTextField.getText().equals("") || GenresComboBox.getSelectedItem().toString().equals("") || AuthorsComboBox.getSelectedItem().equals("") || MusiciansComboBox.getSelectedItem().equals("") || RateComboBox.getSelectedItem().equals(""))
						{
							JOptionPane.showMessageDialog(clientWindow, "Can't be null", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						try
						{
							saveSongs();
						}
						catch (SQLException exc)
						{
							JOptionPane.showMessageDialog(clientWindow, exc.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(clientWindow, "Error saving Song: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
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

	protected void saveSongs() throws SQLException
	{

		// get the people info from gui
		String songName = songsTextField.getText();
		String author = AuthorsComboBox.getSelectedItem().toString();
		String authorFirstName = author.split(" ")[0];
		String authorLastName = author.split(" ")[1];
		String musician = MusiciansComboBox.getSelectedItem().toString();
		String musicianFirstName = musician.split(" ")[0];
		String musicianLastName = musician.split(" ")[1];
		String genre = GenresComboBox.getSelectedItem().toString();
		int rate = Integer.parseInt((String) RateComboBox.getSelectedItem());
		int genreID = genresDAO.getGenreIDbyName(genre);

		Songs tempSong = new Songs(songName, genreID, rate);

		try
		{
			// save to the database
			songsDAO.addSongs(tempSong);

			int songID = songsDAO.getSongIDbyName(songName);
			int authorID = authorsDAO.getAuthorIDbyName(authorsDAO.selectPeopleID(authorFirstName, authorLastName));
			int musicianID = musiciansDAO.getMusicianIDbyName(musiciansDAO.selectPeopleID(musicianFirstName, musicianLastName));

			songsDAO.insertIntoSongAuthors(songID, authorID);
			songsDAO.insertIntoSongMusicians(songID, musicianID);
			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			ClientWindow.refreshSongsView();

			// TODO:
			// show success message
			JOptionPane.showMessageDialog(clientWindow, "The Song was added succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error saving Song: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}

	}

}

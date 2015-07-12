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

public class UpdateGenresDialog extends JDialog
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField genreTextField;

	private GenresDAO genresDAO;

	ClientWindow clientWindow;

	private Genres previousGenre = null;
	private boolean updateMode = false;

	public UpdateGenresDialog(ClientWindow theGenreClientWindow, GenresDAO theGenresDAO, Genres thePreviousGenre, boolean theUpdateMode)
	{
		this();
		genresDAO = theGenresDAO;
		clientWindow = theGenreClientWindow;

		previousGenre = thePreviousGenre;

		updateMode = theUpdateMode;

		if(updateMode)
		{
			setTitle("Update Genres");

			populateGui(previousGenre);
		}
	}

	private void populateGui(Genres theGenre)
	{

		genreTextField.setText(theGenre.getGenre());
	}

	public UpdateGenresDialog(ClientWindow theGenresClientWindow, GenresDAO theGenresDAO)
	{
		this(theGenresClientWindow, theGenresDAO, null, false);
	}

	/**
	 * Create the dialog.
	 */
	public UpdateGenresDialog()
	{
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setUndecorated(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.UTILITY);
		setTitle("Update Genre");
		setBounds(550, 353, 270, 95);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblGenre = new JLabel("Genre");
			lblGenre.setBounds(9, 21, 44, 15);
			lblGenre.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblGenre.setBackground(new Color(128, 128, 128));
			contentPanel.add(lblGenre);
		}
		{
			genreTextField = new JTextField();
			genreTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			genreTextField.setBounds(65, 15, 200, 28);
			contentPanel.add(genreTextField);
			genreTextField.setColumns(10);
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
						if(genreTextField.getText().equals(""))
						{
							JOptionPane.showMessageDialog(clientWindow, "Can't be null", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
						saveGenre();
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

	protected void saveGenre()
	{

		// get the genre info from gui
		String genre = genreTextField.getText();

		Genres tempGenre = null;

		if(updateMode)
		{
			tempGenre = previousGenre;

			tempGenre.setGenre(genre);

		}
		else
		{
			tempGenre = new Genres(genre);
		}

		try
		{
			// save to the database
			if(updateMode)
			{

				genresDAO.updateGenres(tempGenre);

			}
			else
			{
				genresDAO.addGenres(tempGenre);
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			ClientWindow.refreshGenresView();

			// show success message
			JOptionPane.showMessageDialog(null, "The Genre saved succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (SQLException exc)
		{
			JOptionPane.showMessageDialog(null, exc.getMessage(), "SQLException!", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error!", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}

	}
}

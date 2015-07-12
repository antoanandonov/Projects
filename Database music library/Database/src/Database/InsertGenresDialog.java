package Database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class InsertGenresDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField genreTextField;
	private ClientWindow clientWindow;
	private GenresDAO genresDAO;

	/**
	 * Create the dialog.
	 */
	public InsertGenresDialog()
	{
		getContentPane().setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setType(Type.UTILITY);
		setUndecorated(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Insert");
		setResizable(false);
		try
		{
			genresDAO = new GenresDAO();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		setBounds(550, 353, 265, 85);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblGenre = new JLabel("Genre");
			lblGenre.setBounds(9, 16, 44, 15);
			lblGenre.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblGenre.setForeground(Color.BLACK);
			contentPanel.add(lblGenre);
		}
		{
			genreTextField = new JTextField();
			genreTextField.setBounds(57, 10, 200, 27);
			genreTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(genreTextField);
			genreTextField.setColumns(10);
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
						if(genreTextField.getText().equals(""))
						{
							JOptionPane.showMessageDialog(clientWindow, "Can't be NULL!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						saveGenres();
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

	protected void saveGenres()
	{

		// get the people info from gui
		String genreName = genreTextField.getText();

		Genres genre = new Genres(genreName);

		try
		{
			// save to the database
			genresDAO.addGenres(genre);

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			ClientWindow.refreshGenresView();

			// TODO:
			// show success message
			JOptionPane.showMessageDialog(clientWindow, "The genre was added succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error saving Genre: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}
}

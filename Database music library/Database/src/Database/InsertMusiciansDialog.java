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

public class InsertMusiciansDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private ClientWindow clientWindow;
	private MusiciansDAO musiciansDAO;
	private JTextField lastNameTextField;

	/**
	 * Create the dialog.
	 */
	public InsertMusiciansDialog()
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
			musiciansDAO = new MusiciansDAO();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		setBounds(550, 353, 290, 110);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblMusicianFirstName = new JLabel("First Name");
			lblMusicianFirstName.setBounds(5, 11, 75, 15);
			lblMusicianFirstName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblMusicianFirstName.setForeground(Color.BLACK);
			contentPanel.add(lblMusicianFirstName);
		}
		{
			firstNameTextField = new JTextField();
			firstNameTextField.setBounds(85, 5, 200, 27);
			firstNameTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(firstNameTextField);
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(8, 43, 72, 15);
			lblLastName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lblLastName);
		}
		{
			lastNameTextField = new JTextField();
			lastNameTextField.setBounds(85, 37, 200, 27);
			lastNameTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lastNameTextField);
			lastNameTextField.setColumns(10);
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
						if(firstNameTextField.getText().equals("") && lastNameTextField.getText().equals(""))
						{
							JOptionPane.showMessageDialog(clientWindow, "Can't be null", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(firstNameTextField.getText().equals(""))
						{
							firstNameTextField.setText("NULL");
						}
						else if(lastNameTextField.getText().equals(""))
						{
							lastNameTextField.setText("NULL");
						}
						try
						{
							saveMusicians();
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(clientWindow, "Error saving Musician: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

	protected void saveMusicians()
	{

		// get the people info from gui
		String musicianFirstName = firstNameTextField.getText();
		String musicianLastName = lastNameTextField.getText();

		Musicians musician = new Musicians(musicianFirstName, musicianLastName);

		try
		{
			// check isInTablePeople
			if(musiciansDAO.isExsistsPeople(musicianFirstName, musicianLastName))
			{
				// save to the database
				// musiciansDAO.addMusicians(musician);
				musiciansDAO.insertIntoMusicians(musiciansDAO.selectPeopleID(musicianFirstName, musicianLastName));
				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				ClientWindow.refreshMusiciansView();

				// TODO:
				// show success message
				JOptionPane.showMessageDialog(clientWindow, "The Musician was added succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);

			}
			else
			{
				// save to the database
				musiciansDAO.addMusicians(musician);
				musiciansDAO.insertIntoMusicians(musiciansDAO.selectPeopleID(musicianFirstName, musicianLastName));
				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				ClientWindow.refreshMusiciansView();

				// TODO:
				// show success message
				JOptionPane.showMessageDialog(clientWindow, "The Musician was added succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);

			}
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error saving Musician: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}
}

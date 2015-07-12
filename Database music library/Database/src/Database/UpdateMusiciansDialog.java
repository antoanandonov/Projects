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

public class UpdateMusiciansDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;

	private MusiciansDAO musiciansDAO;

	ClientWindow clientWindow;

	private Musicians previousMusician = null;
	private boolean updateMode = false;

	public UpdateMusiciansDialog(ClientWindow theMusicianClientWindow, MusiciansDAO theMusiciansDAO, Musicians thePreviousMusician, boolean theUpdateMode)
	{
		this();
		musiciansDAO = theMusiciansDAO;
		clientWindow = theMusicianClientWindow;

		previousMusician = thePreviousMusician;

		updateMode = theUpdateMode;

		if(updateMode)
		{
			setTitle("Update Musicians");

			populateGui(previousMusician);
		}
	}

	private void populateGui(Musicians theMusician)
	{

		firstNameTextField.setText(theMusician.getFirstName());
		lastNameTextField.setText(theMusician.getLastName());
	}

	public UpdateMusiciansDialog(ClientWindow theMusiciansClientWindow, MusiciansDAO theMusiciansDAO)
	{
		this(theMusiciansClientWindow, theMusiciansDAO, null, false);
	}

	/**
	 * Create the dialog.
	 */
	public UpdateMusiciansDialog()
	{
		setUndecorated(true);
		setType(Type.UTILITY);
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setTitle("Update Musician");
		setBounds(550, 353, 325, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		{
			JLabel lblFirstName = new JLabel("First Name");
			lblFirstName.setBounds(24, 16, 75, 15);
			lblFirstName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lblFirstName);
		}
		{
			firstNameTextField = new JTextField();
			firstNameTextField.setBounds(111, 10, 203, 27);
			firstNameTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(firstNameTextField);
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(27, 48, 72, 15);
			lblLastName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lblLastName);
		}
		{
			lastNameTextField = new JTextField();
			lastNameTextField.setBounds(111, 42, 203, 27);
			lastNameTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lastNameTextField);
			lastNameTextField.setColumns(10);
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
						if(firstNameTextField.getText().equals("") && lastNameTextField.getText().equals(""))
						{
							JOptionPane.showMessageDialog(clientWindow, "Can't be NULL", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
						updateMusician();
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

	protected void updateMusician()
	{
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();

		Musicians tempMusician = null;

		if(updateMode)
		{
			tempMusician = previousMusician;
			tempMusician.setFirstName(firstName);
			tempMusician.setLastName(lastName);
			tempMusician.setPeopleID(previousMusician.getPeopleID());
		}
		else
		{
			tempMusician = new Musicians(previousMusician.getPeopleID(), firstName, lastName);
		}

		try
		{
			// save to the database
			if(updateMode)
			{
				musiciansDAO.updateMusicians(tempMusician);
			}
			else
			{
				musiciansDAO.addMusicians(tempMusician);
				musiciansDAO.insertIntoMusicians(musiciansDAO.selectPeopleID(firstName, lastName));
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			ClientWindow.refreshMusiciansView();

			// show success message
			JOptionPane.showMessageDialog(null, "The Musician saved succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (SQLException exc)
		{
			JOptionPane.showMessageDialog(null, exc.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}

	}
}

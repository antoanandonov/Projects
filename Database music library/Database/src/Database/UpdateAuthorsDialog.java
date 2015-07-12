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

public class UpdateAuthorsDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;

	private AuthorsDAO authorsDAO;

	ClientWindow clientWindow;

	private Authors previousAuthor = null;
	private boolean updateMode = false;

	public UpdateAuthorsDialog(ClientWindow theAuthorClientWindow, AuthorsDAO theAuthorsDAO, Authors thePreviousAuthor, boolean theUpdateMode)
	{
		this();
		authorsDAO = theAuthorsDAO;
		clientWindow = theAuthorClientWindow;

		previousAuthor = thePreviousAuthor;

		updateMode = theUpdateMode;

		if(updateMode)
		{
			setTitle("Update Authors");

			populateGui(previousAuthor);
		}
	}

	private void populateGui(Authors theAuthor)
	{

		firstNameTextField.setText(theAuthor.getFirstName());
		lastNameTextField.setText(theAuthor.getLastName());
	}

	public UpdateAuthorsDialog(ClientWindow theAuthorsClientWindow, AuthorsDAO theAuthorsDAO)
	{
		this(theAuthorsClientWindow, theAuthorsDAO, null, false);
	}

	/**
	 * Create the dialog.
	 */
	public UpdateAuthorsDialog()
	{
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setUndecorated(true);
		setType(Type.UTILITY);
		setTitle("Update Author");
		setBounds(550, 353, 309, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblFirstName = new JLabel("First Name");
			lblFirstName.setBounds(17, 16, 75, 15);
			lblFirstName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lblFirstName);
		}
		{
			firstNameTextField = new JTextField();
			firstNameTextField.setBounds(104, 10, 195, 27);
			firstNameTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(firstNameTextField);
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(20, 48, 72, 15);
			lblLastName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(lblLastName);
		}
		{
			lastNameTextField = new JTextField();
			lastNameTextField.setBounds(104, 42, 195, 27);
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
							JOptionPane.showMessageDialog(clientWindow, "Can't be null", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
						updateAuthor();
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

	protected void updateAuthor()
	{
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();

		Authors tempAuthor = null;

		if(updateMode)
		{
			tempAuthor = previousAuthor;
			tempAuthor.setFirstName(firstName);
			tempAuthor.setLastName(lastName);
			tempAuthor.setPeopleID(previousAuthor.getPeopleID());
		}
		else
		{
			tempAuthor = new Authors(firstName, lastName);
		}

		try
		{
			// save to the database
			if(updateMode)
			{
				// TODO:
				authorsDAO.updateAuthors(tempAuthor);

			}
			else
			{
				authorsDAO.addAuthors(tempAuthor);
				authorsDAO.insertIntoAuthors(authorsDAO.selectPeopleID(firstName, lastName));
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			ClientWindow.refreshAuthorsView();

			// show success message
			JOptionPane.showMessageDialog(null, "The Author saved succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (SQLException exc)
		{
			JOptionPane.showMessageDialog(null, exc.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error!", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}

	}
}

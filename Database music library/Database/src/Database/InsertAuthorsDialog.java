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

public class InsertAuthorsDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private ClientWindow clientWindow;
	private AuthorsDAO authorsDAO;
	private JTextField lastNameTextField;

	/**
	 * Create the dialog.
	 */

	public InsertAuthorsDialog()
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
			authorsDAO = new AuthorsDAO();
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
			JLabel lblAuthorFirstName = new JLabel("First Name");
			lblAuthorFirstName.setBounds(5, 11, 75, 15);
			lblAuthorFirstName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblAuthorFirstName.setForeground(Color.BLACK);
			contentPanel.add(lblAuthorFirstName);
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
							JOptionPane.showMessageDialog(clientWindow, "Can't be NULL", "Error!", JOptionPane.ERROR_MESSAGE);
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
							saveAuthors();
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(clientWindow, "Error saving author: " + exc.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
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

	protected void saveAuthors()
	{

		// get the people info from gui
		String authorFirstName = firstNameTextField.getText();
		String authorLastName = lastNameTextField.getText();

		Authors author = new Authors(authorFirstName, authorLastName);

		try
		{
			if(authorsDAO.isExsistsPeople(authorFirstName, authorLastName))
			{
				// save to the database
				// authorsDAO.addAuthors(author);
				authorsDAO.insertIntoAuthors(authorsDAO.selectPeopleID(authorFirstName, authorLastName));
				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				ClientWindow.refreshAuthorsView();

				// TODO:
				// show success message
				JOptionPane.showMessageDialog(clientWindow, "The Author was added succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				// save to the database
				authorsDAO.addAuthors(author);
				authorsDAO.insertIntoAuthors(authorsDAO.selectPeopleID(authorFirstName, authorLastName));
				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				ClientWindow.refreshAuthorsView();

				// TODO:
				// show success message
				JOptionPane.showMessageDialog(clientWindow, "The Author was added succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
			}

		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error saving Author: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}
}

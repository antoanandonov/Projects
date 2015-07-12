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

public class InsertPeopleDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PeopleDAO peopleDAO;
	private ClientWindow clientWindow;
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;

	public InsertPeopleDialog()
	{
		getContentPane().setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		setUndecorated(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Insert");
		setType(Type.UTILITY);
		setResizable(false);
		try
		{
			peopleDAO = new PeopleDAO();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Something went wrong!\n" + exc, "Error!", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		setBounds(550, 353, 290, 105);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		contentPanel.setBackground(new Color(169, 169, 169));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblFirstName = new JLabel("First Name");
			lblFirstName.setBounds(5, 9, 75, 15);
			lblFirstName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblFirstName.setForeground(Color.BLACK);
			contentPanel.add(lblFirstName);
		}
		{
			firstNameTextField = new JTextField();
			firstNameTextField.setBounds(85, 3, 200, 27);
			firstNameTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			contentPanel.add(firstNameTextField);
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(8, 41, 72, 15);
			lblLastName.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
			lblLastName.setForeground(Color.BLACK);
			contentPanel.add(lblLastName);
		}
		{
			lastNameTextField = new JTextField();
			lastNameTextField.setBounds(85, 35, 200, 27);
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
						savePeople();
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

	protected void savePeople()
	{

		// get the people info from gui
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();

		People people = new People(firstName, lastName);

		try
		{
			// save to the database
			peopleDAO.addPeople(people);

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			ClientWindow.refreshPeopleView();

			// TODO:
			// show success message
			// JOptionPane.showMessageDialog(clientWindow,
			// "people added succesfully.", "Info",
			// JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(clientWindow, "Error saving people: " + exc.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}
}

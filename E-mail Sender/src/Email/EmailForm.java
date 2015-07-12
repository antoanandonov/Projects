package Email;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class EmailForm extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fieldFrom;
	private JPasswordField passwordField;
	private JTextField fieldTo;
	private JTextField subjectField;
	private JEditorPane textAreaMessage;
	private JButton btnAttach;
	private JButton btnNewButton;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JProgressBar progressBar;

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
					EmailForm frame = new EmailForm();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmailForm()
	{
		setTitle("Email Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(32, 11, 32, 16);
		contentPane.add(lblFrom);

		fieldFrom = new JTextField();
		fieldFrom.setBounds(69, 5, 376, 28);
		contentPane.add(fieldFrom);
		fieldFrom.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(5, 44, 59, 16);
		contentPane.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(69, 38, 376, 28);
		contentPane.add(passwordField);

		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(48, 77, 16, 16);
		contentPane.add(lblTo);

		fieldTo = new JTextField();
		fieldTo.setBounds(69, 71, 376, 28);
		contentPane.add(fieldTo);
		fieldTo.setColumns(10);

		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setBounds(18, 110, 46, 16);
		contentPane.add(lblSubject);

		subjectField = new JTextField();
		subjectField.setBounds(69, 104, 376, 28);
		contentPane.add(subjectField);
		subjectField.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 138, 438, 143);
		contentPane.add(scrollPane);

		textAreaMessage = new JEditorPane();
		textAreaMessage.setBorder(UIManager.getBorder("ScrollPane.border"));
		scrollPane.setViewportView(textAreaMessage);
		textAreaMessage.setDoubleBuffered(true);

		textField = new JTextField();
		textField.setEditable(false);
		scrollPane.setColumnHeaderView(textField);
		textField.setColumns(10);

		progressBar = new JProgressBar();
		progressBar.setBounds(134, 293, 182, 29);
		contentPane.add(progressBar);

		btnAttach = new JButton("Attach");
		btnAttach.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				// For File
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(true);

				int rVal = fileChooser.showOpenDialog(null);
				if(rVal == JFileChooser.APPROVE_OPTION)
				{
					textField.setText(fileChooser.getSelectedFile().toString());
				}

			}
		});
		btnAttach.setBounds(5, 293, 117, 29);
		contentPane.add(btnAttach);

		btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!validateFields())
				{
					return;
				}

				String sendFrom = fieldFrom.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();
				String sendTo = fieldTo.getText();
				String subject = subjectField.getText();
				String message = textAreaMessage.getText();
				String file = textField.getText();

				try
				{
					if(file.isEmpty())
					{
						new SendEmail(sendFrom, password, sendTo, subject, message).sendEmailWithoutAttachment();
						JOptionPane.showMessageDialog(contentPane, "The e-mail has been sent successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
						progressBar.setString("Done");
						progressBar.setStringPainted(true);
						clearScreen();
						
					}
					else
					{
						new SendEmail(sendFrom, password, sendTo, subject, message, file).sendEmailWithAttachment();
						JOptionPane.showMessageDialog(contentPane, "The e-mail has been sent successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
						clearScreen();
						progressBar.setString("Done");
						progressBar.setStringPainted(true);

					}
				}
				catch (AddressException e1)
				{
					JOptionPane.showMessageDialog(contentPane, "Wrong or incorrect e-mail address", "AddressException", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				catch (MessagingException e1)
				{
					JOptionPane.showMessageDialog(contentPane, "Wrong or incorrect e-mail address or password", "MessagingException", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(328, 293, 117, 29);
		contentPane.add(btnNewButton);

	}

	private boolean validateFields()
	{
		if(fieldFrom.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please enter field 'From'!", "Error", JOptionPane.ERROR_MESSAGE);
			fieldFrom.requestFocus();
			return false;
		}

		if(passwordField.getPassword().toString().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please enter field 'Password'!", "Error", JOptionPane.ERROR_MESSAGE);
			passwordField.requestFocus();
			return false;
		}

		if(fieldTo.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please enter field 'To'!", "Error", JOptionPane.ERROR_MESSAGE);
			fieldTo.requestFocus();
			return false;
		}

		if(subjectField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please enter field 'Subject'!", "Error", JOptionPane.ERROR_MESSAGE);
			subjectField.requestFocus();
			return false;
		}

		return true;
	}

	private void clearScreen()
	{
		fieldFrom.setText("");
		passwordField.setText("");
		fieldTo.setText("");
		subjectField.setText("");
		textField.setText("");
		textAreaMessage.setText("");
	}
}

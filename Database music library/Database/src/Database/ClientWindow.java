package Database;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ClientWindow extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static JPanel contentPane;
	private JTextField searchTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private static JTable table;

	private static PeopleDAO peopleDAO;
	private static SongsDAO songsDAO;
	private static GenresDAO genresDAO;
	private static AuthorsDAO authorsDAO;
	private static MusiciansDAO musiciansDAO;
	private JPanel panel_1;
	private JButton btnDelete;
	private JButton btnInsert;
	private JComboBox<String> comboBox;

	private static final String[] TABLES = new String[] { "", "Songs", "Genres", "Authors", "Musicians", "People" };
	private JButton btnUpdate;

	/**
	 * Create the frame.
	 */
	public ClientWindow()
	{
		initialize();
	}

	private void initialize()
	{
		setResizable(false);
		setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 12));

		// create the DAO
		try
		{
			peopleDAO = new PeopleDAO();
			songsDAO = new SongsDAO();
			genresDAO = new GenresDAO();
			authorsDAO = new AuthorsDAO();
			musiciansDAO = new MusiciansDAO();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}

		setTitle("by Antoan Andonov");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((dimension.getWidth()-515)/2), (int) ((dimension.getHeight()-320)/2), 515, 320);
		contentPane = new JPanel();
	    
		contentPane.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		contentPane.setBackground(new Color(169, 169, 169));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JPanel panel = new JPanel();
		panel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		panel.setBackground(new Color(169, 169, 169));
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, 62, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, 500, SpringLayout.WEST, contentPane);
		contentPane.add(panel);
		panel.setLayout(null);

		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					Toolkit.getDefaultToolkit().beep();
					btnSearch.doClick();
				}
			}
		});
		searchTextField.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		searchTextField.setBounds(6, 16, 386, 28);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		btnSearch.setBounds(405, 5, 85, 50);
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				switch (comboBox.getSelectedItem().toString())
				{
					case "People":

						// Get last name from the text field
						// Call DAO and get people for the last name
						// If first name is empty, then get all people
						// Print out people

						try
						{
							List<People> people = null;

							String box = searchTextField.getText();
							String[] names = box.split(" ");
							String firstName = names[0];

							if(names.length == 1)
							{
								people = peopleDAO.searchPeople(firstName, null);

								if(people.size() == 0)
								{
									people = peopleDAO.searchPeople(null, firstName);
								}
							}
							else if(names.length == 2)
							{
								String lastName = names[1];
								people = peopleDAO.searchPeople(firstName, lastName);
							}
							else
							{
								people = peopleDAO.getAllPeople();
							}

							// create the model and update the "table"
							PeopleTableModel model = new PeopleTableModel(people);

							table.setModel(model);

						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					case "Songs":
						try
						{
							String songName = searchTextField.getText();

							List<Songs> song = null;

							if(songName != null && songName.trim().length() > 0)
							{
								song = songsDAO.searchSongs(songName);
							}
							else
							{
								song = songsDAO.getAllSongs();
							}

							// create the model and update the "table"
							SongsTableModel model = new SongsTableModel(song);

							table.setModel(model);

						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;
					case "Genres":
						try
						{
							String genreName = searchTextField.getText();

							List<Genres> genre = null;

							if(genreName != null && genreName.trim().length() > 0)
							{
								genre = genresDAO.searchGenres(genreName);
							}
							else
							{
								genre = genresDAO.getAllGenres();
							}

							// create the model and update the "table"
							GenresTableModel model = new GenresTableModel(genre);

							table.setModel(model);
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					case "Authors":
						try
						{
							List<Authors> author = null;

							String box = searchTextField.getText();
							String[] names = box.split(" ");
							String firstName = names[0];

							if(names.length == 1)
							{
								author = authorsDAO.searchAuthor(firstName, null);
								if(author.size() == 0)
								{
									author = authorsDAO.searchAuthor(null, firstName);
								}
							}
							else if(names.length == 2)
							{
								String lastName = names[1];
								author = authorsDAO.searchAuthor(firstName, lastName);
							}
							else
							{
								author = authorsDAO.getAllAuthors();
							}

							// create the model and update the "table"
							AuthorsTableModel model = new AuthorsTableModel(author);

							table.setModel(model);
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					case "Musicians":
						try
						{
							List<Musicians> musician = null;

							String box = searchTextField.getText();
							String[] names = box.split(" ");
							String firstName = names[0];

							if(names.length == 1)
							{
								musician = musiciansDAO.searchMusician(firstName, null);
								if(musician.size() == 0)
								{
									musician = musiciansDAO.searchMusician(null, firstName);
								}
							}
							else if(names.length == 2)
							{
								String lastName = names[1];
								musician = musiciansDAO.searchMusician(firstName, lastName);
							}
							else
							{
								musician = musiciansDAO.getAllMusicians();
							}

							// create the model and update the "table"
							MusiciansTableModel model = new MusiciansTableModel(musician);

							table.setModel(model);
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					default:
						break;
				}
			}
		});
		panel.add(btnSearch);

		scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panel);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, panel);
		scrollPane.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 75, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, 215, SpringLayout.NORTH, contentPane);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		panel_1 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.EAST, panel);
		panel_1.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		panel_1.setBackground(new Color(169, 169, 169));
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_1, 220, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_1, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_1, 282, SpringLayout.NORTH, contentPane);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		btnDelete.setEnabled(false);
		btnDelete.setBounds(405, 5, 85, 50);
		btnDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				switch (comboBox.getSelectedItem().toString())
				{
				// case "People":
				// try
				// {
				// // get the selected row
				// int row = table.getSelectedRow();
				//
				// // make sure a row is selected
				// if(row < 1)
				// {
				// JOptionPane.showMessageDialog(contentPane,
				// "You must select a people", "Error",
				// JOptionPane.ERROR_MESSAGE);
				// return;
				// }
				//
				// // TODO:
				// // prompt the user
				// // int response =
				// // JOptionPane.showConfirmDialog(contentPane,
				// // "Are you sure want to delete this people?",
				// // "Confirm", JOptionPane.YES_NO_OPTION,
				// // JOptionPane.QUESTION_MESSAGE);
				// //
				// // if(response != JOptionPane.YES_OPTION)
				// // {
				// // return;
				// // }
				//
				// // get the current people
				// People tempPeople = (People) table.getValueAt(row,
				// PeopleTableModel.OBJECT_COL);
				//
				// // delete the people
				// peopleDAO.deletePeople(tempPeople.getID());
				//
				// // refresh GUI
				// refreshPeopleView();
				//
				// // TODO:
				// // show success message
				// // JOptionPane.showMessageDialog(contentPane,
				// // "People deleted succesfully.", "Info",
				// // JOptionPane.INFORMATION_MESSAGE);
				//
				// }
				// catch (Exception exc)
				// {
				// JOptionPane.showMessageDialog(contentPane,
				// "Error deleting people: " + exc.getMessage(), "Error",
				// JOptionPane.ERROR_MESSAGE);
				// exc.printStackTrace();
				// }
				// break;

					case "Songs":
						try
						{
							int row = table.getSelectedRow();

							if(row < 0)
							{
								JOptionPane.showMessageDialog(contentPane, "You must select a Song", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							int response = JOptionPane.showConfirmDialog(contentPane, "Are you sure want to delete this Song?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if(response != JOptionPane.YES_OPTION)
							{
								return;
							}

							Songs tempSong = (Songs) table.getValueAt(row, SongsTableModel.OBJECT_COL);

							songsDAO.deleteSongs(tempSong.getID());

							refreshSongsView();

							// TODO:
							JOptionPane.showMessageDialog(contentPane, "The Song was deleted succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);

						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Error deleting Song: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;
					case "Genres":
						try
						{
							int row = table.getSelectedRow();

							if(row < 1)
							{
								JOptionPane.showMessageDialog(contentPane, "You must select a Genre", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							int response = JOptionPane.showConfirmDialog(contentPane, "Are you sure want to delete this Genre?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if(response != JOptionPane.YES_OPTION)
							{
								return;
							}

							Genres tempGenre = (Genres) table.getValueAt(row, GenresTableModel.OBJECT_COL);

							genresDAO.deleteGenres(tempGenre.getID());

							refreshGenresView();

							// TODO:
							JOptionPane.showMessageDialog(contentPane, "The Genre was deleted succesfully.", "Info", JOptionPane.INFORMATION_MESSAGE);

						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Error deleting Genre: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					case "Authors":
						try
						{
							int row = table.getSelectedRow();

							if(row < 1)
							{
								JOptionPane.showMessageDialog(contentPane, "You must select an Author", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							int response = JOptionPane.showConfirmDialog(contentPane, "Are you sure want to delete this Author?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if(response != JOptionPane.YES_OPTION)
							{
								return;
							}

							Authors tempAuthor = (Authors) table.getValueAt(row, AuthorsTableModel.OBJECT_COL);

							authorsDAO.deleteAuthors(tempAuthor.getPeopleID());

							refreshAuthorsView();

						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Error deleting Author: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					case "Musicians":
						try
						{
							int row = table.getSelectedRow();

							if(row < 1)
							{
								JOptionPane.showMessageDialog(contentPane, "You must select a Musician", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							int response = JOptionPane.showConfirmDialog(contentPane, "Are you sure want to delete this Musician?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if(response != JOptionPane.YES_OPTION)
							{
								return;
							}

							Musicians tempMusician = (Musicians) table.getValueAt(row, MusiciansTableModel.OBJECT_COL);

							musiciansDAO.deleteMusicians(tempMusician.getPeopleID());

							refreshMusiciansView();

						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Error deleting Musician: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					default:
						break;
				}
			}
		});
		panel_1.add(btnDelete);

		btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		btnInsert.setEnabled(false);
		btnInsert.setBounds(212, 5, 85, 50);
		panel_1.add(btnInsert);

		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		comboBox.setBounds(6, 17, 194, 29);
		panel_1.add(comboBox);
		comboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String tableSelected = comboBox.getSelectedItem().toString();
				switch (tableSelected)
				{
					case "Songs":
						btnInsert.setEnabled(true);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						refreshSongsView();
						break;
					case "Genres":
						btnInsert.setEnabled(true);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						refreshGenresView();
						break;
					case "Authors":
						btnInsert.setEnabled(true);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						refreshAuthorsView();
						break;
					case "Musicians":
						btnInsert.setEnabled(true);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						refreshMusiciansView();
						break;
					case "People":
						btnInsert.setEnabled(false);
						btnUpdate.setEnabled(false);
						btnDelete.setEnabled(false);
						refreshPeopleView();
						break;
					default:
						table.setModel(new DefaultTableModel());
						btnInsert.setEnabled(false);
						btnUpdate.setEnabled(false);
						btnDelete.setEnabled(false);
						break;
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(TABLES));

		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		btnUpdate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				switch (comboBox.getSelectedItem().toString())
				{
					case "Songs":
						// get the selected item
						int rowS = table.getSelectedRow();

						// make sure a row is selected
						if(rowS < 0)
						{
							JOptionPane.showMessageDialog(contentPane, "You must select a Song!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						// get the current
						Songs tempSong = (Songs) table.getValueAt(rowS, SongsTableModel.OBJECT_COL);

						// create dialog
						UpdateSongsDialog dialogS = new UpdateSongsDialog(ClientWindow.this, songsDAO, tempSong, true);

						// show dialog
						dialogS.setVisible(true);

						break;

					case "Genres":
						// get the selected item
						int rowG = table.getSelectedRow();

						// make sure a row is selected
						if(rowG < 1)
						{
							JOptionPane.showMessageDialog(contentPane, "You must select a Genre!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						// get the current
						Genres tempGenre = (Genres) table.getValueAt(rowG, GenresTableModel.OBJECT_COL);

						// create dialog
						UpdateGenresDialog dialogG = new UpdateGenresDialog(ClientWindow.this, genresDAO, tempGenre, true);

						// show dialog
						dialogG.setVisible(true);

						break;

					case "Authors":
						int rowA = table.getSelectedRow();

						if(rowA < 1)
						{
							JOptionPane.showMessageDialog(contentPane, "You must select an Author!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						Authors tempAuthor = (Authors) table.getValueAt(rowA, AuthorsTableModel.OBJECT_COL);
						tempAuthor.setPeopleID(tempAuthor.getPeopleID());
						UpdateAuthorsDialog dialogA = new UpdateAuthorsDialog(ClientWindow.this, authorsDAO, tempAuthor, true);

						dialogA.setVisible(true);
						break;

					case "Musicians":
						int rowM = table.getSelectedRow();

						if(rowM < 1)
						{
							JOptionPane.showMessageDialog(contentPane, "You must select a Musician!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						Musicians tempMusician = (Musicians) table.getValueAt(rowM, MusiciansTableModel.OBJECT_COL);
						tempMusician.setPeopleID(tempMusician.getPeopleID());
						UpdateMusiciansDialog dialogM = new UpdateMusiciansDialog(ClientWindow.this, musiciansDAO, tempMusician, true);

						dialogM.setVisible(true);

						break;

					default:
						break;
				}
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setBounds(309, 6, 84, 50);
		panel_1.add(btnUpdate);
		btnInsert.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				switch (comboBox.getSelectedItem().toString())
				{
				// case "People":
				//
				// try
				// {
				// EventQueue.invokeLater(new Runnable()
				// {
				// public void run()
				// {
				// InsertPeopleDialog dialog = new InsertPeopleDialog();
				// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				// dialog.setVisible(true);
				// }
				// });
				// }
				// catch (Exception exc)
				// {
				// JOptionPane.showMessageDialog(contentPane,
				// "Something went wrong!\n" + exc, "Error",
				// JOptionPane.ERROR_MESSAGE);
				// exc.printStackTrace();
				// }
				//
				// break;

					case "Songs":
						try
						{
							EventQueue.invokeLater(new Runnable()
							{
								public void run()
								{
									InsertSongsDialog dialog = new InsertSongsDialog();
									dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									dialog.setVisible(true);
								}
							});
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}

						break;

					case "Genres":
						try
						{
							EventQueue.invokeLater(new Runnable()
							{
								public void run()
								{
									InsertGenresDialog dialog = new InsertGenresDialog();
									dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									dialog.setVisible(true);
								}
							});
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}

						break;

					case "Authors":
						try
						{
							EventQueue.invokeLater(new Runnable()
							{
								public void run()
								{
									InsertAuthorsDialog dialog = new InsertAuthorsDialog();
									dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									dialog.setVisible(true);
								}
							});
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					case "Musicians":
						try
						{
							EventQueue.invokeLater(new Runnable()
							{
								public void run()
								{
									InsertMusiciansDialog dialog = new InsertMusiciansDialog();
									dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									dialog.setVisible(true);
								}
							});
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(contentPane, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
							exc.printStackTrace();
						}
						break;

					default:
						break;
				}
			}
		});
	}

	public static void refreshSongsView()
	{
		try
		{
			List<Songs> song = songsDAO.getAllSongs();
			// List<Genres> genre = genresDAO.getAllGenres();
			// List<Authors> author = authorsDAO.getAllAuthors();
			// List<Musicians> musician = musiciansDAO.getAllMusicians();

			// create the model and update the "table"
			// SongsTableModel model = new SongsTableModel(song, genre, author,
			// musician);
			SongsTableModel model = new SongsTableModel(song);
			table.setModel(model);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}

	public static void refreshGenresView()
	{
		try
		{
			List<Genres> genre = genresDAO.getAllGenres();

			// create the model and update the "table"
			GenresTableModel model = new GenresTableModel(genre);
			table.setModel(model);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}

	public static void refreshAuthorsView()
	{
		try
		{
			List<Authors> author = authorsDAO.getAllAuthors();

			// create the model and update the "table"
			AuthorsTableModel model = new AuthorsTableModel(author);

			table.setModel(model);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}

	public static void refreshMusiciansView()
	{
		try
		{
			List<Musicians> musician = musiciansDAO.getAllMusicians();

			// create the model and update the "table"
			MusiciansTableModel model = new MusiciansTableModel(musician);

			table.setModel(model);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}

	public static void refreshPeopleView()
	{
		try
		{
			List<People> people = peopleDAO.getAllPeople();

			// create the model and update the "table"
			PeopleTableModel model = new PeopleTableModel(people);

			table.setModel(model);
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong!\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}
}

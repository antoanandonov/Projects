package Database;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AuthorsTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	public static final int OBJECT_COL = -1;
	private final static int PEOPLE_FIRST_NAME = 0;
	private final static int PEOPLE_LAST_NAME = 1;

	private String[] colNames = { "First Name", "Last Name" };
	private List<Authors> authors;

	public AuthorsTableModel(List<Authors> authors)
	{
		this.authors = authors;
	}

	@Override
	public int getColumnCount()
	{
		return colNames.length;
	}

	@Override
	public int getRowCount()
	{
		return authors.size();
	}

	@Override
	public String getColumnName(int col)
	{
		return colNames[col];
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		Authors tempAuthor = authors.get(row);

		switch (col)
		{
			case PEOPLE_FIRST_NAME:
				return tempAuthor.getFirstName();
			case PEOPLE_LAST_NAME:
				return tempAuthor.getLastName();
			case OBJECT_COL:
				return tempAuthor;
			default:
				return tempAuthor.getPeopleID();
		}
	}

	@Override
	public Class<? extends Object> getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();
	}

}

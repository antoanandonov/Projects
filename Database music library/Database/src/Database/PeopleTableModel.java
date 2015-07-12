package Database;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PeopleTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int OBJECT_COL = -1;
	private final static int ID = 0;
	private final static int FIRST_NAME = 1;
	private final static int LAST_NAME = 2;

	private String[] colNames = { "ID", "First Name", "Last Name" };
	private List<People> people;

	public PeopleTableModel(List<People> people)
	{
		this.people = people;
	}

	@Override
	public int getColumnCount()
	{
		return colNames.length;
	}

	@Override
	public int getRowCount()
	{
		return people.size();
	}

	@Override
	public String getColumnName(int col)
	{
		return colNames[col];
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		People tempPeople = people.get(row);

		switch (col)
		{
			case ID:
				return tempPeople.getId();
			case FIRST_NAME:
				return tempPeople.getFirstName();
			case LAST_NAME:
				return tempPeople.getLastName();
			case OBJECT_COL:
				return tempPeople;
			default:
				return tempPeople.getFirstName();
		}
	}

	@Override
	public Class<? extends Object> getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();
	}

}

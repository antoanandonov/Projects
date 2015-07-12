package Database;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MusiciansTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	public static final int OBJECT_COL = -1;
	private final static int PEOPLE_FIRST_NAME = 0;
	private final static int PEOPLE_LAST_NAME = 1;


	private String[] colNames = { "First Name", "Last Name" };
	private List<Musicians> musicians;


	public MusiciansTableModel(List<Musicians> musicians)
	{
		this.musicians = musicians;
	}
	

	@Override
	public int getColumnCount()
	{
		return colNames.length;
	}

	@Override
	public int getRowCount()
	{
		return musicians.size();
	}

	@Override
	public String getColumnName(int col)
	{
		return colNames[col];
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		Musicians tempMusician = musicians.get(row);

		switch (col)
		{
			case PEOPLE_FIRST_NAME:
				return tempMusician.getFirstName();
			case PEOPLE_LAST_NAME:
				return tempMusician.getLastName();
			case OBJECT_COL:
				return tempMusician;
			default:
				return tempMusician.getPeopleID();
		}
	}

	@Override
	public Class<? extends Object> getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();
	}

}

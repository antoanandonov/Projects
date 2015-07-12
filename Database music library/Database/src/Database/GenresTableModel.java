package Database;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class GenresTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int OBJECT_COL = -1;
	private final static int ID = 0;
	private final static int GENRE = 1;

	private String[] colNames = { "ID", "Genre" };
	private List<Genres> genre;

	public GenresTableModel(List<Genres> genre)
	{
		this.genre = genre;
	}

	@Override
	public int getColumnCount()
	{
		return colNames.length;
	}

	@Override
	public int getRowCount()
	{
		return genre.size();
	}

	@Override
	public String getColumnName(int col)
	{
		return colNames[col];
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		Genres tempGenre = genre.get(row);

		switch (col)
		{
			case ID:
				return tempGenre.getID();
			case GENRE:
				return tempGenre.getGenre();
			case OBJECT_COL:
				return tempGenre;
			default:
				return tempGenre.getID();
		}
	}

	@Override
	public Class<? extends Object> getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();
	}

}

package Database;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class SongsTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int OBJECT_COL = -1;
	private final static int ID = 0;
	private final static int SONG = 1;
	private final static int RATE = 2;

	private String[] colNames = { "ID", "Songs", "Rate" };
	private List<Songs> song;

	// private List<Genres> genre;
	// private List<Authors> author;
	// private List<Musicians> musician;

	public SongsTableModel(List<Songs> song)
	{
		this.song = song;
	}

	// public SongsTableModel(List<Songs> song, List<Genres> genre,
	// List<Authors> author, List<Musicians> musician)
	// {
	// this.song = song;
	// this.genre = genre;
	// this.author = author;
	// this.musician = musician;
	// }

	@Override
	public int getColumnCount()
	{
		return colNames.length;
	}

	@Override
	public int getRowCount()
	{
		return song.size();
	}

	@Override
	public String getColumnName(int col)
	{
		return colNames[col];
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		Songs tempSong = song.get(row);

		switch (col)
		{
			case ID:
				return tempSong.getID();
			case SONG:
				return tempSong.getSong();
			case RATE:
				return tempSong.getRate();
			case OBJECT_COL:
				return tempSong;
			default:
				return tempSong.getID();
		}
	}

	@Override
	public Class<? extends Object> getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();
	}

}

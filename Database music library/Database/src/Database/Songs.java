package Database;

public class Songs
{
	private int id;
	private String song;
	private int genreID;
	private int rate;

	public Songs(int id, String song, int genreID, int rate)
	{
		setID(id);
		setSong(song);
		setGenreID(genreID);
		setRate(rate);
	}

	public Songs(String song, int genreID, int rate)
	{
		setSong(song);
		setGenreID(genreID);
		setRate(rate);
	}

	public Songs(String song)
	{
		setSong(song);
	}

	public int getRate()
	{
		return this.rate;
	}

	public int getID()
	{
		return this.id;
	}

	private void setID(int id)
	{
		this.id = id;
	}

	public String getSong()
	{
		return this.song;
	}

	public void setSong(String song)
	{
		this.song = song;
	}

	public int getGenreID()
	{
		return this.genreID;
	}

	public void setGenreID(int genreID)
	{
		this.genreID = genreID;
	}

	public void setRate(int rate)
	{
		this.rate = rate;
	}

}

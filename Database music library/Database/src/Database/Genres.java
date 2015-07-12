package Database;

public class Genres
{
	private int id;
	private String genre;

	public Genres(int id, String genre)
	{
		super();
		setID(id);
		setGenre(genre);
	}

	public Genres(String genre)
	{
		super();
		setGenre(genre);
	}

	public int getID()
	{
		return this.id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public String getGenre()
	{
		return this.genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

}

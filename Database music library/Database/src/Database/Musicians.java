package Database;

public class Musicians extends People
{
	private int peopleID;

	public Musicians(int peopleID)
	{
		setPeopleID(peopleID);
	}

	public Musicians(String firstName, String lastName)
	{
		super(firstName, lastName);
	}

	public Musicians(int peopleID, String firstName, String lastName)
	{
		super(firstName, lastName);
		setPeopleID(peopleID);
	}

	public int getPeopleID()
	{
		return this.peopleID;
	}

	public void setPeopleID(int peopleID)
	{
		this.peopleID = peopleID;
	}

}

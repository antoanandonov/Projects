package Database;

public class Authors extends People
{
	private int peopleID;

	public Authors(int peopleID)
	{
		setPeopleID(peopleID);
	}

	public Authors(int peopleID, String firstName, String lastName)
	{
		super(firstName, lastName);
		setPeopleID(peopleID);
	}
	
	public Authors( String firstName, String lastName)
	{
		super(firstName, lastName);
		
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

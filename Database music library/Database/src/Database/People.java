package Database;

public class People
{
	private int id;
	private String firstName;
	private String lastName;

	public People()
	{
	}

	public People(int id, String firstName, String lastName)
	{
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public People(String firstName, String lastName)
	{
		setFirstName(firstName);
		setLastName(lastName);
	}

	protected int getId()
	{
		return this.id;
	}

	protected String getFirstName()
	{
		return this.firstName;
	}

	protected String getLastName()
	{
		return this.lastName;
	}

	protected void setId(int id)
	{
		this.id = id;
	}

	protected void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	protected void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	@Override
	public String toString()
	{
		return String.format("People [id=%s, firstName=%s, lastName=%s]", getId(), getFirstName(), getLastName());
	}

}

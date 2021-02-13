package prism.networks;

public class Machine
{
	private int ID;
	private boolean infected;
	private boolean cured;

	public Machine(int ID)
	{
		this.ID = ID;

		infected = false;
		cured = false;
	}

	public void cure()
	{
		cured = true;
		infected = false;
	}

	public void infect()
	{
		if(!cured)
			infected = true;
	}

	public int getID()
	{
		return ID;
	}

	public boolean getCured()
	{
		return cured;
	}

	public void setCured(boolean cured)
	{
		this.cured = cured;
	}

	public boolean getInfected()
	{
		return infected;
	}

	public void setInfected(boolean infected)
	{
		this.infected = infected;
	}
}

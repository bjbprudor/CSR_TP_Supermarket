package fr.csr;

public class Chariot 
{
	
	private int nbChariots;
	
	public Chariot()
	{
		nbChariots = Supermarche.CHARIOTS_INIT;
	}
	
	synchronized public void prendreChariot() throws InterruptedException
	{
		if(nbChariots > 0)
		{
			nbChariots--;
		}
		else 
		{
			wait();
		}
	}
	
	synchronized public void deposerChariot()
	{
		nbChariots++;
		notifyAll();		
	}

}

package fr.csr;

public class Caissier extends Thread
{

	private int id;
	
	public Caissier(int unId)
	{
		id = unId;
	}
	
	public int getCaissierId() 
	{
		return this.id;
	}

	public void setCaissierId(int id) 
	{
		this.id = id;
	}
	
	public void run()
	{
		while(true)
		{
			try 
			{
				AttendreClient();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void AttendreClient() throws InterruptedException
	{
		String s = "";
		while(s != Supermarche.caisse.marq)
		{
			s = Supermarche.caisse.prendreSurTapis();
		}
		System.out.println("Le caissier a scanner tous les articles");
		Supermarche.caisse.demanderReglement();
		System.out.println("Le caissier a ete payer");
	}
	
}

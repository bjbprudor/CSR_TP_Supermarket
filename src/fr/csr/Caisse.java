package fr.csr;

import java.util.ArrayList;
import java.util.List;

public class Caisse 
{
	
	private int id;
	private List<String> tapis;
	private int taille_tapis;
	private boolean clientEnCours;
	
	public final String marq = "Client Suivant";
	
	public Caisse(int unId)
	{
		id = unId;
		tapis = new ArrayList<String>();
		taille_tapis = Supermarche.TAILLE_TAPIS;
		clientEnCours = false;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getMarq() 
	{
		return marq;
	}
	
	synchronized public void passerEnCaisse() throws InterruptedException
	{
		if(clientEnCours)
		{
			wait();
		}
		else
		{
			clientEnCours = true;
		}		
	}
	
	synchronized public void sortirDeCaisse()
	{
		clientEnCours = false;
		notifyAll();
	}
	
	synchronized public void deposerSurTapis(String p) throws InterruptedException
	{
		if(tapis.size() < taille_tapis)
		{
			tapis.add(p);
			notifyAll();
		}
		else
		{
			wait();
		}
	}
	
	synchronized public String prendreSurTapis() throws InterruptedException
	{
		int ind = 0;
		String s = "";
		if(tapis.size() > 0)
		{
			s = tapis.get(0);
			tapis.remove(0);
		}
		else
		{
			wait();
		}
		notifyAll();
		return s;
	}
	
	synchronized public void demanderReglement() throws InterruptedException
	{
		// le caisser reveille le client
		notifyAll();
		// le caissier attends le reglement
		wait();
	}
	
	synchronized public void payerAchats() throws InterruptedException
	{
		wait();
		//pay
		notifyAll();
	}

}

package fr.csr;

import java.util.HashMap;

public class Client extends Thread
{

	private int id;
	public etatClient etat;
	public HashMap<Produit, Integer> liste;

	public Client(int unId)
	{
		setClientId(unId);
		etat = etatClient.ATTENTE_CHARIOT;
		liste = new HashMap<Produit,Integer>();
	}

	public int getClientId() 
	{
		return id;
	}

	public void setClientId(int id) 
	{
		this.id = id;
	}

	public enum etatClient
	{
		ATTENTE_CHARIOT,
		EN_COURSE,
		ATTENTE_PRODUIT,
		ATTENTE_CAISSE,
		A_LA_CAISSE
	};

	public void run()
	{
		try
		{
			// on genere la liste de courses
			GenererListeCourse();
			// on prends un chariot
			PrendreUnChariot();
			System.out.println("Le client " + id + " à pris son chariot");
			// on parcours les rayons
			ParcourirRayons();
			System.out.println("Le client " + id + " se dirige a la caisse");
			// on passe en caisse
			PassageCaisse();
			// on depose en caisse
			DeposerEnCaisse();
			System.out.println("Le client " + id + " a deposer tous ses produits en caisse");
			// on prepare le reglement
			Payement();		
			Supermarche.caisse.sortirDeCaisse();
			// on depose le chariot
			Supermarche.chariots.deposerChariot();
			System.out.println("Le client " + id + " a deposer son chariot");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	public void GenererListeCourse()
	{
		liste.put(Supermarche.lesProduits.get(0),3);
		liste.put(Supermarche.lesProduits.get(1),4);
		liste.put(Supermarche.lesProduits.get(2),7);
		liste.put(Supermarche.lesProduits.get(3),0);
		System.out.println("Le client " + id + " a decider ce qu'il a besoin");
	}

	public void PrendreUnChariot() throws InterruptedException
	{
		Supermarche.chariots.prendreChariot();
	}

	public void ParcourirRayons() throws InterruptedException
	{
		for(Rayon r : Supermarche.lesRayons)
		{
			sleep(300);
			int nb = liste.get(r.getP());
			while(nb > 0)
			{
				r.prendreExem();
				nb--;
			}
			System.out.println("Le client " + id + " a pris ses " + liste.get(r.getP()) + " " + r.getP().getNom() + " ");
		}
		System.out.println("Le client " + id + " a fini ses courses");
	}

	public void PassageCaisse() throws InterruptedException
	{
		Supermarche.caisse.passerEnCaisse();			
	}

	public void DeposerEnCaisse() throws InterruptedException
	{
		int i;
		for(Produit p : liste.keySet())
		{
			i = liste.get(p);
			while(i > 0)
			{			
				Supermarche.caisse.deposerSurTapis(p.getNom());
				i--;
				sleep(20);				
			}
		}
	}

	public void Payement() throws InterruptedException
	{
		// on entre en mode payment
		Supermarche.caisse.deposerSurTapis(Supermarche.caisse.marq);
		Supermarche.caisse.payerAchats();
	}

}

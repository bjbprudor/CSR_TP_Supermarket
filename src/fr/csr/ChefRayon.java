package fr.csr;

import java.util.HashMap;

public class ChefRayon extends Thread
{
	
	private int id;
	private HashMap<Produit,Integer> stock;
	
	public ChefRayon(int unId)
	{
		setChefRayonId(unId);
		setStock(new HashMap<Produit, Integer>());
	}

	public void run()
	{
		try 
		{
			while(true)
			{			
				
				Supermarche.restockFromEntrepot();
				sleep(500); // faire le plein
				System.out.println("Le chef de rayon est passer par l'entrepot");
				for (Rayon r : Supermarche.lesRayons) 
				{	
					sleep(200); // se deplacer jusqu'au rayon
					int nb = stock.get(r.getP());
					nb = r.majStock(nb);
					stock.replace(r.getP(), nb);
					System.out.println("Le chef de rayon est passer au rayon " + r.getP().getNom());
				}
				System.out.println("le chef de rayon a fait le tour des rayon");
				sleep(10000);
				
			}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	public int getChefRayonId() 
	{
		return id;
	}

	public void setChefRayonId(int id) 
	{
		this.id = id;
	}
	

	public HashMap<Produit,Integer> getStock() 
	{
		return stock;
	}
	

	public void setStock(HashMap<Produit,Integer> stock) 
	{
		this.stock = stock;
	}
	
}

package fr.csr;

public class Rayon 
{
	
	private int id;
	private Produit p;
	private int stock;
	
	public Rayon(int id, Produit p, int stock) 
	{
		this.id = id;
		this.p = p;
		this.stock = stock;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Produit getP() 
	{
		return p;
	}

	public void setP(Produit p) 
	{
		this.p = p;
	}

	public int getStock()
	{
		return stock;
	}

	public void setStock(int stock) 
	{
		this.stock = stock;
	}
		
	synchronized public int majStock(int nbExem)
	{
		if (stock > Supermarche.RAYON_STOCK_MAX) 
		{
			nbExem += (stock - Supermarche.RAYON_STOCK_MAX);
		}
		else if(stock < Supermarche.RAYON_STOCK_MAX)
		{
			int diff = Supermarche.RAYON_STOCK_MAX - stock;
			if(nbExem > diff)
			{
				stock = stock + diff;
				nbExem = nbExem - diff;
			}
			else
			{
				stock = stock + nbExem;
				nbExem = 0;
			}
		}
		notifyAll();
		return nbExem;
	}
	
	synchronized public void prendreExem() throws InterruptedException
	{
		if(stock > 0)
		{
			stock--;
		}
		else 
		{
			wait();
		}
	}
}

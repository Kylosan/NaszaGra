package com.example.naszagra;

public class Population {
	private Shot Pop[];
	private double Rate[];
	private double Probability[];
	private double CumulativeProbability[];
	private int count;
	Point start, target;
	Terrain terrain;
	
	Population(int c, Point s, Point t, Terrain ter)
	{
		count = c;
		start = new Point();
		target = new Point();
		start.Copy(s);
		target.Copy(t);
		terrain = ter;
		Pop = new Shot[count];
		Rate = new double[count];
		Probability = new double[count];
		CumulativeProbability = new double[count];
		for(int i = 0; i<count; i++)
			Pop[i] = new Shot(start, target, terrain);
		this.Rate(0);
	}

	public Shot BestShot()
	{//Zwraca najlepszy strza³ w danej iteracji
		double max = 0;
		int m = 0;
		for(int i = 0; i<count; i++)
		{
			if(Rate[i]>max)
			{
				max = Rate[i];
				m = i;
			}
		}
		return Pop[m];
	}
	
	public void Rate(int it)
	{//Ocenia wszystkie osobniki w populacji
		for(int i = 0; i<count; i++)
		{
			double acc = Pop[i].Accuracy();
			
			//Zak³ócenie b³êdem
			double p = 10 - it;
			if(p<1)
				p=1;
			p/=100;
			acc += Math.random()*(AppConstants.SCREEN_WIDTH*p)-(AppConstants.SCREEN_WIDTH*(p/2));
			
			if(acc!=0)
				Rate[i] = 1/acc;
			else
				Rate[i] = 99999999;
		}
	}
	
	public void Probability()
	{//oblicza prawdopodobieñstwo wyboru do populajcji potomnej
		//oraz skumulowane prawdopodobieñstwo (potrzebne to reprodukcji ruletkowej)
		double sum = 0;
		for(int i = 0; i<count; i++)
			sum += Rate[i];
		for(int i = 0; i<count; i++)
			Probability[i] = Rate[i]/sum;
		CumulativeProbability[0] = Probability[0];
		for(int i = 1; i<count; i++)
			CumulativeProbability[i] = CumulativeProbability[i-1]+Probability[i];
	}
	
	public Population Reproduction()
	{
		//Ruletka
		//Losuje z populacji rodzicielsiej osobniki do populacji potomnej
		//Prawdopodobieñstwo wylosowania jest wiêksze dla lepiej prystosowanych osobników
		Population Q = new Population(count, start, target, terrain);
		
		for(int i = 0; i<count; i++)
		{
			double rand = Math.random();
			int j = 0;
			while(rand>this.CumulativeProbability[j])
				j++;
			Q.Pop[i].Copy(this.Pop[j]);
		}
		
		return Q;
	}
	public void Crossover()
	{
		//Krzy¿owanie
		//Dwa s¹siednie osobniki s¹ ze sob¹ krzy¿owanie
		for(int i = 0; i<count; i+=2)
		{
			double rand = Math.random();
			double f1, f2, a1, a2;
			
			f1=this.Pop[i].GetForce();
			f2=this.Pop[i+1].GetForce();
			a1=this.Pop[i].GetAngle();
			a2=this.Pop[i+1].GetAngle();
			
			this.Pop[i].SetForce(f1+rand*(f2-f1));
			this.Pop[i+1].SetForce(f2+f1-this.Pop[i].GetForce());
			
			this.Pop[i].SetAngle(a1+rand*(a2-a1));
			this.Pop[i+1].SetAngle(a2+a1-this.Pop[i].GetAngle());
		}
	}
	
	public void Mutation(double prob)
	{
		//Mutacja
		//Dla zadanego prawdopodobieñstwa mutacji zmieniamy o losow¹ wartoœæ si³ê, k¹t i wspó³rzêdn¹ X
		for(int i = 0; i< count; i++)
		{	
			//if(Math.random()<prob)
			//	this.Pop[i].SetMove(Math.random()*10-5);//ruch o (-5,5);
			if(Math.random()<prob)
				this.Pop[i].SetAngle(this.Pop[i].GetAngle()+(Math.random()-0.5));//k¹t o (-0.5,0.5);
			if(Math.random()<prob)
				this.Pop[i].SetForce(this.Pop[i].GetForce()+(Math.random()*10-5));//si³a o (-5,5);
		}
	}
	
	public void Succession(Population P)
	{//Do kolejnej iteracji przechodzi najlepszy osobnik z aktualnej iteracji i wygenerowane osobniki z nowej populacji (poza jednnym)
		this.Pop[0].Copy(P.BestShot());
	}

	public void SetPlayersPosition(Player player, Player ai) 
	{
		for(int i = 0; i< count; i++)
			this.Pop[i].SetPlayersPosition(player, ai);
	}
	
	public void Copy(Population p)
	{
		for(int i = 0; i< count; i++)
			this.Pop[i].Copy(p.Pop[i]);
	}
}

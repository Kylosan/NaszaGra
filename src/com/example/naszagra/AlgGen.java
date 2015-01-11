package com.example.naszagra;


public class AlgGen {
	
	private Population P, T;
	private int count, it;	
	private double prevAcc;
	
	AlgGen(int c, Player player, Player AIplayer, Terrain ter)
	{
		if(c%2==1)//populacja musi mieæ parzyst¹ iloœc osobników
			c++;
		count = c;
		P = new Population(count, AIplayer.pos, player.pos, ter);
		T = new Population(count, AIplayer.pos, player.pos, ter);
		prevAcc = 9999999;
		it=0;
		P.Rate();
		P.Probability();
	}
	
	public void Iteration()
	{
		T.Copy(P.Reproduction());
		T.Crossover();
		T.Mutation(0.1);//prawdopodobieñstwo mutacji 0.1
		T.Succession(P);
		P.Copy(T);
		P.Rate();
		P.Probability();
		
		if(prevAcc == this.BestShot().Accuracy())
			it++;
		else
			it=0;
		prevAcc = this.BestShot().Accuracy();
	}
	
	public Shot BestShot()
	{//Zwraca najlepszy strza³ w danej iteracji
		return P.BestShot();
	}
	
	public void Update(Player player, Player ai)
	{//Aktualizuje pozycjê graczy
		P.SetPlayersPosition(player, ai);
		P.Rate();
		P.Probability();
	}
	
	public int HowManyIt()
	{
		if(it<3)
			return 1;
		else
			return 5;
	}
}
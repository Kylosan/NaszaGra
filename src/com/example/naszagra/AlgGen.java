package com.example.naszagra;


public class AlgGen {
	
	private Population P, T;
	private int count;	
	
	AlgGen(int c, Player player, Player AIplayer, Terrain ter)
	{
		if(c%2==1)//populacja musi mie� parzyst� ilo�c osobnik�w
			c++;
		count = c;
		P = new Population(count, AIplayer.pos, player.pos, ter);
		T = new Population(count, AIplayer.pos, player.pos, ter);
		P.Rate();
		P.Probability();
	}
	
	public void Iteration()
	{
		T.Copy(P.Reproduction());
		T.Crossover();
		T.Mutation(0.1);//prawdopodobie�stwo mutacji 0.1
		T.Succession(P);
		P.Copy(T);
		P.Rate();
		P.Probability();
	}
	
	public Shot BestShot()
	{//Zwraca najlepszy strza� w danej iteracji
		return P.BestShot();
	}
	
	public void Update(Point player, Point ai)
	{//Aktualizuje pozycj� graczy
		P.SetPlayersPosition(player, ai);
		P.Rate();
		P.Probability();
	}
}
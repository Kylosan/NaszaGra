package com.example.naszagra;

import java.util.ArrayList;
import java.util.Random;

public class Terrain {
	
	Point Points[];
	Obstacle Obstacles[];

	Terrain()
	{
		Points = new Point[10];
		Obstacles = new Obstacle[2];
		
		//Losowe 10 punktów tworz¹cych teren
		Random generator = new Random(); 
		int x = 0;
		int y = generator.nextInt(AppConstants.SCREEN_HEIGHT-300)+200;
		Points[0] = new Point(x,y);
		
		for(int i = 1; i < 9; i++)
		{
			x = (int)Points[i-1].GetX()+generator.nextInt(AppConstants.SCREEN_WIDTH/(9-i)/2)+ AppConstants.SCREEN_WIDTH/(9-i)/2;
			y = generator.nextInt(AppConstants.SCREEN_HEIGHT-300)+200;
			Points[i] = new Point(x,y);
		}
		x = AppConstants.SCREEN_WIDTH;
		y = generator.nextInt(AppConstants.SCREEN_HEIGHT-300)+200;
		Points[9] = new Point(x,y);
		
		x = generator.nextInt(AppConstants.SCREEN_WIDTH/2-100) + 100;
		Obstacles[0] = new Obstacle(x,Height(x),AppConstants.SCREEN_WIDTH/200,AppConstants.SCREEN_HEIGHT/5);
		x = generator.nextInt(AppConstants.SCREEN_WIDTH/2-200) + AppConstants.SCREEN_WIDTH/2;
		Obstacles[1] = new Obstacle(x,Height(x),AppConstants.SCREEN_WIDTH/200,AppConstants.SCREEN_HEIGHT/5);
	}
	
	public boolean Collision(Point p)
	{
		if(p.GetX() < 0)
			return true;
		
		if(p.GetY() >= Height(p.GetX()))
			return true;
		
		for(int i = 0; i<2; i++)
		{
			if(Obstacles[i].Collision(p))
				return true;
		}
		
		return false;
	}
	
	public double Height(double x)
	{//Zwraca wysokoœc terenu w danym punkcie 
		double a, b, y;
		
		for(int j = 0; j < Points.length; j++)
			if(x==Points[j].GetX())
				return Points[j].GetY();
		
		int i, j = 0;
		for(i = 0; i < Points.length-1; i++)
			if(this.Points[i].GetX() < x && this.Points[i+1].GetX() > x)
				j = i;

		a = (Points[j].GetY()-Points[j+1].GetY())/(Points[j].GetX()-Points[j+1].GetX());
		b = Points[j].GetY() - a*Points[j].GetX();

		y = a*x+b;
		
		return y;
	}
	
	public ArrayList<Point> GetTerrain()
	{
		ArrayList<Point> terr = new ArrayList<Point>();
		
		for(int i = 0; i < 10; i++)
		{			
			Point p = new Point();
			
			p.SetX(this.Points[i].GetX());
			p.SetY(this.Points[i].GetY());
			terr.add(p);
		}
		return terr;
	}
	
	public ArrayList<Point> GetObstacles()
	{
		ArrayList<Point> ob = new ArrayList<Point>();
		
		for(int i = 0; i < 2; i++)
		{			
			Point p = new Point();
			
			p.SetX(this.Obstacles[i].X);
			p.SetY(this.Obstacles[i].Y);
			ob.add(p);
			p = new Point();
			p.SetX(this.Obstacles[i].X);
			p.SetY(this.Obstacles[i].Y-this.Obstacles[i].height);
			ob.add(p);
			p = new Point();
			p.SetX(this.Obstacles[i].X+this.Obstacles[i].width);
			p.SetY(this.Obstacles[i].Y-this.Obstacles[i].height);
			ob.add(p);
			p = new Point();
			p.SetX(this.Obstacles[i].X+this.Obstacles[i].width);
			p.SetY(this.Height(p.GetX()));
			ob.add(p);
		}
		return ob;
	}
	
	public void Copy (Terrain t)
	{
		this.Obstacles[0].Copy(t.Obstacles[0]);
		this.Obstacles[1].Copy(t.Obstacles[1]);
		for(int i = 0; i < 10; i++)
			this.Points[i].Copy(t.Points[i]);
	}
}

package com.example.naszagra;

import java.util.ArrayList;

public class Shot {
	
	Point start, target;
	Terrain terrain;
	private double force, angle, g;
	private int direction;//1 - prawo, -1 - lewo
	private int hit;
	
	Shot(Point s, Point t, Terrain ter)
	{
		start = new Point();
		target = new Point();
		start.Copy(s);
		target.Copy(t);
		terrain = ter;
		double dist = Math.sqrt(Math.pow((start.GetX()-target.GetX()), 2)+Math.pow((start.GetY()-target.GetY()), 2));
		force = Math.random()*dist*0.6;
		angle = Math.random()*Math.PI/3;
		g=9.81;
		
		if(target.GetX()<start.GetX())//okreœla w któr¹ stronê ma strzelaæ gracz komputerowy
			direction = -1;
		else 
			direction = 1;
	}
	
	public Shot() {
		start = new Point();
		target = new Point();
		terrain = new Terrain();
		force = 0;
		angle = 0;
		g = 9.81;
	}

	public ArrayList<Point> GetDrawTrajectory() 
	{ 
		ArrayList<Point> traj = new ArrayList<Point>();
		
		while(true)
		{
			double t = 0;
			Point p = new Point(start.GetX(), start.GetY());
			if(terrain.Collision(p))
				break;
			
			p.SetX(direction*force*t*Math.cos(angle) + start.GetX());
			p.SetY(1080 - (force*t*Math.sin(angle) - (g*Math.pow(t,2))/2 + start.GetY()));
			traj.add(p);
			t+=0.01;
		}
		return traj; 
	}
	
	public double Accuracy()
	{
		//tworzy dyskretn¹ trajektoriê lotu strza³y i sprawdza jak blisko celu ona przeleci
		double min = Double.POSITIVE_INFINITY, t = 0, dist = Double.POSITIVE_INFINITY;
		Point p = new Point(start.GetX(), start.GetY());
		while(!terrain.Collision(p))
		{
			if(target.GetX() >= p.GetX() && target.GetX() <= p.GetX() + 30 && target.GetY() >= p.GetY() && target.GetY() <= p.GetY() + 70)
				hit = 1;
			else
				hit = 0;
			
			p.SetX(direction*force*t*Math.cos(angle) + start.GetX());
			p.SetY(force*t*Math.sin(angle) - (g*Math.pow(t,2))/2 + start.GetY());
			dist = Math.sqrt(Math.pow((p.GetX()-target.GetX()), 2)+Math.pow((p.GetY()-target.GetY()), 2));
			if(dist<min)
				min = dist;
			t+=0.01;
		}
		return min;
	}
	
	public String ToString()
	{
		String s = "";
		//s += "Si³a: "+force + ", k¹t: "+ angle + ", pozycja X: " + start.GetX() + ", odleg³óœæ: " + Accuracy();
		s += ""+force + ", "+ angle + ", " + start.GetX() + ", " + Accuracy();
		return s;
	}
	
	public void SetPlayersPosition(Point s, Point t)
	{
		start.SetX(s.GetX());
		start.SetY(s.GetY());
		target.SetX(t.GetX());
		target.SetY(t.GetY());
		
		if(target.GetX()<start.GetX())//okreœla w któr¹ stronê ma strzelaæ gracz komputerowy
			direction = -1;
		else 
			direction = 1;
	}
	
	public double GetForce()
	{
		return force;
	}
	
	public void SetForce(double f)
	{
		force = f;
	}
	
	public double GetAngle()
	{
		return angle;
	}
	
	public int GetHit()
	{
		return hit;
	}
	
	public void SetAngle(double a)
	{
		angle = a;
	}
	
	public void SetMove(double m)
	{
		start.SetX(start.GetX()+m);
		start.SetY(terrain.Height(start.GetX()));
	}
	
	public void Copy(Shot s)
	{
		this.SetForce(s.GetForce());
		this.SetAngle(s.GetAngle());
		this.start.Copy(s.start);
		this.target.Copy(s.target);
	}
}

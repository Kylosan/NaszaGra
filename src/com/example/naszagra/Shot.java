package com.example.naszagra;

import java.util.ArrayList;

public class Shot {
	
	Point start, target;
	Terrain terrain;
	private double force, angle, g;
	private int direction;//1 - prawo, -1 - lewo
	private int hit, aihit = 0;
	
	Shot(Point s, Point t, Terrain ter)
	{
		start = new Point();
		target = new Point();
		start.Copy(s);
		target.Copy(t);
		terrain = ter;
		double dist = Math.sqrt(Math.pow((start.GetX()-target.GetX()), 2)+Math.pow((start.GetY()-target.GetY()), 2));
		force = Math.random()*dist*0.2;
		angle = Math.random()*Math.PI/2;
		g=9.81;
		hit = 0;
		aihit = 0;
		
		if(target.GetX()<start.GetX())//okreœla w któr¹ stronê ma strzelaæ gracz komputerowy
			direction = -1;
		else 
			direction = 1;
	}
	
	public Shot() {
		start = new Point();
		target = new Point();
		terrain = AppConstants.GetEngine().terrain;
		if(target.GetX()<start.GetX())//okreœla w któr¹ stronê ma strzelaæ gracz komputerowy
			direction = -1;
		else 
			direction = 1;
		
		g = 9.81;
		hit = 0;
		aihit = 0;
	}
	
	private ArrayList<Point> GetTraj(int help)
	{
		ArrayList<Point> traj = new ArrayList<Point>();
		double t = 0;
		Point p;
		while(true)
		{
			p = new Point();
			p.SetX(direction*force*t*Math.cos(angle) + start.GetX());
			p.SetY(-(force*t*Math.sin(angle) - (g*Math.pow(t,2))/2 - start.GetY())- AppConstants.SCREEN_HEIGHT/32);
			traj.add(p);
			
			if(start.GetX()<target.GetX())
				if(target.GetX() <= p.GetX() && target.GetX() + AppConstants.SCREEN_WIDTH/64 >= p.GetX() && target.GetY() >= p.GetY() && target.GetY() - AppConstants.SCREEN_HEIGHT/16 <= p.GetY())
					this.aihit = 1;
			
			if(terrain.Collision(p))
				break;	
			t+=0.01;
		}
		if(help==1)
		{
			if(start.GetX()<target.GetX() && p.GetX()> terrain.Obstacles[1].X + terrain.Obstacles[1].height && this.aihit != 1)
			{
				if(p.GetX() < target.GetX() && p.GetX() > target.GetX() - 3*AppConstants.SCREEN_WIDTH/64)
					this.aihit = 2;
				if(p.GetX() > target.GetX() && p.GetX() < target.GetX() + 4*AppConstants.SCREEN_WIDTH/64)
					this.aihit = 3;
			}
		}
		
		return traj;
	}

	public ArrayList<Point> GetTrajectory() 
	{ 
		ArrayList<Point> traj = GetTraj(1);
		if(start.GetX() > target.GetX())
			return traj;
		else
		{
			if(this.aihit <= 1)
				return traj;
			else
			{
				Shot temp = new Shot();
				temp.Copy(this);
				int change = 1;
				while(temp.aihit!=1)
				{
					int h = this.aihit;
					if(this.aihit == 2)
						temp.force+=(1/change);
					else if(this.aihit == 3)
						temp.force-=(1/change);
					temp.GetTraj(0);
					if(h != this.aihit)
						change = 10;
				}
				this.aihit = 1;
				return temp.GetTraj(0);
			}	
		}
	}
	
	private double Dist(Point x, Point y)
	{
		return Math.sqrt(Math.pow((x.GetX()-y.GetX()), 2)+Math.pow((x.GetY()-y.GetY()), 2));
	}
	
	private double Distance(Point p)
	{
		double dist = 0;
		
		int i, t = 0, a = 0;
		for(i = 0; i < terrain.Points.length-1; i++)
		{
			if(terrain.Points[i].GetX() <= target.GetX() && terrain.Points[i+1].GetX() > target.GetX())
				t = i;
			if(terrain.Points[i].GetX() <= p.GetX() && terrain.Points[i+1].GetX() > p.GetX())
				a = i;
		}
		
		if(p.GetX()<=0)
		{
			dist += (terrain.Height(0) - p.GetY());
			p = new Point(0,terrain.Height(0));
		}
		
		if(t==a)
			return dist + Dist(target,p);
		
		Point l = new Point();
		Point r = new Point();
		int x, y;
		
		if(a>t)
		{
			l.Copy(target);
			x = t;
			r.Copy(p);
			y = a;
		}
		else
		{
			l.Copy(p);
			x = a;
			r.Copy(target);
			y = t;
		}
		
		dist += Dist(l, terrain.Points[x+1]);
		for(i = x+1; i < y; i++)
			dist += Dist(terrain.Points[i],terrain.Points[i+1]);
		dist += Dist(terrain.Points[y],r);
		
		if(p.GetX()>terrain.Obstacles[0].X)
			dist+=terrain.Obstacles[0].height*2;
		if(p.GetX()>terrain.Obstacles[1].X)
			dist+=terrain.Obstacles[1].height*2;
		
		double h = terrain.Height(p.GetX())-p.GetY();
		if(h>1)
			dist-=h;
		
		return dist;
	}
	
	public double Accuracy()
	{
		//tworzy dyskretn¹ trajektoriê lotu strza³y i sprawdza jak blisko celu ona przeleci
		double t = 0;
		hit = 0;
		Point p = new Point(start.GetX(), start.GetY()- AppConstants.SCREEN_HEIGHT/32);
		while(true)
		{
			p.SetX(direction*force*t*Math.cos(angle) + start.GetX());
			p.SetY(-(force*t*Math.sin(angle) - (g*Math.pow(t,2))/2 - start.GetY())- AppConstants.SCREEN_HEIGHT/32);
			
			if(target.GetX() <= p.GetX() && target.GetX() + AppConstants.SCREEN_WIDTH/64 >= p.GetX() && target.GetY() >= p.GetY() && target.GetY() - AppConstants.SCREEN_HEIGHT/16 <= p.GetY())
				this.hit = 1;
			
			if(terrain.Collision(p))
				break;
			t+=0.01;
		}
		double d = Distance(p);
		return d;
	}
	
	public void SetPlayersPosition(Player player, Player ai)
	{
		this.start.Copy(ai.pos);
		this.target.Copy(player.pos);
		
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
	
	public int GetAIHit()
	{
		return aihit;
	}

	public void SetAngle(double a)
	{
		angle = a;
	}
	
	public void SetDirection(int d)
	{
		direction = d;
	}
	
	public void Copy(Shot s)
	{
		this.SetForce(s.GetForce());
		this.SetAngle(s.GetAngle());
		this.start.Copy(s.start);
		this.target.Copy(s.target);
		this.terrain.Copy(s.terrain);
		this.direction = s.direction;
		this.hit = s.hit;
		this.aihit = s.aihit;
	}
}

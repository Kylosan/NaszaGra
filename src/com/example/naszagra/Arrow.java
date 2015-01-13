package com.example.naszagra;

import java.util.ArrayList;

public class Arrow {
	public ArrayList<Point> trajectory;
	double _x, _y;
	int hit;

	/**
	 * Initiate the x and y parameters to bow x and y coordinates
	 * */
	
	public Arrow(Point start, Point target, Terrain t, float angle, float force)
	{
		Shot shoot = new Shot(start, target, t);
		shoot.SetAngle(angle);
		shoot.SetForce(force);
		trajectory = shoot.GetTrajectory();
		hit = shoot.GetAIHit();
	}

	public double GetX()
	{
		return _x;
	}

	public double GetY()
	{
		return _y;
	}
	public int GetHit()
	{
		return hit;
	}
	
	public void Advance(int ars,float x,float y)
	{
		_x += x-_x * ars;
		_y += y-_y * ars;
	}
	
}

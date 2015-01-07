package com.example.naszagra;

import java.util.ArrayList;

public class Arrow {
	public ArrayList<Point> trajectory;
	double _x, _y,_deltaX, _deltaY;

	/**
	 * Initiate the x and y parameters to Cannon x and y coordinates
	 * */
	public Arrow(float bow_x, float bow_y, float angle, float force)
	{
		Shot shoot = new Shot();
		_x = bow_x;
		_y = bow_y;
		shoot.start.SetX(_x);
		shoot.start.SetY(_y);
		shoot.SetAngle(angle);
		shoot.SetForce(force);
		trajectory = shoot.GetDrawTrajectory();

	}

	public double GetX()
	{
		return _x;
	}

	public double GetY()
	{
		return _y;
	}
	
	public void Advance(int ars,float x,float y)
	{
		_x += x-_x * ars;
		_y += y-_y * ars;
	}
	
}
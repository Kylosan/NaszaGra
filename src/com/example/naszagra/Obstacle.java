package com.example.naszagra;

public class Obstacle {
	
	double X, Y, height, width;
	
	Obstacle(double posX, double posY, double h, double w)
	{
		X = posX;
		Y = posY;
		height = h;
		width = w;
	}
	
	public boolean Collision(Point p)
	{
		if(p.GetX() >= X && p.GetX() <= X + width && p.GetY() >= Y && p.GetY() <= Y + height)
			return true;
		return false;
	}
}

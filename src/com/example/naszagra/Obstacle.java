package com.example.naszagra;

public class Obstacle {
	
	double X, Y, height, width;
	
	Obstacle(double posX, double posY, double w, double h)
	{
		X = posX;
		Y = posY;
		height = h;
		width = w;
	}
	
	public boolean Collision(Point p)
	{
		if(p.GetX() >= this.X && p.GetX() <= this.X + this.width && p.GetY() <= this.Y && p.GetY() >= this.Y - this.height)
			return true;
		return false;
	}
	
	public void Copy(Obstacle o)
	{
		this.X = o.X;
		this.Y = o.Y;
		this.height = o.height;
		this.width=o.width;
	}
}

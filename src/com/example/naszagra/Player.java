package com.example.naszagra;

public class Player {

	Point pos;
	Shot shot;
	int H, W;
	int health;
	
	Player()
	{
		pos = new Point();
		H = 70;
		W = 30;
		health = 100;
	}
	
	Player(double posX, double posY)
	{
		pos = new Point(posX, posY);
		H = 70;
		W = 30;
		health = 100;
	}
	
	public void Move(double newX, double newY)
	{
		pos.SetX(newX);
		pos.SetY(newY);
	}
	
	public void SetShot(Shot s)
	{
		shot.Copy(s);
	}
	
	public double GetX()
	{
		return pos.GetX();
	}
	
	public double GetY()
	{
		return AppConstants.SCREEN_HEIGHT-(pos.GetY()+H);
	}
	
	public double GetDrawX()
	{
		return pos.GetX();
	}
	
	public double GetDrawY()
	{
		return AppConstants.SCREEN_HEIGHT-(pos.GetY()+H);
	}
	
	public Shot GetShot()
	{
		return shot;
	}
}
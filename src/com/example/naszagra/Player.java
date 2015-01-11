package com.example.naszagra;

public class Player {

	Point pos;
	Shot shot;
	int H, W;
	int health;
	
	Player()
	{
		pos = new Point();
		H = AppConstants.SCREEN_HEIGHT/16;
		W = AppConstants.SCREEN_WIDTH/64;
		health = 100;
	}
	
	Player(double posX, double posY)
	{
		pos = new Point(posX, posY);
		H = AppConstants.SCREEN_HEIGHT/16;
		W = AppConstants.SCREEN_WIDTH/64;
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
		return pos.GetY();
	}
	
	public double GetDrawX()
	{
		return pos.GetX();
	}
	
	public double GetDrawY()
	{
		return pos.GetY()-H;
	}
	
	public Shot GetShot()
	{
		return shot;
	}
	
	public void Damage(int c)
	{
		health-=c;
	}
	public void Copy(Player p) 
	{
		this.pos.Copy(p.pos);
		this.shot.Copy(p.GetShot());
		this.H=p.H;
		this.W=p.W;
		this.health=p.health;
	}
}
package com.example.naszagra;

public class Point {
	private double X, Y;
	
	Point ()
	{
		X = Y = 0;
	}
	
	Point (double x, double y)
	{
		X = x;
		Y = y;
	}
	
	public double GetX()
	{
		return X;
	}
	
	public double GetY()
	{
		return Y;
	}
	
	public void SetX(double x)
	{
		this.X = x;
	}
	
	public void SetY(double y)
	{
		this.Y = y;
	}
	
	public void Copy(Point p)
	{
		this.SetX(p.GetX());
		this.SetY(p.GetY());
	}
}

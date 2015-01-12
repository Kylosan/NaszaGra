package com.example.naszagra;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Bow {

	public Bow()
	{
		_width = 42;
		_height = 35;
		_rotation = 0;
	}
	
	static int _width;
	static int _height;
	static float _rotation;
	static float _x, _y;
	static Rect _rect, _irect;
	

	public void SetX(int x)
	{
		_x = x;
	}
	

	public void SetY(int y)
	{
		_y = y;
	}
	

	public float GetX()
	{
		return _x;
	}
	

	public float GetY()
	{
		return _y;
	}
	

	public int GetWidth()
	{
		return _width;
	}

	public int GetHeight()
	{
		return _height;
	}


	public float GetRotation()
	{
		return _rotation;
	}
	
	public void SetRotation(float BowRotationByTouch) 
	{
		_rotation = BowRotationByTouch;
	}
	public Rect iRect(int plX, int plY, Bitmap b)
	{
		int left = plX-GetWidth()/2;
		int top = plY;
		int bottom = top + GetHeight();
		int right = left +GetWidth();
		
		SetX(left);
		SetY(top);
		_irect = new Rect( left , top, right, bottom);  
		return _irect;
	}
	
	public Rect GetRect(int plX, int plY, Bitmap b) 
	{
		//if(_rect == null)
		//{
			int left = plX-10;
			int top = plY;
			int bottom = top + GetHeight();
			int right = left +GetWidth();
			
			SetX(left);
			SetY(top);
			_rect = new Rect( left , top, right, bottom);  
		//}
		
		return _rect;
	}
}
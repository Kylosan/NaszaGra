package com.example.naszagra;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapBank {
Bitmap _bow, _arrow, _aim, _player;
	
	/**
	 * Loads bitmaps from the resources
	 * @param res
	 * 		resources reference
	 * */
	public BitmapBank(Resources res)
	{
		_bow = BitmapFactory.decodeResource(res, R.drawable.bow);
		_arrow = BitmapFactory.decodeResource(res, R.drawable.arrow);
		_aim = BitmapFactory.decodeResource(res, R.drawable.aim_ball);
		_player = BitmapFactory.decodeResource(res, R.drawable.player);
	}
	/**
	 * @return Android Bitmap
	 * */
	public Bitmap GetBow()
	{
		return _bow;
	}

	/**
	 * @return arrow Bitmap
	 * */
	public Bitmap GetArrow()
	{
		return _arrow;
	}
	
	public Bitmap GetPlayer()
	{
		return _player;
	}
	/**
	 * Rotates given bitmap according to passed angle, using Metrix object
	 * @param source
	 * 			Bitmap that needed to be rotated
	 * @param angle
	 * 			Rotation angle
	 * 
	 * @return rotated bitmap
	 * */
	public static Bitmap RotateBitmap(Bitmap source, float angle)
	{
		Matrix matrix = new Matrix();
	    matrix.postRotate(angle);
	    
	    return Bitmap.createBitmap
	    		(
	    				source, 
	    				0, 0,
	    				source.getWidth(), 
	    				source.getHeight(), 
	    				matrix, 
	    				true
	    		);
	}
	public Bitmap GetAim() {
		return _aim;
	}
}

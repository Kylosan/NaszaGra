package com.example.naszagra;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapBank {
Bitmap _bow, _aim, _player, plr,bow,left,right,fire;
	
	/**
	 * Loads bitmaps from the resources
	 * @param res
	 * 		resources reference
	 * */
	public BitmapBank(Resources res)
	{
		left = BitmapFactory.decodeResource(res, R.drawable.left);
		right = BitmapFactory.decodeResource(res, R.drawable.right);
		fire = BitmapFactory.decodeResource(res, R.drawable.fire);
		bow = BitmapFactory.decodeResource(res, R.drawable.bow);
		_bow = BitmapFactory.decodeResource(res, R.drawable.ibow);
		_aim = BitmapFactory.decodeResource(res, R.drawable.aim_ball);
		_player = BitmapFactory.decodeResource(res, R.drawable.player);
		//_player.reconfigure(AppConstants.SCREEN_WIDTH/64, AppConstants.SCREEN_HEIGHT/16,null);
		plr = Bitmap.createScaledBitmap(_player, AppConstants.SCREEN_WIDTH/64, AppConstants.SCREEN_HEIGHT/16, false);
		
	}
	public Bitmap GetLeft()
	{
		return left;
	}
	public Bitmap GetRight()
	{
		return right;
	}
	public Bitmap GetFire()
	{
		return fire;
	}
	public Bitmap GetBow()
	{
		return bow;
	}

	public Bitmap GetiBow()
	{
		return _bow;
	}
	
	
	public Bitmap GetPlayer()
	{
		return plr;
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

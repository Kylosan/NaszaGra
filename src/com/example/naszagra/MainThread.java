package com.example.naszagra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;


public class MainThread extends Thread {
	
	SurfaceHolder _surfaceHolder;
    Paint _backgroundPaint;

    long _sleepTime;
    
    //Delay amount between screen refreshes
    final long  DELAY = 2;
    
    boolean  _isOnRun;

    public MainThread(SurfaceHolder surfaceHolder, Context context) 
    {
        _surfaceHolder = surfaceHolder;

        //black painter below to clear the screen before the game is rendered
        _backgroundPaint = new Paint();
        _backgroundPaint.setARGB(255, 255, 255, 255);
        _isOnRun = true;
    }


    @Override
    public void run() 
    {
    	
		while (_isOnRun) 
		{
			
			AppConstants.GetEngine().Update();
			
			
			Canvas canvas = _surfaceHolder.lockCanvas(null);
			
		    if (canvas != null) 
		    {
				
				synchronized (_surfaceHolder) 
				{
				     canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), _backgroundPaint);
				     AppConstants.GetEngine().Draw(canvas);
				}
				
				
		        _surfaceHolder.unlockCanvasAndPost(canvas);
		    }
		    
		    
			try 
			{
			    Thread.sleep(DELAY);
			} 
			catch (InterruptedException ex) 
			{
			    
			}
		}
    }


    public boolean IsRunning()
    {
   	 	return _isOnRun;
    }

    public void SetIsRunning(boolean state)
    {
      	 _isOnRun = state;
    }

}
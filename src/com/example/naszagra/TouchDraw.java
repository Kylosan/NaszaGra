package com.example.naszagra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("DrawAllocation") public class TouchDraw extends SurfaceView implements SurfaceHolder.Callback 
{
	SurfaceHolder _surfaceHolder;
    Context _context;

    private MainThread _Thread;
 
    public TouchDraw(Context context, Engine gEngine)
    {
        super(context);
        
        _context = context;
        InitView();
    }
    

    void InitView()
    {
		SurfaceHolder holder = getHolder();
		holder.addCallback( this);

		_Thread = new MainThread(holder, _context);
		setFocusable(true);
   }
 
   @Override 
   public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
   {
   }

   @Override 
   public void surfaceDestroyed(SurfaceHolder arg0) 
   {
       _Thread.SetIsRunning(false);	
       AppConstants.StopThread(_Thread);
   }

   @Override 
   public void surfaceCreated(SurfaceHolder arg0) 
   {
       if(!_Thread.IsRunning())
       {
           _Thread = new MainThread(getHolder(), _context);
           _Thread.start();
       }
       else
       {
           _Thread.start();
       }
   }
   public Thread GetThread()
   {
	   return _Thread;
   }
}
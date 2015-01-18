package com.example.naszagra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class Touching extends Activity {

    private static final int DIALOG_WIN = 1;
	private static final int DIALOG_LOST = 0;
	public SurfaceView view;
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
	 super.onCreate(savedInstanceState);
     
	 AppConstants.Initialization(this.getApplicationContext());
     view = new TouchDraw(this, AppConstants.GetEngine());
     setContentView(view);
 }
	public Touching()
	{
		
	}
	public Thread getthread()
	{
		return ((TouchDraw) view).GetThread();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		 super.onTouchEvent(event);
		int action = event.getAction();
		if(AppConstants.GetEngine().turn)
		switch (action) 
		{
			case MotionEvent.ACTION_DOWN:
			{
				OnActionDown(event);
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				OnActionUp(event);
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				OnActionMove(event);
				break;
			}
			default:break;
		}
		return false;
	}
	

	private void OnActionMove(MotionEvent event) 
	{
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		
		
		if(GetIfTouchInTheZone(x, y))
		{
			 AppConstants.GetEngine().SetBowRotaion(x, y);
		}
		
		AppConstants.GetEngine().SetLastTouch(event.getX(), event.getY());
	}


	private boolean GetIfTouchInTheZone(int x, int y) 
	{
		return RotationHandler.CheckIfTouchIsInTheZone(x,y,  AppConstants.GetEngine().getBow());
	}
	
	private void OnActionUp(MotionEvent event) 
	{
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		
		if(GetIfTouchInTheZone(x, y))
		{
			 AppConstants.GetEngine().SetBowRotaion(x, y); 
		}
	}

	@SuppressWarnings("static-access")
	private void OnActionDown(MotionEvent event) 
	{
		float x = event.getX();
		float y = event.getY();
		
		if(x>0&&x<100 && y>0 && y<100)
		{
			AppConstants.GetEngine().PlayerPos(-3, 0);
		}
		else if(x>120&&x<200 && y>0 && y<100)
		{
			AppConstants.GetEngine().PlayerPos(3, 0);
		}
		
		else if(x>0&&x<AppConstants.SCREEN_WIDTH && y>AppConstants.SCREEN_HEIGHT-50 && y<AppConstants.SCREEN_HEIGHT)
		{
			AppConstants.GetEngine().powermeter(x);
		}
		else if(x>AppConstants.SCREEN_WIDTH-100&&x<AppConstants.SCREEN_WIDTH && y>0 && y<100)
		{
			AppConstants.GetEngine().CreateNewArrow();
			AppConstants.GetEngine().powreset();
			if(AppConstants.GetEngine().arrows.get(0).GetHit()==1)
				AppConstants.GetEngine().hitAI = true;
		}
		 	
	}
	@Override
	protected Dialog onCreateDialog(int id)
	{
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());
		builder.setCancelable(false);
		switch(id)
		{
		case DIALOG_LOST:
			builder.setTitle(R.string.dialresult);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(R.string.Lost);
			builder.setNeutralButton(R.string.dlpos, new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int arg1)
				{
					finish();
				}
			});
		case DIALOG_WIN:
			builder.setTitle(R.string.dialresult);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(R.string.Win);
			builder.setNeutralButton(R.string.dlpos, new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int arg1)
				{
					finish();
				}
			});
			
			break;
		}
		return builder.create();
	}
	
	@SuppressWarnings("deprecation")
	public void getwin()
	{
		showDialog(DIALOG_WIN);
	}
	@SuppressWarnings("deprecation")
	public void getlost()
	{
		showDialog(DIALOG_LOST);
	}
}
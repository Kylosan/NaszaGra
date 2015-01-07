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
}
 /*extends View {
	private Paint m_paint;
	Player player;
	float m_x0, m_y0, sx0,sy0,fx0,fy0;
	float Px, Py, arx,ary;
	Point arr;
	Path mpath = new Path();
	
	// promieñ kó³ek
	float radius = 30;
	float angle;
	// informacje o tym który palec co dotyka
	public boolean t0;

	// Kostruktor pierwszy
	public TouchDraw(Context context)
	{
		super(context);
		Init();
	}
	// Konstruktor drugi
	public TouchDraw(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Init();
	}
	// Kostruktor trzeci
	public TouchDraw(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		Init();
	}
	
	private void Init()
	{ 
		arr = new Point();
		player = new Player(100.0,300.0);
		// pocz¹tkowe ustawienia malowania
		m_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		m_paint.setColor(Color.TRANSPARENT);
		Px = (float) player.GetX();
		Py = (float) player.GetY();
		
		
	}
	public void PlayerPos(float plaX, float plaY)
	{
		Px += plaX;
		Py += plaY;
		player.Move(Px, Py);
		invalidate();
	}
	public void projectile(float pow)
	{
		player.shot.start.SetX(Px);
		player.shot.start.SetY(Py);
		player.shot.SetForce(pow);
		
		r.run();
		
		invalidate();
	}
	final Runnable r = new Runnable()
	{
		public void run()
		{
			//ArrayList<Point> trajectory = player.shot.GetTrajectory();
			
			//arr= trajectory.get(i);
			//arx+=0.1;//(float) arr.GetX()-arx;
			//ary=100;//(float) arr.GetY()-ary;   hehehe xD
			
			for(int i=0; i< 100;i++)
		        	   	 {
		   new Runnable() { 
		         public void run() {
		        		
		        	   		 arx+=2; 
		        	   		 ary=100; 
		        	   		 invalidate();
		        	   	  
				        	Log.d("test", String.valueOf(arx) + "\n");  
		         }
		    };
		    }
		    
	
		    
		 
		}
		
	};
	public void setSpos(float cX, float cY, int pointer)
	{
		float sx = cX;
		float sy = cY;
		
		sx0=sx;
		sy0=sy;
		
		invalidate();
	}

	public void setPos(float coordX, float coordY, int pointer)
	{
		float x = coordX;
		float y = coordY;

				m_x0 = x;
				m_y0 = y;

		invalidate();
	}
	
	
	@Override
	public void onDraw(Canvas canvas)
	{
		Log.d("odsw", "odsw" + "\n");
		RectF rectF = new RectF();
	    rectF.set(Px,Py,Px+50,Py+50);
		// po kó³ku dla ka¿dego palca
		super.onDraw(canvas);
		m_paint.setColor(Color.GREEN);
		canvas.drawCircle(50, 50, 50, m_paint);
		m_paint.setColor(Color.RED);
		canvas.drawCircle(150, 50, 50, m_paint);
		m_paint.setColor(Color.MAGENTA);
		canvas.drawRect(rectF, m_paint);
		m_paint.setColor(Color.BLUE);
		canvas.drawRect(300, 350, 400, 400, m_paint);
	
		canvas.drawCircle(arx, ary, 10, m_paint);
		canvas.drawLine(Px, Py, arx, ary, m_paint);
		mpath.moveTo(Px, Py);
		mpath.lineTo(arx, ary);
		if(t0)
		{
			m_paint.setColor(Color.TRANSPARENT);
			canvas.drawCircle(m_x0, m_y0, radius, m_paint);
			
		}
		
	}
}
*/
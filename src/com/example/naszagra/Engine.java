package com.example.naszagra;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

public class Engine 
{
	static final int ARROWS_COUNT = 1; 
	static Bow bow;
	static List<Arrow> arrows;
	static final Object sync = new Object();
	static float _lastTouchedX, _lastTouchedY;
	static final float DO_NOT_DRAW_X = Float.MAX_VALUE;
	static final float DO_NOT_DRAW_Y = Float.MAX_VALUE;
	float Px, Py,arx5,ary5, arx,ary,terx,tery, powx=0;
	Point arr,arr5,terr;
	Player player;
	Player AI;
	Terrain terrain;
	Path mpath = new Path();
	Boolean turn;
	AlgGen ai;
	Paint paint;
		
	public Engine()
	{
		start();
		AI = new Player(1,1);
		player = new Player(1,1);
		arrows = new LinkedList<Arrow>();
		paint = new Paint();
		bow = new Bow();
		terrain = new Terrain();
	}
	
	private void start() {
		AI.Move(AppConstants.SCREEN_WIDTH-AI.W*2, terrain.Height(AppConstants.SCREEN_WIDTH-AI.W*2));
		player.Move(player.W*2, terrain.Height(player.W*2));
		turn=true;
	}

	public void Update()
	{
		AdvanceArrows();
		DrawPow(powx);
		AIgame();
	}
	
	private void AIgame() 
	{
		if(turn==false)
		{
			//IZ IT GOOD?
			ai = new AlgGen(0, player, AI, terrain);
			turn=true;
		}
		
	}


	private void AdvanceArrows() 
	{	
		synchronized (sync) 
		{
			for(Arrow a : arrows)
			{
				for(int i=5;i<a.trajectory.size();i++)
				{
					arr5 = a.trajectory.get(i-5);
					arx5=(float) arr5.GetX();
					ary5=(float) arr5.GetY();
					arr= a.trajectory.get(i);
					arx=(float) arr.GetX();
					ary=(float) arr.GetY();
				a.Advance(ARROWS_COUNT,arx,ary);
				}
			}
			
		}
	}
	
	public void PlayerPos(float plaX, float plaY)
	{
		Px += plaX;
		Py += plaY;
		bow.SetX((int) plaX);
		bow.SetY((int) plaY);		
		player.Move(Px, Py);
	}

	public void Draw(Canvas canvas)
	{
		DrawControls(canvas);
		DrawPlayer(canvas);
		DrawBow(canvas);
		DrawArrows(canvas);
		DrawAim(canvas);
		DrawTerrain(canvas);

	}
	private void DrawTerrain(Canvas canvas) 
	{
		paint.setColor(Color.BLACK);
		
		ArrayList<Point> points = terrain.GetDrawTerrain();
		for(int i=0;i< points.size();i++)
		{
			terr=points.get(i);
			terx=(float) terr.GetX();
			tery=(float) terr.GetY();
			mpath.lineTo(terx, tery);
			mpath.moveTo(terx, tery);
		}
	}


	private void DrawPlayer(Canvas canvas) {
		Bitmap _player = AppConstants.GetBitmapsBank().GetPlayer();
		canvas.drawBitmap(_player, (float)player.GetDrawX(), (float)player.GetDrawY(), paint);
		
	}


	private void DrawControls(Canvas canvas) {
		paint.setColor(Color.GREEN);
		canvas.drawCircle(50, 50, 50, paint);
		paint.setColor(Color.RED);
		canvas.drawCircle(150, 50, 50, paint);
		paint.setColor(Color.BLUE);
		canvas.drawCircle(AppConstants.SCREEN_WIDTH-50,50,50,paint);
	}

	public void powermeter(float p)
	{
		powx = p;
	}
	public void powreset()
	{
		powx=0;
	}
	
	public float getpower()
	{
		return powx;
	}
	private void DrawPow(float px) 
	{
		Canvas canvas=new Canvas();
	    canvas.drawRect(0, AppConstants.SCREEN_HEIGHT-10, px, AppConstants.SCREEN_HEIGHT,paint);
	}

	private void DrawAim(Canvas canvas) 
	{
		//Doesn't draws on touch ACTION_UP event, only on ACTION_DOWN or ACTION_MOVE
		if(_lastTouchedX != DO_NOT_DRAW_X
				&& _lastTouchedY != DO_NOT_DRAW_Y)
		{
			Bitmap bitmap = AppConstants.GetBitmapsBank().GetAim();
			canvas.drawBitmap(bitmap, _lastTouchedX, _lastTouchedY, paint);
		}
	}
	private void DrawArrows(Canvas canvas) 
	{
		synchronized (sync) 
		{
			
				paint.setStrokeWidth(5);
				paint.setColor(Color.BLUE);
				canvas.drawLine(arx5, ary5, arx, ary, paint);
			
		}
	}

	private void DrawBow(Canvas canvas) 
	{
		Bitmap _bow = BitmapBank.RotateBitmap( AppConstants.GetBitmapsBank().GetBow(),
				bow.GetRotation());
		
		Rect rect = bow.GetRect((int)player.GetDrawX(), (int)player.GetDrawY(), _bow);
		canvas.drawBitmap(_bow, null, rect, paint);
	}
	
	public void SetBowRotaion(int touch_x, int touch_y) 
	{
		float bowRotation = RotationHandler
				.BowRotationByTouch(touch_x, touch_y, bow);
		
		bow.SetRotation(bowRotation);
	}

	public void CreateNewArrow(int touchX, int touchY, float force) 
	{
		synchronized (sync) 
		{
			arrows.add
			(
					new Arrow
					(
							bow.GetX(), 
							bow.GetY(),
							bow.GetRotation(), 
							force
					)
			);
		}
	}
	public Bow getBow() 
	{
		return bow;
	}
	
	public void SetLastTouch(float x, float y)
	{
		_lastTouchedX = x;
		_lastTouchedY = y;
	}
}
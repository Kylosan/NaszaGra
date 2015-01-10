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
	float Px, Py,arx5,ary5, arx,ary, powx=100;
	float terx=0,tery=0;
	int indeks=10;
	Point arr,arr5,terr;
	Player player;
	Player AI;
	Terrain terrain;
	Path mpath = new Path();
	Boolean turn;
	AlgGen ai;
	Paint paint;
	Paint tpaint;
	
	public Engine()
	{
		AI = new Player(1,1);
		player = new Player(1,1);
		arrows = new LinkedList<Arrow>();
		paint = new Paint();
		tpaint = new Paint();
		bow = new Bow();
		terrain = new Terrain();
		terr=new Point();
		
		AI.Move(AppConstants.SCREEN_WIDTH-AI.W*2, terrain.Height(AppConstants.SCREEN_WIDTH-AI.W*2));
		player.Move(player.W*2, terrain.Height(player.W*2));//TO MUSI BYÆ PRZD STWORZENIEM AI
		
		ai = new AlgGen(20, player, AI, terrain);
		start();
	}
	
	private void start() {
		//AI.Move(AppConstants.SCREEN_WIDTH-AI.W*2, terrain.Height(AppConstants.SCREEN_WIDTH-AI.W*2));
		//player.Move(player.W*2, terrain.Height(player.W*2));
		turn=false;//true;
	}

	public void Update()
	{
		AdvanceArrows();
		DrawPow();
		AIgame();
	}
	
	private void AIgame() 
	{
		if(turn==false)
		{
			for(int it = 0; it <1; it++)//Dopasowaæ iloœæ iteracji
			{
				ai.Iteration();
//				Shot BS = new Shot();
//				BS.Copy(ai.BestShot());
//				double uuu = BS.Accuracy();
//				ArrayList<Point> points = BS.GetTrajectory();
//				int a = 0, b = 0;
//				a = b+(int)uuu;
			}
		}
		turn = true;
	}

	private void DrawTraj(Canvas canvas)//tymczasowe 
	{
		tpaint.setColor(Color.RED);
		tpaint.setStrokeWidth(3);
		tpaint.setStyle(Paint.Style.STROKE);
		mpath = new Path();
		
		Shot BS = new Shot();
		BS.Copy(ai.BestShot());
		
		ArrayList<Point> points = BS.GetTrajectory();
		mpath = new Path();
		
		for(int i=0; i<points.size(); i++)
		{
			
			terr.Copy(points.get(i));
			terx=(float) terr.GetX();
			tery=(float) terr.GetY();
			if(i==0)
				mpath.moveTo(terx, tery);
			else
				mpath.lineTo(terx, tery);
		}
		canvas.drawPath(mpath, tpaint);
		
		Paint text = new Paint();
		text.setTextSize(40);
		canvas.drawText(""+BS.Accuracy(), 1000, 1000, text);
	}

	private void AdvanceArrows() 
	{	
		synchronized (sync) 
		{
			for(Arrow a : arrows)
			{
				if(indeks<a.trajectory.size())
				{
					arr5 = a.trajectory.get(indeks-10);
					arx5=(float) arr5.GetX();
					ary5=(float) arr5.GetY();
					arr= a.trajectory.get(indeks);
					arx=(float) arr.GetX();
					ary=(float) arr.GetY();
					a.Advance(ARROWS_COUNT,arx,ary);
					indeks++;
				}
			}
		}
	}
	
	public void PlayerPos(float plaX, float plaY)
	{
		if(Px+plaX+player.W < terrain.Obstacles[0].X)
		{
			Px += plaX;
			bow.SetX((int) plaX);
			bow.SetY((int) plaY);		
			player.Move(Px, terrain.Height(Px));
		}
	}

	public void Draw(Canvas canvas)
	{
		DrawControls(canvas);
		DrawPlayer(canvas);
		DrawPC(canvas);
		DrawBow(canvas);
		DrawArrows(canvas);
		DrawAim(canvas);
		DrawTerrain(canvas);
		DrawTraj(canvas);
	}
	private void DrawPC(Canvas canvas) {
		Bitmap _player = AppConstants.GetBitmapsBank().GetPlayer();
		canvas.drawBitmap(_player, (float)player.GetDrawX(), (float)player.GetDrawY(), paint);
	}

	private void DrawTerrain(Canvas canvas) 
	{
		tpaint.setColor(Color.BLACK);
		tpaint.setStrokeWidth(3);
		tpaint.setStyle(Paint.Style.STROKE);
		ArrayList<Point> points = terrain.GetTerrain();
		mpath = new Path();
		
		for(int i=0; i<points.size(); i++)
		{
			
			terr.Copy(points.get(i));
			terx=(float) terr.GetX();
			tery=(float) terr.GetY();
			if(i==0)
				mpath.moveTo(terx, tery);
			else
				mpath.lineTo(terx, tery);
		}
		canvas.drawPath(mpath, tpaint);
		
		points = terrain.GetObstacles();
		for(int i = 0; i<=1; i++)
		{
			mpath = new Path();

			terr.Copy(points.get(0+4*i));
			terx=(float) terr.GetX();
			tery=(float) terr.GetY();
			mpath.moveTo(terx, tery);
			terr.Copy(points.get(1+4*i));
			terx=(float) terr.GetX();
			tery=(float) terr.GetY();
			mpath.lineTo(terx, tery);
			terr.Copy(points.get(2+4*i));
			terx=(float) terr.GetX();
			tery=(float) terr.GetY();
			mpath.lineTo(terx, tery);
			terr.Copy(points.get(3+4*i));
			terx=(float) terr.GetX();
			tery=(float) terr.GetY();
			mpath.lineTo(terx, tery);
			canvas.drawPath(mpath, tpaint);
		}
	}


	private void DrawPlayer(Canvas canvas) {
		Bitmap _player = AppConstants.GetBitmapsBank().GetPlayer();
		canvas.drawBitmap(_player, (float)AI.GetDrawX(), (float)AI.GetDrawY(), paint);
		
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
		//powx=0;
		//powx += p;
	}
	public void powreset()
	{
		//powx=0;
	}
	
	public float getpower()
	{
		return powx;
	}
	private void DrawPow() 
	{
		Canvas canvas=new Canvas();
	    canvas.drawRect(0, AppConstants.SCREEN_HEIGHT-50, powx, AppConstants.SCREEN_HEIGHT,paint);
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
		Bitmap _bow = BitmapBank.RotateBitmap( AppConstants.GetBitmapsBank().GetBow(),bow.GetRotation());
		Rect rect = bow.GetRect((int)player.GetDrawX(), (int)player.GetDrawY(), _bow);
		canvas.drawBitmap(_bow, null, rect, paint);
	}
	
	public void SetBowRotaion(int touch_x, int touch_y) 
	{
		float bowRotation = RotationHandler
				.BowRotationByTouch(touch_x, touch_y, bow);
		
		bow.SetRotation(bowRotation);
	}

	public void CreateNewArrow(float x, float y) 
	{
		indeks = 10;
		synchronized (sync) 
		{
			arrows.add
			(
					new Arrow
					(
							bow.GetX(), 
							bow.GetY(),
							bow.GetRotation(), 
							powx
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
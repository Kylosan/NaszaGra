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
	static List<Arrow> arrows;
	static final Object sync = new Object();
	static float _lastTouchedX, _lastTouchedY;
	static final float DO_NOT_DRAW_X = Float.MAX_VALUE;
	static final float DO_NOT_DRAW_Y = Float.MAX_VALUE;
	float Px, Py,arx5,ary5, arx,ary, powx=100;
	float terx=0,tery=0;
	int indeks=10,i;
	Bow bow,ibow;
	Point arr,arr5,terr;
	Player player,AI;
	Terrain terrain;
	Path mpath = new Path();
	Boolean turn, controls=true;
	Boolean hitP = false, hitAI = false;
	AlgGen ai;
	Paint paint,tpaint;
	Touching touch;
	
	public Engine()
	{
		touch = new Touching();
		terrain = new Terrain();
		AI = new Player(1,1);
		player = new Player(1,1);
		arrows = new LinkedList<Arrow>();
		paint = new Paint();
		tpaint = new Paint();
		bow = new Bow();
		ibow = new Bow();
		terr = new Point();
		
		AI.Move(AppConstants.SCREEN_WIDTH-AI.W*2, terrain.Height(AppConstants.SCREEN_WIDTH-AI.W*2));
		player.Move(player.W*2, terrain.Height(player.W*2));//TO MUSI BYÆ PRZD STWORZENIEM ai
		Px = player.W*2;
		ai = new AlgGen(20, player, AI, terrain);
		start();
	}
	
	private void start() {
		
		turn=true;
	}

	public void Update()
	{
		AdvanceArrows();
		AIgame();
		HealthCheck();
	}
	
	
	private void HealthCheck() {
		if(player.health==0)
		{
			//touch.getlost();
			//AppConstants.StopThread(touch.getthread());
		}
		else if(AI.health==0)
		{
			//touch.getwin();
		}
		
	}

	private void AIgame() 
	{
		if(turn==false)
		{
			hitP=true;
			ai.Update(player, AI);
			for(int it = 0; it < ai.HowManyIt(); it++)
				ai.Iteration();
			IArrow();
			turn = true;
		}
		
	}
	
	public void DrawHealth(Canvas canvas)
	{
		paint.setColor(Color.RED);
		tpaint.setColor(Color.RED);
		int w = AppConstants.SCREEN_WIDTH;
		
		mpath = new Path();
		mpath.moveTo((float)(w*0.3),10);
		mpath.lineTo((float)(w*0.3)+100, 10);
		mpath.lineTo((float)(w*0.3)+100, 50);
		mpath.lineTo((float)(w*0.3), 50);
		mpath.lineTo((float)(w*0.3), 10);
		canvas.drawPath(mpath, tpaint);
		canvas.drawRect((float)(w*0.3), (float)10, (float)(w*0.3)+player.health, (float)50, paint);
		
		mpath = new Path();
		mpath.moveTo((float)(w*0.7),10);
		mpath.lineTo((float)(w*0.7)+100, 10);
		mpath.lineTo((float)(w*0.7)+100, 50);
		mpath.lineTo((float)(w*0.7), 50);
		mpath.lineTo((float)(w*0.7), 10);
		canvas.drawPath(mpath, tpaint);
		canvas.drawRect((float)(w*0.7), (float)10, (float)(w*0.7)+AI.health, (float)50, paint);
	}

	private void AdvanceArrows() 
	{	
		double p; 
		synchronized (sync) 
		{
			for(Arrow a : arrows)
			{
				p = a.trajectory.get(0).GetX();
				if(indeks<a.trajectory.size())
				{
					arr5 = a.trajectory.get(indeks-10);
					arx5=(float) arr5.GetX();
					ary5=(float) arr5.GetY();
					arr= a.trajectory.get(indeks);
					arx=(float) arr.GetX();
					ary=(float) arr.GetY();
					a.Advance(arx,ary);
					indeks+=10;
					turn = true;
				}
				else
				{
					if(p < AppConstants.SCREEN_WIDTH/2)
					{
						if(hitAI)
							AI.Damage(10);
						hitAI=false;
						turn = false;
						controls = false;
					}
					else
					{
						if(ai.BestShot().GetHit()==1 && hitP)
							player.Damage(10);
						hitP=false;
						controls = true;
					}
				}
			}
		}
	}
	
	public void PlayerPos(float plaX, float plaY)
	{
		if(controls)
		{
			if(Px+plaX+player.W < terrain.Obstacles[0].X)
			{
				Px += plaX;
				bow.SetX((int) plaX);
				bow.SetY((int) plaY);		
	
				player.Move(Px, terrain.Height(Px));
	
			}
		}
	}

	public void Draw(Canvas canvas)
	{
		if(controls)
		{
			DrawControls(canvas);
			DrawAim(canvas);
			DrawPow(canvas);
		}
		DrawPlayer(canvas);
		DrawPC(canvas);
		DrawiBow(canvas);
		DrawBow(canvas);
		DrawArrows(canvas);
		DrawTerrain(canvas);
		DrawHealth(canvas);
	}
	
	
	private void DrawPC(Canvas canvas) {
		Bitmap _player = AppConstants.GetBitmapsBank().GetPlayer();
		canvas.drawBitmap(_player, (float)AI.GetX(), (float)AI.GetDrawY(), paint);
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
		canvas.drawBitmap(_player, (float)player.GetX(), (float)player.GetDrawY(), paint);
	}


	private void DrawControls(Canvas canvas) {

		canvas.drawBitmap(AppConstants.GetBitmapsBank().GetLeft(), 0, 0, paint);

		canvas.drawBitmap(AppConstants.GetBitmapsBank().GetRight(), 100, 0, paint);

		canvas.drawBitmap(AppConstants.GetBitmapsBank().GetFire(), AppConstants.SCREEN_WIDTH-100, 0, paint);
	}

	public void powermeter(float p)
	{
		powx=0;
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
	private void DrawPow(Canvas canvas) 
	{
		paint.setColor(Color.RED);
		tpaint.setColor(Color.BLUE);
		int w = AppConstants.SCREEN_WIDTH;
		int h = AppConstants.SCREEN_HEIGHT;
		
		mpath = new Path();
		mpath.moveTo(0,(float)h-52);
		mpath.lineTo((float)w, h-52);
		mpath.lineTo((float)w, h);
		mpath.lineTo(0, (float)h);
		mpath.lineTo(0,(float)h-52);
		canvas.drawPath(mpath, tpaint);
	    canvas.drawRect(0, AppConstants.SCREEN_HEIGHT-50, powx, AppConstants.SCREEN_HEIGHT,paint);
	}

	private void DrawAim(Canvas canvas) 
	{
		
			Bitmap bitmap = AppConstants.GetBitmapsBank().GetAim();
			canvas.drawBitmap(bitmap, _lastTouchedX-(AppConstants.GetBitmapsBank()._aim.getWidth()/2), _lastTouchedY-(AppConstants.GetBitmapsBank()._aim.getHeight()/2), paint);
		
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
		//
		Rect rect = bow.GetRect((int)player.GetX(), (int)player.GetDrawY(), _bow);
		//
		canvas.drawBitmap(_bow, null, rect, paint);
		//
	}
	private void DrawiBow(Canvas canvas) 
	{
		Bitmap _ibow = BitmapBank.RotateBitmap( AppConstants.GetBitmapsBank().GetiBow(),(float)Math.toDegrees(ai.BestShot().GetAngle())-90);
		Rect irect = ibow.iRect((int)AI.GetX(), (int)AI.GetDrawY(), _ibow);
		canvas.drawBitmap(_ibow, null, irect, paint);
	}
	
	public void SetBowRotaion(int touch_x, int touch_y) 
	{
		if(controls)
		{
			float bowRotation = RotationHandler.BowRotationByTouch(touch_x, touch_y, bow);
		
			bow.SetRotation(bowRotation);
		}
	}

	public void CreateNewArrow() 
	{
		if(controls)
		{
			arrows.clear();
			indeks = 10;
			float b = bow.GetRotation();
			float ang = (float) Math.toRadians(90-b);
			synchronized (sync) 
			{
				arrows.add
				(
						new Arrow
						(
								new Point(bow.GetX()+player.W,bow.GetY()+player.H*0.7),
								AI.pos,
								terrain,
								ang,
								powx/8
						)
				);
			}
		}
	}
	
	public void IArrow()
	{
		arrows.clear();
		indeks = 10;
		synchronized (sync) 
		{
			arrows.add
			(
					new Arrow
					(
							AI.pos,
							player.pos,
							terrain,
							(float) ai.BestShot().GetAngle(), 
							(float) ai.BestShot().GetForce()
					)
			);
		}
	}
	
	public Bow getiBow()
	{
		return ibow;
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
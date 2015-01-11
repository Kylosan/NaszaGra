package com.example.naszagra;

public class RotationHandler {
	/*Angle constants*/
	public static final int ANGLE_180 = 180;
	public static final int ANGLE_360 = 360;
	public static final int ANGLE_270 = 270;
	public static final int ANGLE_90 = 90;
	public static final int ANGLE_0 = 0;
	
	public static final int TOLERANCE = 35;
	
	public static float BowRotationByTouch(float touch_x, float touch_y, Bow bow)
	{
		float result = bow.GetRotation();
		
		if(CheckIfTouchIsInTheZone(touch_x, touch_y, bow))
		{
			if(CheckIsOnLeftSideScreen(touch_x))
			{
				float Opposite = touch_x - bow.GetX();
				float Adjacent  = bow.GetY() - touch_y;
				
				double angle = Math.atan2(Opposite, Adjacent);
				result = (float)Math.toDegrees(angle);
			}
			else
			{
				
				float Opposite = bow.GetX() - touch_x;
				float Adjacent  = bow.GetY() - touch_y;
				
				double angle = Math.atan2(Opposite, Adjacent);
				result = ANGLE_360 - (float)Math.toDegrees(angle) ;
			}
		}
		
		return result;
	}

	private static boolean CheckIsOnLeftSideScreen(float touch_x) 
	{
		return touch_x > AppConstants.SCREEN_WIDTH / 2;
	}

	public static boolean CheckIfTouchIsInTheZone(float touch_x, float touch_y, Bow bow) 
	{
		return touch_y + TOLERANCE < bow.GetY();
	}
}

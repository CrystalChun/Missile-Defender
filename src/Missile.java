import java.awt.*;

/**
 * The missile class 
 * @author Crystal Chun ID# 012680952
 *
 */
public class Missile extends Rectangle
{
	/**The missile's starting point*/
	private Point start;
	/**The missile's current location*/
	private Point.Double location;
	/**The missile's target (the end)*/
	private Point end;
	/**The missile's speed*/
	private int speed;
	/**The color of the missile*/
	private Color color;
	/**The type of missile it is (0 = defender, 1 = attacker)*/
	private int type;
	/**Whether this missile is active or not*/
	private boolean active;
	/**The x-distance from start to end*/
	private double dx;
	/**The y-distance from start to end*/
	private double dy;	
	/**The magnitude (distance) from start to end*/
	private double magnitude;

	/**
	 * Constructs the missile object
	 * @param start The missile's starting point
	 * @param end The missile's ending point
	 * @param speed The speed the missile is traveling
	 * @param type The type of missile it is (0 = defender, 1 = attacker)
	 * @param color The color of the missile
	 */
	public Missile(Point start, Point end, int speed, int type, Color color)
	{
		this.start = start;
		
		this.end = end;
		this.speed = speed;
		this.type = type;
		this.color = color;
		active = true;
		
		//Initializes the current location as the start location
		Point.Double currentLocation = new Point.Double(start.getX(), start.getY());
		this.location = currentLocation;
		
		dx = end.getX() - start.getX();
		dy = end.getY() - start.getY();
		
		//Magitude = Square root of ((dx^2) + (dy^2))
		magnitude = Math.sqrt((dx * dx) + (dy * dy));
	}
	
	/**
	 * Moves the missiles
	 */
	public void move()
	{
		//The new points
		double newX = dx * (speed/magnitude);
		double newY = dy * (speed/magnitude);
	
		location.setLocation(newX + location.getX(), newY + location.getY());
		
		//Tests if the missile reached its target
		switch(type)
		{
			case 0:		//Defender
						if(location.getY() < end.getY())
						{
							active = false;
						}
						break;
			case 1:		//Attacker
						if(location.getY() > end.getY())
						{
							active = false;
						}
						break;
		}
	}
	
	/**
	 * Draws the missile 
	 * @param g The graphics component
	 */
	public void draw(Graphics g)
	{
		if(isActive())
		{
			g.setColor(color);
			g.drawLine((int)start.getX(), (int)start.getY(), 
					   (int)location.getX(), (int)location.getY());
			
			move();
		}	
	}
	
	/**
	 * Gets the type of missile this is (0 = defender, 1 = attacker)
	 * @return The type of missile this is (0 or 1)
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * Gets whether this missile is active or not
	 * @return True if this missile is active or false
	 * if it's not active
	 */
	public boolean isActive()
	{
		return active;
	}
	
	/**
	 * Gets the missile's current location
	 * @return The missile's current location
	 */
	public Point.Double getLocPoint()
	{
		return this.location;
	}
	
	/**
	 * Gets where the missile ends
	 * @return The point the missile ends (target)
	 */
	public Point getEndPoint()
	{
		return this.end;
	}
	
	/**
	 * Sets whether or not this missile is active
	 * @param active True or false 
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}
}

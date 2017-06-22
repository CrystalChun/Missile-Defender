import java.awt.*;

/**
 * Is an explosion object that expands
 * until a certain range and is active
 * for that range.
 * @author Crystal Chun	ID# 012680952
 *
 */
public class Explosion extends Rectangle
{
	/**Is true if the missile size is less than the specified size, and false otherwise*/
	private boolean expanding;
	/**Is true if the missile size is less than the specified size, and false otherwise*/
	private boolean active;
	/**Holds which type of missile this explosion came from*/
	private int type;
	/**The original x location of the explosion*/
	private double x;
	/**The original y location of the explosion*/
	private double y;
	/**Holds which color to color this explosion*/
	private int counter;
	/**Holds all the colors of the explosion*/
	private Color [] colors;

	/**
	 * Constructs the explosion
	 * @param p The point where it starts
	 * @param type The type of missile the explosion came from
	 * 0 if it's a defender missile or 1 if it's an attacker missile.
	 */
	public Explosion(Point p, int type)
	{
		super(p);
		
		x = p.getX();
		y = p.getY();
		this.type = type;
		
		//When the explosion is created, it's both active and expanding
		active = true;
		expanding = true;
		
		//Sets the colors of the explosion
		colors = new Color []{Color.WHITE, Color.WHITE, Color.WHITE, 
							  Color.BLUE, Color.BLUE, Color.BLUE, 
							  Color.WHITE, Color.YELLOW, Color.WHITE, 
							  Color.BLUE, Color.RED, Color.WHITE, Color.BLUE};
	}
	
	/**
	 * Draws the explosion
	 * @param g The component to draw
	 */
	public void draw(Graphics g)
	{
		//Draws it if it's active
		if(this.active)
		{
			g.setColor(colors[counter]);
			
			g.fillOval((int)super.getX(), (int)super.getY(), (int)super.getWidth(), (int)super.getHeight());
			move();
			
			counter ++;
			
			//Resets the counter 
			if(counter >= colors.length)
			{
				counter = 0;
			}
		}
	}
	
	/**
	 * Moves the explosion by increasing the size of the explosion
	 * and then updating the upper left-hand corner of the circle
	 * so that its center is still in the place the missile hit.
	 * @return Whether or not you were able to move the explosion
	 */
	public boolean move()
	{
		//Tests if the explosion has reached maximum size
		if(super.getWidth() > 50 && super.getHeight() > 50)
		{
			this.active = false;
			expanding = false;
			return false;
		}
		//Otherwise not maximum size and able to expand and move
		else
		{
			//Changes size
			double width = super.getWidth() + 1;
			double height = super.getHeight() + 1;
			super.setSize((int)width, (int)height);

			//Updates location: Where the missile hit - half the size of the rectangle
			super.setLocation((int)(x - (super.getWidth()/2)), (int)(y - (super.getHeight()/2)));	
			
			return true;
		}
	}
	
	/**
	 * Sets the missile's expansion
	 * @param expand True or false depending on if the
	 * missile is set to expand or not
	 */
	public void setExpanding(boolean expand)
	{
		this.expanding = expand;
	}
	
	/**
	 * Gets whether or not this explosion is active
	 * @return True if this explosion is active, false
	 * otherwise
	 */
	public boolean isActive()
	{
		return this.active;
	}
	
	/**
	 * Tests if this rectangle contains
	 * the specified point.
	 * @param p The point to be found in the rectangle
	 * @return True if the point is inside this rectangle
	 * false otherwise.
	 */
	public boolean contains(Point p)
	{
		return super.contains(p);
	}
	
	/**
	 * Gets the upper left-hand point of 
	 * this rectangle.
	 * @return The upper left-hand point of this
	 * rectangle.
	 */
	public Point getLocPoint()
	{
		return super.getLocation();
	}
	
	/**
	 * Gets what type of explosion this is
	 * @return A 0 if it's a defender explosion
	 * or a 1 if it's an attacker explosion.
	 */
	public int getType()
	{
		return type;
	}

}

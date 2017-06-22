import java.awt.*;

/**
 * City object
 * @author Crystal Chun	ID #012680952
 *
 */
public class City extends Rectangle
{
	/**Whether or not this city has been destroyed*/
	private boolean active;
	
	/**
	 * Constructs the city object 
	 * @param location The upper left-hand corner of the city
	 */
	public City(Point location)
	{
		super((int)location.getX(), (int)location.getY(), 80, 50);
		this.active = true;
	}

	/**
	 * Draws the city
	 * @param g The graphics component
	 */
	public void draw(Graphics g, Image skyline)
	{
		if(isActive())
		{
			//If this city isn't destroyed, draws the city
			g.drawImage(skyline, (int)super.getX(), (int)super.getY(), null);
		}
	}
	
	/**
	 * Gets whether or not this city is active
	 * @return True if this city is active, false
	 * otherwise
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * Gets if this city is hit
	 * @param p The point of impact
	 * @return Whether or not the point was inside
	 * the city (meaning it was hit)
	 */
	public boolean isHit(Point p)
	{
		if(this.contains(p))
		{
			this.active = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets this city to either active or not active
	 * @param set Whether or not this city is active
	 */
	public void setActive(boolean set)
	{
		this.active = set;
	}
	
	/**
	 * The upper left-hand corner of this city
	 * @return The upper left-hand corner of this city
	 */
	public Point getLocPoint()
	{
		return super.getLocation(); 
	}
}

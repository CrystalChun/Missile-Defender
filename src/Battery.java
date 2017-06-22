import java.awt.*;

/**
 * The battery class that shows
 * how many missiles each battery has
 * @author Crystal Chun	ID #012680952
 *
 */
public class Battery extends Rectangle
{
	/**The number of missiles in this battery*/
	private int numMissiles;

	/**
	 *Constructs the battery by taking in where
	 *it's located then initializing the location and size
	 *in the Rectangle constructor. Initializes the number of
	 *missiles to 10.
	 * @param location The upper left-hand corner of the battery
	 */
	public Battery(Point location)
	{
		super((int)location.getX(), (int)location.getY(), 25, 30);
		this.numMissiles = 10;
	}
	
	/**
	 * Draws the battery
	 * @param g The graphics component
	 * @param missile The image to show the number of missiles
	 * in this battery
	 */
	public void draw(Graphics g, Image missile)
	{
		//Tests if battery has missiles, draws if it does
		if(numMissiles > 0)
		{
			//Offset is how far apart each missile is drawn from one another
			int offSet = 10;
			
			int x = (int)super.getX() + offSet;
			int y = (int)super.getY();

			//Draws all missiles in this battery
			for(int i = 0; i < numMissiles; i++)
			{
				if(i == 5) //Goes to the next row after 5 missiles are drawn
				{
					x = (int)super.getX() + offSet;
					y = y + 30;
				}
				
				g.drawImage(missile, x, y, null);
				
				x = x + offSet;
			}
		}
	}
	
	/**
	 * Resets the battery by resetting the number
	 * of missiles to 10.
	 */
	public void reset()
	{
		numMissiles = 10;
	}
	
	/**
	 * Gets the number of missiles in
	 * this battery.
	 * @return The number of missiles in this battery.
	 */
	public int getNumMissiles()
	{
		return numMissiles;
	}
	
	/**
	 * Removes a missile from this battery.
	 */
	public void removeMissile()
	{
		this.numMissiles --;
	}
	
	/**
	 * Gets the location of this battery
	 * (the upper left-hand point of rectangle)
	 * @return The upper left-hand point of this rectangle.
	 */
	public Point getLocPoint()
	{
		return super.getLocation();
	}
	
	/**
	 * Tests whether or not this battery was hit
	 * by testing if the parameter point is
	 * inside the rectangle.
	 * @param p The point of impact
	 * @return True if this battery was hit,
	 * false otherwise.
	 */
	public boolean isHit(Point p)
	{
		if(this.contains(p))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the number of missiles to zero
	 * since this battery is hit.
	 * @param numMissiles The number of missiles,
	 * probably zero to indicate it was hit.
	 */
	public void setHit(int numMissiles)
	{
		this.numMissiles = numMissiles;
	}
}

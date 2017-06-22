/**
 * Creates a player that stores the player's
 * name and their high score.
 * @author Crystal Chun ID #012680952
 *
 */
public class Player implements Comparable<Player>
{
	/**The player's name*/
	private String name;
	/**The player's score*/
	private int score;
	
	/**
	 * Constructs the player
	 * @param name The player's name
	 * @param score The player's score
	 */
	public Player(String name, int score)
	{
		this.name = name;
		this.score = score;
	}
	
	/**
	 * Overridden to compare this player to
	 * the player passed in to see which one comes
	 * ahead of the other.
	 * @param player2 The player to be compared to
	 * @return An integer: Negative if this player's
	 * high score is greater than the player passed in.
	 * Positive if this player's high score is less than
	 * the player passed in.
	 * If their scores are equal, it compares the player's
	 * names and returns a negative if this player comes
	 * ahead of the player passed in alphabetically, a positive
	 * if this player comes after the player passed in alphabetically
	 * or a zero if this player's name is the same as the player
	 * passed in.
	 */
	@Override
	public int compareTo(Player player2)
	{
		//If their scores are equal, compares by the players' name
		if(this.score == player2.score)
		{
			return this.name.compareTo(player2.getName());
		}
		return player2.score - this.score;
	}
	
	/**
	 * Gets the name of the player
	 * @return The player's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the score of the player
	 * @return The score
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Converts this player into a string representation.
	 * @return the string representation of this player
	 */
	@Override
	public String toString()
	{
		String s = name + "\r\n" + score + "\r\n";
		return s;
	}
}

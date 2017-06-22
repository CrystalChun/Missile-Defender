import java.util.Scanner;
/**
 * Checks various user inputs
 * @author Crystal Chun		ID#012680952
 *
 */
public class CheckInput 
{
	/**
	 * Gets an integer input
	 * @return An integer
	 */
	public static int intInput()
	{
		Scanner in = new Scanner (System.in);
		int input = 0;
		boolean inputCorrect = false;
		
		//Loops until the user enters an integer
		while(!inputCorrect)
		{
			if(in.hasNextInt())		//Checks for integer
			{
				input = in.nextInt();
				inputCorrect = true;
			}
			else
			{
				in.next();
				System.out.println("That's not a valid option, try again.");
			}
		}
		
		return input;
	}
	
	/**
	 * Gets an integer input within a certain bounds
	 * @param lowerBound The lowest value that the input can be
	 * @param upperBound The highest value that the input can be
	 * @return An integer within bounds
	 */
	public static int intInput(int lowerBound, int upperBound)
	{
		Scanner in = new Scanner (System.in);
		int input = 0;
		boolean inputCorrect = false;
		
		//Loops until the user enters a correct input within bounds
		while(!inputCorrect)
		{
			if(in.hasNextInt())		//Checks for integer
			{
				input = in.nextInt();
				
				if(input >= lowerBound && input <= upperBound)		//Checks if it's within bounds
				{
					inputCorrect = true;
				}
				else
				{
					System.out.println("That's not a valid option, try again.");
				}
			}
			else
			{
				System.out.println("That's not a valid option, try again.");
				in.next();
			}
		}
		return input;
	}
	
	/**
	 * Gets a double value
	 * @return A double value
	 */
	public static double decInput()
	{
		Scanner in = new Scanner (System.in);
		double input = 0;
		boolean inputCorrect = false;
		
		//Loops until the correct input is entered
		while(!inputCorrect)
		{
			if(in.hasNextDouble())
			{
				input = in.nextDouble();
				inputCorrect = true;
			}
			else
			{
				in.next();
				System.out.println("That is not a valid input, try again.");
			}
		}
		return input;
	}
	
	/**
	 * Checks that the input is a decimal and within the bounds
	 * @param lowerBound The lowest number the input can be
	 * @param upperBound The highest number the input can be
	 * @return The decimal input within bounds
	 */
	public static double decInput(double lowerBound, double upperBound)
	{
		Scanner in = new Scanner (System.in);
		double input = 0;
		boolean inputCorrect = false;
		
		//Loops while the user hasn't entered a correct decimal within bounds
		while(!inputCorrect)
		{
			if(in.hasNextDouble())	//Checks for correct type of input
			{
				input = in.nextDouble();
				if(input >= lowerBound && input <= upperBound)	//Checks that it's in bounds
				{
					inputCorrect = true;
				}
				else
				{
					System.out.println("That's not a valid input, try again");
				}
			}
			else
			{
				in.next();
				System.out.println("That's not a valid input, try again.");
			}
		}
		return input;
	}
	
	/**
	 * Gets a string input
	 * @return A string
	 */
	public static String getString()
	{
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		return input;
	}
}

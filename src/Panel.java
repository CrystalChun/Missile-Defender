import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creates the panel for this game
 * @author Crystal Chun	ID #012680952
 *
 */
public class Panel extends JPanel 
implements MouseListener, MouseMotionListener, KeyListener, ActionListener
{
	/**The array of cities in this game*/
	private City [] cities;
	/**The array of batteries in this game*/
	private Battery [] availMissiles;
	/**The missile objects*/
	private ArrayList <Missile> missiles;
	/**The explosion objects*/
	private ArrayList <Explosion> explosions;
	/**The list of high scores*/
	private ArrayList<Player> highScores;
	/**The x position of the cursor*/
	private int xCursor;
	/**The y position of the cursor*/
	private int yCursor;
	/**Whether or not the game is in play*/
	private boolean inGame;
	/**Whether or not the game is over*/
	private boolean gameOver;
	/**Whether or not to show the round intro text*/
	private boolean reset;
	/**Whether or not the mouse is in the game area and the user can fire a missile*/
	private boolean canFire;
	/**Whether or not to show the high scores*/
	private boolean showHighScores;
	/**The number of attacker missiles that have been fired*/
	private int numMissiles;
	/**The number of rounds played*/
	private int round;
	/**The number of points this player has*/
	private int points;
	/**The image of the background*/
	private Image nightSky;
	/**The image of the coin button*/
	private Image coin;
	/**The game over title*/
	private Image game_over;
	/**The missile command title*/
	private Image title;
	/**The missile image*/
	private Image missile;
	/**The city image*/
	private Image city;
	/**The button to play the game*/
	JButton playGame;
	/**The button to play again after game over*/
	JButton restart;
	/**The button to submit the player's name for the high score list*/
	JButton submit;
	/**The button to view the high scores*/
	JButton highScore;
	/**The place where the player can input their name*/
	JTextField nameGetter;

	/**
	 * Panel constructor that initializes all components for
	 * this game, reads in all images for this game,
	 * initializes all objects for the game, reads in the
	 * high scores, and creates the thread.
	 */
	public Panel()
	{
		this.setLayout(null);
		
		//Reads in the list of high scores
		highScores = new ArrayList<Player>();
		File f = new File("highScores.txt");
		if(f.exists())
		{
			try
			{
				BufferedReader in = new BufferedReader(new FileReader("highScores.txt"));
				boolean reading = true;
				
				String line = in.readLine();
				if(line == null)
				{
					reading = false;
				}
				
				while(reading)
				{
					String name = line;
					int score = Integer.parseInt(in.readLine());
					
					Player temp = new Player(name, score);
					highScores.add(temp);
					
					line = in.readLine();
					if(line == null )
					{
						reading = false;
					}
				}
				in.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("No such file");
			} 
			catch (IOException e) 
			{
				System.out.println("Error reading file");
			}
			
			Collections.sort(highScores);
		}
		
		//Initializing variables
		points = 0;
		numMissiles = 0;
		round = 1;
		
		inGame = false;
		reset = true;
		showHighScores = false;
		
		cities = new City[6];
		availMissiles = new Battery[3];
		explosions = new ArrayList<Explosion>();
		missiles = new ArrayList<Missile>();
		
		//Initialize Batteries
		int x = 50;
		int y = 680;
		for(int i = 0; i < availMissiles.length; i++)
		{
			Battery battery = new Battery(new Point(x, y));	
			x = x + 530;
			availMissiles[i] = battery;
		}
		
		//Initialize Cities
		x = 180;
		y = 700;
		for(int i = 1; i <= cities.length; i++)      
		{
			Point p = new Point(x, y);
			
			//If this is the fourth cities, moves x-value over by 100
			if(i == 4)
			{
				x = x + 100;
				p.setLocation(x, 700);
			}
			
			City city = new City(p);
			x = x + 150;
			cities[i - 1] = city;
		}
		
		//Initialize buttons
		playGame = new JButton();
		playGame.addActionListener(this);
		playGame.setMargin(new Insets(0, 0, 0, 0));
		playGame.setBorder(null);
		playGame.setBounds(600, 600, 80, 80);
		
		highScore = new JButton("Show High Scores");
		highScore.addActionListener(this);
		highScore.setBackground(Color.GRAY);
		highScore.setBorder(null);
		highScore.setForeground(Color.WHITE);
		highScore.setBounds(800, 350, 200, 50);
		
		restart = new JButton("TRY AGAIN?");
		restart.addActionListener(this);
		restart.setFont(new Font("Garamond", Font.PLAIN, 30));
		restart.setBackground(Color.BLACK);
		restart.setForeground(Color.WHITE);
		restart.setBorder(null);
		restart.setBounds(480, 600, 300, 80);
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		submit.setFont(new Font("Arial", Font.BOLD, 14));
		submit.setBackground(Color.BLACK);
		submit.setForeground(Color.RED);
		submit.setBorder(null);
		submit.setBounds(520, 400, 80, 40);
		
		//Initialize text field
		nameGetter = new JTextField(20);
		nameGetter.setBounds(300, 400, 200, 40);
		nameGetter.setBackground(Color.BLACK);
		nameGetter.setForeground(Color.WHITE);
		
		//Starts the cursor in the middle of the window
		xCursor = 1300 / 2;
		yCursor = 800 / 2;
		
		//Adding listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		
		//Adding components to panel
		this.add(playGame);
		
		//Reads in all images
		try 
		{
			coin = ImageIO.read(new File("coin.png"));
			coin = coin.getScaledInstance(70, 70, Image.SCALE_DEFAULT);
			title = ImageIO.read(new File("title.png"));
			title = title.getScaledInstance(850, 350, Image.SCALE_SMOOTH);
			nightSky = ImageIO.read(new File("night.jpg"));
			nightSky = nightSky.getScaledInstance(1300, 800, Image.SCALE_SMOOTH);
			game_over = ImageIO.read(new File("gameOver.jpg"));
			game_over = game_over.getScaledInstance(850, 350, Image.SCALE_DEFAULT);
			missile = ImageIO.read(new File("mis.png"));
			city = ImageIO.read(new File("skyline.png"));
		}
		catch (IOException e1) 
		{
			System.out.println("Error reading image");
		}
		
		//Creates the thread
		Thread thread = new Thread()
		{
			public void run()
			{
				//Timer for inserting missiles
				int counter = 0;
				while(true)
				{	
					if(inGame)
					{
						/*
						 * If playing and counter is at a certain number
						 * and haven't reached max missiles, creates
						 * new enemy missile and resets counter.
						 */
						if(counter == 200 && numMissiles < 15)
						{
							createAttackMissile();
							counter = 0;
							reset = false;
						}
						/*
						 * Reached max missiles, tests if the user
						 * won this round.
						 */
						else if(numMissiles >= 15)
						{
							//If no more missiles and the user still has cities
							if(missiles.size() <= 0 && !testEnd())
							{
								//Resets all variables
								numMissiles = 0;
								counter = 0;
								inGame = true;
								gameOver = false;
								reset = true;	//Shows the rounds
								explosions.clear();
								missiles.clear();
								
								//Adds up the amount of points the user earned
								for(Battery b: availMissiles)
								{
									//Each missile they have left is 1 point
									if(b.getNumMissiles() > 0) 
									{
										points += b.getNumMissiles();
									}
									b.reset();
								}
								
								for(City c: cities)
								{
									//Each city they have left is 5 points
									if(c.isActive())
									{
										points += 5;
									}
								}
								round ++;
							}
						}
						else
						{
							counter++;
						}
						
						//Tests if the user lost
						testEnd();
						
						//Removes inactive missiles
						remove();		
					}

					repaint();
					try
					{
						Thread.sleep(8);

					}
					catch(InterruptedException e)
					{
						System.out.println("Thread interrupted");
					}
				}
			}
		};
		thread.start();
	}
	
	/**
	 * Paints this panel 
	 * @param g The graphics component to draw on this panel
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Color dirt = new Color(225, 213, 68);
		
		//Draws the background
		g.drawImage(nightSky, 0, 0, null);
		
		//Resetting the round, displays the round number and text
		if(reset)
		{
			String roundString = "Round " + round;
			g.setColor(Color.white);
			g.setFont(new Font("Garamond", Font.BOLD, 54));
			g.drawString(roundString, 540, 350);
			
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString("Defend your cities!", 510, 400);
		}
		//Draw if in game
		if(inGame)
		{
			//Draws the ground
			g.setColor(dirt);
			int [] x = new int []{0, 60, 65, 70, 78, 80, 85, 93, 96, 100, 107, 115, 130, 145,
					168, 400, 580, 
					595, 600, 607, 615, 620, 630, 634, 637, 640, 643, 650, 655, 662, 
					698, 720, 800, 1110, 
					1119, 1120, 1124, 1127, 1130, 1136, 1145, 1156, 1160, 1164, 1171, 1180, 1187, 
					1195, 1200, 1204, 1210, 1223, 1240, 1400, 0, 0};
			
			int [] y = new int []{750, 675, 670, 669, 667, 665, 663, 661, 661, 658, 659, 660, 665, 675,
					740, 740, 740, 
					675, 670, 669, 667, 665, 663, 659, 660, 661, 660, 660, 663, 675,
					740, 740, 740, 740,
					675, 670, 669, 667, 665, 663, 659, 660, 661, 660, 660, 663, 675, 
					682, 687, 690, 693, 700, 705, 800, 800, 750};
			
			g.fillPolygon(x, y, x.length);
			
			//Draws the cities & Batteries
			int j = 0;
			for(int i = 0; i < cities.length; i++)      
			{
				if(j < 3)
				{
					availMissiles[j].draw(g, missile);
					j++;
				}
				cities[i].draw(g, city);
			}
			
			//Draws the cursor
			g.setColor(Color.white);
			g.drawLine(xCursor - 6, yCursor, xCursor + 6, yCursor);
			g.drawLine(xCursor, yCursor - 6, xCursor, yCursor + 6);
			
			//Draws attacker missiles
			for(int i = 0; i < missiles.size(); i++)
			{
				missiles.get(i).draw(g);
			}
			
			//Draws the score on the upper left hand corner of the screen
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1300, 30);
			String score = "SCORE: " + points;
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Garamond", Font.PLAIN, 14));
			g.drawString(score, 10, 20);
			
			//Draws explosions
			for(int i = 0; i < explosions.size(); i++)
			{
				if(explosions.get(i).isActive())
				{
					explosions.get(i).draw(g);
					
					//Tests if the explosion hits anything
					testCollision(explosions.get(i), explosions.get(i).getType());
				}
			}
		}
		//Draws if Game Over
		else if(gameOver)
		{
			//Draw background
			this.setBackground(Color.black);
			g.fillRect(0, 0, 1300, 800);
			
			//Draws game over title
			g.drawImage(game_over, 200, 80, this);
			
			//Displays user's score
			String score = "You scored " + points + " points!";
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.PLAIN, 24));
			g.drawString(score, 300, 350);
			
			//Prompts user to enter name
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 14));
			g.drawString("Enter your name here: ", 305, 400);
			
			//Shows high scores
			if(showHighScores)
			{
				//Title
				String scores = "High Scores";
				g.setFont(new Font("Garamond", Font.BOLD, 20));
				g.setColor(Color.YELLOW);
				g.drawString(scores, 780, 350);
				
				int y = 400;
				int index = 1;
				g.setFont(new Font("Arial", Font.PLAIN, 18));
				g.setColor(Color.WHITE);
				
				//High score list
				scores = "";
				for(Player p : highScores)
				{
					scores = scores.format("%2d. %-30s %5d", index, p.getName(), p.getScore());
					
					g.drawString(scores, 750, y);
					
					y += 30;
					index ++;
				}
			}
		}
		//Draws title screen
		else
		{
			//Background
			g.drawImage(nightSky, 0, 0, null);
			//Coin button
			g.drawImage(coin, 600, 600, this);
			//Title 
			g.drawImage(title, 200, 80, this);
			
			//Push coin to start prompt
			g.setColor(Color.white);
			g.setFont(new Font("Garamond", Font.BOLD, 40));
			g.drawString("PUSH \t COIN \t  TO \t  START", 390, 550);
		}	
	}
	
	/**
	 * Creates the attacker missiles
	 */
	public void createAttackMissile()
	{
		//Initialize the attacker's missiles
		int y = 0;
		int startX = (int) (Math.random() * 1300);
		int endX = 0;
		int endY = 0;
		int aim = (int) (Math.random() * 2); //Chooses either city or battery to target
		boolean able = false; //Whether a target is able to be established
		
		if(aim == 0) //Aim for the cities 
		{
			int target = (int) (Math.random() * (cities.length));
			//Only targets active cities
			if(cities[target].isActive())
			{
				//Aims for the middle of the city
				endX = (int) ((int) (cities[target].getWidth() / 2) + cities[target].getX());
				endY = (int) ((int) (cities[target].getHeight() / 2) + cities[target].getY());
				able = true;
			}
		}
		else //Aim for the batteries
		{
			int target = (int) (Math.random() * (availMissiles.length));

			//Only targets active batteries
			if(availMissiles[target].getNumMissiles() > 0)
			{
				//Aims for the middle of the battery
				endX = (int) ((int) (availMissiles[target].getWidth() / 2) + availMissiles[target].getX());
				endY = (int) ((int) (availMissiles[target].getHeight() / 2) + availMissiles[target].getY());
				able = true;	
			}
		}

		if(able) //Target established
		{
			Point start = new Point(startX, y);
			Point end = new Point(endX, endY);

			//Creates missile
			Missile attacker = new Missile(start, end, 1, 1, Color.RED);

			missiles.add(attacker);
			numMissiles ++;
		}			
	}

	/**
	 * Creates the defender's missile from the battery closest
	 * to the location passed in.
	 * @param x The target x-coordinate
	 * @param y The target y-coordinate
	 */
	public void createDefMissile(int x, int y)
	{
		//The source battery (where it's fired from)
		Battery closest = null;
		double distance = 0;
		//Used to track if the battery is the second battery
		int j = 0; 
		
		//Runs through all batteries and gets the closest one to the target
		for(int i = 0; i < availMissiles.length; i++)
		{
			//If battery is active 
			if(availMissiles[i].getNumMissiles() > 0)
			{
				//If there is no source battery set yet
				if(closest == null)
				{
					//Sets this battery as the source
					closest = availMissiles[i];
					//Calculates its distance away from target: sqrt( ((endx - beginx)^2) + ((endy - beginy) ^2) )
					distance = Math.sqrt(Math.pow(((x - availMissiles[i].getCenterX())), 2) 
											+ Math.pow(((y - availMissiles[i].getCenterY())), 2) );
					j = i;
				}
				else	//Otherwise checks if this battery's distance is shorter than the closest battery so far
				{
					//Calculates this battery's distance from target
					double newDist = Math.sqrt(Math.pow(((x - availMissiles[i].getCenterX())), 2) 
												+ Math.pow(((y - availMissiles[i].getCenterY())), 2) );
					//If this battery's distance is smaller than the closest battery's distance
					if(newDist < distance)
					{
						//Sets this battery as the source battery
						closest = availMissiles[i];
						distance = newDist;
						j = i;
					}
				}
			}
		}
		
		//Checks to see if a battery was chosen
		if(closest != null)
		{
			//The center of the battery
			Point start = new Point((int)(closest.getX() + closest.getWidth()),
									(int)(closest.getHeight() + closest.getY()));
			Point end = new Point(x,y);
			
			int speed = 2;
			if(j == 1) //If battery 2 was chosen, increases speed
			{
				speed = 4;
			}
			
			//Creates missile
			Missile defender = new Missile(start, end, speed, 0, Color.WHITE);
			
			missiles.add(defender);
			closest.removeMissile();
		}
	}

	/**
	 * Overloaded defender missile creator, takes in which battery to fire from
	 * @param x The target x-coordinate
	 * @param y The target y-coordinate
	 * @param batteryNum The battery to fire from
	 */
	public void createDefMissile(int x, int y, int batteryNum)
	{
		Battery battery = availMissiles[batteryNum];
		
		//If this battery has missiles
		if(battery.getNumMissiles() > 0)
		{
			//Starts from the battery's center position
			Point start = new Point((int)(battery.getX() + battery.getWidth()),
									(int)(battery.getHeight() + battery.getY()));
			Point end = new Point(x,y);
			int speed = 5;
			
			//If it's the second battery, increases the speed
			if(batteryNum == 1)
			{
				speed = 8;
			}
			
			//Creates missile
			Missile defender = new Missile(start, end, speed, 0, Color.WHITE);
			
			missiles.add(defender);
			battery.removeMissile();
		}
	}

	/**
	 * Tests if the explosion hits anything
	 * @param e The explosion to be checked
	 * @param type The type of explosion it is, 0 for defender, 1 for attacker
	 */
	public void testCollision(Explosion explosion, int type)
	{
		//Checks if the explosion hits certain things based on what type of explosion
		switch(type)
		{
			case 0:		//Defender explosion -- checks if hits attacker missiles
						for(int i = 0; i < missiles.size(); i++)
						{
							if(missiles.get(i).getType() == 1)
							{
								if(explosion.contains(missiles.get(i).getLocPoint()))
								{
									missiles.get(i).setActive(false);
									
									Double missile = missiles.get(i).getLocPoint();
									
									//Creates explosion based on where the missile hit
									Point explosionPoint = new Point((int) missile.getX(), (int) missile.getY());
									Explosion newExplosion = new Explosion(explosionPoint, missiles.get(i).getType());
									
									explosions.add(newExplosion);
									missiles.remove(i);
								}
							}
						}
						break;
			case 1:		//Attacker missiles -- checks if hits cities or batteries
				
						//Checks if explosion hits a city
						for(int i = 0; i < cities.length; i++)
						{
							//Tests if the explosion crosses the city boundaries
							if(explosion.intersects(cities[i]))
							{
								cities[i].isHit(explosion.getLocPoint());
							}
						}
						
						//Checks if explosion hits a battery
						for(int i = 0; i < availMissiles.length; i++)
						{
							if(availMissiles[i].isHit(explosion.getLocPoint()))
							{
								availMissiles[i].setHit(0);
							}
						}
						break;
		}
	}

	/**
	 * Removes any inactive missiles and creates explosions where they hit
	 */
	public void remove()
	{
		//Goes through all missiles
		for(int i = 0; i < missiles.size(); i++)
		{
			//Tests if they're inactive
			if(!missiles.get(i).isActive())
			{
				//Gets where the missile died
				Double missile = missiles.get(i).getLocPoint();
				Point explosion = new Point((int)missile.getX(), (int) missile.getY());
				
				//Creates explosion
				Explosion e = new Explosion(explosion, missiles.get(i).getType());
				explosions.add(e);
				
				missiles.remove(i);
			}
		}
	}

	/**
	 * Tests whether or not the user has lost all their cities
	 * @return True if the user lost all their cities, false otherwise
	 */
	public boolean testEnd()
	{
		//Tests how many cities have died
		int dead = 0;
		for(City c: cities)
		{
			if(!c.isActive())
			{
				dead ++;
			}
		}
		
		//If the user lost all their cities
		if(dead >= cities.length)
		{
			//Exits game
			gameOver = true;
			inGame = false;
			
			//Adds components (buttons and textfield) to screen
			this.add(restart);
			this.add(submit);
			this.add(nameGetter);
			this.add(highScore);
			
			return true;
		}
		return false;
	}

	/**
	 * Checks which key is pressed and creates a defender missile
	 * where the cursor is.
	 * @param e The key pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) 
	{
		//Only if the mouse is in the game area
		if(canFire && yCursor < 720)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_SPACE:	//Chooses the battery closest to this location
										createDefMissile(xCursor, yCursor);
										break;
				case KeyEvent.VK_1:		//Fires from battery 1
										createDefMissile(xCursor, yCursor, 0);
										break;
				case KeyEvent.VK_2:		//Fires from battery 2
										createDefMissile(xCursor, yCursor, 1);
										break;
				case KeyEvent.VK_3:		//Fires from battery 3
										createDefMissile(xCursor, yCursor, 2);
										break;
			}
		}
	}

	/**
	 * Gets if the mouse is in the window, if it is,
	 * then allows the user to fire missiles.
	 * @param e The mouse event
	 */
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		canFire = true;
	}

	/**
	 * Gets if the mouse is outside the window,
	 * if it is, then doesn't allow the user to
	 * fire missiles.
	 * @param e The mouse event
	 */
	@Override
	public void mouseExited(MouseEvent e) 
	{
		canFire = false;
	}

	/**
	 * Sees where the mouse is and updates the cursor 
	 * position.
	 * @param e Where the mouse is with respect to the panel
	 */
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		xCursor = e.getX();
		yCursor = e.getY();
	}

	/**
	 * Sees where the mouse is clicked and creates a defender
	 * missile aimed towards that location.
	 * @param e The event where the mouse is clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		//Only when the mouse is in the game area
		if(canFire && e.getY() < 720)
		{
			int x = e.getX();
			int y = e.getY();
			createDefMissile(x, y);
		}	
	}

	/**
	 * Gets an action performed and tests to 
	 * see what happens.
	 * @param e The action
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//If the playGame button is hit
		if(e.getSource() == playGame)
		{
			//Removes button, updates layout, starts game
			setLayout(null);
			this.remove(playGame);
			reset = true;
			inGame = true;
		}
		//If the restart (try again) button is hit
		else if(e.getSource() == restart)
		{
			//Removes all components from game over screen
			this.remove(restart);
			this.remove(submit);
			this.remove(nameGetter);
			
			//Resets all cities to active
			for(City c: cities)
			{
				c.setActive(true);
			}
			
			//Resets all batteries to active
			for(Battery b: availMissiles)
			{
				b.reset();
			}
			
			//Clears all lists
			missiles.clear();
			explosions.clear();
			
			//Sets the test variables
			reset = true;
			inGame = true;
			gameOver = false;
			showHighScores = false;
			
			//Reset points, round, and attacker missiles
			points = 0;
			round = 1;
			numMissiles = 0;
			
			//Tests if the high score button hasn't been pushed
			if(!showHighScores)
			{
				//Removes the button if it hasn't been pushed
				this.remove(highScore);
			}
		}
		//If the submit button was pushed
		else if(e.getSource() == submit)
		{
			//Gets name from text field
			String name = nameGetter.getText();
			
			//Creates new player and adds to list
			Player thisPlayer = new Player(name, points);
			highScores.add(thisPlayer);
			
			//Removes both submit button, and the text field
			this.remove(submit);
			this.remove(nameGetter);
			
			//Write data to file
			try
			{
				PrintWriter write = new PrintWriter("highScores.txt");
				
				//Sorts list by high score
				Collections.sort(highScores);
				
				//Removes players so the list only contains the top 5 high scores
				if(highScores.size() > 5)
				{
					for(int i = 5; i < highScores.size(); i++)
					{
						highScores.remove(i);
					}
				}
				
				//Writes players to file
				for(Player p: highScores)
				{
					write.print(p);
				}
				write.close();
			}
			catch(FileNotFoundException gs)
			{
				System.out.println("File not found");
			}	
		}
		//If the high score button was clicked
		else if(e.getSource() == highScore)
		{
			//Removes button and toggles to show high score list
			this.remove(highScore);
			showHighScores = true;
		}
	}

	/**
	 * From mouse listener, unused method.
	 * @param e The mouse event
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	/**
	 * From mouse listener, unused method
	 * @param e The mouse event
	 */
	@Override
	public void mousePressed(MouseEvent e) 
	{
	
	}

	/**
	 * From mouse listener, unused method
	 * @param e The mouse event
	 */
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		
	}

	/**
	 * From Key Listener, unused method
	 * @param e The key event
	 */
	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}

	/**
	 * From key listener, unused method
	 * @param e The key event
	 */
	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
}

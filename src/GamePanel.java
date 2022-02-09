import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Timer;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int PANEL_WIDTH_FACTOR = 22; // shouldn't be less than player's default long
	static final int PANEL_HEIGHT_FACTOR = 22;
	
	private static  int BASE_GAME_SPEED = 140;
	private static  int BASE_SCORE = 0;
	private static  char BASE_DIRECTION = 'r';
	
	private static int PANEL_WIDTH;
	private static int PANEL_HEIGHT;
	
	private static char currentDirection;
	private static char pressedDirection;
	
	private static int speed;
	private static int score;
	private int loseOrWin; //0 continue, 1 win, -1 lose
	
	private static ArrayList<Color> backgroundColors; //in-game background
	private static Color appleColor;
	private static Color playerColor;
	
	Player player1;
	Apple apple1;
	
	Timer gameTimer;
	
	public GamePanel(){
		
		player1 = new Player();
		
		setCurrentDirection(BASE_DIRECTION);
		setPressedDirection(BASE_DIRECTION);
		setScore(BASE_SCORE);
		setBackgroundColors(new ArrayList<>(Arrays.asList(new Color(111, 199, 183, 110),new Color(111, 199, 183, 220))));
		setPlayerColor(new Color(77, 153, 106, 220));
		setAppleColor(new Color(230, 71, 105, 220));
		setLoseOrWin(0);
		
		this.createApple();
		
		setPANEL_HEIGHT(PANEL_HEIGHT_FACTOR * Player.getSize());
		setPANEL_WIDTH(PANEL_WIDTH_FACTOR * Player.getSize());
		
		setSpeed(BASE_GAME_SPEED);
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.setFocusable(true);
		
		this.addKeyListener(new GameKeyListener());

		gameTimer = new Timer(speed, this);
		gameTimer.start();
		
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		if(loseOrWin == 0) // continue
		{
			paintBackground(graphics2d, Player.getSize());

			graphics2d.setColor(playerColor);
			graphics2d.fillOval(player1.bodyParts.get(0).x(), player1.bodyParts.get(0).y() , Player.getSize(), Player.getSize());
		
			for(int i = 1; i< player1.bodyParts.size(); i++)
			{
				graphics2d.fillOval(player1.bodyParts.get(i).x(), player1.bodyParts.get(i).y() , Player.getSize(), Player.getSize());
			}
			
			graphics2d.setColor(appleColor);
			graphics2d.fillOval(apple1.getX(), apple1.getY(), Player.getSize(), Player.getSize());
			
			
			graphics2d.setColor(Color.darkGray);
			graphics2d.setFont(new Font("Monospaced Bold", Font.ITALIC, 25));
			FontMetrics metrics = getFontMetrics(graphics2d.getFont());
			String scoreString = "Score: " + score;
			graphics2d.drawString( scoreString , PANEL_WIDTH - (metrics.stringWidth(scoreString) * 3/2), graphics2d.getFont().getSize());
			return;
		}
		

		graphics2d.setColor(Color.LIGHT_GRAY);
		graphics2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		FontMetrics metrics = getFontMetrics(graphics2d.getFont());
		
		if(loseOrWin == -1) { //lose
			
			graphics2d.setColor(Color.red);
			graphics2d.setFont(new Font("Serif Bold", Font.BOLD, 30));
			String loseString = "YOU LOSE";
			graphics2d.drawString( loseString , (PANEL_WIDTH - metrics.stringWidth(loseString) * 2)/2, PANEL_HEIGHT / 2);
			
			
		}
		else { //win

			
			graphics2d.setColor(Color.green);
			graphics2d.setFont(new Font("Serif Bold", Font.BOLD, 30));
			String winString = "YOU WIN";
			graphics2d.drawString( winString , (PANEL_WIDTH - metrics.stringWidth(winString) * 2)/2, PANEL_HEIGHT / 2);
			
		}
		
		graphics2d.setColor(Color.black);
		graphics2d.setFont(new Font("Serif Bold", Font.BOLD, 22));
		String scoreString = "Score: " + score;
		graphics2d.drawString( scoreString , (PANEL_WIDTH - metrics.stringWidth(scoreString) * 2)/2, (PANEL_HEIGHT / 2) + metrics.stringWidth(scoreString));
		
		String infoString = "Press 'r' to replay";
		graphics2d.drawString( infoString , (PANEL_WIDTH - metrics.stringWidth(infoString) * 2)/2, 20);
		
		gameTimer.stop();
		
	}
	
	public void paintBackground(Graphics2D graphics, int size) {
		
		
		for(int i = 0; i < PANEL_HEIGHT_FACTOR * size; i += size)
		{
			for(int j = 0; j < PANEL_WIDTH_FACTOR * size; j += size)
			{
				if((i + j) % 2 == 0)
				{
					graphics.setColor(backgroundColors.get(0));
				}
				else {
					graphics.setColor(backgroundColors.get(1));
				}
				graphics.fillRect(j, i, size, size);
			}
		}
	}
	
	public int isGameOver(Player player) {
		
		if(player.bodyParts.size() == (PANEL_WIDTH_FACTOR * PANEL_HEIGHT_FACTOR) - 1)
		{
			return 1;
		}
		
		int headX = player.bodyParts.get(0).x();
		int headY = player.bodyParts.get(0).y();
		
		for(int i = 1; i< player.bodyParts.size(); i++)
		{
			if(player.bodyParts.get(i).x() == headX && player.bodyParts.get(i).y() == headY)
			{
				return -1;
			}
		}
		
		return 0;
	}
	
	public boolean isAppleEaten(Apple apple, Player player) {
		
			if(player.bodyParts.get(0).x() == apple.getX() && player.bodyParts.get(0).y() == apple.getY())
			{
				return true;
			}
		return false;
	}
	
	public boolean isAppleRespawnValid(Apple apple, Player player) {
		
		for(int i = 0; i< player.bodyParts.size(); i++)
		{
			if(player.bodyParts.get(i).x() == apple.getX() && player.bodyParts.get(i).y() == apple.getY())
			{
				return false;
			}
		}
		return true;
	}
	
	public void createApple() {
		apple1 = new Apple(PANEL_WIDTH_FACTOR, PANEL_HEIGHT_FACTOR, Player.getSize());
		while (!isAppleRespawnValid(apple1, player1)) {
			apple1 = new Apple(PANEL_WIDTH_FACTOR, PANEL_HEIGHT_FACTOR, Player.getSize());
		}
	}
	
	public class GameKeyListener extends KeyAdapter{
		 @Override
		    public void keyPressed(KeyEvent event) {
			 
			 int pressedKey = event.getKeyCode();
		        switch (pressedKey) {
		        case KeyEvent.VK_LEFT: {
		        	if(currentDirection != 'r')
		        	{
		        		setPressedDirection('l');
		        	}
		        	break;
				}
		        case KeyEvent.VK_RIGHT: {
		        	if(currentDirection != 'l')
		        	{
		        		setPressedDirection('r');
		        	}
		        	break;
		        }
				case KeyEvent.VK_UP: {
					if(currentDirection != 'd')
		        	{
						setPressedDirection('u');
		        	}
		        	break;
		        }
				case KeyEvent.VK_DOWN: {
					if(currentDirection != 'u')
		        	{
						setPressedDirection('d');
		        	}
					break;
				}
				}
		        
		        if(loseOrWin != 0 && (pressedKey == KeyEvent.VK_R))
		        {
		        	player1 = new Player();
		        	createApple();
		        	gameTimer.start();
		        }

		    }
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		repaint();
		
		if(player1.bodyParts.get(0).x() == PANEL_WIDTH - Player.getSize() && pressedDirection == 'r') {
			player1.teleportX(0);
		}
		else if (player1.bodyParts.get(0).x() == 0 && pressedDirection == 'l') {
			player1.teleportX(PANEL_WIDTH - Player.getSize());
		}
		
		else if(player1.bodyParts.get(0).y() == PANEL_HEIGHT - Player.getSize() && pressedDirection == 'd') {
			player1.teleportY(0);
		}
		else if (player1.bodyParts.get(0).y() == 0 && pressedDirection == 'u') {
			player1.teleportY(PANEL_HEIGHT - Player.getSize());
		}
		else {
			setCurrentDirection(pressedDirection);
			player1.move(currentDirection);
		}
		
		setLoseOrWin(this.isGameOver(player1));
		
		if(this.isAppleEaten(apple1, player1)) {
			score += 1;
			
			if(score % 6 == 0)
			{
				setSpeed(speed + score/6);
			}
			
			player1.addTail();
			this.createApple();
		}
		
		
	}
	
	public static void setSpeed(int speed) {
		GamePanel.speed = speed;
	}
	public static void setPANEL_HEIGHT(int pANEL_HEIGHT) {
		PANEL_HEIGHT = pANEL_HEIGHT;
	}
	
	public static void setPANEL_WIDTH(int pANEL_WIDTH) {
		PANEL_WIDTH = pANEL_WIDTH;
	}
	
	public static void setCurrentDirection(char currentDirection) {
		GamePanel.currentDirection = currentDirection;
	}
	
	public static void setPressedDirection(char pressedDirection) {
		GamePanel.pressedDirection = pressedDirection;
	}
	public static void setScore(int score) {
		GamePanel.score = score;
	}
	
	public static void setBackgroundColors(ArrayList<Color> backgroundColors) {
		GamePanel.backgroundColors = backgroundColors;
	}
	public static void setAppleColor(Color appleColor) {
		GamePanel.appleColor = appleColor;
	}
	public static void setPlayerColor(Color playerColor) {
		GamePanel.playerColor = playerColor;
	}
	public void setLoseOrWin(int loseOrWin) {
		this.loseOrWin = loseOrWin;
	}
}

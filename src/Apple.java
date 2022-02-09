import java.util.Random;

public class Apple {
	
	private int x;
	private int y;
	
	public Apple(int factorX, int factorY, int size) {
		Random random = new Random();
		
		int locationX = random.nextInt(factorX) * size;
		int locationY = random.nextInt(factorY) * size;
		
		setX(locationX);
		setY(locationY);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
}

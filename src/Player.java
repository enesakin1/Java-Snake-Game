import java.util.ArrayList;

public class Player {

	public record partInfos(int x, int y) {}
	
	private static final int size = 21;
	private static final int defaultLong = 6;
	
	public ArrayList<partInfos> bodyParts;
	
	public Player() {
		bodyParts = new ArrayList<partInfos>();
		int x = size*defaultLong;
		for(int i = 0; i < defaultLong; i++) {
			bodyParts.add(new partInfos(x, size));
			x-=size;
		}
	}
	
	public void move(char pressedDirection) {
		int headX = bodyParts.get(0).x();
		int headY = bodyParts.get(0).y();
		
		
		
		switch (pressedDirection) {
		case 'r': {
			bodyParts.set(0, new partInfos(headX+size, headY));
			break;
		}
		case 'l': {
			bodyParts.set(0, new partInfos(headX-size, headY));
			break;
		}
		case 'u': {
			bodyParts.set(0, new partInfos(headX, headY-size));
			break;
		}
		case 'd': {
			bodyParts.set(0, new partInfos(headX, headY+size));
		}
		}
		
		for(int i = bodyParts.size() - 1; i > 1; i--)
		{
			bodyParts.set(i, new partInfos(bodyParts.get(i-1).x(), bodyParts.get(i-1).y()));
		}
		bodyParts.set(1, new partInfos(headX, headY));
		
	}
	
	public void addTail() {
		int directionX = bodyParts.get(bodyParts.size()-1).x() - bodyParts.get(bodyParts.size()-2).x();
		int directionY = bodyParts.get(bodyParts.size()-1).y() - bodyParts.get(bodyParts.size()-2).y();
		
		if (directionX == 0) {
			if(directionY < 0) //up
			{
				bodyParts.add(new partInfos(bodyParts.get(bodyParts.size()-1).x(), bodyParts.get(bodyParts.size()-1).y() - size));
			}
			else {
				bodyParts.add(new partInfos(bodyParts.get(bodyParts.size()-1).x(), bodyParts.get(bodyParts.size()-1).y() + size));
			}
		}
		else {
			if(directionX < 0) //
			{
				bodyParts.add(new partInfos(bodyParts.get(bodyParts.size()-1).x() - size, bodyParts.get(bodyParts.size()-1).y()));
			}
			else {
				
			}bodyParts.add(new partInfos(bodyParts.get(bodyParts.size()-1).x() + size, bodyParts.get(bodyParts.size()-1).y()));
		}
	}
	
	public void teleportX(int x) {
		int headX = bodyParts.get(0).x();
		int headY = bodyParts.get(0).y();
		bodyParts.set(0, new partInfos(x, headY));
		
		for(int i = bodyParts.size() - 1; i > 1; i--)
		{
			bodyParts.set(i, new partInfos(bodyParts.get(i-1).x(), bodyParts.get(i-1).y()));
		}
		bodyParts.set(1, new partInfos(headX, headY));
	}
	
	public void teleportY(int y) {
		int headX = bodyParts.get(0).x();
		int headY = bodyParts.get(0).y();
		bodyParts.set(0, new partInfos(headX, y));
		
		for(int i = bodyParts.size() - 1; i > 1; i--)
		{
			bodyParts.set(i, new partInfos(bodyParts.get(i-1).x(), bodyParts.get(i-1).y()));
		}
		bodyParts.set(1, new partInfos(headX, headY));
	}
	
	public static int getSize() {
		return size;
	}
	
	public static int getDefaultlong() {
		return defaultLong;
	}
}

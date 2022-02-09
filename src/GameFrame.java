import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	GamePanel panel;
	
	public GameFrame() {
		panel = new GamePanel();
		this.add(panel);
		this.pack();
		
		this.setTitle("Snake Game");
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
	}
}

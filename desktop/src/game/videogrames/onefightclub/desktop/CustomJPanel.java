package game.videogrames.onefightclub.desktop;

import java.awt.Graphics;

import javax.swing.JPanel;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CustomJPanel extends JPanel{
	private java.awt.Image bg;
	
	public CustomJPanel(java.awt.Image bg) {
		super();
		this.bg = bg;
	}
	
	protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(bg, 0, 0, null);
	}
	
}

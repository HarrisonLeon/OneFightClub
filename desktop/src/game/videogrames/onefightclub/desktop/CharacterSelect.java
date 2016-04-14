package game.videogrames.onefightclub.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterSelect extends JFrame{
	private JPanel background;
	private JButton left;
	private JLabel image;
	private JButton right;
	private JButton start;
	private JFrame jf;
	
	public CharacterSelect() {
		jf = this;
		setTitle("Character Selection");
		init();
		createGUI();
		addActions();
		jf.setSize(600, 500);
		jf.setLocation(500, 150);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void init() {
		background = new JPanel();
		left = new JButton();
		left.setIcon(new ImageIcon("images/leftarrow.png"));
		
		right = new JButton();
		right.setIcon(new ImageIcon("images/rightarrow.png"));
		
		image = new JLabel();
		image.setIcon(new ImageIcon("images/basic.png"));
		start = new JButton("Start");
	}
	
	private void createGUI() {
		background.setLayout(null);
		background.setBackground(Color.white);
		

		Insets insets = background.getInsets();
		Dimension size = null;
		
		background.add(left);
		size = left.getPreferredSize();
		left.setBackground(Color.white);
		//left.setBorderPainted(false);
		left.setBounds(75 + insets.left, 200+ insets.top, size.width, size.height);
		
		background.add(image);
		size = image.getPreferredSize();
		image.setBackground(Color.white);
		image.setBounds(240 + insets.left, 200 + insets.top, size.width, size.height);
		
		background.add(right);
		size = right.getPreferredSize();
		right.setBackground(Color.white);
		right.setBounds(400 + insets.left, 200 + insets.top, size.width, size.height);
		
		add(background);
	}
	
	private void addActions() {
		
	}
	
	public static void main(String[] args) {
		CharacterSelect cs = new CharacterSelect();
	}
	
}

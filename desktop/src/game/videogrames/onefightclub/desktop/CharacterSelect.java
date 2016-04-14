package game.videogrames.onefightclub.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class CharacterSelect extends JFrame{
	private JPanel background;
	private JButton left;
	private JLabel image;
	private JButton right;
	private JButton start;
	private JFrame jf;
	private Font customFont;
	static Mixer mixer;
	static Clip clip;
	private Server s;
	private Client c;
	private String user;
	
	public CharacterSelect(Server s, Client c, String username) {
		jf = this;
		this.s = s;
		this.c = c;
		this.user = username;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/leadcoat.ttf")).deriveFont(20f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		UIManager.put("Button.font", new FontUIResource(customFont));
		UIManager.put("Label.font", new FontUIResource(customFont));
		setTitle("Character Selection");
		init();
		createGUI();
		addActions();
		jf.setIconImage(new ImageIcon("images/basic-sword.png").getImage());
		jf.setSize(600, 500);
		jf.setLocation(500, 150);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
		left.setBorderPainted(false);
		left.setBounds(75 + insets.left, 200+ insets.top, size.width, size.height);
		
		background.add(image);
		size = image.getPreferredSize();
		image.setBackground(Color.white);
		image.setBounds(240 + insets.left, 150 + insets.top, size.width, size.height);
		
		background.add(right);
		size = right.getPreferredSize();
		right.setBackground(Color.white);
		right.setBorderPainted(false);
		right.setBounds(400 + insets.left, 200 + insets.top, size.width, size.height);
		
		
		background.add(start);
		size = start.getPreferredSize();
		start.setBackground(Color.white);
		start.setBorderPainted(false);
		start.setBounds(500 + insets.left, 400 + insets.top, size.width, size.height);
		
		
		add(background);
	}
	
	private void addActions() {
		left.addMouseListener(new MouseAdapter() {		
			public void mousePressed(MouseEvent e) {
				sound_clicked();
				
			}
		});
		
		right.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				sound_clicked();
				
			}
		});
		
		start.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				sound_woosh();
				start.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				start.setForeground(Color.black);
			}
			
			public void mousePressed(MouseEvent e) {
				sound_clicked();
				
			}
		});
	}
	
	void sound_clicked() {
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try { clip = (Clip)mixer.getLine(dataInfo); }
		catch (LineUnavailableException lue){lue.printStackTrace();}
		
		try
		{
			File file = new File("Desktop_Sounds/Button_Click.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip.open(audioStream);
		} catch (LineUnavailableException lue) { lue.printStackTrace();}
		  catch (UnsupportedAudioFileException uafe) {uafe.printStackTrace(); }	
		  catch (IOException ioe) {ioe.printStackTrace();}
		clip.start();
	}
	
	void sound_woosh() { // I hate copy-pasting codeeeeee (but libgdx sound isn't working in desktop launcher)
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try { clip = (Clip)mixer.getLine(dataInfo); }
		catch (LineUnavailableException lue){lue.printStackTrace();}
		
		try
		{
			File file = new File("Desktop_Sounds/Whoosh.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip.open(audioStream);
		} catch (LineUnavailableException lue) { lue.printStackTrace();}
		  catch (UnsupportedAudioFileException uafe) {uafe.printStackTrace(); }	
		  catch (IOException ioe) {ioe.printStackTrace();}
		clip.start();
	}
	
	public static void main(String[] args) {
		CharacterSelect cs = new CharacterSelect(null,null,"test");
	}
	
}

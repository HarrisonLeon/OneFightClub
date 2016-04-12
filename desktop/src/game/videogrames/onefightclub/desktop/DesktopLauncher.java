package game.videogrames.onefightclub.desktop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.backends.lwjgl.audio.OpenALAudio;

import game.videogrames.onefightclub.OneFightClub;
import game.videogrames.onefightclub.utils.Constants;
import game.videogrames.onefightclub.utils.UserInfo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.applet.Main;

public class DesktopLauncher extends JFrame
{	
	  static Mixer mixer;
	  static Clip clip;
	
	  JLabel logo;
	  JLabel slogan;
	
	  JLabel user;
	  JLabel userSign;
	  JLabel pass;
	  JLabel passSign;
	  JLabel repeat;
	
	  JButton login;
	  JButton signin;
	  JButton signup;
	  JButton register;
	  JButton offline;
	
	  JTextField username;
	  JPasswordField password;
	
	  JTextField userSignUp;
	  JPasswordField passSignUp;
	  JPasswordField passwordConfirm;
	
	  JPanel firstScreen;
	  JPanel loginScreen;
	  JPanel registerScreen;
	  JPanel cardLayout;
	
	  JFrame jf = this;
	  
	  Font customFont;
	  Font logoFont;
	
	  CardLayout cl;
	  
	  File sound_clicked;
	
	DesktopLauncher() {
		sound_clicked = new File("Button_Click");
	
		jf = this;
		jf.setIconImage(new ImageIcon("images/basic-sword.png").getImage());
		//new Thread(Constant.s = new Server(3306)).start();
		//new Thread(Constant.c = new Client()).start();
		createGUI();
		createFirstGUI();
		createLoginGUI();
		createRegisterGUI();
		addActions();
		jf.setSize(400, 500);
		jf.setLocation(500, 150);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] arg)
	{
		DesktopLauncher dl = new DesktopLauncher();
	}
	
	private void createGUI() {
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/leadcoat.ttf")).deriveFont(20f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
			logoFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/italic_bricks.ttf")).deriveFont(16f);
			ge.registerFont(logoFont);
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		UIManager.put("Button.font", new FontUIResource(customFont));
		UIManager.put("Label.font", new FontUIResource(customFont));
		
		logo = new JLabel("2-0:");
		
		slogan = new JLabel("The One Fight Club");

		user = new JLabel("Username: ");
		userSign = new JLabel("Username: ");
		pass = new JLabel("Password: ");
		passSign = new JLabel("Password: ");	
		repeat = new JLabel("Repeat: ");
	
		login = new JButton("Login");
		signin = new JButton("Sign In");
		signup = new JButton("Signup");
		offline = new JButton("Offline");	
		register = new JButton("Register");
		
		username = new JTextField();
		password = new JPasswordField();
		userSignUp = new JTextField();
		passSignUp = new JPasswordField();
		passwordConfirm = new JPasswordField();
		
		firstScreen = new JPanel();
		loginScreen = new JPanel();	
		registerScreen = new JPanel();
		
		cardLayout = new JPanel();
		cl = new CardLayout();
		cardLayout.setLayout(cl);
	}
	
	private void createFirstGUI() {
		//First Screen
		
		firstScreen.setLayout(new BorderLayout());
		JLabel overall = new JLabel();
		overall.setIcon(new ImageIcon("images/clipboard.png"));
		overall.setLayout(null);
		
		
		Insets insets = overall.getInsets();
		Dimension size;
		
		overall.add(logo);
		logo.setFont(logoFont);
		logo.setBackground(Color.white);
		size = logo.getPreferredSize();
		logo.setBounds(170 + insets.left, 120 + insets.top, size.width, size.height);
		
		overall.add(slogan);
		slogan.setFont(logoFont);
		slogan.setBackground(Color.white);
		size = slogan.getPreferredSize();
		slogan.setBounds(80 + insets.left, 150 + insets.top, size.width, size.height);
		
		overall.add(login);
		size = signup.getPreferredSize();
		login.setBackground(Color.white);
		login.setBorderPainted(false);
		login.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				sound_woosh();
				login.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				login.setForeground(Color.black);
			}
			
			public void mousePressed(MouseEvent e) {
				sound_clicked();
			}
			
		});
		login.setBounds(120 + insets.left, 200 + insets.top, size.width, size.height);
		
		overall.add(signup);
		signup.setBackground(Color.white);
		signup.setBorderPainted(false);
		signup.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				sound_woosh();
				signup.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signup.setForeground(Color.black);
			}
			
			public void mousePressed(MouseEvent e) {
				sound_clicked();
			}
			
		});
		signup.setBounds(120 + insets.left + login.getPreferredSize().width, 200 + insets.top, size.width, size.height);
		
		overall.add(offline);
		size = offline.getPreferredSize();
		offline.setBackground(Color.white);
		offline.setBorderPainted(false);
		offline.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				sound_woosh();
				offline.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				offline.setForeground(Color.black);
			}
			
			public void mousePressed(MouseEvent e) {
				sound_clicked();
			}
			
		});
		offline.setBounds(153 + insets.left, 250, size.width, size.height);
		
		firstScreen.add(overall);
		
		cardLayout.add(firstScreen, "1");
		
		cl.show(cardLayout, "1");
		jf.add(cardLayout);
	}
	
	private void createLoginGUI() {
		
		loginScreen.setLayout(new BorderLayout());
		JLabel overall = new JLabel();
		overall.setIcon(new ImageIcon("images/clipboard.png"));
		overall.setLayout(null);
		
		Insets insets = overall.getInsets();
		Dimension size = user.getPreferredSize();
		
		overall.add(user);
		user.setBackground(Color.white);
		user.setBounds(95 + insets.left, 120 + insets.top, size.width, size.height);
		
		overall.add(username);
		size = username.getPreferredSize();
		username.setBounds(95 + insets.left + pass.getPreferredSize().width, 120 + insets.top, 100, size.height);
		
		overall.add(pass);
		size = pass.getPreferredSize();
		pass.setBounds(95 + insets.left, 175 + insets.top, size.width, size.height);
		
		overall.add(password);
		size = password.getPreferredSize();
		password.setBounds(95 + insets.left + pass.getPreferredSize().width, 175 + insets.top, 100, size.height);
		
		overall.add(signin);
		size = signin.getPreferredSize();
		signin.setBorderPainted(false);
		signin.setBackground(Color.white);
		signin.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				sound_woosh();
				signin.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signin.setForeground(Color.black);
			}
			
			public void mousePressed(MouseEvent e) {
				sound_clicked();
			}
			
		});
		signin.setBounds(150 + insets.left, 225 + insets.top, size.width, size.height);
		
		loginScreen.add(overall);
		
		cardLayout.add(loginScreen, "2");
		
	}
	
	private void createRegisterGUI() {
		registerScreen.setLayout(new BorderLayout());
		JLabel overall = new JLabel();
		overall.setIcon(new ImageIcon("images/clipboard.png"));
		overall.setLayout(null);
	
		Insets insets = overall.getInsets();
		Dimension size = user.getPreferredSize();
		
		overall.add(userSign);
		userSign.setBackground(Color.white);
		userSign.setBounds(100 + insets.left, 120 + insets.top, size.width, size.height);
		
		overall.add(userSignUp);
		size = userSignUp.getPreferredSize();
		userSignUp.setBounds(95 + insets.left + passSign.getPreferredSize().width, 120 + insets.top, 100, size.height);
		
		overall.add(passSign);
		size = passSign.getPreferredSize();
		passSign.setBounds(95 + insets.left, 175 + insets.top, size.width, size.height);
		
		overall.add(passSignUp);
		size = passSignUp.getPreferredSize();
		passSignUp.setBounds(95 + insets.left + passSign.getPreferredSize().width, 175 + insets.top, 100, size.height);
		
		overall.add(repeat);
		size = repeat.getPreferredSize();
		repeat.setBackground(Color.white);
		repeat.setBounds(122 + insets.left, 230 + insets.top, size.width, size.height);
		
		overall.add(passwordConfirm);
		size = passwordConfirm.getPreferredSize();
		passwordConfirm.setBounds(95 + insets.left + passSign.getPreferredSize().width, 230 + insets.top, 100, size.height);
		
		overall.add(register);
		size = register.getPreferredSize();
		register.setBackground(Color.white);
		register.setBorderPainted(false);
		register.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				sound_woosh();
				register.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				register.setForeground(Color.black);
			}
			
			public void mousePressed(MouseEvent e) {
				sound_clicked();
				
				String alphabetUpper = "ABCDEFHIJKLMNOPQRSTUVWXYZ";
				String numbers = "1234567890";
				
				String password = new String(passSignUp.getPassword());
				
				boolean hasAlpha = false;
				boolean hasNumeric = false;
				
				for(int i = 0; i < alphabetUpper.length(); i++) {
					if(password.contains(Character.toString(alphabetUpper.charAt(i)))){
						hasAlpha = true;
					}
					if(i < numbers.length()) {
						if(password.contains(Character.toString(numbers.charAt(i)))) {
							hasNumeric = true;
						}
					}
				}
				
				if(!hasAlpha || !hasNumeric) {
					JOptionPane.showMessageDialog(null, "Passwords require 1 alpha character and 1 number", "Register Failed!", JOptionPane.WARNING_MESSAGE);
				}
				
				else if(passSignUp.getPassword() != passwordConfirm.getPassword()) {
					JOptionPane.showMessageDialog(null, "Passwords don't match!", "Register Failed!", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		
		register.setBounds(140 + insets.left, 285 + insets.top, size.width, size.height);
		
		registerScreen.add(overall);
		
		cardLayout.add(registerScreen, "3");
		
	}
	
	private void addActions() {
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cardLayout, "2");
			}
			
		});
		
		signup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cardLayout, "3");
			}
			
		});
		
		offline.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(false);
				String[] userinfo = {"offline"};
				start(userinfo);
			}
			
		});
	}
	  
	void start(String[] args) {
		String line = "";
		String username = "";
		
		for(int i = 0; i < args.length; i++) {
			if(i == 0) {
				line = args[i];	
			}
			if(i == 1) {
				username = args[i];
			}
		}
		
		Constants.ui = new UserInfo(line, username);
		
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "OneFightClub";
		config.useGL30 = false;
		config.width = Constants.APP_WIDTH;
		config.height = Constants.APP_HEIGHT;

		new LwjglApplication(new OneFightClub(), config);
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
}



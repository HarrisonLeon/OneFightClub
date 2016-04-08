package game.videogrames.onefightclub.desktop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.videogrames.onefightclub.OneFightClub;
import game.videogrames.onefightclub.utils.Constants;
import game.videogrames.onefightclub.utils.UserInfo;

public class DesktopLauncher extends JFrame
{
	  JLabel logo;
	
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
	
	  CustomJPanel firstScreen;
	  JPanel loginScreen;
	  JPanel registerScreen;
	  JPanel cardLayout;
	
	  JFrame jf = this;
	  
	  Font customFont;
	
	  CardLayout cl;
	
	DesktopLauncher() {
		jf = this;
		createGUI();
		createFirstGUI();
		createLoginGUI();
		createRegisterGUI();
		addActions();
		
		jf.setSize(400, 500);
		jf.setLocation(500, 150);
		jf.setResizable(false);
		jf.setVisible(true);
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
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		UIManager.put("Button.font", new FontUIResource(customFont));
		
		logo = new JLabel("2-0: The One Fight Club");
		//logo.setFont(customFont);

		user = new JLabel("Username: ");
		//user.setFont(customFont);
		
		userSign = new JLabel("Username: ");
		//userSign.setFont(customFont);
		
		pass = new JLabel("Password: ");
		//pass.setFont(customFont);
		
		passSign = new JLabel("Password: ");
		//passSign.setFont(customFont);
		
		repeat = new JLabel("Repeat: ");
		//repeat.setFont(customFont);
		
		login = new JButton("Login");
		//login.setFont(customFont);
		
		signin = new JButton("Sign In");
		//signin.setFont(customFont);
		
		signup = new JButton("Signup");
		//signup.setFont(customFont);
		
		offline = new JButton("Offline");
		//offline.setFont(customFont);
		
		register = new JButton("Register");
		//register.setFont(customFont);
		
		username = new JTextField();
		password = new JPasswordField();
		userSignUp = new JTextField();
		passSignUp = new JPasswordField();
		passwordConfirm = new JPasswordField();
		
		firstScreen = new CustomJPanel(Toolkit.getDefaultToolkit().createImage("clipboard.png"));
		//firstScreen.setImage();
		
		
		loginScreen = new JPanel();
		loginScreen.setBackground(Color.gray);
		
		registerScreen = new JPanel();
		registerScreen.setBackground(Color.gray);
		
		
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
		
		overall.add(login);
		Dimension size = signup.getPreferredSize();
		login.setBackground(Color.white);
		login.setBorderPainted(false);
		login.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				login.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				login.setForeground(Color.black);
				
			}
			
		});
		login.setBounds(100 + insets.left, 200 + insets.top, size.width, size.height);
		
		overall.add(signup);
		signup.setBackground(Color.white);
		signup.setBorderPainted(false);
		signup.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				signup.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signup.setForeground(Color.black);
				
			}
			
		});
		signup.setBounds(140 + insets.left + login.getPreferredSize().width, 200 + insets.top, size.width, size.height);
		
		overall.add(offline);
		size = offline.getPreferredSize();
		offline.setBackground(Color.white);
		offline.setBorderPainted(false);
		offline.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {
				offline.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				offline.setForeground(Color.black);
				
			}
			
		});
		offline.setBounds(153 + insets.left, 250, size.width, size.height);
		
		firstScreen.add(overall);
		
		cardLayout.add(firstScreen, "1");
		
		cl.show(cardLayout, "1");
		jf.add(cardLayout);
	}
	
	private void createLoginGUI() {

		JPanel overall = new JPanel();
		overall.setLayout(new BoxLayout(overall, BoxLayout.Y_AXIS));
		overall.setBackground(Color.gray);
		
		JPanel usernamePassword = new JPanel();
		usernamePassword.setLayout(new GridLayout(0,2));
		usernamePassword.setBackground(Color.gray);
		usernamePassword.add(user);
		usernamePassword.add(username);
		usernamePassword.add(pass);
		usernamePassword.add(password);
		
		overall.add(usernamePassword);
		
		overall.add(signin);
		
		loginScreen.add(overall);
		
		loginScreen.setBackground(Color.gray);
		
		cardLayout.add(loginScreen, "2");
		
	}
	
	private void createRegisterGUI() {
		
		JPanel overall = new JPanel();
		overall.setLayout(new BoxLayout(overall, BoxLayout.Y_AXIS));
		overall.setBackground(Color.gray);
		
		JPanel usernamePassword = new JPanel();
		usernamePassword.setLayout(new GridLayout(0,2));
		usernamePassword.setBackground(Color.gray);
		usernamePassword.add(userSign);
		usernamePassword.add(userSignUp);
		usernamePassword.add(passSign);
		usernamePassword.add(passSignUp);
		usernamePassword.add(repeat);
		usernamePassword.add(passwordConfirm);
		
		overall.add(usernamePassword);
		
		overall.add(register);
		
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
	
	
}
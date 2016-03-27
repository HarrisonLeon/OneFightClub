package game.videogrames.onefightclub.desktop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.videogrames.onefightclub.OneFightClub;
import game.videogrames.onefightclub.utils.Constants;
import game.videogrames.onefightclub.utils.UserInfo;

public class DesktopLauncher extends JFrame
{
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
	
	  CardLayout cl;
	
	DesktopLauncher() {
		jf = this;
		createGUI();
		createFirstGUI();
		createLoginGUI();
		createRegisterGUI();
		addActions();
		
		jf.setSize(300, 400);
		jf.setVisible(true);
	}

	public static void main(String[] arg)
	{
		DesktopLauncher dl = new DesktopLauncher();
	}
	
	private void createGUI() {
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
		
		firstScreen = new JPanel();
		firstScreen.setBackground(Color.gray);
		
		
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
		JPanel overall = new JPanel();
		overall.setLayout(new BoxLayout(overall, BoxLayout.Y_AXIS));
		
		JPanel addLoginSignup = new JPanel();
		addLoginSignup.add(login);
		addLoginSignup.add(signup);
		addLoginSignup.setBackground(Color.gray);
		
		JPanel offlineP = new JPanel();
		offlineP.add(offline);
		offlineP.setBackground(Color.gray);
		
		JPanel optionsPanel = new JPanel(new BorderLayout());
		optionsPanel.add(addLoginSignup, "North");
		optionsPanel.add(offlineP, "Center");
		optionsPanel.setBackground(Color.gray);
		
		overall.add(optionsPanel);
		firstScreen.add(overall, BorderLayout.CENTER);
		
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
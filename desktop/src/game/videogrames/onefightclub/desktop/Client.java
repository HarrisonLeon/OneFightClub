package game.videogrames.onefightclub.desktop;
	import java.awt.Font;
	import java.awt.FontFormatException;
	import java.awt.GraphicsEnvironment;
	import java.io.BufferedReader;
	import java.io.File;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.io.PrintWriter;
	import java.net.Socket;
	import java.net.UnknownHostException;
	import java.util.ArrayList;
	import java.util.Hashtable;
	import java.util.Vector;

	import javax.swing.JOptionPane;
	import javax.swing.UIManager;
	import javax.swing.plaf.FontUIResource;

public class Client extends Thread{
	boolean online = true;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	Socket s;
	private boolean toSleep = false;
	private Hashtable<String, Boolean> exists;
	private Hashtable<String, Boolean> authenticate;
	
	Client() {
		System.out.println("Starting Client");
		exists = new Hashtable<String, Boolean>();
		authenticate = new Hashtable<String, Boolean>();
		Font customFont; 
		try {
				s = new Socket("localhost", 1234);
				System.out.println("connected!");
				oos = new ObjectOutputStream(s.getOutputStream());
				System.out.println("oos working");
				ois = new ObjectInputStream(s.getInputStream());
				System.out.println("ois working");
			
			System.out.println("Created host");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			try {
				online = false;
				customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/leadcoat.ttf")).deriveFont(12f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(customFont);
				System.out.println("customed!");
				UIManager.put("OptionPane.messageFont", new FontUIResource(customFont));
				UIManager.put("Button.font", new FontUIResource(customFont));
				System.out.println("client " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Server cannot be reached. Program in offline mode.", "Log in Failed", JOptionPane.WARNING_MESSAGE);
			} catch (FontFormatException | IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	public void sendMessage(String message) {
		try {
			oos.writeObject(message);
			oos.flush();
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addUser(String username, String password) {
		sendMessage("$:" + username + ":" + password);
	}
	
	public boolean authenticate(String username, String password) {
		boolean pass = false;
		sendMessage("^:" + username + ":" + password);
			
		while(!authenticate.containsKey(username)) {
			
		}
		
		pass = authenticate.get(username);
		return pass;
	}
	
	public boolean checkUser(String username) {
		boolean pass = false;
		System.out.println("checking " + username);
		sendMessage("@:" + username);
		
		while(!exists.containsKey(username)) {
			
		}
		System.out.println("output");
		pass = exists.get(username);
			
		return pass;
	}

	public void changeCharacter(String username, String charSprite) {
		sendMessage("#:" + username + ":" + charSprite);
	}

	public void updateStats(String username, int k, int d, int j, int killstreak) {
		sendMessage("*:" + username + ":" + k + ":" + d + ":" + j + ":" + killstreak);
	}
	
	public void close() {
		try {
			if (s != null) {
				s.close();
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	public void run() {
		try {
			while(true) {
				String message = (String)ois.readObject();
				System.out.println(message);
				String[] info = message.split(":");
				if(info[0].equals("^")) {
					boolean bo = false;
					if(info[2].equals("true")) {
						bo = true;
					}
					if(info[1] != null) {
						authenticate.put(info[1], bo);
					}
				}
				if(info[0].equals("@")) {
					boolean bo = false;
					if(info[2].equals("true")) {
						bo = true;
					}
					System.out.println(info[1] + " " + bo);
					exists.put(info[1], bo);
					System.out.println("put exists");
				}
				if(info[0].equals("+")) {
					if(info[1].equals("Server Stopped")) {
						online = false;
					}
					else {
						online = true;
					}
				}
				
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public boolean isOnline() {
		return online;
	}
}

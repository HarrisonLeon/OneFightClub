package game.videogrames.onefightclub.desktop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server serv;
	private ClientDatabase cd;
	public ServerThread(Socket s, Server serv, ClientDatabase cd) {
		try {
			System.out.println("starting server thread");
			this.serv = serv;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.cd = cd;
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(String message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void run() {
		try {
			oos.writeObject("Welcome to the server");
			while(true) {
				String message = (String)ois.readObject();
				String[] info = message.split(":");
				String prefix = info[0];
				String username = "";
				String password = "";
				if(info[0].equals("$")) {
					cd.addUser(info[1], info[2]);
				}
				if(info[0].equals("^")) {
					String pass = cd.findPassword(info[1]);
						if(pass.equals(info[2])) {
							sendMessage("^:" + info[1] + ":" + "true");
						}
						else {
							sendMessage("^:" + info[1] + ":" + "false");
						}
				}
				if(info[0].equals("@")) {
					System.out.println(info[1]);
					String pass = cd.findPassword(info[1]);
						if(pass == "") {
							sendMessage("@:" + info[1] + ":" + "true");
						}
						else {
							sendMessage("@:" + info[1] + ":" + "false");
						}
				}
				if(info[0].equals("#")) {
					System.out.println(info[1] + " " + info[2]);
					cd.setCharacter(info[1], info[2]);
				}
				if(info[0].equals("*")) {
					cd.setKillsDeathsJumpsKillStreak(info[1], info[2], info[3], info[4], info[5]);
				}
				
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in run: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
	}
}

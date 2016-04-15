package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable {
	private Vector<ServerThread> serverThreads;
	private ServerSocket ss;
	private int port;
	private boolean started;
	private Socket s;
	private ClientDatabase cd;
	
	public Server(int port) {
		this.port = port;
		serverThreads = new Vector<ServerThread>();
		cd = new ClientDatabase();
		this.start();
	}
	
	public void start() {
		started = true;
		for(ServerThread st : serverThreads) {
			st.sendMessage("+:" + "Server Started");
		}
		//this.run();
	}
	
	public void stop() {
		started = false;
		for(ServerThread st : serverThreads) {
			st.sendMessage("+:" + "Server Stopped");
		}
		try {
			if(s != null ) {
				s.close();
			}
			if(ss != null) {
				ss.close();	
				ss = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean started() { return started;}
	
	@Override
	public void run() {
		while(true) {
			if(started) {
				try {
					if(ss == null) {
						ss = new ServerSocket(port);	
					}
					s = ss.accept();
					System.out.println("connection from " + s.getInetAddress());
					ServerThread st = new ServerThread(s, this, cd);
					serverThreads.add(st);
				} catch (IOException ioe) {
					if(ioe.getMessage().equals("Socket is closed")) {
						
					}
					else {System.out.println("ioe: " + ioe.getMessage());}
				} finally {
					if (ss != null) {
						try {
							ss.close();
						} catch (IOException ioe) {
							System.out.println("ioe closing ss: " + ioe.getMessage());
						}
					}
				}
			}
		}
		
	}
	
	public void sendMessageToAll(String message) {
		for(ServerThread st : serverThreads) {
			st.sendMessage(message + " From server");
			System.out.println(message + " From server");
		}
	}
}

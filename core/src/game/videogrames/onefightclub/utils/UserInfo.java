package game.videogrames.onefightclub.utils;

public class UserInfo {
	boolean online = false;
	String username = "";
	int maxlevel = 0;
	int character = 0;
	int kills = 0;
	int deaths = 0;
	int longestKillStreak = 0;
	int jumps = 0;
	
	UserInfo(String line, String username) {
		if(line.equals("online")) {
			online = true;
			this.username = username;
			//get info from database
			
		}
	}
	
	public String userName() {
		return username;
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public int character() {
		return character;
	}
	
	public int numKills() {
		return kills;
	}
	
	public int numDeaths() {
		return deaths;
	}
	
	public int numJumps() {
		return jumps;
	}
	
	public int killStreak() {
		return longestKillStreak;
	}
}

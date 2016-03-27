package game.videogrames.onefightclub.utils;

public class UserInfo {
    boolean online;
    String username;
    int maxlevel;
    int character;
    int kills;
    int deaths = 0;
    int longestKillStreak;
    int jumps;

    public UserInfo(String line, String username) {
	if (line.equals("online")) {
	    online = true;
	    this.username = username;
	    // get info from database

	} else {
	    online = false;
	    username = "";
	    maxlevel = 0;
	    character = 0;
	    kills = 0;
	    deaths = 0;
	    longestKillStreak = 0;
	    jumps = 0;
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

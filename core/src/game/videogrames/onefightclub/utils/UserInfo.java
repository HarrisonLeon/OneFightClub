package game.videogrames.onefightclub.utils;

import java.util.Vector;

import client.Client;

public class UserInfo {
    private boolean online;
    private String username;
    private int maxlevel;
    private int character;
    private int kills;
    private int deaths = 0;
    private int longestKillStreak;
    private int jumps;
    private Client c;

    public UserInfo(String line, String username, Client c2) {
	
    if (line.equals("online")) {
    	this.c = c2;
	    online = true;
	    this.username = username;
	   
	    // get info from database
	    Vector<String> stats = c.getStats(username);
	    maxlevel = Integer.parseInt(stats.get(0));
	    character = Integer.parseInt(stats.get(1));
	    kills = Integer.parseInt(stats.get(2));
	    deaths = Integer.parseInt(stats.get(3));
	    jumps = Integer.parseInt(stats.get(4));
	    longestKillStreak = Integer.parseInt(stats.get(5));
	    
	    System.out.println(character);

	} else {
	    online = false;
	    username = "";
	    maxlevel = 1;
	    character = 1;
	    kills = 0;
	    deaths = 0;
	    longestKillStreak = 0;
	    jumps = 0;
	}
    }
    
    public void updateStats(String username, int kills, int deaths, int jumps, int killstreak) {
    	this.kills = kills;
    	this.deaths = deaths;
    	this.jumps = jumps;
    	this.longestKillStreak = killstreak;
    	if(online) {
    		c.updateStats(username, kills, deaths, jumps, killstreak);	
    	}
    }

    public String userName() {
    	return username;
    }

    public boolean isOnline() {
    	return online;
    }

    public int maxLevel() {
    	return maxlevel;
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

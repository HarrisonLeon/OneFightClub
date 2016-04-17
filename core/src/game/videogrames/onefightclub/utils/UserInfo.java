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
    private Vector<Boolean> achievements;

    public UserInfo(String line, String username, Client c2) {
	achievements = new Vector<Boolean>();
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
	    
	    achievements = new Vector<Boolean>();
	    if (kills >= 1) { 
	    	achievements.add(true); 
	    } else { 
	    	achievements.add(false); 
	    }
	    if (kills >= 10) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (kills >= 100) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (deaths >= 1) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (deaths >= 10) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (deaths >= 100) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (jumps >= 1) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (jumps >= 10) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (jumps >= 100) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (longestKillStreak >= 5) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (longestKillStreak >= 10) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    if (longestKillStreak >= 20) {
	    	achievements.add(true);
	    } else {
	    	achievements.add(false);
	    }
	    
	    System.out.println(character);

	} else {
		for(int i = 0; i < 12; i++) {
			achievements.add(false);
		}
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
    
    public void updateStats() {
    	if(online || c != null) {
    		if (kills >= 1) { 
    	    	achievements.set(0, true); 
    	    } else { 
    	    	achievements.set(0, false); 
    	    }
    	    if (kills >= 10) {
    	    	achievements.set(1, true);
    	    } else {
    	    	achievements.set(1, false);
    	    }
    	    if (kills >= 100) {
    	    	achievements.set(2, true);
    	    } else {
    	    	achievements.set(2, false);
    	    }
    	    if (deaths >= 1) {
    	    	achievements.set(3, true);
    	    } else {
    	    	achievements.set(3, false);
    	    }
    	    if (deaths >= 10) {
    	    	achievements.set(4, true);
    	    } else {
    	    	achievements.set(4, false);
    	    }
    	    if (deaths >= 100) {
    	    	achievements.set(5, true);
    	    } else {
    	    	achievements.set(5, false);
    	    }
    	    if (jumps >= 1) {
    	    	achievements.set(6, true);
    	    } else {
    	    	achievements.set(6, false);
    	    }
    	    if (jumps >= 10) {
    	    	achievements.set(7, true);
    	    } else {
    	    	achievements.set(7, false);
    	    }
    	    if (jumps >= 100) {
    	    	achievements.set(8, true);
    	    } else {
    	    	achievements.set(8, false);
    	    }
    	    if (longestKillStreak >= 5) {
    	    	achievements.set(9, true);
    	    } else {
    	    	achievements.set(9, false);
    	    }
    	    if (longestKillStreak >= 10) {
    	    	achievements.set(10, true);
    	    } else {
    	    	achievements.set(10, false);
    	    }
    	    if (longestKillStreak >= 20) {
    	    	achievements.set(11, true);
    	    } else {
    	    	achievements.set(11, false);
    	    }
    		c.updateStats(username, kills, deaths, jumps, longestKillStreak);	
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

    public void setmaxLevel(int ml) {
    	maxlevel = ml;
    }
    
    public void setcharacter(int ch) {
    	character = ch;
    }

    public void setnumKills(int num) {
    	kills = num;
    }

    public void setnumDeaths(int num) {
    	deaths = num;
    }

    public void setnumJumps(int num) {
    	jumps = num;
    }

    public void setkillStreak(int num) {
    	longestKillStreak = num;
    }
    
    
    /* Index key for achievement vector:
     * 0 = 1 kill
     * 1 = 10 kill
     * 2 = 100 kill
     * 3 = 1 death
     * 4 = 10 death
     * 5 = 100 death
     * 6 = 1 jump
     * 7 = 10 jump
     * 8 = 100 jump
     * 9 = 5 killstreak
     * 10 = 10 killstreak
     * 11 = 20 killstreak
     */
    
    public Vector<Boolean> achievements() {
    	return achievements;
    }
    
}

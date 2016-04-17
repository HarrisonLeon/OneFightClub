package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Vector;

public class ClientDatabase {
	Connection conn = null;
	Statement st = null;
	
	public ClientDatabase() {
		System.out.println("starting database");
		String url = "jdbc:mysql://onefightclub.cvylzfevzynk.us-west-2.rds.amazonaws.com:3306/";
		String userName = "onefightclub";
		String password = "2lawrence01";
		String dbName = "onefightclub";
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
			System.out.println("named class");
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("connected");
			st = conn.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String findPassword(String username) {
		String password = "";
		try {
			System.out.println("trying");
			ResultSet rs = st.executeQuery("SELECT * FROM userpass");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM userpass WHERE username=?");
			System.out.println("yay");
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String user = rs.getString("username");
				if(user.equals(username)) {
					password = rs.getString("pass");
				}
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;
	}

	public void addUser(String username, String password) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO userpass(username,pass) VALUES(?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			System.out.println("prepared");
			ps.executeUpdate();
			System.out.println("executed");
			ps.close();
			
			ps = conn.prepareStatement("INSERT INTO userinfo(username,maxlevel,charSprite,numKills,youDed,jump,killstreak) VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, "1");
			ps.setString(3, "1");
			ps.setString(4, "0");
			ps.setString(5, "0");
			ps.setString(6, "0"); 
			ps.setString(7, "0");
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setLevel(String username, String ml) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE userinfo SET maxlevel=? WHERE username=?");
			ps.setString(2, username);
			ps.setString(1, ml);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setKillsDeathsJumpsKillStreak(String username, String k, String d, String j, String ks) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE userinfo SET numKills=? youDed=? WHERE username=?");
			ps.setString(3, username);
			ps.setString(1, k);
			ps.setString(2, d);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement("UPDATE userinfo SET jump=? killstreak=? WHERE username=?");
			ps.setString(1, j);
			ps.setString(2, ks);
			ps.setString(3, username);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<String> getStats(String username) {
		Vector<String> Stats = new Vector<String>();
		try {
			String query ="SELECT * FROM userinfo";
			ResultSet rs = st.executeQuery(query);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM userinfo WHERE username=?");
			
			ps.setString(1, username);
			rs = ps.executeQuery();
			System.out.println("adding");
			
			while(rs.next()) {
				Stats.add(rs.getString(2));
				Stats.add(rs.getString(3));
				Stats.add(rs.getString(4));
				Stats.add(rs.getString(5));
				Stats.add(rs.getString(6));
				Stats.add(rs.getString(7));
			}
			ps.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Stats;
	}
	
	public void setCharacter(String username, String charS) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE userinfo SET charSprite=? WHERE username=?");
			ps.setString(1, charS);
			ps.setString(2, username);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
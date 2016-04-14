package game.videogrames.onefightclub.desktop;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			
			ps = conn.prepareStatement("INSERT INTO userinfo(user,maxlevel,charSprite,numKills,numDeaths,jump,killstreak) VALUES(?,?,?,?,?,?,?)");
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
	
	public void setChar(String username, int c) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE userinfo(user,character) SET VALUES(?,?)");
			ps.setString(1, username);
			ps.setInt(3, c);
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setLevel(String username, int ml) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE userinfo SET maxlevel=? WHERE user=?");
			ps.setString(2, username);
			ps.setInt(1, ml);
			ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setKillsDeathsJumpsKillStreak(String username, int k, int d, int j, int ks) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE userinfo SET numKills=? numDeaths=? jump=? killstreak=? WHERE user=?");
			ps.setString(5, username);
			ps.setString(1, Integer.toString(k));
			ps.setString(2, Integer.toString(d));
			ps.setString(3, Integer.toString(j));
			ps.setString(4, Integer.toString(ks));
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setCharacter(String username, int charS) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE userinfo SET charSprite=? WHERE user=?");
			ps.setString(1, Integer.toString(charS));
			ps.setString(2, username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
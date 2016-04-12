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
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + Constant.ip + "/" + "?user=" + Constant.username + "&password=" + Constant.password); //import java.sql
			st = conn.createStatement();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch(ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}

	}

	public String findPassword(String username) {
		String password = "";
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM passandfiles");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM passandfiles WHERE username=?");
			
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
			PreparedStatement ps = conn.prepareStatement("INSERT INTO passandfiles(username,pass) VALUES(?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
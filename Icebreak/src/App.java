import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.sql.*;

public class App {

	public static void main(String[] args) {
		DatabaseManager.connectToDatabase();
		
		if(DatabaseManager.con == null) {
			System.exit(0);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new MainFrame("Icebreak", "adsda");
				frame.setSize(750, 500);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		

	}
	
	public static class DatabaseManager {
		public static Connection con = null;
		
		public static void connectToDatabase() {
			if(con == null) {
				try {
					String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
					String username = "cs421g07";
					String password = "dbdbSQSQ2007";
					
					Class.forName("org.postgresql.Driver");
					
					con = DriverManager.getConnection(url, username, password);
					System.out.println("Successfully connected to database");
				}catch(SQLException | ClassNotFoundException e) {
					System.out.println("Successfully connected to database");
				}
			}else {
				System.out.println("Already connected to database");
			}
		}
		
		public static String login(String email, String password) {
			if(email.isBlank() || email.isEmpty() || email == null || password.isBlank() || password.isEmpty() || password == null) {
				try {
					Statement stmt;
					stmt = con.createStatement();
					String sql;
					sql = "SELECT email FROM Users WHERE email = " + email + " AND password = " + password;
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()) {
						return rs.getString("email");
					}
					
					return null;
				} catch (SQLException e) {
					System.out.println("Error logging in to user account");
					return null;
				}
			}
			
			return null;
		}
		
		public static boolean signup() {
			//return success status
			return false;
		}
		
		public static int match() {
			//return chatid
			return -1;
		}
		
		public static void closeConnection() {
			if(con != null) {
				try {
					con.close();
					System.out.println("Successfully closed connection to database");
				}catch(SQLException e) {
					System.out.println("Error closing connection to database");
				}
			}else {
				System.out.println("No connection to close");
			}
		}
	}

}

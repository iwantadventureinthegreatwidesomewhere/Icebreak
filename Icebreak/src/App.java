import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.sql.*;
import java.util.List;

public class App {

	public static void main(String[] args) {
		DatabaseManager.connectToDatabase();
		
		if(DatabaseManager.con == null) {
			System.exit(0);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new LoginFrame("Icebreak");
				//JFrame frame = new MainFrame("Icebreak", "adsda");
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
					e.printStackTrace();
					System.out.println("Error connecting to database");
				}
			}else {
				System.out.println("Already connected to database");
			}
		}
		
		public static int login(String email, String password) {
			if(!email.isBlank() && !email.isEmpty() && email != null && !password.isBlank() && !password.isEmpty() && password != null) {
				try {
					Statement stmt;
					stmt = con.createStatement();
					String sql;
					sql = "SELECT email FROM Users WHERE email = ? AND password = ?";
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					preparedStatement.setString(1, email);
					preparedStatement.setString(2, password);
					ResultSet rs = preparedStatement.executeQuery();
					
					if(rs.next()) {
						return rs.getInt("userid");
					}
					
					System.out.println("User account not found");
					return -1;
				} catch (SQLException e) {
					System.out.println("Error logging in to user account");
					return -1;
				}
			}
			
			return -1;
		}
		
		public static boolean signup() {
			//return success status
			return false;
		}
		
		public static int match(int userid) {
			try {
				Statement stmt;
				String sql;
				PreparedStatement preparedStatement;
				
				stmt = con.createStatement();
				sql = "(SELECT Users.email"
						+ " FROM Users, Possesses WHERE Users.userid = Possesses.userid"
						+ " AND Users.is_matchmaking = true"
						+ " AND Possesses.type = (SELECT type FROM Possesses WHERE userid = ?))"
						+ " INTERSECT"
						+ " (SELECT Users.userid"
						+ " FROM Users, Likes WHERE Users.userid = Likes.userid"
						+ " AND Likes.type = (SELECT type FROM Likes WHERE userid = ?))";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setInt(1, userid);
				preparedStatement.setInt(2, userid);
				preparedStatement.executeQuery();
				
				
				
				
				
				
				
				stmt = con.createStatement();
				sql = "UPDATE Users SET is_matchmaking = true WHERE userid = ?";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setInt(1, userid);
				preparedStatement.executeQuery();
				
				
				
				
				
				
				
				//checks there is someone else that is matchmaking who is also a strong match
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//return chatid
			//should either scan existing matchmakers to see if 
			return -1;
		}
		
		public static List<Integer> getAllChats() {
			//fetches all chatids of all connections with active chats
			return null;
		}
		
		public static void refreshChat() {
			//not sure what to return, i'm thinking a new object that contains the conversation history and other chat details
			//caller should use this method on loop as we don't have a notification system
			//should also be used to load a previous existing conversation
		}
		
		public static void sendMessage() {
			//return success status
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

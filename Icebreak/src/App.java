import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class App {

	public static void main(String[] args) {
		DatabaseManager.connectToDatabase();
		
		if(DatabaseManager.con == null) {
			System.exit(0);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new LoginFrame("Icebreak");
//				JFrame frame = new MainFrame("Icebreak", 4000);
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
					
//					Class.forName("org.postgresql.Driver");
					
					con = DriverManager.getConnection(url, username, password);
					System.out.println("Successfully connected to database");
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println("Error connecting to database");
				}
			} else {
				System.out.println("Already connected to database");
			}
		}
		
		public static int login(String email, String password) {
//			if(!email.isBlank() && !email.isEmpty() && !password.isBlank() && !password.isEmpty()) {
			if(!email.isEmpty()  && !password.isEmpty()) {
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
		
		public static boolean signup(char gender, Date birthDate, String name, String email,
									 String password, Date createDate, Time createTime,
									 int profilePic, String location) {
			try {
				Statement stmt;
				stmt = con.createStatement();
				String sql;
				sql = "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setInt(1, 500);
				preparedStatement.setString(2, String.valueOf(gender));
				preparedStatement.setDate(3, birthDate);
				preparedStatement.setString(4, name);
				preparedStatement.setString(5, email);
				preparedStatement.setString(6, password);
				preparedStatement.setDate(7, createDate);
				preparedStatement.setInt(8, profilePic);
				preparedStatement.setBoolean(9, true);
				preparedStatement.setBoolean(10, false);
				preparedStatement.setString(11, location);

				return !preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error signing up user account");
				return false;
			}
		}
		
		public static int match(int userid) {
			try {
				Statement stmt;
				String sql;
				PreparedStatement preparedStatement;
				
				stmt = con.createStatement();
				sql = "(SELECT Users.userid"
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
				ResultSet matchedUsers = preparedStatement.executeQuery();
				
				if(matchedUsers.next()) {
					//found a match
					int matchedUserid = matchedUsers.getInt("userid");
					
					Random random = new Random();
					int chatid = random.nextInt();
					
					//inserts a new chat into the Chats table
					stmt = con.createStatement();
					sql = "INSERT INTO Chats (chatid, date_time_created, is_active)"
							+ " VALUES (?, ?, true)";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, chatid);
					preparedStatement.setDate(2, Date.valueOf(java.time.LocalDate.now()));
					preparedStatement.executeUpdate();
					
					//inserts the matched users into the Participates table
					stmt = con.createStatement();
					sql = "INSERT INTO Participates (chatid, userid)"
							+ " VALUES (?, ?), (?, ?)";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, chatid);
					preparedStatement.setInt(2, userid);
					preparedStatement.setInt(3, chatid);
					preparedStatement.setInt(4, matchedUserid);
					preparedStatement.executeUpdate();
					
					//finds the interest that the matched users have in common
					stmt = con.createStatement();
					sql = "(SELECT type FROM Likes WHERE userid = ?)"
							+ " INTERSECT"
							+ " (SELECT type FROM Likes WHERE userid = ?)";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, userid);
					preparedStatement.setInt(2, matchedUserid);
					ResultSet rsInterest = preparedStatement.executeQuery();

					String interest = "";

					if (rsInterest.next()) {
						interest = rsInterest.getString("type");
					}

					//gets all subjects generated from the interest
					stmt = con.createStatement();
					sql = "SELECT subject FROM Generates WHERE type = ?";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setString(1, interest);
					ResultSet rsTopics = preparedStatement.executeQuery();
					
					List<String> icebreakerTopics = new ArrayList<>();
					
					while(rsTopics.next()) {
						icebreakerTopics.add(rsTopics.getString("subject"));
					}

					//selects three random subjects from the list
					Collections.shuffle(icebreakerTopics);
					List<String> threeIcebreakerTopics = icebreakerTopics.stream().limit(3).collect(Collectors.toList());

					stmt = con.createStatement();
					sql = "INSERT INTO Conversations (chatid, conversation_number)"
							+ " VALUES (?, 1), (?, 2), (?, 3)";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, chatid);
					preparedStatement.setInt(2, chatid);
					preparedStatement.setInt(3, chatid);
					preparedStatement.executeUpdate();

					stmt = con.createStatement();
					sql = "INSERT INTO Icebreakers (chatid, conversation_number, subject, time_duration)"
							+ " VALUES (?, 1, ?, 60), (?, 2, ?, 60), (?, 3, ?, 60)";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, chatid);
					preparedStatement.setString(2, threeIcebreakerTopics.get(0));
					preparedStatement.setInt(3, chatid);
					preparedStatement.setString(4, threeIcebreakerTopics.get(1));
					preparedStatement.setInt(5, chatid);
					preparedStatement.setString(6, threeIcebreakerTopics.get(2));
					preparedStatement.executeUpdate();
					
					System.out.println("Match was found");
					return chatid;
				}else {
					//gets the old chats before attempting to find a match
					stmt = con.createStatement();
					sql = "SELECT chatid FROM Participates WHERE userid = ?";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, userid);
					ResultSet oldChats = preparedStatement.executeQuery();
					
					List<Integer> oldChatids = new ArrayList<Integer>();
					int oldNumberOfChats = 0;
					
					while(oldChats.next()) {
						oldChatids.add(oldChats.getInt("chatid"));
						oldNumberOfChats++;
					}
					
					//sets is_matchmaking to true
					stmt = con.createStatement();
					sql = "UPDATE Users SET is_matchmaking = true WHERE userid = ?";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, userid);
					preparedStatement.executeUpdate();
					
					//attempts to find a match for 30 seconds
					long start = System.currentTimeMillis();
					List<Integer> newChatids = null;
					int newNumberOfChats = -1;
					
					while(System.currentTimeMillis() - start < 30000) {
						//gets the current chats
						stmt = con.createStatement();
						sql = "SELECT chatid FROM Participates WHERE userid = ?";
						preparedStatement = con.prepareStatement(sql);
						preparedStatement.setInt(1, userid);
						ResultSet newChats = preparedStatement.executeQuery();
						
						newChatids = new ArrayList<Integer>();
						newNumberOfChats = 0;
						
						while(newChats.next()) {
							newChatids.add(newChats.getInt("chatid"));
							newNumberOfChats++;
						}
						
						//if current number of chats is greater than before, then a match was made by another user
						if(newNumberOfChats > oldNumberOfChats) {
							break;
						}
					}
					
					//sets is_matchmaking to false
					stmt = con.createStatement();
					sql = "UPDATE Users SET is_matchmaking = false WHERE userid = ?";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, userid);
					preparedStatement.executeUpdate();
					
					//if a match was made, return the new chatid
					if(newNumberOfChats > oldNumberOfChats) {
						for(Integer i : newChatids) {
							if(!oldChatids.contains(i)) {
								System.out.println("Match was made");
								return i;
							}
						}
					}
					
					System.out.println("No match was made");
					return -1;
				}
			} catch (SQLException e) {
				System.out.println("Error searching for a match");
				return -1;
			}
		}
		
		public static List<Integer> getAllChats(int userid) {
			try {
				//gets the chats
				Statement stmt;
				String sql;
				PreparedStatement preparedStatement;
				
				stmt = con.createStatement();
				sql = "SELECT Chats.chatid FROM Chats, Participates WHERE Chats.chatid = Participates.chatid AND userid = ? AND Participates.is_active = true";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setInt(1, userid);
				ResultSet chats = preparedStatement.executeQuery();
				
				List<Integer> chatids = new ArrayList<Integer>();
				
				while(chats.next()) {
					chatids.add(chats.getInt("chatid"));
				}
				
				System.out.println("Successfully fetched all chats");
				return chatids;
			}catch (SQLException e) {
				System.out.println("Error fetching all chats");
				return null;
			}
		}
		
		public static Chat refreshChat(int userid, int chatid) {
			try {
				Statement stmt;
				String sql;
				PreparedStatement preparedStatement;
				
				stmt = con.createStatement();
				sql = "SELECT User.userid, User.​name​ FROM Users, Participates"
						+ " WHERE Users.userid = Participates.userid"
						+ " AND Participates.userid != ? AND Participates.chatid = ?";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setInt(1, userid);
				preparedStatement.setInt(2, chatid);
				ResultSet rsMatchedUser = preparedStatement.executeQuery();
				
				String matchedName = null;
				
				if(rsMatchedUser.next()) {
					matchedName = rsMatchedUser.getString("User.​name");
				}
				
				stmt = con.createStatement();
				sql = "SELECT msgid, content, name, conversation_number FROM Conversations, Messages, Users"
						+ " WHERE Conversations.chatid = Messages.chatid"
						+ " AND Messages.userid = Users.userid"
						+ " AND Conversations.chatid = ?"
						+ " AND Messages.status = ?"
						+ " ORDER BY msgid";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setInt(1, chatid);
				preparedStatement.setString(2, "sent");
				ResultSet rsOrderedMessages = preparedStatement.executeQuery();
				
				List<Message> orderedMessages = new ArrayList<Message>();
				while(rsOrderedMessages.next()) {
					int msgid = rsOrderedMessages.getInt("msgid");
					String content = rsOrderedMessages.getString("content");
					String name = rsOrderedMessages.getString("name");
					int conversation_number = rsOrderedMessages.getInt("conversation_number");
					
					orderedMessages.add(new Message(msgid, content, name, chatid, conversation_number));
				}
				
				Chat chat = new Chat(chatid, matchedName, orderedMessages);
				
				System.out.println("Sucessfully loaded chat with " + matchedName);
				return chat;
			} catch (SQLException e) {
				System.out.println("Error loading some chat");
				return null;
			}
		}
		
		public static void sendMessage(int chatid, int conversation_number) {
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
	
	public static class Chat {
		int chatid;
		String recipientName;
		List<Message> orderedMessages;
		
		public Chat(int chatid, String recipientName, List<Message> orderedMessages) {
			this.chatid = chatid;
			this.recipientName = recipientName;
			this.orderedMessages = orderedMessages;
		}
	}
	
	public static class Message {
		int msgid;
		String content;
		String sender;
		int chatid;
		int conversation_number;
		
		public Message(int msgid, String content, String sender, int chatid, int conversation_number) {
			this.msgid = msgid;
			this.content = content;
			this.sender = sender;
			this.chatid = chatid;
			this.conversation_number = conversation_number;
		}
	}
}

import java.awt.Component;
import java.awt.Container;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.*;


public class MainFrame extends JFrame {

	public static List<Integer> chatidList = new ArrayList<>();
	public int userid;

	public MainFrame(String title, int userid) {
		super(title);
		this.userid = userid;
		this.setSize(750, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		List<Integer> chatids = App.DatabaseManager.getAllChats(userid);
		DefaultListModel<String> chatList = new DefaultListModel<>();
		if (chatids != null) {
			chatidList = chatids;
			for (Integer id : chatids) {
				App.Chat chat = App.DatabaseManager.refreshChat(userid, id);
				if (chat == null) continue;
				chatList.addElement(chat.recipientName);
			}
		}
		
		JButton matchButton = new JButton("FIND MATCH NOW");
		matchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		matchButton.addActionListener(e -> {
			int chatid = App.DatabaseManager.match(userid);

			if(chatid != -1) {
				new ChatFrame("Icebreaker", userid, chatid);
			}else {
				JOptionPane.showMessageDialog(pane, "Failed to find a match.");
			}
		});
        pane.add(matchButton);
		JLabel label = new JLabel("Recent chats");
		pane.add(label);
		pane.add(createChatList(chatList));
		//TODO: Make sure the connection to db is closed when window is closed
	}

	private JList createChatList(DefaultListModel<String> chatList) {
		JList<String> chatListPanel = new JList<>(chatList);
		chatListPanel.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList) evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            new ChatFrame(chatList.elementAt(index), userid, chatidList.get(index));
		        } else if (evt.getClickCount() == 3) {

		            // Triple-click detected
		            int index = list.locationToIndex(evt.getPoint());
		        }
		    }
		});

		chatListPanel.setBounds(100, 100, 300, 550);
		return chatListPanel ;
	}
}

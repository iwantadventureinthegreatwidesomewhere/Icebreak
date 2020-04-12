import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


public class MainFrame extends JFrame {
	public static List<Integer> chatidList = new ArrayList<Integer>();
	public static int userid;
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
		DefaultListModel<String> chatList = new DefaultListModel<String>();
		if (chatids != null) {
			chatidList = chatids;
			for (Integer id : chatids) {
				App.Chat chat = App.DatabaseManager.refreshChat(userid, Integer.valueOf(id));
				if (chat == null) continue;
				chatList.addElement(chat.recipientName);
			}
		}
		
		JButton matchButton = new JButton("FIND MATCH NOW");
		matchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		matchButton.addActionListener(e -> {
			int chatid = App.DatabaseManager.match(userid);

			if(chatid != -1) {
				//load chat and launch ChatFrame
			}else {
				JOptionPane.showMessageDialog(pane, "Failed to find a match.");
			}
		});
        pane.add(matchButton);
		JLabel label = new JLabel("Recent chats");
		pane.add(label);
		pane.add(createChatList(chatList));
		
	}

	private JList createChatList(DefaultListModel<String> chatList) {
		JList<String> chatListPanel = new JList<String>(chatList);
		chatListPanel.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)((EventObject) evt).getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            ChatFrame chatFrame = new ChatFrame(chatList.elementAt(index), userid, chatidList.get(index));
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

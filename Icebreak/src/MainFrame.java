import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	public MainFrame(String title, int userid) {
		super(title);
		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		List<Integer> chatids = App.DatabaseManager.getAllChats(userid);
		DefaultListModel chatList = new DefaultListModel();
		for (Integer id : chatids) {
			App.Chat chat = App.DatabaseManager.refreshChat(userid, Integer.valueOf(id));
			chatList.addElement(chat.recipientName);
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
		pane.add(createChatList(chatList));
		pane.add(createMatchButton());
		
	}

	private JButton createMatchButton() {
		JButton matchButton = new JButton("Next Match!");
		matchButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	//TODO add functionality of chat with next match
		    }
		});
		return matchButton;
	}

	private JPanel createChatList(DefaultListModel chatList) {
		JPanel cont = new JPanel();
		JLabel label = new JLabel("Recent chats");
		JList<String> chatListPanel = new JList<String>(chatList);
		cont.add(label);
		cont.add(chatListPanel);
		return cont ;
	}

}

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	public MainFrame(String title, String username) {
		super(title);
		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		DefaultListModel chatList = new DefaultListModel();
		//TODO 
		//for (chat c : chats){ chatList.addElement(chat.otherUserName())
		
	}

}

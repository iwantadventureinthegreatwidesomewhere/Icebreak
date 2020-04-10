import java.awt.*;


import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	public MainFrame(String title, String username) {
		super(title);
		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		DefaultListModel chatList = new DefaultListModel();
		
		JButton matchButton = new JButton("FIND MATCH NOW");
		matchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(matchButton);
		//TODO 
		//for (chat c : chats){ chatList.addElement(chat.otherUserName())
	}

}

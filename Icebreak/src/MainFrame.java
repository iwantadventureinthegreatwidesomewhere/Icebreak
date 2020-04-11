import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
	public MainFrame(String title, String email) {
		super(title);
		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		DefaultListModel chatList = new DefaultListModel();
		
		JButton matchButton = new JButton("FIND MATCH NOW");
		matchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		matchButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				App.DatabaseManager.match(email);
			} 
		});
        pane.add(matchButton);
		//TODO 
		//for (chat c : chats){ chatList.addElement(chat.otherUserName())
	}

}

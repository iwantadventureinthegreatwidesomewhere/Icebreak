import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class ChatFrame extends JFrame {
	String[] users = {"peach", "eggplanet", "peach", "eggsplant", "peach"};
	String[] messages = {"hey come", "I am playing cod", "My parents are not home", "OMW", "Bring pizza"};
	
	private static Container currentPane;
	private static String currentMessage;
	private static String sender;
	private static String receiver;
	public ChatFrame(String title, int userid, int chatid) {
		super(title);
		Chat currentChat = refreshChat(userid, chatid);
		Container pane = getContentPane();
		currentPane = pane;
		this.setSize(400, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		pane.add(DisplayMessages(users, messages));
	}
	
	public static JList<String> DisplayMessages(String[] users, String[] messages) {
        DefaultListModel<String> l1 = new DefaultListModel<>();  
        for (int i = 0; i < users.length; i++) {
        	l1.addElement(users[i] + ": " + messages[i]);
        }
        JList<String> msgList = new JList<>(l1);
        msgList.setBounds(200, 200, 200, 200);
        
        return msgList;
	}
	
	private static Container createCurrentMessagePanel() {
		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
		PlaceholderTextField messageField = new PlaceholderTextField("Message", 40);
		messageField.setColumns(40);
		messageField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				currentMessage = messageField.getText();				
			}
		});
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	postMessage(sender, receiver, currentMessage);
		    	currentMessage = "";
		    	messageField.setText("");
		    }
		});
		
		JPanel messagePanel = new JPanel();
		messagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		messagePanel.add(messageField);
		messagePanel.add(sendButton);
		cont.add(messagePanel);
		return cont;
	}
}

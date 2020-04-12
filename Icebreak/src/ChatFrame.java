import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ChatFrame extends JFrame {
	private static Container currentPane;
	private static String currentMessage;
	private static int userid;
	private static int chatid;
	public ChatFrame(String title, int userid, int chatid) {
		super(title);
		this.userid = userid;
		this.chatid = chatid;
		Container pane = getContentPane();
		currentPane = pane;
		this.setSize(400, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		App.Chat currentChat = App.DatabaseManager.refreshChat(userid, chatid);
		List<App.Message> msgs = currentChat.orderedMessages;
		pane.add(displayMessages(msgs));
		pane.add(createCurrentMessagePanel());
	}
	
	public static JList<String> displayMessages(List<App.Message> msgs) {
        DefaultListModel<String> l1 = new DefaultListModel<>();  
        for (App.Message m : msgs) {
        	l1.addElement(m.sender + ": " + m.content);        	
        }
        JList<String> msgList = new JList<>(l1);
        msgList.setBounds(100, 100, 200, 200);

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
        		App.Chat currentChat = App.DatabaseManager.refreshChat(userid, chatid);
        		List<App.Message> msgs = currentChat.orderedMessages;
//                ArrayList<String> l1 = new ArrayList<String>(); 
                String[] messages = new String[msgs.size()];
                int i = 0;
                for (App.Message m : msgs) {
                	messages[i] = m.sender + ": " + m.content;
//                	l1.add(m.sender + ": " + m.content);   
                	i++;
                }
            	msgList.setListData(messages);
            }
        });
        timer.start();
        return msgList;
	}
	
	private static Container createCurrentMessagePanel() {

		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
		PlaceholderTextField messageField = new PlaceholderTextField("Message", 15);
		messageField.setColumns(15);
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
		    	App.DatabaseManager.sendMessage(userid, chatid);
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

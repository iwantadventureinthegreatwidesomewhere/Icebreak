import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginFrame extends JFrame {
	public LoginFrame(String title) {
		super(title);
		
		Container pane = getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font(null, Font.PLAIN, 18));
		loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(loginLabel);
		
		PlaceholderTextField emailField = new PlaceholderTextField("");
		emailField.setColumns(15);
		emailField.setPlaceholder("Username");
		
		JPanel emailPanel = new JPanel();
		emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailPanel.add(emailField);
		pane.add(emailPanel);
		
		PlaceholderTextField passwordField = new PlaceholderTextField("");
		passwordField.setColumns(15);
		passwordField.setPlaceholder("Password");
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordPanel.add(passwordField);
		pane.add(passwordPanel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(loginButton);
        
        JLabel SignupLabel = new JLabel("No account? Sign up!");
        SignupLabel.setFont(new Font(null, Font.PLAIN, 18));
        SignupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(SignupLabel);
		
		JButton signupButton = new JButton("Sign Up");
		signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(signupButton);
        signupButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JFrame frame = new SignupFrame("Icebreak");
				frame.setSize(750, 300);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
		    }
		});
        
    }
}

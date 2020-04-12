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
		
		JTextField emailField = new JTextField("Email");
		emailField.setColumns(15);
		
		JPanel emailPanel = new JPanel();
		emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailPanel.add(emailField);
		pane.add(emailPanel);
		
		JTextField passwordField = new JTextField("Password");
		passwordField.setColumns(15);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordPanel.add(passwordField);
		pane.add(passwordPanel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginButton.addActionListener(e -> {
			int rsUserid = App.DatabaseManager.login(emailField.getText(), passwordField.getText());

			if(rsUserid != -1) {
				JFrame nextFrame = new MainFrame("Icebreak", rsUserid);
				nextFrame.setVisible(true);
				dispose();
			}else {
				JOptionPane.showMessageDialog(pane, "Incorrect password for " + emailField.getText() + ". Please try again.");
			}
		});
        pane.add(loginButton);
        
        JLabel SignupLabel = new JLabel("No account? Sign up!");
        SignupLabel.setFont(new Font(null, Font.PLAIN, 18));
        SignupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(SignupLabel);
		
		JButton signupButton = new JButton("Sign Up");
		signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(signupButton);
        signupButton.addActionListener(e -> {
			JFrame frame = new SignupFrame("Icebreak");
			frame.setSize(750, 300);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
        
    }
}

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.PlaceholderTextField;
import javax.swing.filechooser.FileSystemView;

public class SignupFrame extends JFrame {
	private static Container currentPane;
	private static String gender = "Female";
	private static Date birthDate;
	private static String name;
	private static String email;
	private static String password;
	private static String ppPath;
	private static Boolean isActive = true;
	private static Boolean isMatchMaking = true;
	private static String location;
	private static String error = "Not found";
	private static Date dateCreated;
	private static Time timeCreated;
	private static String preference = "M";
	private static String interest = "football";
	private static String interestType = "sport";
	private static final String[] prefList = { "M", "F", "O", "M/F", "M/O", "F/O", "M/F/O"};
	private static String[] interestTypeList = {"sport", "others"};
	private static String[][] interestList = {{"football", "pingpong", "baseball"},
            {"reading", "dancing", "drawing"}};
	public SignupFrame(String title) {
		super(title);
		Container pane = getContentPane();
		currentPane = pane;
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		

	    pane.add(createGenderPanel());
	    pane.add(createBirthDatePanel());
	    pane.add(createNamePanel());
	    pane.add(createEmailPanel());
	    pane.add(createPasswordPanel());
	    pane.add(createBrowseButton());
	    pane.add(createLocationPanel());
	    pane.add(createPreferencePanel());
	    pane.add(createInterestPanel());
	    
	    pane.add(createDoneButton());
	    pane.add(createCancelButton());		
	}
	
	private static Container createGenderPanel() {
		Container genderPane = new Container();
		genderPane.setLayout(new BoxLayout(genderPane, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Gender: ");
	    String[] choices = { "Female","Male"};
	    final JComboBox<String> genderDDM = new JComboBox<>(choices);
	    genderDDM.addActionListener(e -> gender = genderDDM.getSelectedItem().toString());
	    genderPane.add(lbl);
	    genderPane.add(genderDDM);	
	    return genderPane;
	}
	
	private static Container createPreferencePanel() {
		Container preferencePane = new Container();
		preferencePane.setLayout(new BoxLayout(preferencePane, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Preference: ");
	    final JComboBox<String> preferenceDDM = new JComboBox<>(prefList);
	    preferenceDDM.addActionListener(e -> preference = preferenceDDM.getSelectedItem().toString());
	    preferencePane.add(lbl);
	    preferencePane.add(preferenceDDM);
	    return preferencePane;
	}
	
	private static Container createInterestPanel() {
		Container interestPane = new Container();
		interestPane.setLayout(new BoxLayout(interestPane, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Interest type: ");
	    JLabel lbl2 = new JLabel("Interest: ");
	    final JComboBox<String> typeDDM = new JComboBox<>(interestTypeList);
	    final JComboBox<String> interestDDM = new JComboBox<>(interestList[0]);
	    typeDDM.addActionListener(e -> {
			interestType = typeDDM.getSelectedItem().toString();
			interestDDM.removeAllItems();
			for (int i = 0; i < interestList[typeDDM.getSelectedIndex()].length; i++) {
				interestDDM.addItem(interestList[typeDDM.getSelectedIndex()][i]);
			}
		});
	    interestDDM.addActionListener(e -> {
			if (interestDDM.getItemCount() > 0)
				interest = interestDDM.getSelectedItem().toString();
		});
	    interestPane.add(lbl);
	    interestPane.add(typeDDM);	
	    interestPane.add(lbl2);
	    interestPane.add(interestDDM);	
	    return interestPane;
	}
	
	private static JPanel createBirthDatePanel() {
		JPanel birthContainer = new JPanel();
		birthContainer.setLayout(new BoxLayout(birthContainer, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Date of birth (yyyy-MM-dd): ");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		JFormattedTextField dateTextField = new JFormattedTextField(format);
		dateTextField.setColumns(15);
		dateTextField.addKeyListener(new KeyListener()
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
				try{
					birthDate = java.sql.Date.valueOf(dateTextField.getText());
				}
				catch (Exception ignored) {
				}
				
			}
		});
		
		JPanel datePanel = new JPanel();
		datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		birthContainer.add(lbl);
		birthContainer.add(dateTextField);
		return birthContainer;
	}
	
	private static Container createNamePanel() {
		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Name: ");
		PlaceholderTextField nameField = new PlaceholderTextField("Name", 15);
		nameField.setColumns(15);
		nameField.addKeyListener(new KeyListener()
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
				name = nameField.getText();				
			}
		});
		
		JPanel namePanel = new JPanel();
		namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		namePanel.add(nameField);
		cont.add(lbl);
		cont.add(namePanel);
		return cont;
	}
	
	private static Container createEmailPanel() {
		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Email: ");
		PlaceholderTextField emailField = new PlaceholderTextField("");
		emailField.setColumns(15);
		emailField.setPlaceholder("Email");
		emailField.addKeyListener(new KeyListener()
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
				email = emailField.getText();				
			}
		});
		
		JPanel emailPanel = new JPanel();
		emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailPanel.add(emailField);
		cont.add(lbl);
		cont.add(emailPanel);
		return cont;
	}
	
	private static Container createPasswordPanel() {
		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Password: ");
		PlaceholderTextField passwordField = new PlaceholderTextField("");
		passwordField.setColumns(15);
		passwordField.setPlaceholder("Password");
		passwordField.addKeyListener(new KeyListener()
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
				password = passwordField.getText();				
			}
		});
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordPanel.add(passwordField);
		cont.add(lbl);
		cont.add(passwordPanel);
		return cont;
	}
	
	private static Container createBrowseButton() {
		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Profile Picture:");
	    JLabel lbl2 = new JLabel("Not found");
		JButton browseButton = new JButton("Browse...");
		browseButton.addActionListener(e -> lbl2.setText(browseForPP()));
		cont.add(lbl);
		cont.add(browseButton);
		cont.add(lbl2);
		return cont;
	}
	
	private static String browseForPP() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(currentPane);
		String path = "";

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			ppPath = selectedFile.getAbsolutePath();
		}
		return ppPath;
	}
	
	private static Container createLocationPanel() {
		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
	    JLabel lbl = new JLabel("Location: ");
		PlaceholderTextField locationField = new PlaceholderTextField("");
		locationField.setColumns(15);
		locationField.setPlaceholder("Location");
		locationField.addKeyListener(new KeyListener()
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
				location = locationField.getText();				
			}
		});
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordPanel.add(locationField);
		cont.add(lbl);
		cont.add(passwordPanel);
		return cont;
	}
	
	private JButton createDoneButton() {
		JButton doneButton = new JButton("Done");
		doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		doneButton.addActionListener(e -> {
			if (createUserSQL()){
				System.out.println("Successfully signed up!");
				dispose();
			} else {
				JOptionPane.showMessageDialog(currentPane, "Some input is wrong. Reason: " + error);} //TODO
		});
		return doneButton;
	}
	
	private static JButton createCancelButton() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		return cancelButton;
	}
	
	private static boolean createUserSQL(){
		System.out.println("Signed up user with\n" +
				"\tbirthDate: " + birthDate+
				"\tname: " + name+
				"\temail: " + email+
				"\tpassword: " + password+
				"\tppPath: " + ppPath+
				"\tisActive = true: " + isActive+
				"\tisMatchMaking = true: " + isMatchMaking+
				"\tlocation: " + location+
				"\terror = \"Not found\": " + error+
				"\tdateCreated: " + dateCreated+
				"\ttimeCreated: " + timeCreated+
				"\tpreference: " + preference+
				"\tinterest: " + interest+
				"\tinterestType: " + interestType);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		dateCreated = java.sql.Date.valueOf(format.format(now));
		format = DateTimeFormatter.ofPattern("HH:mm:ss");
		timeCreated = java.sql.Time.valueOf(format.format(now));

		char sqlGender;
		if (gender.equals("Female")) sqlGender = 'F';
		else if (gender.equals("Male")) sqlGender = 'M';
		else sqlGender = 'O';

		return App.DatabaseManager.signup(sqlGender, birthDate, name, email, password,
				dateCreated, preference, interest, interestType, 0, location);
//		error = "duplicated user"; //TODO do like this
	}
	
}

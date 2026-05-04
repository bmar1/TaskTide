/*	Name: Bilal
 * 	Date: January 9th, 2024
 * 	Description: Controls all actions when the user tries to log in, once they try to press the login in button it will validate all information they enter (or do not)
 * and respond correctly by letting them into the program or not. 
 * Also controls when the user switches to sign up mode and lets them sign up accordingly to save their information and proceed to login.
 * 
 */

package controller;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import view.LoginFrame;

public class LoginController implements ActionListener {

	// fields needed
	private LoginFrame loginFrame = new LoginFrame();

	// to switch between modes
	private boolean signUp = false;
	private boolean logIn = true;
	private boolean marked = false;

	public LoginController() {

		// add action listeners
		addActonListeners();

	}

	private void addActonListeners() {
		loginFrame.getSignInButton().addActionListener(this);
		loginFrame.getSignUp().addActionListener(this);
		loginFrame.getRevealPassword().addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// if they try to sign in
		if (e.getSource() == loginFrame.getSignInButton()) {

			// check if either field is empty
			if (loginFrame.getUsernameField().getText().equals("")
					|| new String(loginFrame.getPasswordField().getPassword()).equals("")) {

				JOptionPane.showMessageDialog(loginFrame, "You haven't entered all the information before logging in.",
						"Error", JOptionPane.OK_CANCEL_OPTION);
			} else {

				// if they are in login mode
				if (logIn == true) {

					// if we find a match with their information
					if (checkCredentials(getUsername(), getPassword())) {

						// login succesful, let them them in
						JOptionPane.showMessageDialog(loginFrame, "Login Successful! Welcome, " + getUsername() + ".",
								"Success", JOptionPane.INFORMATION_MESSAGE);
						loginFrame.setVisible(false);

						// create a new frame for the home screen
						new DashController(this);

						// otherwise it was incorrect
					} else {
						JOptionPane.showMessageDialog(loginFrame,
								"Username or password incorrect. Try again or make a new account.", "Error",
								JOptionPane.OK_CANCEL_OPTION);
					}
				}

				// otherwise if we're in sign up mode
				else if (signUp) {

					// Get the entered username and password
					String username = loginFrame.getUsernameField().getText();
					String password = new String(loginFrame.getPasswordField().getPassword());

					// make sure it is at least 8 characters long
					if (password.length() > 8) {

						// Save the information to a file
						saveUserInfoToFile(username, password);
						JOptionPane.showMessageDialog(loginFrame,
								"Sign Up Successful! Proceed to login, " + username + ".", "Success",
								JOptionPane.INFORMATION_MESSAGE);
						// switch back to login mode
						loginMode(logIn);
					}

					// otherwise give an error message
					else
						JOptionPane.showMessageDialog(loginFrame, "Password required to be 8 characters in length.",
								"Error", JOptionPane.OK_CANCEL_OPTION);

				}
			}
		}

		else if (e.getSource() == loginFrame.getSignUp()) {

			// if login mode is already active
			if (logIn == true)
				// switch to sign up
				signUpMode(signUp);

			else
				// otherwise that means we are in sign up so switch to log in
				loginMode(logIn);
		}

		else if (e.getSource() == loginFrame.getRevealPassword()) {
			if (!marked) {
				// making the password visible
				marked = true;
				loginFrame.getRevealPassword().setIcon(new ImageIcon("images/hide.png"));
				loginFrame.getPasswordField().setEchoChar((char) 0);
			} else {
				marked = false;
				loginFrame.getRevealPassword().setIcon(new ImageIcon("images/reveal.png"));
				loginFrame.getPasswordField().setEchoChar('*');
			}

		}

	}

	private void saveUserInfoToFile(String username, String password) {

		// code learned from
		// https://www.digitalocean.com/community/tutorials/java-filewriter-example

		// 1. Create a writer to write the information
		try (FileWriter writer = new FileWriter("files/userDatabase.txt", true)) {

			// 2. Write the information passed in into the file with a comma to seperate the
			// username and password
			writer.write("Username: " + username + ", Password: " + password + "\n");

			// otherwise there was an error
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Changes the mode of the screen to log in and switches the text
	private void loginMode(boolean logIn) {
		// Change the text if we are in log in mode
		this.signUp = false;
		this.logIn = true;

		loginFrame.getSignInButton().setText("Log In");
		loginFrame.getTitleFrame().setText("Log In");
		loginFrame.getSignUp().setText("Don't have an account? Sign up");

		loginFrame.repaint();
		loginFrame.revalidate();

	}

	// Changes the mode of the screen to sign up and switches the text
	private void signUpMode(boolean signUp) {
		// changes the text accordingly in sign up mode
		this.signUp = true;
		this.logIn = false;
		loginFrame.getSignInButton().setText("Sign Up");
		loginFrame.getTitleFrame().setText("Sign Up");
		loginFrame.getSignUp().setText("Return to login screen");
		loginFrame.getWelcome().setText("Welcome!");

		loginFrame.repaint();
		loginFrame.revalidate();
	}

	// Checks the users credentials when they try to log in
	private boolean checkCredentials(String user, String pass) {

		// 1. Create a scanner to read the file
		try (Scanner scanner = new Scanner(new File("files/userDatabase.txt"))) {

			// 1.1 Keep going while we have a line to read
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				// If the line is empty, skip to the next line
				if (line.isEmpty()) {
					continue;
				}

				// 2. Split the line into two parts to read the information
				String[] parts = line.split(", ");

				if (parts.length >= 2) {
					// 2.1 Get the username and password as a substring
					String username = parts[0].substring("Username: ".length());
					String password = parts[1].substring("Password: ".length());

					// 2.2 Check if the username nad password match the user input
					if (user.equals(username) && pass.equals(password)) {
						// 2.2.1 If they do, return true
						return true;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// 3. Otherwise return false as no match was found
		return false;
	}

	// Getters and setters needed by other controllers
	public LoginFrame getLoginFrame() {
		return loginFrame;
	}

	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	public String getUsername() {
		return loginFrame.getUsernameField().getText();
	}

	public String getPassword() {
		return new String(loginFrame.getPasswordField().getPassword());
	}

}
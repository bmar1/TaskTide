// Description: acts as the data source for the user and their tasks

package model;

import java.util.ArrayList;


public class User {

	// fields
	String userName;
	String password;
	ArrayList<Task> userTasks = new ArrayList<Task>();
	
	public User(String userName, String password, ArrayList<Task> userTasks) {
		super();
		this.userName = userName;
		this.password = password;
		this.userTasks = userTasks;
	}

	// getters and setters
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Task> getUserTasks() {
		return userTasks;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserTasks(ArrayList<Task> userTasks) {
		this.userTasks = userTasks;
	}

	@Override
	public String toString() {
		return "CurrentUser [userName=" + userName + ", password=" + password + ", userTasks=" + userTasks + "]";
	}
	
	
}
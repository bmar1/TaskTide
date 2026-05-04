/* Name(s): 
	Bilal Umar
	
	Date:
	Friday, January 19th, 2024
	
	Course Code:
	ICS4U1-02
	
	Title:
	FSP - TaskTide
	
	Description:
	TaskTide is a time tracking for tasks  that helps students anywhere manage their tasks they need to do, it continually reminds users often depending 
	on the priority of their tasks and also boasts a login and sign up feature. Allowing the users information to be stored. It also allows the user to delete and add tasks
	as well as updating the progress for each task when the user claims they submitted a subtask.
	
	Features:
	Logging In/Sign Up
	Task management/deleting/adding
	Task reminders and time (e.g when a reminder triggers and how often)
	Clickable tasks 
	
	Major Skills:
	ArrayLists, file writing and saving, GUI, dynamic programming, methods, OOP, 
	
	Areas of Concern:
	Recommended to be ran on a wide screen monitor (24 inch 1920x1080)
	Files need to be downloaded to log in properly
	As well as to save the information
 */



package application;

import controller.LoginController;
import view.Dashboard;


@SuppressWarnings("unused")
public class TaskTideApplication {
	
	public static void main(String[] args) {

		// runs start of program
		new LoginController();
		//new Dashboard();
	}
}

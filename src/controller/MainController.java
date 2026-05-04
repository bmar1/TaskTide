package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.Task;
import model.User;
import view.HomeScreen;

public class MainController implements ActionListener {

	private static final int MAX_TASKS = 12;

	private HomeScreen homeScreen = new HomeScreen();
	private UpdateController updateController;
	private LoginController loginController;

	private ArrayList<Timer> taskTimers = new ArrayList<>();
	private ArrayList<Task> userTasks = new ArrayList<>();

	private JButton addTask = new JButton("+");
	private JButton deleteTask = new JButton();
	private JButton deleteTaskWhite = new JButton();
	private JLabel timeLabel = new JLabel();

	private Clip popup;
	private Runnable tasksChangedListener = () -> {
	};

	public MainController(LoginController loginController) {
		this.loginController = loginController;
		addActionListeners();

		try {
			simpleAudioPlayer();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		updateController = new UpdateController(this, userTasks);
		checkUser();

		for (Task task : userTasks) {
			startTimerForTask(task);
		}
	}

	private void simpleAudioPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File audioFile = new File("files/popup.wav");
		if (!audioFile.exists()) {
			return;
		}

		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
		popup = AudioSystem.getClip();
		popup.open(audioInputStream);
	}

	private void checkUser() {
		updateController.loadTasks();
	}

	public User startUser() {
		return new User(loginController.getUsername(), loginController.getPassword(), userTasks);
	}

	public Timer createTimer(Task task) {
		for (Timer existingTimer : taskTimers) {
			if (existingTimer.getActionCommand().equals(task.getTaskName())) {
				return existingTimer;
			}
		}

		int delay = calculatePriorityDelay(task.getPriority());
		Timer taskTimer = new Timer(delay, e -> {
			try {
				handleTaskTimer(task);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
		});
		taskTimer.setActionCommand(task.getTaskName());
		taskTimers.add(taskTimer);
		return taskTimer;
	}

	public void startTimerForTask(Task task) {
		Timer taskTimer = createTimer(task);
		if (!taskTimer.isRunning()) {
			taskTimer.start();
		}
	}

	private void handleTaskTimer(Task task)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (userTasks.contains(task)) {
			popupReminder(task);
			task.getProgressBar().setValue(Integer.parseInt(task.getProgress()));
		}
	}

	private void addActionListeners() {
		homeScreen.getQuit().addActionListener(this);
		homeScreen.getAddTask().addActionListener(this);
		homeScreen.getDeleteTask().addActionListener(this);
		homeScreen.getToggleAppearance().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == homeScreen.getDeleteTask() || event.getSource() == getDeleteTask()
				|| event.getSource() == getDeleteTaskWhite()) {
			showTaskDelete(homeScreen);
		} else if (event.getSource() == homeScreen.getQuit()) {
			saveAndExit();
		} else if (event.getSource() == homeScreen.getAddTask() || event.getSource() == getAddTask()) {
			showAndAddTask(homeScreen);
		} else if (event.getSource() == homeScreen.getToggleAppearance()) {
			homeScreen.setDarkMode(!homeScreen.isDarkMode());
		}
	}

	public boolean showAndAddTask(Component parent) {
		Object[] resultArray = homeScreen.showTaskInputDialog();
		int result = (int) resultArray[0];
		if (result != JOptionPane.OK_OPTION) {
			return false;
		}

		if (userTasks.size() >= MAX_TASKS) {
			JOptionPane.showMessageDialog(parent,
					"You have reached the maximum task limit. Please delete a task before creating a new one.",
					"Task limit reached", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String taskName = (String) resultArray[1];
		String deadline = (String) resultArray[2];
		int priority = Integer.parseInt((String) resultArray[3]);
		String subtask1 = (String) resultArray[4];
		String subtask2 = (String) resultArray[5];
		String subtask3 = (String) resultArray[6];

		Task task = new Task(taskName, deadline, priority, subtask1, subtask2, subtask3, "0");
		userTasks.add(task);
		updateController.createTaskPanel(task, 0, 0, 0, 0, false, true);
		startTimerForTask(task);
		notifyTasksChanged();
		return true;
	}

	public boolean showTaskDelete(Component parent) {
		if (userTasks.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "There are no tasks to delete.", "No tasks",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		String result = JOptionPane.showInputDialog(parent, "Enter which task to delete. (1-" + userTasks.size() + ")");

		if (result == null) {
			return false;
		}

		try {
			int taskIndex = Integer.parseInt(result.trim()) - 1;

			if (taskIndex >= 0 && taskIndex < userTasks.size()) {
				updateController.deleteTask(userTasks.get(taskIndex));
				notifyTasksChanged();
				return true;
			}

			JOptionPane.showMessageDialog(parent, "Invalid task. Please enter a number between 1 and " + userTasks.size());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(parent, "Enter a number please");
		}

		return false;
	}

	public void saveAndExit() {
		saveToFile(startUser().getUserName(), startUser().getPassword(), startUser().getUserTasks());
		System.exit(0);
	}

	public void saveToFile(String username, String password, ArrayList<Task> userTasks) {
		try (FileWriter writer = new FileWriter("files/userTasks.txt", true);
				Scanner scanner = new Scanner(new File("files/userTasks.txt"))) {
			boolean userFound = false;
			String lastUserLine = "";

			while (scanner.hasNextLine()) {
				lastUserLine = scanner.nextLine();
			}

			if (!lastUserLine.isEmpty() && lastUserLine.contains(username) && lastUserLine.contains(password)) {
				userFound = true;

				for (Task task : userTasks) {
					boolean taskExistsInFile = lastUserLine.contains(task.getTaskName());

					if (!taskExistsInFile) {
						writeTask(writer, task);
					}
				}
				writer.write("\n");
			}

			if (!userFound) {
				writer.write(username + "," + password + "\n");
				for (Task task : userTasks) {
					writeTask(writer, task);
				}
				writer.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeTask(FileWriter writer, Task task) throws IOException {
		writer.write(task.getTaskName() + "," + task.getDeadline() + "," + task.getPriority() + ","
				+ task.getSubtask1() + "," + task.getSubtask2() + "," + task.getSubtask3() + ","
				+ task.getProgress() + "\n");
	}

	private void popupReminder(Task task) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (popup != null) {
			popup.stop();
			popup.setMicrosecondPosition(0);
			popup.start();
		}

		String progressText = JOptionPane.showInputDialog(homeScreen,
				"Reminder for " + task.getTaskName() + "\nHave you completed one of its subtasks?");

		if (popup != null) {
			popup.stop();
			popup.setMicrosecondPosition(0);
		}

		if (progressText == null) {
			return;
		}

		String completed = progressText.trim();
		if (completed.isEmpty()) {
			JOptionPane.showMessageDialog(homeScreen, "No progress recorded. Try again at the next reminder.");
			return;
		}

		if (task.getSubtask1().toLowerCase().contains(completed.toLowerCase())
				|| task.getSubtask2().toLowerCase().contains(completed.toLowerCase())
				|| task.getSubtask3().toLowerCase().contains(completed.toLowerCase())) {
			updateController.updateProgress(task);
		} else {
			JOptionPane.showMessageDialog(homeScreen,
					"That does not match one of the subtasks. Open the task details if you need a reminder.");
		}
	}

	public void finalPanel(Task task) {
		JOptionPane.showMessageDialog(homeScreen,
				"Good work completing " + task.getTaskName() + "! The task will be removed.");
		updateController.deleteTask(task);
		notifyTasksChanged();
	}

	public void setTasksChangedListener(Runnable tasksChangedListener) {
		this.tasksChangedListener = tasksChangedListener == null ? () -> {
		} : tasksChangedListener;
	}

	private void notifyTasksChanged() {
		tasksChangedListener.run();
	}

	private int calculatePriorityDelay(int priority) {
		switch (priority) {
		case 1:
			return 30 * 60 * 1000;
		case 2:
			return 45 * 60 * 1000;
		case 3:
			return 60 * 60 * 1000;
		default:
			return 60 * 60 * 1000;
		}
	}

	public int countCompletedTasks() {
		int completed = 0;
		for (Task task : userTasks) {
			if ("100".equals(task.getProgress())) {
				completed++;
			}
		}
		return completed;
	}

	public ArrayList<Task> getUserTasks() {
		return userTasks;
	}

	public void setUserTasks(ArrayList<Task> userTasks) {
		this.userTasks = userTasks;
	}

	public JButton getAddTask() {
		return addTask;
	}

	public void setAddTask(JButton addTask) {
		this.addTask = addTask;
	}

	public JButton getDeleteTask() {
		return deleteTask;
	}

	public void setDeleteTask(JButton deleteTask) {
		this.deleteTask = deleteTask;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public JButton getDeleteTaskWhite() {
		return deleteTaskWhite;
	}

	public void setDeleteTaskWhite(JButton deleteTaskWhite) {
		this.deleteTaskWhite = deleteTaskWhite;
	}

	public HomeScreen getHomeScreen() {
		return homeScreen;
	}

	public ArrayList<Timer> getTaskTimers() {
		return taskTimers;
	}

	public void setTaskTimers(ArrayList<Timer> taskTimers) {
		this.taskTimers = taskTimers;
	}
}

package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import model.Task;
import model.TaskDateUtils;
import model.TaskFrame;
import view.HomeScreen;
import view.TaskTideTheme;

public class UpdateController implements MouseListener {

	private HomeScreen homeScreen;
	private MainController mainController;

	public UpdateController(MainController mainController, ArrayList<Task> userTasks) {
		this.mainController = mainController;
		homeScreen = mainController.getHomeScreen();
	}

	public void loadTasks() {
		try (Scanner scanner = new Scanner(new File("files/userTasks.txt"))) {
			boolean userFound = false;

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (line.contains(mainController.startUser().getUserName())
						&& line.contains(mainController.startUser().getPassword())) {
					userFound = true;
				} else if (userFound && line.isEmpty()) {
					break;
				} else if (userFound) {
					String[] taskData = line.split(",");
					if (taskData.length < 7) {
						continue;
					}

					Task task = new Task(taskData[0], taskData[1], Integer.parseInt(taskData[2]), taskData[3],
							taskData[4], taskData[5], taskData[6]);
					mainController.getUserTasks().add(task);
					createTaskPanel(task, 0, 0, 0, 0, true, true);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void deleteTask(Task deleteTask) {
		mainController.getUserTasks().remove(deleteTask);

		mainController.getTaskTimers().removeIf(timer -> {
			boolean matches = timer.getActionCommand().equals(deleteTask.getTaskName());
			if (matches) {
				timer.stop();
			}
			return matches;
		});

		reOrganizeTasks();
	}

	public void reOrganizeTasks() {
		homeScreen.clearTaskCards();

		for (Task task : mainController.getUserTasks()) {
			createTaskPanel(task, 0, 0, 0, 0, false, true);
		}

		homeScreen.refreshTaskContainer();
	}

	public void updateProgress(Task task) {
		int currentProgress = Integer.parseInt(task.getProgress());
		int newProgress = Math.min(currentProgress + 33, 100);

		if (newProgress >= 99) {
			task.setProgress("100");
			task.getProgressBar().setValue(100);
			mainController.finalPanel(task);
		} else {
			task.setProgress(String.valueOf(newProgress));
			task.getProgressBar().setValue(newProgress);
		}
	}

	public void createTaskPanel(Task task, int x, int y, int width, int height, boolean isTrue, boolean home) {
		JPanel taskPanel = new TaskTideTheme.RoundedCard(24);
		taskPanel.setLayout(new BorderLayout(12, 12));
		taskPanel.addMouseListener(this);
		taskPanel.putClientProperty("Task", task);
		taskPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		taskPanel.setBackground(priorityColor(task.getPriority()));

		JLabel name = new JLabel(task.getTaskName());
		name.setFont(TaskTideTheme.SECTION_FONT);
		name.setForeground(Color.WHITE);

		JLabel deadline = new JLabel(TaskDateUtils.displayDeadline(task.getDeadline()));
		deadline.setFont(TaskTideTheme.BODY_FONT);
		deadline.setForeground(TaskTideTheme.SKY);

		JLabel relative = new JLabel(TaskDateUtils.relativeDeadline(task.getDeadline()));
		relative.setFont(TaskTideTheme.SMALL_FONT);
		relative.setForeground(Color.WHITE);

		JPanel copy = new JPanel(new GridLayout(3, 1, 0, 4));
		copy.setOpaque(false);
		copy.add(name);
		copy.add(deadline);
		copy.add(relative);
		taskPanel.add(copy, BorderLayout.NORTH);

		JProgressBar progress = task.getProgressBar();
		progress.setMinimum(0);
		progress.setMaximum(100);
		progress.setStringPainted(true);
		progress.setFont(TaskTideTheme.BUTTON_FONT);
		if (isTrue) {
			progress.setValue(Integer.parseInt(task.getProgress()));
		}
		taskPanel.add(progress, BorderLayout.CENTER);

		JLabel footer = new JLabel("Priority " + task.getPriority(), SwingConstants.RIGHT);
		footer.setFont(new Font("Sans Serif", Font.BOLD, 13));
		footer.setForeground(Color.WHITE);
		taskPanel.add(footer, BorderLayout.SOUTH);

		if (home) {
			homeScreen.getTaskContainer().add(taskPanel);
			homeScreen.refreshTaskContainer();
		}
	}

	private Color priorityColor(int priority) {
		switch (priority) {
		case 1:
			return TaskTideTheme.BLUE;
		case 2:
			return TaskTideTheme.WAVE;
		case 3:
			return TaskTideTheme.SUCCESS;
		default:
			return TaskTideTheme.MUTED;
		}
	}

	private void createTaskInformationPanel(Task task) {
		new TaskFrame(task);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JPanel taskPanel = (JPanel) e.getSource();
		Task task = (Task) taskPanel.getClientProperty("Task");
		createTaskInformationPanel(task);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}

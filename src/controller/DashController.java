package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import model.Task;
import model.TaskDateUtils;
import view.Dashboard;

public class DashController implements ActionListener, MouseListener {

	private Dashboard dashboard = new Dashboard();
	private MainController mainController;

	public DashController(LoginController loginController) {
		mainController = new MainController(loginController);
		mainController.setTasksChangedListener(this::refreshDashboardStats);
		mainController.getHomeScreen().setVisible(false);

		addActionlisteners();
		refreshDashboardStats();
	}

	private void addActionlisteners() {
		dashboard.getAddTask().addActionListener(this);
		dashboard.getDeleteTask().addActionListener(this);
		dashboard.getQuit().addActionListener(this);
		dashboard.getHelp().addActionListener(this);

		dashboard.getCircle().addMouseListener(this);
		dashboard.getTasksToday().addMouseListener(this);
		dashboard.getTasks().addMouseListener(this);
		dashboard.getCircle2().addMouseListener(this);
		dashboard.getTotalTasks().addMouseListener(this);
		dashboard.getTasksTotal().addMouseListener(this);
	}

	private int determineTasksDueToday() {
		int counter = 0;

		for (Task task : mainController.getUserTasks()) {
			if (TaskDateUtils.isDueToday(task.getDeadline())) {
				counter++;
			}
		}

		return counter;
	}

	private void refreshDashboardStats() {
		dashboard.refreshStats(determineTasksDueToday(), mainController.getUserTasks().size(),
				mainController.countCompletedTasks(), 12);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dashboard.getAddTask()) {
			if (mainController.showAndAddTask(dashboard)) {
				refreshDashboardStats();
			}
		} else if (e.getSource() == dashboard.getDeleteTask()) {
			if (mainController.showTaskDelete(dashboard)) {
				refreshDashboardStats();
			}
		} else if (e.getSource() == dashboard.getQuit()) {
			mainController.saveAndExit();
		} else if (e.getSource() == dashboard.getHelp()) {
			JOptionPane.showMessageDialog(dashboard,
					"Use + to add tasks and the trash icon to delete them.\n"
							+ "Click Total tasks to open the full task board.\n"
							+ "Dates work best as YYYY-MM-DD, for example 2026-05-04.");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == dashboard.getCircle() || e.getSource() == dashboard.getTasksToday()
				|| e.getSource() == dashboard.getTasks()) {
			mainController.getHomeScreen().setVisible(true);
			JOptionPane.showMessageDialog(dashboard,
					"You have " + determineTasksDueToday() + " task(s) due today. They are shown on the task board.");
		} else if (e.getSource() == dashboard.getCircle2() || e.getSource() == dashboard.getTotalTasks()
				|| e.getSource() == dashboard.getTasksTotal()) {
			mainController.getHomeScreen().setVisible(true);
		}

		refreshDashboardStats();
	}

	@Override
	public void mousePressed(MouseEvent e) {
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

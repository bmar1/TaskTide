package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import model.TaskDateUtils;

@SuppressWarnings("serial")
public class HomeScreen extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("User Controls");
	private JMenuItem quit = new JMenuItem("Save and Quit");
	private JMenuItem toggleAppearance = new JMenuItem("Switch Theme");

	private JButton addTask = new JButton("+ Add Task");
	private JButton deleteTask = new JButton(TaskTideTheme.scaledIcon("delete.png", 28, 28));
	private JLabel timeLabel = new JLabel();
	private JLabel emptyState = new JLabel("No tasks yet. Add your first task to start the tide.");
	private JPanel root = new JPanel(new BorderLayout(20, 20));
	private JPanel taskContainer = new JPanel(new GridLayout(0, 3, 18, 18));
	private boolean darkMode = false;

	public HomeScreen() {
		setTitle("TaskTide - Task Board");
		TaskTideTheme.setFrameIcon(this);
		setForeground(TaskTideTheme.TEXT);
		setSize(1280, 760);
		setMinimumSize(new Dimension(1100, 700));
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setupElements();
		updateTime();

		Timer timer = new Timer(1000, e -> updateTime());
		timer.start();
		revalidate();
		repaint();
		setVisible(true);
	}

	private void updateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d  |  hh:mm a");
		timeLabel.setText(dateFormat.format(new Date()));
	}

	private void setupElements() {
		root.setBackground(TaskTideTheme.BACKGROUND);
		root.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
		setContentPane(root);

		menuBar.setBackground(TaskTideTheme.BLUE);
		menu.setForeground(Color.white);
		menuBar.add(menu);
		menu.add(toggleAppearance);
		menu.add(quit);
		setJMenuBar(menuBar);

		JPanel header = new JPanel(new BorderLayout(18, 18));
		header.setOpaque(false);
		root.add(header, BorderLayout.NORTH);

		JPanel titlePanel = new JPanel(new GridBagLayout());
		titlePanel.setOpaque(false);
		header.add(titlePanel, BorderLayout.WEST);

		JLabel logo = new JLabel(TaskTideTheme.scaledIcon("logologo.png", 44, 44));
		GridBagConstraints logoConstraints = new GridBagConstraints();
		logoConstraints.gridx = 0;
		logoConstraints.gridy = 0;
		logoConstraints.gridheight = 2;
		logoConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
		titlePanel.add(logo, logoConstraints);

		JLabel title = new JLabel("Task Board");
		title.setFont(TaskTideTheme.TITLE_FONT);
		title.setForeground(TaskTideTheme.TEXT);
		GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridx = 1;
		titleConstraints.gridy = 0;
		titleConstraints.anchor = GridBagConstraints.WEST;
		titlePanel.add(title, titleConstraints);

		JLabel subtitle = new JLabel("Track deadlines, subtasks, and reminder progress.");
		subtitle.setFont(TaskTideTheme.BODY_FONT);
		subtitle.setForeground(TaskTideTheme.MUTED);
		titleConstraints.gridy = 1;
		titlePanel.add(subtitle, titleConstraints);

		JPanel actions = new JPanel(new GridBagLayout());
		actions.setOpaque(false);
		header.add(actions, BorderLayout.EAST);

		timeLabel.setFont(TaskTideTheme.BUTTON_FONT);
		timeLabel.setForeground(TaskTideTheme.BLUE);
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints actionConstraints = new GridBagConstraints();
		actionConstraints.gridx = 0;
		actionConstraints.gridy = 0;
		actionConstraints.insets = new java.awt.Insets(0, 0, 0, 14);
		actions.add(timeLabel, actionConstraints);

		TaskTideTheme.stylePrimaryButton(addTask);
		actionConstraints.gridx = 1;
		actions.add(addTask, actionConstraints);

		TaskTideTheme.styleIconButton(deleteTask);
		deleteTask.setToolTipText("Delete a task");
		actionConstraints.gridx = 2;
		actionConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
		actions.add(deleteTask, actionConstraints);

		taskContainer.setOpaque(false);
		emptyState.setFont(TaskTideTheme.BODY_FONT);
		emptyState.setForeground(TaskTideTheme.MUTED);
		emptyState.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel board = new TaskTideTheme.RoundedCard(30);
		board.setLayout(new BorderLayout());
		board.setBackground(Color.WHITE);
		board.add(emptyState, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(taskContainer);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		board.add(scrollPane, BorderLayout.CENTER);
		root.add(board, BorderLayout.CENTER);
	}

	public Object[] showTaskInputDialog() {
		while (true) {
			JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
			JTextField taskNameField = new JTextField();
			JTextField deadlineField = new JTextField();
			JTextField priorityField = new JTextField();
			JTextField subtask1Field = new JTextField();
			JTextField subtask2Field = new JTextField();
			JTextField subtask3Field = new JTextField();

			TaskTideTheme.styleTextField(taskNameField);
			TaskTideTheme.styleTextField(deadlineField);
			TaskTideTheme.styleTextField(priorityField);
			TaskTideTheme.styleTextField(subtask1Field);
			TaskTideTheme.styleTextField(subtask2Field);
			TaskTideTheme.styleTextField(subtask3Field);

			panel.add(new JLabel("Task Name:"));
			panel.add(taskNameField);
			panel.add(new JLabel("Deadline (YYYY-MM-DD):"));
			panel.add(deadlineField);
			panel.add(new JLabel("Priority (1 high, 2 medium, 3 low):"));
			panel.add(priorityField);
			panel.add(new JLabel("Subtask 1:"));
			panel.add(subtask1Field);
			panel.add(new JLabel("Subtask 2:"));
			panel.add(subtask2Field);
			panel.add(new JLabel("Subtask 3:"));
			panel.add(subtask3Field);

			int result = JOptionPane.showConfirmDialog(this, panel, "Enter Task Details", JOptionPane.OK_CANCEL_OPTION);
			if (result != JOptionPane.OK_OPTION) {
				return new Object[] { result, "", "", "", "", "", "" };
			}

			String taskName = taskNameField.getText().trim();
			String deadline = deadlineField.getText().trim();
			String priority = priorityField.getText().trim();
			String subtask1 = subtask1Field.getText().trim();
			String subtask2 = subtask2Field.getText().trim();
			String subtask3 = subtask3Field.getText().trim();

			if (taskName.isEmpty() || deadline.isEmpty() || priority.isEmpty() || subtask1.isEmpty()
					|| subtask2.isEmpty() || subtask3.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill in every field before creating the task.", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			if (!("1".equals(priority) || "2".equals(priority) || "3".equals(priority))) {
				JOptionPane.showMessageDialog(this, "Priority must be 1, 2, or 3.", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			if (!TaskDateUtils.isValidDeadline(deadline)) {
				JOptionPane.showMessageDialog(this, "Use a date like 2026-05-04 or May 4, 2026.", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			return new Object[] { result, taskName, TaskDateUtils.normalizeDeadline(deadline), priority, subtask1,
					subtask2, subtask3 };
		}
	}

	public void refreshTaskContainer() {
		emptyState.setVisible(taskContainer.getComponentCount() == 0);
		taskContainer.revalidate();
		taskContainer.repaint();
	}

	public void clearTaskCards() {
		taskContainer.removeAll();
		refreshTaskContainer();
	}

	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
		Color background = darkMode ? TaskTideTheme.TEXT : TaskTideTheme.BACKGROUND;
		Color foreground = darkMode ? Color.WHITE : TaskTideTheme.TEXT;
		root.setBackground(background);
		timeLabel.setForeground(darkMode ? TaskTideTheme.WAVE : TaskTideTheme.BLUE);
		emptyState.setForeground(darkMode ? TaskTideTheme.SKY : TaskTideTheme.MUTED);
		deleteTask.setIcon(TaskTideTheme.scaledIcon(darkMode ? "deleteWhite.png" : "delete.png", 28, 28));
		getContentPane().setBackground(background);
		for (java.awt.Component component : root.getComponents()) {
			component.setForeground(foreground);
		}
		repaint();
	}

	public boolean isDarkMode() {
		return darkMode;
	}

	public JPanel getTaskContainer() {
		return taskContainer;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenu getMenu() {
		return menu;
	}

	public void setMenu(JMenu menu) {
		this.menu = menu;
	}

	public JMenuItem getQuit() {
		return quit;
	}

	public void setQuit(JMenuItem quit) {
		this.quit = quit;
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

	public JMenuItem getToggleAppearance() {
		return toggleAppearance;
	}

	public void setToggleAppearance(JMenuItem toggleAppearance) {
		this.toggleAppearance = toggleAppearance;
	}
}

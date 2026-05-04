package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import model.RoundedButton;

@SuppressWarnings("serial")
public class Dashboard extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("User Controls");
	private JMenuItem quit = new JMenuItem("Save and Quit");
	private JMenuItem help = new JMenuItem("Help");

	private JPanel controls = new JPanel();

	private JLabel tasksToday = new JLabel("0");
	private JLabel tasks = new JLabel("Due today");

	private JLabel totalTasks = new JLabel("0");
	private JLabel tasksTotal = new JLabel("Total tasks");

	private RoundedButton addTask = new RoundedButton("+", 18);
	private JButton deleteTask = new JButton(TaskTideTheme.scaledIcon("deleteWhite.png", 28, 28));
	private JLabel timeLabel = new JLabel();

	private JLabel circle = new JLabel(TaskTideTheme.scaledIcon("circle.png", 120, 120));
	private JLabel circle2 = new JLabel(TaskTideTheme.scaledIcon("circle.png", 120, 120));

	private JPanel stats = new TaskTideTheme.RoundedCard(30);
	private JLabel blah = new JLabel("Tasks completed");
	private JLabel a = new JLabel("Out of 12 task slots");
	JProgressBar progress = new JProgressBar();

	public Dashboard() {
		setTitle("TaskTide - Dashboard");
		setForeground(TaskTideTheme.TEXT);
		setBackground(TaskTideTheme.BACKGROUND);
		TaskTideTheme.setFrameIcon(this);
		setSize(1280, 760);
		setMinimumSize(new Dimension(1100, 700));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(true);
		setDefaultCloseOperation(LoginFrame.EXIT_ON_CLOSE);

		setupElements();
		updateTime();

		Timer timer = new Timer(1000, e -> updateTime());
		timer.start();
		revalidate();
		repaint();
		setVisible(true);
	}

	private void setupElements() {
		JPanel root = new TaskTideTheme.GradientPanel(TaskTideTheme.BACKGROUND, TaskTideTheme.SKY);
		root.setLayout(new BorderLayout(24, 24));
		root.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
		setContentPane(root);

		controls.setLayout(new GridBagLayout());
		controls.setBackground(TaskTideTheme.NAVY);
		controls.setPreferredSize(new Dimension(92, 0));
		controls.setBorder(BorderFactory.createEmptyBorder(18, 12, 18, 12));
		root.add(controls, BorderLayout.WEST);

		JLabel railLogo = new JLabel(TaskTideTheme.scaledIcon("logologo.png", 48, 48));
		addRailItem(railLogo, 0, 0, 0);

		addTask.setBackground(TaskTideTheme.WAVE);
		addTask.setForeground(Color.white);
		addTask.setFont(new Font("Helvetica", Font.BOLD, 28));
		addTask.setToolTipText("Create a task");
		addRailItem(addTask, 0, 1, 38);

		deleteTask.setToolTipText("Delete a task");
		TaskTideTheme.styleIconButton(deleteTask);
		addRailItem(deleteTask, 0, 2, 18);

		menuBar.setBackground(TaskTideTheme.BLUE);
		menu.setForeground(Color.white);
		menuBar.add(menu);
		menu.add(help);
		menu.add(quit);
		setJMenuBar(menuBar);

		JPanel content = new JPanel(new BorderLayout(22, 22));
		content.setOpaque(false);
		root.add(content, BorderLayout.CENTER);

		JPanel header = new JPanel(new BorderLayout());
		header.setOpaque(false);
		content.add(header, BorderLayout.NORTH);

		JPanel titleBlock = new JPanel(new GridLayout(2, 1));
		titleBlock.setOpaque(false);
		JLabel heading = new JLabel("Your TaskTide");
		heading.setFont(TaskTideTheme.TITLE_FONT);
		heading.setForeground(TaskTideTheme.TEXT);
		JLabel subtitle = new JLabel("A calm place to check deadlines, progress, and priorities.");
		subtitle.setFont(TaskTideTheme.BODY_FONT);
		subtitle.setForeground(TaskTideTheme.MUTED);
		titleBlock.add(heading);
		titleBlock.add(subtitle);
		header.add(titleBlock, BorderLayout.WEST);

		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setFont(TaskTideTheme.SECTION_FONT);
		timeLabel.setForeground(TaskTideTheme.BLUE);
		header.add(timeLabel, BorderLayout.EAST);

		JPanel dashboardGrid = new JPanel(new GridLayout(1, 2, 22, 22));
		dashboardGrid.setOpaque(false);
		content.add(dashboardGrid, BorderLayout.CENTER);

		JPanel statColumn = new JPanel(new GridLayout(2, 1, 0, 22));
		statColumn.setOpaque(false);
		statColumn.add(createStatCard(circle, tasksToday, tasks, "Click to focus on tasks due today"));
		statColumn.add(createStatCard(circle2, totalTasks, tasksTotal, "Click to open your full task board"));
		dashboardGrid.add(statColumn);

		stats.setLayout(new GridBagLayout());
		stats.setBackground(TaskTideTheme.BLUE);
		dashboardGrid.add(stats);

		blah.setFont(TaskTideTheme.SECTION_FONT);
		blah.setForeground(Color.white);
		a.setFont(TaskTideTheme.BODY_FONT);
		a.setForeground(TaskTideTheme.SKY);

		progress.setMinimum(0);
		progress.setMaximum(12);
		progress.setStringPainted(true);
		progress.setFont(TaskTideTheme.BUTTON_FONT);
		progress.setForeground(TaskTideTheme.WAVE);
		progress.setBackground(new Color(255, 255, 255, 80));
		progress.setPreferredSize(new Dimension(420, 38));

		JLabel progressNote = new JLabel("<html>Keep the tide moving: finish subtasks as reminders appear.</html>");
		progressNote.setFont(TaskTideTheme.BODY_FONT);
		progressNote.setForeground(Color.WHITE);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new java.awt.Insets(18, 18, 18, 18);
		stats.add(blah, constraints);
		constraints.gridy++;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		stats.add(progress, constraints);
		constraints.gridy++;
		constraints.fill = GridBagConstraints.NONE;
		stats.add(a, constraints);
		constraints.gridy++;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		stats.add(progressNote, constraints);
	}

	private JPanel createStatCard(JLabel icon, JLabel value, JLabel label, String tooltip) {
		JPanel card = new TaskTideTheme.RoundedCard(30);
		card.setLayout(new BorderLayout(18, 18));
		card.setBackground(Color.WHITE);
		card.setToolTipText(tooltip);
		card.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

		icon.setHorizontalAlignment(SwingConstants.CENTER);
		card.add(icon, BorderLayout.WEST);

		JPanel copy = new JPanel(new GridLayout(2, 1));
		copy.setOpaque(false);
		value.setFont(new Font("Helvetica", Font.BOLD, 64));
		value.setForeground(TaskTideTheme.BLUE);
		label.setFont(TaskTideTheme.SECTION_FONT);
		label.setForeground(TaskTideTheme.MUTED);
		copy.add(value);
		copy.add(label);
		card.add(copy, BorderLayout.CENTER);
		return card;
	}

	private void addRailItem(Component component, int x, int y, int topInset) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.insets = new java.awt.Insets(topInset, 0, 0, 0);
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		controls.add(component, constraints);
	}

	private void updateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d  |  hh:mm a");
		timeLabel.setText(dateFormat.format(new Date()));
	}

	public void refreshStats(int dueToday, int total, int completed, int maximum) {
		tasksToday.setText(Integer.toString(dueToday));
		totalTasks.setText(Integer.toString(total));
		progress.setMaximum(Math.max(maximum, 1));
		progress.setValue(Math.min(completed, progress.getMaximum()));
		progress.setString(completed + " / " + maximum);
		a.setText("Out of " + maximum + " task slots");
	}

	// Kept for compatibility with the existing controller.
	@SuppressWarnings("unused")
	private void configureLabel(Component label, int i, int j, int k, int l, boolean isTrue, int size) {
		label.setBounds(i, j, k, l);
		label.setFont(new Font("Helvetica", Font.BOLD, size));
		label.setForeground(TaskTideTheme.BLUE);
		add(label);

		if (isTrue) {
			((AbstractButton) label).setContentAreaFilled(false);
			((AbstractButton) label).setBorderPainted(false);
			((JComponent) label).setOpaque(false);
		}
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

	public JMenuItem getHelp() {
		return help;
	}

	public void setHelp(JMenuItem help) {
		this.help = help;
	}

	public JPanel getControls() {
		return controls;
	}

	public void setControls(JPanel controls) {
		this.controls = controls;
	}

	public JLabel getTasksToday() {
		return tasksToday;
	}

	public void setTasksToday(JLabel tasksToday) {
		this.tasksToday = tasksToday;
	}

	public JLabel getTasks() {
		return tasks;
	}

	public void setTasks(JLabel tasks) {
		this.tasks = tasks;
	}

	public JLabel getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(JLabel totalTasks) {
		this.totalTasks = totalTasks;
	}

	public JLabel getTasksTotal() {
		return tasksTotal;
	}

	public void setTasksTotal(JLabel tasksTotal) {
		this.tasksTotal = tasksTotal;
	}

	public RoundedButton getAddTask() {
		return addTask;
	}

	public void setAddTask(RoundedButton addTask) {
		this.addTask = addTask;
	}

	public JButton getDeleteTask() {
		return deleteTask;
	}

	public void setDeleteTask(JButton deleteTask) {
		this.deleteTask = deleteTask;
	}

	public JLabel getCircle() {
		return circle;
	}

	public void setCircle(JLabel circle) {
		this.circle = circle;
	}

	public JLabel getCircle2() {
		return circle2;
	}

	public void setCircle2(JLabel circle2) {
		this.circle2 = circle2;
	}

	public JLabel getBlah() {
		return blah;
	}

	public void setBlah(JLabel blah) {
		this.blah = blah;
	}

	public JLabel getA() {
		return a;
	}

	public void setA(JLabel a) {
		this.a = a;
	}

	public JProgressBar getProgress() {
		return progress;
	}

	public void setProgress(JProgressBar progress) {
		this.progress = progress;
	}
}

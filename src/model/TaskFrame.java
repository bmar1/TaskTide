package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import view.TaskTideTheme;

@SuppressWarnings("serial")
public class TaskFrame extends JFrame {

	public TaskFrame(Task task) {
		setTitle("Task Details - " + task.getTaskName());
		setSize(520, 420);
		setMinimumSize(new Dimension(480, 380));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		TaskTideTheme.setFrameIcon(this);

		JPanel root = new TaskTideTheme.GradientPanel(TaskTideTheme.BACKGROUND, TaskTideTheme.SKY);
		root.setLayout(new BorderLayout(18, 18));
		root.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
		setContentPane(root);

		JLabel title = new JLabel(task.getTaskName());
		title.setFont(TaskTideTheme.TITLE_FONT);
		title.setForeground(TaskTideTheme.TEXT);
		root.add(title, BorderLayout.NORTH);

		JPanel card = new TaskTideTheme.RoundedCard(28);
		card.setBackground(Color.WHITE);
		card.setLayout(new GridLayout(0, 1, 8, 8));
		root.add(card, BorderLayout.CENTER);

		card.add(detail("Deadline", TaskDateUtils.displayDeadline(task.getDeadline())));
		card.add(detail("Timing", TaskDateUtils.relativeDeadline(task.getDeadline())));
		card.add(detail("Priority", priorityText(task.getPriority())));
		card.add(detail("Subtask 1", task.getSubtask1()));
		card.add(detail("Subtask 2", task.getSubtask2()));
		card.add(detail("Subtask 3", task.getSubtask3()));

		JProgressBar progress = new JProgressBar(0, 100);
		progress.setValue(Integer.parseInt(task.getProgress()));
		progress.setStringPainted(true);
		progress.setFont(TaskTideTheme.BUTTON_FONT);
		progress.setForeground(TaskTideTheme.WAVE);
		card.add(progress);

		setVisible(true);
	}

	private JLabel detail(String label, String value) {
		JLabel detail = new JLabel("<html><b>" + label + ":</b> " + value + "</html>");
		detail.setFont(TaskTideTheme.BODY_FONT);
		detail.setForeground(TaskTideTheme.TEXT);
		detail.setHorizontalAlignment(SwingConstants.LEFT);
		return detail;
	}

	private String priorityText(int priority) {
		switch (priority) {
		case 1:
			return "High";
		case 2:
			return "Medium";
		case 3:
			return "Low";
		default:
			return "Unknown";
		}
	}
}

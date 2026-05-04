// Description: Task object as the main data source for all the tasks

package model;

import javax.swing.JProgressBar;

public class Task {

	// fields
	private String taskName;
	private String deadline;
	private int priority;
	private String subtask1;
	private String subtask2;
	private String subtask3;
	private String progress;

	private JProgressBar progressBar;

	public Task(String taskName, String deadline, int priority, String subtask1, String subtask2, String subtask3,
			String progress) {
		this.taskName = taskName;
		this.deadline = deadline;
		this.priority = priority;
		this.subtask1 = subtask1;
		this.subtask2 = subtask2;
		this.subtask3 = subtask3;
		this.progress = progress;
		this.progressBar = new JProgressBar();
		// string of the progress bar initializes as true (so you can see 0%);
		this.progressBar.setStringPainted(true);
	}

	// getters and setters
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getSubtask1() {
		return subtask1;
	}

	public void setSubtask1(String subtask1) {
		this.subtask1 = subtask1;
	}

	public String getSubtask2() {
		return subtask2;
	}

	public void setSubtask2(String subtask2) {
		this.subtask2 = subtask2;
	}

	public String getSubtask3() {
		return subtask3;
	}

	public void setSubtask3(String subtask3) {
		this.subtask3 = subtask3;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	// Getter and setter methods for other fields

}

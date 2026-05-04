# TaskTide

<p align="center">
  <img src="images/logobl.png" alt="TaskTide wave logo" width="180" />
</p>

TaskTide is a Java Swing desktop app for planning tasks, tracking subtasks, and getting priority-based reminders. It includes account login/signup, a redesigned dashboard, a task board, task detail cards, reminder audio, and simple file-based persistence.

## Highlights

- Branded Swing UI using the TaskTide wave and icon assets.
- Dashboard stats for total tasks, tasks due today, and completion progress.
- Task board with add/delete actions, progress bars, deadline summaries, and task detail popups.
- Priority-based reminder timers:
  - Priority 1: every 30 minutes
  - Priority 2: every 45 minutes
  - Priority 3: every 60 minutes
- Safer task input validation with normalized deadline dates.
- Local text-file storage for users and tasks.

## Project Structure

```text
TaskTide/
├── files/                  # User/task data and reminder audio
├── images/                 # Logo and UI image assets
├── src/
│   ├── application/        # App entry point
│   ├── controller/         # Login, dashboard, task, and update controllers
│   ├── model/              # Task/User models and reusable Swing components
│   └── view/               # Swing frames and shared theme helpers
└── docs/                   # Setup, user guide, and design notes
```

## Running Locally

This is an Eclipse-style Java project. Use Java 19 or newer, then run:

```powershell
javac -d out src\application\TaskTideApplication.java src\controller\*.java src\model\*.java src\view\*.java
java -cp out application.TaskTideApplication
```

Run commands from the project root so the relative `images/` and `files/` paths resolve correctly.

## Repository

Remote origin is configured for [bmar1/TaskTide](https://github.com/bmar1/TaskTide.git).

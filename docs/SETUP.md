# Setup Guide

## Requirements

- Windows, macOS, or Linux with a desktop environment
- Java 19 or newer
- Git, if you want to push changes to GitHub

## Compile From PowerShell

Run these commands from the project root:

```powershell
javac -d out src\application\TaskTideApplication.java src\controller\*.java src\model\*.java src\view\*.java
java -cp out application.TaskTideApplication
```

## Eclipse Setup

1. Open Eclipse.
2. Choose `File > Import > Existing Projects into Workspace`.
3. Select the `TaskTide` folder.
4. Make sure the project uses JavaSE-19 or newer.
5. Run `application.TaskTideApplication`.

## Runtime Files

TaskTide reads assets through relative paths, so keep these folders at the project root:

- `images/` for logos and icons
- `files/` for `userDatabase.txt`, `userTasks.txt`, and `popup.wav`

If the app is launched from a different working directory, images or audio may not load.

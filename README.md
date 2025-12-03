# CODEALPHA StudentGradeTracker

A small JavaFX student grade tracker project.

Prerequisites
- JDK 11+ (or newer) installed
- Maven

Build

```powershell
mvn clean package
```

Run

- From an IDE: run the module `com.task1/com.task1.GradeTrackerGUI`.
- From the command line (adjust JavaFX SDK path):

```powershell
java --module-path "C:\path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml -m com.task1/com.task1.GradeTrackerGUI
```

Notes
- I added a `.gitignore` to avoid committing build artifacts and IDE files.

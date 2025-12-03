# Chatbot GUI

A JavaFX-based desktop chatbot application with a conversational interface and system integration features.

## Features

- **Conversational AI**: Responds to user queries with predefined responses and pattern matching
- **User Personalization**: Remembers user names and greets them by name
- **System Integration**: 
  - Open Google in default browser
  - Launch system calculator (Windows, macOS, Linux)
- **Time Display**: Query current time
- **Message History**: Scrollable chat interface with message history
- **Responsive UI**: Clean JavaFX interface with auto-scrolling messages

## Requirements

- Java 11 or higher
- Maven 3.6+ (optional, for building via command line)
- OpenJFX 20 (automatically fetched by Maven)

## Getting Started

### Option 1: Run with Maven (Recommended)

```powershell
cd 'd:\CodeAplha_Artifical Intelligence ChatBot\chatbot'
mvn clean javafx:run
```

### Option 2: Run from IDE

Open the project in IntelliJ IDEA, VS Code, or Eclipse with Maven support and run the main class `com.task3.ChatbotGUI`.

## Project Structure

```
chatbot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── module-info.java         # Module descriptor
│   │   │   └── com/task3/
│   │   │       ├── ChatbotGUI.java      # Main GUI application
│   │   │       └── ChatbotLogic.java    # Chatbot logic and response generation
│   │   └── resources/
│   │       └── com/task3/
│   │           ├── primary.fxml         # Primary scene layout
│   │           ├── secondary.fxml       # Secondary scene layout
│   │           └── styles.css           # Styling
│   └── test/
├── pom.xml                              # Maven configuration
├── .gitignore                           # Git ignore rules
└── README.md                            # This file
```

## Usage

1. **Start the Application**: Run the chatbot GUI
2. **Type Messages**: Enter text in the input field and click "Send" or press Enter
3. **Common Commands**:
   - `hello` → Receive a greeting
   - `what is my name` → Ask the chatbot to recall your name
   - `my name is: [name]` → Tell the chatbot your name
   - `open google` → Open Google in your browser
   - `open calculator` → Launch system calculator
   - `time` → Get the current time
   - `help` → Get help information
   - `bye` → Say goodbye

## Building from Source

### Compile

```powershell
mvn clean compile
```

### Run Tests

```powershell
mvn test
```

### Package as JAR

```powershell
mvn clean package
```

## Technologies Used

- **Java 11+**: Core language
- **JavaFX 20**: GUI framework
- **Maven**: Build tool

## Module System

This project uses Java's module system (JPMS). The module descriptor (`module-info.java`) requires:
- `javafx.controls` – JavaFX UI controls
- `javafx.fxml` – FXML layout support
- `javafx.graphics` – JavaFX graphics
- `java.desktop` – Desktop integration (browser, calculator)

## Troubleshooting

### Module javafx.graphics not found

**Solution**: Ensure `pom.xml` includes platform-specific JavaFX classifiers. The current configuration targets Windows (`win`). For other platforms, update `<javafx.platform>` in `pom.xml` to:
- `mac` for macOS
- `linux` for Linux

Example for macOS:
```xml
<javafx.platform>mac</javafx.platform>
```

### Maven not found

**Solution**: 
1. Install Maven from https://maven.apache.org/download.cgi
2. Add Maven's `bin` directory to your system PATH
3. Or use your IDE's built-in Maven support

### Application won't start

**Solution**: Ensure Java 11+ is installed and on your PATH:
```powershell
java -version
```

## Future Enhancements

- Persistent chat history (save/load conversations)
- Integration with external APIs (weather, news)
- Voice input/output support
- Multi-language support
- User authentication and profiles
- Database for storing conversations

## License

This project is provided as-is for educational purposes.

## Author

CodeAlpha Chatbot Project

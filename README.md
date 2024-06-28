
# Text Processor Application

## Overview

The Text Processor application is a JavaFX-based application designed to perform text processing tasks, including finding and replacing text using regular expressions, counting words matching a specific pattern, and mapping text to its size. It also provides functionalities to open and save text files, as well as manage a data table for text entries.

## Features

1. **Find Matches**: Find all occurrences of a pattern in the input text using a regular expression.
2. **Replace Text**: Replace all occurrences of a pattern in the input text with a specified replacement text.
3. **Count Matches**: Count the number of words in the input text that match a specific pattern.
4. **Map Text to Size**: Map the input text to its size (excluding spaces) and store it in a data table.
5. **Open File**: Open a text file and load its contents into the input field.
6. **Save File**: Save the contents of the input field to a text file.
7. **Data Management**: Add, update, and delete entries in a data table.

## Project Structure


### DataManager.java

Manages a map of text entries. Provides methods to add, remove, update, and retrieve entries from the map.

### TextProcessor.java

Contains methods for text processing, including finding matches, replacing occurrences, and counting words matching a pattern.

### TextProcessorApplication.java

The main application class that sets up the JavaFX stage and loads the FXML layout.

### TextProcessorController.java

The controller class that handles UI interactions, binds UI components to methods, and manages the application's functionalities.

## How to Run

1. **Clone the repository**:
    ```
    git clone <repository-url>
    ```

2. **Open the project in your favorite IDE**.

3. **Run the main application class**:
    ```
    com.testprocessor.textprocessor.TextProcessorApplication
    ```

## Usage

### Finding Matches

1. Enter a regular expression in the `regex_textField`.
2. Enter the text to be processed in the `input_field`.
3. Click the `results_button` to display all matches in the `text_area_field`.

### Replacing Text

1. Enter a regular expression in the `regex_textField`.
2. Enter the text to be processed in the `input_field`.
3. Enter the replacement text in the `replace_field`.
4. Click the `replace_button` to replace all occurrences of the pattern in the `text_area_field`.

### Mapping Text to Size

1. Enter the text to be mapped in the `input_field`.
2. Click the `add_button` to add the text to the data table.

### Managing Data Table

- **Update**: Double-click an entry in the table or use the context menu to open the edit dialog and modify the entry.
- **Delete**: Use the context menu to delete an entry from the table.
- **Populate to Input Field**: Use the context menu to load an entry's text into the `input_field`.

### File Operations

- **Open File**: Click the `open_button` in the menu to open a text file and load its contents into the `input_field`.
- **Save File**: Click the `export_button` to save the contents of the `input_field` to a text file.

## Dependencies

- JavaFX: Ensure JavaFX is properly set up in your development environment.
- Java 11 or later.

## License

This project is licensed under the MIT License.

## Acknowledgements

Special thanks to the developers and contributors of the JavaFX framework and the OpenJDK project.

---

This README provides a comprehensive overview of the Text Processor application, including its features, project structure, usage instructions, and dependencies. Feel free to modify and enhance this document as needed.

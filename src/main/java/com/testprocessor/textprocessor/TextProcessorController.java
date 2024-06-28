package com.testprocessor.textprocessor;

import com.testprocessor.textprocessor.textprocessing.DataManager;
import com.testprocessor.textprocessor.textprocessing.TextProcessor;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.*;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessorController implements Initializable {


    public TextField regex_textField;
    public TextArea input_field;
    public Label label_button;
    public TextFlow text_area_field;
    public Button results_button;
    public Button replace_button;
    public TextField replace_field;
    public Button clear_button;
    public VBox vbox_main;
    public MenuItem open_button;
    public ScrollPane scroll_pane;
    public TableView<Map.Entry<Integer, String>> tableView;
    public Button add_button;
    public TableColumn<Map.Entry<Integer, String>, Integer> id_column;
    public TableColumn<Map.Entry<Integer, String>, String> text_column;
    public Button export_button;
    private TextProcessor textProcessor;
    private DataManager dataManager;

    @FXML
    private void addToTable() {

        //dataManager.printDataMap();
        tableView.getItems().setAll(dataManager.getDataMap().entrySet());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textProcessor = new TextProcessor();
        dataManager = new DataManager();
        results_button.setOnAction(actionEvent -> displayResults());
        replace_button.setOnAction(actionEvent -> replaceText());
        clear_button.setOnAction(actionEvent -> clearFields());
        open_button.setOnAction(actionEvent -> openFile());
        export_button.setOnAction(actionEvent -> exportToFile());
        //speak_button.setOnAction(actionEvent -> speakText());

        addToTable();

        id_column.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getKey()).asObject());
        text_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));


        scroll_pane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            text_area_field.setPrefWidth(newValue.getWidth());
        });

        tableView.setRowFactory(tv -> {
            TableRow<Map.Entry<Integer, String>> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem updateItem = new MenuItem("Update");
            updateItem.setOnAction(event -> {
                if (!row.isEmpty()) {
                    Map.Entry<Integer, String> rowData = row.getItem();
                    showEditDialog(rowData);
                }
            });

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                if (!row.isEmpty()) {
                    Map.Entry<Integer, String> rowData = row.getItem();
                    dataManager.getDataMap().remove(rowData.getKey());
                    addToTable();
                }
            });

            MenuItem populateItem = new MenuItem("Populate to Input Field");
            populateItem.setOnAction(event -> {
                if (!row.isEmpty()) {
                    Map.Entry<Integer, String> rowData = row.getItem();
                    input_field.setText(rowData.getValue());
                }
            });

            contextMenu.getItems().addAll(updateItem, deleteItem, populateItem);
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Map.Entry<Integer, String> rowData = row.getItem();
                    showEditDialog(rowData);
                }
            });

            return row;
        });

    }

    private String getRegularExpression() {

        return regex_textField.getText();
    }

    private String getTextFieldData() {


        return input_field.getText();
    }

    private void displayResults() {
        String regex = getRegularExpression();
        String text = getTextFieldData();

        if (regex.isEmpty()) {
            showErrorDialog("Info", "Please enter a regular expression.");
            return;
        }
        if (text.isEmpty()) {
            showErrorDialog("Error", "Please enter some text before displaying results.");
            return;
        }

        // highlightPatterns(text, regex);

        List<String> matches = textProcessor.findMatches(text, regex);
        int count = textProcessor.countWordsMatchingPattern(text, regex);

        StringBuilder resultText = new StringBuilder("Matches found:\n");
        for (String match : matches) {
            resultText.append(match).append("\n");
        }
        resultText.append("\nTotal Matches: ").append(count);

        Text resultDisplay = new Text(resultText.toString());
        text_area_field.getChildren().clear();
        text_area_field.getChildren().add(resultDisplay);
        scroll_pane.setContent(text_area_field);
    }

    private void replaceText() {
        String regex = getRegularExpression();
        String text = getTextFieldData();
        String replacement = replace_field.getText();

        if (regex.isEmpty()) {
            showErrorDialog("Info", "Please enter a regular expression.");
            return;
        }
        if (text.isEmpty()) {
            showErrorDialog("Error", "Please enter some text before replacing.");
            return;
        }
        if (replacement.isEmpty()) {
            showErrorDialog("Error", "Please enter the replacement text.");
            return;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        text_area_field.getChildren().clear();
        int lastEnd = 0;
        while (matcher.find()) {
            if (lastEnd < matcher.start()) {
                Text before = new Text(text.substring(lastEnd, matcher.start()));
                text_area_field.getChildren().add(before);
            }
            Text replacedText = new Text(replacement);
            replacedText.setFill(Color.RED);
            text_area_field.getChildren().add(replacedText);
            lastEnd = matcher.end();
        }
        if (lastEnd < text.length()) {
            Text after = new Text(text.substring(lastEnd));
            text_area_field.getChildren().add(after);
        }
    }


    private void clearFields() {
        if (regex_textField.getText().isEmpty() && input_field.getText().isEmpty() && replace_field.getText().isEmpty()) {
            showErrorDialog("Info", "There is nothing to clear.");
        } else {
            regex_textField.clear();
            input_field.clear();
            replace_field.clear();
            text_area_field.getChildren().clear();
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();

        alert.showAndWait();
    }

    @FXML
    private void mapTextToSize() {
        String text = getTextFieldData();

        if (text.isEmpty()) {
            showErrorDialog("Error", "Please enter some text to map to its size.");
            return;
        }

        String textWithoutSpaces = text.replaceAll("\\s+", "");
        int textSize = textWithoutSpaces.length();

        dataManager.addToMap(textSize, text);

        addToTable();
        input_field.clear();
    }

    private void showEditDialog(Map.Entry<Integer, String> rowData) {
        // Create a new Stage (window) for the edit dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Edit Text");

        // Create the layout for the dialog
        VBox dialogVBox = new VBox();
        dialogVBox.setPadding(new Insets(10));
        dialogVBox.setSpacing(10);

        // Create TextArea for editing the text
        TextArea editTextArea = new TextArea(rowData.getValue());
        editTextArea.setPrefRowCount(10);
        editTextArea.setPrefColumnCount(50);

        // Create HBox for the buttons
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);

        // Create Save and Cancel buttons
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        buttonsHBox.getChildren().addAll(saveButton, cancelButton);
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);

        dialogVBox.getChildren().addAll(new Label("Edit text:"), editTextArea, buttonsHBox);

        Scene dialogScene = new Scene(dialogVBox, 400, 300);
        dialogStage.setScene(dialogScene);

        // Set the Save button action
        saveButton.setOnAction(event -> {
            String newText = editTextArea.getText();
            dataManager.updateMapEntry(rowData.getKey(), newText);
            addToTable();
            dialogStage.close();
        });

        // Set the Cancel button action
        cancelButton.setOnAction(event -> dialogStage.close());

        dialogStage.showAndWait();
    }


    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(vbox_main.getScene().getWindow());

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
                input_field.setText(fileContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
                showErrorDialog("Error", "Could not read the file: " + e.getMessage());
            }
        }
    }

    private void showExportSuccessDialog(File file) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Successful");
        alert.setHeaderText(null);
        alert.setContentText("File successfully exported to:\n" + file.getAbsolutePath());
        alert.showAndWait();
    }

    private void exportToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Text File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(vbox_main.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(input_field.getText());
                writer.flush();
                showExportSuccessDialog(file);
            } catch (IOException e) {
                showErrorDialog("Error", "Could not export the file: " + e.getMessage());
            }
        }
    }
}




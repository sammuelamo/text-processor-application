module com.testprocessor.textprocessor {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.testprocessor.textprocessor to javafx.fxml;
    exports com.testprocessor.textprocessor;
}
package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController implements ToPane{

    @FXML
    private Button processorButton;

    @FXML
    private Button memoryButton;

    @FXML
    private Button pagingButton;

    @FXML
    void initialize() {
        processorButton.setOnMouseEntered(event -> {
            processorButton.setStyle("-fx-background-color: #FF7F50;");
        });
        processorButton.setOnMouseExited(event -> processorButton.setStyle("-fx-background-color: #FF6347;"));
        processorButton.setOnAction(event -> {
            processorButton.getScene().getWindow().hide();
            toPane("../recourses/processorStatePane.fxml");
        });

        memoryButton.setOnMouseEntered(event -> {
            memoryButton.setStyle("-fx-background-color: #FF7F50;");
        });
        memoryButton.setOnMouseExited(event -> memoryButton.setStyle("-fx-background-color: #FF6347;"));
        memoryButton.setOnAction(event -> {
            memoryButton.getScene().getWindow().hide();
            toPane("../recourses/memoryStatePane.fxml");
        });

        pagingButton.setOnMouseEntered(event -> {
            pagingButton.setStyle("-fx-background-color: #FF7F50;");
        });
        pagingButton.setOnMouseExited(event -> pagingButton.setStyle("-fx-background-color: #FF6347;"));
        pagingButton.setOnAction(event -> {
            pagingButton.getScene().getWindow().hide();
            toPane("../recourses/pagingFileStatusPane.fxml");
        });
    }
}

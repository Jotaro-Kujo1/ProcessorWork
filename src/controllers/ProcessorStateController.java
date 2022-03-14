package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class ProcessorStateController implements ToPane{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField processorText;

    @FXML
    private TextField mmxText;

    @FXML
    private TextField manufacturerText;

    @FXML
    private TextField freqText;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        try{
            CpuInfo cpu = new Sigar().getCpuInfoList()[0];
            processorText.setText(cpu.getModel());
            manufacturerText.setText(cpu.getVendor());
        }catch (SigarException ex){
            ex.printStackTrace();
        }


        backButton.setOnMouseEntered(event -> {
            backButton.setStyle("-fx-background-color: #FF7F50;");
        });
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-background-color: #FF6347;"));
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            toMainPane();
        });

    }
}

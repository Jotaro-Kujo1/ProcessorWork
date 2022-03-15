package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

public class MemoryController implements ToPane{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField allOZU;

    @FXML
    private TextField canUseOZU;

    @FXML
    private TextField allVirMem;

    @FXML
    private TextField canUzeVirMem;

    @FXML
    private Button backButton;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    void initialize() {
        try {
            Sigar sigar = new Sigar();
            Mem mem = sigar.getMem();
            allOZU.setText(Long.toString(mem.getTotal() / 1024L / 1024L) + " Мбайт всего ");
            canUseOZU.setText(Long.toString(mem.getFree() / 1024L / 1024L) + " Мбайт свободно ");

            Swap swap = sigar.getSwap();
            allVirMem.setText(Long.toString(swap.getTotal() / 1024L / 1024L) + " Мбайт всего ");
            canUzeVirMem.setText(Long.toString((mem.getFree()+(swap.getTotal() - mem.getTotal() ))/ 1024L / 1024L) + " Мбайт свободно ");

            System.out.println(swap.getPageIn()/1024L/124L);
            System.out.println(swap.getPageOut());



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

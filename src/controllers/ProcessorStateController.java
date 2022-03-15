package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import graphics.Processor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.ResourceLimit;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import recourses.Storage;

public class ProcessorStateController implements ToPane {

    private Map<String, Long> myLongMap = new HashMap<String, Long>();
    private Map <String, Double> myDoubleMap = new HashMap <String, Double> ();
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
    private LineChart<Number, Number> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    void initialize() {
        try {
            CpuInfo cpu = new Sigar().getCpuInfoList()[0];
            processorText.setText(cpu.getModel());
            manufacturerText.setText(cpu.getVendor());
            freqText.setText(Integer.toString(cpu.getMhz()) + " MHz ");


        } catch (SigarException ex) {
            ex.printStackTrace();
        }

        XYChart.Series series = new XYChart.Series();

        Processor processor = new Processor(new Storage(), lineChart, series);
        new Thread(processor).start();

        //myLongMap = storage.getMyLongMap();
        //myDoubleMap = storage.getMyDoubleMap();



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

package controllers;

import java.net.URL;
import java.util.*;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import graphics.Processor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
    private List<Double> myList = new ArrayList<>();
    private int iteration;
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
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);


        Storage storage = new Storage();

        /*
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("1",23));
        series.getData().add(new XYChart.Data("2",33));
        series.getData().add(new XYChart.Data("3",43));
        series.getData().add(new XYChart.Data("4",53));
        series.getData().add(new XYChart.Data("5",63));
        series.getData().add(new XYChart.Data("6",73));
        lineChart.getData().add(series);
        */


        XYChart.Series series = new XYChart.Series();



        iteration=0;
        Runnable r1 = () -> {
            try{
                for(iteration=0;iteration<1000;iteration++){
                    storage.treatment();
                    myDoubleMap = storage.getMyDoubleMap();

                    //series.getData().add(new XYChart.Data(Integer.toString(i), myDoubleMap.get("getSystemCpuLoad") * 100));
                    //lineChart.getData().addAll(series);
                    //Platform.runLater(() -> lineChart.getData().add(series));
                    Platform.runLater(()->{
                        series.getData().add(new XYChart.Data(Integer.toString(iteration), myDoubleMap.get("getSystemCpuLoad") * 100));
                        lineChart.getData().addAll(series);
                    });
                }
                //Platform.runLater(() -> lineChart.getData().add(series));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        };



        Thread th1 = new Thread(r1);
        //th1.setPriority(5);
        th1.start();



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

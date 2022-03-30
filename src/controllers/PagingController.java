package controllers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hyperic.sigar.*;
import recourses.Storage;

public class PagingController implements ToPane{

    private Map<String, Long> myLongMap = new HashMap<String, Long>();
    private Map <String, Double> myDoubleMap = new HashMap <String, Double> ();
    private int iteration;
    private boolean flag = false;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField allSwap;

    @FXML
    private TextField usedSwap;

    @FXML
    private Button swapButton;

    @FXML
    private Button backButton;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;


    @FXML
    void initialize() {
        try {
            Sigar sigar = new Sigar();
            Mem mem = sigar.getMem();
            Swap swap = sigar.getSwap();
            Storage storage = new Storage();
            storage.treatment();
            myLongMap = storage.getMyLongMap();
            allSwap.setText(Long.toString((swap.getTotal() - mem.getTotal()) / 1024L / 1024L) + " Мбайт всего ");

            usedSwap.setText(Long.toString(myLongMap.get("getCommittedVirtualMemorySize")/1024L/1024L) + " Мбайт занято ");

        }catch (SigarException ex){
            ex.printStackTrace();
        }


        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);

        startButton.setOnMouseEntered(event -> {
            startButton.setStyle("-fx-background-color: #FF7F50;");
        });
        startButton.setOnMouseExited(event -> startButton.setStyle("-fx-background-color: #FF6347;"));
        startButton.setOnAction(event -> {
            lineChart.getData().clear();
            Storage storage = new Storage();
            XYChart.Series series = new XYChart.Series();
            iteration=0;
            flag = true;
            Runnable r1 = () -> {
                try{
                    Sigar sigar = new Sigar();
                    Mem mem = sigar.getMem();
                    Swap swap = sigar.getSwap();
                    while(flag){
                        if(iteration%10==0) {
                            storage.treatment();
                            myLongMap = storage.getMyLongMap();
                            Platform.runLater(() -> {
                                //Long tmp = (((myLongMap.get("getCommittedVirtualMemorySize") / 1024L / 1024L)) / ((swap.getTotal() - mem.getTotal()) / 1024L / 1024L))*100;
                                Long tmp = (swap.getTotal() - mem.getTotal()) / 1024L / 1024L;
                                Long tmp1 = myLongMap.get("getCommittedVirtualMemorySize") / 1024L / 1024L;
                                Long tmp2 = ((tmp1 * 10) / tmp) * 100 / 10;
                                series.getData().add(new XYChart.Data(Integer.toString(iteration), tmp2));
                                if (!lineChart.getData().contains(series)) {
                                    lineChart.getData().addAll(series);
                                }
                            });
                        }
                        iteration++;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            };
            Thread th1 = new Thread(r1);
            th1.start();
        });


        stopButton.setOnMouseEntered(event -> {
            stopButton.setStyle("-fx-background-color: #FF7F50;");
        });
        stopButton.setOnMouseExited(event -> stopButton.setStyle("-fx-background-color: #FF6347;"));
        stopButton.setOnAction(event -> {
            flag = false;
        });


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

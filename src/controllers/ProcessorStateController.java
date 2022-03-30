package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


import javafx.application.Platform;

import javafx.fxml.FXML;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import recourses.Storage;
import sun.security.krb5.internal.crypto.Des;

public class ProcessorStateController implements ToPane {

    private Map<String, Long> myLongMap = new HashMap<String, Long>();
    private Map <String, Double> myDoubleMap = new HashMap <String, Double> ();
    private int iteration;
    private boolean flag = false;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField processorText;


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
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button openButton;


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
                    while(flag){
                        if(iteration%10==0) {
                            storage.treatment();
                            myDoubleMap = storage.getMyDoubleMap();

                            Platform.runLater(() -> {
                                series.getData().add(new XYChart.Data(Integer.toString(iteration), myDoubleMap.get("getSystemCpuLoad") * 100));
                                lineChart.getData().addAll(series);
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

        openButton.setOnMouseEntered(event -> {
            openButton.setStyle("-fx-background-color: #FF7F50;");
        });
        openButton.setOnMouseExited(event -> openButton.setStyle("-fx-background-color: #FF6347;"));
        openButton.setOnAction(event -> {
            Desktop desktop = null;
            if(Desktop.isDesktopSupported()) desktop = Desktop.getDesktop();
            try{
                desktop.open(new File("C:/Users/bucel/Desktop/2 курс/ТА_РПЗ.docx"));
                System.out.println("Opened");
            }catch (IOException ex){
                ex.printStackTrace();
            }
        });

    }
}

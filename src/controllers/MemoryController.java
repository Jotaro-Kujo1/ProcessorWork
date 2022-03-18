package controllers;

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
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import recourses.Storage;

public class MemoryController implements ToPane{

    private Map<String, Long> myLongMap = new HashMap<String, Long>();
    private Map <String, Double> myDoubleMap = new HashMap <String, Double> ();
    private int iteration;
    private boolean flag = false;

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
    private Button startButton;

    @FXML
    private Button stopButton;

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
                        storage.treatment();
                        myLongMap = storage.getMyLongMap();
                        Platform.runLater(()->{
                            Long tmp = (myLongMap.get("getFreePhysicalMemorySize") * 100) / myLongMap.get("getTotalPhysicalMemorySize");
                            series.getData().add(new XYChart.Data(Integer.toString(iteration), tmp));
                            lineChart.getData().addAll(series);
                        });
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

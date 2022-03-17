package graphics;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import recourses.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Processor implements Runnable{
    private LineChart<Number, Number> lineChart;
    //private CategoryAxis x;
    //private NumberAxis y;
    private Storage storage;
    private XYChart.Series series;

    public Processor(Storage storage, LineChart lineChart, XYChart.Series series){
        this.storage = storage;
        this.lineChart = lineChart;
        //this.x = x;
        //this.y = y;
        this.series = series;
    }

    public void run() {
        /*
        for (int i = 0; i < 10; i++) {
            storage.treatment();
            Map<String, Double> map = new HashMap<String, Double>();
            series.getData().add(new XYChart.Data("i",map.get("getSystemCpuLoad")*100));
            lineChart.getData().addAll(series);
            try{
                Thread.sleep(500);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
         */
        Task<Integer> task = new Task<Integer>(){
            @Override protected Integer call() throws Exception{
                int iterations;
                Storage storage = new Storage();
                for(iterations = 0;iterations<100;iterations++){
                    if(isCancelled()){
                        break;
                    }
                    storage.treatment();
                    Map<String, Double> map = new HashMap<String, Double>();
                    series.getData().add(new XYChart.Data(iterations,map.get("getSystemCpuLoad")*100));
                    lineChart.getData().addAll(series);
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException ex){
                        if(isCancelled()){
                            break;
                        }
                    }
                }
                return iterations;
            }
        };
    }
}

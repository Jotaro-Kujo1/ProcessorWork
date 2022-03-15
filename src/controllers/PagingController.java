package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hyperic.sigar.*;

public class PagingController implements ToPane{

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
    void initialize() {
        try {
            Sigar sigar = new Sigar();
            Mem mem = sigar.getMem();
            Swap swap = sigar.getSwap();


            ResourceLimit rLimit = sigar.getResourceLimit();
            System.out.println(rLimit.getMemoryMax());
            System.out.println(rLimit.getVirtualMemoryMax() / 1024L / 1024L);
            System.out.println(rLimit.getCpuMax());
            System.out.println(rLimit.getStackMax());
            System.out.println(rLimit.getOpenFilesMax());
            System.out.println(rLimit.getMemoryCur());
            System.out.println(rLimit.getVirtualMemoryCur());
            System.out.println(rLimit.getFileSizeMax());
            System.out.println(rLimit.getPipeSizeCur());



            allSwap.setText(Long.toString((swap.getTotal() - mem.getTotal())/1024L/1024L) + " Мбайт всего ");
            usedSwap.setText(Long.toString(swap.getPageOut() / 1024L / 1024L) + " Мбайт использовано ");
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

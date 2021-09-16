package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    static CellMap map = new CellMap(Main.WIDTH, Main.HEIGHT);
//    static CellMap map = new CellMap(100, 100);

    @FXML
    public FlowPane display;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.display.getChildren().add(map.getCvs());
        map.randomSetCells();
//        boolean[][] origin = {
//                {false, false, false},
//                {false, true, true},
//                {false, true, true}
//        };
//        map.setCells(origin);
        map.paint();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.util.Arrays;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modele.Categorie;
import modele.Emprunt;

/**
 * FXML Controller class
 *
 * @author JOKISS
 */
public class StatistiqueController implements Initializable {

    @FXML
    private TableView<?> tab;

    @FXML
    private JFXButton add;

    @FXML
    private ImageView icones;

    
    @FXML
    private AnchorPane statCercle;

    @FXML
    private AnchorPane statBar;
    @FXML
    private JFXTextField txt_search;
    private Emprunt emprunt;
    private Categorie categorie;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emprunt = new Emprunt();
        categorie=new Categorie();
        statBar.getChildren().add(createChart());
        statCercle.getChildren().add(createCIRC());
        final Timeline digitalTime = new Timeline(
            new KeyFrame(Duration.seconds(0),
                new EventHandler<ActionEvent>() {

                    @Override public void handle(ActionEvent actionEvent) {
                        statBar.getChildren().clear();
                        statBar.getChildren().add(createChart());
                        statCercle.getChildren().clear();
                        statCercle.getChildren().add(createCIRC());
                        
                    }
                }
            ),
            new KeyFrame(Duration.seconds(2))
        );
        digitalTime.setCycleCount(Animation.INDEFINITE);

        // start the analogueClock.
        digitalTime.play();

    }    
    protected BarChart<String, Number> createChart() {
        final String[] years = {"0","1", "2", "3","4", "5", "6", "7", "8", "9","10", "11","12"};
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis,"",null));
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        // setup chart
        bc.setPrefSize(680, 209);
        
        bc.setTitle("Staistique générale annuelle");
   
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(years)));
      
        // add starting data
        XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
        series1.setName("Nombre d'emprunt effectuer");
       
        for (int i = 1; i <= 12; i++) {
            series1.getData().add(new XYChart.Data<String,Number>(years[i],emprunt.getNbEmpruntByMois(i)));
        }
        
       
        bc.getData().add(series1);
        
        return bc;
    }
    protected PieChart createCIRC() {
        ObservableList<PieChart.Data> test = FXCollections.observableArrayList();
        for (int i = 0; i < categorie.getAllCat().size(); i++) {
            test.add(new PieChart.Data(categorie.getAllCat().get(i).toString(), emprunt.getNbEmpruntByCat(categorie.getCatByNom(categorie.getAllCat().get(i).toString()).getId())));
            
        }
//        PieChart pc = new PieChart(FXCollections.observableArrayList(
//            new PieChart.Data("Sun", 20),
//            new PieChart.Data("IBM", 12),
//            new PieChart.Data("HP", 25),
//            new PieChart.Data("Dell", 22),
//            new PieChart.Data("Apple", 30)
//        ));
         PieChart pc= new PieChart(test);
        
        // setup chart
        pc.setId("BasicPie");
        pc.setLegendSide(Side.LEFT);
        pc.setTitle("Diagrame des emprunt par catégorie");
        final Label caption = new Label("");
        for (final PieChart.Data data : pc.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()) + "%");
                     }
                });
        }
        return pc;
    }
}

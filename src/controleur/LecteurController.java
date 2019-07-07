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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import modele.*;


/**
 * FXML Controller class
 *
 * @author JOKISS
 */
public class LecteurController implements Initializable {

    @FXML
    private TableView<Lecteur> tab;

    @FXML
    private JFXButton add;

    @FXML
    private ImageView icones;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colMat;

    @FXML
    private TableColumn colNom;

    @FXML
    private TableColumn colPost;

    @FXML
    private TableColumn colPrenom;

    @FXML
    private TableColumn colAdresse;

    @FXML
    private TableColumn colTel;

    @FXML
    private TableColumn colMail;

    @FXML
    private TableColumn colType;

    @FXML
    private AnchorPane paneStat;
    
    @FXML
    private AnchorPane panMain;
    
    @FXML
    private AnchorPane panAdd;
    

    @FXML
    private JFXTextField txt_code;

    @FXML
    private JFXTextField txt_nom;

    @FXML
    private JFXTextField txt_post;

    @FXML
    private JFXTextField txt_pre;

    @FXML
    private JFXComboBox combo_typ;

    @FXML
    private JFXTextField txt_addresse;

    @FXML
    private JFXTextField txt_tel;

    @FXML
    private JFXTextField txt_mail;

    //tab2
    //tab2
    @FXML
    private TableView<Emprunt> tab1;
    @FXML
    private TableColumn colId1;

    @FXML
    private TableColumn colLiv;

    

    @FXML
    private TableColumn colJour;

    @FXML
    private TableColumn colRetour;

    @FXML
    private TableColumn colOb;

    @FXML
    private TableColumn colId_lec;

    @FXML
    private TableColumn colId_liv;

    @FXML
    private Label lab_tit;
    private boolean visible;
    private int save;
    private Lecteur lecteur;
    private Emprunt emprunt;
    Image imPlus;
    Image imFois ;

    @FXML
    void add() {
        lab_tit.setText("Ajouter");
        seeDrawer();
        save = 1;
    }

    @FXML
    void annuler() {
        seeDrawer();
    }

    @FXML
    void mod() {
        save = 2;
        lab_tit.setText("Modifier");
        seeDrawer();
        ObservableList<Lecteur> setected;
        Lecteur l  = tab.getSelectionModel().getSelectedItems().get(0);
        txt_code.setText(l.getMatricule());
        txt_nom.setText(l.getNom());
        txt_post.setText(l.getPostnom());
        txt_pre.setText(l.getPrenom());
        txt_tel.setText(l.getTel());
        txt_mail.setText(l.getMail());
        txt_addresse.setText(l.getAdresse());
        combo_typ.setValue(l.getType());
        lecteur=l;
    }

    @FXML
    void save() {
        if(isValid()){
            if(save == 1){
                try {

                    lecteur.ajouter( txt_code.getText(), txt_nom.getText(), txt_post.getText(), txt_pre.getText(), txt_addresse.getText(), txt_tel.getText(), txt_mail.getText(), combo_typ.getValue().toString());
                    tab.getItems().setAll(lecteur.afficher());
                    seeDrawer();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur se produite lors de l'ajout\n"+ex.getMessage()); 

                }
            }else{
                try {

                    lecteur.modifier(lecteur.getId(), txt_code.getText(), txt_nom.getText(), txt_post.getText(), txt_pre.getText(), txt_addresse.getText(), txt_tel.getText(), txt_mail.getText(), combo_typ.getValue().toString());
                    tab.getItems().setAll(lecteur.afficher());
                    seeDrawer();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur se produite lors de la modification\n"+ex.getMessage()); 

                }
            }
        }
        
    }

    @FXML
    void supp() {
        seeDrawer();
        ObservableList<Lecteur> setected;
        Lecteur l  = tab.getSelectionModel().getSelectedItems().get(0);
        l.supprimer(l.getId());
        seeDrawer();
        tab.getItems().setAll(lecteur.afficher());
    }

    @FXML
    void recherche() {
        if(!txt_search.getText().isEmpty())
            tab.setItems(lecteur.search(txt_search.getText()));
        
    }
    @FXML
    void actualiser(){
        if(txt_search.getText().isEmpty())
            tab.setItems(lecteur.afficher());
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lecteur = new Lecteur();
        emprunt = new Emprunt();
        imPlus  = new Image("/images/ic_add_black_48dp.png",false);
        imFois = new Image("/images/ic_close_black_36dp.png",false);
        colId.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("id"));
        colMat.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("matricule"));
        colNom.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("nom"));
        colPost.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("postnom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("prenom"));

        colTel.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("tel"));
        colMail.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("mail"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("adresse"));

        colType.setCellValueFactory(new PropertyValueFactory<Lecteur,String>("type"));
        tab.getItems().addAll(lecteur.afficher());
        
        
        colId.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("id"));
        colLiv.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("liv"));
        
        colJour.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("dateEmprunt"));
        colRetour.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("dateRetour"));
        colOb.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("observation"));
        combo_typ.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if ("Ajouter".equals(newValue)) {
                    try{
                        
                        combo_typ.setEditable(true);
                        
                    }catch(IndexOutOfBoundsException e){
                        
                    }
                    
                    //txt_titre.cellFactoryProperty();
                }
            }
        }); 
        visible =false;
        save = 0;
        seeDrawer();
    }    
    public void seeDrawer(){
        if(!visible){
            icones.setImage(imPlus);
            panMain.getChildren().remove(panAdd);
            combo_typ.getItems().clear();
            vide();
            visible = true;
            save = 0;
        }else{
            icones.setImage(imFois);
            panMain.getChildren().add(panAdd);
            combo_typ.getItems().add("Ajouter");
            combo_typ.getItems().addAll(lecteur.getAllType());
            visible = false;
        }
    }
    public void vide(){
        txt_code.setText("");
        txt_nom.setText("");
        txt_post.setText("");
        txt_pre.setText("");
        txt_tel.setText("");
        txt_mail.setText("");
        txt_addresse.setText("");
        combo_typ.setValue("");
    }
    public boolean isValid(){
        boolean ok = true;
        if(txt_code.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir le matricule SVP"); 
            ok = false;
        }
        if(txt_pre.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir le nom"); 
            ok = false;
        }
        if(txt_nom.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir le prenom svp"); 
            ok = false;
        }
        if(txt_tel.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir le numero téléphone"); 
            ok = false;
        }
        if(txt_addresse.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir l'adresse"); 
            ok = false;
        }
        
        return ok;
    }
    @FXML
    private void clicTab(){
        ObservableList<Lecteur> setected;
        Lecteur l  = tab.getSelectionModel().getSelectedItems().get(0);
        tab1.getItems().setAll(emprunt.getEmpruntNoRemiByLecteur(l.getId()));
        
        paneStat.getChildren().clear();
        paneStat.getChildren().add(createChart(l.getId()));
    }  
    protected BarChart<String, Number> createChart(int id) {
        final String[] years = {"0","1", "2", "3","4", "5", "6", "7", "8", "9","10", "11","12"};
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis,"",null));
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        // setup chart
        bc.setPrefSize(246, 135);
        bc.setTitle("Staistique des emprunt");
   
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(years)));
      
        // add starting data
        XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
        series1.setName("Data Series 1");
       
        for (int i = 1; i <= 12; i++) {
            series1.getData().add(new XYChart.Data<String,Number>(years[i],emprunt.getNbEmpruntLivreByMoisAndLivre(i, id)));
        }
        
       
        bc.getData().add(series1);
        
        return bc;
    }
    
}

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
public class LivreController implements Initializable {

    
    @FXML
    private TableView<Livre> tab;
    
    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colCode;
    
    @FXML
    private Label lab_tit;

    @FXML
    private TableColumn colTitre;

    @FXML
    private TableColumn colAut;

    @FXML
    private TableColumn colAnn;

    @FXML
    private TableColumn colCat;

    @FXML
    private TableColumn colVil;

    @FXML
    private TableColumn colEmp;

    @FXML
    private TableColumn colNb;

    @FXML
    private JFXButton add;

    @FXML
    private ImageView icones;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private AnchorPane panMain;
    @FXML
    private AnchorPane panAdd;

    @FXML
    private JFXTextField txt_code;

    @FXML
    private JFXTextField txt_titre;

    @FXML
    private JFXTextField txt_aut;

    @FXML
    private JFXTextField txt_edi,txt_EDITION;

    @FXML
    private JFXComboBox combo_cat;

    @FXML
    private JFXTextField txt_vil;

    @FXML
    private JFXTextField txt_emp;

    //tab2
    @FXML
    private TableView<Emprunt> tab1;
    @FXML
    private TableColumn colId1;

    @FXML
    private TableColumn colLect;

    

    @FXML
    private TableColumn colJour;

    @FXML
    private TableColumn colRetour;

    @FXML
    private TableColumn colOb;

    @FXML
    private TableColumn colId_lec,colEdition;

    @FXML
    private TableColumn colId_liv;

    @FXML
    private AnchorPane paneStat;
    @FXML
    private JFXTextField txt_qte;
    private boolean visible;
    private Livre livre;
    private Categorie categorie;
    private int save;
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
    void mod() {
        save = 2;
        lab_tit.setText("Modifier");
        seeDrawer();
        ObservableList<Livre> setected;
        Livre l  = tab.getSelectionModel().getSelectedItems().get(0);
        txt_code.setText(l.getCode());
        txt_aut.setText(l.getAuteur());
        txt_edi.setText(l.getAnneEdition());
        txt_vil.setText(l.getVilleEdition());
        txt_qte.setText(l.getNombre());
        txt_titre.setText(l.getTitre());
        txt_emp.setText(l.getEnplacement());
        combo_cat.setValue(l.getCategorie());
        livre=l;
        
    }

    @FXML
    void recherche() {
        if(!txt_search.getText().isEmpty())
            tab.setItems(livre.search(txt_search.getText()));
        
    }
    @FXML
    void actualiser(){
        if(txt_search.getText().isEmpty())
            tab.setItems(livre.afficher());
    }
    @FXML
    void supp() {
        seeDrawer();
        ObservableList<Livre> setected;
        Livre l  = tab.getSelectionModel().getSelectedItems().get(0);
        l.supprimer(l.getId());
        seeDrawer();
        tab.getItems().setAll(livre.afficher());
    }

    @FXML
    void annuler() {
        seeDrawer();
        
    }
    @FXML
    void save() {
        System.out.println("test ");
        
        if(isValid()){
            categorie = categorie.getCatByNom(combo_cat.getValue().toString());
            if(categorie.getId()==0){
                try {
                        categorie.ajouter(combo_cat.getValue().toString());
                        categorie = categorie.getCatByNom(combo_cat.getValue().toString());

                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Une erreur se produite lors de l'ajout de la categorie\n"+ex.getMessage()); 
                        
                    }    
            }
            if(save == 1){
                try {
                    if(categorie.getAllCat().contains(combo_cat.getValue().toString())){
                        livre.ajouter( txt_code.getText(), txt_titre.getText(), txt_aut.getText(), txt_edi.getText(), txt_EDITION.getText(),categorie, txt_qte.getText(), txt_vil.getText(), txt_emp.getText());
                        tab.getItems().setAll(livre.afficher());
                        seeDrawer();
                    }else{
                        categorie.ajouter(combo_cat.getValue().toString());
                        livre.ajouter( txt_code.getText(), txt_titre.getText(), txt_aut.getText(), txt_edi.getText(), txt_EDITION.getText(),categorie, txt_qte.getText(), txt_vil.getText(), txt_emp.getText());
                        tab.getItems().setAll(livre.afficher());
                        seeDrawer();
                    }
                    
                }catch (SQLException ex) {
                    
                    if(ex.getErrorCode()==1452){
                        try {
                            
                            livre.ajouter( txt_code.getText(), txt_titre.getText(), txt_aut.getText(), txt_edi.getText(), txt_EDITION.getText(),categorie, txt_qte.getText(), txt_vil.getText(), txt_emp.getText());
                            tab.getItems().setAll(livre.afficher());
                            seeDrawer();

                        }catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "Une erreur se produite lors de l'ajout de la categorie\n"+e.getMessage()); 

                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Une erreur se produite lors de l'ajout\n"+ex.getMessage()); 
                    }
                    

                }           
                
            }else{
                try {
                    if(categorie.getAllCat().contains(combo_cat.getValue().toString())){
                        livre.modifier(livre.getId(), txt_code.getText(), txt_titre.getText(), txt_aut.getText(), txt_edi.getText(),txt_EDITION.getText(), categorie, txt_qte.getText(), txt_vil.getText(), txt_emp.getText());
                        tab.getItems().setAll(livre.afficher());
                        seeDrawer();
                    }else{
                        categorie.ajouter(combo_cat.getValue().toString());
                        livre.modifier(livre.getId(), txt_code.getText(), txt_titre.getText(), txt_aut.getText(), txt_edi.getText(), txt_EDITION.getText(),categorie, txt_qte.getText(), txt_vil.getText(), txt_emp.getText());
                        tab.getItems().setAll(livre.afficher());
                        seeDrawer();
                    }
                    
                }
               catch (SQLException ex) {
                    if(ex.getErrorCode()==1452){
                        try {
                            categorie.ajouter(combo_cat.getValue().toString());
                            livre.modifier(livre.getId(), txt_code.getText(), txt_titre.getText(), txt_aut.getText(), txt_edi.getText(),txt_EDITION.getText(), categorie, txt_qte.getText(), txt_vil.getText(), txt_emp.getText());
                            tab.getItems().setAll(livre.afficher());
                            seeDrawer();

                        }catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "Une erreur se produite lors de l'ajout de la categorie\n"+e.getMessage()); 

                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Une erreur se produite lors de la modification\n"+ex.getMessage()); 

                    }
                    
                 
                }
            }
        }
        
    }

    /**
     * Initializes the controller class.
     */
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        livre  = new Livre();
        categorie = new Categorie();
        imPlus  = new Image("/images/ic_add_black_48dp.png",false);
        imFois = new Image("/images/ic_close_black_36dp.png",false);
        colId.setCellValueFactory(new PropertyValueFactory<Livre,String>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<Livre,String>("code"));
        colAnn.setCellValueFactory(new PropertyValueFactory<Livre,String>("AnneEdition"));
        colCat.setCellValueFactory(new PropertyValueFactory<Livre,String>("categorie"));
        colNb.setCellValueFactory(new PropertyValueFactory<Livre,String>("nombre"));
        colVil.setCellValueFactory(new PropertyValueFactory<Livre,String>("villeEdition"));
        colEmp.setCellValueFactory(new PropertyValueFactory<Livre,String>("Enplacement"));
        colTitre.setCellValueFactory(new PropertyValueFactory<Livre,String>("titre"));
        colAut.setCellValueFactory(new PropertyValueFactory<Livre,String>("auteur"));
        colEdition.setCellValueFactory(new PropertyValueFactory<Livre,String>("edition"));
        tab.getItems().addAll(livre.afficher());
        
        
        emprunt = new Emprunt();
        colId.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("id"));
        
        colLect.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("lect"));
        colJour.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("dateEmprunt"));
        colRetour.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("dateRetour"));
        colOb.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("observation"));
        combo_cat.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if ("Ajouter".equals(newValue)) {
                    try{
                        
                        combo_cat.setEditable(true);
                        
                        
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
            combo_cat.getItems().clear();
            vide();
            visible = true;
            save = 0;
        }else{
            icones.setImage(imFois);
            panMain.getChildren().add(panAdd);
            combo_cat.getItems().add("Ajouter");
            combo_cat.getItems().addAll(categorie.getAllCat());
            visible = false;
        }
    }
    public void vide(){
        txt_code.setText("");
        txt_aut.setText("");
        txt_edi.setText("");
        txt_vil.setText("");
        txt_qte.setText("");
        txt_titre.setText("");
        txt_emp.setText("");
        txt_EDITION.setText("");
        combo_cat.setValue(null);
    }
    public boolean isValid(){
        boolean ok = true;
        if(txt_code.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir le code SVP"); 
            ok = false;
        }
        if(txt_titre.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir le titre"); 
            ok = false;
        }
        if(txt_aut.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir l'auteur"); 
            ok = false;
        }
        if(combo_cat.getValue()==null){
            JOptionPane.showMessageDialog(null, "Veuillez choisir une catégorie"); 
            ok = false;
        }
        if(txt_qte.getText().isEmpty()){
            
            JOptionPane.showMessageDialog(null, "Veuillez saisir la quantité de livre ajouter"); 
            ok = false;
        }if(!txt_qte.getText().isEmpty()){
            try{
                if(Integer.parseInt(txt_qte.getText())<0){
                    JOptionPane.showMessageDialog(null, "Vous ne pouvez pas saisir une quantité negative"); 
                    ok = false;
                }
            }catch(NumberFormatException i){
                JOptionPane.showMessageDialog(null, "la quantité que vous devais saisir doit etre un nombre entier SVP"); 
                    ok = false;
            }
            
            
        }
        return ok;
    }
    @FXML
    private void clicTab(){
        ObservableList<Livre> setected;
        Livre l  = tab.getSelectionModel().getSelectedItems().get(0);
        tab1.getItems().setAll(emprunt.getEmpruntNoRemiByLivre(l.getId()));
        
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
            series1.getData().add(new XYChart.Data<String,Number>(years[i],emprunt.getNbEmpruntLecteurByMoisAndLecteur(i, id)));
        }
        
       
        bc.getData().add(series1);
        
        return bc;
    }
    
}

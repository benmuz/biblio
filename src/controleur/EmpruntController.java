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
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
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
public class EmpruntController implements Initializable {

    @FXML
    private TableView<Emprunt> tab;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colLect;

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
    private JFXButton add;

    @FXML
    private ImageView icones;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXComboBox combo_liv;

    @FXML
    private Label lab_tit;

    @FXML
    private JFXComboBox combo_lect;
    
    @FXML
    private AnchorPane panMain;

    @FXML
    private AnchorPane panAdd;

    
    @FXML
    private DatePicker txt_retour;

  
    private boolean visible;
    private Lecteur lecteur;
    private Livre livre;
    private Emprunt emprunt;
    private int save;
    Image imPlus;
    Image imFois ;
    @FXML
    void add(ActionEvent event) {
        seeDrawer();
        lab_tit.setText("Emprunter");
        
        lecteur.afficher().stream().forEach((l) -> {
            combo_lect.getItems().add(l.getNom()+" "+l.getPostnom()+" "+l.getPrenom());
        });
        
        ObservableList<Emprunt> setected;
        
        Emprunt e  = tab.getSelectionModel().getSelectedItems().get(0);
        
        livre.afficher().stream().forEach((l) -> {
            combo_liv.getItems().add(l.getTitre());
        });
        if(e!=null){
            combo_liv.setValue(e.getLiv());
        }
        save = 1;
    }

    @FXML
    void mod(ActionEvent event) {
        save = 2;
        lab_tit.setText("Modifier");
        
        ObservableList<Emprunt> setected;
        
        Emprunt e  = tab.getSelectionModel().getSelectedItems().get(0);
         lecteur.afficher().stream().forEach((l) -> {
            combo_lect.getItems().add(l.getNom()+" "+l.getPostnom()+" "+l.getPrenom());
        });
        livre.afficher().stream().forEach((l) -> {
            combo_liv.getItems().add(l.getTitre());
        });
        combo_lect.setValue(e.getLect());
        combo_liv.setValue(e.getLiv());
        txt_retour.setValue(LocalDate.of(Integer.parseInt(e.getDateRetour().substring(0,4)), Integer.parseInt(e.getDateRetour().substring(5,7)), Integer.parseInt(e.getDateRetour().substring(8,10))));
        seeDrawer();
        emprunt=e;
    }
    @FXML
    public void retourner(){
        save = 3;
        lab_tit.setText("Retourner");
        
        ObservableList<Emprunt> setected;
        
        Emprunt e  = tab.getSelectionModel().getSelectedItems().get(0);
        combo_lect.getItems().add(e.getLect());
        combo_lect.setValue(e.getLect());
        combo_liv.getItems().add(e.getLiv());
        combo_liv.setValue(e.getLiv());
        txt_retour.setValue(LocalDate.now());
        seeDrawer();
        emprunt=e;
    }
    @FXML
    void recherche() {
        if(!txt_search.getText().isEmpty())
            tab.setItems(emprunt.search(txt_search.getText()));
        
    }
    @FXML
    void actualiser(){
        if(txt_search.getText().isEmpty())
            tab.setItems(emprunt.afficher());
    }

    @FXML
    void supp(ActionEvent event) {
        ObservableList<Emprunt> setected;
        Emprunt e  = tab.getSelectionModel().getSelectedItems().get(0);
        e.supprimer(e.getId());
        seeDrawer();
        tab.getItems().setAll(emprunt.afficher());
    }

    @FXML
    void annuler(ActionEvent event) {
        seeDrawer();
    }
    @FXML
    void save(ActionEvent event) {
        if(save == 1){
            try {
                lecteur = lecteur.getLecteurByMatriculeOrNom(combo_lect.getValue().toString());
                livre = livre.getLivreByCodeOrTitre(combo_liv.getValue().toString());
                String retour = txt_retour.getValue().toString();
                emprunt.enprunter(lecteur, livre,retour );
                tab.getItems().setAll(emprunt.afficher());
                seeDrawer();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Une erreur se produite lors de l'emprunt\n"+ex.getMessage()); 
   
            }
        }else if(save == 2){
            try {
                lecteur = lecteur.getLecteurByMatriculeOrNom(combo_lect.getValue().toString());
                livre = livre.getLivreByCodeOrTitre(combo_liv.getValue().toString());
                String retour = txt_retour.getValue().toString();
                emprunt.modifier(emprunt.getId(), lecteur, livre,retour);
                tab.getItems().setAll(emprunt.afficher());
                seeDrawer();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Une erreur se produite lors de la modification\n"+ex.getMessage()); 
   
            }
        }else {
            try {
                lecteur = lecteur.getLecteurByMatriculeOrNom(combo_lect.getValue().toString());
                livre = livre.getLivreByCodeOrTitre(combo_liv.getValue().toString());
                String retour = txt_retour.getValue().toString();
                emprunt.retourner(emprunt.getId(), lecteur, livre,retour);
                tab.getItems().setAll(emprunt.afficher());
                seeDrawer();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Une erreur se produite lors de la opération\n"+ex.getMessage()); 
   
            }
        }
        
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lecteur = new Lecteur();
        livre  = new Livre();
        emprunt = new Emprunt();
        imPlus  = new Image("/images/ic_add_black_48dp.png",false);
        imFois = new Image("/images/ic_close_black_36dp.png",false);
        colId.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("id"));
        colLiv.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("liv"));
        colLect.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("lect"));
        colJour.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("dateEmprunt"));
        colRetour.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("dateRetour"));
        colOb.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("observation"));
        tab.getItems().addAll(emprunt.afficher());
        visible =false;
        save = 0;
        seeDrawer();
        
        
    } 
    public void seeDrawer(){
        if(!visible){
            icones.setImage(imPlus);
            panMain.getChildren().remove(panAdd);
            combo_lect.getItems().clear();
            combo_liv.getItems().clear();
            vide();
            visible = true;
            save = 0;
        }else{
            icones.setImage(imFois);
            panMain.getChildren().add(panAdd);
            
            visible = false;
        }
    }
    public void vide(){
        combo_lect.setValue(null);
        combo_liv.setValue(null);
        txt_retour.setValue(null);
       
    }
    
}

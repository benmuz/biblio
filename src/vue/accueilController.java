/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import com.jfoenix.controls.JFXButton;
import controleur.LivreController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import modele.DatabaseAccess;

/**
 *
 * @author JOKISS
 */
public class accueilController implements Initializable {
    
        
    @FXML
    private  StackPane contenteurPrefet;
    @FXML
    private JFXButton menuLivre;

    @FXML
    private JFXButton menuStatisitque;

    @FXML
    private JFXButton menuLecteur;

    @FXML
    private JFXButton menuEmprunt;
    
    @FXML
    private AnchorPane panMessage;
            
    private AnchorPane panLivre, panLecteur, panEmprunt, panStatisitque;
    String style = "-fx-background-color: #6d4c41; -fx-text-fill:white;";
    String styleInit = "-fx-background-color:  #a1887f; -fx-text-fill:black;";
    public void resetStyle(){
        menuLivre.setStyle(styleInit);
        menuLecteur.setStyle(styleInit);
        menuEmprunt.setStyle(styleInit);
        menuStatisitque.setStyle(styleInit);
        
    }
    @FXML
    private void menuLivre(){
        if(DatabaseAccess.isOk){
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panLivre);
            menuLivre.setStyle(style);
        }else{
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panMessage);
        }
        
    }
    @FXML
    private void menuLecteur(){
        
        if(DatabaseAccess.isOk){
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panLecteur);
            menuLecteur.setStyle(style);
        }else{
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panMessage);
        }
    }
    @FXML
    private void menuEmprunt(){
        
        if(DatabaseAccess.isOk){
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panEmprunt);
            menuEmprunt.setStyle(style);
        }else{
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panMessage);
        }
    }
    @FXML
    private void menuStatisitque(){
        
        if(DatabaseAccess.isOk){
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panStatisitque);
            menuStatisitque.setStyle(style);
        }else{
            resetStyle();
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panMessage);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            panLivre = FXMLLoader.load(getClass().getResource("/vue/livre.fxml"));
            panLecteur = FXMLLoader.load(getClass().getResource("/vue/lecteur.fxml"));
            panEmprunt = FXMLLoader.load(getClass().getResource("/vue/emprunt.fxml"));
            panStatisitque = FXMLLoader.load(getClass().getResource("/vue/statistique.fxml"));
            DatabaseAccess.connecterDB();
            
            contenteurPrefet.getChildren().clear();
                contenteurPrefet.getChildren().add(panLivre);
                menuLivre.setStyle(style);
                System.out.println("ok : "+DatabaseAccess.isOk);
            if(DatabaseAccess.isOk){
                contenteurPrefet.getChildren().clear();
                contenteurPrefet.getChildren().add(panLivre);
                menuLivre.setStyle(style);
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(LivreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}

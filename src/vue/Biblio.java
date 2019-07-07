/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static modele.DatabaseAccess.connecterDB;

/**
 *
 * @author JOKISS
 */

public class Biblio extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        connecterDB();
        Parent root = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/images/tél.ico")); 
        

        Runtime runtime = Runtime.getRuntime();
        runtime.exec(new String[]{"C:\\xampp\\xampp-control.exe"});
        stage.setScene(scene);
        stage.show();
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

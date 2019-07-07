/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import static modele.DatabaseAccess.Rs;
import static modele.DatabaseAccess.St;

/**
 *
 * @author JOKISS
 */
public class Livre {
    private int id;
    private String code;
    private String titre;
    private String auteur;
    private String AnneEdition;
    private String edition;
    private String categorie;
    private Categorie cat;
    private String nombre;
    private String villeEdition;
    private String Enplacement;
    

    public Livre() {
        cat  = new Categorie();
        DatabaseAccess.connecterDB();
    }

    public Livre(int id, String code, String titre, String auteur, String AnneEdition, String edition, String categorie, String nombre, String villeEdition, String Enplacement) {
        this.id = id;
        this.code = code;
        this.titre = titre;
        this.auteur = auteur;
        this.AnneEdition = AnneEdition;
        this.edition=edition;
        this.categorie = categorie;
        this.nombre = nombre;
        this.villeEdition = villeEdition;
        this.Enplacement = Enplacement;
    }

    public Livre(int id) {
        this.id = id;
    }
    
    public Livre getLivreById(int id){
        Livre l = new Livre();
        try{        
            Rs = St.executeQuery("SELECT * FROM `livre` where id_livre = '"+id+"';");
            while(Rs.next()){
                
                l.setId(Rs.getInt(1));
                l.setCode(Rs.getString(2));
                l.setTitre(Rs.getString(3));
                l.setAuteur(Rs.getString(4));
                l.setAnneEdition(Rs.getString(5));
                l.setEdition(Rs.getString(6));
                l.setNombre(Rs.getString(8));
                l.setVilleEdition(Rs.getString(9));
                l.setEnplacement(Rs.getString(10));
                l.setCategorie(Rs.getString(7));
            }
            l.setCat(cat.getCatById(Integer.parseInt(l.getCategorie())));
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return l;
        
    }
    public Livre getLivreByCodeOrTitre(String code){
        Livre l = new Livre();
        try{        
            Rs = St.executeQuery("SELECT * FROM `livre` where code = '"+code+"' or titre ='"+code+"' ;");
            while(Rs.next()){
                
                l.setId(Rs.getInt(1));
                l.setCode(Rs.getString(2));
                l.setTitre(Rs.getString(3));
                l.setAuteur(Rs.getString(4));
                l.setAnneEdition(Rs.getString(5));
                l.setEdition(Rs.getString(6));
                l.setNombre(Rs.getString(8));
                l.setVilleEdition(Rs.getString(9));
                l.setEnplacement(Rs.getString(10));
                l.setCategorie(Rs.getString(7));
            }
            l.setCat(cat.getCatById(Integer.parseInt(l.getCategorie())));
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return l;
        
    }
    
     public ObservableList<Livre> afficher(){
        ObservableList<Livre> livre = FXCollections.observableArrayList();
        
        try{        
            Rs = St.executeQuery("SELECT * FROM `livre` ;");
            while(Rs.next()){
                Livre l = new Livre();
                l.setId(Rs.getInt(1));
                l.setCode(Rs.getString(2));
                l.setTitre(Rs.getString(3));
                l.setAuteur(Rs.getString(4));
                l.setAnneEdition(Rs.getString(5));
                l.setEdition(Rs.getString(6));
                l.setNombre(Rs.getString(8));
                l.setVilleEdition(Rs.getString(9));
                l.setEnplacement(Rs.getString(10));
                l.setCategorie(Rs.getString(7));
                l.setCat(new Categorie(Rs.getInt(7)));
                livre.add(l);
            }
            for (int i = 0; i < livre.size(); i++) {
                Livre li = livre.get(i);
                li.setCategorie(cat.getCatById(Integer.parseInt(li.getCategorie())).getNom());
               livre.set(i, li);
                
            }
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return livre;
    }
    public ObservableList<Livre> search(String mot){

        ObservableList<Livre> livre = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT * FROM `livre` "
                    + "WHERE `code` like '%"+mot+"%' or `titre` like '%"+mot+"%' or `auteur` like '%"+mot+"%' "
                    + "or `anneedition` like '%"+mot+"%' "
                    + "or `id_cat` like (select `id_cat` from categorie where nom  like '%"+mot+"%' ) "
                    + "or `nombre` = '%"+mot+"%' or `villesition` like '%"+mot+"%' or `rayon` like '%"+mot+"%' ;");
            while(Rs.next()){
                Livre l = new Livre();
                l.setId(Rs.getInt(1));
                l.setCode(Rs.getString(2));
                l.setTitre(Rs.getString(3));
                l.setAuteur(Rs.getString(4));
                l.setAnneEdition(Rs.getString(5));
                l.setEdition(Rs.getString(6));
                l.setNombre(Rs.getString(8));
                l.setVilleEdition(Rs.getString(9));
                l.setEnplacement(Rs.getString(10));
                l.setCategorie(Rs.getString(7));
                l.setCat(new Categorie(Rs.getInt(7)));
                livre.add(l);
            }
            for (int i = 0; i < livre.size(); i++) {
                Livre li = livre.get(i);
                li.setCategorie(cat.getCatById(Integer.parseInt(li.getCategorie())).getNom());
               livre.set(i, li);
                
            }
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return livre;
    }
    public void ajouter( String code, String titre, String auteur, String AnneEdition,String edition, Categorie categorie, String nombre, String villeEdition, String Enplacement) throws SQLException{
        St.executeUpdate("INSERT INTO `livre`( `code`, `titre`, `auteur`, `anneedition`, `edition`, `id_cat`, `nombre`, `villesition`, `rayon`) "
                + "     VALUES ('"+code+"','"+titre+"','"+auteur+"','"+AnneEdition+"','"+edition+"','"+categorie.getId()+"','"+nombre+"','"+villeEdition+"','"+Enplacement+"')");
    }
    public void modifier(int id, String code, String titre, String auteur, String AnneEdition, String edition, Categorie categorie, String nombre, String villeEdition, String Enplacement) throws SQLException{
        if(JOptionPane.showConfirmDialog(null, "Confirmer la modification", "Modifier", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            St.executeUpdate("UPDATE `livre` SET `code`='"+code+"',`titre`='"+titre+"',"
                    + "`auteur`='"+auteur+"',`anneedition`='"+AnneEdition+"',`edition` = '"+edition+"',`id_cat`='"+categorie.getId()+"',"
                    + "`nombre`='"+nombre+"',`villesition`='"+villeEdition+"',`rayon`='"+Enplacement+"' "
                    + "WHERE `id_livre`='"+id+"';");      
        }           

    }
    public void supprimer(int id){
        if(JOptionPane.showConfirmDialog(null, "Confirmer la suppression", "Suppression", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            try {      
                St.executeUpdate("DELETE FROM `livre` WHERE id_livre ='"+id+"';");
            } catch (SQLException ex) {
                Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }           

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getAnneEdition() {
        return AnneEdition;
    }

    public void setAnneEdition(String AnneEdition) {
        this.AnneEdition = AnneEdition;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVilleEdition() {
        return villeEdition;
    }

    public void setVilleEdition(String villeEdition) {
        this.villeEdition = villeEdition;
    }

    public String getEnplacement() {
        return Enplacement;
    }

    public void setEnplacement(String Enplacement) {
        this.Enplacement = Enplacement;
    }

    public Categorie getCat() {
        return cat;
    }

    public void setCat(Categorie cat) {
        this.cat = cat;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
    
    
}

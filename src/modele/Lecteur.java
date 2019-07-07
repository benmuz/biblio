/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.SQLException;
import java.util.LinkedList;
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
public class Lecteur {
    private int id;
    private String matricule;
    private String nom;
    private String postnom;
    private String prenom;
    private String adresse;
    private String tel;
    private String mail;
    private String type;

    public Lecteur() {
    }

    public Lecteur(int id) {
        this.id = id;
    }

    public Lecteur(int id, String matricule, String nom, String postnom, String prenom, String adresse, String tel, String mail, String type) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
        this.mail = mail;
        this.type = type;
    }
    public ObservableList<Lecteur> afficher(){
        ObservableList<Lecteur> lecteur = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT * FROM `lecteur` ;");
            while(Rs.next()){
                Lecteur l = new Lecteur();
                l.setId(Rs.getInt(1));
                l.setMatricule(Rs.getString(2));
                l.setNom(Rs.getString(3));
                l.setPostnom(Rs.getString(4));
                l.setPrenom(Rs.getString(5));
                l.setAdresse(Rs.getString(6));
                l.setTel(Rs.getString(7));
                l.setMail(Rs.getString(8));
                l.setType(Rs.getString(9));
                
                lecteur.add(l);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lecteur;
    }
    public ObservableList<Lecteur> search(String mot){
        ObservableList<Lecteur> lecteur = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT * FROM `lecteur` "
                    + "WHERE `matricule` like '%"+mot+"%' or `nom` like  '%"+mot+"%'"
                    + " or `postnom` like'%"+mot+"%' or `prenom` like  '%"+mot+"%' "
                    + "or `adresse` like  '%"+mot+"%' or `tel` like  '%"+mot+"%'"
                    + " or `mail`  like  '%"+mot+"%' or type like  '%"+mot+"%' ;");
            while(Rs.next()){
                Lecteur l = new Lecteur();
                l.setId(Rs.getInt(1));
                l.setMatricule(Rs.getString(2));
                l.setNom(Rs.getString(3));
                l.setPostnom(Rs.getString(4));
                l.setPrenom(Rs.getString(5));
                l.setAdresse(Rs.getString(6));
                l.setTel(Rs.getString(7));
                l.setMail(Rs.getString(8));
                l.setType(Rs.getString(9));
                
                lecteur.add(l);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return lecteur;
    }
    public Lecteur getLecteurById(int id){
        Lecteur l = new Lecteur();
        try{        
            Rs = St.executeQuery("SELECT * FROM `lecteur` WHERE id_lecteur = '"+id+"' ;");
            while(Rs.next()){
                
                l.setId(Rs.getInt(1));
                l.setMatricule(Rs.getString(2));
                l.setNom(Rs.getString(3));
                l.setPostnom(Rs.getString(4));
                l.setPrenom(Rs.getString(5));
                l.setAdresse(Rs.getString(6));
                l.setTel(Rs.getString(7));
                l.setMail(Rs.getString(8));
                l.setType(Rs.getString(9));
                
                
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return l;
    }
    public Lecteur getLecteurByMatriculeOrNom(String mat){
        Lecteur l = new Lecteur();
        try{        
            Rs = St.executeQuery("SELECT * FROM `lecteur` WHERE concat(nom,' ',postnom,' ',prenom) = '"+mat+"' or matricule =  '"+mat+"';");
            while(Rs.next()){
                
                l.setId(Rs.getInt(1));
                l.setMatricule(Rs.getString(2));
                l.setNom(Rs.getString(3));
                l.setPostnom(Rs.getString(4));
                l.setPrenom(Rs.getString(5));
                l.setAdresse(Rs.getString(6));
                l.setTel(Rs.getString(7));
                l.setMail(Rs.getString(8));
                l.setType(Rs.getString(9));
                
                
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return l;
    } 
    
    public LinkedList getAllType(){
        LinkedList tit = new LinkedList();
        try{        
            Rs = St.executeQuery("SELECT distinct `type` FROM `lecteur`;");
            while(Rs.next()){
                tit.add(Rs.getString("type"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tit;
    }
    public void ajouter( String matricule, String nom, String postnom, String prenom, String adresse, String tel, String mail, String type) throws SQLException{
         St.executeUpdate("INSERT INTO `lecteur`( `matricule`, `nom`, `postnom`, `prenom`, `adresse`, `tel`, `mail`, `type`) "
                 + "VALUES ('"+matricule+"','"+nom+"','"+postnom+"','"+prenom+"','"+adresse+"','"+tel+"','"+mail+"','"+type+"')");
    }
    public void modifier(int id, String matricule, String nom, String postnom, String prenom, String adresse, String tel, String mail, String type) throws SQLException{
        if(JOptionPane.showConfirmDialog(null, "Confirmer la modification", "Modifier", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            St.executeUpdate("UPDATE `lecteur` SET `matricule`='"+matricule+"',`nom`='"+nom+"',"
                    + "`postnom`='"+postnom+"',`prenom`='"+prenom+"',`adresse`='"+adresse+"',"
                    + "`tel`='"+tel+"',`mail`='"+mail+"',`type`='"+type+"' "
                    + "WHERE  `id_lecteur`='"+id+"';");      
        } 
    }
    public void supprimer(int id){
        if(JOptionPane.showConfirmDialog(null, "Confirmer la suppression", "Suppression", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            try {      
                St.executeUpdate("DELETE FROM lecteur WHERE id_lecteur ='"+id+"';");
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

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}

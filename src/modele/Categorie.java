/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.SQLException;
import java.util.LinkedList;
import static modele.DatabaseAccess.Rs;
import static modele.DatabaseAccess.St;

/**
 *
 * @author JOKISS
 */
public class Categorie {
    private int id;
    private String nom;

    public Categorie() {
    }

    public Categorie(int id) {
        this.id = id;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public LinkedList getAllCat(){
        LinkedList tit = new LinkedList();
        try{        
            Rs = St.executeQuery("SELECT `nom` FROM `categorie`;");
            while(Rs.next()){
                tit.add(Rs.getString("nom"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tit;
    }
    public Categorie getCatByNom(String nom){
        Categorie p = new  Categorie();
        try{        
            Rs = St.executeQuery("SELECT * FROM `categorie` WHERE `nom` = '"+nom+"';");
            while(Rs.next()){
                p.setId(Rs.getInt(1));
                p.setNom(Rs.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return p;
    }
    public Categorie getCatById(int id){
        Categorie p = new  Categorie();
        try{        
            Rs = St.executeQuery("SELECT * FROM `categorie` WHERE `id_cat` = '"+id+"';");
            while(Rs.next()){
                p.setId(Rs.getInt(1));
                p.setNom(Rs.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return p;
    }
    public void ajouter(String nom) throws SQLException{
        St.executeUpdate("INSERT INTO `categorie`(`nom`) VALUES ('"+nom+"')");
    }
    public void modifier(int id, String nom) throws SQLException{
        St.executeUpdate("");
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static modele.DatabaseAccess.Rs;
import static modele.DatabaseAccess.St;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static modele.DatabaseAccess.St;

/**
 *
 * @author JOKISS
 */
public class Emprunt {
    private int id;
    private Livre livre;
    private String liv;
    private Lecteur lecteur;
    private String lect;
    private String dateEmprunt;
    private String dateRetour;
    private String observation;

    
    public Emprunt() {
        livre = new Livre();
        lecteur = new Lecteur();
    }

    public Emprunt(int id, Livre livre, String liv, Lecteur lecteur, String lect, String dateEmprunt, String dateRetour, String observation) {
        this.id = id;
        this.livre = livre;
        this.liv = liv;
        this.lecteur = lecteur;
        this.lect = lect;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.observation = observation;
    }

   
    
    public ObservableList<Emprunt> afficher(){
        ObservableList<Emprunt> emprunt = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT e.`id_emp`, concat(le.nom,' ',le.postnom,' ',le.prenom) as lecteur, e.`id_lecteur`,li.titre, e.`id_livre`, e.`dateEmprunt`, e.`dateRetour`, e.`observation` \n" +
                                "FROM `emprunt` e, livre li, lecteur le \n" +
                                "WHERE e.`id_lecteur` = le.`id_lecteur`\n" +
                                "and e.`id_livre` = li.`id_livre` ORDER BY e.`id_emp` desc;");
            while(Rs.next()){
                emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                
            }
            
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return emprunt;
    }
    public ObservableList<Emprunt> search(String mot){
        ObservableList<Emprunt> emprunt = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT e.`id_emp`, concat(le.nom,' ',le.postnom,' ',le.prenom) as lecteur, e.`id_lecteur`,li.titre, e.`id_livre`, e.`dateEmprunt`, e.`dateRetour`, e.`observation` \n" +
                                "FROM `emprunt` e, livre li, lecteur le \n" +
                                "WHERE e.`id_lecteur` = le.`id_lecteur`\n" +
                                "and e.`id_livre` = li.`id_livre`"
                    + "and (concat(le.nom,' ',le.postnom,' ',le.prenom) like '%"+mot+"%' or e.`dateEmprunt` like '%"+mot+"%' or  e.`observation` like '%"+mot+"%' or li.titre like '%"+mot+"%' ) ORDER BY e.`id_emp` desc; ;");
            while(Rs.next()){
                emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                
            }
            
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return emprunt;
    }
    public Emprunt getEmpruntById(int id){
        Emprunt e = new Emprunt();
        try{        
            Rs = St.executeQuery("SELECT e.`id_emp`, concat(le.nom,' ',le.postnom,' ',le.prenom) as lecteur, e.`id_lecteur`,li.titre, e.`id_livre`, e.`dateEmprunt`, e.`dateRetour`, e.`observation` \n" +
                                "FROM `emprunt` e, livre li, lecteur le \n" +
                                "WHERE e.`id_lecteur` = le.`id_lecteur`\n" +
                                "and e.`id_livre` = li.`id_livre` and e.`id_emp` = '"+id+"';");
            while(Rs.next()){
                e= new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8)); 
                
            }
            
            
                
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return e;
    }
    public ObservableList<Emprunt> getEmpruntNoRemiByLivre(int idLivre){
        ObservableList<Emprunt> emprunt = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT e.`id_emp`, concat(le.nom,' ',le.postnom,' ',le.prenom) as lecteur, e.`id_lecteur`,li.titre, e.`id_livre`, e.`dateEmprunt`, e.`dateRetour`, e.`observation` \n" +
                                "FROM `emprunt` e, livre li, lecteur le \n" +
                                "WHERE e.`id_lecteur` = le.`id_lecteur`\n" +
                                "and e.`id_livre` = li.`id_livre` and  e.`id_livre` = '"+idLivre+"' and e.`observation` = 'non retourner';");
            while(Rs.next()){
                emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    
        return emprunt;
    }
    public ObservableList<Emprunt> getEmpruntNoRemiByLecteur(int idLecteur){
        ObservableList<Emprunt> emprunt = FXCollections.observableArrayList();
         try{        
            Rs = St.executeQuery("SELECT e.`id_emp`, concat(le.nom,' ',le.postnom,' ',le.prenom) as lecteur, e.`id_lecteur`,li.titre, e.`id_livre`, e.`dateEmprunt`, e.`dateRetour`, e.`observation` \n" +
                                "FROM `emprunt` e, livre li, lecteur le \n" +
                                "WHERE e.`id_lecteur` = le.`id_lecteur`\n" +
                                "and e.`id_livre` = li.`id_livre` and  e.`id_lecteur` = '"+idLecteur+"' and e.`observation` = 'non retourner';");
            while(Rs.next()){
                emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return emprunt;
    }
    public ObservableList<Emprunt> getEmpruntHistoriqueByLivre(int idLivre){
        ObservableList<Emprunt> emprunt = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT e.`id_emp`, concat(le.nom,' ',le.postnom,' ',le.prenom) as lecteur, e.`id_lecteur`,li.titre, e.`id_livre`, e.`dateEmprunt`, e.`dateRetour`, e.`observation` \n" +
                                "FROM `emprunt` e, livre li, lecteur le \n" +
                                "WHERE e.`id_lecteur` = le.`id_lecteur`\n" +
                                "and e.`id_livre` = li.`id_livre` and  e.`id_lecteur` = '"+idLivre+"' ;");
            while(Rs.next()){
                emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return emprunt;
    }
    public ObservableList<Emprunt> getEmpruntHistoriqueByLecteur(int idLecteur){
        ObservableList<Emprunt> emprunt = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT e.`id_emp`, concat(le.nom,' ',le.postnom,' ',le.prenom) as lecteur, e.`id_lecteur`,li.titre, e.`id_livre`, e.`dateEmprunt`, e.`dateRetour`, e.`observation` \n" +
                                "FROM `emprunt` e, livre li, lecteur le \n" +
                                "WHERE e.`id_lecteur` = le.`id_lecteur`\n" +
                                "and e.`id_livre` = li.`id_livre` and  e.`id_lecteur` = '"+idLecteur+"' ;");
            while(Rs.next()){
                emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return emprunt;
    }
    public int getNbEmpruntLecteurByMoisAndLecteur(int mois, int idLect){
        int nb = 0;
        try{        
            Rs = St.executeQuery("SELECT count(`id_emp`) FROM `emprunt` WHERE (SELECT MONTH( `dateEmprunt` )) = '"+mois+"' and `id_lecteur` = '"+idLect+"';");
            while(Rs.next()){
                //emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                nb =Rs.getInt(1);
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nb;
    }
    public int getNbEmpruntByMois(int mois){
        int nb = 0;
        try{        
            Rs = St.executeQuery("SELECT count(`id_emp`) FROM `emprunt` WHERE (SELECT MONTH( `dateEmprunt` )) = '"+mois+"' ;");
            while(Rs.next()){
                //emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                nb =Rs.getInt(1);
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nb;
    }
    public int getNbEmpruntLivreByMoisAndLivre(int mois, int idLect){
        int nb = 0;
        try{        
            Rs = St.executeQuery("SELECT count(`id_emp`) FROM `emprunt` WHERE (SELECT MONTH( `dateEmprunt` )) = '"+mois+"' and  `id_livre` = '"+idLect+"';");
            while(Rs.next()){
                //emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                nb =Rs.getInt(1);
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nb;
    }
   
    public int getNbEmpruntByCat(int idCat){
         int nb = 0;
        try{        
            Rs = St.executeQuery("SELECT count(e.`id_emp`) FROM `emprunt` e, livre l WHERE e.`id_livre` = l.`id_livre` and l.id_cat = '"+idCat+"';");
            while(Rs.next()){
                //emprunt.add(new Emprunt(Rs.getInt(1), new Livre(Rs.getInt(5)), Rs.getString(4), new Lecteur(Rs.getInt(3)), Rs.getString(2), Rs.getString(6), Rs.getString(7), Rs.getString(8))); 
                nb =Rs.getInt(1);
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nb;
    }
    public void enprunter(Lecteur le, Livre li, String dateRetour) throws SQLException{
        System.out.println("date : "+LocalDate.now());
        St.executeUpdate("INSERT INTO `emprunt`( `id_lecteur`, `id_livre`, `dateEmprunt`, `dateRetour`, `observation`) "
                + "VALUES ('"+le.getId()+"','"+li.getId()+"','"+LocalDate.now().toString()+"','"+dateRetour+"','non retourner')");
    }
    public void retourner(int id,Lecteur le, Livre li,String dateRetour) throws SQLException{
        if(JOptionPane.showConfirmDialog(null, "Confirmer l'opération", "Retourner", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            St.executeUpdate("UPDATE `emprunt` SET `id_lecteur`='"+le.getId()+"',`id_livre`='"+li.getId()+"',"
                    + "`dateEmprunt`='"+LocalDate.now().toString()+"',`dateRetour`='"+dateRetour+"',`observation`='Retourner'"
                    + " WHERE  `id_emp`='"+id+"';");      
        } 
    }
    public void modifier(int id, Lecteur le, Livre li, String dateRetour) throws SQLException{
        if(JOptionPane.showConfirmDialog(null, "Confirmer la modification", "Modifier", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            St.executeUpdate("UPDATE `emprunt` SET `id_lecteur`='"+le.getId()+"',`id_livre`='"+li.getId()+"',"
                    + "`dateEmprunt`='"+LocalDate.now().toString()+"',`dateRetour`='"+dateRetour+"'"
                    + "WHERE  `id_emp`= '"+id+"';");      
        } 
    }
    public void supprimer(int id){
        if(JOptionPane.showConfirmDialog(null, "Confirmer la suppression", "Suppression", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            try {      
                St.executeUpdate("DELETE FROM `emprunt` WHERE `id_emp` ='"+id+"';");
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

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Lecteur getLecteur() {
        return lecteur;
    }

    public void setLecteur(Lecteur lecteur) {
        this.lecteur = lecteur;
    }

   

    public String getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(String dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getLiv() {
        return liv;
    }

    public void setLiv(String liv) {
        this.liv = liv;
    }

    public String getLect() {
        return lect;
    }

    public void setLect(String lect) {
        this.lect = lect;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
    
}

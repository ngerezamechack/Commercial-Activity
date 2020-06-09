/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.controlers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vente.beans.Boite;
import vente.beans.Produit;
import vente.donnees.DProduit;

/**
 *
 * @author NGEREZA
 */
class ProduitPane extends AnchorPane{
    //la vue
    @FXML
    private TextField tid,tnom,tpu,tch;
    @FXML
    private ListView<Produit> tlist;
    
    //les donnees
    private DProduit dpro = new DProduit();
    private Produit produit;
    
    //fenetre
    private Stage st = new Stage(StageStyle.UTILITY);
    
    //constructeur pardefaut
    public ProduitPane(ListView<Produit> _tlist,TextField _tpu,TextField _tqte,TextField _ttotal,TextField _tch) {
        
        FXMLLoader load = new FXMLLoader(getClass().getResource("/org/fieldnt/vente/vues/produit.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            getStylesheets().add(getClass().getResource("/org/fieldnt/vente/styles/styles.css").toExternalForm());
            initList();
            tlist.setItems(dpro.list());
            
            st.setScene(new Scene(this));
            st.setOnCloseRequest((event)->{
                tlist.setItems(dpro.list());
            });
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    
    //ajouter une periode
    @FXML
    private void ajouter(){
        if(Boite.verifier(tnom,tpu)){
            produit = new Produit();
            produit.setId((int) System.currentTimeMillis()/100);
            produit.setNom(tnom.getText());
            produit.setPu(Double.valueOf(tpu.getText()));
            
            //execution
            if(Boite.showConfirmation("Ajouter?", "ajout")){
                if(dpro.add(produit) != null){
                    rafraichir();
                    Boite.showInformation(produit.toString()+" ajouté(e)", "ajout", Alert.AlertType.INFORMATION);
                }
            }
        }
    }
    
    
    
    //recuperer une période
    @FXML
    private void get(){
        if((produit = tlist.getSelectionModel().getSelectedItem()) != null){
            tid.setText(String.valueOf(produit.getId()));
            tnom.setText(produit.getNom());
            tpu.setText(String.valueOf(produit.getPu()));
        }
    }
    
    //modfier une période
    @FXML
    private void modifier(){
        if(Boite.verifier(tid,tnom,tpu)){
            produit = new Produit();
            produit.setId(Integer.valueOf(tid.getText()));
            produit.setNom(tnom.getText());
            produit.setPu(Double.valueOf(tpu.getText()));
            
            //execution
            if(Boite.showConfirmation("Modifier?", "modif")){
                if(dpro.update(produit) != null){
                    rafraichir();
                    Boite.showInformation(produit.toString()+" modifié(e)", "modif", Alert.AlertType.INFORMATION);
                }
            }
        }
    }
    
    //supprimer une période
    @FXML
    private void supprimer(){
        if(Boite.verifier(tid)){
            produit = new Produit();
            produit.setId(Integer.valueOf(tid.getText()));
            produit.setNom(tnom.getText());
            produit.setPu(Double.valueOf(tpu.getText()));
            
            //execution
            if(Boite.showConfirmation("Supprimer?", "suppression")){
                if(dpro.remove(produit) != null){
                    rafraichir();
                    Boite.showInformation(produit.toString()+" supprimé(e)", "suppression", Alert.AlertType.INFORMATION);
                }
            }
        }
    }
    
    @FXML
    private void rafraichir(){
        Boite.vider(tid,tnom,tpu,tch);
        initList();
    }
    
    
    private void initList(){
        tlist.setItems(dpro.list());
    }
    
    public void show(){
        if(st.isShowing()){
            st.setAlwaysOnTop(true);
            st.setAlwaysOnTop(false);
        }else{
            st.show();
            st.setAlwaysOnTop(true);
            st.setAlwaysOnTop(false);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.controlers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fieldnt.vente.beans.Boite;
import org.fieldnt.vente.beans.Periode;
import org.fieldnt.vente.donnees.DPeriode;

/**
 *
 * @author NGEREZA
 */
public class PeriodePane extends AnchorPane{
    //la vue
    @FXML
    private TextField tid,tlib;
    @FXML
    private DatePicker tdeb,tfin;
    @FXML
    private ListView<Periode> tlist;
    
    //les donnees
    private DPeriode dper = new DPeriode();
    private Periode periode;
    
    //fenetre
    private Stage st = new Stage(StageStyle.UTILITY);
    
    //constructeur pardefaut
    public PeriodePane(ComboBox<Periode> tper) {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/org/fieldnt/vente/vues/periode.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            getStylesheets().add(getClass().getResource("/org/fieldnt/vente/styles/styles.css").toExternalForm());
            initList();
            tper.setItems(dper.list());
            
            st.setScene(new Scene(this));
            st.setOnCloseRequest((event)->{
                tper.setItems(dper.list());
            });
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    
    //ajouter une periode
    @FXML
    private void ajouter(){
        if(Boite.verifier(tlib,tdeb,tfin)){
            periode = new Periode();
            periode.setId((int) System.currentTimeMillis()/100);
            periode.setDebut(tdeb.getValue());
            periode.setFin(tfin.getValue());
            periode.setLibelle(tlib.getText());
            
            //execution
            if(Boite.showConfirmation("Ajouter?", "ajout")){
                if(dper.add(periode) != null){
                    rafraichir();
                    Boite.showInformation(periode.getLibelle()+" ajouté(e)", "ajout", Alert.AlertType.INFORMATION);
                }
            }
        }
    }
    
    
    
    //recuperer une période
    @FXML
    private void get(){
        if((periode = tlist.getSelectionModel().getSelectedItem()) != null){
            tid.setText(String.valueOf(periode.getId()));
            tdeb.setValue(periode.getDebut());
            tfin.setValue(periode.getFin());
            tlib.setText(periode.getLibelle());
        }
    }
    
    //modfier une période
    @FXML
    private void modifier(){
        if(Boite.verifier(tid,tdeb,tfin,tlib)){
            periode = new Periode();
            periode.setId(Integer.valueOf(tid.getText()));
            periode.setDebut(tdeb.getValue());
            periode.setFin(tfin.getValue());
            periode.setLibelle(tlib.getText());
            
            //execution
            if(Boite.showConfirmation("Modifier?", "modif")){
                if(dper.update(periode) != null){
                    rafraichir();
                    Boite.showInformation(periode.getLibelle()+" modifié(e)", "modif", Alert.AlertType.INFORMATION);
                }
            }
        }
    }
    
    //supprimer une période
    @FXML
    private void supprimer(){
        if(Boite.verifier(tid)){
            periode = new Periode();
            periode.setId(Integer.valueOf(tid.getText()));
            periode.setLibelle(tlib.getText());
            
            //execution
            if(Boite.showConfirmation("Supprimer?", "suppression")){
                if(dper.remove(periode) != null){
                    rafraichir();
                    Boite.showInformation(periode.getLibelle()+" supprimé(e)", "suppression", Alert.AlertType.INFORMATION);
                }
            }
        }
    }
    
    @FXML
    private void rafraichir(){
        Boite.vider(tid,tdeb,tfin,tlib);
        initList();
    }
    
    
    private void initList(){
        tlist.setItems(dper.list());
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

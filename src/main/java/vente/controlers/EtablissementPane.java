/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.controlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vente.beans.Boite;
import org.fieldnt.vente.beans.Etablissement;
import vente.controlers.event.CEvent;
import vente.donnees.DEtablissement;

/**
 *
 * @author NGEREZA
 */
public class EtablissementPane extends AnchorPane{

    @FXML
    private TextField tnom;
    
    private DEtablissement det =  new DEtablissement();
    private Etablissement et;
    
    private ObservableList<CEvent> events = FXCollections.observableArrayList();
    
    //fenetre
    private Stage st = new Stage(StageStyle.UTILITY);
    
    public EtablissementPane() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/org/fieldnt/vente/vues/etablissement.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            getStylesheets().add(getClass().getResource("/org/fieldnt/vente/styles/styles.css").toExternalForm());
            initVues();
            
            st.setScene(new Scene(this));
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    @FXML
    private void valider(){
        if(Boite.verifier(tnom)){
            
            et = new Etablissement();
            et.setId((int) System.currentTimeMillis()/100);
            et.setNom(tnom.getText());
            
            if(Boite.showConfirmation("Valider?", "Attention..")){
                
                if(det.list().size() > 0){
                    if(det.update(et) != null){
                        execute();
                    }
                }else{
                    if(det.add(et) != null){
                        execute();
                    }
                }
                
            }
            
        }
    }
    
    
    private void initVues(){
        if(det.list().size() > 0){
            tnom.setText(det.list().get(0).getNom());
        }
    }
    public void verifier(){
        if(det.list().size() > 0){
            execute();
        }else{
            show();
        }
    }
    
    
    public void addEvent(CEvent e){
        this.events.add(e);
    }
    private void execute(){
        events.forEach(e->{
            e.execute();
        });
        st.hide();
    }
    
    
    public void show(){
        if (st.isShowing()) {
            st.setAlwaysOnTop(true);
            st.setAlwaysOnTop(false);
        } else {
            st.show();
            st.setAlwaysOnTop(true);
            st.setAlwaysOnTop(false);
        }
    }
}

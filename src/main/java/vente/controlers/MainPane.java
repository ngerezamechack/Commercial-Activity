/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.controlers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import vente.beans.Boite;

/**
 *
 * @author NGEREZA
 */
public class MainPane extends BorderPane{
    
    private ActivityPane activitypane = new ActivityPane();
    private AboutPane aboutpane = new AboutPane();
    
    public MainPane(){
        FXMLLoader load = new FXMLLoader(getClass().getResource("/vente/vues/main.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            setCenter(activitypane);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    
    @FXML
    private void about(){
        aboutpane.show();
    }
    @FXML
    private void fermer(){
        if(Boite.showConfirmation("Quitter?", "Attention")){
            System.exit(0);
        }
    }
}

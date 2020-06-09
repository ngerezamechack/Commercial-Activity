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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vente.vues.Main;

/**
 *
 * @author NGEREZA
 */
public class AboutPane extends BorderPane{
    
    //fenetre
    private Stage st = new Stage(StageStyle.UTILITY);
    
    //constructeur pardefaut
    public AboutPane() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/org/fieldnt/vente/vues/about.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            
            st.setOnCloseRequest((e) -> {
                
            });
            st.setScene(new Scene(this));
            st.setResizable(false);
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    
    @FXML
    private void ouvrir(){
        Main.openurl("http://www.facebook.com/fieldnt");
    }
   
    
    //ouvrir la fenetre
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

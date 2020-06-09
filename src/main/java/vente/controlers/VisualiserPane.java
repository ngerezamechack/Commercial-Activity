/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.controlers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author NGEREZA
 */
public class VisualiserPane extends BorderPane{

    private Stage st = new Stage(StageStyle.UTILITY);
   
    
    
    public VisualiserPane() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/vente/vues/visualiser.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            st.setScene(new Scene(this));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    public void voir(JRViewerFX jv){
        this.setCenter(jv);
        
        st.showAndWait();
        st.setAlwaysOnTop(true);
        st.setAlwaysOnTop(false);
    }
}

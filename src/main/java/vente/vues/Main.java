/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.vues;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vente.beans.Boite;
import vente.controlers.EtablissementPane;
import vente.controlers.MainPane;

/**
 *
 * @author NGEREZA
 */
public class Main extends Application {
    
    private static HostServices hser;
    private static MainPane mp = new MainPane();
    private static EtablissementPane etp = new EtablissementPane();
    
    @Override
    public void start(Stage st) {
        hser = getHostServices();
        
        st.setScene(new Scene(mp));
        st.setOnCloseRequest((e)->{
            if(Boite.showConfirmation("Quitter?", "Attention..")){
                System.exit(0);
            }else{
                e.consume();
            }
        });
        
        etp.addEvent(()->{
            st.show();
        });
        etp.verifier();
        
        
    }

    public static void openrul(String url){
        hser.showDocument(url);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

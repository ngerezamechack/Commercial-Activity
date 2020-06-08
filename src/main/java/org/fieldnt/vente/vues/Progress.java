/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.vues;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author NGEREZA
 */
public class Progress extends BorderPane {

    @FXML
    private ProgressIndicator pg;
    private Scene sc;
    private Stage st = new Stage(StageStyle.TRANSPARENT);

    public Progress() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("progress.fxml"));
        load.setRoot(this);
        load.setController(this);

        try {
            load.load();
            sc = new Scene(this);
            st.setScene(sc);
            st.setAlwaysOnTop(true);
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }

    public ProgressIndicator getProgress() {
        return pg;
    }

    public Stage getStage() {
        return st;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.controlers.stats;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vente.beans.Activity;

/**
 *
 * @author NGEREZA
 */
public class Statistiques extends BorderPane{

    @FXML
    private PieChart chartjour,chartper;
    
    //fenetre
    private Stage st = new Stage(StageStyle.UTILITY);
    private Screen screen;
    
    private double total = 0.0;
    
    
    public Statistiques() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/vente/vues/stats/statistiques.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            st.setScene(new Scene(this));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    private void screen(){
        if(Screen.getScreens().size() > 1){
            screen = Screen.getScreens().get(1);
            
        }
    }
    
    
    
    public void show(ObservableList<PieChart.Data> jour,ObservableList<PieChart.Data> periode,Activity a){
        chartjour.getData().clear();
        chartper.getData().clear();
        
        chartjour.setData(jour);
        chartjour.setTitle("Statistiques du "+a.getDate());
        
        chartper.setData(periode);
        chartper.setTitle("Statistiques de la période de "+a.getPeriode().toString());
        
        pourcentageCharts(chartjour);
        pourcentageCharts(chartper);
        
        screen();
        
        if(st.isShowing()){
            st.setAlwaysOnTop(true);
            st.setAlwaysOnTop(false);
        }else{
            st.show();
            st.setAlwaysOnTop(true);
            st.setAlwaysOnTop(false);
        }
        
    }
    
    private void pourcentageCharts(PieChart pch){
        total = 0.0;
        //calcul du total
        pch.getData().forEach((PieChart.Data d) -> {
            total += d.getPieValue();
        });
        
        //calcul des poucentages
        pch.getData().forEach(d ->{
            double v = (d.getPieValue()/total) *100;
            String pourcentage = String.format("%.2f%%", v);
            Tooltip.install(d.getNode(), new Tooltip(pourcentage+" de "+ d.getName()));
        });
    }
}

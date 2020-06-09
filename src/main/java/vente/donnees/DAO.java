/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.donnees;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.PieChart;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import vente.beans.Boite;
import vente.controlers.VisualiserPane;
import vente.vues.Progress;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author NGEREZA
 * @param <T>
 */
public abstract class DAO<T> {
    
    protected ResultSet rs,result;
    protected String sql = "";
    
    protected T bean;
    protected ObservableList<T> beans;
    protected ObservableList<PieChart.Data> piedata;
    
    public abstract T add(T o);
    public abstract T update(T o);
    public abstract T get(T o);
    public abstract T remove(T o);
    
    public abstract ObservableList<T> list();
    public abstract ObservableList<T> list(T o);
    
    private PreparedStatement ps;
    protected PreparedStatement prepare(String sql, Object... o) throws SQLException{
        ps = NConnection.connect().prepareStatement(sql);
        for(int i = 0 ;i<o.length; i++){
            ps.setObject(i+1, o[i]);
        }
        return ps;
    }
    
    //les variables
    private JasperReport jsr;
    private JasperPrint jsp;
    
    private JRViewerFX jv;
    private Service<JRViewerFX> service;
    
    private Progress pg = new Progress();
    private VisualiserPane vp = new VisualiserPane();
    protected void print(JRResultSetDataSource r,URL url){
        //execution de la tache
        service = new Service<JRViewerFX>() {
            @Override
            protected Task<JRViewerFX> createTask() {
                return new Task<JRViewerFX>() {
                    @Override
                    protected JRViewerFX call() throws IOException, JRException  {
                        
                        jsr = JasperCompileManager.compileReport(url.openStream());
                        jsp = JasperFillManager.fillReport(jsr, null, r);
                        
                        return jv = new JRViewerFX(jsp);
                    }
                };
            }
        };
        pg.getProgress().progressProperty().bind(service.progressProperty());
        
        //event
        service.setOnScheduled((se) ->{
            pg.getStage().show();
        });
        service.setOnFailed((se) ->{
            pg.getStage().hide();
            Boite.showException(new Exception(service.getException()), "Service");
        });
        service.setOnSucceeded((se) ->{
            pg.getStage().hide();
            vp.voir(service.getValue());
        });
        
        service.start();
        
    }
}

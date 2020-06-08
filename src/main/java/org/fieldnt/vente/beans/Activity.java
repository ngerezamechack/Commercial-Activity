/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.beans;

import java.time.LocalDate;
import javafx.collections.ObservableList;
import org.fieldnt.vente.donnees.DDetail;
import org.fieldnt.vente.donnees.DPeriode;

/**
 *
 * @author NGEREZA
 */
public class Activity {

    private int numero;
    private LocalDate date;
    private double total;
    private String client;

    private int periode;
    private DPeriode dper = new DPeriode();
    
    private ObservableList<Detail> details;
    private DDetail ddt = new DDetail();

    public Activity() {
    }

    public Activity(int numero, LocalDate date, String client,double total, int periode) {
        this.numero = numero;
        this.date = date;
        this.total = total;
        this.client = client;
        this.periode = periode;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient() {
        return client;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public Periode getPeriode() {
        return dper.get(new Periode(periode, null, null, ""));
    }

    public ObservableList<Detail> getDetails() {
        for(Detail d : details){
            d.setActivity(numero);
        }
        return details;
    }

    public void setDetails(ObservableList<Detail> details) {
        this.details = details;
    }

    
    public ObservableList<Detail> details(){
        Detail dt = new Detail();
        dt.setActivity(numero);
        return ddt.list(dt);
    }
    
    
    
    @Override
    public String toString() {
        return numero + " -> " + client + "\n" + total;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Activity) {
            return this.numero == ((Activity) o).numero;
        }
        return false;
    }

}

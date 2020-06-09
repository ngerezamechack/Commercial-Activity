/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.beans;

import java.time.LocalDate;

/**
 *
 * @author NGEREZA
 */
public class Periode {
    private int id;
    private LocalDate debut;
    private LocalDate fin;
    private String libelle;

    public Periode() {
    }
    public Periode(int id, LocalDate debut, LocalDate fin, String libelle) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.libelle = libelle;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }
    public LocalDate getDebut() {
        return debut;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
    public LocalDate getFin() {
        return fin;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public String getLibelle() {
        return libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Periode){
            return this.id == ((Periode) o).id;
        }
        return false;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.beans;

/**
 *
 * @author NGEREZA
 */
public class Produit {
    private int id;
    private String nom;
    private double pu;

    public Produit() {
    }
    public Produit(int id, String nom, double pu) {
        this.id = id;
        this.nom = nom;
        this.pu = pu;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setNom(String libelle) {
        this.nom = libelle;
    }
    public String getNom() {
        return nom;
    }

    public void setPu(double pu) {
        this.pu = pu;
    }
    public double getPu() {
        return pu;
    }
    
    @Override
    public String toString() {
        return nom+" -> "+pu;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Produit){
            return this.id == ((Produit) o).id;
        }
        return false;
    }
    
}

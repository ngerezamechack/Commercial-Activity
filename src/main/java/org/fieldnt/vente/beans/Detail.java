/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.beans;

import org.fieldnt.vente.donnees.DProduit;

/**
 *
 * @author NGEREZA
 */
public class Detail {
    private int id;
    private double pu;
    private double qte;
    private double total;
    
    private int activity;
    private int produit;
    
    private DProduit dpr = new DProduit();

    public Detail() {
    }

    public Detail(int id, double pu, double qte, double total, int activity, int produit) {
        this.id = id;
        this.pu = pu;
        this.qte = qte;
        this.total = total;
        this.activity = activity;
        this.produit = produit;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setPu(double pu) {
        this.pu = pu;
    }
    public double getPu() {
        return pu;
    }

    public void setQte(double qte) {
        this.qte = qte;
    }
    public double getQte() {
        return qte;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public double getTotal() {
        return total;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }
    public int getActivity() {
        return activity;
    }

    public void setProduit(int produit) {
        this.produit = produit;
    }
    public Produit getProduit() {
        return dpr.get(new Produit(produit, "", 0));
    }
    
    
    
    
    
    
    @Override
    public String toString() {
        return getProduit().getNom() + " -> " + qte + " -> " + total;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Detail) {
            return this.id == ((Detail) o).id;
        }
        return false;
    }
}

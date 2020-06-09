/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.beans;

/**
 *
 * @author NGEREZA
 */
public class Etablissement {
    private int id;
    private String nom;

    public Etablissement() {
    }

    public Etablissement(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    @Override
    public String toString(){
        return nom;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Etablissement){
            return this.id == ((Etablissement) o).id;
        }
        return false;
    }
}

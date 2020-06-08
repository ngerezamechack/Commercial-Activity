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
public class Etablissement {
    private int id;
    private String nom;

    public Etablissement(){
    }
    
    public Etablissement(int id,String nom){
        this.id = id;
        this.nom = nom;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getNom(){
        return nom;
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

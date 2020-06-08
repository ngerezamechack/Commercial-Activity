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
}

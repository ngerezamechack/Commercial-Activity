/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.donnees;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vente.beans.Boite;
import vente.beans.Produit;

/**
 *
 * @author NGEREZA
 */
public class DProduit extends DAO<Produit>{

    @Override
    public Produit add(Produit o) {
        sql = "INSERT into produit (id_p,nom_p,pu_p) VALUES (?,?,?)";
        
        try{
            int r = prepare(sql, o.getId(),
                    o.getNom(),o.getPu()
            ).executeUpdate();
            return r > 0? o: null;
        }catch(Exception ex){
            Boite.showException(ex, "add");
        }
        return null;
    }

    @Override
    public Produit update(Produit o) {
        sql = "UPDATE produit "
                + "SET nom_p=?,pu_p=? "
                + "WHERE id_p = ?";
        
        try{
            int r = prepare(sql,
                    o.getNom(),o.getPu(),o.getId()
            ).executeUpdate();
            return r > 0? o: null;
        }catch(Exception ex){
            Boite.showException(ex, "add");
        }
        return null;}

    @Override
    public Produit get(Produit o) {
        sql = "SELECT * FROM produit WHERE id_p = ?";
        try{
            rs = prepare(sql, o.getId()).executeQuery();
            if(rs.next()){
                return o = new Produit(
                        rs.getInt("id_p"),  
                        rs.getString("nom_p"),
                        rs.getDouble("pu_p")
                );
            }
        }catch(Exception ex){
            Boite.showException(ex, "get");
        }
        return null;
    }

    @Override
    public Produit remove(Produit o) {
        sql = "DELETE FROM produit WHERE id_p = ?";
        
        try{
            int r = prepare(sql, o.getId()).executeUpdate();
            return o;
        }catch(Exception ex){
            Boite.showException(ex, "remove");
        }
        return null;
    }

    @Override
    public ObservableList<Produit> list() {
        sql = "SELECT * FROM produit ORDER BY nom_p";
        beans = FXCollections.observableArrayList();
        try{
            result = prepare(sql).executeQuery();
            while(result.next()){
                bean = new Produit(
                        result.getInt("id_p"), 
                        result.getString("nom_p"),
                        result.getDouble("pu_p")
                );
                
                beans.add(bean);
            }
        }catch(Exception ex){
            Boite.showException(ex, "list");
        }
        return beans;
    }

    @Override
    public ObservableList<Produit> list(Produit o) {
        sql = "SELECT * FROM produit "
                + "WHERE nom_p LIKE ? "
                + " ORDER BY nom_p";
        beans = FXCollections.observableArrayList();
        try{
            result = prepare(sql,"%"+o.getNom()+"%").executeQuery();
            while(result.next()){
                bean = new Produit(
                        result.getInt("id_p"), 
                        result.getString("nom_p"),
                        result.getDouble("pu_p")
                );
                
                beans.add(bean);
            }
        }catch(Exception ex){
            Boite.showException(ex, "list()");
        }
        return beans;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.donnees;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vente.beans.Boite;
import org.fieldnt.vente.beans.Etablissement;

/**
 *
 * @author NGEREZA
 */
public class DEtablissement extends DAO<Etablissement>{

    @Override
    public Etablissement add(Etablissement o) {
        sql = "INSERT into etablissement (id_et,nom_et) VALUES (?,?)";
        
        try{
            int r = prepare(sql, o.getId(),o.getNom()).executeUpdate();
            return r > 0? o: null;
        }catch(Exception ex){
            Boite.showException(ex, "d_et add");
        }
        return null;
    }

    @Override
    public Etablissement update(Etablissement o) {
        sql = "UPDATE etablissement "
                + "SET nom_et=? ";
        
        try{
            int r = prepare(sql,o.getNom()).executeUpdate();
            return r > 0 ? o : null;
        }catch(Exception ex){
            Boite.showException(ex, "update");
        }
        return null;
    }

    @Override
    public Etablissement get(Etablissement o) {
        sql = "SELECT * FROM etablissement WHERE id_et = ?";
        try{
            rs = prepare(sql, o.getId()).executeQuery();
            if(rs.next()){
                return o = new Etablissement(
                        rs.getInt("id_et"), 
                        rs.getString("nom_et")
                );
            }
        }catch(Exception ex){
            Boite.showException(ex, "get");
        }
        return null;
    }

    @Override
    public Etablissement remove(Etablissement o) {
        sql = "DELETE FROM etablissement";
        
        try{
            int r = prepare(sql).executeUpdate();
            return o;
        }catch(Exception ex){
            Boite.showException(ex, "remove");
        }
        return null;
    }

    @Override
    public ObservableList<Etablissement> list() {
        sql = "SELECT * FROM etablissement";
        beans = FXCollections.observableArrayList();
        try{
            result = prepare(sql).executeQuery();
            while(result.next()){
                bean = new Etablissement(
                        result.getInt("id_et"), 
                        result.getString("nom_et")
                );
                
                beans.add(bean);
            }
        }catch(Exception ex){
            Boite.showException(ex, "list");
        }
        return beans;
    }

    @Override
    public ObservableList<Etablissement> list(Etablissement o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

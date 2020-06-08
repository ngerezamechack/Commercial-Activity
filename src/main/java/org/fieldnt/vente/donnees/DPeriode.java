/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.donnees;

import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fieldnt.vente.beans.Boite;
import org.fieldnt.vente.beans.Periode;

/**
 *
 * @author NGEREZA
 */
public class DPeriode extends DAO<Periode>{

    @Override
    public Periode add(Periode o) {
        sql = "INSERT into periode (id_pr,debut,fin,lib_pr) VALUES (?,?,?,?)";
        
        try{
            int r = prepare(sql, o.getId(),
                    Date.valueOf(o.getDebut()),
                    Date.valueOf(o.getFin()),
                    o.getLibelle()).executeUpdate();
            return r > 0? o: null;
        }catch(Exception ex){
            Boite.showException(ex, "add");
        }
        return null;
    }

    @Override
    public Periode update(Periode o) {
        sql = "UPDATE periode "
                + "SET debut=?,fin=?,lib_pr=? "
                + "WHERE id_pr=?";
        
        try{
            int r = prepare(sql,
                    Date.valueOf(o.getDebut()),
                    Date.valueOf(o.getFin()),
                    o.getLibelle(),
                    o.getId()).executeUpdate();
            return r > 0 ? o : null;
        }catch(Exception ex){
            Boite.showException(ex, "update");
        }
        return null;
    }

    @Override
    public Periode get(Periode o) {
        sql = "SELECT * FROM periode WHERE id_pr = ?";
        try{
            rs = prepare(sql, o.getId()).executeQuery();
            if(rs.next()){
                return o = new Periode(
                        rs.getInt("id_pr"), 
                        rs.getDate("debut").toLocalDate(), 
                        rs.getDate("fin").toLocalDate(), 
                        rs.getString("lib_pr")
                );
            }
        }catch(Exception ex){
            Boite.showException(ex, "get");
        }
        return null;
    }

    @Override
    public Periode remove(Periode o) {
        sql = "DELETE FROM periode WHERE id_pr = ?";
        
        try{
            int r = prepare(sql, o.getId()).executeUpdate();
            return o;
        }catch(Exception ex){
            Boite.showException(ex, "remove");
        }
        return null;
    }

    @Override
    public ObservableList<Periode> list() {
        sql = "SELECT * FROM periode ORDER BY id_pr DESC";
        beans = FXCollections.observableArrayList();
        try{
            result = prepare(sql).executeQuery();
            while(result.next()){
                bean = new Periode(
                        result.getInt("id_pr"), 
                        result.getDate("debut").toLocalDate(), 
                        result.getDate("fin").toLocalDate(), 
                        result.getString("lib_pr")
                );
                
                beans.add(bean);
            }
        }catch(Exception ex){
            Boite.showException(ex, "list");
        }
        return beans;
    }

    @Override
    public ObservableList<Periode> list(Periode o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

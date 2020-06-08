/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.donnees;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fieldnt.vente.beans.Boite;
import org.fieldnt.vente.beans.Detail;

/**
 *
 * @author NGEREZA
 */
public class DDetail extends DAO<Detail>{

    @Override
    public Detail add(Detail o) {
        sql = "INSERT into detail (id_d,pu_d,qte_d,total_d,num_a,id_p) VALUES (?,?,?,?,?,?)";
        
        try{
            int r = prepare(sql, 
                    o.getId(),o.getPu(),o.getQte(),o.getTotal(),o.getActivity(),o.getProduit().getId()
            ).executeUpdate();
            
            return r > 0? o: null;
        }catch(Exception ex){
            Boite.showException(ex, "add");
        }
        return null;
    }

    @Override
    public Detail update(Detail o) {
        return null;
    }

    @Override
    public Detail get(Detail o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Detail remove(Detail o) {
        sql = "DELETE FROM detail WHERE num_a = ? ";
        
        try{
            int r = prepare(sql, o.getActivity()).executeUpdate();
            return r > 0 ? o: null;
        }catch(Exception ex){
            Boite.showException(ex, "remove");
        }
        return null;
    }

    @Override
    public ObservableList<Detail> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Detail> list(Detail o) {
        sql = "SELECT * FROM detail WHERE num_a = ?";
        beans = FXCollections.observableArrayList();
        try{
            result = prepare(sql ,o.getActivity()).executeQuery();
            while(result.next()){
                bean = new Detail(
                        result.getInt("id_d"),
                        result.getDouble("pu_d"),
                        result.getDouble("qte_d"),
                        result.getDouble("total_d"),
                        result.getInt("num_a"),
                        result.getInt("id_p")
                );
                
                beans.add(bean);
            }
        }catch(Exception ex){
            Boite.showException(ex, "list()");
        }
        return beans;
    }
    
}

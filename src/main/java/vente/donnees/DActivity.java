/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.donnees;

import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import vente.beans.Activity;
import vente.beans.Boite;
import vente.beans.Detail;
import vente.beans.Produit;

/**
 *
 * @author NGEREZA
 */
public class DActivity extends DAO<Activity> {

    private DDetail ddt = new DDetail();
    private DProduit dpro = new DProduit();

    //ajout d'une activité
    @Override
    public Activity add(Activity o) {
        sql = "INSERT into activity (num_a,date_a,client_a,total_a,id_pr) VALUES (?,?,?,?,?)";

        try {
            int r = prepare(sql, o.getNumero(), Date.valueOf(o.getDate()),
                    o.getClient(), o.getTotal(), o.getPeriode().getId()
            ).executeUpdate();

            //insertion des details
            for (Detail d : o.getDetails()) {
                if (ddt.add(d) == null) {
                    return null;
                }
            }

            return r > 0 ? o : null;
        } catch (Exception ex) {

            Boite.showException(ex, "add");
        }
        return null;
    }

    //modification d'une activité
    @Override
    public Activity update(Activity o) {

        if (get(o) != null) {
            if (remove(o) != null) {
                return add(o);
            }
        } else {
            return add(o);
        }
        return null;
    }

    //recuperation
    @Override
    public Activity get(Activity o) {
        sql = "SELECT * FROM activity WHERE num_a = ?";
        try {
            rs = prepare(sql, o.getNumero()).executeQuery();
            if (rs.next()) {
                return o = new Activity(
                        rs.getInt("num_a"),
                        rs.getDate("date_a").toLocalDate(),
                        rs.getString("client_a"),
                        rs.getDouble("total_a"),
                        rs.getInt("id_pr")
                );
            }
        } catch (Exception ex) {
            Boite.showException(ex, "get");
        }
        return null;
    }

    //suppression
    @Override
    public Activity remove(Activity o) {
        sql = "DELETE FROM activity WHERE num_a = ? ";

        try {
            int r = prepare(sql, o.getNumero()).executeUpdate();
            //suppression des details
            Detail d = new Detail();
            d.setActivity(o.getNumero());
            if (ddt.remove(d) == null) {
                return null;
            }

            return r > 0 ? o : null;
        } catch (Exception ex) {
            Boite.showException(ex, "remove");
        }
        return null;
    }

    @Override
    public ObservableList<Activity> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //list d'activité
    @Override
    public ObservableList<Activity> list(Activity o) {
        sql = "SELECT * FROM activity WHERE date_a = ? ";
        beans = FXCollections.observableArrayList();
        try {
            result = prepare(sql, Date.valueOf(o.getDate())).executeQuery();
            while (result.next()) {
                bean = new Activity(
                        result.getInt("num_a"),
                        result.getDate("date_a").toLocalDate(),
                        result.getString("client_a"),
                        result.getDouble("total_a"),
                        result.getInt("id_pr")
                );

                beans.add(bean);
            }
        } catch (Exception ex) {
            Boite.showException(ex, "activity list()");
        }
        return beans;
    }

    //calculer les statistiques journalières
    public ObservableList<PieChart.Data> getStatjour(Activity o) {

        piedata = FXCollections.observableArrayList();
        for (Produit p : dpro.list()) {

            sql = "SELECT sum(detail.total_d) AS t "
                    + "FROM activity,detail "
                    + "WHERE activity.date_a = ? AND "
                    + " detail.num_a = activity.num_a AND "
                    + "detail.id_p = ?";
            try {
                rs = prepare(sql, Date.valueOf(o.getDate()), p.getId()).executeQuery();
                if (rs.next()) {
                    piedata.add(new PieChart.Data(p.getNom(), rs.getDouble("t")));
                }

            } catch (Exception ex) {
                Boite.showException(ex, "dactivity stat jour");
            }

        }
        return piedata;
    }

    //calculer les statistiques journalières
    public ObservableList<PieChart.Data> getStatperiode(Activity o) {

        piedata = FXCollections.observableArrayList();
        for (Produit p : dpro.list()) {

            sql = "SELECT sum(detail.total_d) AS t "
                    + "FROM activity,detail "
                    + "WHERE activity.id_pr = ? AND "
                    + " detail.num_a = activity.num_a AND "
                    + "detail.id_p = ?";
            try {
                result = prepare(sql, o.getPeriode().getId(), p.getId()).executeQuery();
                if (result.next()) {
                    piedata.add(new PieChart.Data(p.getNom(), result.getDouble("t")));
                }

            } catch (Exception ex) {
                Boite.showException(ex, "dactivity stat periode");
            }

        }
        return piedata;
    }

    //IMPRESSION DE LA FACTURE
    public void facture(Activity o) {
        if (Boite.showConfirmation("Afficher la facture?", "Attention..")) {
            sql = "SELECT * \n"
                    + "FROM etablissement,activity,detail,produit\n"
                    + "WHERE activity.num_a = ? \n"
                    + "AND detail.num_a = activity.num_a \n"
                    + "AND produit.id_p = detail.id_p ";
            try {
                rs = prepare(sql, o.getNumero()).executeQuery();
                print(
                        new JRResultSetDataSource(rs),
                        getClass().getResource("/org/fieldnt/vente/rapports/facture.jrxml/")
                );
            } catch (Exception ex) {
                Boite.showException(ex, "facture");
            }
        }
    }

    //IMPRESSION DU RAPPORT JOURNALIER
    public void rapportJour(Activity o) {
        if (Boite.showConfirmation("Afficher le rapport?", "Attention..")) {
            sql = "SELECT *\n"
                    + "FROM etablissement,activity\n"
                    + "WHERE date_a = ? \n"
                    + "ORDER BY date_a DESC";
            try {
                rs = prepare(sql, Date.valueOf(o.getDate())).executeQuery();
                print(
                        new JRResultSetDataSource(rs),
                        getClass().getResource("/org/fieldnt/vente/rapports/jour.jrxml/")
                );
            } catch (Exception ex) {
                Boite.showException(ex, "rapport jour");
            }
        }
    }

    //IMPRESSION DU RAPPORT PERIODIQUE
    public void rapportPeriode(Activity o) {
        if (Boite.showConfirmation("Afficher le rapport?", "Attention..")) {
            sql = "SELECT *\n"
                    + "FROM etablissement,periode,activity\n"
                    + "WHERE periode.id_pr = ? \n"
                    + "AND activity.id_pr = periode.id_pr\n"
                    + "ORDER BY date_a DESC";
            try {
                rs = prepare(sql, o.getPeriode().getId()).executeQuery();
                print(
                        new JRResultSetDataSource(rs),
                        getClass().getResource("/org/fieldnt/vente/rapports/periode.jrxml/")
                );
            } catch (Exception ex) {
                Boite.showException(ex, "rapport periode");
            }
        }
    }
}

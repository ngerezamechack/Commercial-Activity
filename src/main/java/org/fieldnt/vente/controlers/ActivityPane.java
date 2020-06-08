/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fieldnt.vente.controlers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.fieldnt.vente.beans.Activity;
import org.fieldnt.vente.beans.Boite;
import org.fieldnt.vente.beans.Detail;
import org.fieldnt.vente.beans.Periode;
import org.fieldnt.vente.beans.Produit;
import org.fieldnt.vente.controlers.stats.Statistiques;
import org.fieldnt.vente.donnees.DActivity;
import org.fieldnt.vente.donnees.NConnection;

/**
 *
 * @author NGEREZA
 */
public class ActivityPane extends BorderPane {

    /**
     * ****************** PRODUITS ************************
     */
    @FXML
    private TextField tpu, tqte, ttotal, tchproduit;
    @FXML
    private ListView<Produit> tlistproduit;
    private ProduitPane produitpane;

    /**
     * ****************** PERIODE ************************
     */
    @FXML
    private ComboBox<Periode> tper;
    private PeriodePane periodepane;

    /**
     * ************************* Details **********************
     */
    @FXML
    private ListView<Detail> tlistdetail;
    private Detail dtl;

    /**
     * ******************************** ACTIVITE
     * ************************************
     */
    @FXML
    private TextField tnum, ttotal_a, tclient;
    @FXML
    private DatePicker tdate;
    @FXML
    private ListView<Activity> tlistactivity;

    private Activity activity;
    private DActivity dac = new DActivity();

    public ActivityPane() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/org/fieldnt/vente/vues/activity.fxml"));
        load.setRoot(this);
        load.setController(this);

        try {
            load.load();
            getStylesheets().add(getClass().getResource("/org/fieldnt/vente/styles/styles.css").toExternalForm());
            
             
            produitpane = new ProduitPane(tlistproduit, tpu, tqte, ttotal, tchproduit);
            periodepane = new PeriodePane(tper);
            initVues();
            vidertout();

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * *********************** LE METHODES DETAIL
     * ************************************
     */
    //ajouter un detail
    @FXML
    private void ajouterDetail() {
        if (Boite.verifier(tpu, tqte, ttotal)) {

            dtl = new Detail();
            dtl.setId((int) System.currentTimeMillis() / 100);
            dtl.setProduit(tlistproduit.getSelectionModel().getSelectedItem().getId());
            dtl.setPu(Double.valueOf(tpu.getText()));
            dtl.setQte(Double.valueOf(tqte.getText()));
            dtl.setTotal(Double.valueOf(ttotal.getText()));

            if (!detailExists(dtl)) {
                tlistdetail.getItems().add(dtl);
                tlistdetail.scrollTo(dtl);
                total();
                viderProduit();
            }else{
                Boite.showInformation("produit/Service exitant!", "Erreur", Alert.AlertType.ERROR);
            }
        }
    }

    //enlever un detail
    @FXML
    private void enleverDetail() {
        if ((dtl = tlistdetail.getSelectionModel().getSelectedItem()) != null) {
            if (Boite.showConfirmation("Enlever " + dtl.getProduit().getNom() + " ?", "Attention..")) {
                tlistdetail.getItems().remove(dtl);
            }
        }
    }

    //verifier si un produit existe
    private boolean detailExists(Detail dt) {

        for (Detail d : tlistdetail.getItems()) {
            if (d.getProduit().equals(dt.getProduit())) {
                return true;
            }
        }

        return false;
    }

    //actualiser le produit
    @FXML
    private void viderProduit() {
        Boite.vider(tpu, tqte, ttotal, tlistproduit);
    }

    /**
     * **************** ****** ACTIVITE ***********************************
     */
    @FXML
    private void valider() {
        if (Boite.verifier(tdate, ttotal_a, tclient, tper)) {

            //initialisation
            activity = new Activity();
            if (tnum.getText().isBlank()) {
                activity.setNumero((int) System.currentTimeMillis() / 100);
            } else {
                activity.setNumero(Integer.valueOf(tnum.getText()));
            }

            activity.setDate(tdate.getValue());
            activity.setClient(tclient.getText());
            activity.setTotal(Double.valueOf(ttotal_a.getText()));
            activity.setPeriode(tper.getSelectionModel().getSelectedItem().getId());
            activity.setDetails(tlistdetail.getItems());

            //execution
            if (Boite.showConfirmation("Valider?", "Validation..")) {

                NConnection.autoCommit(false);

                if (dac.update(activity) != null) {
                    Boite.showInformation("Effectué!", "Validation", Alert.AlertType.INFORMATION);
                    dac.facture(activity);
                    vidertout();
                    NConnection.commit();
                } else {
                    NConnection.rollBack();
                }

                NConnection.autoCommit(true);
            }
        }
    }

    @FXML
    private void getActivity() {
        if ((activity = tlistactivity.getSelectionModel().getSelectedItem()) != null) {
            tnum.setText(String.valueOf(activity.getNumero()));
            tdate.setValue(activity.getDate());
            ttotal_a.setText(String.valueOf(activity.getTotal()));
            tclient.setText(activity.getClient());
            tper.getSelectionModel().select(activity.getPeriode());

            tlistdetail.setItems(activity.details());
        }
    }

    @FXML
    private void delActivity() {
        if ((activity = tlistactivity.getSelectionModel().getSelectedItem()) != null) {

            //execution
            if (Boite.showConfirmation("Supprimer?", "Validation..")) {
                NConnection.autoCommit(false);
                if (dac.remove(activity) != null) {
                    Boite.showInformation("Effectué!", "Validation", Alert.AlertType.INFORMATION);
                    vidertout();
                    NConnection.commit();
                } else {
                    NConnection.rollBack();
                }
                NConnection.autoCommit(true);
            }

        } else {
            Boite.showInformation("Coisir une activité!", "Erreur", Alert.AlertType.ERROR);
        }
    }

    private void total() {
        double total = 0;
        for (Detail d : tlistdetail.getItems()) {
            total += d.getTotal();
        }
        ttotal_a.setText(String.valueOf(total));
    }

    @FXML
    private void vidertout() {
        Boite.vider(tnum, tdate, tclient, ttotal_a, tper);

        tlistdetail.getItems().clear();
        viderProduit();

        activity = new Activity();
        activity.setDate(tdate.getValue());
        tlistactivity.setItems(dac.list(activity));
    }

    private void initVues() {
        tdate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                activity = new Activity();
                activity.setDate(tdate.getValue());
                tlistactivity.setItems(dac.list(activity));
            }
        });
    }

    /**
     * *********************ouvrir d'autre fenetre *********************
     */
    @FXML
    private void openProduit() {
        produitpane.show();
    }

    @FXML
    private void openPeriode() {
        periodepane.show();
    }

    /********************** LES STATISTIQUES *******************************/
    private Statistiques statistiques = new Statistiques();
    
    @FXML
    private void statjour(){
        if(Boite.verifier(tdate,tper)){
            activity = new Activity();
            activity.setDate(tdate.getValue());
            activity.setPeriode(tper.getSelectionModel().getSelectedItem().getId());
            statistiques.show(dac.getStatjour(activity), dac.getStatperiode(activity),activity);
        }
    }
    @FXML
    private void rapportJour(){
        if(Boite.verifier(tdate)){
            activity = new Activity();
            activity.setDate(tdate.getValue());
            dac.rapportJour(activity);
        }
    }
    
    @FXML
    private void rapportPeriode(){
        if(Boite.verifier(tper)){
            activity = new Activity();
            activity.setPeriode(tper.getSelectionModel().getSelectedItem().getId());
            dac.rapportPeriode(activity);
        }
    }
}

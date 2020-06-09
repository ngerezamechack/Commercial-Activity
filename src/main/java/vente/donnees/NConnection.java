/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vente.donnees;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import vente.beans.Boite;

/**
 *
 * @author NGEREZA
 */
public class NConnection {
    
    private static Connection con = null;
    private static String url = "jdbc:sqlite:"+System.getProperty("user.home")+File.separator+"commerce.db";
    
    
    
    
    public static Connection connect() throws SQLException{
        
        if(con == null){
            try {
                Class.forName(org.sqlite.JDBC.class.getName());
            } catch (ClassNotFoundException ex) {
                Boite.showException(ex, "");
            }
            con = DriverManager.getConnection(url);
            createTable();
        }
        
        return con;
    }
    
    
    
    
    public static void autoCommit(boolean b){
        try {
            connect().setAutoCommit(b);
        } catch (SQLException ex) {
            Boite.showException(ex, "autocommit");
        }
    }
    
    
    public static void rollBack(){
        try {
            connect().rollback();
        } catch (SQLException ex) {
            Boite.showException(ex, "roolback");
        }
    }
    
    
    public static void commit(){
        try {
            connect().commit();
        } catch (SQLException ex) {
            Boite.showException(ex, "commit");
        }
    }
    
   
    
    private static void createTable() throws SQLException{
        int r = con.createStatement().executeUpdate(sql);
    }
    
    
    
    
    private static final String sql = "CREATE TABLE IF NOT EXISTS etablissement\n" +
"(\n" +
"    id_et integer PRIMARY KEY NOT NULL,\n" +
"    nom_et VARCHAR (100) NOT NULL\n" +
");\n" +
"\n" +
"CREATE TABLE IF NOT EXISTS periode\n" +
"(\n" +
"    id_pr integer PRIMARY KEY NOT NULL,\n" +
"    debut DATE NOT NULL,\n" +
"    fin DATE NOT NULL,\n" +
"    lib_pr VARCHAR (100)\n" +
");\n" +
"\n" +
"CREATE TABLE IF NOT EXISTS produit\n" +
"(\n" +
"    id_p integer PRIMARY KEY NOT NULL,\n" +
"    nom_p VARCHAR(30) NOT NULL,\n" +
"    pu_p REAL NOT NULL\n" +
");\n" +
"\n" +
"CREATE TABLE IF NOT EXISTS activity\n" +
"(\n" +
"    num_a integer PRIMARY KEY NOT NULL,\n" +
"    date_a date NOT NULL,\n" +
"    client_a VARCHAR(50),\n" +
"    total_a REAL NOT NULL,\n" +
"    id_pr integer NOT NULL,\n" +
"    CONSTRAINT fk_pr FOREIGN KEY (id_pr) REFERENCES periode (id_pr)\n" +
");\n" +
"\n" +
"CREATE TABLE IF NOT EXISTS detail\n" +
"(\n" +
"    id_d integer PRIMARY KEY NOT NULL,\n" +
"    pu_d double precision NOT NULL,\n" +
"    qte_d REAL NOT NULL,\n" +
"    total_d REAL NOT NULL,\n" +
"    num_a integer NOT NULL,\n" +
"    id_p integer NOT NULL,\n" +
"    CONSTRAINT fk_a FOREIGN KEY (num_a) REFERENCES activity (num_a) MATCH SIMPLE\n" +
"        ON UPDATE CASCADE\n" +
"        ON DELETE CASCADE,\n" +
"    CONSTRAINT fk_p FOREIGN KEY (id_p) REFERENCES produit (id_p) \n" +
");";
    
}

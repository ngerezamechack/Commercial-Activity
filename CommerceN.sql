/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  NGEREZA
 * Created: 31 mai 2020
 */
CREATE TABLE IF NOT EXISTS etablissement
(
    id_et integer PRIMARY KEY NOT NULL,
    nom_et VARCHAR (100) NOT NULL
);

CREATE TABLE IF NOT EXISTS periode
(
    id_pr integer PRIMARY KEY NOT NULL,
    debut DATE NOT NULL,
    fin DATE NOT NULL,
    lib_pr VARCHAR (100)
);

CREATE TABLE IF NOT EXISTS produit
(
    id_p integer PRIMARY KEY NOT NULL,
    nom_p VARCHAR(30) NOT NULL,
    pu_p REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS activity
(
    num_a integer PRIMARY KEY NOT NULL,
    date_a date NOT NULL,
    client_a VARCHAR(50),
    total_a REAL NOT NULL,
    id_pr integer NOT NULL,
    CONSTRAINT fk_pr FOREIGN KEY (id_pr) REFERENCES periode (id_pr)
);

CREATE TABLE IF NOT EXISTS detail
(
    id_d integer PRIMARY KEY NOT NULL,
    pu_d double precision NOT NULL,
    qte_d REAL NOT NULL,
    total_d REAL NOT NULL,
    num_a integer NOT NULL,
    id_p integer NOT NULL,
    CONSTRAINT fk_a FOREIGN KEY (num_a) REFERENCES activity (num_a) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_p FOREIGN KEY (id_p) REFERENCES produit (id_p) 
);


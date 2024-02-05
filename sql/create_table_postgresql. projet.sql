-- En tant que user postgres connecté à la pabse postgres
create database tst;
create user tst password 'tst';
grant all privileges on database tst to tst;

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- Création de la table des Joueurs
CREATE TABLE joueur
(
    idJoueur     INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nomJoueur    VARCHAR(70) NOT NULL,
    prenomJoueur VARCHAR(70) NOT NULL

)
;

-- Création de la table Jouer

CREATE TABLE jouer
(
    idJoueur   INT,
    codePartie CHAR(4)
)
;

-- Création de la table des Parties
CREATE TABLE partie
(
    codePartie CHAR(4) PRIMARY KEY
)
;

ALTER TABLE jouer
    ADD CONSTRAINT FKPartieJouer FOREIGN KEY (codePartie) REFERENCES partie (codePartie);
ALTER TABLE jouer
    ADD CONSTRAINT FKJoueurJouer FOREIGN KEY (idJoueur) REFERENCES joueur (idJoueur);
CREATE UNIQUE INDEX UXJoueurNomPrenom ON joueur (nomJoueur, prenomJoueur);

grant all privileges on all tables in schema public to tst;
grant all privileges on all sequences in schema public to tst;
grant all privileges on schema public to tst;


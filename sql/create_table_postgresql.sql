-- En tant que user postgres connecté à la pabse postgres
create database tst;
create user tst password 'tst';
grant all privileges on database tst to tst;

-- en tant que user postgres connecté à la base tst
-- Création de la table des utilisateurs
CREATE TABLE utilisateur
(
    id       INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nom      VARCHAR(20) NOT NULL,
    login    VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL
)
grant all privileges on all tables in schema public to tst;
grant all privileges on all sequences in schema public to tst;

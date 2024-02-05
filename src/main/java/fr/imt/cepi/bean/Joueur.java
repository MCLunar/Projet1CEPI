package fr.imt.cepi.bean;

import java.io.Serializable;

public class Joueur implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	private int id;
	private final String nom;
	private final String prenom;



	public Joueur(String nom, String prenom, int id) {
		this.nom = nom;
		this.prenom = prenom;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}



	@Override
	public String toString() {
		return "Joueur [id=" + id + ", nom=" + nom + ", login=" + prenom + "]";
	}

}

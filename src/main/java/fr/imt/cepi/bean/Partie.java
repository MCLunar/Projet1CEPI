package fr.imt.cepi.bean;
import java.util.ArrayList ;


public class Partie
{

    private String codePartie;
    private final ArrayList<Joueur> listeJoueur;

    public Partie(String code, Joueur joueur) {
        this.codePartie = code;
        this.listeJoueur = new ArrayList() ;

        this.listeJoueur.add(joueur);
    }


}

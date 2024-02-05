package fr.imt.cepi.bean;
import java.util.Random;
public class Code
{
    /**
     * Attributs : tableau de taille 4 d'entier
     */
    public String code4Chiffres;

    /**
     * Constructeur
     * Construit un tableau de taille 4 avec dans chacune de ses cases un chiffre
     */
    public Code()
    {
        Random rd = new Random();
        code4Chiffres = String.format("%04d",rd.nextInt(9999) + 1);

    }

    @Override
    public String toString()
    {
        return  code4Chiffres;
    }

}

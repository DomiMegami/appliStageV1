package modeles;

/**
 * Created by Dominique DURI on 01/02/2018.
 */

public class MotCle {

    private int mot_cle_id;
    private String mot_cle_nom;
    private int mot_cle_categorie_id;
    private int mot_cle_liste;

    public MotCle() {
    }

    public MotCle(String mot_cle_nom) {
        this.mot_cle_nom = mot_cle_nom;
    }

    public MotCle(int mot_cle_id, String mot_cle_nom, int mot_cle_categorie_id, int mot_cle_liste) {
        this.mot_cle_id = mot_cle_id;
        this.mot_cle_nom = mot_cle_nom;
        this.mot_cle_categorie_id = mot_cle_categorie_id;
        this.mot_cle_liste = mot_cle_liste;
    }

    public int getMot_cle_id() {
        return mot_cle_id;
    }

    public void setMot_cle_id(int mot_cle_id) {
        this.mot_cle_id = mot_cle_id;
    }

    public String getMot_cle_nom() {
        return mot_cle_nom;
    }

    public void setMot_cle_nom(String mot_cle_nom) {
        this.mot_cle_nom = mot_cle_nom;
    }

    public int getMot_cle_categorie_id() {
        return mot_cle_categorie_id;
    }

    public void setMot_cle_categorie_id(int mot_cle_categorie_id) {
        this.mot_cle_categorie_id = mot_cle_categorie_id;
    }

    public int getMot_cle_liste() {
        return mot_cle_liste;
    }

    public void setMot_cle_liste(int mot_cle_liste) {
        this.mot_cle_liste = mot_cle_liste;
    }
}

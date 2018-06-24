package modeles;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dominique DURI on 15/02/2018.
 */

public class User implements Serializable {


    private int id;
    private String pseudo;
    private String email;
    private String motDePasse;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private String telephone;
    private ArrayList<Integer> listePreferencesId;
    private ArrayList<String> listeNomCategoriesPreferees;


    public User() {
    }

    public User(String pseudo, String email, String motDePasse, ArrayList<Integer> listePreferencesId) {
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
        this.listePreferencesId = listePreferencesId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public ArrayList<Integer> getListePreferencesId() {
        return listePreferencesId;
    }

    public void setListePreferencesId(ArrayList<Integer> listePreferencesId) {
        this.listePreferencesId = listePreferencesId;
    }

    public ArrayList<String> getListeNomCategoriesPreferees() {
        return listeNomCategoriesPreferees;
    }

    public void setListeNomCategoriesPreferees(ArrayList<String> listeNomCategoriesPreferees) {
        this.listeNomCategoriesPreferees = listeNomCategoriesPreferees;
    }
}
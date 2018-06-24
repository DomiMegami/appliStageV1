package modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dominique DURI on 12/01/2018.
 */

public class Article implements Serializable {

    private int id;
    private String date_publication;
    private String titre;
    private String chapo;
    private String contenu;
    private String date_debut;
    private String date_fin;
    private String horaire;
    private double prix;
    private String url_site_web;
    private String url_facebook;
    private String contact_nom;
    private String contact_adresse;
    private String contact_commune;
    private String url_twitter;
    private String email_evenement;
    private String url_pdf;
    private String telephone;
    private String url_youtube;
    private String url_image;
    private String categorie;


    private HashMap<Integer, String> mots_cle_liste_hm = new HashMap<Integer, String>();
    public HashMap<Integer, String> getMots_cle_liste_hm() {
        return mots_cle_liste_hm;
    }

    public void setMots_cle_liste_hm(HashMap<Integer, String> mots_cle_liste_hm) {
        this.mots_cle_liste_hm = mots_cle_liste_hm;
    }


    public String getUrl_twitter() {
        return url_twitter;
    }

    public void setUrl_twitter(String url_twitter) {
        this.url_twitter = url_twitter;
    }

    public String getEmail_evenement() {
        return email_evenement;
    }

    public void setEmail_evenement(String email_evenement) {
        this.email_evenement = email_evenement;
    }



    public Article() {
    }


    public Article(int id, String date_publication, String titre, String chapo, String contenu, String date_debut, String date_fin, String horaire, double prix, String url_site_web, String url_facebook, String url_pdf, String telephone, String url_youtube, String url_image, String categorie) {
        this.id = id;
        this.date_publication = date_publication;
        this.titre = titre;
        this.chapo = chapo;
        this.contenu = contenu;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.horaire = horaire;
        this.prix = prix;
        this.url_site_web = url_site_web;
        this.url_facebook = url_facebook;
        this.url_pdf = url_pdf;
        this.telephone = telephone;
        this.url_youtube = url_youtube;
        this.url_image = url_image;
        this.categorie = categorie;

    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(String date_publication) {
        this.date_publication = date_publication;
    }

    public String getChapo() {
        return chapo;
    }

    public void setChapo(String chapo) {
        this.chapo = chapo;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getUrl_site_web() {
        return url_site_web;
    }

    public void setUrl_site_web(String url_site_web) {
        this.url_site_web = url_site_web;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }

    public void setUrl_pdf(String url_pdf) {
        this.url_pdf = url_pdf;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUrl_youtube() {
        return url_youtube;
    }

    public void setUrl_youtube(String url_youtube) {
        this.url_youtube = url_youtube;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getUrl_facebook() {
        return url_facebook;
    }

    public void setUrl_facebook(String url_facebook) {
        this.url_facebook = url_facebook;
    }

    public String getContact_nom() {
        return contact_nom;
    }

    public void setContact_nom(String contact_nom) {
        this.contact_nom = contact_nom;
    }

    public String getContact_adresse() {
        return contact_adresse;
    }

    public void setContact_adresse(String contact_adresse) {
        this.contact_adresse = contact_adresse;
    }

    public String getContact_commune() {
        return contact_commune;
    }

    public void setContact_commune(String contact_commune) {
        this.contact_commune = contact_commune;
    }
}

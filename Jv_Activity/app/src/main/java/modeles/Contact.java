package modeles;

/**
 * Created by Dominique DURI on 01/02/2018.
 */

public class Contact {

    private int contact_id;
    private String contact_nom;
    private String contact_tel;
    private String contact_mail;
    private String contact_adresse;
    private String contact_commune;
    private String contact_facebook;
    private String contact_twitter;
    private String contact_site;


    public Contact() {
    }

    public Contact(int contact_id, String contact_nom, String contact_tel, String contact_mail, String contact_adresse, String contact_commune, String contact_facebook, String contact_twitter, String contact_site) {
        this.contact_id = contact_id;
        this.contact_nom = contact_nom;
        this.contact_tel = contact_tel;
        this.contact_mail = contact_mail;
        this.contact_adresse = contact_adresse;
        this.contact_commune = contact_commune;
        this.contact_facebook = contact_facebook;
        this.contact_twitter = contact_twitter;
        this.contact_site = contact_site;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getContact_nom() {
        return contact_nom;
    }

    public void setContact_nom(String contact_nom) {
        this.contact_nom = contact_nom;
    }

    public String getContact_tel() {
        return contact_tel;
    }

    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }

    public String getContact_mail() {
        return contact_mail;
    }

    public void setContact_mail(String contact_mail) {
        this.contact_mail = contact_mail;
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

    public String getContact_facebook() {
        return contact_facebook;
    }

    public void setContact_facebook(String contact_facebook) {
        this.contact_facebook = contact_facebook;
    }

    public String getContact_twitter() {
        return contact_twitter;
    }

    public void setContact_twitter(String contact_twitter) {
        this.contact_twitter = contact_twitter;
    }

    public String getContact_site() {
        return contact_site;
    }

    public void setContact_site(String contact_site) {
        this.contact_site = contact_site;
    }
}

package utilitaires;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import adapters.Article_ArrayAdapter;
import modeles.Article;
import modeles.Categorie;
import modeles.Contact;
import modeles.MotCle;

/**
 * Created by Dominique DURI on 12/01/2018.
 */

public class Utility {

    // Check the email address is OK
    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    // Check the password is OK
    public static boolean isValidPassword(String password) {
        String passwordRegEx;
        Pattern pattern;
        passwordRegEx = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,20}$";
        pattern = Pattern.compile(passwordRegEx);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    //formater les dates
    public String formaterDateStr(String dateAformater){
        String date_formatage_intermediaire= dateAformater.substring(0,10);
        String date_annee_str = date_formatage_intermediaire.substring(0,4);
        String date_mois_str = date_formatage_intermediaire.substring(5,7);
        String[] tab_mois = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"};
        for(int i=0; i<tab_mois.length; i++){
            Integer mois_int = Integer.parseInt(date_mois_str)-1;
            if(mois_int == i ){
                date_mois_str = tab_mois[i];
                break;
            }
        }
        String date_jour_str = date_formatage_intermediaire.substring(8);
        String date_formatee = date_jour_str + " " + date_mois_str+" "+date_annee_str;
        return date_formatee;
    }

    //traitement des données reçues depuis l'API
    public static void convertirJSONEnArrayList(JSONArray jsonArray, String nomJSONArray , ArrayList<Article> listeArticles, Article_ArrayAdapter article_arrayAdapter) {

        Article article;
        String titre;
        String chapoHtml;
        String chapoTxt;
        String contenuHtml;
        String contenuTxt;
        String date_publication_str;
        String date_debut_str;
        String date_fin_str;
        String horaire_str;
        String prix_str;
        Double prix = 0.01;
        int contact_id_dans_article;
        String url_site_web = null;
        String url_facebook= null;
        String adresse;
        String contact_nom = null;
        String contact_adresse= null;
        String contact_commune= null;
        String url_pdf= null;
        String telephone_str= null;
        String url_youtube= null;
        String url_image= null;
        String url_twitter= null;
        String email_evenement= null;
        int cat_id_dans_article;
        String categorie = null ;
        ArrayList <Categorie> categories_liste = new ArrayList<>();
        ArrayList<String> mots_cle_id_X_article_liste = new ArrayList<>();
        HashMap<String, String> motsCle_id_X_article_id_listehm = new HashMap<String, String>();
        ArrayList<Contact> contacts_liste = new ArrayList<>();
        ArrayList<MotCle> motCleArrayList = new ArrayList<>();

        listeArticles.clear();
        try {
            JSONArray jArray = new JSONArray(nomJSONArray);
            JSONArray contact_jsonArray = (JSONArray) jArray.get(0);
            JSONArray mots_cle_jsonArray = (JSONArray) jArray.get(1);
            JSONArray categories_jsonArray = (JSONArray) jArray.get(2);
            JSONArray article_jsonArray = (JSONArray) jArray.get(3);
            JSONArray liste_mots_c_par_article_jsonArray = (JSONArray) jArray.get(4);

            for (int i=0; i<categories_jsonArray.length(); i++){
                Categorie categ  = new Categorie();
                JSONObject jso_categ = categories_jsonArray.getJSONObject(i);
                categ.setCat_id(jso_categ.getInt("cat_id"));
                categ.setCat_nom(jso_categ.getString("cat_nom"));
                categories_liste.add(categ);
            }
            for (int i=0; i<mots_cle_jsonArray.length(); i++){
                MotCle motCle = new MotCle();
                JSONObject jso_motsCle = mots_cle_jsonArray.getJSONObject(i);
                motCle.setMot_cle_id(jso_motsCle.getInt("mc_id"));
                motCle.setMot_cle_nom(jso_motsCle.getString("mc_nom"));
                motCle.setMot_cle_categorie_id(jso_motsCle.getInt("mc_cat_id"));
                motCleArrayList.add(motCle);
            }

            for (int ind=0; ind<liste_mots_c_par_article_jsonArray.length(); ind++){
                JSONObject jso_motCleXarticle = liste_mots_c_par_article_jsonArray.getJSONObject(ind);
                String article_id_str = jso_motCleXarticle.getString("article_id");
                String motCle_id_str = jso_motCleXarticle.getString("kw_id");
                motsCle_id_X_article_id_listehm.put(motCle_id_str,article_id_str);
            }

            for (int i=0; i<contact_jsonArray.length(); i++){
                Contact contact = new Contact();
                JSONObject jso_contact = contact_jsonArray.getJSONObject(i);
                contact.setContact_id(jso_contact.getInt("contact_id"));
                contact.setContact_nom(jso_contact.getString("contact_nom"));
                contact.setContact_adresse(jso_contact.getString("contact_adresse"));
                contact.setContact_commune(jso_contact.getString("contact_commune"));
                String contact_tel = jso_contact.getString("contact_tel");
                if(contact_tel.length()==0 || contact_tel.equals("null")){
                    contact_tel="non connu";
                }
                contact.setContact_tel(contact_tel);
                String contact_mail = jso_contact.getString("contact_mail");
                if(contact_mail.length() == 0 || contact_mail.equals("null")){
                    contact_mail="non connu";
                }
                contact.setContact_mail(contact_mail);
                String contact_FB = jso_contact.getString("contact_FB");
                if(contact_FB.length() == 0 || contact_FB.equals("null")){
                    contact_FB = "non connu";
                }
                contact.setContact_facebook(contact_FB);
                String contact_TW = jso_contact.getString("contact_TW");
                if((contact_TW.length() == 0) || (contact_TW.equals("null"))){
                    contact_TW = "non connu";
                }
                contact.setContact_twitter(contact_TW);
                String contact_site = jso_contact.getString("contact_site");
                if((contact_site.length()==0)||(contact_site.equals("null"))){
                    contact_site = "non_connu";
                }
                contact.setContact_site(contact_site);
                contacts_liste.add(contact);
            }
            for ( int i = 0 ; i<article_jsonArray.length();i++){

                JSONObject jsoArticle = (JSONObject) article_jsonArray.get(i);
                article = new Article();

                article.setId(jsoArticle.getInt("id"));

                date_publication_str = jsoArticle.getString("date_pub");
                date_publication_str = date_publication_str.substring(0,10);
                article.setDate_publication(date_publication_str);

                titre = jsoArticle.getString("titre") ;
                titre = Html.fromHtml((String) titre).toString();
                article.setTitre(titre);

                chapoHtml =jsoArticle.getString("chapo");
                if((chapoHtml.length()==0) || (chapoHtml.equals("null"))){
                    chapoTxt = "non connu";
                }else{
                    chapoTxt =chapoHtml.replaceAll("<[^>]*>", "");
                    chapoTxt = Html.fromHtml((String) chapoTxt).toString();
                }
                article.setChapo(chapoTxt);

                contenuHtml = jsoArticle.getString("contenu");
                contenuTxt = contenuHtml.replaceAll("^<BR>$", "\n");
                contenuTxt = Html.fromHtml((String) contenuTxt).toString();
                article.setContenu(contenuTxt);

                date_debut_str=jsoArticle.getString("date_deb");
                article.setDate_debut(date_debut_str);
                date_fin_str = jsoArticle.getString("date_fin");
                article.setDate_fin(date_fin_str);
                horaire_str = jsoArticle.getString("horaires");
                article.setHoraire(horaire_str);

                prix_str = jsoArticle.getString("prix");
                if( prix_str.equals("null")){
                    prix = 9999.99;
                }else{
                    prix = jsoArticle.getDouble("prix");
                }
                article.setPrix(prix);

                contact_id_dans_article = jsoArticle.getInt("liste_contacts_id");
                for(int cpt =0; cpt<contacts_liste.size(); cpt++){
                    if (contact_id_dans_article == contacts_liste.get(cpt).getContact_id()){
                         url_site_web = contacts_liste.get(cpt).getContact_site();
                        url_facebook=contacts_liste.get(cpt).getContact_facebook();
                        contact_nom = contacts_liste.get(cpt).getContact_nom() ;
                        contact_adresse = contacts_liste.get(cpt).getContact_adresse();
                        contact_commune = contacts_liste.get(cpt).getContact_commune();
                        telephone_str = contacts_liste.get(cpt).getContact_tel();
                        url_twitter = contacts_liste.get(cpt).getContact_twitter();
                        email_evenement = contacts_liste.get(cpt).getContact_mail();
                        break;
                    }
                }
                if((url_site_web.length() == 0 ) || (url_site_web.equals("null"))){
                    url_site_web = "non connu";
                }
                article.setUrl_site_web(url_site_web);

                if((url_facebook.length() == 0 ) || (url_facebook.equals("null"))){
                    url_facebook = "non connu";
                }
                article.setUrl_facebook(url_facebook);

                if((contact_nom.length() == 0 ) || (contact_nom.equals("null"))){
                    contact_nom = "non connu";
                }
                article.setContact_nom(contact_nom);

                if((contact_adresse.length() == 0 ) || (contact_adresse.equals("null"))){
                    contact_adresse = "non connu";
                }
                article.setContact_adresse(contact_adresse);

                if((contact_commune.length() == 0 ) || (contact_commune.equals("null"))){
                    contact_commune = "non connu";
                }
                article.setContact_commune(contact_commune);

                if((telephone_str.length() == 0 ) || (telephone_str.equals("null"))){
                    telephone_str = "non connu";
                }
                article.setTelephone(telephone_str);

                if((url_twitter.length() == 0 ) || (url_twitter.equals("null"))){
                    url_twitter = "non connu";
                }
                article.setUrl_twitter(url_twitter);

                if((email_evenement.length() == 0 ) || (email_evenement.equals("null"))){
                    email_evenement = "non connu";
                }
                article.setEmail_evenement(email_evenement);

                url_pdf = jsoArticle.getString("lien_pdf");
                if((url_pdf.length()==0)||(url_pdf.equals("null"))){
                    url_pdf = "non connu";
                }
                article.setUrl_pdf(url_pdf);

                url_youtube = jsoArticle.getString("lien_yt");
                if((url_youtube.length()==0)||(url_youtube.equals("null"))){
                    url_youtube = "non connu";
                }
                article.setUrl_youtube(url_youtube);


                cat_id_dans_article = jsoArticle.getInt("categorie_id");
                for(int c =0; c<categories_liste.size(); c++){
                    if (cat_id_dans_article == categories_liste.get(c).getCat_id()){
                        categorie = categories_liste.get(c).getCat_nom();
                        break;
                    }
                }
                article.setCategorie(categorie);

                url_image = "http://77.141.122.110/jvback/img/"+categorie+"/" + jsoArticle.getString("lien_image");
                article.setUrl_image(url_image);

                mots_cle_id_X_article_liste.clear();
                Set<Map.Entry<String, String>> entrees = motsCle_id_X_article_id_listehm.entrySet();
                Iterator<Map.Entry<String, String>> iter = entrees.iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entree = (Map.Entry<String, String>)iter.next();
                    String motCle_id_K = entree.getKey();
                    String article_id_V = entree.getValue();
                    String id_article_créé_str = jsoArticle.getString("id");
                    if(article_id_V.equals(id_article_créé_str)){
                        mots_cle_id_X_article_liste.add(motCle_id_K);

                    }
                }
                for(int j = 0 ; j<mots_cle_id_X_article_liste.size(); j++){
                    Integer motCle_id_dans_liste_mc_X_art = Integer.parseInt(mots_cle_id_X_article_liste.get(j));

                    for (int k = 0; k<motCleArrayList.size(); k++){

                        if(motCleArrayList.get(k).getMot_cle_id() == motCle_id_dans_liste_mc_X_art){
                            article.getMots_cle_liste_hm().put(motCle_id_dans_liste_mc_X_art, motCleArrayList.get(k).getMot_cle_nom());
                            break;
                        }
                    }
                }
                article_arrayAdapter.add(article);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

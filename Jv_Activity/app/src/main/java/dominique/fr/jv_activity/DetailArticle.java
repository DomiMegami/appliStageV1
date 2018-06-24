package dominique.fr.jv_activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import geoloc.GeoActivity;
import modeles.Article;
import utilitaires.Utility;

public class DetailArticle extends AppCompatActivity {

    Toolbar toolbar;
    Intent browserIntent;
    TextView tv_aff_mot_cle;
    Button btn_mot_cle;
    int id;
    String titre;
    String date_publication;
    String date_debut_str;
    String date_fin_str;
    String horaire;
    double prix;
    String chapo;
    String contenu;
    String url_site_web;
    String url_facebook;
    String adresse;
    String adresse_nom_salle;
    String adresse_rue;
    String adresse_commune;
    String url_pdf;
    String telephone_event;
    String url_youtube;
    String url_image;
    String categorie;
    HashMap<Integer, String> mots_cle_listehm = new HashMap<Integer, String>();
    String url_twitter;
    String email_evenement;
    Article actu_recue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        actu_recue = (Article)intent.getSerializableExtra("ActuEnvoyee");
        String phrase_date_a_afficher ="";
        String non_connu = "non connu";
        TextView tv_titre = (TextView)findViewById(R.id.tv_titre);
        LinearLayout ll_place_mots_cle = findViewById(R.id.ll_place_mots_cle);
        TextView tv_aff_dates = findViewById(R.id.tv_aff_dates);
        TextView tv_aff_horaires = findViewById(R.id.tv_aff_horaires);
        TextView tv_aff_prix = findViewById(R.id.tv_aff_prix);
        TextView tv_aff_chapo = findViewById(R.id.tv_aff_chapo);
        TextView tv_aff_contenu = findViewById(R.id.tv_aff_contenu);
        TextView tv_aff_adresse = findViewById(R.id.tv_aff_adresse);
        ImageView img_article = findViewById(R.id.img_article);
        ImageButton imgBtn_appel = findViewById(R.id.imgBtn_appel);
        ImageButton imgBtn_facebook = findViewById(R.id.imgBtn_facebook_event);
        ImageButton imgBtn_twitter = findViewById(R.id.imgBtn_twitter_event);
        ImageButton imgBtn_site_web = findViewById(R.id.imgBtn_site_web_event);
        ImageButton imgBtn_pdf = findViewById(R.id.imgBtn_pdf);
        ImageButton imgBtn_youtube = findViewById(R.id.imgBtn_youtube);
        ImageButton imgBtn_mail = findViewById(R.id.imgBtn_mail);
        if (actu_recue != null) {
            id = actu_recue.getId();
            titre = actu_recue.getTitre();
            date_publication = actu_recue.getDate_publication();
            date_debut_str = actu_recue.getDate_debut();
            date_fin_str = actu_recue.getDate_fin();
            horaire = actu_recue.getHoraire();
            prix = actu_recue.getPrix();
            chapo = actu_recue.getChapo();
            contenu = actu_recue.getContenu();
            url_site_web = actu_recue.getUrl_site_web();
            url_facebook = actu_recue.getUrl_facebook();
            adresse_nom_salle = actu_recue.getContact_nom();
            adresse_rue = actu_recue.getContact_adresse();
            adresse_commune = actu_recue.getContact_commune();
            url_pdf = actu_recue.getUrl_pdf();
            telephone_event = actu_recue.getTelephone();
            url_youtube = actu_recue.getUrl_youtube();
            url_image = actu_recue.getUrl_image();
            categorie = actu_recue.getCategorie();
            url_twitter = actu_recue.getUrl_twitter();
            email_evenement = actu_recue.getEmail_evenement();
            tv_titre.setText(titre);
            mots_cle_listehm = actu_recue.getMots_cle_liste_hm();
            Set<Map.Entry<Integer, String>> entrees = mots_cle_listehm.entrySet();
            Iterator<Map.Entry<Integer, String>> iter = entrees.iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, String> entree = (Map.Entry<Integer, String>)iter.next();
                final Integer cle = entree.getKey();
                final String val = entree.getValue();
                btn_mot_cle = new Button(this);
                btn_mot_cle.setId(cle);
                btn_mot_cle.setText(val);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(10, 0, 15, 0);
                btn_mot_cle.setLayoutParams(params);
                btn_mot_cle.setTextColor(getResources().getColor(R.color.noir));
                btn_mot_cle.setBackground(getResources().getDrawable(R.drawable.button_bkg_transp));
                btn_mot_cle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ArticlesTriesParTheme.class);
                        intent.putExtra("id", cle);
                        intent.putExtra("nom", val);
                        startActivity(intent);
                    }
                });
                ll_place_mots_cle.addView(btn_mot_cle);
            }

            /*//Ne fonctionne pas - DD le 20/02/2018
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.FRENCH);

            try {
                String date_debut_str_split= date_debut_str.substring(0,10);
                Date date_deb = formatter.parse(date_debut_str_split);
                date_debut_str = formatter.format(date_deb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                String date_debut_str_split= date_fin_str.substring(0,10);
                Date date_fin = formatter.parse(date_debut_str_split);
                date_fin_str = formatter.format(date_fin);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            date_debut_str = new Utility().formaterDateStr(date_debut_str);
            date_fin_str = new Utility().formaterDateStr(date_fin_str);
            if (date_fin_str.equals(date_debut_str)){
                phrase_date_a_afficher = "Le "+ date_debut_str;
             }
             else{
                phrase_date_a_afficher = "Du "+ date_debut_str + " au " + date_fin_str;
            }
            tv_aff_dates.setText(phrase_date_a_afficher);
            tv_aff_horaires.setText("A " + horaire);
            if(prix == 9999.99){
                tv_aff_prix.setVisibility(View.GONE);
            }else{
                tv_aff_prix.setText("Prix : " + String.valueOf(prix) + " €");
            }
            if(chapo.equals(non_connu)){
                tv_aff_chapo.setVisibility(View.GONE);
            }else{
                tv_aff_chapo.setText(chapo);
            }
            tv_aff_contenu.setText(contenu);
            adresse = "Lieu : " + adresse_nom_salle + " à " + adresse_commune;
            tv_aff_adresse.setText(adresse);
            if(email_evenement.equals(non_connu)){
                imgBtn_mail.setImageResource(R.drawable.ic_phone_grey_30dp);
            }else {
                imgBtn_mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse(email_evenement));
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sujet_mail_demande_renseignements));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
            }
            Glide.with(getApplicationContext()).load(url_image).into(img_article);
           // Picasso.with(getApplicationContext()).load(url_image).into(img_article);
           if(telephone_event.equals(non_connu)){
                imgBtn_appel.setImageResource(R.drawable.ic_phone_grey_30dp);

            }else{
                imgBtn_appel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri number = Uri.parse("tel:"+telephone_event);
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);
                    }
                });
            }
            if(url_facebook.equals(non_connu)){
                imgBtn_facebook.setImageResource(R.drawable.ic_facebook_grey);
            }else{
                imgBtn_facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent facebookIntent;
                        try {
                            getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                            facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href="+url_facebook));
                        } catch (PackageManager.NameNotFoundException e) {
                            facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_facebook));
                        }
                        startActivity(facebookIntent);
                    }
                });
            }
            if(url_twitter.equals(non_connu)){
                imgBtn_twitter.setImageResource(R.drawable.ic_twitter_grey_30dp);
            }else{
                imgBtn_twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+url_twitter));
                            startActivity(intent);
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/"+url_twitter)));
                        }
                    }
                });
            }
            if(url_site_web.equals(non_connu)){
                imgBtn_site_web.setImageResource(R.drawable.ic_icon_grey_www_30dp);
            }else{
                imgBtn_site_web.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_site_web));
                        startActivity(browserIntent);
                    }
                });
            }
            if(url_pdf.equals(non_connu)){
                imgBtn_pdf.setVisibility(View.GONE);
            }
            if(url_youtube.equals(non_connu)){
                imgBtn_youtube.setVisibility(View.GONE);
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.go_accueil:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.go_contact:
                intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.go_connexion:
                intent = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clicSurYoutube(View view) {
        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_youtube));
        startActivity(browserIntent);
    }

    public void clicSurPdf(View view) {
       browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_pdf));
        startActivity(browserIntent);
    }

    public void clicSurMap(View view) {
        Intent intent = new Intent(getApplicationContext(), GeoActivity.class);
        intent.putExtra("ARTICLE2",actu_recue);
        startActivity(intent);
    }
}

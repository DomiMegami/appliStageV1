package dominique.fr.jv_activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import adapters.Article_ArrayAdapter;
import modeles.Article;
import modeles.Categorie;
import utilitaires.UserSessionManager;
import utilitaires.Utility;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    UserSessionManager session ;
    Article articleCourant;
    ArrayList<Article> listeSortiesDuJour = new ArrayList<Article>();
    ListView lV_sortiesDuJour;
    Article_ArrayAdapter list_sortiesDuJour_arrayAdapter;
    String strData = null;
    ArrayList<Categorie> categories_liste;
    String url_requete_api;
    String debut_url_requete_api = "http://77.141.122.110/api/public/eventsbyfilters";
    String bout_url_date_1 = "/0";
    String bout_url_date_2 = "/1";
    String bout_url_date_3 = "/2";
    String bout_url_date="";
    int id_categorie_1 = 31;
    int id_categorie_2 = 32;
    int id_categorie_3 = 33;
    int id_categorie_4 = 35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new UserSessionManager(getApplicationContext());
        if (session.isUserLoggedIn()){
            startActivity(new Intent(getApplicationContext(), AccueilUserConnectedActivity.class));
        }

        final CheckBox cb_categorie_1 = findViewById(R.id.cb_categorie_1);
        final CheckBox cb_categorie_2 = findViewById(R.id.cb_categorie_2);
        final CheckBox cb_categorie_3 = findViewById(R.id.cb_categorie_3);
        final CheckBox cb_categorie_4 = findViewById(R.id.cb_categorie_4);
        final CheckBox cb_tri_date_1 = findViewById(R.id.cb_tri_date_1);
        final CheckBox cb_tri_date_2 = findViewById(R.id.cb_tri_date_2);
        final CheckBox cb_tri_date_3 = findViewById(R.id.cb_tri_date_3);

        url_requete_api = debut_url_requete_api + bout_url_date_1;

        cb_categorie_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_categorie_1.isChecked()){
                    if(cb_tri_date_1.isChecked()){
                       bout_url_date = bout_url_date_1;
                   }
                   else if(cb_tri_date_2.isChecked()){
                       bout_url_date=bout_url_date_2;
                   }
                   else if(cb_tri_date_3.isChecked()){
                       bout_url_date= bout_url_date_3;
                   }else{
                       Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                   }
                    if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api=debut_url_requete_api + bout_url_date;
                    }
                    else if ((cb_categorie_2.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+ id_categorie_1+"/"+id_categorie_2+"/"+ id_categorie_3;
                     }else if((cb_categorie_3.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_1+"/"+id_categorie_3+"/"+ id_categorie_4 ;
                     }else if((cb_categorie_2.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_1+"/"+id_categorie_2+"/"+ id_categorie_4 ;
                    }
                    else if(cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_1+"/"+id_categorie_2 ;
                    }else if (cb_categorie_3.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_1+"/"+id_categorie_3 ;
                    }else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_1+"/"+id_categorie_4 ;
                    }
                    else if (!cb_categorie_2.isChecked() && !cb_categorie_3.isChecked() && !cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_1;
                    }
                    connexionAPI(url_requete_api);
                }
                if (!cb_categorie_1.isChecked()){
                    if(cb_tri_date_1.isChecked()){
                        bout_url_date = bout_url_date_1;
                    }
                    else if(cb_tri_date_2.isChecked()){
                        bout_url_date=bout_url_date_2;
                    }
                    else if(cb_tri_date_3.isChecked()){
                        bout_url_date= bout_url_date_3;
                    }else{
                        Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                    }
                    if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+ id_categorie_2+"/"+id_categorie_3+"/"+ id_categorie_4;
                    }
                     else if ((cb_categorie_2.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_2+"/"+ id_categorie_3;
                     }else if((cb_categorie_3.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date+"/"+id_categorie_3+"/"+ id_categorie_4 ;
                    }else if((cb_categorie_2.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+ id_categorie_4 ;
                    }
                    else if(cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2 ;
                    }else if (cb_categorie_3.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3 ;
                    }else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4 ;
                   }
                    else if (!cb_categorie_2.isChecked() && !cb_categorie_3.isChecked() && !cb_categorie_4.isChecked()){
                        Toast.makeText(getApplicationContext(), R.string.txt_erreur_accueil_aucune_categ_sélectionnée, Toast.LENGTH_SHORT).show();
                    }
                    connexionAPI(url_requete_api);
                }
            }//onChangeCheckedListener
        });

        cb_categorie_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_categorie_2.isChecked()){
                    if(cb_tri_date_1.isChecked()){
                        bout_url_date = bout_url_date_1;
                    }
                    else if(cb_tri_date_2.isChecked()){
                        bout_url_date=bout_url_date_2;
                    }
                    else if(cb_tri_date_3.isChecked()){
                        bout_url_date= bout_url_date_3;
                    }else{
                        Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                    }
                    if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date;
                    }
                    else if ((cb_categorie_1.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+id_categorie_1+"/"+ id_categorie_3;
                    }else if((cb_categorie_3.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+id_categorie_3+"/"+ id_categorie_4 ;
                     }else if((cb_categorie_1.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+id_categorie_1+"/"+ id_categorie_4 ;
                    }
                    else if(cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+id_categorie_1 ;
                    }else if (cb_categorie_3.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+id_categorie_3 ;
                    }else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+id_categorie_4 ;
                    }
                   else if (!cb_categorie_2.isChecked() && !cb_categorie_3.isChecked() && !cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2;
                    }
                    connexionAPI(url_requete_api);
                }
                if (!cb_categorie_2.isChecked()){
                    if(cb_tri_date_1.isChecked()){
                        bout_url_date = bout_url_date_1;
                    }
                    else if(cb_tri_date_2.isChecked()){
                        bout_url_date=bout_url_date_2;
                    }
                    else if(cb_tri_date_3.isChecked()){
                        bout_url_date= bout_url_date_3;
                    }else{
                        Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                    }
                    if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+id_categorie_3+"/"+ id_categorie_4;
                    }
                    else if ((cb_categorie_1.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+ id_categorie_3;
                    }else if((cb_categorie_3.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3+"/"+ id_categorie_4 ;
                    }else if((cb_categorie_1.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+ id_categorie_4 ;
                    }
                    else if(cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1 ;
                    }else if (cb_categorie_3.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3 ;
                    }else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4 ;
                    }
                    else if (!cb_categorie_1.isChecked() && !cb_categorie_3.isChecked() && !cb_categorie_4.isChecked()){
                   }
                    connexionAPI(url_requete_api);
                }
            }
        });

        cb_categorie_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_categorie_3.isChecked()){
                    if(cb_tri_date_1.isChecked()){
                        bout_url_date = bout_url_date_1;
                    }
                    else if(cb_tri_date_2.isChecked()){
                        bout_url_date=bout_url_date_2;
                    }
                    else if(cb_tri_date_3.isChecked()){
                        bout_url_date= bout_url_date_3;
                    }else{
                        Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                    }
                    if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api=debut_url_requete_api + bout_url_date;
                    }
                    else if ((cb_categorie_1.isChecked() && cb_categorie_2.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3+"/"+id_categorie_1+"/"+ id_categorie_2;
                    }else if((cb_categorie_2.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api =debut_url_requete_api + bout_url_date +"/"+id_categorie_3+"/"+id_categorie_2+"/"+ id_categorie_4 ;
                     }else if((cb_categorie_1.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3+"/"+id_categorie_1+"/"+ id_categorie_4 ;
                    }
                    else if(cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3+"/"+id_categorie_1 ;
                     }else if (cb_categorie_2.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3+"/"+id_categorie_2 ;
                    }else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3+"/"+id_categorie_4 ;
                    }
                    else if (!cb_categorie_1.isChecked() && !cb_categorie_2.isChecked() && !cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3;
                    }
                    connexionAPI(url_requete_api);
                }
                if (!cb_categorie_3.isChecked()){
                   if(cb_tri_date_1.isChecked()){
                        bout_url_date = bout_url_date_1;
                    }
                    else if(cb_tri_date_2.isChecked()){
                        bout_url_date=bout_url_date_2;
                    }
                    else if(cb_tri_date_3.isChecked()){
                        bout_url_date= bout_url_date_3;
                    }else{
                        Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                    }
                    if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+id_categorie_2+"/"+ id_categorie_4;
                    }
                    else if ((cb_categorie_1.isChecked() && cb_categorie_2.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+ id_categorie_2;
                    }else if((cb_categorie_2.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+ id_categorie_4 ;
                    }else if((cb_categorie_1.isChecked() && cb_categorie_4.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+ id_categorie_4 ;
                    }
                     else if(cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1 ;
                    }else if (cb_categorie_2.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2 ;
                    }else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4 ;
                    }
                   else if (!cb_categorie_1.isChecked() && !cb_categorie_2.isChecked() && !cb_categorie_4.isChecked()){
                        Toast.makeText(getApplicationContext(), R.string.txt_erreur_accueil_aucune_categ_sélectionnée, Toast.LENGTH_SHORT).show();
                    }
                    connexionAPI(url_requete_api);
                }
            }
        });

        cb_categorie_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_categorie_4.isChecked()){
                    if(cb_tri_date_1.isChecked()){
                        bout_url_date = bout_url_date_1;
                    }
                    else if(cb_tri_date_2.isChecked()){
                        bout_url_date=bout_url_date_2;
                    }
                    else if(cb_tri_date_3.isChecked()){
                        bout_url_date= bout_url_date_3;
                    }else{
                        Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                    }
                    if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api=debut_url_requete_api + bout_url_date;
                     }
                    else if ((cb_categorie_1.isChecked() && cb_categorie_2.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4+"/"+id_categorie_1+"/"+ id_categorie_2;
                    }else if((cb_categorie_2.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4+"/"+id_categorie_2+"/"+ id_categorie_3 ;
                    }else if((cb_categorie_1.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4+"/"+id_categorie_1+"/"+ id_categorie_3 ;
                    }
                    else if(cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4+"/"+id_categorie_1 ;
                    }else if (cb_categorie_2.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4+"/"+id_categorie_2 ;
                     }else if (cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4+"/"+id_categorie_3 ;
                    }
                    else if (!cb_categorie_1.isChecked() && !cb_categorie_2.isChecked() && !cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_4;
                    }
                    connexionAPI(url_requete_api);
                }
                if (!cb_categorie_4.isChecked()){
                    if(cb_tri_date_1.isChecked()){
                        bout_url_date = bout_url_date_1;
                    }
                    else if(cb_tri_date_2.isChecked()){
                        bout_url_date=bout_url_date_2;
                    }
                     else if(cb_tri_date_3.isChecked()){
                        bout_url_date= bout_url_date_3;
                    }else{
                        Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                    }
                    if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+id_categorie_2+"/"+ id_categorie_3;
                     }
                    else if ((cb_categorie_1.isChecked() && cb_categorie_2.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+ id_categorie_2;
                    }else if((cb_categorie_2.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2+"/"+ id_categorie_3 ;
                    }else if((cb_categorie_1.isChecked() && cb_categorie_3.isChecked())){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1+"/"+ id_categorie_3 ;
                    }
                    else if(cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_1 ;
                     }else if (cb_categorie_2.isChecked() ){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_2 ;
                     }else if (cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date +"/"+id_categorie_3 ;
                    }
                    else if (!cb_categorie_1.isChecked() && !cb_categorie_2.isChecked() && !cb_categorie_3.isChecked()){
                        Toast.makeText(getApplicationContext(), R.string.txt_erreur_accueil_aucune_categ_sélectionnée, Toast.LENGTH_SHORT).show();
                    }
                    connexionAPI(url_requete_api);
                }
            }
        });

        cb_tri_date_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_tri_date_1.isChecked()){
                    cb_tri_date_2.setChecked(false);
                    cb_tri_date_3.setChecked(false);
                    if(cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1;
                    }
                    else if(cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_1 + "/" + id_categorie_2 + "/"+id_categorie_3;
                     }
                    else if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_1 + "/" + id_categorie_3 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_1 + "/" + id_categorie_2 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_2 + "/" + id_categorie_3 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_1 + "/" + id_categorie_2 ;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_1 + "/" + id_categorie_3 ;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_1 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_2 + "/" + id_categorie_3 ;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_2 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_3 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_1  ;
                    }
                    else if (cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_2  ;
                    }
                    else if (cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_3  ;
                    }
                    else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_1 +"/"+ id_categorie_4  ;
                    }
                }
                if (!cb_tri_date_1.isChecked() && !cb_tri_date_2.isChecked() && !cb_tri_date_3.isChecked()) {
                    Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                }
                connexionAPI(url_requete_api);
            }
        });
        cb_tri_date_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_tri_date_2.isChecked()){
                    cb_tri_date_1.setChecked(false);
                    cb_tri_date_3.setChecked(false);
                    if(cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2;
                    }
                    else if(cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_1 + "/" + id_categorie_2 + "/"+id_categorie_3;
                     }
                    else if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_1 + "/" + id_categorie_3 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_1 + "/" + id_categorie_2 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_2 + "/" + id_categorie_3 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_1 + "/" + id_categorie_2 ;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_1 + "/" + id_categorie_3 ;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_1 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_2 + "/" + id_categorie_3 ;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_2 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_3 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_1  ;
                    }
                    else if (cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_2  ;
                     }
                    else if (cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_3  ;
                    }
                    else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_2 +"/"+ id_categorie_4  ;
                    }

                }
                if (!cb_tri_date_1.isChecked() && !cb_tri_date_2.isChecked() && !cb_tri_date_3.isChecked()){
                    Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                }
                connexionAPI(url_requete_api);
            }
        });

        cb_tri_date_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_tri_date_3.isChecked()){
                    cb_tri_date_1.setChecked(false);
                    cb_tri_date_2.setChecked(false);
                    if(cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3;
                    }
                    else if(cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_1 + "/" + id_categorie_2 + "/"+id_categorie_3;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_1 + "/" + id_categorie_3 + "/"+id_categorie_4;
                   }
                    else if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_1 + "/" + id_categorie_2 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_2 + "/" + id_categorie_3 + "/"+id_categorie_4;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_1 + "/" + id_categorie_2 ;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_1 + "/" + id_categorie_3 ;
                    }
                    else if (cb_categorie_1.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_1 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_2 + "/" + id_categorie_3 ;
                    }
                    else if (cb_categorie_2.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_2 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_3.isChecked() && cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_3 + "/" + id_categorie_4 ;
                    }
                    else if (cb_categorie_1.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_1  ;
                    }
                    else if (cb_categorie_2.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_2  ;
                    }
                    else if (cb_categorie_3.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_3  ;
                    }
                    else if (cb_categorie_4.isChecked()){
                        url_requete_api = debut_url_requete_api + bout_url_date_3 +"/"+ id_categorie_4  ;
                    }
                }
                if (!cb_tri_date_1.isChecked() && !cb_tri_date_2.isChecked() && !cb_tri_date_3.isChecked()){
                    Toast.makeText(getApplicationContext(), "choisissez au moins un filtre", Toast.LENGTH_LONG).show();
                }
                connexionAPI(url_requete_api);
            }
        });
        connexionAPI(url_requete_api);

        list_sortiesDuJour_arrayAdapter = new Article_ArrayAdapter(this, listeSortiesDuJour);
        lV_sortiesDuJour = findViewById(R.id.liste1);
        lV_sortiesDuJour.setAdapter(list_sortiesDuJour_arrayAdapter);
        lV_sortiesDuJour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = lV_sortiesDuJour.getItemAtPosition(i);
                articleCourant = (Article) o;
                Intent intent = new Intent(MainActivity.this, DetailArticle.class);
                intent.putExtra("ActuEnvoyee", (Serializable) articleCourant);
                startActivity(intent);
            }
        });
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
        }
        return super.onOptionsItemSelected(item);
    }

    private class TacheChargerDonnes extends AsyncTask<URL, Void ,JSONArray> {

        @Override
        protected JSONArray doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK){
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    strData = builder.toString();
                    return new JSONArray(strData);
                }else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            Utility.convertirJSONEnArrayList(jsonArray, strData, listeSortiesDuJour, list_sortiesDuJour_arrayAdapter);
            list_sortiesDuJour_arrayAdapter.notifyDataSetChanged();

        }
    }

    public void connexionAPI(String url_requete_api){

        try {
            new TacheChargerDonnes().execute(new URL(url_requete_api)) ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
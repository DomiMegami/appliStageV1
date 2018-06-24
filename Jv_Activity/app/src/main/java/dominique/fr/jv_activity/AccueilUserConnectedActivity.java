package dominique.fr.jv_activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import adapters.Article_ArrayAdapter;
import modeles.Article;
import requests.RecupInfo_ModifProfilRequest;
import utilitaires.UserSessionManager;
import utilitaires.Utility;

public class AccueilUserConnectedActivity extends AppCompatActivity {

        Toolbar toolbar;
        UserSessionManager session ;
        Article articleCourant;
        ArrayList<Article> listeSortiesDuJour = new ArrayList<Article>();
        ListView lV_sortiesDuJour;
        Article_ArrayAdapter list_sortiesDuJour_arrayAdapter;
        String strData = null;
        ArrayList<Integer> liste_id_categ_tab;
        ArrayList<String> liste_categ_tab;
        int idFromSession;
        String pseudo;
        TextView titre_de_la_page;
        String debut_url_api = "http://77.141.122.110/api/public/eventsbyfilters";
        String bout_url_date_1 = "/0";
        String bout_url_date_2 = "/1";
        String bout_url_date_3 = "/2";
        String categ_x_user_url_api = "";
        String url_api_str = "";
        String keyHashMapPourDebug;
        String liste_categ_pour_debug="";

       @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accueil_user_connected);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);


           session = new UserSessionManager(getApplicationContext());
            if (session.checkLogin()){
                finish();
            }
            HashMap<String, Integer> userFromSession = session.getUserDetails();

            titre_de_la_page = findViewById(R.id.titre_de_la_page);

            String contenuHashMap="";
            Set <Map.Entry<String, Integer>> entrees = userFromSession.entrySet();
            Iterator <Map.Entry<String, Integer>> iter = entrees.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Integer> entree = (Map.Entry<String, Integer>)iter.next();
                String cle = entree.getKey();
                Integer val = entree.getValue();
                contenuHashMap += "cle : " + cle + " | val : "+ val + "\n";
                idFromSession = val;
                keyHashMapPourDebug = cle;
            }

           final CheckBox cb_tri_date_1 = findViewById(R.id.cb_tri_date_1);
           final CheckBox cb_tri_date_2 = findViewById(R.id.cb_tri_date_2);
           final CheckBox cb_tri_date_3 = findViewById(R.id.cb_tri_date_3);

           final Response.Listener<String> responseListener = new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   try {
                       JSONObject jsonResponse = new JSONObject(response);
                       boolean success = jsonResponse.getBoolean("success");
                       if(success){
                           liste_id_categ_tab = new ArrayList<>();
                           JSONArray jsonArrayCateg = jsonResponse.getJSONArray("cat_pref_id");
                           for (int i = 0; i<jsonArrayCateg.length(); i++){
                               liste_id_categ_tab.add(jsonArrayCateg.getInt(i));
                           }
                           liste_categ_tab = new ArrayList<>();
                           JSONArray jsonArrayCateg_nom = jsonResponse.getJSONArray("cat_nom");
                           for (int i = 0; i<jsonArrayCateg_nom.length(); i++){
                               liste_categ_tab.add(jsonArrayCateg_nom.getString(i));
                           }
                           pseudo = jsonResponse.getString("user_pseudo");
                           titre_de_la_page.setText("Bienvenue " + pseudo + " ! ");

                           if (liste_id_categ_tab !=null){
                               if(liste_id_categ_tab.size()>0 ){
                                   for (int j = 0 ; j < liste_id_categ_tab.size(); j++){
                                       switch(liste_id_categ_tab.get(j)){
                                           case 1 :
                                               categ_x_user_url_api += "/31" ;
                                               break;
                                           case 2:
                                               categ_x_user_url_api += "/32" ;
                                               break;
                                           case 3:
                                               categ_x_user_url_api += "/33" ;
                                               break;
                                           case 4 :
                                               categ_x_user_url_api += "/35" ;
                                               break;
                                       }
                                   }
                                   url_api_str = debut_url_api +bout_url_date_1 + categ_x_user_url_api;
                               }
                           }else{
                               url_api_str = debut_url_api + bout_url_date_1;
                           }
                           String url_api_test = "http://77.141.122.110/api/public/eventsbyfilters/1/31";
                           //titre_de_la_page.setText(url_api_str);
                           try {
                              new AccueilUserConnectedActivity.TacheChargerDonnes().execute(new URL(url_api_str)) ;
                               //new AccueilUserConnectedActivity.TacheChargerDonnes().execute(new URL(url_api_test)) ;

                           } catch (MalformedURLException e) {
                               e.printStackTrace();
                           }
                           list_sortiesDuJour_arrayAdapter = new Article_ArrayAdapter(getApplicationContext(), listeSortiesDuJour);
                           lV_sortiesDuJour = findViewById(R.id.liste1);
                           lV_sortiesDuJour.setAdapter(list_sortiesDuJour_arrayAdapter);
                           lV_sortiesDuJour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                               @Override
                               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                   Object o = lV_sortiesDuJour.getItemAtPosition(i);
                                   articleCourant = (Article) o;
                                   Intent intent = new Intent(getApplicationContext(), DetailArticle.class);
                                   intent.putExtra("ActuEnvoyee", (Serializable) articleCourant);
                                   startActivity(intent);
                               }
                           });
                       }else{
                           AlertDialog.Builder builder = new AlertDialog.Builder(AccueilUserConnectedActivity.this);
                           builder.setMessage("oups")
                                   .setNegativeButton("retry", null)
                                   .create()
                                   .show();
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }//end onresponse
           };
           RecupInfo_ModifProfilRequest recupInfo_modifProfilRequest = new RecupInfo_ModifProfilRequest(idFromSession, responseListener);
           RequestQueue queue = Volley.newRequestQueue(AccueilUserConnectedActivity.this);
           queue.add(recupInfo_modifProfilRequest);

           cb_tri_date_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if (cb_tri_date_1.isChecked()){
                       cb_tri_date_2.setChecked(false);
                       cb_tri_date_3.setChecked(false);

                       final Response.Listener<String> responseListener = new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               try {
                                   JSONObject jsonResponse = new JSONObject(response);
                                   boolean success = jsonResponse.getBoolean("success");
                                   if(success){
                                       liste_id_categ_tab = new ArrayList<>();
                                       JSONArray jsonArrayCateg = jsonResponse.getJSONArray("cat_pref_id");
                                       for (int i = 0; i<jsonArrayCateg.length(); i++){
                                           liste_id_categ_tab.add(jsonArrayCateg.getInt(i));
                                       }
                                       liste_categ_tab = new ArrayList<>();
                                       JSONArray jsonArrayCateg_nom = jsonResponse.getJSONArray("cat_nom");
                                       for (int i = 0; i<jsonArrayCateg_nom.length(); i++){
                                           liste_categ_tab.add(jsonArrayCateg_nom.getString(i));
                                       }
                                       pseudo = jsonResponse.getString("user_pseudo");
                                       titre_de_la_page.setText("Bienvenue " + pseudo + " ! ");
                                       categ_x_user_url_api = "";
                                       url_api_str="";

                                       if (liste_id_categ_tab !=null){
                                           if(liste_id_categ_tab.size()>0 ){
                                               for (int j = 0 ; j < liste_id_categ_tab.size(); j++){
                                                   switch(liste_id_categ_tab.get(j)){
                                                       case 1 :
                                                           categ_x_user_url_api += "/31" ;
                                                           break;
                                                       case 2:
                                                           categ_x_user_url_api += "/32" ;
                                                           break;
                                                       case 3:
                                                           categ_x_user_url_api += "/33" ;
                                                           break;
                                                       case 4 :
                                                           categ_x_user_url_api += "/35" ;
                                                           break;
                                                   }
                                               }
                                               url_api_str = debut_url_api +bout_url_date_1 + categ_x_user_url_api;
                                           }
                                       }else{
                                           url_api_str = debut_url_api + bout_url_date_1;
                                       }
                                       String url_api_test = "http://77.141.122.110/api/public/eventsbyfilters/1/31";

                                       try {
                                           new AccueilUserConnectedActivity.TacheChargerDonnes().execute(new URL(url_api_str)) ;
                                           //new AccueilUserConnectedActivity.TacheChargerDonnes().execute(new URL(url_api_test)) ;

                                       } catch (MalformedURLException e) {
                                           e.printStackTrace();
                                       }
                                       list_sortiesDuJour_arrayAdapter = new Article_ArrayAdapter(getApplicationContext(), listeSortiesDuJour);
                                       lV_sortiesDuJour = findViewById(R.id.liste1);
                                       lV_sortiesDuJour.setAdapter(list_sortiesDuJour_arrayAdapter);
                                       lV_sortiesDuJour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                               Object o = lV_sortiesDuJour.getItemAtPosition(i);
                                               articleCourant = (Article) o;
                                               Intent intent = new Intent(getApplicationContext(), DetailArticle.class);
                                               intent.putExtra("ActuEnvoyee", (Serializable) articleCourant);
                                               startActivity(intent);
                                           }
                                       });
                                   }else{
                                       AlertDialog.Builder builder = new AlertDialog.Builder(AccueilUserConnectedActivity.this);
                                       builder.setMessage("oups")
                                               .setNegativeButton("retry", null)
                                               .create()
                                               .show();
                                   }
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }//end onresponse
                       };
                       RecupInfo_ModifProfilRequest recupInfo_modifProfilRequest = new RecupInfo_ModifProfilRequest(idFromSession, responseListener);
                       RequestQueue queue = Volley.newRequestQueue(AccueilUserConnectedActivity.this);
                       queue.add(recupInfo_modifProfilRequest);
                   }
               }
           });

           cb_tri_date_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if (cb_tri_date_2.isChecked()){
                       cb_tri_date_1.setChecked(false);
                       cb_tri_date_3.setChecked(false);
                       final Response.Listener<String> responseListener = new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               try {
                                   JSONObject jsonResponse = new JSONObject(response);
                                   boolean success = jsonResponse.getBoolean("success");
                                   if(success){
                                       liste_id_categ_tab = new ArrayList<>();
                                       JSONArray jsonArrayCateg = jsonResponse.getJSONArray("cat_pref_id");
                                       for (int i = 0; i<jsonArrayCateg.length(); i++){
                                           liste_id_categ_tab.add(jsonArrayCateg.getInt(i));
                                       }
                                       liste_categ_tab = new ArrayList<>();
                                       JSONArray jsonArrayCateg_nom = jsonResponse.getJSONArray("cat_nom");
                                       for (int i = 0; i<jsonArrayCateg_nom.length(); i++){
                                           liste_categ_tab.add(jsonArrayCateg_nom.getString(i));
                                       }
                                       pseudo = jsonResponse.getString("user_pseudo");
                                       titre_de_la_page.setText("Bienvenue " + pseudo + " ! ");

                                       categ_x_user_url_api = "";
                                       url_api_str="";
                                       if (liste_id_categ_tab !=null){
                                           if(liste_id_categ_tab.size()>0 ){
                                               for (int j = 0 ; j < liste_id_categ_tab.size(); j++){
                                                   switch(liste_id_categ_tab.get(j)){
                                                       case 1 :
                                                           categ_x_user_url_api += "/31" ;
                                                           break;
                                                       case 2:
                                                           categ_x_user_url_api += "/32" ;
                                                           break;
                                                       case 3:
                                                           categ_x_user_url_api += "/33" ;
                                                           break;
                                                       case 4 :
                                                           categ_x_user_url_api += "/35" ;
                                                           break;
                                                   }
                                               }
                                               url_api_str = debut_url_api +bout_url_date_2 + categ_x_user_url_api;

                                           }
                                       }else{
                                           url_api_str = debut_url_api + bout_url_date_2;
                                       }
                                       try {
                                           new AccueilUserConnectedActivity.TacheChargerDonnes().execute(new URL(url_api_str)) ;

                                       } catch (MalformedURLException e) {
                                           e.printStackTrace();
                                       }
                                       list_sortiesDuJour_arrayAdapter = new Article_ArrayAdapter(getApplicationContext(), listeSortiesDuJour);
                                       lV_sortiesDuJour = findViewById(R.id.liste1);
                                       lV_sortiesDuJour.setAdapter(list_sortiesDuJour_arrayAdapter);
                                       lV_sortiesDuJour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                               Object o = lV_sortiesDuJour.getItemAtPosition(i);
                                               articleCourant = (Article) o;
                                               Intent intent = new Intent(getApplicationContext(), DetailArticle.class);
                                               intent.putExtra("ActuEnvoyee", (Serializable) articleCourant);
                                               startActivity(intent);
                                           }
                                       });
                                   }else{
                                       AlertDialog.Builder builder = new AlertDialog.Builder(AccueilUserConnectedActivity.this);
                                       builder.setMessage("oups")
                                               .setNegativeButton("retry", null)
                                               .create()
                                               .show();
                                   }
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }//end onresponse
                       };
                       RecupInfo_ModifProfilRequest recupInfo_modifProfilRequest = new RecupInfo_ModifProfilRequest(idFromSession, responseListener);
                       RequestQueue queue = Volley.newRequestQueue(AccueilUserConnectedActivity.this);
                       queue.add(recupInfo_modifProfilRequest);
                   }
               }
           });

           cb_tri_date_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if (cb_tri_date_3.isChecked()){
                       cb_tri_date_1.setChecked(false);
                       cb_tri_date_2.setChecked(false);

                       final Response.Listener<String> responseListener = new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               try {
                                   JSONObject jsonResponse = new JSONObject(response);
                                   boolean success = jsonResponse.getBoolean("success");
                                   if(success){
                                       liste_id_categ_tab = new ArrayList<>();
                                       JSONArray jsonArrayCateg = jsonResponse.getJSONArray("cat_pref_id");
                                       for (int i = 0; i<jsonArrayCateg.length(); i++){
                                           liste_id_categ_tab.add(jsonArrayCateg.getInt(i));
                                       }
                                       liste_categ_tab = new ArrayList<>();
                                       JSONArray jsonArrayCateg_nom = jsonResponse.getJSONArray("cat_nom");
                                       for (int i = 0; i<jsonArrayCateg_nom.length(); i++){
                                           liste_categ_tab.add(jsonArrayCateg_nom.getString(i));
                                       }
                                       pseudo = jsonResponse.getString("user_pseudo");
                                       titre_de_la_page.setText("Bienvenue " + pseudo + " ! ");
                                       categ_x_user_url_api = "";
                                       url_api_str="";
                                       if (liste_id_categ_tab !=null){
                                           if(liste_id_categ_tab.size()>0 ){
                                               for (int j = 0 ; j < liste_id_categ_tab.size(); j++){
                                                   switch(liste_id_categ_tab.get(j)){
                                                       case 1 :
                                                           categ_x_user_url_api += "/31" ;
                                                           break;
                                                       case 2:
                                                           categ_x_user_url_api += "/32" ;
                                                           break;
                                                       case 3:
                                                           categ_x_user_url_api += "/33" ;
                                                           break;
                                                       case 4 :
                                                           categ_x_user_url_api += "/35" ;
                                                           break;
                                                   }
                                               }
                                               url_api_str = debut_url_api +bout_url_date_3 + categ_x_user_url_api;
                                                                                          }
                                       }else{
                                           url_api_str = debut_url_api + bout_url_date_3;
                                       }
                                       try {
                                           new AccueilUserConnectedActivity.TacheChargerDonnes().execute(new URL(url_api_str)) ;

                                       } catch (MalformedURLException e) {
                                           e.printStackTrace();
                                       }
                                       list_sortiesDuJour_arrayAdapter = new Article_ArrayAdapter(getApplicationContext(), listeSortiesDuJour);
                                       lV_sortiesDuJour = findViewById(R.id.liste1);
                                       lV_sortiesDuJour.setAdapter(list_sortiesDuJour_arrayAdapter);
                                       lV_sortiesDuJour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                               Object o = lV_sortiesDuJour.getItemAtPosition(i);
                                               articleCourant = (Article) o;
                                               Intent intent = new Intent(getApplicationContext(), DetailArticle.class);
                                               intent.putExtra("ActuEnvoyee", (Serializable) articleCourant);
                                               startActivity(intent);
                                           }
                                       });
                                   }else{
                                       AlertDialog.Builder builder = new AlertDialog.Builder(AccueilUserConnectedActivity.this);
                                       builder.setMessage("oups")
                                               .setNegativeButton("retry", null)
                                               .create()
                                               .show();
                                   }
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }//end onresponse
                       };
                       RecupInfo_ModifProfilRequest recupInfo_modifProfilRequest = new RecupInfo_ModifProfilRequest(idFromSession, responseListener);
                       RequestQueue queue = Volley.newRequestQueue(AccueilUserConnectedActivity.this);
                       queue.add(recupInfo_modifProfilRequest);
                   }
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
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
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
}

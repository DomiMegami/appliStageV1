package dominique.fr.jv_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import modeles.User;
import requests.RecupInfo_ModifProfilRequest;
import requests.RecupPseudoRequest;
import requests.UpdateProfilRequest;
import utilitaires.UserSessionManager;

public class ProfilModifActivity extends AppCompatActivity {

    Toolbar toolbar;
    UserSessionManager session;
    ArrayList<String> liste_categ_tab;
    User user_connecte=new User();
    CheckBox chk_cat_1;
    CheckBox chk_cat_2;
    CheckBox chk_cat_3;
    CheckBox chk_cat_4;
    EditText et_new_pseudo;
    ArrayList<Integer> listPreferencesId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_modif);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new UserSessionManager(getApplicationContext());
        Intent intent = getIntent();
        user_connecte = (User)intent.getSerializableExtra("user_connecte");
        final EditText etPseudo = (EditText) findViewById(R.id.et_modif_pseudo);
        final TextView tv_aff_email = (TextView) findViewById(R.id.tv_aff_email);
        final TextView tv_aff_mdp = (TextView)findViewById(R.id.tv_aff_mdp);
        final LinearLayout tv_aff_categories = (LinearLayout)findViewById(R.id.tv_aff_categories);
        HashMap<String, Integer> userFromSession = session.getUserDetails();
        final int idFromSession = userFromSession.get(UserSessionManager.KEY_ID);
        user_connecte.setId(idFromSession);
        final ArrayList<String> listePrefUser = user_connecte.getListeNomCategoriesPreferees();
        etPseudo.setText(user_connecte.getPseudo());
        tv_aff_email.setText(user_connecte.getEmail());
        tv_aff_mdp.setText(user_connecte.getMotDePasse());

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        liste_categ_tab = new ArrayList<>();
                        JSONArray jsonArrayCateg = jsonResponse.getJSONArray("cat_nom");
                        for (int i = 0; i<jsonArrayCateg.length(); i++){
                            liste_categ_tab.add(jsonArrayCateg.getString(i));
                        }
                        chk_cat_1 = findViewById(R.id.ckb_categorie_1);
                        chk_cat_1.setText(liste_categ_tab.get(0));
                        chk_cat_2 = findViewById(R.id.ckb_categorie_2);
                        chk_cat_2.setText(liste_categ_tab.get(1));
                        chk_cat_3 = findViewById(R.id.ckb_categorie_3);
                        chk_cat_3.setText(liste_categ_tab.get(2));
                        chk_cat_4 = findViewById(R.id.ckb_categorie_4);
                        chk_cat_4.setText(liste_categ_tab.get(3));

                        for (int u = 0; u < listePrefUser.size(); u++) {
                            switch (listePrefUser.get(u)){
                                case "Jeux" :
                                    chk_cat_1.setChecked(true);
                                    break;
                                case "Art et culture" :
                                    chk_cat_2.setChecked(true);
                                    break;
                                case "Bonnes affaires" :
                                    chk_cat_3.setChecked(true);
                                    break;
                                case "Sport" :
                                    chk_cat_4.setChecked(true);
                                    break;

                            }
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfilModifActivity.this);
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
        RequestQueue queue = Volley.newRequestQueue(ProfilModifActivity.this);
        queue.add(recupInfo_modifProfilRequest);

        Button btn_modif_mdp = (Button) findViewById(R.id.btn_modif_mdp);
        btn_modif_mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilModifActivity.this, PopupModifMdp.class);
                user_connecte.setId(idFromSession);
                intent.putExtra("user_connecte", (Serializable) user_connecte);
                startActivity(intent);
            }
        });
    }

    public void ValidModifProfil(View view) {
        final int user_id = user_connecte.getId();
        final String user_pseudo = user_connecte.getPseudo();
        et_new_pseudo = (EditText) findViewById(R.id.et_modif_pseudo);
        final String user_new_pseudo = et_new_pseudo.getText().toString();
        final Integer user_pref_1;
        final Integer user_pref_2;
        final Integer user_pref_3;
        final Integer user_pref_4;

        listPreferencesId.clear();
        if (chk_cat_1.isChecked()) {
            listPreferencesId.add(0, 31);
        } else {
            listPreferencesId.add(0, 0);
        }
        if (chk_cat_2.isChecked()) {
            listPreferencesId.add(1, 32);
        } else {
            listPreferencesId.add(1, 0);
        }
        if (chk_cat_3.isChecked()) {
            listPreferencesId.add(2, 33);
        } else {
            listPreferencesId.add(2, 0);
        }
        if (chk_cat_4.isChecked()) {
            listPreferencesId.add(3, 35);
        } else {
            listPreferencesId.add(3, 0);
        }
        user_pref_1 = listPreferencesId.get(0);
        user_pref_2 = listPreferencesId.get(1);
        user_pref_3 = listPreferencesId.get(2);
        user_pref_4 = listPreferencesId.get(3);

        if (listPreferencesId.get(0) == 0 && listPreferencesId.get(1) == 0 && listPreferencesId.get(2) == 0 && listPreferencesId.get(3) == 0) {
            Toast.makeText(getApplicationContext(), R.string.txt_info_au_moins_1_categ, Toast.LENGTH_LONG).show();
        }
        else if (!user_new_pseudo.equals(user_pseudo)) {
            final Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Toast.makeText(getApplicationContext(), R.string.txt_info_pseudo_deja_pris, Toast.LENGTH_LONG).show();
                        } else {
                            final Response.Listener<String> responseListener_modifProfil = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (success) {
                                            Toast.makeText(ProfilModifActivity.this, R.string.txt_info_profil_modifie, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfilModifActivity.this);
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
                            UpdateProfilRequest updateProfilRequest = new UpdateProfilRequest(user_id, user_pseudo, user_new_pseudo, user_pref_1, user_pref_2, user_pref_3,user_pref_4, responseListener_modifProfil);
                            RequestQueue queue2 = Volley.newRequestQueue(ProfilModifActivity.this);
                            queue2.add(updateProfilRequest);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RecupPseudoRequest recupPseudoRequest = new RecupPseudoRequest(user_new_pseudo, responseListener);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(recupPseudoRequest);
        }
        else{
            final Response.Listener<String> responseListener_modifProfil = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Toast.makeText(ProfilModifActivity.this, R.string.txt_info_profil_modifie, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfilModifActivity.this);
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
            UpdateProfilRequest updateProfilRequest = new UpdateProfilRequest(user_id, user_pseudo, user_new_pseudo, user_pref_1, user_pref_2, user_pref_3,user_pref_4, responseListener_modifProfil);
            RequestQueue queue2 = Volley.newRequestQueue(ProfilModifActivity.this);
            queue2.add(updateProfilRequest);
        }
    }
    //annuler
    public void annulModif(View view) {
        finish();
    }
    //toolbar
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

}

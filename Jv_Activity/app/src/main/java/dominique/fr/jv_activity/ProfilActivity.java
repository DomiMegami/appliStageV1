package dominique.fr.jv_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import requests.RecupInfoProfilRequest;
import utilitaires.UserSessionManager;

public class ProfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textViewPref;
    UserSessionManager session;
    User user_connecte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView tvPseudo = (TextView) findViewById(R.id.tv_aff_pseudo);
        final TextView tvMdp = (TextView)findViewById(R.id.tv_aff_mdp);
        final TextView tv_aff_email = (TextView) findViewById(R.id.tv_aff_email);
        final LinearLayout tv_aff_preferences = (LinearLayout)findViewById(R.id.tv_aff_preferences);
        final TextView tv_aff_telephone = findViewById(R.id.tv_aff_telephone);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final Float widthPixels = Float.valueOf(metrics.widthPixels);

        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()){
            finish();
        }
        HashMap<String, Integer> userFromSession = session.getUserDetails();
        int idFromSession = userFromSession.get(UserSessionManager.KEY_ID);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        user_connecte = new User();
                        user_connecte.setPseudo(jsonResponse.getString("user_pseudo"));
                        user_connecte.setEmail(jsonResponse.getString("user_email"));
                        user_connecte.setMotDePasse(jsonResponse.getString("user_mdp"));
                        user_connecte.setTelephone(jsonResponse.getString("user_telephone"));
                        ArrayList<String> liste_categ_tab = new ArrayList<>();
                        JSONArray jsonArrayCateg = jsonResponse.getJSONArray("cat_nom");
                        for (int i = 0; i<jsonArrayCateg.length(); i++){
                            liste_categ_tab.add(jsonArrayCateg.getString(i));
                        }
                        user_connecte.setListeNomCategoriesPreferees(liste_categ_tab);
                        tvPseudo.setText(user_connecte.getPseudo());
                        tv_aff_email.setText(user_connecte.getEmail());
                        tv_aff_telephone.setText(user_connecte.getTelephone());
                        tvMdp.setText(user_connecte.getMotDePasse());
                        for (int j=0; j<user_connecte.getListeNomCategoriesPreferees().size(); j++){
                            textViewPref = new TextView(getApplicationContext());
                            textViewPref.setId(j);
                            if(widthPixels >= 1200){
                                textViewPref.setTextSize(22);
                            }else {
                                textViewPref.setTextSize(18);
                            }
                            textViewPref.setTextColor(getResources().getColor(R.color.noir));
                            textViewPref.setText(user_connecte.getListeNomCategoriesPreferees().get(j));
                            tv_aff_preferences.addView(textViewPref);
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfilActivity.this);
                        builder.setMessage("login failed")
                                .setNegativeButton("retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }//end onresponse
        };
        RecupInfoProfilRequest recupInfoProfilRequest = new RecupInfoProfilRequest(idFromSession, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProfilActivity.this);
        queue.add(recupInfoProfilRequest);
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
                intent.putExtra("user_connecte", user_connecte);
                startActivity(intent);
                finish();
                break;
            case R.id.go_contact:
                intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.go_connexion:
                intent = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                intent2.putExtra("user_connecte", user_connecte);
                startActivity(intent2);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goModifProfil(View view) {
        Intent intent = new Intent(this, ProfilModifActivity.class);
        intent.putExtra("user_connecte",(Serializable) user_connecte);
        startActivity(intent);
    }

    public void logout_from_profil(View view) {
        session.logoutUser();
    }
}

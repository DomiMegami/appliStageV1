package dominique.fr.jv_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import modeles.User;
import requests.LoginRequest;
import requests.RecupEmailRequest;
import requests.RecupPseudoRequest;
import requests.RegisterRequest;
import utilitaires.UserSessionManager;
import utilitaires.Utility;

public class ConnexionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private User new_user = new User();
    private ArrayList<String> listUserPreferences = new ArrayList<>();
    private ArrayList<Integer> listPreferencesBool = new ArrayList<>();
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new UserSessionManager(getApplicationContext());

        fragment = (Fragment)new FragmentConnexion();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }

    //affiche le fragment connexion ou inscription
    public void goToFragment(View view) {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final Float widthPixels = Float.valueOf(metrics.widthPixels);

        Button se_connecter = (Button)findViewById(R.id.se_connecter);
        Button s_inscrire = findViewById(R.id.s_inscrire);

        if(widthPixels >= 1200) {
            if (view == findViewById(R.id.se_connecter)){
                se_connecter.setTextSize(22);
                se_connecter.setTextColor(getResources().getColor(R.color.colorAccent));
                s_inscrire.setTextSize(18);
                s_inscrire.setTextColor(getResources().getColor(R.color.txt_sur_fond_clair));
                fragment = (Fragment)new FragmentConnexion();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            }
            else if(view == findViewById(R.id.s_inscrire)){
                s_inscrire.setTextSize(22);
                s_inscrire.setTextColor(getResources().getColor(R.color.colorAccent));
                se_connecter.setTextSize(18);
                se_connecter.setTextColor(getResources().getColor(R.color.txt_sur_fond_clair));
                fragment = (Fragment)new Fragment_Inscription_etape1();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            }
        }else {
            if (view == findViewById(R.id.se_connecter)) {
                se_connecter.setTextSize(18);
                se_connecter.setTextColor(getResources().getColor(R.color.colorAccent));
                s_inscrire.setTextSize(13);
                s_inscrire.setTextColor(getResources().getColor(R.color.txt_sur_fond_clair));
                fragment = (Fragment) new FragmentConnexion();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            } else if (view == findViewById(R.id.s_inscrire)) {
                s_inscrire.setTextSize(18);
                s_inscrire.setTextColor(getResources().getColor(R.color.colorAccent));
                se_connecter.setTextSize(13);
                se_connecter.setTextColor(getResources().getColor(R.color.txt_sur_fond_clair));
                fragment = (Fragment) new Fragment_Inscription_etape1();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            }
        }
    }

    //connexion
    public void logIn(View view){
        EditText et_pseudo = findViewById(R.id.editText_pseudo);
        EditText et_mdp = findViewById(R.id.editText_mdp);
        final String pseudo_entre = et_pseudo.getText().toString();
        final String mdp_entre = et_mdp.getText().toString();
        session = new UserSessionManager(getApplicationContext());
        final Integer test = 1;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        User user_connecte = new User();
                        Integer user_id = jsonResponse.getInt("user_id");
                        session.createUserLoginSession(user_id);
                        Intent intent = new Intent(ConnexionActivity.this, ProfilActivity.class);
                        ConnexionActivity.this.startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConnexionActivity.this);
                        builder.setMessage("login failed")
                                .setNegativeButton("retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(pseudo_entre, mdp_entre, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ConnexionActivity.this);
        queue.add(loginRequest);
    }

    //passer d'un fragment à l'autre
    public void goStep2(View view) {
        EditText et_new_pseudo = findViewById(R.id.editText_new_Pseudo);
        final String new_pseudo = et_new_pseudo.getText().toString();
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        Toast.makeText(ConnexionActivity.this, R.string.txt_info_pseudo_deja_pris, Toast.LENGTH_LONG).show();
                    }else{
                        new_user.setPseudo(new_pseudo);
                        fragment = (Fragment)new Fragment_Inscription_etape2();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_place, fragment);
                        fragmentTransaction.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RecupPseudoRequest recupPseudoRequest = new RecupPseudoRequest(new_pseudo, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ConnexionActivity.this);
        queue.add(recupPseudoRequest);
    }
    public void goStep3(View view) {
        EditText et_new_email = findViewById(R.id.editText_new_email);
        EditText et_verif_new_email = findViewById(R.id.editText_verif_new_email);
        final String new_email = et_new_email.getText().toString();
        final String verif_new_mail = et_verif_new_email.getText().toString();
        if(!new_email.equals(verif_new_mail)){
            Toast.makeText(this, R.string.txt_info_egalite_2_email, Toast.LENGTH_LONG).show();
        }else{
            if (!Utility.isValidEmailAddress(new_email)){
                Toast.makeText(this, R.string.txt_info_demande_verif_email, Toast.LENGTH_LONG).show();
            }else {
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(ConnexionActivity.this, R.string.txt_info_email_existe_se_connecter, Toast.LENGTH_LONG).show();
                            }else{
                                new_user.setEmail(new_email);
                                fragment = (Fragment)new Fragment_Inscription_etape3();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_place, fragment);
                                fragmentTransaction.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RecupEmailRequest recupEmailRequest = new RecupEmailRequest(new_email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ConnexionActivity.this);
                queue.add(recupEmailRequest);
            }
        }
    }
    public void goStep4(View view) {
        EditText et_new_mdp = findViewById(R.id.editText_new_mdp);
        EditText et_verif_new_mdp = findViewById(R.id.editText_verif_new_mdp);
        String new_mdp = et_new_mdp.getText().toString();
        String verif_new_mdp = et_verif_new_mdp.getText().toString();
        if(!new_mdp.equals(verif_new_mdp)){
            Toast.makeText(this, R.string.txt_info_egalite_2_mdp, Toast.LENGTH_LONG).show();
        }else{
            if (!Utility.isValidPassword(new_mdp)){
                Toast.makeText(this, R.string.txt_info_format_mdp, Toast.LENGTH_LONG).show();
            }else{
                new_user.setMotDePasse(new_mdp);
                fragment = (Fragment)new Fragment_Inscription_etape4();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            }
        }
    }
    public void goStep5(View view) {
        EditText et_telephone = findViewById(R.id.et_telephone);
        String telephone = et_telephone.getText().toString();

        new_user.setTelephone(telephone);
        fragment = (Fragment)new Fragment_Inscription_etape5();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();

    }
    public void validateRegister(View view) {
        CheckBox categorie_1 = findViewById(R.id.categ_1);
        if (categorie_1.isChecked()){
            listUserPreferences.add(categorie_1.getText().toString());
            listPreferencesBool.add(0,31);
        }else{
            listPreferencesBool.add(0,0);
        }
        CheckBox categorie_2 = findViewById(R.id.categ_2);
        if (categorie_2.isChecked()){
            listUserPreferences.add(categorie_2.getText().toString());
            listPreferencesBool.add(1,32);
        }else{
            listPreferencesBool.add(1,0);
        }
        CheckBox categorie_3 = findViewById(R.id.categ_3);
        if (categorie_3.isChecked()){
            listUserPreferences.add(categorie_3.getText().toString());
            listPreferencesBool.add(2,33);
        }else{
            listPreferencesBool.add(2,0);
        }
        CheckBox categorie_4 = findViewById(R.id.categ_4);
        if (categorie_4.isChecked()){
            listUserPreferences.add(categorie_4.getText().toString());
            listPreferencesBool.add(3,35);
        }else{
            listPreferencesBool.add(3,0);
        }
        if (listPreferencesBool.get(0) == 0 && listPreferencesBool.get(1) == 0 && listPreferencesBool.get(2) == 0) {
            Toast.makeText(getApplicationContext(), R.string.txt_info_au_moins_1_categ, Toast.LENGTH_LONG).show();
        }
        else{
            final String user_pseudo = new_user.getPseudo();
            final String user_email = new_user.getEmail();
            final String user_mdp = new_user.getMotDePasse();
            final String user_telephone = new_user.getTelephone();
            final Integer user_pref_1 = listPreferencesBool.get(0);
            final Integer user_pref_2 = listPreferencesBool.get(1);
            final Integer user_pref_3 = listPreferencesBool.get(2);
            final Integer user_pref_4 = listPreferencesBool.get(3);
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        Intent intent = new Intent(ConnexionActivity.this, ConnexionActivity.class);
                        ConnexionActivity.this.startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConnexionActivity.this);
                        builder.setMessage("register failed")
                                .setNegativeButton("retry", null)
                                .create()
                                .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(user_pseudo, user_email, user_mdp, user_telephone, user_pref_1, user_pref_2, user_pref_3,user_pref_4, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ConnexionActivity.this);
            queue.add(registerRequest);
        }
    }
    public void goPreviousFromStep2(View view) {
        fragment = (Fragment)new Fragment_Inscription_etape1();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }
    public void goPreviousFromStep3(View view) {
        fragment = (Fragment)new Fragment_Inscription_etape2();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }
    public void goPreviousFromStep4(View view) {
        fragment = (Fragment)new Fragment_Inscription_etape3();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }
    public void goPreviousFromStep5(View view) {
        fragment = (Fragment)new Fragment_Inscription_etape4();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }
    View.OnClickListener getOnClickDoSomething(final Button button){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String message = "Chekbox id = "+ button.getId();
                Toast.makeText(ConnexionActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };
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

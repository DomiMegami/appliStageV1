package dominique.fr.jv_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import modeles.User;
import requests.UpdateMdpRequest;
import utilitaires.Utility;

public class PopupModifMdp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_modif_mdp);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .8));
        final EditText et_ancien_mdp = (EditText) findViewById(R.id.et_ancien_mdp);
        final EditText et_new_mdp = (EditText) findViewById(R.id.et_new_mdp);
        final EditText et_verif_new_mdp = (EditText) findViewById(R.id.et_verif_new_mdp);
        Button btn_valid_modif_mdp = (Button) findViewById(R.id.btn_valid_modif_mdp);
        Intent intent = getIntent();
        final User user_connecte = (User) intent.getSerializableExtra("user_connecte");

        btn_valid_modif_mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int user_id = user_connecte.getId();
                String user_id_str = String.valueOf(user_id);
                String user_pseudo = user_connecte.getPseudo();
                String user_ancien_mdp = et_ancien_mdp.getText().toString();
                String user_new_mdp = et_new_mdp.getText().toString();
                String user_verif_new_mdp = et_verif_new_mdp.getText().toString();
                if (!user_new_mdp.equals(user_verif_new_mdp)) {
                    Toast.makeText(getApplicationContext(), R.string.txt_info_egalite_2_mdp, Toast.LENGTH_LONG).show();
                } else {
                    if (!Utility.isValidPassword(user_new_mdp)) {
                        Toast.makeText(getApplicationContext(), R.string.txt_info_format_mdp, Toast.LENGTH_LONG).show();
                    } else {
                        final Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), R.string.txt_confirm_mdp_modifié, Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PopupModifMdp.this);
                                        builder.setMessage(R.string.txt_ancien_mdp_incorrect)
                                                .setNegativeButton("retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }//end onresponse
                        };
                        UpdateMdpRequest updateMdpRequest = new UpdateMdpRequest(user_id_str, user_pseudo, user_ancien_mdp, user_new_mdp, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(PopupModifMdp.this);
                        queue.add(updateMdpRequest);
                    }
                }
            }//end onclick
        });
    }
    public void annulModif(View view) {
        finish();
    }
}

package requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dominique DURI on 16/02/2018.
 */

public class RecupEmailRequest extends StringRequest {

    //définir l'url de register.php
    private static final String RECUPEMAIL_REQUEST_URL = "http://sd-67292.dedibox.fr/~dominique.d/RecupEmail.php";
    //Créer une Map :
    //elle va servir à recuillir les différentes values qui correspondent aux champs de la bdd.
    private Map<String, String> params;

    //constructeur :
    public RecupEmailRequest(String new_email, Response.Listener<String> listener){
        //on va communiquer avec les fichiers php en post.
        super(Request.Method.POST, RECUPEMAIL_REQUEST_URL, listener, null);
        //initialisation de params :
        params = new HashMap<>();
        params.put("new_email", new_email);
    }

    //maintenant qu'on a alimenté params, on va créer un getter pour y acceder :
    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
package requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dominique DURI on 16/02/2018.
 */

public class UpdateMdpRequest extends StringRequest {

    //définir l'url de register.php
    private static final String LOGIN_REQUEST_URL = "http://sd-67292.dedibox.fr/~dominique.d/UpdateMdp.php";
    //Créer une Map :
    //elle va servir à recuillir les différentes values qui correspondent aux champs de la bdd. ILs seront envoyés en POST au fichier.php
    private Map<String, String> params;

    /*//constructeur :
    public UpdateMdpRequest(String user_id, String user_new_mdp, Response.Listener<String> listener){
        //on va communiquer avec les fichiers php en post.
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        //initialisation de params :
        params = new HashMap<>();
        //on 'put' les =/= values dans params. On est dans register, alors il faut 'put' name, user_pseudo, user_mdp et age :
        //on put dans params des values de type String
        //params.put("nom du champ", valeur); //valeur est donnée en argument du constructeur

        params.put("user_id", user_id);
        params.put("user_new_mdp", user_new_mdp);

    }*/


    // UpdateMdpRequest(user_id_str, user_pseudo, user_mdp, user_new_mdp, responseListener);
    public UpdateMdpRequest(String user_id, String user_pseudo, String user_mdp,String user_new_mdp, Response.Listener<String> listener){
        //on va communiquer avec les fichiers php en post.
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        //initialisation de params :
        params = new HashMap<>();
        //on 'put' les =/= values dans params. On est dans register, alors il faut 'put' name, user_pseudo, user_mdp et age :
        //on put dans params des values de type String
        //params.put("nom du champ", valeur); //valeur est donnée en argument du constructeur

        params.put("user_id", user_id);
        params.put("user_pseudo", user_pseudo);
        params.put("user_mdp", user_mdp);
        params.put("user_new_mdp", user_new_mdp);

    }

    //maintenant qu'on a alimenté params, on va créer un getter pour y acceder :
    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
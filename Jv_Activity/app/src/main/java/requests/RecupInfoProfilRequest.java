package requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dominique DURI on 16/02/2018.
 */

public class RecupInfoProfilRequest extends StringRequest {

    //définir l'url de register.php
    private static final String RECUP_INFO_PROFIL_REQUEST_URL = "http://sd-67292.dedibox.fr/~dominique.d/RecupInfoProfil.php";
    //Créer une Map :
    //elle va servir à recuillir les différentes values qui correspondent aux champs de la bdd. ILs seront envoyés en POST au fichier.php
    private Map<String, String> params;

    //constructeur :
    public RecupInfoProfilRequest(int user_id, Response.Listener<String> listener){
        //on va communiquer avec les fichiers php en post.
        super(Request.Method.POST, RECUP_INFO_PROFIL_REQUEST_URL, listener, null);
        //initialisation de params :
        params = new HashMap<>();
        //on 'put' les =/= values dans params. On est dans register, alors il faut 'put' name, user_pseudo, user_mdp et age :
        //on put dans params des values de type String
        //params.put("nom du champ", valeur); //valeur est donnée en argument du constructeur

        params.put("user_id", String.valueOf(user_id));


    }

    //maintenant qu'on a alimenté params, on va créer un getter pour y acceder :
    @Override
    public Map<String, String> getParams() {

        return params;
    }


}
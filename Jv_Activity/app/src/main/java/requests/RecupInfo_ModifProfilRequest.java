package requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Dominique DURI on 16/02/2018.
 */

public class RecupInfo_ModifProfilRequest extends StringRequest {

    private static final String RECUP_INFO_PROFIL_REQUEST_URL = "http://sd-67292.dedibox.fr/~dominique.d/RecupInfo_pourModifProfil.php";

    private Map<String, String> params;

    //constructeur :
    public RecupInfo_ModifProfilRequest(int user_id, Response.Listener<String> listener){

        super(Request.Method.POST, RECUP_INFO_PROFIL_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("user_id", String.valueOf(user_id));


    }

    //maintenant qu'on a alimenté params, on va créer un getter pour y acceder :
    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
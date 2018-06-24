package requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Dominique DURI on 16/02/2018.
 */

public class UpdateProfilRequest extends StringRequest {

    private static final String UPDATE_PROFIL_REQUEST_URL = "http://sd-67292.dedibox.fr/~dominique.d/UpdateProfil.php";
    private Map<String, String> params;


    //constructeur :
        public UpdateProfilRequest(Integer user_id, String user_pseudo,String user_new_pseudo, Integer user_pref_1, Integer user_pref_2, Integer user_pref_3,Integer user_pref_4, Response.Listener<String> listener){
        super(Method.POST, UPDATE_PROFIL_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id",  String.valueOf(user_id));
        params.put("user_pseudo", user_pseudo);
        params.put("user_new_pseudo", user_new_pseudo);
        params.put("user_pref_1", String.valueOf(user_pref_1));
        params.put("user_pref_2", String.valueOf(user_pref_2));
        params.put("user_pref_3", String.valueOf(user_pref_3));
        params.put("user_pref_4", String.valueOf(user_pref_4));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
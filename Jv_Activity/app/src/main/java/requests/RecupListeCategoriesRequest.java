package requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Dominique DURI on 16/02/2018.
 */

public class RecupListeCategoriesRequest extends StringRequest {

    private static final String RECUP_INFO_PROFIL_REQUEST_URL = "http://sd-67292.dedibox.fr/~dominique.d/RecupListeCategories.php";

    private Map<String, String> params;

    //constructeur :
    public RecupListeCategoriesRequest(Response.Listener<String> listener){

        super(Request.Method.POST, RECUP_INFO_PROFIL_REQUEST_URL, listener, null);

    }


    public Map<String, String> getParams() {

        return params;
    }
}
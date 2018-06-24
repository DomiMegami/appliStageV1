package dominique.fr.jv_activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class Fragment_Inscription_etape1 extends Fragment {


    public Fragment_Inscription_etape1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inscription_etape1, container, false);
    }

    public void goStep2(View view) {
        //a laisser pour ne pas avoir d'erreur
    }
}

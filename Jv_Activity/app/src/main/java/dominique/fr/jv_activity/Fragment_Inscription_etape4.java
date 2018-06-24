package dominique.fr.jv_activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_Inscription_etape4 extends Fragment {


    public Fragment_Inscription_etape4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inscription_etape4, container, false);
    }

    public void goPreviousFromStep4(View view){
        //required empty method
    }

    public void goStep5(View view) {
        //required empty method
    }
}

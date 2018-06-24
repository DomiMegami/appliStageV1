package dominique.fr.jv_activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingScreenActivity extends AppCompatActivity {
    private static int LOADSCREEN_TIME_OUT = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, LOADSCREEN_TIME_OUT );
    }
}

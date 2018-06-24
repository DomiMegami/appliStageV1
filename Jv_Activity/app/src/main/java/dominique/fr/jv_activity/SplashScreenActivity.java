package dominique.fr.jv_activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jaredrummler.android.widget.AnimatedSvgView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AnimatedSvgView svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        svgView.start();
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT );
    }
}

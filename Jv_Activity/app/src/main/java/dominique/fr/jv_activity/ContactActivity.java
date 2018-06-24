package dominique.fr.jv_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class ContactActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar pBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }



    public void SendEmailToAssociation(View view) {
        String e_mail_contact = getString(R.string.mailto_mail_asso);
        String e_mail_contact_organisateurs = getString(R.string.mail_to_orga_asso);
        Intent intent;
        pBar = findViewById(R.id.pBar);
        if (view == findViewById(R.id.e_mail_contact)){
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 10;
                        handler.post(new Runnable() {
                            public void run() {
                                pBar.setProgress(progressStatus);
                            }
                        });

                        try {
                            Thread.sleep(200);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            progressStatus = 0;
            intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(e_mail_contact));
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sujet_mail_asso));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.go_accueil:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.go_contact:
                intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.go_connexion:
                intent = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

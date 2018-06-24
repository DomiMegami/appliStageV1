package dominique.fr.jv_activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import adapters.Article_ArrayAdapter;
import modeles.Article;
import utilitaires.Utility;

public class ArticlesTriesParTheme extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<Article> listeArticles = new ArrayList<Article>();
    Article articleCourant;
    ListView listViewArticles;
    Article_ArrayAdapter articleArrayAdapter ;
    String strData = null;
    String urlApi ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_par_theme);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String titre_page = intent.getStringExtra("nom");
        Integer id =  intent.getIntExtra("id", 0);
        TextView tv_titre_page = findViewById(R.id.tvTitrePage);
        tv_titre_page.setText(titre_page);
        urlApi = "http://77.141.122.110/api/public/eventsbykeyword/" + id;
        listViewArticles = findViewById(R.id.lvArticles);
        articleArrayAdapter= new Article_ArrayAdapter(this, listeArticles);
        listViewArticles.setAdapter(articleArrayAdapter);
        try {
            new TacheChargerDonnes().execute(new URL(urlApi)) ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        listViewArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = listViewArticles.getItemAtPosition(i);
                articleCourant = (Article) o;
                Intent intent = new Intent(getApplicationContext(), DetailArticle.class);
                intent.putExtra("ActuEnvoyee", (Serializable) articleCourant);
                startActivity(intent);
            }
        });

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


    private class TacheChargerDonnes extends AsyncTask<URL, Void ,JSONArray> {

        @Override
        protected JSONArray doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK){
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    strData = builder.toString();
                    return new JSONArray(strData);
                }else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }
            return null;
        }
    @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Utility.convertirJSONEnArrayList(jsonArray, strData, listeArticles, articleArrayAdapter);
            articleArrayAdapter.notifyDataSetChanged();
        }
    }

}

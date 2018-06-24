package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dominique.fr.jv_activity.R;
import modeles.Article;
import utilitaires.Utility;

/**
 * Created by Dominique DURI on 12/01/2018.
 */

public class Article_ArrayAdapter extends ArrayAdapter<Article> {

    private TextView titre_article;
    private TextView tv_aff_dates_main;
    private String date_debut_str;
    private String date_fin_str;
    private String phrase_date_a_afficher ="";
    private ImageView image_article;
    private Map<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();


    public Article_ArrayAdapter(@NonNull Context context, @NonNull ArrayList<Article> objects) {
        super(context, -1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.item_liste_articles, parent, false);
        }
        tv_aff_dates_main = row.findViewById(R.id.tv_aff_dates_main);
        titre_article = row.findViewById(R.id.titre_article);
        image_article = row.findViewById(R.id.image_article);
        Article aAfficher = getItem(position);
        titre_article.setText(aAfficher.getTitre());
        date_debut_str = aAfficher.getDate_debut();
        date_fin_str = aAfficher.getDate_fin();
        date_debut_str = new Utility().formaterDateStr(date_debut_str);
        date_fin_str = new Utility().formaterDateStr(date_fin_str);
        if (date_fin_str.equals(date_debut_str)){
            phrase_date_a_afficher = "Le "+ date_debut_str;
        }
        else{
            phrase_date_a_afficher = "Du "+ date_debut_str + " au " + date_fin_str;
        }
        tv_aff_dates_main.setText(phrase_date_a_afficher);
        String url = aAfficher.getUrl_image();
        Glide.with(getContext()).load(url).into(image_article);
        //Picasso.with(getContext()).load(url).into(image_article);
        return row;
    }
}

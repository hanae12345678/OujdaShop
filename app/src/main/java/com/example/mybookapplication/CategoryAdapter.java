package com.example.mybookapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Récupérer la catégorie à la position actuelle
        Category category = getItem(position);

        // Vérifier si la vue est réutilisée, sinon l'inflater
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }

        // Récupérer les vues du layout
        ImageView categoryIconImageView = convertView.findViewById(R.id.categoryIconImageView);
        TextView categoryNameTextView = convertView.findViewById(R.id.categoryNameTextView);

        // Afficher les données de la catégorie
        categoryNameTextView.setText(category.getName());

        // Définir l'icône en fonction de la catégorie
        switch (category.getName()) {
            case "Littérature et Fiction":
                categoryIconImageView.setImageResource(R.drawable.literature);
                break;
            case "Science-Fiction et Fantasy":
                categoryIconImageView.setImageResource(R.drawable.fiction);
                break;
            case "Policier et Thriller":
                categoryIconImageView.setImageResource(R.drawable.policier);
                break;
            case "Développement Personnel et Motivation":
                categoryIconImageView.setImageResource(R.drawable.motivation);
                break;
            case "Science et Technologie":
                categoryIconImageView.setImageResource(R.drawable.technologie);
                break;
            case "Histoire et Biographies":
                categoryIconImageView.setImageResource(R.drawable.histoire);
                break;
            case "Économie et Finance":
                categoryIconImageView.setImageResource(R.drawable.finance);
                break;
            case "Santé et Bien-être":
                categoryIconImageView.setImageResource(R.drawable.sante);
                break;
            default:
                categoryIconImageView.setImageResource(R.drawable.livre);
                break;
        }

        return convertView;
    }
}

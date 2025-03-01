package com.example.mybookapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private Context context;
    private List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        super(context, R.layout.product_item, books);
        this.context = context;
        this.books = books;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Récupérer le livre à la position actuelle
        Book book = getItem(position);

        // Vérifier si la vue est réutilisée, sinon l'inflater
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        // Récupérer les vues du layout
        ImageView productImageView = convertView.findViewById(R.id.productImageView);
        TextView productNameTextView = convertView.findViewById(R.id.productNameTextView);
        TextView productPriceTextView = convertView.findViewById(R.id.productPriceTextView);

        // Afficher les données du livre
        productNameTextView.setText(book.getName());
        productPriceTextView.setText("Prix : " + book.getPrice() + " €");

        // Charger l'image du livre
        String imageName = book.getImage();
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResId != 0) {
            productImageView.setImageResource(imageResId);
        } else {
            // Image par défaut si l'image n'est pas trouvée
            productImageView.setImageResource(R.drawable.livre);
        }

        return convertView;
    }
}

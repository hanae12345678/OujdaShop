package com.example.mybookapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private TextView bookNameTextView, bookAuthorTextView, bookDescriptionTextView, bookPriceTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); // Assurez-vous que le layout est correct

        // Initialiser les vues
        bookNameTextView = findViewById(R.id.bookNameTextView); // ID correspondant
        bookAuthorTextView = findViewById(R.id.bookAuthorTextView); // ID correspondant
        bookDescriptionTextView = findViewById(R.id.bookDescriptionTextView); // ID correspondant
        bookPriceTextView = findViewById(R.id.bookPriceTextView); // ID correspondant

        // Initialiser DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID du livre depuis l'intent
        int bookId = getIntent().getIntExtra("bookId", -1);

        // Récupérer les détails du livre depuis la base de données
        if (bookId != -1) {
            Book book = dbHelper.getBookById(bookId);
            if (book != null) {
                displayBookDetails(book);
            }
        }
    }

    // Méthode pour afficher les détails du livre
    private void displayBookDetails(Book book) {
        bookNameTextView.setText(book.getName());
        bookAuthorTextView.setText("Auteur : " + book.getAuthor());
        bookDescriptionTextView.setText("Description : " + book.getDescription());
        bookPriceTextView.setText("Prix : " + book.getPrice() + " €");
    }
}
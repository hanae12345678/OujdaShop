package com.example.mybookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private GridView productGridView;
    private Button addProductButton, editProductButton, deleteProductButton;
    private DatabaseHelper dbHelper;
    private BookAdapter adapter;
    private List<Book> books;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Initialiser les vues
        productGridView = findViewById(R.id.productGridView);
        addProductButton = findViewById(R.id.addProductButton);
        editProductButton = findViewById(R.id.btnEditProduct);
        deleteProductButton = findViewById(R.id.btnDeleteProduct);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID de la catégorie depuis l'intent
        categoryId = getIntent().getIntExtra("categoryId", -1);

        // Rafraîchir la liste des livres
        refreshBooks();

        // Gestion du clic sur un livre
        productGridView.setOnItemClickListener((parent, view, position, id) -> {
            // Récupérer le livre sélectionné
            Book selectedBook = books.get(position);

            // Rediriger vers DetailsActivity avec l'ID du livre
            Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
            intent.putExtra("bookId", selectedBook.getId());
            startActivity(intent);
        });

        // Gestion des clics sur les boutons
        addProductButton.setOnClickListener(v -> showAddProductDialog());
        editProductButton.setOnClickListener(v -> showEditProductDialog());
        deleteProductButton.setOnClickListener(v -> deleteSelectedProduct());
    }

    private void refreshBooks() {
        books = dbHelper.getBooksByCategory(categoryId);
        adapter = new BookAdapter(this, books);
        productGridView.setAdapter(adapter);
    }

    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un livre");

        // Ajouter des champs de saisie
        final EditText inputName = new EditText(this);
        inputName.setHint("Nom du livre");

        final EditText inputAuthor = new EditText(this);
        inputAuthor.setHint("Auteur");

        final EditText inputDescription = new EditText(this);
        inputDescription.setHint("Description");

        final EditText inputPrice = new EditText(this);
        inputPrice.setHint("Prix");

        // Créer un LinearLayout pour organiser les champs
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputName);
        layout.addView(inputAuthor);
        layout.addView(inputDescription);
        layout.addView(inputPrice);
        builder.setView(layout);

        // Bouton Ajouter
        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String name = inputName.getText().toString();
            String author = inputAuthor.getText().toString();
            String description = inputDescription.getText().toString();
            double price = Double.parseDouble(inputPrice.getText().toString());

            if (!name.isEmpty() && !author.isEmpty() && !description.isEmpty()) {
                // Utiliser une image par défaut ou laisser le champ "image" vide
                Book book = new Book(0, name, author, description, price, "default_image", categoryId); // 0 pour l'ID (auto-incrémenté)
                dbHelper.addBook(book);
                refreshBooks(); // Rafraîchir la liste
            }
        });

        // Bouton Annuler
        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showEditProductDialog() {
        // Vérifier si un livre est sélectionné
        int selectedPosition = productGridView.getCheckedItemPosition();
        if (selectedPosition == GridView.INVALID_POSITION) {
            Toast.makeText(this, "Veuillez sélectionner un livre", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer le livre sélectionné
        Book selectedBook = books.get(selectedPosition);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modifier le livre");

        // Ajouter des champs de saisie pré-remplis
        final EditText inputName = new EditText(this);
        inputName.setText(selectedBook.getName());

        final EditText inputAuthor = new EditText(this);
        inputAuthor.setText(selectedBook.getAuthor());

        final EditText inputDescription = new EditText(this);
        inputDescription.setText(selectedBook.getDescription());

        final EditText inputPrice = new EditText(this);
        inputPrice.setText(String.valueOf(selectedBook.getPrice()));

        // Créer un LinearLayout pour organiser les champs
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputName);
        layout.addView(inputAuthor);
        layout.addView(inputDescription);
        layout.addView(inputPrice);
        builder.setView(layout);

        // Bouton Modifier
        builder.setPositiveButton("Modifier", (dialog, which) -> {
            String newName = inputName.getText().toString();
            String newAuthor = inputAuthor.getText().toString();
            String newDescription = inputDescription.getText().toString();
            double newPrice = Double.parseDouble(inputPrice.getText().toString());

            if (!newName.isEmpty() && !newAuthor.isEmpty() && !newDescription.isEmpty()) {
                selectedBook.setName(newName);
                selectedBook.setAuthor(newAuthor);
                selectedBook.setDescription(newDescription);
                selectedBook.setPrice(newPrice);
                dbHelper.updateBook(selectedBook);
                refreshBooks(); // Rafraîchir la liste
            }
        });

        // Bouton Annuler
        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void deleteSelectedProduct() {
        // Vérifier si un livre est sélectionné
        int selectedPosition = productGridView.getCheckedItemPosition();
        if (selectedPosition == GridView.INVALID_POSITION) {
            Toast.makeText(this, "Veuillez sélectionner un livre", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer le livre sélectionné
        Book selectedBook = books.get(selectedPosition);

        // Supprimer le livre
        dbHelper.deleteBook(selectedBook.getId());
        refreshBooks(); // Rafraîchir la liste
    }
}
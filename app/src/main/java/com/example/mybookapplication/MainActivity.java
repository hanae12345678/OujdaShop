package com.example.mybookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView categoryListView;
    private Button addCategoryButton, editCategoryButton, deleteCategoryButton;
    private DatabaseHelper dbHelper;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les vues
        categoryListView = findViewById(R.id.categoryListView);
        addCategoryButton = findViewById(R.id.addCategoryButton);
        editCategoryButton = findViewById(R.id.btnEditCategory);
        deleteCategoryButton = findViewById(R.id.btnDeleteCategory);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(this);

        // Rafraîchir la liste des catégories
        refreshCategories();

        // Gestion du clic sur une catégorie
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer la catégorie sélectionnée
                Category selectedCategory = (Category) parent.getItemAtPosition(position);

                // Rediriger vers ProductActivity avec la catégorie sélectionnée
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("categoryId", selectedCategory.getId());
                startActivity(intent);
            }
        });

        // Gestion des clics sur les boutons
        addCategoryButton.setOnClickListener(v -> showAddCategoryDialog());
        editCategoryButton.setOnClickListener(v -> showEditCategoryDialog());
        deleteCategoryButton.setOnClickListener(v -> deleteSelectedCategory());
    }

    private void refreshCategories() {
        List<Category> categories = dbHelper.getAllCategories();
        adapter = new CategoryAdapter(this, categories);
        categoryListView.setAdapter(adapter);
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une catégorie");

        // Ajouter un champ de saisie
        final EditText input = new EditText(this);
        input.setHint("Nom de la catégorie");
        builder.setView(input);

        // Bouton Ajouter
        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String categoryName = input.getText().toString();
            if (!categoryName.isEmpty()) {
                Category category = new Category();
                category.setName(categoryName);
                dbHelper.addCategory(category);
                refreshCategories(); // Rafraîchir la liste
            }
        });

        // Bouton Annuler
        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showEditCategoryDialog() {
        // Vérifier si une catégorie est sélectionnée
        int selectedPosition = categoryListView.getCheckedItemPosition();
        if (selectedPosition == ListView.INVALID_POSITION) {
            Toast.makeText(this, "Veuillez sélectionner une catégorie", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer la catégorie sélectionnée
        Category selectedCategory = (Category) categoryListView.getItemAtPosition(selectedPosition);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modifier la catégorie");

        // Ajouter un champ de saisie pré-rempli
        final EditText input = new EditText(this);
        input.setText(selectedCategory.getName());
        builder.setView(input);

        // Bouton Modifier
        builder.setPositiveButton("Modifier", (dialog, which) -> {
            String newName = input.getText().toString();
            if (!newName.isEmpty()) {
                selectedCategory.setName(newName);
                dbHelper.updateCategory(selectedCategory);
                refreshCategories(); // Rafraîchir la liste
            }
        });

        // Bouton Annuler
        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void deleteSelectedCategory() {
        // Vérifier si une catégorie est sélectionnée
        int selectedPosition = categoryListView.getCheckedItemPosition();
        if (selectedPosition == ListView.INVALID_POSITION) {
            Toast.makeText(this, "Veuillez sélectionner une catégorie", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer la catégorie sélectionnée
        Category selectedCategory = (Category) categoryListView.getItemAtPosition(selectedPosition);

        // Supprimer la catégorie
        dbHelper.deleteCategory(selectedCategory.getId());
        refreshCategories(); // Rafraîchir la liste
    }
}
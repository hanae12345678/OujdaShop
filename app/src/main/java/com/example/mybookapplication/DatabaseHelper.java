package com.example.mybookapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Informations sur la base de données
    private static final String DATABASE_NAME = "OujdaShop.db";
    private static final int DATABASE_VERSION = 2;

    // Table des catégories
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    private static final String COLUMN_BOOK_AUTHOR = "author";
    private static final String COLUMN_BOOK_DESCRIPTION = "description";
    private static final String COLUMN_BOOK_PRICE = "price";

    // Table des livres
    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_BOOK_ID = "id";
    private static final String COLUMN_BOOK_NAME = "name";
    private static final String COLUMN_BOOK_CATEGORY_ID = "category_id";
    private static final String COLUMN_BOOK_IMAGE = "image_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);





        String CREATE_CATEGORIES_TABLE = "CREATE TABLE categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)";
        db.execSQL(CREATE_CATEGORIES_TABLE);



        // Créer la table des livres
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BOOK_NAME + " TEXT,"
                + COLUMN_BOOK_AUTHOR + " TEXT,"
                + COLUMN_BOOK_DESCRIPTION + " TEXT,"
                + COLUMN_BOOK_PRICE + " REAL,"
                + COLUMN_BOOK_IMAGE + " TEXT," // Nouvelle colonne pour l'image
                + COLUMN_BOOK_CATEGORY_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_BOOK_CATEGORY_ID + ") REFERENCES "
                + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + "))";
        db.execSQL(CREATE_BOOKS_TABLE);

        // Insérer des données initiales
        insertInitialData(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer les tables existantes
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }

    public boolean addUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("email", email);
        values.put("password", password);

        // Insérer l'utilisateur dans la base de données
        long result = db.insert("users", null, values);
        db.close();

        // Retourner true si l'insertion a réussi, sinon false
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );

        boolean isValidUser = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValidUser;
    }

    // Insérer des données initiales
    private void insertInitialData(SQLiteDatabase db) {
        // Insérer des catégories
        insertCategory(db, "Littérature et Fiction");
        insertCategory(db, "Science-Fiction et Fantasy");
        insertCategory(db, "Policier et Thriller");
        insertCategory(db, "Développement Personnel et Motivation");
        insertCategory(db, "Science et Technologie");
        insertCategory(db, "Histoire et Biographies");
        insertCategory(db, "Économie et Finance");
        insertCategory(db, "Santé et Bien-être");

        // Insérer des livres pour chaque catégorie
        insertBook(db, "Les Misérables", "Victor Hugo (1862)", "...", 15.99, "les_miserables", 1);
        insertBook(db, "Germinal", "Émile Zola (1885)", "...", 12.99, "germinal", 1);
        insertBook(db, "Madame Bovary", "Gustave Flaubert (1857)", "...", 10.99, "bovary", 1);
        insertBook(db, "Le Rouge et le Noir", "Stendhal (1830)", "...", 9.99, "le_rouge_et_le_noir", 1);
        insertBook(db, "L'Étranger", "Albert Camus (1942)", "...", 8.99, "etranger", 1);




        //insertBook(db, "Dune ", "Frank Herbert", "Un chef-d'œuvre de la science-fiction qui suit Paul Atréides, héritier d’une grande maison noble, sur la planète désertique Arrakis. Entre intrigues politiques, prophéties et luttes de pouvoir, Paul découvre son destin dans un univers dominé par une précieuse ressource : l’Épice..", 14.99, 2);
        insertBook(db, "Fondation ", "Isaac Asimov (1951)", "Premier tome d’une saga mythique, ce roman raconte comment Hari Seldon, un scientifique, prédit la chute de l’Empire Galactique et met en place la \"Fondation\", une organisation destinée à réduire la période de chaos qui suivra. Une œuvre clé sur le destin des civilisations et la science des prédictions..", 18.99,"fondation",2);
        insertBook(db, "1984 ", "George Orwell (1949)", "Un roman dystopique où un régime totalitaire surveille et contrôle chaque aspect de la vie des citoyens. Winston Smith, un employé du Parti, commence à remettre en question la dictature de Big Brother, mais la liberté de penser a un prix… Un classique sur le danger du contrôle absolu et de la manipulation..", 14.99,"livre_1984", 2);
        insertBook(db, "Le Seigneur des Anneaux ", "J.R.R. Tolkien (1954-1955)", "Une épopée qui suit Frodon Sacquet, un Hobbit chargé de détruire l’Anneau Unique, un artefact maléfique convoité par Sauron, le Seigneur des Ténèbres. Accompagné de ses amis et alliés, il entreprend un voyage périlleux à travers la Terre du Milieu. Un pilier de la fantasy..", 14.99, "le_seigneur_des_anneaux",2);
        insertBook(db, "Harry Potter à l'école des sorciers ", "J.K. Rowling (1997)", "Premier tome de la célèbre saga, ce livre introduit Harry Potter, un jeune orphelin découvrant qu’il est un sorcier et qu’il est attendu à Poudlard, une école de magie. Entre amitié, mystères et affrontement avec le redoutable Voldemort, Harry débute son aventure magique.", 14.99,"harry_potter" ,2);


        insertBook(db, "Le Chien des Baskerville ", "Arthur Conan Doyle (1902)", "Une des enquêtes les plus célèbres de Sherlock Holmes. Le détective et son fidèle ami Watson tentent de résoudre le mystère entourant une malédiction familiale et un chien démoniaque qui hanterait la lande anglaise..", 14.99,"le_Chien_des_baskerville", 3);
        insertBook(db, "Les Dix Petits Nègres ", "Agatha Christie (1939)", "Dix personnes sont invitées sur une île isolée et commencent à être tuées une par une selon une comptine macabre. Un chef-d'œuvre du suspense et un des romans policiers les plus vendus au monde..", 18.99,"dix_petits_negres" ,3);
        insertBook(db, "Le Silence des Agneaux ", "Thomas Harris (1988)", "Clarice Starling, une jeune agente du FBI, doit interroger le terrifiant Hannibal Lecter, un tueur en série et psychiatre brillant, pour retrouver un autre criminel en liberté. Un thriller psychologique intense.", 14.99,"le_silence_des_agneaux", 3);
        insertBook(db, "Da Vinci Code ", "Dan Brown (2003)", "Robert Langdon, un professeur de symbologie, se retrouve plongé dans une course contre la montre pour décrypter un secret caché depuis des siècles, entre sociétés secrètes, messages codés et révélations historiques explosives.", 14.99,"da_vinci_code", 3);




        insertBook(db, "Père riche, père pauvre ", "Robert Kiyosaki (1997)", "Un livre incontournable sur l’éducation financière. L’auteur partage les leçons qu’il a apprises de ses deux \"pères\" : son père biologique, qui avait une approche classique de l’argent, et le père de son ami, un entrepreneur qui lui a enseigné comment penser comme un investisseur..", 14.99,"da_vinci_code", 4);
        insertBook(db, "Les 7 habitudes de ceux qui réalisent tout ce qu'ils entreprennent ", "Stephen R. Covey (1989)", "Un guide pratique qui explique comment adopter des habitudes efficaces pour réussir dans la vie professionnelle et personnelle. Covey propose une approche basée sur des principes intemporels comme la proactivité, la vision à long terme et l’organisation des priorités..", 18.99,"da_vinci_code", 4);
        insertBook(db, "L’Art de la victoire", "(Shoe Dog) – Phil Knight (2016)", "L’autobiographie inspirante du fondateur de Nike. Il raconte son parcours, de ses débuts modestes en vendant des chaussures de sport depuis le coffre de sa voiture, jusqu’à la création d’une des marques les plus puissantes du monde. Une leçon de persévérance et d’entrepreneuriat.", 14.99,"da_vinci_code", 4);


        insertBook(db, "Une brève histoire du temps", "Stephen Hawking (1988)", "Un best-seller qui explique de manière accessible les grands concepts de la physique moderne : trous noirs, Big Bang, relativité et mécanique quantique. Parfait pour ceux qui veulent comprendre l'univers sans être experts en maths..", 14.99,"da_vinci_code", 5);
        insertBook(db, "Le Gène : Une histoire intime ", "Siddhartha Mukherjee (2016)", "Un livre fascinant sur l’histoire de la génétique, expliquant comment nos gènes influencent notre santé, notre personnalité et notre avenir. Il aborde aussi les implications éthiques des avancées en génétique..", 18.99,"da_vinci_code",5);
        insertBook(db, "Elon Musk", "Walter Isaacson (2023)", "Une biographie détaillée du célèbre entrepreneur, explorant son génie, ses ambitions et les défis qu’il a rencontrés en construisant Tesla, SpaceX et d'autres entreprises révolutionnaires.", 14.99,"da_vinci_code", 5);


        insertBook(db, "Sapiens : Une brève histoire de l'humanité", "Yuval Noah Harari (2011)", "Un livre captivant qui retrace l’évolution de l’homme depuis la Préhistoire jusqu’à aujourd’hui, expliquant comment nos croyances, cultures et sociétés se sont développées.", 14.99, "da_vinci_code",6);
        insertBook(db, "Devenir", "Michelle Obama (2018)", "L’autobiographie inspirante de l’ancienne Première Dame des États-Unis, racontant son parcours, ses défis en tant que femme noire en politique et son engagement pour l’éducation et l’égalité..", 18.99,"da_vinci_code", 6);
        insertBook(db, "Napoléon : La biographie", "Max Gallo (1997)", "Une biographie détaillée de Napoléon Bonaparte, explorant son ascension, ses victoires, ses défaites et son impact durable sur l’histoire de France et du monde.", 14.99,"da_vinci_code", 6);

        insertBook(db, "L'Investisseur intelligent", "Benjamin Graham (1949)", "Un livre culte sur l’investissement, écrit par le mentor de Warren Buffett. Il explique comment analyser les actions et investir prudemment en bourse sur le long terme.", 14.99, "da_vinci_code",7);
        insertBook(db, "Père riche, père pauvre", "Robert Kiyosaki (1997)", "Un best-seller sur l’éducation financière, expliquant la différence entre les mentalités des riches et des pauvres et comment mieux gérer son argent pour atteindre l’indépendance financière.", 18.99, "da_vinci_code",7);
        insertBook(db, "Capital et idéologie", "Thomas Piketty (2019)", "Une analyse de l’évolution des inégalités économiques et des systèmes politiques qui les ont façonnées, avec des propositions pour un modèle plus équitable.", 14.99,"da_vinci_code", 7);



        insertBook(db, "Pourquoi nous dormons", "Matthew Walker (2017)", "Un livre fascinant sur l’importance du sommeil, expliquant comment il influence notre santé, notre mémoire et notre longévité, avec des conseils pour mieux dormir.", 14.99,"da_vinci_code", 8);
        insertBook(db, "Le Charme discret de l’intestin", "Giulia Enders (2014)", "Un ouvrage ludique et scientifique sur le rôle fondamental de notre intestin, expliquant comment il impacte notre digestion, notre humeur et notre système immunitaire.", 18.99,"da_vinci_code", 8);
        insertBook(db, "Miracle Morning", "Hal Elrod (2012)", "Un guide pratique sur l’importance d’une routine matinale optimisée pour améliorer sa productivité, son bien-être et atteindre ses objectifs personnels.",18.99,"da_vinci_code", 8);

    }

    // Insérer une catégorie
    private void insertCategory(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, name);
        db.insert(TABLE_CATEGORIES, null, values);
    }

    // Insérer un livre
    private void insertBook(SQLiteDatabase db, String name, String author, String description, double price, String image, int categoryId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_NAME, name);
        values.put(COLUMN_BOOK_AUTHOR, author);
        values.put(COLUMN_BOOK_DESCRIPTION, description);
        values.put(COLUMN_BOOK_PRICE, price);
        values.put(COLUMN_BOOK_IMAGE, image); // Ajouter l'image
        values.put(COLUMN_BOOK_CATEGORY_ID, categoryId);
        db.insert(TABLE_BOOKS, null, values);
    }


    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        db.insert("categories", null, values);
        db.close();
    }

    // Récupérer toutes les catégories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name FROM categories", null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                categories.add(category);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categories;
    }
    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());

        return db.update("categories", values, "id=?", new String[]{String.valueOf(category.getId())});
    }

    public void deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("categories", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", book.getName());
        values.put("author", book.getAuthor());
        values.put("description", book.getDescription());
        values.put("price", book.getPrice());
        values.put("image", book.getImage()); // Utiliser une image par défaut ou laisser vide
        values.put("category_id", book.getCategoryId());
        db.insert("books", null, values);
        db.close();
    }


    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", book.getName());
        values.put("price", book.getPrice());
        values.put("image", book.getImage());
        values.put("category_id", book.getCategoryId());

        return db.update("books", values, "id=?", new String[]{String.valueOf(book.getId())});
    }
    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("books", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
    // Récupérer les livres d'une catégorie
    public List<Book> getBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Requête pour récupérer les livres par catégorie (avec la colonne "image")
        Cursor cursor = db.rawQuery(
                "SELECT id, name, author, description, price, image, category_id FROM books WHERE category_id=?",
                new String[]{String.valueOf(categoryId)}
        );

        if (cursor.moveToFirst()) {
            do {
                // Récupérer les valeurs depuis le curseur
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String author = cursor.getString(2);
                String description = cursor.getString(3);
                double price = cursor.getDouble(4);
                String image = cursor.getString(5); // Récupérer l'image
                int bookCategoryId = cursor.getInt(6);

                // Créer un nouvel objet Book
                books.add(new Book(id, name, author, description, price, image, bookCategoryId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return books;
    }

    public Book getBookById(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Book book = null;

        Cursor cursor = db.query(
                TABLE_BOOKS,
                new String[]{COLUMN_BOOK_ID, COLUMN_BOOK_NAME, COLUMN_BOOK_AUTHOR, COLUMN_BOOK_DESCRIPTION, COLUMN_BOOK_PRICE, COLUMN_BOOK_IMAGE, COLUMN_BOOK_CATEGORY_ID},
                COLUMN_BOOK_ID + "=?",
                new String[]{String.valueOf(bookId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_NAME));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_AUTHOR));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_DESCRIPTION));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PRICE));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMAGE)); // Récupérer l'image
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_CATEGORY_ID));
            book = new Book(id, name, author, description, price, image, categoryId);
        }

        cursor.close();
        return book;
    }
}
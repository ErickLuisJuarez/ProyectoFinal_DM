package com.example.fastfoodorderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FastFoodDB";
    private static final int DATABASE_VERSION = 1;

    // TABLA USUARIOS
    private static final String TABLE_USERS = "usuarios";

    private static final String COL_NOMBRE = "nombre";
    private static final String COL_USER_ID = "id";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    // TABLA PEDIDOS
    private static final String TABLE_PEDIDOS = "pedidos";

    private static final String COL_PEDIDO_ID = "id";
    private static final String COL_FECHA = "fecha";
    private static final String COL_HAMBURGUESA = "hamburguesa";
    private static final String COL_PAPAS = "papas";
    private static final String COL_REFRESCO = "refresco";
    private static final String COL_HELADO = "helado";
    private static final String COL_TOTAL = "total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUsersTable =
                "CREATE TABLE " + TABLE_USERS + "("
                        + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COL_NOMBRE + " TEXT,"
                        + COL_EMAIL + " TEXT,"
                        + COL_PASSWORD + " TEXT"
                        + ")";

        String createPedidosTable =
                "CREATE TABLE " + TABLE_PEDIDOS + "("
                        + COL_PEDIDO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COL_FECHA + " TEXT,"
                        + COL_HAMBURGUESA + " INTEGER,"
                        + COL_PAPAS + " INTEGER,"
                        + COL_REFRESCO + " INTEGER,"
                        + COL_HELADO + " INTEGER,"
                        + COL_TOTAL + " REAL"
                        + ")";

        db.execSQL(createUsersTable);
        db.execSQL(createPedidosTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDOS);

        onCreate(db);
    }

    // REGISTRAR USUARIO

    public boolean registrarUsuario(String nombre, String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_NOMBRE, nombre);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);

        return result != -1;
    }

    // LOGIN

    public boolean loginUsuario(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE email=? AND password=?",
                new String[]{email, password}
        );

        boolean existe = cursor.getCount() > 0;

        cursor.close();

        return existe;
    }

    // INSERTAR PEDIDO

    public boolean insertarPedido(Pedido pedido) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_FECHA, pedido.getFecha());
        values.put(COL_HAMBURGUESA, pedido.getHamburguesa());
        values.put(COL_PAPAS, pedido.getPapas());
        values.put(COL_REFRESCO, pedido.getRefresco());
        values.put(COL_HELADO, pedido.getHelado());
        values.put(COL_TOTAL, pedido.getTotal());

        long result = db.insert(TABLE_PEDIDOS, null, values);

        return result != -1;
    }

    public java.util.ArrayList<Pedido> obtenerPedidos() {

        java.util.ArrayList<Pedido> listaPedidos = new java.util.ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_PEDIDOS + " ORDER BY id DESC",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                Pedido pedido = new Pedido();

                pedido.setId(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(COL_PEDIDO_ID)
                        )
                );

                pedido.setFecha(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(COL_FECHA)
                        )
                );

                pedido.setHamburguesa(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(COL_HAMBURGUESA)
                        )
                );

                pedido.setPapas(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(COL_PAPAS)
                        )
                );

                pedido.setRefresco(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(COL_REFRESCO)
                        )
                );

                pedido.setHelado(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(COL_HELADO)
                        )
                );

                pedido.setTotal(
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(COL_TOTAL)
                        )
                );

                listaPedidos.add(pedido);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaPedidos;
    }
}
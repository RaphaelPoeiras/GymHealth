package com.developer.asx.gymhealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    public static final String KEY_ROWID_TB_ALIMENTO = "_id";
    public static final String KEY_NAME_TB_ALIMENTO = "nome";

    public static final String KEY_ROWID_TB_EXERCICIO = "_id";
    public static final String KEY_NAME_TB_EXERCICIO = "nome";
    public static final String KEY_REPETICAO_TB_EXERCICIO = "repeticoes";

    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "DBHealth";
    private static final String DATABASE_TABLE_ALIMENTO = "alimento";
    private static final String DATABASE_TABLE_EXERCICIOS = "exercicio";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_ALIMENTO_CREATE =
        "create table alimento (_id integer primary key autoincrement, "
        + "nome text not null);";

    private static final String TABLE_EXERCICIO_CREATE =
            "create table exercicio (_id integer primary key autoincrement, "
                    + "nome text not null, repeticoes text not null);";

    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	try {
        		db.execSQL(TABLE_ALIMENTO_CREATE);
                db.execSQL(TABLE_EXERCICIO_CREATE);
            } catch (SQLException e) {
        		e.printStackTrace();
        	}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS alimento");
            db.execSQL("DROP TABLE IF EXISTS exercicio");
            onCreate(db);
        }
    }    

    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() 
    {
        DBHelper.close();
    }

    public long insertAlimento(String nome)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME_TB_ALIMENTO, nome);
        return db.insert(DATABASE_TABLE_ALIMENTO, null, initialValues);
    }

    public long insertExercicio(String nome, String repeticoes)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME_TB_EXERCICIO, nome);
        initialValues.put(KEY_REPETICAO_TB_EXERCICIO, repeticoes);
        return db.insert(DATABASE_TABLE_EXERCICIOS, null, initialValues);
    }

    public boolean deleteAlimento(long rowId)
    {
        return db.delete(DATABASE_TABLE_ALIMENTO, KEY_ROWID_TB_ALIMENTO + "=" + rowId, null) > 0;
    }

    public boolean deleteExercicio(long rowId)
    {
        return db.delete(DATABASE_TABLE_EXERCICIOS, KEY_ROWID_TB_EXERCICIO + "=" + rowId, null) > 0;
    }

    public Cursor BuscarExercicios()
    {
        return db.query(DATABASE_TABLE_EXERCICIOS, new String[] {KEY_ROWID_TB_EXERCICIO, KEY_NAME_TB_EXERCICIO,
                KEY_REPETICAO_TB_EXERCICIO}, null, null, null, null, null);
    }

    public Cursor BuscarAlimentos()
    {
        return db.query(DATABASE_TABLE_ALIMENTO, new String[] {KEY_ROWID_TB_ALIMENTO, KEY_NAME_TB_ALIMENTO}, null, null, null, null, null);
    }

    public Cursor ObterAlimentoPorId(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_ALIMENTO, new String[] {KEY_ROWID_TB_ALIMENTO,
                                KEY_NAME_TB_ALIMENTO}, KEY_ROWID_TB_ALIMENTO+ "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor ObterExercicioPorId(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_EXERCICIOS, new String[] {KEY_ROWID_TB_EXERCICIO,
                                KEY_NAME_TB_EXERCICIO, KEY_REPETICAO_TB_EXERCICIO}, KEY_ROWID_TB_EXERCICIO + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateExercicio(long rowId, String nome, String repeticoes)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME_TB_EXERCICIO, nome);
        args.put(KEY_REPETICAO_TB_EXERCICIO, repeticoes);
        return db.update(DATABASE_TABLE_EXERCICIOS, args, KEY_ROWID_TB_EXERCICIO + "=" + rowId, null) > 0;
    }

    public boolean updateAlimento(long rowId, String nome)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME_TB_ALIMENTO, nome);
        return db.update(DATABASE_TABLE_ALIMENTO, args, KEY_ROWID_TB_ALIMENTO + "=" + rowId, null) > 0;
    }
}

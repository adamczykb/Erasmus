package zsp.erasmus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by julupukki on 04.12.17.
 */

public class Database extends SQLiteOpenHelper {

    private final static String DB_NAME="E_DB";
    private final static int DB_VERSON=1;
    private SQLiteDatabase dab;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "EMAIL TEXT," +
                "PASS TEXT);");
        db.execSQL("CREATE TABLE SETTINGS(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "STATUS INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

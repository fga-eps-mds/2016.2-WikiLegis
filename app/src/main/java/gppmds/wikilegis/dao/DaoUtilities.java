package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by marcelo on 9/13/16.
 */
public class DaoUtilities {

    protected static DatabaseHelper database;
    protected static SQLiteDatabase sqliteDatabase;
    protected static Context context;

    //Método para inserir no banco local
    protected long insertAndClose(final SQLiteDatabase sqLiteDatabase, final String table,
                                  final ContentValues values) {

        sqLiteDatabase.insert(table, null, values);
        long resultInsert = 1;

        sqLiteDatabase.close();

        return resultInsert;
    }

    //Metodo para deletar do banco local
    protected long deleteAndClose(final SQLiteDatabase sqLiteDatabase, final String table) {
        int delete;

        delete = sqLiteDatabase.delete(table, null, null);

        sqLiteDatabase.close();

        return delete;
    }

}

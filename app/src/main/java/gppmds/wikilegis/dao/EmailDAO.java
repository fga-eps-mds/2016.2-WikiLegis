package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by marcelo on 9/15/16.
 */
public class EmailDAO extends DaoUtilities{

    private static String tableColumns[] = {"email"};

    private static EmailDAO instance;

    private static String tableName = "Email";

    private EmailDAO(Context context) {
        EmailDAO.database = new DatabaseHelper(context);
    }

    public static EmailDAO getInstance(Context context) {
        if (EmailDAO.instance != null) {
            //nothing to do
        } else {
            EmailDAO.instance = new EmailDAO(context);
        }
        return EmailDAO.instance;
    }

    public boolean isDatabaseEmpty() {

        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT 1 FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        boolean isEmpty = false;

        if (cursor != null) {
            if (cursor.getCount() <= 0) {
                cursor.moveToFirst();
                isEmpty = true;

            } else {
                //nothing to do
            }

        } else {

            isEmpty = true;
        }

        return isEmpty;
    }

    public boolean insertEmail(String email) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[0], email);

        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllEmails(List<String> emailList) {
        Iterator<String> index = emailList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = insertEmail(index.next());
        }

        return result;
    }

    public long deleteAllEmails() {
        long result;

        result = deleteAndClose(sqliteDatabase, tableName);

        return result;
    }

    public List<String> getAllEmails() {

        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<String> emailList = new ArrayList<String>();

        while (cursor.moveToNext()) {

            String email = cursor.getString(cursor.getColumnIndex(tableColumns[0]));

            emailList.add(email);
        }

        //sqliteDatabase.close();

        return emailList;
    }

}

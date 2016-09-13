package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import gppmds.wikilegis.model.Bill;

/**
 * Created by marcelo on 9/13/16.
 */
public class BillDAO extends DaoUtilities{

    private static String tableColumns[] = {"id", "title", "epigraph", "description", "theme",
    "amountParticipants", "amountProposals", "status"};

    private static BillDAO instance;

    private static String tableName = "Bill";

    private BillDAO(Context context) {
        BillDAO.database = new DatabaseHelper(context);
    }

    public static BillDAO getInstance(Context context) {
        if (BillDAO.instance != null) {
            //nothing to do
        } else {
            BillDAO.instance = new BillDAO(context);
        }
        return BillDAO.instance;
    }

    public boolean  isDatabaseEmpty(){
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

    public boolean insertBill(Bill bill) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[0], bill.getId());
        values.put(tableColumns[1], bill.getTitle());
        values.put(tableColumns[2], bill.getEpigraph());
        values.put(tableColumns[3], bill.getDescription());
        values.put(tableColumns[4], bill.getTheme());
        //values.put(tableColumns[5], bill.getAmountParticipants());
        //values.put(tableColumns[7], bill.getAmountProposals());
        values.put(tableColumns[7], bill.getStatus());


        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }


}

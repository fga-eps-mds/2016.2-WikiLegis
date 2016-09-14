package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gppmds.wikilegis.exception.BillException;
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

    public boolean insertBill(Bill bill) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[0], bill.getId());
        values.put(tableColumns[1], bill.getTitle());
        values.put(tableColumns[2], bill.getEpigraph());
        values.put(tableColumns[3], bill.getDescription());
        values.put(tableColumns[4], bill.getTheme());
        values.put(tableColumns[5], 0);
        values.put(tableColumns[6], bill.getNumberOfPrposals());
        values.put(tableColumns[7], bill.getStatus());


        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllBills(List<Bill> billList) {
        Iterator<Bill> index = billList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = insertBill(index.next());
        }

        return result;
    }

    public long deleteAllBills() {
        long result;

        result = deleteAndClose(sqliteDatabase, tableName);

        return result;
    }

    public List<Bill> getAllBills() throws BillException {

        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<Bill> billList = new ArrayList<Bill>();

        while (cursor.moveToNext()) {

            Bill bill = new Bill(cursor.getColumnIndex(tableColumns[0]),
                    cursor.getString(cursor.getColumnIndex(tableColumns[1])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[7])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[3])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[4])),
                    cursor.getColumnIndex(tableColumns[6]),
                    cursor.getColumnIndex(tableColumns[7])
            );

            billList.add(bill);
        }

        //sqliteDatabase.close();

        return billList;
    }

}

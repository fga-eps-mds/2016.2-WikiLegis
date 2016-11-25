package gppmds.wikilegis.dao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;

public class BillDAO extends DaoUtilities{

    private static String tableColumns[] = {"id", "title", "epigraph", "description", "theme",
    "amountProposals", "status", "date"};

    private static BillDAO instance;

    private static String tableName = "Bill";

    private BillDAO(final Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        DaoUtilities.setDatabase(databaseHelper);
    }

    public static BillDAO getInstance(final Context context) {
        if (BillDAO.instance != null) {
            //nothing to do
        } else {
            BillDAO.instance = new BillDAO(context);
        }
        return BillDAO.instance;
    }

    public boolean isDatabaseEmpty() {

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

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

    public boolean insertBill(final Bill bill) {

        SQLiteDatabase sqLiteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[0], bill.getId());
        values.put(tableColumns[1], bill.getTitle());
        values.put(tableColumns[2], bill.getEpigraph());
        values.put(tableColumns[3], bill.getDescription());
        values.put(tableColumns[4], bill.getTheme());
        values.put(tableColumns[5], bill.getNumberOfPrposals());
        values.put(tableColumns[6], bill.getStatus());
        values.put(tableColumns[7], bill.getDate());

        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllBills(final List<Bill> billList) {
        Iterator<Bill> index = billList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = insertBill(index.next());
        }

        return result;
    }

    public long deleteAllBills() {
        long result;

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        result = deleteAndClose(sqliteDatabase, tableName);

        return result;
    }

    public List<Bill> getAllBills() throws BillException {

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<Bill> billList = new ArrayList<Bill>();

        while (cursor.moveToNext()) {

            Bill bill = new Bill(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[1])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[6])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[3])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[4])),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[5]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[7]))));


            billList.add(bill);
        }

        return billList;
    }

    public Bill getBillById(final Integer id) throws BillException {

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE [id] = " + id.toString();

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        Bill bill = null;

        while (cursor.moveToNext()) {
            bill = new Bill(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[1])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[6])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[3])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[4])),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[5]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[7]))));
        }

        return bill;
    }

    public List<Bill> getSearchBills(String queryBill) throws BillException {

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE [title] LIKE '%" + queryBill + "%' OR [description] LIKE '%" + queryBill +"%'";

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<Bill> billList = new ArrayList<Bill>();

        while (cursor.moveToNext()) {

            Bill bill = new Bill(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[1])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[6])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[3])),
                    cursor.getString(cursor.getColumnIndex(tableColumns[4])),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[5]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[7]))));


            billList.add(bill);
        }

        return billList;
    }
}

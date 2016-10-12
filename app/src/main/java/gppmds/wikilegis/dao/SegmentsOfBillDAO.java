package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentsOfBillException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;

/**
 * Created by marcelo on 9/16/16.
 */
public class SegmentsOfBillDAO extends  DaoUtilities{

    private static String tableColumns[] = {"id", "idSegment", "idBill"};

    private static SegmentsOfBillDAO instance;

    private static String tableName = "SegmentsBill";

    private SegmentsOfBillDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        DaoUtilities.setDatabase(databaseHelper);
    }

    public static SegmentsOfBillDAO getInstance(Context context) {
        if (SegmentsOfBillDAO.instance != null) {
            //nothing to do
        } else {
            SegmentsOfBillDAO.instance = new SegmentsOfBillDAO(context);
        }
        return SegmentsOfBillDAO.instance;
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

    public boolean insertSegmentsOfBill(SegmentsOfBill segmentsOfBill) {

        SQLiteDatabase sqLiteDatabase = DaoUtilities.getDatabase().getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[1], segmentsOfBill.getIdSegment());
        values.put(tableColumns[2], segmentsOfBill.getIdBill());

        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllSegmentsOfBills(List<Bill> billList) throws SegmentException {

        boolean result = true;

        for(int i = 0; i < billList.size(); i++) {
            Log.d("PRIMEIRO FOR !!!!", "");
            for(int j=0; j<billList.get(i).getSegments().size(); j++) {
                Log.d("ENTEI NO FOR??? ", "");
                SegmentsOfBill segmentsOfBill = null;
                try {
                    segmentsOfBill = new SegmentsOfBill(billList.get(i).getId(),
                            billList.get(i).getSegments().get(j));
                } catch (SegmentsOfBillException e) {
                    e.printStackTrace();
                }
                result = insertSegmentsOfBill(segmentsOfBill);
            }
        }
        return result;
    }

    public long deleteAllSegmentsOfBill() {
        long result = 0;

        SQLiteDatabase sqLiteDatabase = DaoUtilities.getDatabase().getReadableDatabase();
        DaoUtilities daoUtilities = new DaoUtilities();

        result = daoUtilities.deleteAndClose(sqLiteDatabase, tableName);

        return result;
    }

    public List<SegmentsOfBill> getAllSegments() throws SegmentException {

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<SegmentsOfBill> segmentsOfBillList = new ArrayList<SegmentsOfBill>();

        while (cursor.moveToNext()) {

            SegmentsOfBill segmentsOfBill = null;
            try {
                segmentsOfBill = new SegmentsOfBill(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[2]))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))));
            } catch (SegmentsOfBillException e) {
                e.printStackTrace();
            }

            segmentsOfBillList.add(segmentsOfBill);
        }

        //sqliteDatabase.close();

        return segmentsOfBillList;
    }

    public List<SegmentsOfBill> getAllSegmentsOfBill(Integer idBill) {

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String idBillAux = idBill.toString();

        String query = "SELECT * FROM " + tableName + " WHERE [idBill] = " + idBillAux;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<SegmentsOfBill> segmentsOfBillList = new ArrayList<SegmentsOfBill>();

        while (cursor.moveToNext()) {

            SegmentsOfBill segmentsOfBill = null;
            try {
                segmentsOfBill = new SegmentsOfBill(Integer.parseInt(cursor.getString(cursor.
                        getColumnIndex(tableColumns[2]))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))));
            } catch (SegmentsOfBillException e) {
                e.printStackTrace();
            }

            segmentsOfBillList.add(segmentsOfBill);
        }

        return segmentsOfBillList;
    }

    public boolean clearSegmentsOfBillDaoTable(){
        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getWritableDatabase();
        sqliteDatabase.delete("["+tableName+"]", null, null);

        boolean isEmpty = isDatabaseEmpty();

        return isEmpty;
    }
}

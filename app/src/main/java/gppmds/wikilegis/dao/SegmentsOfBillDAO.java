package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;

/**
 * Created by marcelo on 9/16/16.
 */
public class SegmentsOfBillDAO extends  DaoUtilities{

    private static String tableColumns[] = {"idSegment", "idBill", "position"};

    private static SegmentsOfBillDAO instance;

    private static String tableName = "SegmentsBill";

    private SegmentsOfBillDAO(Context context) {
        BillDAO.database = new DatabaseHelper(context);
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

    public boolean insertSegmentsOfBill(SegmentsOfBill segmentsOfBill) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[0], segmentsOfBill.getIdSegment());
        values.put(tableColumns[1], segmentsOfBill.getIdBill());
        values.put(tableColumns[2], segmentsOfBill.getPosition());

        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllSegmentsOfBills(List<Bill> billList) {

        boolean result = true;

        for(int i = 0; i < billList.size(); i++) {
            for(int j=0; j<billList.get(i).getSegments().size(); j++) {
                SegmentsOfBill segmentsOfBill = new SegmentsOfBill(billList.get(i).getId(),
                        billList.get(i).getSegments().get(j), j);
                result = insertSegmentsOfBill(segmentsOfBill);
            }
        }
        return result;
    }

    public long deleteAllSegmentsOfBill() {
        long result;

        result = deleteAndClose(sqliteDatabase, tableName);

        return result;
    }

    public List<SegmentsOfBill> getAllSegments() {

        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<SegmentsOfBill> segmentsOfBillList = new ArrayList<SegmentsOfBill>();

        while (cursor.moveToNext()) {

            SegmentsOfBill segmentsOfBill = new SegmentsOfBill(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[2]))));

            segmentsOfBillList.add(segmentsOfBill);
        }

        //sqliteDatabase.close();

        return segmentsOfBillList;
    }

    public List<SegmentsOfBill> getAllSegmentsOfBill(Integer idBill) {

        sqliteDatabase = database.getReadableDatabase();

        String idBillAux = idBill.toString();

        String query = "SELECT * FROM " + tableName + " WHERE [idBill] = " + idBillAux;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<SegmentsOfBill> segmentsOfBillList = new ArrayList<SegmentsOfBill>();

        while (cursor.moveToNext()) {

            SegmentsOfBill segmentsOfBill = new SegmentsOfBill(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[2]))));

            segmentsOfBillList.add(segmentsOfBill);
        }

        //sqliteDatabase.close();

        return segmentsOfBillList;
    }
}

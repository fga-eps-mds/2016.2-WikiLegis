package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

/**
 * Created by marcelo on 9/13/16.
 */
public class SegmentDAO extends DaoUtilities{

    private static String tableColumns[] = {"id", "orderNaoPode", "idBill", "original", "replaced",
            "parent", "type", "number", "content", "firstNameAuthor", "secondNameAuthor", "creationDate", "creationSegment"};

    private static SegmentDAO instance;

    private static String tableName = "Segments";

    private SegmentDAO(Context context) {
        SegmentDAO.database = new DatabaseHelper(context);
    }

    public static SegmentDAO getInstance(Context context) {
        if (SegmentDAO.instance != null) {
            //nothing to do
        } else {
            SegmentDAO.instance = new SegmentDAO(context);
        }
        return SegmentDAO.instance;
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

    public boolean insertSegment(Segment segment) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        ContentValues values = new ContentValues();

        SegmentController segmentController = SegmentController.getInstance(context);

        String contentWhitType = segmentController.addingTypeContent(segment);

        values.put(tableColumns[0], segment.getId());
        values.put(tableColumns[1], segment.getOrder());
        values.put(tableColumns[2], segment.getBill());
        values.put(tableColumns[3], segment.isOriginal());
        values.put(tableColumns[4], segment.getReplaced());
        values.put(tableColumns[5], segment.getParent());
        values.put(tableColumns[6], segment.getType());
        values.put(tableColumns[7], segment.getNumber());
        values.put(tableColumns[8], contentWhitType);
        values.put(tableColumns[9], "FirstName");
        values.put(tableColumns[10], "SecondName");
        values.put(tableColumns[11], 1);
        values.put(tableColumns[12], segment.getDate());


        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllSegments(List<Segment> segmentList) {
        Iterator<Segment> index = segmentList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = insertSegment(index.next());
        }

        return result;
    }

    public long deleteAllSegments() {
        long result;

        result = deleteAndClose(sqliteDatabase, tableName);

        return result;
    }

    public Segment getSegmentById(Integer id) throws SegmentException {
        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE \"id\" = " + id.toString();

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        Segment segment=null;

        while (cursor.moveToNext()) {

            segment = new Segment(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[2]))),
                    Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[5]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[6]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[7]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[8])),
                    0,
                    0,
                    0,cursor.getString(cursor.getColumnIndex(tableColumns[12]))
            );
        }
        cursor.close();
        //sqliteDatabase.close();

        return segment;
    }

    public List<Segment> getAllSegments() throws SegmentException {

        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<Segment> segmentList = new ArrayList<Segment>();

        while (cursor.moveToNext()) {

            Segment segment = new Segment(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[2]))),
                    Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[5]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[6]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[7]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[8])),
                    0,
                    0,
                    0,cursor.getString(cursor.getColumnIndex(tableColumns[12]))
            );

            segmentList.add(segment);
        }

        //sqliteDatabase.close();
        //cursor.close();

        return segmentList;
    }

}

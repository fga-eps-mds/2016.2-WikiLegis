package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

public class SegmentDAO extends DaoUtilities{

    private static String tableColumns[] = {"id", "orderNaoPode", "idBill", "original", "replaced",
            "parent", "type", "number", "content", "firstNameAuthor", "secondNameAuthor", "creationDate", "creationSegment"};

    private static SegmentDAO instance;

    private static String tableName = "Segments";

    private static Context context;

    private SegmentDAO(final Context context) {
        this.context = context;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        DaoUtilities.setDatabase(databaseHelper);
    }

    public static SegmentDAO getInstance(final Context context) {
        if (SegmentDAO.instance != null) {
            //nothing to do
        } else {
            SegmentDAO.instance = new SegmentDAO(context);
        }
        return SegmentDAO.instance;
    }

    public boolean  isDatabaseEmpty(){
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

    public boolean insertSegment(final Segment segment) {

        SQLiteDatabase sqLiteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        SegmentController segmentController = SegmentController.getInstance(context);

        String contentWhitType = segmentController.addingTypeContent(segment);

        ContentValues values = setContentValues(segment, contentWhitType);

        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean modifiedSegment(final Segment segment) throws SegmentException {

        SQLiteDatabase sqLiteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        SegmentController segmentController = SegmentController.getInstance(context);

        String contentWhitType = segmentController.addingTypeContent(segment);

        ContentValues values = setContentValues(segment, contentWhitType);

        deleteSegment(segment.getId());
        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;
        Log.d("booleanToInt(): ", ""+ segment.booleanToInt(segment.isOriginal()));
        return result;
    }

    public void deleteSegment(final Integer idSegment) throws SegmentException {
        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "DELETE FROM " + tableName + " WHERE \"id\" = " + idSegment.toString();

        Cursor cursor = sqliteDatabase.rawQuery(query, null);
    }

    public boolean insertAllSegments(final List<Segment> segmentList) {
        Iterator<Segment> index = segmentList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = insertSegment(index.next());
        }

        return result;
    }

    public boolean modifiedAllSegments(final List<Segment> segmentList) throws SegmentException {
        Iterator<Segment> index = segmentList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = modifiedSegment(index.next());
        }

        return result;
    }

    public long deleteAllSegments() {
        long result;

        SQLiteDatabase sqLiteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        result = deleteAndClose(sqLiteDatabase, tableName);

        return result;
    }

    public Segment getSegmentById(final Integer id) throws SegmentException {
        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE \"id\" = " + id.toString();

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        Segment segment = null;

        while (cursor.moveToNext()) {
            segment = setSegmentById(cursor);
        }
        cursor.close();

        return segment;
    }

    public List<Integer> getSegmentsByIdBill(final Integer idBill) throws SegmentException {
        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE \"idBill\" = " + idBill.toString();

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<Integer> segmentsIdList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Segment segment = setSegmentById(cursor);
            segmentsIdList.add(segment.getId());
        }
        cursor.close();

        return segmentsIdList;
    }

    public List<Segment> getAllSegments() throws SegmentException {

        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        List<Segment> segmentList = new ArrayList<Segment>();

        while (cursor.moveToNext()) {
            Segment segment = setAllSegments(cursor);
            segmentList.add(segment);
        }
        return segmentList;
    }

    private static ContentValues setContentValues(final Segment segment, String contentWhitType){
        ContentValues values = new ContentValues();
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
        return values;
    }

    private static Segment setSegmentById(Cursor cursor) throws SegmentException {
        Segment segment = new Segment(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[2]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[5]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[6]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[7]))),
                cursor.getString(cursor.getColumnIndex(tableColumns[8])),
                cursor.getString(cursor.getColumnIndex(tableColumns[12]))
        );
        return segment;
    }

    private static Segment setAllSegments(Cursor cursor) throws SegmentException {
        Segment segment = new Segment(Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[0]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[2]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[5]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[6]))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[7]))),
                cursor.getString(cursor.getColumnIndex(tableColumns[8])),
                cursor.getString(cursor.getColumnIndex(tableColumns[12]))
        );
        return segment;
    }

    public boolean clearSegmentsTable(){
        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getWritableDatabase();
        sqliteDatabase.delete("["+tableName+"]", null, null);

        boolean isEmpty = isDatabaseEmpty();

        return isEmpty;
    }

}

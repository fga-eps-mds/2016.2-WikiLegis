package gppmds.wikilegis.dao;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by marcelo on 9/13/16.
 */
public class SegmentDAO extends DaoUtilities{

    private static String tableColumns[] = {"id", "order", "bill", "original", "replaced",
            "parent", "type", "number", "content", "idAuthor", "idVote", "idComment"};

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
}

package gppmds.wikilegis.dao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Vote;

public class VotesDAO {

    private static String tableColumns[] = {"id", "contentType", "vote", "userId", "segmentId"};

    private static VotesDAO instance;

    private static String tableName = "votes";

    private VotesDAO(final Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        DaoUtilities.setDatabase(databaseHelper);
    }

    public static VotesDAO getInstance(final Context context) {
        if (VotesDAO.instance != null) {
            //nothing to do
        } else {
            VotesDAO.instance = new VotesDAO(context);
        }
        return VotesDAO.instance;
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

    public boolean insertVote(final Vote vote) {

        SQLiteDatabase sqLiteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[1], vote.getContentType());
        values.put(tableColumns[2], vote.isVote() ? "true" : "false");
        values.put(tableColumns[3], vote.getUserId());
        values.put(tableColumns[4], vote.getObjectId());

        DaoUtilities daoUtilities = new DaoUtilities();
        boolean result = daoUtilities.insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllVotes(final List<Vote> voteList) {
        Iterator<Vote> index = voteList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = insertVote(index.next());
        }

        return result;
    }

    public List<Vote> getAllVotes() throws VotesException {
        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        Vote vote = null;
        List<Vote> voteList = new ArrayList<>();

        while (cursor.moveToNext()) {
            vote = new Vote(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])).equals("true") ? true : false
            );

            voteList.add(vote);
        }
        cursor.close();
        return voteList;
    }

    public List<Vote> getVotesByIdOfSegment(final Integer id) throws VotesException {
        SQLiteDatabase sqliteDatabase = DaoUtilities.getDatabase().getReadableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE \"segmentId\" = " + id.toString();

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        Vote vote = null;
        List<Vote> voteList = new ArrayList<>();

        while (cursor.moveToNext()) {
            vote = new Vote(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])).equals("true") ? true : false
            );

            voteList.add(vote);
        }
        cursor.close();
        return voteList;
    }
}

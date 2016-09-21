package gppmds.wikilegis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Votes;

public class VotesDAO extends DaoUtilities{

    private static String tableColumns[] = {"id", "contentType", "vote", "userId", "segmentId"};

    private static VotesDAO instance;

    private static String tableName = "votes";

    private VotesDAO(final Context context) {
        VotesDAO.database = new DatabaseHelper(context);
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

    public boolean insertVote(final Votes vote) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(tableColumns[1], vote.getContentType());
        values.put(tableColumns[2], vote.isVote() ? "true" : "false");
        values.put(tableColumns[3], vote.getUserId());
        values.put(tableColumns[4], vote.getObjectId());


        boolean result = insertAndClose(sqLiteDatabase, tableName, values) > 0;

        return result;
    }

    public boolean insertAllVotes(final List<Votes> votesList) {
        Iterator<Votes> index = votesList.iterator();

        boolean result = true;

        while (index.hasNext()) {
            result = insertVote(index.next());
        }

        return result;
    }

    public List<Votes> getAllVotes() throws VotesException {
        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT * FROM " + tableName;

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        Votes vote = null;
        List<Votes> votesList = new ArrayList<>();

        while (cursor.moveToNext()) {
            vote = new Votes(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])).equals("true") ? true : false
            );

            votesList.add(vote);
        }
        cursor.close();
        return votesList;
    }

    public List<Votes> getVotesByIdOfSegment(final Integer id) throws VotesException {
        sqliteDatabase = database.getReadableDatabase();

        String query = "SELECT * FROM " + tableName + " WHERE \"segmentId\" = " + id.toString();

        Cursor cursor = sqliteDatabase.rawQuery(query, null);

        Votes vote = null;
        List<Votes> votesList = new ArrayList<>();

        while (cursor.moveToNext()) {
            vote = new Votes(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[3]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[1]))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(tableColumns[4]))),
                    cursor.getString(cursor.getColumnIndex(tableColumns[2])).equals("true") ? true : false
            );

            votesList.add(vote);
        }
        cursor.close();
        return votesList;
    }
}

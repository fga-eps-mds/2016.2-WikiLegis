package gppmds.wikilegis.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marcelo on 9/13/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "wikilegisManager";
    private static final Integer DATABASE_VERSION = 1;

    private static final String BILL_TABLE = "[Bill]";
    private static final String SEGMENTS_BILL_TABLE = "[SegmentsBill]";
    private static final String SEGMENTS_TABLE = "[Segments]";

    //Bill

    private static final String BILL_ID = "[id]";
    private static final String BILL_TITLE = "[title]";
    private static final String BILL_EPIGRAPH = "[epigraph]";
    private static final String BILL_DESCRIPTION = "[description]";
    private static final String BILL_THEME = "[theme]";
    private static final String BILL_AMOUNT_PARTICIPANTS = "[amountParticipants]";
    private static final String BILL_AMOUNT_PROPOSALS = "[amountProposals]";
    private static final String BILL_STATUS = "[status]";


    //Segments of Bill

    private static final String SEGMENTS_BILL_ID_SEGMENT = "[idSegment]";
    private static final String SEGMENTS_BILL_ID_BILL = "[idBill]";

    //Segments

    private static final String SEGMENTS_ID = "[id]";
    private static final String SEGMENTS_ORDER = "[order]";
    private static final String SEGMENTS_ID_BILL = "[idBill]";
    private static final String SEGMENTS_ORIGINAL = "[original]";
    private static final String SEGMENTS_REPLACED = "[replaced]";
    private static final String SEGMENTS_PARENT = "[parent]";
    private static final String SEGMENTS_TYPE = "[type]";
    private static final String SEGMENTS_NUMBER = "[number]";
    private static final String SEGMENTS_CONTENT = "[content]";
    private static final String SEGMENTS_FIRST_NAME_AUTHOR = "[firstNameAuthor]";
    private static final String SEGMENTS_SECOND_NAME_AUTHOR = "[secondNameAuthor]";
    private static final String SEGMENTS_CREATION_DATE = "[creationDate]";


    private static final String CREATE_BILL = "CREATE TABLE " + BILL_TABLE + " (" +
            BILL_ID + " INTEGER NOT NULL PRIMARY KEY, " + BILL_TITLE + " VARCHAR(200), " +
            BILL_EPIGRAPH + "  VARCHAR(200), "+ BILL_DESCRIPTION + " VARCHAR(500), " +
            BILL_THEME + " VARCHAR(50), "+ BILL_AMOUNT_PARTICIPANTS + " INTEGER, " +
            BILL_AMOUNT_PROPOSALS + " INTEGER, " + BILL_STATUS + " VARCHAR(50)); ";


    private static final String CREATE_SEGMENTS_BILL = "CREATE TABLE " + SEGMENTS_BILL_TABLE + " ( " +
            SEGMENTS_BILL_ID_SEGMENT + " INTEGER NOT NULL PRIMARY KEY, " + SEGMENTS_BILL_ID_BILL +
            " INTEGER );";

    private static final String CREATE_SEGMENTS = "CREATE TABLE " + SEGMENTS_TABLE + " ( " +
            SEGMENTS_ID + " INTEGER NOT NULL PRIMARY KEY, " + SEGMENTS_ORDER + " INTEGER, " +
            SEGMENTS_ID_BILL + " INTEGER, " + SEGMENTS_ORIGINAL + " INTEGER, " + SEGMENTS_REPLACED +
            " INTEGER, " + SEGMENTS_PARENT + " INTEGER, " + SEGMENTS_TYPE + " INTEGER, " +
            SEGMENTS_NUMBER + " INTEGER, " + SEGMENTS_CONTENT + " VARCHAR(500), " +
            SEGMENTS_FIRST_NAME_AUTHOR + " VARCHAR(30), " + SEGMENTS_SECOND_NAME_AUTHOR + " VARCHAR(30), " +
            SEGMENTS_CREATION_DATE + " DATE );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BILL);
        db.execSQL(CREATE_SEGMENTS_BILL);
        db.execSQL(CREATE_SEGMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
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
    private static final String COMMENTS_TABLE = "[Comments]";
    private static final String VOTES_TABLE = "[votes]";
    private static final String EMAIL_TABLE = "[email]";


    //Emails

    private static final String EMAIL_EMAIL = "[email]";

    private static final String CREATE_EMAIL = "CREATE TABLE " + EMAIL_TABLE + " (" +
            EMAIL_EMAIL + " );";

    //Bill

    private static final String BILL_ID = "[id]";
    private static final String BILL_TITLE = "[title]";
    private static final String BILL_EPIGRAPH = "[epigraph]";
    private static final String BILL_DESCRIPTION = "[description]";
    private static final String BILL_THEME = "[theme]";
    private static final String BILL_AMOUNT_PARTICIPANTS = "[amountParticipants]";
    private static final String BILL_AMOUNT_PROPOSALS = "[amountProposals]";
    private static final String BILL_STATUS = "[status]";
    private static final String BILL_DATE = "[date]";

    private static final String CREATE_BILL = "CREATE TABLE " + BILL_TABLE + " (" +
            BILL_ID + " INTEGER NOT NULL PRIMARY KEY, " + BILL_TITLE + " VARCHAR(200), " +
            BILL_EPIGRAPH + "  VARCHAR(200), "+ BILL_DESCRIPTION + " VARCHAR(500), " +
            BILL_THEME + " VARCHAR(50), "+ BILL_AMOUNT_PARTICIPANTS + " INTEGER, " +
            BILL_AMOUNT_PROPOSALS + " INTEGER, " + BILL_STATUS + " VARCHAR(50), " + BILL_DATE + " INTEGER);" ;


    //Segments of Bill

    private static final String SEGMENTS_BILL_ID_SEGMENT = "[idSegment]";
    private static final String SEGMENTS_BILL_ID_BILL = "[idBill]";
    private static final String SEGMENTS_BILL_POSITION = "[position]";

    private static final String CREATE_SEGMENTS_BILL = "CREATE TABLE " + SEGMENTS_BILL_TABLE + " ( " +
            SEGMENTS_BILL_ID_SEGMENT + " INTEGER NOT NULL PRIMARY KEY, " + SEGMENTS_BILL_POSITION + " INTEGER ," +
            SEGMENTS_BILL_ID_BILL + " INTEGER );";

    //Segments

    private static final String SEGMENTS_ID = "[id]";
    private static final String SEGMENTS_ORDER = "[orderNaoPode]";
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

    private static final String CREATE_SEGMENTS = "CREATE TABLE " + SEGMENTS_TABLE + " ( " +
            SEGMENTS_ID + " INTEGER NOT NULL PRIMARY KEY, " + SEGMENTS_ORDER + " INTEGER, " +
            SEGMENTS_ID_BILL + " INTEGER, " + SEGMENTS_ORIGINAL + " BIT, " + SEGMENTS_REPLACED +
            " INTEGER, " + SEGMENTS_PARENT + " INTEGER, " + SEGMENTS_TYPE + " INTEGER, " +
            SEGMENTS_NUMBER + " INTEGER, " + SEGMENTS_CONTENT + " VARCHAR(500), " +
            SEGMENTS_FIRST_NAME_AUTHOR + " VARCHAR(30), " + SEGMENTS_SECOND_NAME_AUTHOR + " VARCHAR(30), " +
            SEGMENTS_CREATION_DATE + " DATE );";

    //Comments of Segment

    private static final String COMMENT_ID = "[id]";
    private static final String COMMENT_SEGMENT_ID = "[idSegment]";
    private static final String COMMENT_FIRST_NAME_AUTHOR = "[firstNameAuthor]";
    private static final String COMMENT_SECOND_NAME_AUTHOR = "[secondNameAuthor]";
    private static final String COMMENT_SUBMIT_DATE = "[submitDate]";
    private static final String COMMENT_TYPE = "[type]";
    private static final String COMMENT_DESCRITION = "[descrition]";

    private static final String CREATE_COMMENT = "CREATE TABLE " + COMMENTS_TABLE + " ( " +
            COMMENT_ID + " INTEGER NOT NULL PRIMARY KEY, " + COMMENT_SEGMENT_ID + " INTEGER, " +
            COMMENT_FIRST_NAME_AUTHOR + " VARCHAR(30), " + COMMENT_SECOND_NAME_AUTHOR + " VARCHAR(30), " +
            COMMENT_SUBMIT_DATE + " DATE, " + COMMENT_TYPE + " VARCHAR(50), " + COMMENT_DESCRITION + " VARCHAR(500) ); ";


    //Votes of Segment

    private static final String VOTE_ID = "[id]";
    private static final String VOTE_CONTENT_TYPE = "[contentType]";
    private static final String VOTE_VOTE = "[vote]";
    private static final String VOTE_USER_ID = "[userId]";
    private static final String VOTE_SEGMENT_ID = "[segmentId]";

    private static final String CREATE_VOTE = "CREATE TABLE " + VOTES_TABLE + " ( " +
            VOTE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + VOTE_CONTENT_TYPE + " INTEGER, " +
            VOTE_VOTE + " BIT, " + VOTE_USER_ID + " INTEGER, " + VOTE_SEGMENT_ID + " INTEGER );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BILL);
        db.execSQL(CREATE_SEGMENTS_BILL);
        db.execSQL(CREATE_SEGMENTS);
        db.execSQL(CREATE_COMMENT);
        db.execSQL(CREATE_VOTE);
        db.execSQL(CREATE_EMAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

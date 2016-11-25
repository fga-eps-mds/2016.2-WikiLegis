package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.dao.database.BillDAO;
import gppmds.wikilegis.dao.database.SegmentDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.Vote;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DataDownloadControllerTest {
    Context context;
    SegmentDAO segmentDAO;
    DataDownloadController dataDownloadController;
    SegmentController segmentController;
    BillDAO billDAO;
    BillController billController;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        segmentDAO = SegmentDAO.getInstance(context);
        dataDownloadController = DataDownloadController.getInstance(context);
        segmentController = SegmentController.getInstance(context);

        billDAO = BillDAO.getInstance(context);
        billController = BillController.getInstance(context);

        billDAO.deleteAllBills();

        segmentDAO.clearSegmentsTable();

        WifiManager wifiManager = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = true;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        WifiManager wifiManager = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = true;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateSegments() {
        final String date = "2010-01-01";

        List<Segment> segmentsFromAPI = new ArrayList<>();
        try {
            segmentsFromAPI = JSONHelper.segmentListFromJSON("http://wikilegis-staging.labhackercd.net/api/segments/",
                    "?created=" + date);

            SharedPreferences session = PreferenceManager.
                    getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = session.edit();
            editor.putString("date", date);
            editor.commit();

            dataDownloadController.updateSegments();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        List<Segment> segmentsFromDB = SegmentController.getAllSegments();

        for(int i = 0; i < segmentsFromAPI.size(); i++) {
            Segment segment = segmentsFromAPI.get(i);

            String contentWithType = segment.getContent();

            Segment newSegment = null;

            try {
                newSegment = new Segment(segment.getId(), segment.getOrder(), segment.getBill(),
                        segment.isOriginal(), segment.getReplaced(), segment.getParent(), segment.getType(),
                        segment.getNumber(), contentWithType, segment.getDate());
            } catch (SegmentException e) {
                e.printStackTrace();
            }

            segmentsFromAPI.set(i, newSegment);
        }

        int numberOfEqualsSegments = 0;
        for(int i = 0; i < segmentsFromDB.size(); i++) {
            for(int j = 0; j < segmentsFromAPI.size(); j++) {
                Segment segmentFromAPI = segmentsFromAPI.get(j);
                Segment segmentFromDB = segmentsFromDB.get(i);

                if(segmentFromAPI.equals(segmentFromDB)) {
                    numberOfEqualsSegments++;
                }
            }
        }

        assertTrue(segmentsFromDB.size() == segmentsFromAPI.size() &&
                numberOfEqualsSegments == segmentsFromDB.size());
    }

    @Test
    public void testUpdateBills() {
        final String date = "2010-01-01";
        List<Bill> billsFromAPI = new ArrayList<>();

        try {
            billsFromAPI = JSONHelper.billListFromJSON(JSONHelper.requestJsonObjectFromApi
                            ("http://wikilegis-staging.labhackercd.net/api/bills/?created=" + date));

            SharedPreferences session = PreferenceManager.
                    getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = session.edit();
            editor.putString("date", date);
            editor.commit();

            dataDownloadController.updateBills();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }

        List<Bill> billsFromDB = billController.getAllBills();

        int numberOfEqualsBills = 0;
        for(int i = 0; i < billsFromDB.size(); i++) {
            for(int j = 0; j < billsFromAPI.size(); j++) {
                Bill billFromAPI = billsFromAPI.get(j);
                Bill billFromDB = billsFromDB.get(i);

                if(billFromAPI.equals(billFromDB)) {
                    numberOfEqualsBills++;
                }
            }
        }

        Log.d("bill api", billsFromAPI.size() + "");
        Log.d("bill db", billsFromDB.size() + "");
        Log.d("nb eql", numberOfEqualsBills + "");

        assertTrue(billsFromAPI.size() == billsFromDB.size() &&
                numberOfEqualsBills == billsFromDB.size());
    }

    @Test
    public void testGetLocalDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());

        String localDate = DataDownloadController.getLocalTime();

        assertEquals(formatted, localDate);
    }

    @Test
    public void testUpdateDataWithConnectionPreferenceToNeverDownloadAndWifiEnabled() {
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = session.edit();

        final String keyConnection = "NetworkSettings";
        editor.putInt(keyConnection, 2);

        final String keyDate = "date";
        editor.putString(keyDate, "2010-01-01");

        editor.commit();

        try {
            Log.d("PreferencesConnect", session.getInt("NetworkSettings", 999) + "");
            dataDownloadController.updateData();

            Log.d("PreferencesConnect 2", session.getInt("NetworkSettings", 999) + "");
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        Log.d("Session String", session.getString(keyDate, "seiLa"));

        assertEquals(session.getString(keyDate, "2010-01-01"),
                "2010-01-01");

        Log.d("Session String", session.getString(keyDate, "seiLa"));

    }


    @Test
    public void testNumberOfLikes() {
        List<Vote> listVotes = null;
        try {
            listVotes = JSONHelper.votesListFromJSON("?user=&object_id="+75);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        int countLikes = 0;

        for(Vote vote : listVotes) {
            if(vote.isVote()) {
                countLikes++;
            }
        }
        int likes = -1;

        try {
            likes = DataDownloadController.getNumberOfVotesbySegment(75, true);
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertTrue(countLikes == likes);
    }

    @Test
    public void testNumberOfDislikes() {
        List<Vote> listVotes = null;
        try {
            listVotes = JSONHelper.votesListFromJSON("?user=&object_id="+75);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        int countDislikes = 0;

        for(Vote vote : listVotes) {
            if(!vote.isVote()) {
                countDislikes++;
            }
        }
        int likes = -1;

        try {
            likes = DataDownloadController.getNumberOfVotesbySegment(75, false);
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertTrue(countDislikes == likes);
    }
}

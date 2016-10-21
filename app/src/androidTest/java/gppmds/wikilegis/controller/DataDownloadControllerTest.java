package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.dao.BillDAO;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.dao.SegmentDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marcelo on 10/20/16.
 */
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
    }

    @Test
    public void testUpdateSegments() {
        segmentDAO.clearSegmentsTable();
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

            String contentWithType = segmentController.addingTypeContent(segment);

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
            billsFromAPI = JSONHelper.billListFromJSON(JSONHelper.getJSONObjectApi
                    ("http://wikilegis-staging.labhackercd.net/api/bills/?created="+date),
                    SegmentController.getAllSegments());

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

}

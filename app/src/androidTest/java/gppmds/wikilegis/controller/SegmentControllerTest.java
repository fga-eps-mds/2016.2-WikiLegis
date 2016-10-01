package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shammyz on 9/30/16.
 */
public class SegmentControllerTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testGetAllSegments(){
        SegmentController segmentController = SegmentController.getInstance(context);
        try {
            segmentController.initControllerSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertTrue(segmentController.getAllSegments().size() > 0);
    }

    @Test
    public void testGetSegmentByValidId() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initControllerSegments();

        Segment segment = segmentController.getSegmentById(3927);
        assertEquals(segment.getId()+"", "3927");
    }

    @Test
    public void testGetSegmentByInvalidId() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initControllerSegments();

        Segment segment = segmentController.getSegmentById(0);
        assertNull(segment);
    }

    @Test
    public void testConvertRoman(){
        String roman;
        roman = SegmentController.convertRoman(1251);

        assertEquals(roman, "MCCLI");
    }

    @Test
    public void testGetProposalsOfSegment(){
        SegmentController segmentController = SegmentController.getInstance(context);
        List<Segment> proposalList = new ArrayList<>();
        try {
            segmentController.initControllerSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        proposalList = segmentController.getProposalsOfSegment(segmentController.getAllSegments(), 292);

        assertTrue(proposalList.size() == 3);
    }
}

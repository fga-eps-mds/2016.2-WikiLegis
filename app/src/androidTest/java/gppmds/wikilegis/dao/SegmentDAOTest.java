package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shammyz on 10/5/16.
 */
public class SegmentDAOTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void isDatabaseEmptyTest(){
        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);

        assertFalse(segmentDAO.isDatabaseEmpty());
    }

    @Test
    public void insertSegmentTest(){
        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        try {
            Segment segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            assertTrue(segmentDAO.insertSegment(segment));
        } catch (SegmentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertAllSegmentsTest(){
        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        List<Segment> segmentList = new ArrayList<>();
        try {
            Segment segment1 = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            Segment segment2 = new Segment(2, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            Segment segment3 = new Segment(3, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            Segment segment4 = new Segment(4, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            Segment segment5 = new Segment(5, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");

            segmentList.add(segment1);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);
            segmentList.add(segment5);

        } catch (SegmentException e) {
            e.printStackTrace();
        }
        assertTrue(segmentDAO.insertAllSegments(segmentList));
    }

    @Test
    public void getSegmentByIdTest(){
        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        Segment segment2 = null;
        try {
            Segment segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            segment2 = segmentDAO.getSegmentById(1);
        } catch (SegmentException e) {
            e.printStackTrace();
        }
        assertEquals(segment2.getDate(), "13/12/2006");
    }
}

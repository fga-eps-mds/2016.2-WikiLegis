package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.dao.database.SegmentDAO;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class SegmentDAOTest {
    Context context;
    SegmentDAO segmentDAO;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        segmentDAO = SegmentDAO.getInstance(context);
        segmentDAO.clearSegmentsTable();
    }

    @Test
    public void deleteAllSegmentsTest(){
        List<Segment> segmentList = new ArrayList<>();

        try {

            for (int i = 1; i <= 5; i++) {
                Segment segment = new Segment(i, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
                segmentList.add(segment);
            }
            segmentDAO.insertAllSegments(segmentList);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        long deletedSegments = segmentDAO.deleteAllSegments();
        List<Segment> segments = new ArrayList<>();

        try {
            segments = segmentDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(deletedSegments == segmentList.size() && segments.isEmpty());
    }

    @Test
    public void isDatabaseEmptyWhenDatabaseIsEmptyTest() {
        assertTrue(segmentDAO.isDatabaseEmpty());
    }

    @Test
    public void isDatabaseEmptyWhenDatabaseIsNotEmptyTest() {
        try {
            Segment segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");

            segmentDAO.insertSegment(segment);
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertFalse(segmentDAO.isDatabaseEmpty());
    }

    @Test
    public void insertSegmentTest() throws SegmentException {
        Segment segment = null;
        SegmentController segmentController = SegmentController.getInstance(context);

        try {
            segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        boolean insertedSegment = segmentDAO.insertSegment(segment);
        String contentWhitType = segment.getContent();

        try {
            segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, contentWhitType, "13/12/2006");
        } catch (SegmentException e) {
            e.printStackTrace();
        }
        List<Segment> segmentList = new ArrayList<>();

        try {
            segmentList = segmentDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        int countEqualsSegments = 0;

        //Log.d("HAHAHA", segmentList.size() + "");

        //Log.d("CONTENTS -> ", segment.getContent() + " = " + segmentList.get(0).getContent());
        for(int i = 0; i < segmentList.size(); i++) {
            Log.d("Originalkkk: ", segmentList.get(i).isOriginal() ? "1" : "0");
            if(segment.equals(segmentList.get(i))){
                countEqualsSegments++;
            }
        }


        assertTrue(insertedSegment && countEqualsSegments == 1);
    }

    @Test
    public void insertAllSegmentsTest(){
        segmentDAO = SegmentDAO.getInstance(context);
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
    public void getSegmentByIdTest() throws SegmentException {
        boolean result = false;
        boolean isSegmentInserted = false;

        Segment segment = null;
        Segment segment2Compare = null;

        try {
            segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");

            isSegmentInserted = segmentDAO.insertSegment(segment);


            String contentWhitType = segment.getContent();

            segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, contentWhitType, "13/12/2006");

            segment2Compare = segmentDAO.getSegmentById(segment.getId());

            if (segment.equals(segment2Compare)){
                result = true;
            } else {
                result = false;
            }
        } catch (SegmentException e){
            e.printStackTrace();
        }

        assertTrue(result && isSegmentInserted);
    }


    @Test
    public void getAllSegmentsTest(){
        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        List<Segment> segmentList2 = null;
        List<Segment> segmentList = new ArrayList<>();
        try {
            Segment segment1 = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            Segment segment2 = new Segment(2, 2, 8, true, 55, 10, 1, 6, "blablabla", "14/12/2006");
            Segment segment3 = new Segment(3, 2, 8, true, 55, 10, 1, 6, "blablabla", "15/12/2006");
            Segment segment4 = new Segment(4, 2, 8, true, 55, 10, 1, 6, "blablabla", "16/12/2006");
            Segment segment5 = new Segment(5, 2, 8, true, 55, 10, 1, 6, "blablabla", "17/12/2006");

            segmentList.add(segment1);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);
            segmentList.add(segment5);

            segmentDAO.insertAllSegments(segmentList);

        } catch (SegmentException e) {
            e.printStackTrace();
        }
        try {
            segmentList2 = segmentDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(segmentList2.get(0).getId() == 1);
        assertTrue(segmentList2.get(1).getId()== 2);
        assertTrue(segmentList2.get(2).getId()== 3);
        assertTrue(segmentList2.get(3).getId()== 4);
        assertTrue(segmentList2.get(4).getId()== 5);
    }
}

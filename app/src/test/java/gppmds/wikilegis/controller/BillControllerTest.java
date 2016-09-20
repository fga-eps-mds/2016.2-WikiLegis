package gppmds.wikilegis.controller;

import android.util.Log;

import gppmds.wikilegis.controller.BillController;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;

/**
 * Created by freemanpivo on 8/28/16.
 */
public class BillControllerTest {


    @Test

    public  void testCountedNumberOfProposalsWithDifferentBills(){

        List<Segment> segmentList = new ArrayList<>();

        int count;


        try {
            Segment segment = new Segment(1,10,2,true,3,4,5,6,"content",7,8,9,"Date");
            Segment segment2 = new Segment(2,10,2,true,11,4,5,6,"content",7,8,9,"Date");
            Segment segment3 = new Segment(3,10,1,true,23,4,5,6,"content",7,8,9,"Date");
            Segment segment4 = new Segment(4,10,3,true,48,4,5,6,"content",7,8,9,"Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList,2);

        assertEquals(count,2);

    }

    @Test

    public void testCountedNumberOfProposalsWithSegmentsWithoutProposals(){

        int count;
        List<Segment> segmentList = new ArrayList<>();

        try {
            Segment segment = new Segment(1,10,2,true,0,4,5,6,"content",7,8,9,"Date");
            Segment segment2 = new Segment(2,10,2,true,11,4,5,6,"content",7,8,9,"Date");
            Segment segment3 = new Segment(3,10,2,true,0,4,5,6,"content",7,8,9,"Date");
            Segment segment4 = new Segment(4,10,2,true,48,4,5,6,"content",7,8,9,"Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList,2);

        assertEquals(count,2);

    }

    @Test

    public void testCountedNumberOfProposalsWithoutProposals(){
        int count;
        List<Segment> segmentList = new ArrayList<>();

        try {
            Segment segment = new Segment(1,10,2,true,0,4,5,6,"content",7,8,9,"Date");
            Segment segment2 = new Segment(2,10,2,true,0,4,5,6,"content",7,8,9,"Date");
            Segment segment3 = new Segment(3,10,2,true,0,4,5,6,"content",7,8,9,"Date");
            Segment segment4 = new Segment(4,10,2,true,0,4,5,6,"content",7,8,9,"Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList,2);

        assertEquals(count,0);

    }


    @Test

    public void testCountedNumberOfProposalsFulltProposals(){

        int count;
        List<Segment> segmentList = new ArrayList<>();

        try {
            Segment segment = new Segment(1,10,2,true,15,4,5,6,"content",7,8,9,"Date");
            Segment segment2 = new Segment(2,10,2,true,16,4,5,6,"content",7,8,9,"Date");
            Segment segment3 = new Segment(3,10,2,true,17,4,5,6,"content",7,8,9,"Date");
            Segment segment4 = new Segment(4,10,2,true,18,4,5,6,"content",7,8,9,"Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList,2);

        assertEquals(count,4);

    }


}

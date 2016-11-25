package gppmds.wikilegis.controller;

import org.junit.Test;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;



public class SegmentComparatorOrderTest {

    @Test

    public void testHigherOrder() {
        int count = 0;

        SegmentComparatorOrder segmentComparatorOrder = new SegmentComparatorOrder();


        try {
            Segment segment = new Segment(1,3, 3, true, 4, 5, 6, 10, "Comment", "Date");
            Segment segment2 = new Segment(1,2, 3, true, 4, 5, 6, 10, "Comment", "Date");

            count = segmentComparatorOrder.compare(segment, segment2);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertEquals(count, 1);

    }

    @Test

    public void testLowerOrder(){
        int count = 0;

        SegmentComparatorOrder segmentComparatorOrder = new SegmentComparatorOrder();


        try {
            Segment segment = new Segment(1,1, 3, true, 4, 5, 6, 10, "Comment","Date");
            Segment segment2 = new Segment(1,2, 3, true, 4, 5, 6, 10, "Comment","Date");

            count = segmentComparatorOrder.compare(segment, segment2);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertEquals(count, -1);

    }

    @Test

    public void testEqualOrder(){
        int count = 0;

        SegmentComparatorOrder segmentComparatorOrder = new SegmentComparatorOrder();


        try {
            Segment segment = new Segment(1,2, 3, true, 4, 5, 6, 10, "Comment", "Date");
            Segment segment2 = new Segment(1,2, 3, true, 4, 5, 6, 10, "Comment", "Date");

            count = segmentComparatorOrder.compare(segment, segment2);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertEquals(count,0);

    }

}
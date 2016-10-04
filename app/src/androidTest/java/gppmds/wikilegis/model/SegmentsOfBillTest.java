package gppmds.wikilegis.model;

import org.junit.Test;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentsOfBillException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by augusto on 18/09/16.
 */
public class SegmentsOfBillTest {

    @Test
        public void testCreateSegmentOfBill(){
            boolean isValid = true;

            try{
                SegmentsOfBill segmentsOfBill = new SegmentsOfBill(1,null,3);
            } catch (SegmentsOfBillException segmentsOfBillException) {
                 isValid = false;
            }

        assertFalse(isValid);
    }


    @Test

    public void testNullIdBill(){
        boolean isValid = true;

        try{
            SegmentsOfBill segmentsOfBill = new SegmentsOfBill(1,null,3);
        } catch (SegmentsOfBillException segmentsOfBillException) {
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testValidPosition(){
        boolean isValid = true;

        try{
            SegmentsOfBill segmentsOfBill = new SegmentsOfBill(1,2,1);
        } catch (SegmentsOfBillException segmentsOfBillException) {
            isValid = false;
        }

        assertTrue(isValid);
    }
    @Test

    public void testNullPosition(){
        boolean isValid = true;

        try{
            SegmentsOfBill segmentsOfBill = new SegmentsOfBill(1,2,null);
        } catch (SegmentsOfBillException segmentsOfBillException) {
            isValid = false;
        }

        assertFalse(isValid);
    }


    @Test

    public void testNullIdSegment(){
        boolean isValid = true;

        try{
            SegmentsOfBill segmentsOfBill = new SegmentsOfBill(null,2,3);
        } catch (SegmentsOfBillException segmentsOfBillException) {
            isValid = false;
        }

        assertFalse(isValid);
    }

}



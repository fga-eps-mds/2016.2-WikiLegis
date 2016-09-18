package gppmds.wikilegis.model;

import org.junit.Test;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentsOfBillException;

import static junit.framework.Assert.assertFalse;

/**
 * Created by augusto on 18/09/16.
 */
public class SegmentsOfBillTest {

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



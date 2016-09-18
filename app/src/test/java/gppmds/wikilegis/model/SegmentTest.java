package gppmds.wikilegis.model;

import org.junit.Test;

import gppmds.wikilegis.exception.SegmentException;

import static junit.framework.Assert.assertFalse;

/**
 * Created by augusto on 18/09/16.
 */
public class SegmentTest {

    @Test

    public void testNullId(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(null,1,2,true,3,4,5,6,"content",7,8,9);
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullOrder(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,null,2,true,3,4,5,6,"content",7,8,9);
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }
}

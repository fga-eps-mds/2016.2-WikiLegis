package gppmds.wikilegis.model;

import org.junit.Test;

import gppmds.wikilegis.exception.SegmentTypesException;

import static junit.framework.Assert.assertFalse;

/**
 * Created by augusto on 18/09/16.
 */
public class SegmentTypesTest {


    @Test

    public void testNullId(){
        boolean isValid = true;

        try{
            SegmentTypes segmentTypes = new SegmentTypes(null,"name");
        }catch (SegmentTypesException segmentTypesException){
            isValid = false;
        }

        assertFalse(isValid);
    }



}

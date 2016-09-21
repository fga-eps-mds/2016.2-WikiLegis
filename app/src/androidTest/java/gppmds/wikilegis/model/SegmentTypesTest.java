package gppmds.wikilegis.model;

import org.junit.Test;

import gppmds.wikilegis.exception.SegmentTypesException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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

    @Test

    public void testMinId(){
        boolean isValid = true;

        try{
            SegmentTypes segmentTypes = new SegmentTypes(1,"name");
        }catch (SegmentTypesException e){
            isValid = false;
        }

        assertTrue(isValid);
    }

    @Test

    public void testNullName(){
        boolean isValid = true;

        try{
            SegmentTypes segmentTypes = new SegmentTypes(22,null);
        }catch (SegmentTypesException segmentTypesException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test

    public void testEmptyName(){
        boolean isValid = true;

        try{
            SegmentTypes segmentTypes = new SegmentTypes(null,"");
        }catch (SegmentTypesException segmentTypesException){
            isValid = false;
        }

        assertTrue(!isValid);
    }



}

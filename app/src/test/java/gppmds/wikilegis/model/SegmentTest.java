package gppmds.wikilegis.model;

import android.util.Log;

import org.junit.Test;

import gppmds.wikilegis.exception.SegmentException;

import static junit.framework.Assert.*;

/**
 * Created by augusto on 18/09/16.
 */
public class SegmentTest {

    @Test

    public void testNullId(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(null,1,2,true,3,4,5,6,"content","Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullOrder(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,null,2,true,3,4,5,6,"content","Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullBill(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,2,null,true,3,4,5,6,"content","Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullReplaced(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,2,3,true,null,4,5,6,"content","Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullParent(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,2,3,true,4,null,5,6,"content","Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullType(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,2,3,true,4,5,null,6,"content","Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullNumber(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,2,3,true,4,5,6,null,"content","Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullContent(){
        boolean isValid = true;

        try{
            Segment segment = new Segment(1,2,3,true,4,5,6,10,null,"Date");
        }catch (SegmentException segmentException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testEmptyContent(){

        boolean isValid = true;

        try{
            Segment segment = new Segment(1,2,3,true,4,5,6,10,"","Date");
        }catch(SegmentException segmentException){
            Log.d("test", segmentException.getMessage());
            isValid = false;
        }

        assertTrue(isValid);
    }

}


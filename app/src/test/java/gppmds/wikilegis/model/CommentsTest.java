package gppmds.wikilegis.model;

import org.junit.Test;

import gppmds.wikilegis.exception.CommentsException;

import static junit.framework.Assert.assertFalse;

/**
 * Created by augusto on 17/09/16.
 */
public class CommentsTest {
    @Test

    public void testNullId(){
        boolean isValid = true;

        try{
            Comments comments = new Comments(null,22,"Date","ContentType",9,"Comment");
        }catch (CommentsException commentsException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public  void testNullIdUser(){
        boolean isValid = true;

        try{
            Comments comments = new Comments(22,null,"Date","ContentType",9,"Comment");
        }catch (CommentsException commentsException){
            isValid = false;
        }

        assertFalse(isValid);
    }

}

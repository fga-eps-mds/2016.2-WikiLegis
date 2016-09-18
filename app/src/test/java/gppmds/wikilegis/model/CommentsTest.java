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

    @Test

    public void testNullDate(){
        boolean isValid = true;

        try{
            Comments comments = new Comments(22,22,null,"ContentType",9,"Comment");
        }catch (CommentsException commentsException){
            isValid = false;
        }
        assertFalse(isValid);
    }

    @Test

    public void testEmptyDate(){
        boolean isValid = true;

        try{
            Comments comments = new Comments(22,22,"","ContentType",9,"Comment");
        }catch (CommentsException commentsException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullContentType(){
        boolean isValid = true;

        try{
            Comments comments = new Comments(22,22,"Date",null,9,"Comment");
        } catch (CommentsException commentsExeption){
            isValid = false;
        }
        assertFalse(isValid);
    }

    @Test

    public void testEmptuContentType(){
        boolean isValid = true;

        try{
            Comments comments = new Comments(22,22,"Date","",9,"Comment");
        }catch (CommentsException commentsException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test

    public void testNullObjectPk(){
        boolean isValid = true;

        try{
            Comments comments = new Comments(22,22,"Date","ContentType",null,"Comment");
        }catch (CommentsException commentsException){
            isValid  = false;
        }

        assertFalse(isValid);
    }

}

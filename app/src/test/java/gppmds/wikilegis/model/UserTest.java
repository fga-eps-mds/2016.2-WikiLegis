package gppmds.wikilegis.model;

import org.junit.Test;

import java.io.IOException;

import gppmds.wikilegis.exception.UserException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by freemanpivo on 8/28/16.
 */
public class UserTest {

    @Test
    public void testEmptyFirstName(){
        boolean isValid = true;

        try {
            User user = new User("", "Cardoso", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxLengthFirstName(){
        boolean isValid = true;

        try {
            User user = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Cardoso", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMaxMoreOneLengthFirstName(){
        boolean isValid = true;

        try {
            User user = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Cardoso", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxMinusOneLengthFirstName(){
        boolean isValid = true;

        try {
            User user = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Cardoso", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMinLenghtFirstName(){
        boolean isValid = true;

        try {
            User user = new User("a", "Cardoso", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertTrue(isValid);
    }

    @Test
    public void testEmptySecondName(){
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxLengthSecondName(){
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMaxMoreOneLengthSecondName(){
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxMinusOneLengthSecondName(){
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMinLenghtSecondName(){
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "a", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertTrue(isValid);
    }

}

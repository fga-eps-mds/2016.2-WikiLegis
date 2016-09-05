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
    public void testEmptyName(){
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
    public void testMaxLengthName(){
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
    public void testMinLenghtName(){
        boolean isValid = true;

        try {
            User user = new User("a", "Cardoso", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertTrue(isValid);
    }

}

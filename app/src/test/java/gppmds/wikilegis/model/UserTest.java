package gppmds.wikilegis.model;

import org.junit.Test;

import java.io.IOException;

import dalvik.annotation.TestTarget;
import gppmds.wikilegis.exception.UserException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by freemanpivo on 8/28/16.
 */
public class UserTest {

    @Test
    public void testEmptyFirstName() {
        boolean isValid = true;

        try {
            User user = new User("", "Cardoso", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testNullFirstName(){
        boolean isValid = true;

        try{
            User user = new User(null, "Cardoso", "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testMaxLengthFirstName() {
        boolean isValid = true;

        try {
            User user = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Cardoso", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMaxMoreOneLengthFirstName() {
        boolean isValid = true;

        try {
            User user = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Cardoso", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxMinusOneLengthFirstName() {
        boolean isValid = true;

        try {
            User user = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Cardoso", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMinLenghtFirstName() {
        boolean isValid = true;

        try {
            User user = new User("a", "Cardoso", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);
    }

    @Test
    public void testNameWithNumber(){
        boolean isValid = true;

        try{
            User user = new User ("1asa", "Nere", "a@a.com", "1234567890", "1234567890");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }


    @Test
    public void testNameWithSpecialCharacters(){
        boolean isValid = true;

        try{
            User user = new User ("l@sa", "Nere", "a@a.com", "1234567890", "1234567890");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testEmptySecondName() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testNullSecondName(){
        boolean isValid = true;

        try{
            User user = new User("Cardoso", null, "a@a.com", "123456", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testMaxLengthSecondName() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMaxMoreOneLengthSecondName() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxMinusOneLengthSecondName() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMinLenghtSecondName() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "a", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);
    }

    @Test
    public void testLastNameWithNumber(){
        boolean isValid = true;

        try{
            User user = new User ("Josue", "Nasc1mento", "a@a.com", "1234567890", "1234567890");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testLastNameWithSpecialCharacters(){
        boolean isValid = true;

        try{
            User user = new User ("lasa", "N&re", "a@a.com", "1234567890", "1234567890");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxLenghtPassword() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "Nere", "a@a.com", "1234567890", "1234567890");

        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testMinLenghtPassword() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "Nere", "a@a.com", "123456", "123456");
        } catch (UserException userException) {
            isValid = false;
        }

        assertTrue(isValid);
    }

    @Test
    public void testMaxMoreOneLenghtPassword() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "Nere", "a@a.com", "12345678901", "12345678901");
        } catch (UserException userException) {
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testMinMinusOneLenghtPassword() {
        boolean isValid = true;

        try {
            User user = new User("Cardoso", "Nere", "a@a.com", "12345", "12345");
        } catch (UserException userException) {
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testNullPassword(){
        boolean isValid = true;

        try{
            User user = new User("Nere", "Cardoso", "a@a.com", null, "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testEmptyPassword(){
        boolean isValid = true;

        try{
            User user = new User("Nere", "Cardoso", "a@a.com", "", "123456");
        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testDifferenceBetwenPasswords(){
        boolean isValid = true;

        try{
            User user = new User ("Cardoso", "Nere", "a@a.com", "123456", "000000");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testMaxLengthEmail(){
        boolean isValid = true;

        try{
            User user = new User("Cardoso", "Nere", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaa@aaa.aaaa", "123456", "123456");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertTrue(isValid);

    }

    @Test
    public void testEmptyEmail(){
        boolean isValid = true;

        try{
            User user = new User("Nere", "Cardoso", "", "123456", "123456");

        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testNullEmail(){
        boolean isValid = true;

        try{
            User user = new User("Nere", "Cardoso", null, "123456", "123456");

        }
        catch (UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    public void testMaxMoreOneLenghtEmail(){
        boolean isValid = true;

        try{
            User user = new User("Cardoso", "Nere", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaa@aaa.aaaa", "123456", "123456");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertFalse(isValid);


    }

    @Test
    public void testEmailPattern(){
        boolean isValid = true;

        try{
            User user = new User ("Cardoso", "Nere", "aaaaaaaa", "12345678901", "12345678901");
        }
        catch(UserException userException){
            isValid = false;
        }

        assertFalse(isValid);

    }

}


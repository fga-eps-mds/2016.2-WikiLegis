package gppmds.wikilegis.model;

import android.util.Log;

import org.junit.Test;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.UserException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by augusto on 17/09/16.
 */
public class BillTest {


    @Test
    public void testCreatedBill(){

        boolean isValid = true;
        try {
            Bill bill = new Bill(22, "Title", "Epigraph", "Status", "Description", "Theme", 0, 12021554);
        } catch (BillException billException) {
            isValid = false;
        }

        assertTrue(isValid);
    }

    @Test

    public void testNullId() {

        boolean isValid = true;

        try {
            Bill bill = new Bill(null, "Title", "Epigraph", "Status", "Description", "Theme", 0, 12021554);
        } catch (BillException billException) {
            isValid = false;
        }

        assertFalse(isValid);
    }


    @Test

    public void testNullTitle(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,null,"Epigraph","Status","Description","Theme",0,12021554);
        } catch (BillException billException){
            isValid = false;
        }

        assertFalse(isValid);
    }
    @Test

    public void testTitleOnlyWithSpace(){
        Boolean isValid = true;

        try{
            Bill bill = new Bill(22,"   ","Epigraph","Status","Description","Theme",0,12021554);
        } catch (BillException billException){
            isValid = false;
        }
        assertFalse(!isValid);
    }
    @Test

    public void testEmptyTitle(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"","Epigraph","Status","Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }
        assertFalse(!isValid);
    }

    @Test

    public void testNullEpigraph(){
        boolean isValid =  true;

        try{
            Bill bill = new Bill(22,"Title",null,"Status","Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(isValid);
    }
    @Test
    public void testEpigraphOnlyWithSpace(){
        boolean isValid =  true;

        try{
            Bill bill = new Bill(22,"Title","  ","Status","Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);
    }
    @Test

    public void testEmptyEpigraph(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","","Status","Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);
    }

    @Test

    public void testEmptyDescription(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status","","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);

    }
    @Test

    public void testDescriptionWithOnlySpace(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status","   ","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);

    }
    @Test

    public void testNullDescription(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status",null,"Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test

    public void testEmptyStatus(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","","Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);
    }

    @Test

    public void testNullStatus(){
        boolean isValid = true;
        try{
            Bill bill = new Bill(22,"Title","Epigraph",null,"Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(isValid);
    }
    @Test

    public void testStatusOnlyWithSpace(){
        boolean isValid = true;
        try{
            Bill bill = new Bill(22,"Title","Epigraph","   ","Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);
    }
    @Test

    public void testEmptyTheme(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status","Description","",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);

    }
    @Test
    public void testThemeOnlyWithSpace(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status","Description","  ",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(!isValid);

    }
    @Test

    public void testNullTheme(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status","Description",null,0,12021554);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test

    public void testNullNumberOfProposals(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status","Description","Theme",null,12021554);
        } catch (BillException billException){
            isValid = false;
        }

        assertFalse(isValid);

    }

    @Test
    
    public void testNullDate(){

        boolean isValid = true;

        try{
            Bill bill = new Bill(22,"Title","Epigraph","Status","Description","Theme",0,null);
        }catch (BillException billException){
            isValid = false;
        }

        assertFalse(isValid);

    }

}


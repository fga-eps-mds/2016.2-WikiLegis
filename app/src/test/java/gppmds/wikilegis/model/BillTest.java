package gppmds.wikilegis.model;

import org.junit.Test;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.UserException;

import static junit.framework.Assert.assertFalse;

/**
 * Created by augusto on 17/09/16.
 */
public class BillTest {

    @Test

    public void testNullId(){

        boolean isValid = true;

        try{
            Bill bill = new Bill(null,"Title","Epigraph","Status","Description","Theme",0,12021554);
        }catch (BillException billException){
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

    public void testEmptyTitle(){
        boolean isValid = true;

        try{
            Bill bill = new Bill(null,"","Epigraph","Status","Description","Theme",0,12021554);
        }catch (BillException billException){
            isValid = false;
        }
        assertFalse(isValid);
    }

}

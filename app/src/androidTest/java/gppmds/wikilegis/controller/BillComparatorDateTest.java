package gppmds.wikilegis.controller;

import org.junit.Test;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;

import static junit.framework.Assert.assertEquals;


public class BillComparatorDateTest {

    @Test

    public void testCompareOlderDates(){

        int count = 0;
        BillComparatorDate billComparatorDate = new BillComparatorDate();

        try {
            Bill bill = new Bill(22,"TITLE","Epigraph","Status","Description","Theme",0,12021554);
            Bill bill2 = new Bill(23,"TITLE","Epigraph","Status","Description","Theme",0,12021558);

            count=billComparatorDate.compare(bill,bill2);

        } catch (BillException e) {
            e.printStackTrace();
        }

        assertEquals(count,1);

    }

    @Test

    public void testCompareEarlierDates(){

        int count = 0;
        BillComparatorDate billComparatorDate = new BillComparatorDate();

        try {
            Bill bill = new Bill(22,"TITLE","Epigraph","Status","Description","Theme",0,12021559);
            Bill bill2 = new Bill(23,"TITLE","Epigraph","Status","Description","Theme",0,12021558);

            count=billComparatorDate.compare(bill,bill2);

        } catch (BillException e) {
            e.printStackTrace();
        }

        assertEquals(count,-1);
    }

    @Test

    public void testCompareEqualDates(){
        int count = 0;
        BillComparatorDate billComparatorDate = new BillComparatorDate();

        try {
            Bill bill = new Bill(22,"TITLE","Epigraph","Status","Description","Theme",0,12021559);
            Bill bill2 = new Bill(23,"TITLE","Epigraph","Status","Description","Theme",0,12021559);

            count=billComparatorDate.compare(bill,bill2);

        } catch (BillException e) {
            e.printStackTrace();
        }

        assertEquals(count,0);
    }

}

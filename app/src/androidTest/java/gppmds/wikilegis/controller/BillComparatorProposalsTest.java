package gppmds.wikilegis.controller;

import org.junit.Test;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;

import static junit.framework.Assert.assertEquals;


public class BillComparatorProposalsTest {

    @Test

    public void testHigherNumberOfProposals(){

        int count = 0;

        BillComparatorProposals billComparatorProposals = new BillComparatorProposals();


        try {
            Bill bill = new Bill(22,"TITLE","Epigraph","Status","Description","Theme",3,12021559);
            Bill bill2 = new Bill(23,"TITLE","Epigraph","Status","Description","Theme",2,12021558);

            count = billComparatorProposals.compare(bill,bill2);

        } catch (BillException e) {
            e.printStackTrace();
        }

        assertEquals(count,-1);
    }

    @Test

    public void testLowerNumberOfProposals(){

        int count = 0;

        BillComparatorProposals billComparatorProposals = new BillComparatorProposals();


        try {
            Bill bill = new Bill(22,"TITLE","Epigraph","Status","Description","Theme",3,12021559);
            Bill bill2 = new Bill(23,"TITLE","Epigraph","Status","Description","Theme",8,12021558);

            count = billComparatorProposals.compare(bill,bill2);

        } catch (BillException e) {
            e.printStackTrace();
        }

        assertEquals(count,1);

    }

    @Test

    public void testEqualNumberOfProposals(){
        int count = 0;

        BillComparatorProposals billComparatorProposals = new BillComparatorProposals();


        try {
            Bill bill = new Bill(22,"TITLE","Epigraph","Status","Description","Theme",8,12021559);
            Bill bill2 = new Bill(23,"TITLE","Epigraph","Status","Description","Theme",8,12021558);

            count = billComparatorProposals.compare(bill,bill2);

        } catch (BillException e) {
            e.printStackTrace();
        }

        assertEquals(count,0);

    }


}



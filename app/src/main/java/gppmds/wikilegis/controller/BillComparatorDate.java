package gppmds.wikilegis.controller;

import java.util.Comparator;

import gppmds.wikilegis.model.Bill;

/**
 * Created by josue on 9/14/16.
 */
public class BillComparatorDate implements Comparator<Bill> {
    @Override
    public int compare(Bill bill, Bill t1) {
        if(bill.getNumberOfPrposals()>t1.getNumberOfPrposals()){
            return -1;
        }if(bill.getNumberOfPrposals()<t1.getNumberOfPrposals()){
            return 1;
        }
        return 0;
    }

}

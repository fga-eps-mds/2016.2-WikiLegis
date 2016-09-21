package gppmds.wikilegis.controller;

import java.util.Comparator;

import gppmds.wikilegis.model.Bill;

public class BillComparatorDate implements Comparator<Bill> {

    @Override
    public int compare(final Bill bill, final Bill billToCompair) {
       if (bill.getDate() > billToCompair.getDate()){
           return -1;
       } else if (bill.getDate() < billToCompair.getDate()) {
            return 1;
       } else {
           return 0;
       }
    }
}

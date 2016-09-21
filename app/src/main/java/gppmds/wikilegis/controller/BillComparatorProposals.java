package gppmds.wikilegis.controller;

import java.util.Comparator;

import gppmds.wikilegis.model.Bill;

/**
 * Created by josue on 9/14/16.
 */
public class BillComparatorProposals implements Comparator<Bill> {
    @Override
    public int compare(final Bill bill, final Bill billToCompare) {
        if (bill.getNumberOfPrposals() > billToCompare.getNumberOfPrposals()) {
            return -1;
        } else if (bill.getNumberOfPrposals() < billToCompare.getNumberOfPrposals()) {
            return 1;
        } else {
            return 0;
        }
    }

}

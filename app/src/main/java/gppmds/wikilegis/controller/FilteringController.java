package gppmds.wikilegis.controller;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gppmds.wikilegis.model.Bill;

/**
 * Created by josue on 9/9/16.
 */
public class FilteringController {

    private Context context;
    private static FilteringController instance = null;

    private FilteringController(Context context) {
        this.context = context;
    }

    public static FilteringController getInstance(Context context){
        if(instance == null){
            instance = new FilteringController(context);
        }
        return  instance;
    }

    public List<Bill> filterigForStatusClosed(List<Bill> list){
        List<Bill> billListWithStatusClosed= new ArrayList<Bill>();

        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("closed")){
                billListWithStatusClosed.add(list.get(index));
            }
        }
        return billListWithStatusClosed;
    }

    public List<Bill> filterigForStatusPublished(List<Bill> list){
        List<Bill> billListWithStatusPublished= new ArrayList<Bill>();


        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("published")){
                billListWithStatusPublished.add(list.get(index));
            }
        }
        return billListWithStatusPublished;
    }

    public static List<Bill> filteringForNumberOfProposals(List<Bill> billList) {
        List<Bill> billListAux = new ArrayList<>();

        BillComparatorProposals billComparatorDProposals = new BillComparatorProposals();
        Collections.sort(billList, billComparatorDProposals);

        for(int i=0; i<billList.size(); i++) {
            billListAux.add(billList.get(i));
        }

        return billListAux;

    }

    public List<Bill> filteringForDate(List<Bill> billList){
        List<Bill> billListAux = new ArrayList<>();

        BillComparatorDate comparator = new BillComparatorDate();
        Collections.sort(billList,comparator);

        for(int i=0; i<billList.size(); i++) {
            billListAux.add(billList.get(i));
        }

        return billListAux;
    }
}

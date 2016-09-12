package gppmds.wikilegis.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.model.Segment;

/**
 * Created by josue on 9/11/16.
 */
public class BillController {


    public static int countedTheNumberOfProposals(List<Segment> segmentList , int id){

        int counter = 0;

        for(int index = 0 ;index<segmentList.size() ;index ++){

                if(segmentList.get(index).getBill() == id){

                    if(segmentList.get(index).getReplaced()!=0){
                            counter ++;
                    }
            }

        }

        return counter;
    }

}

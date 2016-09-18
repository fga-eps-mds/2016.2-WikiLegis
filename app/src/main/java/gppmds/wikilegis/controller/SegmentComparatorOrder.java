package gppmds.wikilegis.controller;

import java.util.Comparator;

import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

public class SegmentComparatorOrder implements Comparator<Segment> {


    @Override
    public int compare(Segment segment, Segment segmentCompair) {
        if(segment.getOrder()>segmentCompair.getOrder()){
            return 1;
        }if(segment.getOrder()<segmentCompair.getOrder()){
            return -1;
        }
        return 0;
    }
}

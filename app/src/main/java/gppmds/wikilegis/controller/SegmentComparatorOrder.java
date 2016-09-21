package gppmds.wikilegis.controller;

import java.util.Comparator;

import gppmds.wikilegis.model.Segment;

public class SegmentComparatorOrder implements Comparator<Segment> {


    @Override
    public int compare(final Segment segment, final Segment segmentCompair) {
        if (segment.getOrder() > segmentCompair.getOrder()) {
            return 1;
        } else if (segment.getOrder() < segmentCompair.getOrder()) {
            return -1;
        } else {
            return 0;
        }
    }
}

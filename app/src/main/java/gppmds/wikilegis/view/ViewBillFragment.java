package gppmds.wikilegis.view;




import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentComparatorOrder;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;


public class ViewBillFragment extends Fragment {

    public static List<Segment> listSegment;
    public static SegmentController segmentController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int id;
        id=getArguments().getInt("id");

        View view = inflater.inflate(R.layout.fragment_view_bill, container, false);
        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_viewBill);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recycler_view.setLayoutManager(linearLayoutManager);

        segmentController = SegmentController.getInstance(getContext());

        listSegment = new ArrayList<Segment>();

        List<SegmentsOfBill> segmentsOfBillList;

        segmentsOfBillList = SegmentsOfBillController.getAllSegmentsOfBill(40);

        for(int i=0; i<segmentsOfBillList.size(); i++) {
            try {
                Segment segmentAux = SegmentController.getSegmentById(segmentsOfBillList.get(i).getIdSegment());
                if(segmentAux.getOrder() != 0)
                    listSegment.add(segmentAux);

            } catch (SegmentException e) {
                e.printStackTrace();
            }
        }

        SegmentComparatorOrder segmentComparatorOrder = new SegmentComparatorOrder();
        Collections.sort(listSegment, segmentComparatorOrder);

        RecyclerViewAdapterBill adapter = new RecyclerViewAdapterBill(listSegment, getContext());
        recycler_view.setAdapter(adapter);

        return view;
    }

}

package gppmds.wikilegis.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;

public class ViewSegmentFragment extends Fragment {
    private static Integer segmentId;
    private static Integer billId;
    private TextView segmentText;
    private TextView billText;
    private List<Segment> segmentList;
    private SegmentController segmentController;
    private List<Segment> aux = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        segmentId = getArguments().getInt("segmentId");
        billId = getArguments().getInt("billId");
        View view = inflater.inflate(R.layout.fragment_view_segment, container, false);

        RecyclerView recycler_view = (RecyclerView) view.findViewById (R.id.recycler_viewSegment);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recycler_view.setLayoutManager(linearLayoutManager);
        segmentController = SegmentController.getInstance(getContext());
        segmentList = SegmentController.getAllSegments();

        segmentText = (TextView)view.findViewById(R.id.contentSegment);

        billText = (TextView)view.findViewById(R.id.titleBill);

        try{
            Segment segment = SegmentController.getSegmentById(segmentId);
            segmentText.setText(segment.getContent());
            Bill bill = BillController.getBillById(billId);
            billText.setText(bill.getTitle());
            //billText.setText();
        }catch (Exception e){
            Log.d("LIXO", e.getMessage());
        }
        aux = SegmentController.getProposalsOfSegment(segmentList,segmentId);
        RecyclerViewAdapterContent content = new RecyclerViewAdapterContent(aux);
        recycler_view.setAdapter(content);
        return view;
    }

}

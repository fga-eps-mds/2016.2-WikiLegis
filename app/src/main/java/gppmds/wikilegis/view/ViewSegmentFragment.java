package gppmds.wikilegis.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

public class ViewSegmentFragment extends Fragment {
    private static Integer segmentId;
    private static Integer billId;
    private TextView segmentText;
    private TextView billText;
    private List<Segment> segmentList;
    private SegmentController segmentController;
    private List<Segment> segmentListAux= new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        segmentId = getArguments().getInt("segmentId");
        billId = getArguments().getInt("billId");

        setView(inflater, container);

        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        segmentController = SegmentController.getInstance(getContext());
        segmentList = SegmentController.getAllSegments();

        settingText();

        segmentListAux= SegmentController.getProposalsOfSegment(segmentList, segmentId);
        RecyclerViewAdapterContent content = new RecyclerViewAdapterContent(segmentListAux);
        recyclerView.setAdapter(content);
        return view;
    }

    private void setView(final LayoutInflater inflater, final ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_view_segment, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_viewSegment);
        segmentText = (TextView) view.findViewById(R.id.contentSegment);
        billText = (TextView) view.findViewById(R.id.titleBill);
    }

    private void settingText() {
        try {
            Segment segment = SegmentController.getSegmentById(segmentId);
            segmentText.setText(segment.getContent());
            Bill bill = BillController.getBillById(billId);
            billText.setText(bill.getTitle());
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }
    }
}

package gppmds.wikilegis.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        segmentId = getArguments().getInt("segmentId");
        billId = getArguments().getInt("billId");

        Log.d("idBilauhsuahusahul", billId.toString());
        Log.d("segmentISHAIUSGAUIG", segmentId.toString());

        segmentController = SegmentController.getInstance(getContext());
        segmentList = SegmentController.getAllSegments();


        View view = inflater.inflate(R.layout.fragment_view_segment, container, false);



        segmentText = (TextView)view.findViewById(R.id.segment_content);

        billText = (TextView)view.findViewById(R.id.bill_title);
        try{
            Segment segment = SegmentController.getSegmentById(segmentId);
            segmentText.setText(segment.getContent());
            Bill bill = BillController.getBillById(billId);
            billText.setText(bill.getTitle());
            //billText.setText();
        }catch (Exception e){
            Log.d("LIXO", e.getMessage());
        }


        return view;
    }

    public Segment findSegment(List<Segment> segmentList, int idSegment , int billIndex){
        Segment segment =null;
        for(int index = 0 ; index <segmentList.size(); index++) {
            if(segmentList.get(index).getId()==idSegment){

                return segment;

            }
        }
        return segment;
    }
}

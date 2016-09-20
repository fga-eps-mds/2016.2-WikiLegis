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
import java.util.Collections;
import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentComparatorOrder;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;

public class ViewBillFragment extends Fragment {

    public static List<Segment> listSegment;
    public static SegmentController segmentController;
    public static BillController billController;

    private static final String STRING_EMPTY="";

    private TextView titleBillTextView = null;
    private TextView textAbstractTextView = null;
    private TextView numberProposalsTextView = null;
    private TextView numberParticipantsTextView = null;

    private String titleBill = STRING_EMPTY;
    private String textAbstract = STRING_EMPTY;
    private String numberProposals = STRING_EMPTY;
    private String numberParticipants = STRING_EMPTY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int idBill;
        idBill = getArguments().getInt("id");

        Log.d("idBill", idBill+"");

        View view = inflater.inflate(R.layout.fragment_view_bill, container, false);

        this.settingEditText(view);
        this.settingTypeText(view, idBill);

        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_viewBill);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recycler_view.setLayoutManager(linearLayoutManager);

        segmentController = SegmentController.getInstance(getContext());

        listSegment = new ArrayList<Segment>();

        List<SegmentsOfBill> segmentsOfBillList;

        segmentsOfBillList = SegmentsOfBillController.getAllSegmentsOfBill(idBill);

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

    private void settingEditText(View view) {
        this.titleBillTextView = (TextView) view.findViewById(R.id.textViewTitleBill);;
        this.textAbstractTextView = (TextView) view.findViewById(R.id.textViewTitleAbstract);
        this.numberProposalsTextView = (TextView) view.findViewById(R.id.textViewNumberProposal);
    }

    private void settingTypeText(View view, int id) {

        Bill bill =null;

        try {
            bill = BillController.getBillById(id);
        } catch (BillException e) {
            e.printStackTrace();
        }

        this.titleBillTextView.setText(bill.getTitle());
        this.textAbstractTextView.setText(bill.getDescription());
        this.numberProposalsTextView.setText(bill.getNumberOfPrposals() + "");
    }
}

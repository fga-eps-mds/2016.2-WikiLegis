package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

public class ViewBillFragment extends Fragment {

    private TextView titleBillTextView = null;
    private TextView textAbstractTextView = null;
    private TextView numberProposalsTextView = null;
    private BillController billController = null;
    private SegmentController segmentController = null;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        int idBill;
        idBill = getArguments().getInt("id");

        View view = inflater.inflate(R.layout.fragment_view_bill, container, false);

        this.settingEditText(view);
        this.settingTypeText(idBill);

        TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_viewBill);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        billController = BillController.getInstance(getContext());
        segmentController = SegmentController.getInstance(getContext());
        DataDownloadController dataDownloadController = DataDownloadController.getInstance(getContext());
        List<Segment> segmentList = new ArrayList<>();

        if(dataDownloadController.connectionType() < 2){
            try {
                segmentList = segmentController.getSegmentsOfBillById(""+idBill,"", false);
                SegmentController segmentController = SegmentController.getInstance(getContext());
                segmentController.setSegmentList(segmentList);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (BillException e) {
                e.printStackTrace();
            } catch (SegmentException e) {
                e.printStackTrace();
            }
        }else {
            SegmentController segmentController = SegmentController.getInstance(getContext());

            try {
                segmentList = segmentController.getSegmentsByIdBill(idBill);
            } catch (SegmentException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d("TAMANHO15000", segmentList.size() + "");

        RecyclerViewAdapterBill adapter = new RecyclerViewAdapterBill(segmentList, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void settingEditText(final View view) {
        this.titleBillTextView = (TextView) view.findViewById(R.id.textViewTitleBill);;
        this.textAbstractTextView = (TextView) view.findViewById(R.id.textViewTitleAbstract);
        this.numberProposalsTextView = (TextView) view.findViewById(R.id.textViewNumberProposal);
    }

    private void settingTypeText(final int id) {

        Bill bill = null;
        DataDownloadController dataCenter = DataDownloadController.getInstance(getContext());

        if(dataCenter.connectionType() < 2) {
            try {
                bill = billController.getBillByIdFromApi(id);
            } catch (BillException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bill = BillController.getBillById(id);
            } catch (BillException e) {
                e.printStackTrace();
            }
        }

        this.titleBillTextView.setText(bill.getTitle());
        this.textAbstractTextView.setText(bill.getDescription());
        this.numberProposalsTextView.setText(bill.getNumberOfPrposals() + "");
    }
}

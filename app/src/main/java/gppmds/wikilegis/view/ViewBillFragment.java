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

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

public class ViewBillFragment extends Fragment {

    private TextView titleBillTextView = null;
    private TextView textAbstractTextView = null;
    private TextView numberProposalsTextView = null;

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

        RecyclerViewAdapterBill adapter = new RecyclerViewAdapterBill(BillController
                .getSegmentsFromIdOfBill(idBill), getContext());
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

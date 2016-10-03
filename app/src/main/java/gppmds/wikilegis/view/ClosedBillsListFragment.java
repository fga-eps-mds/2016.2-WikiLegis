package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.model.Bill;

/**
 * Created by izabela on 02/10/16.
 */
public class ClosedBillsListFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    public static ClosedBillsListFragment newInstance(){
        return new ClosedBillsListFragment();
    }

    private List<Bill> billListInitial;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Bill> billListRelevantsAndClosed;
    private List<Bill> billListRecentsAndClosed;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_bills, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Spinner dropdown = (Spinner) view.findViewById(R.id.spinner);
        String[] items = new String[]{"Relevantes", "Recentes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);

        initBillList();

        recyclerViewAdapter = new RecyclerViewAdapter(billListInitial);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    private void initBillList() {
        BillController billController = BillController.getInstance(getContext());
        billListInitial = billController.getAllBills();

        billListInitial = billController.filteringForNumberOfProposals(billListInitial);
        billListInitial = billController.filterigForStatusClosed
                (billListInitial);

        billListRelevantsAndClosed = new ArrayList<>();
        billListRelevantsAndClosed = billController.filteringForNumberOfProposals(billListInitial);
        billListRelevantsAndClosed = billController.filterigForStatusClosed
                (billListRelevantsAndClosed);

        billListRecentsAndClosed = new ArrayList<>();
        billListRecentsAndClosed = billController.filteringForDate(billListInitial);
        billListRecentsAndClosed = billController.filterigForStatusClosed(billListRecentsAndClosed);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        if(parent.getItemAtPosition(position).toString().equals("Relevantes")){
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRelevantsAndClosed);
            recyclerViewAdapter.notifyDataSetChanged();
        }
        else{
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRecentsAndClosed);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Clear and reinitialize list if the tab is visible
        if (this.isVisible()) {
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRelevantsAndClosed);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }
}

package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.model.Bill;

/**
 * Created by izabela on 02/10/16.
 */
public class OpenBillsListFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    public static OpenBillsListFragment newInstance(){
        return new OpenBillsListFragment();
    }

    private List<Bill> billListInitial;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Bill> billListRelevantsAndOpened;
    private List<Bill> billListRecentsAndOpened;

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
        billListInitial = billController.filterigForStatusPublished(billListInitial);

        billListRelevantsAndOpened = new ArrayList<>();
        billListRelevantsAndOpened = billController.filteringForNumberOfProposals(billListInitial);
        billListRelevantsAndOpened = billController.filterigForStatusPublished
                (billListRelevantsAndOpened);

        billListRecentsAndOpened = new ArrayList<>();
        billListRecentsAndOpened = billController.filteringForDate(billListInitial);
        billListRecentsAndOpened = billController.filterigForStatusPublished
                (billListRecentsAndOpened);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        if(parent.getItemAtPosition(position).toString().equals("Relevantes")){
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRelevantsAndOpened);
            recyclerViewAdapter.notifyDataSetChanged();
        }
        else{
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRecentsAndOpened);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Initialize list if the tab is visible
        if (this.isVisible()) {
            initBillList();
        }
    }
}

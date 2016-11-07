package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;

/**
 * Created by izabela on 02/10/16.
 */
public class OpenBillsListFragment extends Fragment implements MaterialSpinner.OnItemSelectedListener{

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

        View view = inflater.inflate(R.layout.fragment_list_open_bills, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_open);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        MaterialSpinner spinner = (MaterialSpinner) view.findViewById(R.id.spinner_open);
        spinner.setItems("Relevantes", "Recentes");
        spinner.setOnItemSelectedListener(this);

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
        billListRelevantsAndOpened =
                billController.filterigForStatusPublished(billListRelevantsAndOpened);

        billListRecentsAndOpened = new ArrayList<>();
        billListRecentsAndOpened = billController.filteringForDate(billListInitial);
        billListRecentsAndOpened =
                billController.filterigForStatusPublished(billListRecentsAndOpened);
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item){
        if(item.equals("Relevantes")){
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRelevantsAndOpened);
            recyclerViewAdapter.notifyDataSetChanged();
        }
        else if(item.equals("Recentes")){
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRecentsAndOpened);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {

        Log.d("DEBUG", "onPause");
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Initialize list if the tab is visible
        if (this.isVisible()) {
            recyclerViewAdapter.getData().clear();
            recyclerViewAdapter.getData().addAll(billListRelevantsAndOpened);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }
}

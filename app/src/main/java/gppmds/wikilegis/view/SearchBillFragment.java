package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;


public class SearchBillFragment extends Fragment {

    private List<Bill> billListSearch;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String searchQuery;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        searchQuery = getArguments().getString("searchQuery");

        TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_search_bill, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        initBillList();

        if (billListSearch.size() == 0) {
            Toast.makeText(getContext(), "Nenhum resultado encontrado!", Toast.LENGTH_SHORT).show();
        }

        recyclerViewAdapter = new RecyclerViewAdapter(billListSearch);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    private void initBillList() {
        BillController billController = BillController.getInstance(getContext());
        DataDownloadController dataDownloadController = DataDownloadController.getInstance(getContext());
        try {
            final int CONNECTION_TYPE = dataDownloadController.connectionType();

            if (CONNECTION_TYPE < 2) {
                billListSearch = billController.searchBills(searchQuery);
            } else {
                billListSearch = billController.searchBillsDatabase(searchQuery);
            }
        } catch (BillException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }
    }
}

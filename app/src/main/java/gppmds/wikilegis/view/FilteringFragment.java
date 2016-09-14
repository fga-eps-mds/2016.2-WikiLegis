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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillComparator;
import gppmds.wikilegis.controller.BillComparatorDate;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.RegisterUserController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentTypes;


public class FilteringFragment extends Fragment {

    public static List<Segment> listSegment;
    public static BillController billController;
    public static SegmentController segmentController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtering, container, false);

        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recycler_view.setLayoutManager(linearLayoutManager);

        segmentController = new SegmentController(getContext());

        listSegment = segmentController.getAllSegments();

        billController = new BillController(getContext());

        List<Bill> billList = billController.getAllBills();

        billList = filtringForNumberOfProposals(billList);

        for(int i=0; i<billList.size(); i++) {
            Log.d("Id", billList.get(i).getTitle());
            Log.d("N", String.valueOf(billList.get(i).getNumberOfPrposals()));
        }

        //billList = filterigForStatusPublished();
        /*
        List<String> titles = new ArrayList<>();

        for (int i = 0; i < billList.size(); i++) {
            titles.add(billList.get(i).getTitle());
        }
        */
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(billList);
        recycler_view.setAdapter(adapter);

        return view;
    }

    public AdapterView.OnItemClickListener callActivity() {
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    public List<Bill> filterigForStatusPublished(){
        List<Bill> billListWithStatusPublished= new ArrayList<Bill>();

        List<Bill> list = null;

        list = billController.getAllBills();

        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("published")){
                billListWithStatusPublished.add(list.get(index));
            }
        }
        return billListWithStatusPublished;
    }

    public List<Bill> filterigForStatusClosed(){
        List<Bill> billListWithStatusClosed= new ArrayList<Bill>();

        List<Bill> list = null;

        list = billController.getAllBills();

        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("closed")){
                billListWithStatusClosed.add(list.get(index));
            }
        }
        return billListWithStatusClosed;
    }

    public static List<Bill> filtringForNumberOfProposals(List<Bill> billList) {
        BillComparatorDate billComparatorDate = new BillComparatorDate();
        Collections.sort(billList,billComparatorDate);
        return  billList;
    }
    public List<Bill> filtringForDate(List<Bill> billList){
        BillComparator comparator = new BillComparator();
        Collections.sort(billList,comparator);
        return billList;
    }
}

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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.RegisterUserController;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.SegmentTypes;


public class FilteringFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtering, container, false);

        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recycler_view.setLayoutManager(linearLayoutManager);

        try {

            //ListView listBill = (ListView) view.findViewById(R.id.listBILL);
            List<Bill> billList = JSONHelper.billListFromJSON(JSONHelper.getJSONObjectApi("http://wikilegis.labhackercd.net/api/bills/"));

            List <String> auxList = new ArrayList<>();
            auxList = filterigForStatusPublished();
            /*
            List<String> titles = new ArrayList<>();

            for (int i = 0; i < billList.size(); i++) {
                titles.add(billList.get(i).getTitle());
            }
*/
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(billList);
            recycler_view.setAdapter(adapter);
            //ArrayAdapter<String> billArrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, auxList);
            //listBill.setAdapter(billArrayAdapter);
            //listBill.setOnItemClickListener(callActivity());

        } catch (BillException b) {
            //Nao sabemos o que botar ainda
        } catch (JSONException j) {
            //Nao sabemos o que botar ainda
        }

        return view;
    }

    public AdapterView.OnItemClickListener callActivity() {
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    public List<String> filterigForStatusPublished(){
        List<String> billListWithStatusPublished= new ArrayList<String>();

        List<Bill> list = null;
        try {
           list = JSONHelper.billListFromJSON(JSONHelper.getJSONObjectApi("http://wikilegis.labhackercd.net/api/bills/"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }
        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("published")){
                billListWithStatusPublished.add(list.get(index).getTitle());
            }
        }
        return billListWithStatusPublished;
    }

    public List<String> filterigForStatusClosed(){
        List<String> billListWithStatusClosed= new ArrayList<String>();

        List<Bill> list = null;
        try {
            list = JSONHelper.billListFromJSON(JSONHelper.getJSONObjectApi("http://wikilegis.labhackercd.net/api/bills/"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }
        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("closed")){
                billListWithStatusClosed.add(list.get(index).getTitle());
            }
        }
        return billListWithStatusClosed;
    }
}

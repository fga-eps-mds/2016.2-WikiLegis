package gppmds.wikilegis.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONException;
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
    public  View onCreateView(LayoutInflater inflater,ViewGroup container,
                                Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_filtering, container, false);

        RegisterUserController registerUserController = RegisterUserController.getInstance(getContext());

        try{


            ListView listBill = (ListView) view.findViewById(R.id.listBILL);
            List<Bill> billList = JSONHelper.billListFromJSON(registerUserController.getUrlApi("http://wikilegis.labhackercd.net/api/bills/"));

            List<String> titles = new ArrayList<>();
            for(int i=0; i<billList.size(); i++) {
                titles.add(billList.get(i).getTitle());
            }
            ArrayAdapter<String> billArrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, titles);
            listBill.setAdapter(billArrayAdapter);

            listBill.setOnItemClickListener(callActivity());


        }catch (BillException b){
            //Nao sabemos o que botar ainda
        }catch (JSONException j){
            //Nao sabemos o que botar ainda
        }

        return view;
    }

    public void showSegments(List<SegmentTypes> segmentTypesList) throws SegmentTypesException,JSONException{

        RegisterUserController registerUserController = RegisterUserController.getInstance(getContext());
        JSONHelper.segmentTypesListFromJSON(registerUserController.getUrlApi("http://wikilegis.labhackercd.net/api/bills/"));

    }

    public AdapterView.OnItemClickListener callActivity() {
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
    }
}

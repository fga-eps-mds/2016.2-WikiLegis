package gppmds.wikilegis.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.RegisterUserController;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;


public class FilteringFragment extends Fragment{


    @Override
    public  View onCreateView(LayoutInflater inflater,ViewGroup container,
                                Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_filtering, container, false);

        RegisterUserController registerUserController = RegisterUserController.getInstance(getContext());

        try{


            ListView listBill = (ListView) view.findViewById(R.id.listBILL);

            List <Bill> billList = JSONHelper.billListFromJSON(registerUserController.getUsersExemple());
            ArrayAdapter<Bill> billArrayAdapter = new ArrayAdapter<Bill>(this.getContext() , android.R.layout.simple_list_item_1, billList);

            listBill.setAdapter(billArrayAdapter);

        }catch (BillException b){

        }catch (JSONException j){

        }





        return view;
    }


}

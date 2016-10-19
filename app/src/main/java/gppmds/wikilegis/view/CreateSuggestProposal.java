package gppmds.wikilegis.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.SegmentException;


public class CreateSuggestProposal extends Fragment implements View.OnClickListener{

    private EditText suggestionTyped;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_create_suggest_proposal, container, false);

        Button saveButton = (Button) view.findViewById(R.id.saveSuggestion);
        saveButton.setOnClickListener(this);

        floatingActionButton = (FloatingActionButton) getActivity()
                .findViewById(R.id.floatingButton);
        floatingActionButton.setVisibility(View.INVISIBLE);

        suggestionTyped = (EditText) view.findViewById(R.id.suggestionEditText);

        TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);

        return view;
    }

    private String saveSuggestion(Integer billId, Integer segmentId){

        SegmentController segmentController = SegmentController.getInstance
                (getContext());

        String proposalTyped = suggestionTyped.getText().toString();

        String result = null;

        try{
            result = segmentController.registerSegment(billId, segmentId, proposalTyped,
                    getContext());

        } catch(JSONException e){
            e.printStackTrace();
        } catch(SegmentException e){
            e.printStackTrace();
        }

        if(result == "SUCCESS"){
            result =  "Obrigado pela sugestão!";
        }
        else{
            result = "Desculpe, um problema ocorreu";
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.saveSuggestion){

            SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(getContext());
            if(!session.getString("token", "").isEmpty()){

                Integer idBill = getArguments().getInt("idBill");
                Integer idSegment = getArguments().getInt("idSegment");

                String savingStatus = saveSuggestion(idBill, idSegment);

                Toast.makeText(getContext(), savingStatus, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        floatingActionButton.setVisibility(View.INVISIBLE);
    }
}

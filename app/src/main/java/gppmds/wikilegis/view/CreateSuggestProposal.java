package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;


public class CreateSuggestProposal extends Fragment implements View.OnClickListener {

    private EditText suggestionTyped;
    private FloatingActionButton floatingActionButton;
    private List<Segment> listSegment;

    public CreateSuggestProposal(){
    }

    public CreateSuggestProposal(List<Segment> listSegment){
        this.listSegment = listSegment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        View view  = inflater.inflate(R.layout.fragment_create_suggest_proposal, container, false);

        Button saveButton = (Button) view.findViewById(R.id.saveSuggestion);
        saveButton.setOnClickListener(this);

        floatingActionButton = (FloatingActionButton) getActivity()
                .findViewById(R.id.floatingButton);
        floatingActionButton.setVisibility(View.INVISIBLE);

        suggestionTyped = (EditText) view.findViewById(R.id.suggestionProposalEditText);

        TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);

        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recyclerViewComment);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerViewAdapterContent content = new RecyclerViewAdapterContent(listSegment,
                getArguments().getInt("billId"), getArguments().getInt("segmentId"));
        recyclerView.setAdapter(content);

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
            result = getContext().getResources().getString(R.string.empty_segment);
        }

        if(result.equals("SUCCESS")){
            result =  getContext().getResources().getString(R.string.success_proposal);
        }
        else if(result.equals(getContext().getResources().getString(R.string.empty_segment))){
            suggestionTyped.requestFocus();
            suggestionTyped.setError(result);
        }
        else{
            result = getContext().getResources().getString(R.string.connection_problem);
            Log.d("Connection problem ", result);
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.saveSuggestion){

            Integer idBill = getArguments().getInt("billId");
            Integer idSegment = getArguments().getInt("segmentId");

            Log.d("ID SEGMENT", idSegment + "");
            String savingStatus = saveSuggestion(idBill, idSegment);

            if(savingStatus.equals(getContext().getResources().getString(
                    R.string.success_proposal))){
                Toast.makeText(getContext(), savingStatus, Toast.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        floatingActionButton.setVisibility(View.INVISIBLE);
    }
}

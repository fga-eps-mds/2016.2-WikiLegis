package gppmds.wikilegis.view;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gppmds.wikilegis.R;


public class CreateSuggestProposal extends Fragment implements View.OnClickListener{


    public CreateSuggestProposal() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view ;

        view = inflater.inflate(R.layout.fragment_create_suggest_proposal, container, false);

        return  view;
    }

    @Override
    public void onClick(View view) {
        if(  view.getId() == R.id.floatingButton){

        }

    }
}

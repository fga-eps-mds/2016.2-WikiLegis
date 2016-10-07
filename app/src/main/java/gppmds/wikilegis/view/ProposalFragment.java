package gppmds.wikilegis.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gppmds.wikilegis.R;


public class ProposalFragment extends Fragment implements View.OnClickListener{


    private Button saveButton;
    private View view;
    public ProposalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proposal, container, false);
        saveButton = (Button)view.findViewById(R.id.save);
        saveButton.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        getActivity().getFragmentManager().popBackStack();

    }
}

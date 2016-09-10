package gppmds.wikilegis.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gppmds.wikilegis.R;



public class LoginFragment extends Fragment implements View.OnClickListener{

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_login, container, false);

        TextView visitor = (TextView) view.findViewById(R.id.loginAsVisitorText);
        visitor.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        // Create new fragment and transaction
        Fragment newFragment = new FilteringFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

    }



}




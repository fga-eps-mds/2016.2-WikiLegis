package gppmds.wikilegis.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


        return view;
    }


    @Override
    public void onClick(View view) {

    }


}

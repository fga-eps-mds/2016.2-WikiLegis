package gppmds.wikilegis.view;


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

        TextView visitor = (TextView) view.findViewById(R.id.loginAsVisitorText);
        visitor.setOnClickListener(this);

        TextView register = (TextView) view.findViewById(R.id.registerText);
        register.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        // Create new fragment and transaction
        switch (view.getId()){
            case R.id.loginAsVisitorText:
                //Change activity***
                Fragment filteringFragment = new FilteringFragment();
                openFragment(filteringFragment);
            break;

            case R.id.registerText:
                Fragment registerUserFragment = new RegisterUserFragment();
                openFragment(registerUserFragment);
                break;
        }

    }

    private void openFragment(Fragment fragmentToBeOpen){

        assert fragmentToBeOpen != null;

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();

        assert fragmentTransaction != null;

        fragmentTransaction.replace(R.id.content_panel, fragmentToBeOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}




package gppmds.wikilegis.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Button button = (Button)view.findViewById(R.id.loginButton);

        button.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View view) {
        // Create new fragment and transaction
        switch (view.getId()) {
            case R.id.loginAsVisitorText:
                //Change activity***
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.registerText:
                Fragment registerUserFragment = new RegisterUserFragment();
                openFragment(registerUserFragment);
                break;
            case R.id.loginButton :

                Intent intent1 = new Intent(getContext(), MainActivity.class);
                startActivity(intent1);
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




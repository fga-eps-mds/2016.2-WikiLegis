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



public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView visitor;
    private TextView register;
    private Button button;
    private TextView about;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        settingView(view);

        settingClickLitenersView();

        return view;
    }

    private void settingView(final View view) {
        this.visitor = (TextView) view.findViewById(R.id.loginAsVisitorText);
        this.register = (TextView) view.findViewById(R.id.registerText);
        this.button = (Button) view.findViewById(R.id.loginButton);
        this.about = (TextView) view.findViewById(R.id.aboutApp);
    }

    private void settingClickLitenersView() {
        visitor.setOnClickListener(this);
        register.setOnClickListener(this);
        button.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
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
            case R.id.aboutApp :
                Fragment aboutFragment = new AboutFragment();
                openFragment(aboutFragment);
                break;
            default:
                //nothing to do
        }
    }

    private void openFragment(final Fragment fragmentToBeOpen) {

        assert fragmentToBeOpen != null;

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();

        assert fragmentTransaction != null;

        fragmentTransaction.replace(R.id.content_panel, fragmentToBeOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

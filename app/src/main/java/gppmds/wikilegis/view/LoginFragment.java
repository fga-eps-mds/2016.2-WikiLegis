package gppmds.wikilegis.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.LoginController;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView visitor;
    private TextView register;
    private Button button;
    private EditText personNameField;
    private EditText passwordField;

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
        this.personNameField = (EditText) view.findViewById(R.id.emailLoginField);
        this.passwordField = (EditText) view.findViewById(R.id.passwordLoginField);
    }

    private void settingClickLitenersView() {
        visitor.setOnClickListener(this);
        register.setOnClickListener(this);
        button.setOnClickListener(this);
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
                activityLogin(String.valueOf(personNameField.getText()),
                        String.valueOf(passwordField.getText()));
                break;
            default:
                //nothing to do
        }
    }

    private void activityLogin(final String email, final String password) {
        LoginController loginController = LoginController.getInstance(getContext());

        if(loginController.confirmLogin(email, password).equals("SUCESS")) {
            personNameField.setText("");
            passwordField.setText("");
            Intent intent1 = new Intent(getContext(), MainActivity.class);
            startActivity(intent1);
        } else {
            passwordField.setText("");
            Toast.makeText(getContext(), "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
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

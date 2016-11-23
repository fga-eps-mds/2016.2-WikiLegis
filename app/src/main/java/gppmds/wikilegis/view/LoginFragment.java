package gppmds.wikilegis.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.LoginController;
import gppmds.wikilegis.model.User;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView visitor;
    private TextView register;
    private Button button;
    private EditText personNameField;
    private EditText passwordField;
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
        this.personNameField = (EditText) view.findViewById(R.id.emailLoginField);
        this.passwordField = (EditText) view.findViewById(R.id.passwordLoginField);
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
                //Change activity
                LoginController loginController = LoginController.getInstance(getContext());
                SharedPreferences session = PreferenceManager.
                        getDefaultSharedPreferences(getContext());
                loginController.createSessionIsNotLogged(session);

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.registerText:
                Fragment registerUserFragment = new RegisterUserFragment();
                openFragment(registerUserFragment);
                break;

            case R.id.loginButton :
                validateLoginInformation(String.valueOf(personNameField.getText()),
                        String.valueOf(passwordField.getText()));
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

    private void validateLoginInformation(final String userName, final String password) {

        LoginController login = LoginController.getInstance(getContext());

        String feedbackRegisterMessage = login.confirmLogin(userName, password);

        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(getContext());

        passwordField.setText("");

        switch (feedbackRegisterMessage) {
            case User.EMAIL_CANT_BE_EMPTY_EMAIL:
                setMessageError(personNameField, feedbackRegisterMessage);
                break;
            case User.EMAIL_CANT_BE_HIGHER_THAN_150:
                setMessageError(personNameField, feedbackRegisterMessage);
                break;
            case User.INVALID_EMAIL:
                setMessageError(personNameField, feedbackRegisterMessage);
                break;
            case User.PASSWORD_CANT_BE_EMPTY:
                setMessageError(passwordField, feedbackRegisterMessage);
                break;
            case User.PASSWORD_CANT_BE_LESS_THAN_6:
                setMessageError(passwordField, feedbackRegisterMessage);
                break;
            case User.PASSWORD_CANT_BE_HIGHER_THAN_10:
                setMessageError(passwordField, feedbackRegisterMessage);
                break;
            case "SUCESS":
                personNameField.setText("");
                Intent intent1 = new Intent(getContext(), MainActivity.class);
                startActivity(intent1);
                break;
            case "FAIL":
                if(dataDownloadController.connectionType() == 2){
                    Toast.makeText(getContext(), "Sem conexão de internet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                //nothing to do
                break;
        }
    }

    private void setMessageError(final EditText editText, final String message) {
        editText.requestFocus();
        editText.setError(message);
    }
}

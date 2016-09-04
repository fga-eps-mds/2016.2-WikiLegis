package gppmds.wikilegis.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;

import gppmds.wikilegis.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterUserFragment extends Fragment implements View.OnClickListener{

    private static final String STRING_EMPTY ="";

    private EditText firstNameField = null;
    private EditText lastNameField = null;
    private EditText emailField = null;
    private EditText passwordField = null;
    private EditText passwordConfirmationField = null;

    private String firstName = STRING_EMPTY;
    private String lastName = STRING_EMPTY;
    private String email = STRING_EMPTY;
    private String password = STRING_EMPTY;
    private String passwordConfirmation = STRING_EMPTY;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_user, container, false);

        Button register = (Button) view.findViewById(R.id.registerButton);

        register.setOnClickListener(this);

        return view;
    }

    private void settingEditText(View view){
        this.firstNameField = (EditText) view.findViewById(R.id.firstNameField);
        this.lastNameField = (EditText) view.findViewById(R.id.lastNameField);
        this.emailField = (EditText) view.findViewById(R.id.emailField);
        this.passwordField = (EditText) view.findViewById(R.id.passwordField);
        this.passwordConfirmationField = (EditText) view.findViewById(R.id.passwordConfirmationField);
    }

    private void settingTextTyped(){
        this.firstName = firstNameField.getText().toString();
        this.lastName = lastNameField.getText().toString();
        this.email = emailField.getText().toString();
        this.password = passwordField.getText().toString();
        this.passwordConfirmation = passwordConfirmationField.getText().toString();
    }

    @Override
    public void onClick(View view){

        this.settingTextTyped();

        //this.validateUserInformation();
    }

}

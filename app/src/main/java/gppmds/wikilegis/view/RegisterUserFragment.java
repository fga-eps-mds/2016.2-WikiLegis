package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;

import gppmds.wikilegis.controller.RegisterUserController;
import gppmds.wikilegis.dao.GetRequest;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.User;

import gppmds.wikilegis.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterUserFragment extends Fragment implements View.OnClickListener{

    private static final String STRING_EMPTY="";

    private EditText firstNameField=null;
    private EditText lastNameField=null;
    private EditText emailField=null;
    private EditText passwordField=null;
    private EditText passwordConfirmationField=null;

    private String firstName=STRING_EMPTY;
    private String lastName=STRING_EMPTY;
    private String email=STRING_EMPTY;
    private String password=STRING_EMPTY;
    private String passwordConfirmation=STRING_EMPTY;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register_user, container, false);

        Button register=(Button) view.findViewById(R.id.registerButton);

        register.setOnClickListener(this);
        this.settingEditText(view);

        return view;
    }

    private void settingEditText(View view) {
        this.firstNameField=(EditText) view.findViewById(R.id.firstNameField);
        this.lastNameField=(EditText) view.findViewById(R.id.lastNameField);
        this.emailField=(EditText) view.findViewById(R.id.emailField);
        this.passwordField=(EditText) view.findViewById(R.id.passwordField);
        this.passwordConfirmationField=(EditText) view.findViewById(R.id.passwordConfirmationField);
    }

    private void settingTextTyped() {
        this.firstName=firstNameField.getText().toString();
        this.lastName=lastNameField.getText().toString();
        this.email=emailField.getText().toString();
        this.password=passwordField.getText().toString();
        this.passwordConfirmation=passwordConfirmationField.getText().toString();
    }

    private void settingErrorNull(){
        this.firstNameField.setError(null);
        this.lastNameField.setError(null);
        this.emailField.setError(null);
        this.passwordField.setError(null);
        this.passwordConfirmationField.setError(null);
    }

    @Override
    public void onClick(View view)  {
        String aux = null;
        this.settingTextTyped();
        this.settingErrorNull();
        this.validateUserInformation();
        RegisterUserController e = RegisterUserController.getInstance(getContext());

        //Tirar isso daqui, é só pra teste

    }

    private void setMessageError(EditText editText, String message) {
        editText.requestFocus();
        editText.setError(message);

    }

    private void validateUserInformation() {

        RegisterUserController registerUser = RegisterUserController.getInstance(getContext());

        String feedbackRegisterMessage = registerUser.registerUser(firstName, lastName, email, password, passwordConfirmation);

        switch (feedbackRegisterMessage) {
            case User.FIRST_NAME_CANT_BE_EMPTY:
                setMessageError(firstNameField, feedbackRegisterMessage);
                break;
            case User.NAME_CONTAINS_SPECIAL_CHARACTERS:
                setMessageError(firstNameField, feedbackRegisterMessage);
                break;
            case User.FIRST_NAME_CANT_BE_HIGHER_THAN_30:
                setMessageError(firstNameField, feedbackRegisterMessage);
                break;
            case User.LAST_NAME_CANT_BE_EMPTY:
                setMessageError(lastNameField, feedbackRegisterMessage);
                break;
            case User.LAST_NAME_CONTAINS_SPECIAL_CHARACTERS:
                setMessageError(lastNameField, feedbackRegisterMessage);
                break;
            case User.LAST_NAME_CANT_BE_HIGHER_THAN_30:
                setMessageError(lastNameField, feedbackRegisterMessage);
                break;
            case User.EMAIL_CANT_BE_EMPTY_EMAIL:
                setMessageError(emailField, feedbackRegisterMessage);
                break;
            case User.EMAIL_CANT_BE_HIGHER_THAN_150:
                setMessageError(emailField, feedbackRegisterMessage);
                break;
            case User.INVALID_EMAIL:
                setMessageError(emailField, feedbackRegisterMessage);
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

            case User.PASSWORD_ISNT_EQUALS:
                setMessageError(passwordConfirmationField, feedbackRegisterMessage);
                break;

            case User.EMAIL_CANT_BE_EQUALS:
                setMessageError(emailField, feedbackRegisterMessage);
                break;

            case "SUCESS":
                String SUCCESSFUL_REGISTRATION_MESSAGE="Cadastro efetuado com sucesso!";
                Toast.makeText(getActivity().getBaseContext(),
                        SUCCESSFUL_REGISTRATION_MESSAGE, Toast.LENGTH_LONG).show();
                break;
            default:
                //nothing to do
                break;
        }
    }
}


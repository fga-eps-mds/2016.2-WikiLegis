package gppmds.wikilegis.controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import gppmds.wikilegis.dao.PostRequest;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;


public class RegisterUserController {

    private static RegisterUserController instance = null;
    private final Context context;

    private RegisterUserController(final Context contextParameter) {
        this.context = contextParameter;
    }

    public static RegisterUserController getInstance(final Context context) {
        if (instance == null) {
            instance = new RegisterUserController(context);
        } else {
			/* ! Nothing To Do. */
        }
        return instance;
    }

    public String registerUser(final String firstName,
                               final String lastName,
                               final String email,
                               final String password,
                               final String passwordConfirmation) throws UserException, JSONException{


            User user = new User(firstName, lastName, email, password, passwordConfirmation);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", user.getEmail());
            jsonParam.put("first_name", user.getFirstName());
            jsonParam.put("last_name", user.getLastName());
            jsonParam.put("password", user.getPassword());

            String url = "http://wikilegis-staging.labhackercd.net/api/user/create/";

            PostRequest postRequest = new PostRequest(context, jsonParam, url);
            postRequest.execute();

            return "SUCESS";
    }
}

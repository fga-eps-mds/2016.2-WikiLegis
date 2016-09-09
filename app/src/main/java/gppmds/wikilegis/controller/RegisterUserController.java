package gppmds.wikilegis.controller;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.dao.GetRequest;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;


public class RegisterUserController {
    private static RegisterUserController instance = null;
    private final Context context;

    private RegisterUserController(Context context) {
        this.context = context;
    }

    public static RegisterUserController getInstance(Context context) {
        if (instance == null) {
            instance = new RegisterUserController(context);
        } else {
			/* ! Nothing To Do. */
        }
        return instance;
    }

    public String registerUser(String firstName,
                               String lastName,
                               String email,
                               String password,
                               String passwordConfirmation) {

        try {

            User user = new User(firstName, lastName, email, password, passwordConfirmation);

            return "SUCESS";

        } catch (UserException e) {
            String exceptionMessage = e.getMessage();
            return exceptionMessage;

        }
    }


    /**
     * Log D the users
     */
    public String getUsersExemple() {
        final String URL = "http://wikilegis.labhackercd.net/api/segments/";
        String apiString = "";
        GetRequest request = new GetRequest();

        apiString=request.execute(URL).toString();

        return apiString;

    }
}
package gppmds.wikilegis.controller;

import android.content.Context;

import java.io.IOException;

import gppmds.wikilegis.api.GetRequest;
import gppmds.wikilegis.dao.UtilitiesDAO;
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


    public static boolean emailIsRepeated(String email) throws IOException {

        UtilitiesDAO utilities = new UtilitiesDAO();

            if (utilities.findEmail(email) == true) {
                return true;
            } else {
                return false;
            }

    }

    /**
     * Log D the users
     */
    public void getUsersExemple() {
        final String URL = "http://wikilegis.labhackercd.net/api/bills/";

        GetRequest request = new GetRequest();

        request.execute(URL);

    }
}

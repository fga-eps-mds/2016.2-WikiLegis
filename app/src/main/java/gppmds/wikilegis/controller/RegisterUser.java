package gppmds.wikilegis.controller;

import android.content.Context;

import java.io.IOException;

import gppmds.wikilegis.api.GetRequest;
import gppmds.wikilegis.dao.UtilitiesDAO;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;


public class RegisterUser {
    private static RegisterUser instance = null;
    private final Context context;

    private RegisterUser(Context context) {
        this.context = context;
    }

    public static RegisterUser getInstance(Context context) {
        if (instance == null) {
            instance = new RegisterUser(context);
        } else {
			/* ! Nothing To Do. */
        }
        return instance;
    }

    public String registerUser (String firstName,
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


    public static boolean emailIsRepeated(String email) throws IOException{

        UtilitiesDAO utilities = new UtilitiesDAO();

            if(utilities.findEmail(email) == true) {
                return true;
            }
            else {
                return false;
            }

    }

    /**
     * Log D the users
     */
    public void getUsersExemple(){
        final String URL = "http://wikilegis.labhackercd.net/api/users/?api_key=9944b09199c62bcf9418ad846dd0e4bbdfc6ee4b";

        GetRequest request = new GetRequest();

        request.execute(URL);

    }
}

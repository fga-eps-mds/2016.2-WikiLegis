package gppmds.wikilegis.controller;

import android.content.Context;

import java.io.IOException;

import gppmds.wikilegis.api.Request;
import gppmds.wikilegis.dao.UtilitiesDAO;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;

/**
 * Created by josue on 9/3/16.
 */
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

        } catch (IOException e) {

            return "IOException";

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
     * change the URL for other responses
     */
    public void usingRequestExemple(){
        final String URL = "http://wikilegis.labhackercd.net/api/";

        Request request = new Request();

        request.execute(URL);

    }
}

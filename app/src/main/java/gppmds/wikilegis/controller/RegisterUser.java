package gppmds.wikilegis.controller;

import java.io.IOException;

import gppmds.wikilegis.dao.UtilitiesDAO;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;

/**
 * Created by josue on 9/3/16.
 */
public class RegisterUser {

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


    public boolean emailIsRepeated(String email) throws IOException{

        UtilitiesDAO utilities = new UtilitiesDAO();

            if(utilities.findEmail(email) == true) {
                return true;
            }
            else {
                return false;
            }

    }
}

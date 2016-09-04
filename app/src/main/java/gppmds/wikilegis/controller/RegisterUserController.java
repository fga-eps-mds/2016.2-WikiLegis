package gppmds.wikilegis.controller;

import java.io.IOException;

import gppmds.wikilegis.dao.DaoUtilities;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;

/**
 * Created by josue on 9/3/16.
 */
public class RegisterUserController {

    public User registerUser (String firstName , String lastName, String email,String password)throws UserException{
        User user = new User(firstName ,lastName,email,password) ;
        return user;
    }


    public boolean emailIsRepeated(String email)throws IOException{
        DaoUtilities utilities = new DaoUtilities();

            if(utilities.findEmail(email)==true) {
                return true;
            }
            else{
                return false;
            }

    }
}

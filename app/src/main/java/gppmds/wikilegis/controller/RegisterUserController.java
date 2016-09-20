package gppmds.wikilegis.controller;

import android.content.Context;

import java.io.IOException;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.dao.EmailDAO;
import gppmds.wikilegis.dao.GetRequest;
import gppmds.wikilegis.dao.DaoUtilities;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.dao.PostRequest;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;


public class RegisterUserController {

    private static List<String> emailList = new ArrayList<String>();
    private static RegisterUserController instance = null;
    private final Context context;
    private EmailDAO emailDAO;

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

    public List<String> getAllEmails() {
        return emailList;
    }

    public void initControllerEmails() throws JSONException {

        emailDAO = EmailDAO.getInstance(context);

        if (emailDAO.isDatabaseEmpty()) {

            emailList = JSONHelper.emailListFromJSON(emailList);

            emailDAO.insertAllEmails(emailList);

        } else {
            emailList = emailDAO.getAllEmails();
        }
    }

    public String registerUser(String firstName,
                               String lastName,
                               String email,
                               String password,
                               String passwordConfirmation) {

        try {

            User user = new User(firstName, lastName, email, password, passwordConfirmation);
            PostRequest postRequest = new PostRequest(user, context);
            postRequest.execute();
            return "SUCESS";

        } catch (UserException e) {
            String exceptionMessage = e.getMessage();
            return exceptionMessage;
        }
    }
}

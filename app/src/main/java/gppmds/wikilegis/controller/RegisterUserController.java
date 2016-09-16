package gppmds.wikilegis.controller;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.dao.EmailDAO;
import gppmds.wikilegis.dao.GetRequest;
import gppmds.wikilegis.dao.JSONHelper;
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

            //Usuário instaciado e criado que será passado para a base de dados
            User user = new User(firstName, lastName, email, password, passwordConfirmation);

            return "SUCESS";

        } catch (UserException e) {
            String exceptionMessage = e.getMessage();
            return exceptionMessage;

        }
    }

    public static boolean validateEmailIsNotRepeated(String email) {

        for (int i=0; i<emailList.size(); i++) {
            if (email.equals(emailList.get(i))) {
                return false;
            }
        }
        return true;
    }
}

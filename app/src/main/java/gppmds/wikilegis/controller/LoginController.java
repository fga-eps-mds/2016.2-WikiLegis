package gppmds.wikilegis.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.dao.PostRequest;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;

/**
 * Created by marcelo on 10/6/16.
 */

public class LoginController {

    private static LoginController instance = null;
    private final Context context;

    private LoginController(final Context contextParameter) {
        this.context = contextParameter;
    }

    public static LoginController getInstance(final Context context) {
        if (instance == null) {
            instance = new LoginController(context);
        } else {
			/* ! Nothing To Do. */
        }
        return instance;
    }

    public String confirmLogin(final String email, final String password) {
        PostRequest postRequest = new PostRequest(context,
                "http://wikilegis-staging.labhackercd.net/accounts/api-token-auth/");

        try {

            User userLogin = new User(email, password);

            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("username", userLogin.getEmail());
            builder.appendQueryParameter("password", userLogin.getPassword());

            String authentication = builder.build().getEncodedQuery();

            Log.v("TOKEN", authentication);

            String userInformation = null;

            try {
                userInformation = postRequest.execute(authentication,
                        "application/x-www-form-urlencoded").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            setUserWithSharedPreferences(userInformation);

            Log.v("Status", postRequest.getResponse() + "");

            //Reponse is 200 if authentication is correct.
            if(postRequest.getResponse() == 200) {
                Log.v("Token", userInformation);
                return "SUCESS";
            }
            //Reponse is 400 if authentication is incorrect.
            else {
                return "FAIL";
            }

        } catch (UserException exception) {
            String exceptionMessage = exception.getMessage();
            return exceptionMessage;
        }
    }

    private void setUserWithSharedPreferences(String userInformation) {
        JSONObject userJson = null;
        String token = null;

        try {
            userJson = new JSONObject(userInformation);
            token = userJson.getString("token");

            JSONObject user = userJson.getJSONObject("user");

            String firstName = user.getString("first_name");
            String lastName = user.getString("last_name");
            String email = user.getString("email");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
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
                if(userInformation!=null)
                    Log.i("UserInformation", userInformation);
                else
                    Log.i("Info", "UserInformation is null");
            } catch (InterruptedException e) {
                Log.e("Error", "InterruptException");
                e.printStackTrace();
            } catch (ExecutionException e) {
                Log.e("Error", "ExecutionException");
                e.printStackTrace();
            }

            setUserAsSharedPreferences(userInformation);

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

    private void setUserAsSharedPreferences(String userInformation) {

        JSONObject userJson = null;
        String token = null;

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        try {
            if (userInformation != null) {
                userJson=new JSONObject(userInformation);
                token=userJson.getString("token");

                JSONObject user=userJson.getJSONObject("user");

                String firstName=user.getString("first_name");
                String lastName=user.getString("last_name");
                String email=user.getString("email");

                createLoginSession(email, token, firstName, lastName, session);
            } else {
                Log.i("Info", "UserInformation is not valid");

                createSessionIsNotLogged(session);
            }

        } catch (JSONException e) {
            Log.e("Error", "JSONException");
            e.printStackTrace();
        }
    }

    public void createLoginSession(final String email,
                                   final String token,
                                   final String firstName,
                                   final String lastName,
                                   SharedPreferences session) {

        // All Shared Preferences Keys
        final String IS_LOGIN = "IsLoggedIn";
        // User token
        final String USER_TOKEN = "token";
        // User first name
        final String FIRST_NAME = "firstName";
        // User first name
        final String LAST_NAME = "lastName";
        // Email address
        final String KEY_EMAIL = "email";

        SharedPreferences.Editor editor = session.edit();

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(USER_TOKEN, token);
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.commit();
    }

    public void createSessionIsNotLogged(SharedPreferences session) {
        // All Shared Preferences Keys
        final String IS_LOGIN = "IsLoggedIn";

        // User token
        final String USER_TOKEN = "token";

        SharedPreferences.Editor editor = session.edit();

        editor.putBoolean(IS_LOGIN, false);
        editor.putString(USER_TOKEN, null);
        editor.commit();
    }
}

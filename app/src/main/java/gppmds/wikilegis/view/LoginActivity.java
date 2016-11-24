package gppmds.wikilegis.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.json.JSONException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        LoginFragment loginFragment = new LoginFragment();

        super.onCreate(savedInstanceState);


        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());

        boolean isLogged = session.getBoolean("IsLoggedIn", false);

        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(getBaseContext());

        final int CONNECTION_TYPE = dataDownloadController.connectionType();

        if (CONNECTION_TYPE == 2) {
            initDataWithDatabase();
        } else {
            updateDataWithDatabase();
        }

        if (isLogged) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);

        openFragment(loginFragment);
    }

    private void updateDataWithDatabase() {
        DataDownloadController dataDownloadController = DataDownloadController.getInstance(getApplicationContext());
        try {
            try {
                dataDownloadController.updateData();
            } catch (VotesException e) {
                e.printStackTrace();
            }
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }
    }

    private void initDataWithDatabase() {
        SegmentController segmentController = SegmentController.getInstance(getBaseContext());
        try {
            segmentController.initSegmentsWithDatabase();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        BillController billController = BillController.getInstance(getBaseContext());
        try {
            billController.initBillsWithDatabase();
        } catch (BillException e) {
            e.printStackTrace();
        }
    }


    private void openFragment(final Fragment fragmentToBeOpen) {

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_panel, fragmentToBeOpen);
        fragmentTransaction.commit();
    }
}

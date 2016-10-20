package gppmds.wikilegis.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.controller.VotesController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;

public class LoadingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("LoadingActivity", "");

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());

        boolean isLogged = session.getBoolean("IsLoggedIn", false);

        if (isLogged) {
            updateDataWithDatabase();
            Intent intent=new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {

            if (SegmentController.getInstance(getApplicationContext()).isSegmentDatabaseIsEmpty()) {
                setContentView(R.layout.activity_loading);
                Button button=(Button) findViewById(R.id.button);
                button.setOnClickListener(this);
            } else {
                updateDataWithDatabase();
                Intent intent=new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void updateDataWithDatabase() {
        DataDownloadController dataDownloadController = DataDownloadController.getInstance(getApplicationContext());
        try {
            try {
                dataDownloadController.updateData();
            } catch (VotesException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(final View view) {
        updateDataWithDatabase();


    }
}


package gppmds.wikilegis.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.controller.VotesController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());
        Intent intent=new Intent(LoadingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
//
//        boolean isLogged = session.getBoolean("IsLoggedIn", false);
//
//        if (isLogged) {
//            updateDataWithDatabase();
//            Intent intent=new Intent(LoadingActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//
//            if (SegmentController.getInstance(getApplicationContext()).isSegmentDatabaseIsEmpty()) {
//                setContentView(R.layout.activity_loading);
//                Button button=(Button) findViewById(R.id.button);
//                button.setOnClickListener(this);
//            } else {
//                updateDataWithDatabase();
//                Intent intent=new Intent(LoadingActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }
    }

//    private void updateDataWithDatabase() {
//        try {
//            SegmentController.getInstance(getApplicationContext()).initControllerSegments();
//
//            BillController billController=BillController.getInstance(getBaseContext());
//            billController.initControllerBills();
//
//            SegmentsOfBillController.getInstance(getApplicationContext()).initControllerSegmentsOfBill();
//
//            VotesController.getInstance(getBaseContext()).initControllerVotes();
//        } catch (SegmentException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (BillException e) {
//            e.printStackTrace();
//        } catch (VotesException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void onClick(final View view) {
//        try {
//            SegmentController.getInstance(getApplicationContext()).initControllerSegments();
//
//            BillController billController = BillController.getInstance(getBaseContext());
//            billController.initControllerBills();
//
//            SegmentsOfBillController.getInstance(getApplicationContext()).initControllerSegmentsOfBill();
//
//            Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        } catch (SegmentException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (BillException e) {
//            e.printStackTrace();
//        }
//
//        VotesController votesController = VotesController.getInstance(getBaseContext());
//
//        try {
//            votesController.initControllerVotes();
//        } catch (SegmentException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (VotesException e) {
//            e.printStackTrace();
//        }
//    }
}


package gppmds.wikilegis.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;

public class LoadingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (SegmentController.getInstance(getApplicationContext()).isSegmentDatabaseIsEmpty()) {
                setContentView(R.layout.activity_loading);
                Button button = (Button) findViewById(R.id.button);
                button.setOnClickListener(this);

            } else {
                SegmentController.getInstance(getApplicationContext()).initControllerSegments();

                BillController billController = BillController.getInstance(getBaseContext());
                billController.initControllerBills();

                SegmentsOfBillController.getInstance(getApplicationContext()).initControllerSegmentsOfBill();

                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
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
        try {
            SegmentController.getInstance(getApplicationContext()).initControllerSegments();

            BillController billController = BillController.getInstance(getBaseContext());
            billController.initControllerBills();

            SegmentsOfBillController.getInstance(getApplicationContext()).initControllerSegmentsOfBill();

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
}


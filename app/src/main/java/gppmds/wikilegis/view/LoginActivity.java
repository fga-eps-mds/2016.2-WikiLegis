package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.RegisterUserController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.SegmentsOfBill;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_user);

        RegisterUserFragment registerUser = new RegisterUserFragment();
        RegisterUserController controller = RegisterUserController.getInstance(getApplicationContext());

        LoginFragment loginFragment = new LoginFragment();

        ViewBillFragment bill = new ViewBillFragment();

        SegmentController segmentController = SegmentController.getInstance(getApplicationContext());

        try {
            segmentController.initControllerSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BillController billController = new BillController(getBaseContext());

        try {
            billController.initControllerBills();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        SegmentsOfBillController segmentsOfBillController = SegmentsOfBillController.getInstance(getApplicationContext());

        try {
            segmentsOfBillController.initControllerSegmentsOfBill();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        //Pegar os segmentos da Bill com ID = 18

        List<SegmentsOfBill> segmentsOfBillList = new ArrayList<>();

        segmentsOfBillList = SegmentsOfBillController.getAllSegmentsOfBill(38);

        RecyclerViewAdapterBill recyclerViewAdapterBill = new RecyclerViewAdapterBill(getBaseContext());

        recyclerViewAdapterBill.initiSegm();

        openFragment(loginFragment);

    }
    private void openFragment(Fragment fragmentToBeOpen){

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_panel, fragmentToBeOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

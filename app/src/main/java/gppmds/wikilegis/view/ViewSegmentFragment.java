package gppmds.wikilegis.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.VotesController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

public class ViewSegmentFragment extends Fragment implements View.OnClickListener{

    private static Integer segmentId;
    private static Integer billId;
    private TextView likes;
    private TextView dislikes;
    private TextView segmentText;
    private TextView billText;
    private List<Segment> segmentList;
    private SegmentController segmentController;
    private List<Segment> segmentListAux= new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Button proposalButon;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        segmentId = getArguments().getInt("segmentId");
        billId = getArguments().getInt("billId");

        setView(inflater, container);

        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        segmentController = SegmentController.getInstance(getContext());
        segmentList = SegmentController.getAllSegments();

        floatingActionButton = (FloatingActionButton)getActivity().findViewById(R.id.floatingButton);
        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton.setOnClickListener(this);

        settingText();

        TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);

        segmentListAux= SegmentController.getProposalsOfSegment(segmentList, segmentId);
        RecyclerViewAdapterContent content = new RecyclerViewAdapterContent(segmentListAux);
        Log.d("TAMANHO2", segmentListAux.size() + "");
        recyclerView.setAdapter(content);

        return view;
    }

    private void setView(final LayoutInflater inflater, final ViewGroup container) {

        view = inflater.inflate(R.layout.fragment_view_segment, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_viewSegment);
        segmentText = (TextView) view.findViewById(R.id.contentSegment);
        billText = (TextView) view.findViewById(R.id.titleBill);
        likes = (TextView) view.findViewById(R.id.textViewNumberLike);
        dislikes = (TextView) view.findViewById(R.id.textViewNumberDislike);

    }

    private void settingText() {
        try {
            dislikes.setText(VotesController.getDislikesOfSegment(segmentId).toString());
            likes.setText(VotesController.getLikesOfSegment(segmentId).toString());
            segmentText.setText(SegmentController.getSegmentById(segmentId).getContent());
            billText.setText(BillController.getBillById(billId).getTitle());
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        }
    }
    private void openFragment(Fragment fragmentToBeOpen){

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.view_segment_fragment, fragmentToBeOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.floatingButton){
            Bundle segmentAndBillId = new Bundle();
            segmentAndBillId.putInt("billId", billId);
            segmentAndBillId.putInt("segmentId", segmentId);

            CreateSuggestProposal createSuggestProposal = new CreateSuggestProposal();
            createSuggestProposal.setArguments(segmentAndBillId);

            openFragment(createSuggestProposal);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        floatingActionButton.setVisibility(View.INVISIBLE);
    }
}

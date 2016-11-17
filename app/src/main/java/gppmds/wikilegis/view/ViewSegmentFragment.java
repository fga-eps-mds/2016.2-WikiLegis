package gppmds.wikilegis.view;



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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.VotesController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Segment;

public class ViewSegmentFragment extends Fragment implements View.OnClickListener{

    private static Integer segmentId;
    private static Integer billId;
    private TextView likes;
    private TextView dislikes;
    private TextView segmentText;
    private TextView billText;
    private ImageView likesIcon;
    private ImageView dislikesIcon;
    private List<Segment> segmentList;
    private SegmentController segmentController;
    private List<Segment> proposalsList = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton floatingActionButton;

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

        DataDownloadController dataDownloadController = DataDownloadController.getInstance(getContext());
        //TODO TESTAR
        if(dataDownloadController.connectionType() < 2){
            Log.d("Entrou aqui no wifi...", "");
            try {
                proposalsList = segmentController.getSegmentsOfBillById(billId+"" ,""+segmentId, true);
                Log.d("Numero de propostas",proposalsList.size()+"");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (BillException e) {
                e.printStackTrace();
            } catch (SegmentException e) {
                e.printStackTrace();
            }
        }else {
            proposalsList = SegmentController.getProposalsOfSegment(segmentList, segmentId);
        }

            RecyclerViewAdapterContent content = new RecyclerViewAdapterContent(proposalsList);
        recyclerView.setAdapter(content);

        return view;
    }

    private void setView(final LayoutInflater inflater, final ViewGroup container) {
        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(getContext());

        final int WIFI = 0;
        final int MOBILE_3G = 1;
        final int NO_NETWORK = 2;

        int connectionType = dataDownloadController.connectionType();

        if(connectionType == WIFI || connectionType == MOBILE_3G) {
            view = inflater.inflate(R.layout.fragment_view_segment, container, false);
            likes = (TextView) view.findViewById(R.id.textViewNumberLike);
            dislikes = (TextView) view.findViewById(R.id.textViewNumberDislike);
            billText = (TextView) view.findViewById(R.id.titleBill);
            segmentText = (TextView) view.findViewById(R.id.contentSegment);
            likesIcon = (ImageView)view.findViewById(R.id.imageViewLike);
            dislikesIcon = (ImageView)view.findViewById(R.id.imageViewDislike);

            setLikedAndDislikedIcon(true);
            setLikedAndDislikedIcon(false);

        } else if (connectionType == NO_NETWORK){
            view = inflater.inflate(R.layout.fragment_view_segment_offline, container, false);
            billText = (TextView) view.findViewById(R.id.titleBillOffline);
            segmentText = (TextView) view.findViewById(R.id.contentSegmentOffline);
        }

        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_viewSegment);

        likes.setOnClickListener(this);
        likesIcon.setOnClickListener(this);

        dislikes.setOnClickListener(this);
        dislikesIcon.setOnClickListener(this);
    }

    private void settingText() {
        try {
            DataDownloadController dataDownloadController = DataDownloadController.getInstance(getContext());
            SegmentController segmentController = SegmentController.getInstance(getContext());
            //TODO TESTAR
            if(dataDownloadController.connectionType() < 2) {
                final Segment SEGMENT = segmentController.getSegmentByIdFromList(segmentId);

                segmentText.setText(SEGMENT.getContent());
                billText.setText(BillController.getBillByIdFromList(billId).getTitle());
                dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,true) +"");
            }else{
                final Segment SEGMENT = SegmentController.getSegmentById(segmentId, getContext());
                segmentText.setText(SEGMENT.getContent());
                billText.setText(BillController.getBillById(billId).getTitle());
            }
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLikedAndDislikedIcon(boolean vote) {
        VotesController votesController = VotesController.getInstance(getContext());

        boolean evaluated = votesController.getVoteByUserAndIdSegment(118, segmentId, vote);

        if(evaluated && vote == true) {
            Log.d("BLA", "JÁ LIKEI ESSE SEGMENT");

            //TODO: Change the dislike icon for a highlighted like icon
            likesIcon.setImageDrawable(getResources().getDrawable(R.drawable.dislike));
        }

        if(evaluated && vote == false) {
            Log.d("BLA", "JÁ DISLIKEI ESSE SEGMENT");

            //TODO: Change the like icon for a highlighted dislike icon
            dislikesIcon.setImageDrawable(getResources().getDrawable(R.drawable.like));
        }
    }

    @Override
    public void onClick(View view) {
        final int idView = view.getId();
        String resultPost= "fail" ;
        String resultDelete = "Fail";
        if(idView == R.id.imageViewLike ) {
            VotesController votesController = VotesController.getInstance(getContext());

            try {

                resultPost = votesController.registerVote(segmentId, true);
                setLikedAndDislikedIcon(true);
                likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");

            } catch (VotesException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (BillException e) {
                e.printStackTrace();
            }
            if (resultPost == "SUCCESS") {
                Toast.makeText(getContext(), "like", Toast.LENGTH_SHORT)
                        .show();
            }
            Log.d("LIKEI", "onClick ");
            Log.d("resut:", resultPost);
            Log.d("ID SEGMENT", segmentId + "");
        }else if(idView == R.id.imageViewDislike ) {
            VotesController votesController = VotesController.getInstance(getContext());

            try {
                resultPost =  votesController.registerVote(segmentId, false);
                setLikedAndDislikedIcon(false);
                dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,false)+"");
            } catch (VotesException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (BillException e) {
                e.printStackTrace();
            }
            if(resultPost == "SUCCESS") {
                Toast.makeText(getContext(), "deslike", Toast.LENGTH_SHORT)
                        .show();
            }
            Log.d("desLIKEI", "onClick ");
            Log.d("resut:" , resultPost);
        }
        else {
            SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(getContext());

            if(session.getBoolean(getContext().getString(R.string.is_logged_in), false)){

                Bundle segmentAndBillId = new Bundle();
                segmentAndBillId.putInt("billId", billId);
                segmentAndBillId.putInt("segmentId", segmentId);
                CreateSuggestProposal createSuggestProposal = new CreateSuggestProposal();
                createSuggestProposal.setArguments(segmentAndBillId);
                openFragment(createSuggestProposal);

            }
            else{
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        }
    }

        private void openFragment(Fragment fragmentToBeOpen){

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.view_segment_fragment, fragmentToBeOpen,
                "SUGGEST_PROPOSAL");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        floatingActionButton.setVisibility(View.INVISIBLE);
    }
}

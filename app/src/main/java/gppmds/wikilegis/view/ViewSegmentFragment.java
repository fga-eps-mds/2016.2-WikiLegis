package gppmds.wikilegis.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.Vote;

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
    private Button proposalButon;

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

        view = inflater.inflate(R.layout.fragment_view_segment, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_viewSegment);
        segmentText = (TextView) view.findViewById(R.id.contentSegment);
        billText = (TextView) view.findViewById(R.id.titleBill);
        likes = (TextView) view.findViewById(R.id.textViewNumberLike);
        dislikes = (TextView) view.findViewById(R.id.textViewNumberDislike);
        proposalButon = (Button)view.findViewById(R.id.buttonGreen);
        likesIcon = (ImageView)view.findViewById(R.id.imageViewLike);
        dislikesIcon = (ImageView)view.findViewById(R.id.imageViewDislike);

        proposalButon.setOnClickListener(this);

        likes.setOnClickListener(this);
        likesIcon.setOnClickListener(this);

        dislikes.setOnClickListener(this);
        dislikesIcon.setOnClickListener(this);

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
        } else if (connectionType == NO_NETWORK){
            view = inflater.inflate(R.layout.fragment_view_segment_offline, container, false);
            billText = (TextView) view.findViewById(R.id.titleBillOffline);
            segmentText = (TextView) view.findViewById(R.id.contentSegmentOffline);
        }

        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_viewSegment);
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
                dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,false)+"");
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

    @Override
    public void onClick(View view) {
        final int idView = view.getId();
        String result= "fail" ;
        if(idView == R.id.imageViewLike ) {
             VotesController votesController = VotesController.getInstance(getContext());
            try {
            result =  votesController.registerVote(billId, true);
            } catch (VotesException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(result == "SUCCESS"){
                Toast.makeText(getContext(), "like", Toast.LENGTH_SHORT)
                        .show();
            }else{
                Toast.makeText(getContext(), "sua avaliação não foi efetuada", Toast.LENGTH_SHORT)
                        .show();
            }
            Log.d("LIKEI", "onClick ");
            Log.d("resut:" , result);

        }

        else if(idView == R.id.imageViewDislike ) {
            VotesController votesController = VotesController.getInstance(getContext());
            try {
                votesController.registerVote(billId, false);
            } catch (VotesException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(result == "SUCCESS"){
                Toast.makeText(getContext(), "deslike", Toast.LENGTH_SHORT)
                        .show();
            }else{
                Toast.makeText(getContext(), "sua avaliação não foi efetuada", Toast.LENGTH_SHORT)
                        .show();
            }
            Log.d("desLIKEI", "onClick ");
            Log.d("resut:" , result);
        }
        else {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.fragment_proposal);
            dialog.show();

            Button saveProposalButton = (Button) dialog.findViewById(R.id.save);

            saveProposalButton.setOnClickListener(new View.OnClickListener() {

                String result = "FAIL";

                @Override
                public void onClick(View v) {
                    EditText proposalField = (EditText) dialog.findViewById(R.id.proposal);
                    String proposalTyped = proposalField.getText().toString();

                    SegmentController segmentController = SegmentController.getInstance(getContext());

                    try {
                        result = segmentController.registerSegment(billId, 1, proposalTyped);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SegmentException e) {
                        e.printStackTrace();
                    }

                    if (result == "SUCCESS") {
                        Toast.makeText(getContext(), "Obrigado pela sugestão!", Toast.LENGTH_SHORT)
                                .show();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(getContext(), "Desculpe, um problema ocorreu",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });
        }
    }
}

package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.CommentSegmentController;
import gppmds.wikilegis.exception.CommentsException;
import gppmds.wikilegis.model.Segment;


public class CreateComment extends Fragment implements View.OnClickListener {

    private EditText commentEditText;
    private FloatingActionButton floatingActionButton;
    private List<Segment> listSegment;

    public CreateComment(){
    }

    public CreateComment(List<Segment> listSegment){
        this.listSegment = listSegment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_create_comment, container, false);

        floatingActionButton = (FloatingActionButton) getActivity()
                .findViewById(R.id.floatingButton);
        floatingActionButton.setVisibility(View.INVISIBLE);

        Button saveComment = (Button) view.findViewById(R.id.saveComment);
        saveComment.setOnClickListener(this);

        commentEditText = (EditText) view.findViewById(R.id.commentEditText);

        TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);

        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recyclerViewComment);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerViewAdapterContent content = new RecyclerViewAdapterContent(listSegment,
                getArguments().getInt("idBill"), getArguments().getInt("idSegment"));
        recyclerView.setAdapter(content);

        return view;
    }

    private String saveComment(Integer segmentId){

        CommentSegmentController commentSegmentController = CommentSegmentController.getInstance
                (getContext());

        String proposalTyped = commentEditText.getText().toString();

        String result = null;

        try{
            result = commentSegmentController.registerComment(segmentId, proposalTyped,
                    getContext());

        } catch(JSONException e){
            e.printStackTrace();
        } catch(CommentsException e){
            e.printStackTrace();
        }

        if(result.equals("SUCCESS")){
            result =  getContext().getResources().getString(R.string.success_comment);
        }
        else if(result.equals(getContext().getResources().getString(R.string.empty_comment))){
            commentEditText.requestFocus();
            commentEditText.setError(result);
        }
        else{
            result = getContext().getResources().getString(R.string.connection_problem);
            Log.d("Connection problem ", result);
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.saveComment){

            Integer idSegment = getArguments().getInt("idSegment");

            Log.d("ID SEGMENT", idSegment + "");
            String savingStatus = saveComment(idSegment);

            if(savingStatus.equals(getContext().getResources().getString(
                    R.string.success_comment))){
                Toast.makeText(getContext(), savingStatus, Toast.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        floatingActionButton.setVisibility(View.INVISIBLE);
    }
}

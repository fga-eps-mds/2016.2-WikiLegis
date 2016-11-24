package gppmds.wikilegis.view;

import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.CommentSegmentController;
import gppmds.wikilegis.controller.DataDownloadController;

import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.VotesController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.CommentsException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;

import gppmds.wikilegis.model.Comments;
import gppmds.wikilegis.model.Segment;

public class RecyclerViewAdapterContent extends RecyclerView.Adapter<RecyclerViewAdapterContent
        .ContentViewHolder> {

    private List<Segment> listSegment = new ArrayList<>();
    private Context context;
    private Integer billId;
    private Integer segmentId;
    private Integer userId;

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        View cardView;
        TextView proposals;
        TextView likes;
        TextView dislikes;
        TextView billText;
        TextView contentSegment;
        ImageView commentSegment;
        ImageView likesIcon;
        ImageView dislikesIcon;

        ContentViewHolder(final View itemView, final List<Segment> listSegment) {
            super(itemView);

            cardView = itemView.findViewById(R.id.frameCardViewSegment);
            proposals = (TextView) itemView.findViewById(R.id.textViewSegment);
            likes = (TextView) itemView.findViewById(R.id.textViewNumberLikeCard);
            dislikes = (TextView) itemView.findViewById(R.id.textViewNumberDislikeCard);
            billText = (TextView) itemView.findViewById(R.id.titleBill);
            contentSegment = (TextView) itemView.findViewById(R.id.contentSegment);
            commentSegment = (ImageView) itemView.findViewById(R.id.imageViewProposalCard);

            likesIcon = (ImageView) itemView.findViewById(R.id.imageViewLikeCard);
            dislikesIcon = (ImageView) itemView.findViewById(R.id.imageViewDislikeCard);

            //If the view is the header, the comment segment will be null
            if(commentSegment != null){

                commentSegment.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){

                        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(
                                view.getContext());

                        Bundle bundle = new Bundle();
                        bundle.putInt("idSegment", Integer.parseInt(cardView.getTag(R.id.idSegment)
                                .toString()));
                        bundle.putInt("idBill", Integer.parseInt(cardView.getTag(R.id.idBill)
                                .toString()));

                        if(session.getBoolean("IsLoggedIn", false)){
                            CommentSegmentController commentSegmentController =
                                    CommentSegmentController.getInstance(view.getContext());

                            Log.d("Id do segmento: ", cardView.getTag(R.id.idSegment).toString());

                            List<Comments> commentsList = null;
                            try {
                                commentsList = commentSegmentController.
                                        getCommentsByIdOfSegment(Integer.parseInt(
                                                cardView.getTag(R.id.idSegment).toString()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (CommentsException e) {
                                e.printStackTrace();
                            }

                            CreateComment createComment = new CreateComment(commentsList);
                            createComment.setArguments(bundle);

                            AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                            activity.getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                    .replace(R.id.main_content, createComment, "COMMENT_FRAGMENT")
                                    .commit();
                        }
                        else{
                            Toast.makeText(itemView.getContext(), "Entre para comentar!",
                                    Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(view.getContext(), LoginActivity.class);
                            view.getContext().startActivity(intent);
                        }
                    }
                });
            }
        }
    }

    RecyclerViewAdapterContent(final List<Segment> listSegment, Integer billId, Integer segmentId){
        this.listSegment = listSegment;
        this.billId = billId;
        this.segmentId = segmentId;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        context = parent.getContext();

        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(context);

        int connectionType = dataDownloadController.connectionType();

        ContentViewHolder contentViewHolder = null;

        final int HEADER = 0;

        if(viewType == HEADER){
            contentViewHolder = renderItemViewHolder(connectionType, parent,
                    R.layout.offline_header_view_segment, R.layout.online_header_view_segment);
        }
        else {
            contentViewHolder = renderItemViewHolder(connectionType, parent,
                    R.layout.item_view_segment_offline, R.layout.item_view_segment);
        }

        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, final int position) {

        holder.cardView.setTag(R.id.idSegment, listSegment.get(position).getId());
        holder.cardView.setTag(R.id.idBill, billId);

        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(context);
        int connectionType = dataDownloadController.connectionType();

        final int HEADER = 0;

        if(position == HEADER){
            final VotesController votesController = VotesController.getInstance(context);

            boolean evaluatedTrue = votesController.getVoteByUserAndIdSegment(userId, segmentId, true);
            boolean evaluatedFalse = votesController.getVoteByUserAndIdSegment(userId, segmentId, false);

            if(evaluatedTrue) {
                holder.likesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_up));
            } else if(evaluatedFalse) {
                holder.dislikesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_down));
            }

            try{
                setHeaderInfo(holder, connectionType, segmentId, billId);
            } catch(BillException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            } catch(VotesException e){
                e.printStackTrace();
            } catch(SegmentException e){
                e.printStackTrace();
            }
        }
        else{

            holder.proposals.setText(listSegment.get(position).getContent());

            try{
                holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,
                        false) + "");
                holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,
                        true) + "");
            } catch(BillException e){
                e.printStackTrace();
            } catch(VotesException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }
        }

        final VotesController votesController = VotesController.getInstance(context);

        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);
        final Integer userId = session.getInt(context.getResources().getString(R.string.id), 0);

        holder.likesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean evaluatedTrue = votesController.getVoteByUserAndIdSegment(userId, segmentId, true);
                boolean evaluatedFalse = votesController.getVoteByUserAndIdSegment(userId, segmentId, false);

                String resultPost = "";
                if(evaluatedTrue) {
                    try {
                        try {
                            votesController.deleteVote(segmentId, userId);
                            holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                            holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");
                        } catch (BillException e) {
                            e.printStackTrace();
                        }
                        holder.likesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_up_outline));
                    } catch (VotesException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if(evaluatedFalse) {
                    try {
                        votesController.updateVote(segmentId, userId, true);
                        holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                        holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");

                        holder.likesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_up));
                        holder.dislikesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_down_outline));
                    } catch (BillException e) {
                        e.printStackTrace();
                    } catch (VotesException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        resultPost = votesController.registerVote(segmentId, true);
                        holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                        holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");

                        holder.likesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_up));
                    } catch (VotesException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (BillException e) {
                        e.printStackTrace();
                    }

                    if (resultPost == "SUCCESS") {
                        Toast.makeText(context, "like", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                try {
                    holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");
                    holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                } catch (BillException e) {
                    e.printStackTrace();
                } catch (VotesException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.dislikesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean evaluatedTrue = votesController.getVoteByUserAndIdSegment(userId, segmentId, true);
                boolean evaluatedFalse = votesController.getVoteByUserAndIdSegment(userId, segmentId, false);

                String resultPost = "";
                if(evaluatedFalse) {
                    try {
                        try {
                            votesController.deleteVote(segmentId, userId);
                            holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                            holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");
                        } catch (BillException e) {
                            e.printStackTrace();
                        }
                        holder.dislikesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_down_outline));
                    } catch (VotesException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if(evaluatedTrue) {
                    try {
                        votesController.updateVote(segmentId, userId, false);
                        holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                        holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");

                        holder.likesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_up_outline));
                        holder.dislikesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_down));
                    } catch (BillException e) {
                        e.printStackTrace();
                    } catch (VotesException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        resultPost = votesController.registerVote(segmentId, false);

                        holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                        holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");

                        holder.dislikesIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb_down));
                    } catch (VotesException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (BillException e) {
                        e.printStackTrace();
                    }
                    if (resultPost == "SUCCESS") {
                        Toast.makeText(context, "deslike", Toast.LENGTH_SHORT)
                                .show();
                    }
                    Log.d("resut:", resultPost);
                }

                try {
                    holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, false) + "");
                    holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId, true) + "");

                } catch (BillException e) {
                    e.printStackTrace();
                } catch (VotesException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setHeaderInfo(final ContentViewHolder holder, Integer connectionType,Integer
            segmentId, Integer billId) throws BillException,
            JSONException, VotesException, SegmentException{
        final int WIFI = 0;
        final int MOBILE_3G = 1;

        SegmentController segmentController = SegmentController.getInstance(context);

        holder.billText.setText(BillController.getBillByIdFromList(billId).getTitle());
        Log.d("SEGMENT ID ON LIST", segmentId+"");
        holder.contentSegment.setText(segmentController.getSegmentByIdFromList(segmentId).getContent());

        if(connectionType == WIFI || connectionType == MOBILE_3G) {
            holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,true)
                    +"");
            holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,
                    false)+"");
        }
    }

    private ContentViewHolder renderItemViewHolder(Integer connectionType, final ViewGroup parent,
                                                   Integer offlineLayoutId, Integer onlineLayoutId){
        ContentViewHolder contentViewHolder = null;

        final int WIFI = 0;
        final int MOBILE_3G = 1;
        final int NO_NETWORK = 2;

        if(connectionType == WIFI || connectionType == MOBILE_3G){
            View view = LayoutInflater.from(parent.getContext()).inflate(onlineLayoutId,
                    parent, false);
            contentViewHolder = new ContentViewHolder(view, listSegment);
        }
        else {
            if(connectionType == NO_NETWORK){
                View view = LayoutInflater.from(parent.getContext()).inflate(offlineLayoutId, parent,
                        false);
                contentViewHolder = new ContentViewHolder(view, listSegment);
            }
        }

        return contentViewHolder;
    }

    @Override
    public int getItemCount() {
        return listSegment.size();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position){
        final int HEADER_POSITION = 0;
        final int ITEM_POSITION = 1;

        int viewType;

        if(position == HEADER_POSITION){
            viewType = HEADER_POSITION;
        }
        else{
            viewType = ITEM_POSITION;
        }
        return viewType;
    }
}

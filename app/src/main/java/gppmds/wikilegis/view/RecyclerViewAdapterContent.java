package gppmds.wikilegis.view;

import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.DataDownloadController;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.VotesException;

import gppmds.wikilegis.model.Segment;


public class RecyclerViewAdapterContent extends RecyclerView.Adapter<RecyclerViewAdapterContent
        .ContentViewHolder> {

    private List<Segment> listSegment = new ArrayList<>();
    private Context context;

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView proposals;
        TextView likes;
        TextView dislikes;
        ImageView commentSegment;

        ContentViewHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.frameCardViewSegment);
            proposals = (TextView) itemView.findViewById(R.id.textViewSegment);
            likes = (TextView) itemView.findViewById(R.id.textViewNumberLikeCard);
            dislikes = (TextView) itemView.findViewById(R.id.textViewNumberDislikeCard);
            commentSegment = (ImageView) itemView.findViewById(R.id.imageViewProposalCard);

            commentSegment.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(view
                            .getContext());

                    Bundle bundle = new Bundle();
                    bundle.putInt("idSegment", Integer.parseInt(cardView.getTag(R.id.idSegment)
                            .toString()));

                    if(session.getBoolean("IsLoggedIn", false)){
                        CreateComment createComment = new CreateComment();
                        createComment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                        activity.getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.main_content,createComment, "COMMENT_FRAGMENT")
                                .commit();
                    }
                    else{
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    RecyclerViewAdapterContent(final List<Segment> listSegment){
        this.listSegment = listSegment;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        context = parent.getContext();

        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(context);

        final int WIFI = 0;
        final int MOBILE_3G = 1;
        final int NO_NETWORK = 2;

        int connectionType = dataDownloadController.connectionType();

        ContentViewHolder contentViewHolder = null;

        if(connectionType == WIFI || connectionType == MOBILE_3G) {
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_segment,
                    parent, false);
            contentViewHolder=new ContentViewHolder(v);
        } else if (connectionType == NO_NETWORK) {
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout
                            .item_view_segment_offline, parent, false);
            contentViewHolder=new ContentViewHolder(v);
        }

        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, final int position) {

        holder.cardView.setTag(R.id.idSegment, listSegment.get(position).getId());
        holder.proposals.setText(listSegment.get(position).getContent());

        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(context);

        final int WIFI = 0;
        final int MOBILE_3G = 1;
        final int NO_NETWORK = 2;

        int connectionType = dataDownloadController.connectionType();

        int proposalId = listSegment.get(position).getId();
        try {
            holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(proposalId,false)+"");
            holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(proposalId,true) +"");
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listSegment.size();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

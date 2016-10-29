package gppmds.wikilegis.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Segment;


public class RecyclerViewAdapterContent extends RecyclerView.Adapter<RecyclerViewAdapterContent.ContentViewHolder> {
    private List<Segment> listSegment = new ArrayList<>();
    private Context context;

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView proposals;
        TextView likes;
        TextView dislikes;

        ContentViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            proposals = (TextView) itemView.findViewById(R.id.textViewSegment);
            likes = (TextView) itemView.findViewById(R.id.textViewNumberLikeCard);
            dislikes = (TextView) itemView.findViewById(R.id.textViewNumberDislikeCard);
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
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_segment_offline,
                    parent, false);
            contentViewHolder=new ContentViewHolder(v);
        }

        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, final int position) {
        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(context);

        final int WIFI = 0;
        final int MOBILE_3G = 1;
        final int NO_NETWORK = 2;

        int connectionType = dataDownloadController.connectionType();
        holder.proposals.setText(listSegment.get(position).getContent());

        //FIXME
        holder.likes.setText("7");
        holder.dislikes.setText("13");
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

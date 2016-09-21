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
import gppmds.wikilegis.model.Segment;

/**
 * Created by josue on 9/20/16.
 */
public class RecyclerViewAdapterContent extends RecyclerView.Adapter<RecyclerViewAdapterContent.ContentViewHolder>  {
    public List<Segment> listSegment = new ArrayList<>();

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
       TextView proposals;

         ContentViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
    RecyclerViewAdapterContent(List<Segment> listSegment){
        this.listSegment=listSegment;
    }
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_view_segment, parent, false);
        ContentViewHolder contentViewHolder = new ContentViewHolder(v);
        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        holder.proposals.setText(listSegment.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return listSegment.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}

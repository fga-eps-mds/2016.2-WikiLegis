package gppmds.wikilegis.view;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.VotesController;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Segment;


public class RecyclerViewAdapterContent extends RecyclerView.Adapter<RecyclerViewAdapterContent.ContentViewHolder> {
    private List<Segment> listSegment = new ArrayList<>();

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

            commentSegment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    openDialog(view.getContext());
                }
            });
        }

        private void openDialog(Context context){
            LayoutInflater inflater = LayoutInflater.from(context);
            View inputView = inflater.inflate(R.layout.fragment_comment, null);

            AlertDialog.Builder alertDialogProposalBuilder = new AlertDialog.Builder(context);
            alertDialogProposalBuilder.setView(inputView);

            alertDialogProposalBuilder
                    .setCancelable(false)
                    .setPositiveButton("Enviar", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogBox, int id){
                            //Colocar o que deve ser feito quando o usuario digitar o comentario
                        }
                    })

                    .setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialogBox, int id){
                                    dialogBox.cancel();
                                }
                            });

            AlertDialog alertDialogProposal = alertDialogProposalBuilder.create();
            alertDialogProposal.show();
        }
    }

    RecyclerViewAdapterContent(final List<Segment> listSegment){
        this.listSegment = listSegment;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_segment,
                parent, false);
        ContentViewHolder contentViewHolder = new ContentViewHolder(v);
        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, final int position) {
        holder.cardView.setTag(R.id.idSegment, listSegment.get(position).getId());
        holder.proposals.setText(listSegment.get(position).getContent());
        try {
            holder.likes.setText(VotesController.getLikesOfSegment(listSegment.get(position).getId()).toString());
            holder.dislikes.setText(VotesController.getDislikesOfSegment(listSegment.get(position).getId()).toString());
        } catch (VotesException e) {
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

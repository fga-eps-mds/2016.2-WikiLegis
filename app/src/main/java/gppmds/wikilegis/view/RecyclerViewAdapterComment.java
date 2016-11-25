package gppmds.wikilegis.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Comments;
import gppmds.wikilegis.model.Segment;

public class RecyclerViewAdapterComment extends RecyclerView.Adapter<RecyclerViewAdapterComment
        .ContentViewHolder> {

    private List<Comments> listComments = new ArrayList<>();
    private Context context;
    private Integer billId;
    private Integer segmentId;

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        View cardView;
        //TextView proposals;
        TextView likes;
        TextView dislikes;
        //TextView billText;
        TextView contentProposal;
        TextView comment;
        //ImageView commentSegment;

        ContentViewHolder(final View itemView, final List<Comments> listComments) {
            super(itemView);

            cardView = itemView.findViewById(R.id.frameCardViewProposal);
            //proposals = (TextView) itemView.findViewById(R.id.textViewSegment);
            likes = (TextView) itemView.findViewById(R.id.textViewNumberDislikeCardViewProposal);
            dislikes = (TextView) itemView.findViewById(R.id.textViewNumberDislikeCardViewProposal);
            //billText = (TextView) itemView.findViewById(R.id.titleBill);
            contentProposal = (TextView) itemView.findViewById(R.id.contentProposal);
            comment = (TextView) itemView.findViewById(R.id.textViewComment);
            //commentSegment = (ImageView) itemView.findViewById(R.id.imageViewProposalCard);
        }
    }

    RecyclerViewAdapterComment(final List<Comments> listComments, Integer billId, Integer segmentId){
        this.listComments = listComments;
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
            /*contentViewHolder = renderItemViewHolder(connectionType, parent,
                    R.layout.offline_header_view_segment, R.layout.online_header_view_segment);*/
            contentViewHolder = renderItemViewHolder(connectionType, parent,
                    R.layout.offline_header_view_proposal, R.layout.online_header_view_proposal);
        }
        else {
            /*contentViewHolder = renderItemViewHolder(connectionType, parent,
                    R.layout.item_view_segment_offline, R.layout.item_view_segment);*/
            contentViewHolder = renderItemViewHolder(connectionType, parent,
                    R.layout.item_view_proposal_comments, R.layout.item_view_proposal_comments);
        }

        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, final int position) {

        holder.cardView.setTag(R.id.idSegment, segmentId);
        holder.cardView.setTag(R.id.idBill, billId);

        DataDownloadController dataDownloadController =
                DataDownloadController.getInstance(context);
        int connectionType = dataDownloadController.connectionType();

        final int HEADER = 0;

        if(position == HEADER){
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
            Log.d("ENTREI NESSE ELSE", "onBindViewHolder ");
            holder.comment.setText(listComments.get(position).getComment());
        }
    }

    private void setHeaderInfo(final ContentViewHolder holder, Integer connectionType, Integer
            segmentId, Integer billId) throws BillException,
            JSONException, VotesException, SegmentException{
        final int WIFI = 0;
        final int MOBILE_3G = 1;

        SegmentController segmentController = SegmentController.getInstance(context);

        if(connectionType == WIFI || connectionType == MOBILE_3G) {

            List<Segment> listSegments =
                    segmentController.getSegmentsOfBillById(billId + "", "", true);


            Segment segment = null;

            for(Segment segmentAux : listSegments) {
                Log.d(segmentAux.getId() + "", segmentId + "");

                if((segmentAux.getId() + "").equals(segmentId + "")) {
                    segment = segmentAux;
                    break;
                }
            }

            holder.contentProposal.setText(segment.getContent());

            holder.likes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,true)
                    +"");
            holder.dislikes.setText(DataDownloadController.getNumberOfVotesbySegment(segmentId,
                    false)+"");
        } else {
            holder.contentProposal.setText(segmentController.getSegmentById(segmentId, context).getContent());
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
            contentViewHolder = new ContentViewHolder(view, listComments);
        }
        else {
            if(connectionType == NO_NETWORK){
                View view = LayoutInflater.from(parent.getContext()).inflate(offlineLayoutId, parent,
                        false);
                contentViewHolder = new ContentViewHolder(view, listComments);
            }
        }

        return contentViewHolder;
    }

    @Override
    public int getItemCount() {
        return listComments.size();
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

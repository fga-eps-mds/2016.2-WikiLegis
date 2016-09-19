package gppmds.wikilegis.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.model.Segment;

public class ViewSegmentFragment extends Fragment {
    private static Integer segmentId;
    private TextView segmentText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        segmentId = getArguments().getInt("id");

        View view = inflater.inflate(R.layout.fragment_view_segment, container, false);

        segmentText = (TextView)view.findViewById(R.id.segmentText);


        return view;
    }

    public Segment findSegment(List<Segment> segmentList, int idSegment , int billIndex){
        Segment segment =null;
        for(int index = 0 ; index <segmentList.size(); index++) {
            if(segmentList.get(index).getBill()==billIndex && segmentList.get(index).getId()==idSegment){

                segment = segmentList.get(index);

            }
        }
        return segment;
    }
}

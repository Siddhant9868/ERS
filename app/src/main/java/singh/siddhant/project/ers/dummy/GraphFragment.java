package singh.siddhant.project.ers.dummy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import singh.siddhant.project.ers.R;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

/**
 * Created by Siddhant Singh on 1/19/2018.
 */

public class GraphFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph,
                container, false);
        //populateGraphView(view);

        GraphViewXML graph = (GraphViewXML) view.findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 5),
                new DataPoint(4, 3)
        });
        graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();

            }
        });


        series.setSpacing(50);
// draw values on top
        //series.setDrawValuesOnTop(true);
        //series.setValuesOnTopColor(Color.RED);
series.setValuesOnTopSize(50);


        return view;
    }



    /*
    private void populateGraphView(View view) {
        // init example series data
        GraphView exampleSeries = new GraphView(new GraphViewData[] {
                new GraphViewData(1, 2.0d)
                , new GraphViewData(2, 1.5d)
                , new GraphViewData(3, 2.5d)
                , new GraphViewData(4, 1.0d)
        });

        LineGraphView graphView = new LineGraphView(
                getActivity() // context
                , "GraphViewDemo" // heading
        );
        graphView.addSeries(exampleSeries); // data

        try {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.graph1);
            layout.addView(graphView);
        } catch (NullPointerException e) {
            // something to handle the NPE.
        }
    }*/
}
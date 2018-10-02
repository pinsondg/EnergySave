package jmu.booze.energysave.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import jmu.booze.energysave.R;
import jmu.booze.energysave.model.DeviceCollection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private View rootview;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.home_fragment, container, false);
        ((TextView) rootview.findViewById(R.id.home_num_devices)).setText(
                String.format("%d", DeviceCollection.getInstance().size()));
        setUpGraph();
        return rootview;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setUpGraph() {
        GraphView view = rootview.findViewById(R.id.home_graph);
        view.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX) + "/18";
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " W";
                }
            }
        });
        LineGraphSeries<DataPoint> points = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 1),
                new DataPoint(2, 3),
                new DataPoint(3, 6),
                new DataPoint(4, 2),
                new DataPoint(5, 3),
                new DataPoint(6, 2)
        });
        view.addSeries(points);
        view.setTitle("Year to Date Usage");
        view.setTitleTextSize(10);
        view.getViewport().setMaxX(6);
        view.getViewport().setMinX(1);
    }
}

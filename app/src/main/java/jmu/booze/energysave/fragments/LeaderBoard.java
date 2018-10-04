package jmu.booze.energysave.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import jmu.booze.energysave.LeaderBoardAdapter;
import jmu.booze.energysave.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderBoard extends Fragment {


    private View rootView;

    public LeaderBoard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Money.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderBoard newInstance() {
        LeaderBoard fragment = new LeaderBoard();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.leaderboard_fragment, container, false);
        ListView listView = rootView.findViewById(R.id.leaderboard_list);
        listView.setAdapter(new LeaderBoardAdapter(this.getContext(), R.id.leaderboard_list));

        return rootView;
    }

}

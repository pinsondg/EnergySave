package jmu.booze.energysave.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jmu.booze.energysave.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Money#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Money extends Fragment {


    public Money() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Money.
     */
    // TODO: Rename and change types and number of parameters
    public static Money newInstance() {
        Money fragment = new Money();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money, container, false);
    }

}

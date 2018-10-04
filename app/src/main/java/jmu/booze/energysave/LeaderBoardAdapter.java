package jmu.booze.energysave;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jmu.booze.energysave.model.Department;
import jmu.booze.energysave.model.LeaderBoardList;

public class LeaderBoardAdapter extends ArrayAdapter<Department>{

    private List<Department> list;
    public LeaderBoardAdapter(@NonNull Context context, int resource) {
        super(context, 0, LeaderBoardList.getInstance());
        list = LeaderBoardList.getInstance();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_list, parent, false);
        TextView placemnt = convertView.findViewById(R.id.placement);
        String suffix = "";
        if ( position == 0 ) {
            suffix = "st";
        } else if ( position == 1 ) {
            suffix = "nd";
        } else if (position == 2 ){
            suffix = "rd";
        } else {
            suffix = "th";
        }
        placemnt.setText(String.format("%d%s",list.get(position).getDepartmentPlace(), suffix ));

        TextView name = convertView.findViewById(R.id.depatment_name);
        name.setText(list.get(position).getDepartmentName());
        return convertView;
    }
}

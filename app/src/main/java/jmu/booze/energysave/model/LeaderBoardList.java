package jmu.booze.energysave.model;

import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoardList extends ArrayList<Department> {

    private static LeaderBoardList leaderBoardList = new LeaderBoardList();

    private LeaderBoardList() {
        super();
    }

    public static LeaderBoardList getInstance() {
        return leaderBoardList;
    }

}
